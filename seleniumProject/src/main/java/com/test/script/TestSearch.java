package com.test.script;

import com.test.util.BundleUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSearch {
    private WebDriver driver;
    private WebDriverWait wait;
    private BundleUtil bundle;
    private List<String> itemsList;

    @DataProvider(name = "searchItems")
    public static Object[][] words() {
        return new Object[][] {
                {"bing"},
                {"sc"}
        };
    }

    @Test(dataProvider = "searchItems")
    public void searchItemsTest(String item) {
        WebElement searchInput = getElements("homePage.searchBox").get(0);
        searchInput.clear();
        searchInput.sendKeys(item);
        searchInput.submit();

        WebElement page2 = getElements("resultPage.page2").get(0);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", page2);
        page2.click();

        getResult();
    }

    private void getResult() {
        List<String> domainList = new ArrayList<>();
        List<WebElement> resultList = getElements("resultPage.resultItems");

        itemsList.add("\n结果列表");
        for (WebElement element : resultList) {
            String href = element.getAttribute("href");
            String domainTmp = getDomain(href);
            if (domainTmp != null) {
                domainList.add(domainTmp);
            } else {
                Assert.fail("Failed to get the domain.");
            }

            itemsList.add(element.getText() + " ---> " + href);
        }

        Map<String, Integer> map = new HashMap<>();
        for (String str : domainList) {
            Integer num = map.get(str);
            map.put(str, num == null ? 1 : num + 1);
        }
        Set set = map.entrySet();
        Iterator it1 = map.keySet().iterator();

        itemsList.add("\n结果统计");
        while (it1.hasNext()) {
            Object key = it1.next();
            itemsList.add(key + ": " + map.get(key));
        }
    }

    private  List<WebElement> getElements(String key) {
        try {
            By locator = bundle.getLocator(key);
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return driver.findElements(locator);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDomain(String url) {
        Pattern p = Pattern.compile(bundle.getString("regx"),Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        boolean isFind = matcher.find();
        if (isFind) {
            return matcher.group();
        } else {
            Pattern p1 = Pattern.compile(bundle.getString("regxBU"),Pattern.CASE_INSENSITIVE);
            matcher = p1.matcher(url);
            if (!matcher.find()) {
                System.out.println("failed url: " + url);
                return null;
            }
            return matcher.group();
        }
    }

    @Parameters("browser")
    @BeforeClass
    public void startTest(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("blink-settings=imagesEnabled=false");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        } else {
            Assert.fail("Wrong parameters in testng.xml");
        }

        bundle = new BundleUtil("src/main/resources/application.properties");
        Point point = new Point(-1000, 0);
        driver.manage().window().setPosition(point);
        driver.manage().window().maximize();
        driver.get(bundle.getString("URL"));
        wait = new WebDriverWait(driver, 20, 200);
        itemsList = new ArrayList<>();

        Alert alert = driver.switchTo().alert();
        alert.accept();
        WebElement frame = getElements("homePage.iframe").get(0);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(frame, "src"));
        driver.switchTo().frame(frame);
    }

    @AfterTest
    public void endTest() {
        for (String str : itemsList) {
            System.out.println(str);
        }
        itemsList.clear();
        driver.quit();
    }
}