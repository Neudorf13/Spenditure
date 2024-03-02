package com.spenditure;

import com.spenditure.business.ReportManagerTest;
import com.spenditure.business.unitTests.CategoryHandlerTest;
import com.spenditure.business.unitTests.ExceptionTest;

import com.spenditure.business.unitTests.TransactionExceptionTest;
import com.spenditure.business.unitTests.TransactionHandlerTest;
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
        ExceptionTest.class,
        TransactionExceptionTest.class,
        TransactionHandlerTest.class,
        ReportManagerTest.class
})
public class AllUnitTests {
}
