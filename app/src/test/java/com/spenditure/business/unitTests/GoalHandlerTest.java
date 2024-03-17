package com.spenditure.business.unitTests;

import static org.junit.Assert.*;

import com.spenditure.logic.GoalHandler;
import com.spenditure.logic.exceptions.InvalidGoalException;
import com.spenditure.object.DateTime;
import com.spenditure.object.Goal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GoalHandlerTest {

    private GoalHandler goalHandler;

    @Before
    public void setup() {

        goalHandler = new GoalHandler(true);
        goalHandler.cleanup(true);

    }

    @After
    public void tearDown() {

        goalHandler = null;

    }

    @Test
    public void testGetByGoalIDValidCase() {

        Goal test = goalHandler.getGoalByID(1);

        assertEquals("Check 1st goal in Stub DB, 'eat less fast food'", "eat less fast food", test.getGoalName());

    }

    @Test
    public void testByGoalIDInvalidCase() {

        Goal test = goalHandler.getGoalByID(999999);

        assertNull("No goal should be found", test);

    }

    @Test
    public void testCreateGoalValidCase() {

        goalHandler.createGoal(1, "Buy less rare minerals online", new DateTime(2024, 12, 31), 500, 3);

        assertEquals("Goal ID should be 1 greater than the last in the stub DB", "Buy less rare minerals online", goalHandler.getGoalByID(4).getGoalName());

    }

    @Test
    public void testCreateGoalInvalidCase() {

        boolean caught = false;

        try {

            goalHandler.createGoal(1, "", new DateTime(2025, 12, 31), -100, 1);

        } catch(InvalidGoalException ignored) {

            caught = true;

        }

        assertTrue(caught);

    }

    @Test
    public void deleteGoalValidCase() {

        goalHandler.createGoal(1, "Buy less rare minerals online", new DateTime(2024, 12, 31), 500, 3);

        Goal test = goalHandler.getGoalByID(4);

        assertNotNull(test);

        goalHandler.deleteGoal(4);

        test = goalHandler.getGoalByID(4);

        assertNull(test);

    }

    @Test
    public void deleteGoalInvalidCase() {

        Goal test = goalHandler.getGoalByID(99999);

        assertNull(test);

        goalHandler.deleteGoal(99999);

        //No exceptions should be thrown
    }

    @Test
    public void testGoalsForUserIDValidCase() {

        List<Goal> list = goalHandler.getGoalsForUserID(1);

        assertEquals("There should be 3 items in the list", 3, list.size());

    }

    @Test
    public void testGoalsForUserIDInvalidCase() {

        List<Goal> list = goalHandler.getGoalsForUserID(999);

        assert(list.isEmpty());

    }

}
