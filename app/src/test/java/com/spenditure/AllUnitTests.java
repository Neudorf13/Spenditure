package com.spenditure;

import com.spenditure.business.CategoryHandlerTest;
import com.spenditure.business.ExceptionTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        CategoryHandlerTest.class,
        ExceptionTest.class
})
public class AllUnitTests {
}
