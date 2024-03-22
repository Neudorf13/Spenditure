package com.spenditure;

import com.spenditure.


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountManagementTest.class,
        CategoryTest.class,
        GeneralReportTest.class,
        IllustrativeReportTest.class,
        ImageTest.class,
        TimeBaseHeavyTest.class,
        TimeBasedReportTest.class,
        TransactionTest.class
})

public class AllSystemTests {


}
