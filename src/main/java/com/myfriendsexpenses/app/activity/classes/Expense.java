package com.myfriendsexpenses.app.activity.classes;

/**
 * Created by lapie on 25/04/14
 */
@SuppressWarnings("unused")
public class Expense {

    private float expenses;
    private String comments;

    public Expense(float expense,String comment)
    {
        expenses=expense;
        comments=comment;
    }
    public float getExpenses() {
        return expenses;
    }

    public void setExpenses(float expenses) {
        this.expenses = expenses;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
