package com.example.myfriendsexpenses.app.controler;

/**
 * Created by lapie on 25/04/14.
 */
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
