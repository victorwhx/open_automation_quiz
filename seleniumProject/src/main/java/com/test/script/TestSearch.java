package com.test.script;

import com.test.util.BundleUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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

    @DataProvider(name = "searchItems")
    public static Object[][] words() {
        return new Object[][] {
                {"bing"},
                {"sc"}
        };
    }

    @Test(dataProvider = "searchItems")
    public void searchItemsTest(String item) throws InterruptedException {
        WebElement searchInput = getElement("homePage.searchBox");
        searchInput.clear();
        searchInput.sendKeys(item);
        searchInput.submit();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(bundle.getLocator("resultPage.resultItems")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(bundle.getLocator("resultPage.resultItems")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        getElement("resultPage.page2").click();

        getResult();
    }

    private void getResult() {
        List<String> domainList = new ArrayList<>();
        List<WebElement> resultList = getElements("resultPage.resultItems");

        System.out.println("结果列表");
        for (WebElement element : resultList) {
            String href = element.getAttribute("href");
            String domainTmp = getDomain(href);
            if (domainTmp != null) {
                domainList.add(domainTmp);
            } else {
                Assert.fail("Failed to get the domain.");
            }

            System.out.println(element.getText() + " ---> " + href);
        }

        Map<String, Integer> map = new HashMap<>();
        for (String str : domainList) {
            Integer num = map.get(str);
            map.put(str, num == null ? 1 : num + 1);
        }
        Set set = map.entrySet();
        Iterator it1 = map.keySet().iterator();

        System.out.println("");
        System.out.println("结果统计");
        while (it1.hasNext()) {
            Object key = it1.next();
            System.out.println(key + "：" + map.get(key));
        }
    }

    private  WebElement getElement(String key) {

        try {
            By locator = bundle.getLocator(key);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return driver.findElement(locator);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private  List<WebElement> getElements(String key) {
        try {
            By locator = bundle.getLocator(key);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
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
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        } else {
            Assert.fail("Wrong parameters in testng.xml");
        }

        bundle = new BundleUtil("src/main/resources/application.properties");
        driver.manage().window().maximize();
        driver.get(bundle.getString("url"));
        wait = new WebDriverWait(driver, 10);
    }

    @AfterTest
    public void endTest() {
        driver.quit();
    }
}
