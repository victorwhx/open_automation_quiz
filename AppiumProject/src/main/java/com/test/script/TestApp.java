package com.test.script;

import com.test.config.ConstantParameters;
import com.test.config.KeyWordsAction;
import com.test.util.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestApp {
    private static Method[] methods;
    private static KeyWordsAction keywordsAction;
    private static boolean testResult = true;

    @BeforeTest
    public void initTest() {
        keywordsAction = new KeyWordsAction();
        methods = keywordsAction.getClass().getMethods();
    }

    /*
     * Get test cases from TestPlan_Wandoujia.xlsx
     * If isRun is "Yes", run each step in the test case.
     */
    @Test
    public void runTestCases() {
        String testPlanPath = ConstantParameters.testPlanPath;
        ExcelUtil.setExcelFile(testPlanPath);

        int testCaseCnt = ExcelUtil.getRowCount(ConstantParameters.testCasesSheet);
        for (int testCaseNo=1; testCaseNo<=testCaseCnt; testCaseNo++) {
            String testCaseID = ExcelUtil.getCellData(ConstantParameters.testCasesSheet, testCaseNo, ConstantParameters.testCaseIdCol);
            String isRun = ExcelUtil.getCellData(ConstantParameters.testCasesSheet, testCaseNo, ConstantParameters.isRunCol);
            assert isRun != null;
            if (isRun.equalsIgnoreCase("yes")) {
                int firstStep = ExcelUtil.getFirstStepRowNum(ConstantParameters.searchAndSelectAppSheet, testCaseID, ConstantParameters.testCaseIdCol);
                if (firstStep == -1) {
                    Assert.fail("Test Failed！");
                }
                int testLastStep = ExcelUtil.getLastStepRowNum(ConstantParameters.searchAndSelectAppSheet, testCaseID, firstStep);

                testResult = true;
                for (int step = firstStep; step <= testLastStep; step++) {
                    String keyword = ExcelUtil.getCellData(ConstantParameters.searchAndSelectAppSheet, step, ConstantParameters.keyWordsCol);
                    String data = ExcelUtil.getCellData(ConstantParameters.searchAndSelectAppSheet, step, ConstantParameters.dataCol);
                    String locator = ExcelUtil.getCellData(ConstantParameters.searchAndSelectAppSheet, step, ConstantParameters.locatorCol);

                    execute_Actions(keyword, locator, data);
                    handleTestResult(step, testCaseNo);
                    if (!testResult) {
                        break;
                    }
                }
                if (testResult) {
                    ExcelUtil.setCellData(ConstantParameters.testCasesSheet, testCaseNo, ConstantParameters.testCaseResultCol, "Pass");
                }
            }
        }
    }

    /*
     * Execute each test step by Java reflection.
     */
    private static void execute_Actions(String keyword, String locator, String data) {
        try {
            for (Method method : methods) {
                if (method.getName().equals(keyword)) {
                    if (locator == null) {
                        if (data == null) {
                            method.invoke(keywordsAction);
                        } else {
                            method.invoke(keywordsAction, data);
                        }
                    } else {
                        if (data == null) {
                            method.invoke(keywordsAction, locator);
                        } else {
                            method.invoke(keywordsAction, locator, data);
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            TestApp.setTestResult(false);
            e.printStackTrace();
            Assert.fail("Test Failed！");
        }
    }

    /*
     * Verify current test step pass or not.
     * If any one step fails, the test case will fail.
     */
    private static void handleTestResult(int stepRow, int casesRow) {
        if (testResult == false) {
            ExcelUtil.setCellData(ConstantParameters.searchAndSelectAppSheet, stepRow,
                    ConstantParameters.stepResultCol, "Fail");

            ExcelUtil.setCellData(ConstantParameters.testCasesSheet, casesRow,
                    ConstantParameters.testCaseResultCol, "Fail");

            execute_Actions("endTest", null, null);
            Assert.fail("Test Failed！");
        } else {
            ExcelUtil.setCellData(ConstantParameters.searchAndSelectAppSheet, stepRow,
                    ConstantParameters.stepResultCol, "Pass");
        }
    }

    public static void setTestResult(boolean testResult) {
        TestApp.testResult = testResult;
    }
}
