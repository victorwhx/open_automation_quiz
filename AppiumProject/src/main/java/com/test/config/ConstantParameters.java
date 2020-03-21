package com.test.config;

public class ConstantParameters {
    // Test data and config file path
    public static final String testPlanPath = "src/main/java/com/test/data/TestPlan_Wandoujia.xlsx";
    public static final String propertiesPath = "src/main/resources/application.properties";

    // Excel sheet name
    public static final String testCasesSheet = "testCases";
    public static final String searchAndSelectAppSheet = "searchAndSelectApp";

    // Parameters for testCases sheet
    public static final int isRunCol = 2;
    public static final int testCaseResultCol = 3;

    // Parameters for searchAndSelectApp sheet
    public static final int testCaseIdCol = 0;
    public static final int keyWordsCol = 3;
    public static final int locatorCol = 4;
    public static final int dataCol = 5;
    public static final int stepResultCol = 6;
}
