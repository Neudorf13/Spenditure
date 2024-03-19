package com.spenditure.business.unitTests;

import static org.junit.Assert.*;

import com.spenditure.logic.SecurityQuestionHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SecurityQuestionHandlerTest {

    private SecurityQuestionHandler securityQuestionHandler;

    @Before
    public void setup() {

        securityQuestionHandler = new SecurityQuestionHandler(true);
        securityQuestionHandler.cleanup(true);

    }

    @After
    public void tearDown() {

        securityQuestionHandler = null;

    }

    @Test
    public void testGetSecurityQuestionValidCase() {

        String securityQuestion = securityQuestionHandler.getSecurityQuestion(1);

        assertEquals("name of childhood pet", securityQuestion);

        securityQuestion = securityQuestionHandler.getSecurityQuestion(2);

        assertEquals("fathers bachelors name", securityQuestion);

        securityQuestion = securityQuestionHandler.getSecurityQuestion(3);

        assertEquals("favorite UofM comp class course number", securityQuestion);

    }

    @Test
    public void testGetSecurityQuestionInvalidCase() {

        boolean caught = false;
        String securityQuestion;

        try {

            securityQuestion = securityQuestionHandler.getSecurityQuestion(0);

        } catch (AssertionError ignored) {

            caught = true;

        }

        assertTrue(caught);

        securityQuestion = securityQuestionHandler.getSecurityQuestion(90999);

        assertNull(securityQuestion);

    }

    @Test
    public void testGetAllSecurityQuestions() {

        ArrayList<String> questions = securityQuestionHandler.getAllSecurityQuestions();

        assert(questions.size() == 3);

        assertEquals("name of childhood pet", questions.get(0));
        assertEquals("fathers bachelors name", questions.get(1));
        assertEquals("favorite UofM comp class course number", questions.get(2));

    }

}
