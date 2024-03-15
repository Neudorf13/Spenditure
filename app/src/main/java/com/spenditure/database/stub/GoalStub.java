package com.spenditure.database.stub;

import com.spenditure.database.GoalPersistence;
import com.spenditure.logic.exceptions.InvalidCategoryException;
import com.spenditure.logic.exceptions.InvalidGoalException;
import com.spenditure.object.DateTime;
import com.spenditure.object.Goal;
import com.spenditure.object.MainCategory;
import com.spenditure.object.Transaction;

import java.util.ArrayList;
import java.util.List;

public class GoalStub implements GoalPersistence {

    private ArrayList<Goal> goalList;

    public GoalStub(){
        this.goalList = new ArrayList<>();
        this.goalList.add(new Goal(1,1,"eat less fast food", new DateTime(2024,11,5), 500, 2));
        this.goalList.add(new Goal(2,1,"less gems in clash of clans", new DateTime(2024,6,12), 200, 3));
        this.goalList.add(new Goal(3,1,"less partying", new DateTime(2025,1,1), 750, 3));
    }


    @Override
    public void addGoal(Goal goal) {
        if(goal != null) {
            goalList.add(goal);
        }
        else {
            throw new InvalidGoalException("Could not add goal.");
        }
    }

    @Override
    public List<Goal> getGoalsForUser(int targetUID) {
        List<Goal> goalsForUser = new ArrayList<>();
        for (Goal goal : goalList) {
            if(goal.getUserID() == targetUID) {
                goalsForUser.add(goal);
            }
        }
        return goalsForUser;
    }

    @Override
    public boolean deleteGoal(int targetGID) {

        for (Goal goal : goalList) {
            if(goal.getGoalID() == targetGID) {
                goalList.remove(goal);
                return true;
            }
        }

        return false;
    }

    @Override
    public Goal getGoalByID(int targetGID) {

        for (Goal goal : goalList) {
            if(goal.getGoalID() == targetGID) {
                return goal;
            }
        }

        return null;
    }
}
