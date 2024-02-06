package com.spenditure;

import com.spenditure.business.CategoryHandlerTest;
import com.spenditure.business.ExceptionTest;
import com.spenditure.business.ReportManagerTest;
import com.spenditure.business.TransactionHandlerTest;
import com.spenditure.objects.MainCategoryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoryHandlerTest.class,
        MainCategoryTest.class,
        ExceptionTest.class,
        TransactionHandlerTest.class,
        ReportManagerTest.class
})
public class AllUnitTests {
}
