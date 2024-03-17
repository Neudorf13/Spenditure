package com.spenditure.logic;

import static com.spenditure.logic.GoalValidator.validateGoal;

import com.spenditure.application.Services;
import com.spenditure.database.GoalPersistence;
import com.spenditure.object.DateTime;
import com.spenditure.object.Goal;

import java.util.List;

public class GoalHandler {

    private static GoalPersistence dataAccessGoal;

    public GoalHandler(boolean getStubDB) {

        if(dataAccessGoal == null) {
            dataAccessGoal = Services.getGoalPersistence(getStubDB);
        }

    }

    public void cleanup(boolean getStubDB) {

        Services.restartGoalDB(getStubDB);

        dataAccessGoal = Services.getGoalPersistence(getStubDB);

    }

    /*

        createGoal

        Takes in all of the necessary parameters to build a Goal, validates it, and then sends it to
        the data layer to add to the database.

     */
    public void createGoal(int userID, String name, DateTime completeBy, int goalAmount, int categoryID) {

        Goal result = new Goal(1, userID, name, completeBy, goalAmount, categoryID);

        validateGoal(result);

        result.setGoalID(dataAccessGoal.generateUniqueID());

        dataAccessGoal.addGoal(result);

    }

    /*

        getGoalByID

        Passes the Goal ID to the data layer and returns the resulting Goal.

     */
    public Goal getGoalByID(int goalID) {

        return dataAccessGoal.getGoalByID(goalID);

    }

    /*

        getGoalsForUserID

        Passes the User ID to the data layer, and returns all goals linked to that User ID.

     */
    public List<Goal> getGoalsForUserID(int userID) {

        return dataAccessGoal.getGoalsForUser(userID);

    }

    /*

        deleteGoal

        Passes the Goal ID to the data layer, which will delete any matching goal.

     */
    public void deleteGoal(int goalID) {

        dataAccessGoal.deleteGoal(goalID);

    }

}
