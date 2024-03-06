package com.spenditure;

//import com.spenditure.business.ReportManagerTest;
import com.spenditure.business.unitTests.AccountManagerTest;
import com.spenditure.business.unitTests.CategoryHandlerTest;
import com.spenditure.business.unitTests.CategoryExceptionTest;

import com.spenditure.business.unitTests.DateTimeAdjusterTest;
import com.spenditure.business.unitTests.GeneralReportHandlerTest;
import com.spenditure.business.unitTests.ReportHandlerTest;
import com.spenditure.business.unitTests.TransactionExceptionTest;
import com.spenditure.business.unitTests.TransactionHandlerTest;
import com.spenditure.business.unitTests.UserManagerExceptionTest;
import com.spenditure.objects.MainCategoryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * All unit tests
 * @author Bao Ngo
 * @version 06 Feb 2024
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoryHandlerTest.class,
        MainCategoryTest.class,
        CategoryExceptionTest.class,
        TransactionExceptionTest.class,
        TransactionHandlerTest.class,
        ReportHandlerTest.class,
        AccountManagerTest.class,
        GeneralReportHandlerTest.class,
        UserManagerExceptionTest.class,
        DateTimeAdjusterTest.class
})
public class AllUnitTests {
}
