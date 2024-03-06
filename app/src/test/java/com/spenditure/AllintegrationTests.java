package com.spenditure;

import com.spenditure.business.integrationTests.CategoryHandlerIT;
import com.spenditure.business.integrationTests.GeneralReportHandlerIT;
import com.spenditure.business.integrationTests.ReportHandlerIntegrationTest;
import com.spenditure.business.integrationTests.TransactionHandlerIT;
import com.spenditure.business.integrationTests.UserManagerIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserManagerIT.class,
        CategoryHandlerIT.class,
        GeneralReportHandlerIT.class,
        ReportHandlerIntegrationTest.class,
        TransactionHandlerIT.class,
        UserManagerIT.class
})

public class AllintegrationTests {

}
