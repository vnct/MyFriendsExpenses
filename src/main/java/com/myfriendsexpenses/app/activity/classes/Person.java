package com.myfriendsexpenses.app.activity.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 19/04/14
 */
public class Person implements Parcelable {
    private String _name;
    private String _phoneNumber;
    private float _expenses;
    private float _payback;
    private float _balance;
    private List<Expense> expenseList;
    private Group _group;
    private String _groupname;

    public Person()
    {
        expenseList = new ArrayList<Expense>();
    }
    public Person(String name,String phone,Expense expenses,String groupname)
    {
        _name = name;
        expenseList = new ArrayList<Expense>();
        expenseList.add(expenses);

        _phoneNumber = phone;
        _groupname = groupname;
    }

    public void operatePayback(float iExpensesPerPerson)
    {
       _payback = _expenses - iExpensesPerPerson;
    }
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public void set_phoneNumber(String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

    public float get_expenses() {
        return _expenses;
    }

   /* public void set_expenses(float _expenses) {
        this._expenses = _expenses;
    }*/

    public float get_payback() {
        return _payback;
    }
    @SuppressWarnings("unused")
    public void set_payback(float _payback) {
        this._payback = _payback;
    }
    @SuppressWarnings("unused")
    public Group get_group() {
        return _group;
    }
    @SuppressWarnings("unused")
    public void set_group(Group _group) {
        this._group = _group;
    }

    public String get_groupname() {
        return _groupname;
    }

    public void set_groupname(String _groupname) {
        this._groupname = _groupname;
    }

    public float get_balance() {
        return _balance;
    }

    public void set_balance(float _balance) {
        this._balance = _balance;
    }

    public void addExpenseList(Expense expense)
    {
        this.expenseList.add(expense);
        _expenses = _expenses + expense.getExpenses();
    }
    public List<Expense> getExpenseList() {
        return expenseList;
    }
    @SuppressWarnings("unused")
    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_name);
        parcel.writeString(_phoneNumber);
        parcel.writeString(_groupname);
        if(expenseList.size()>0) {
            parcel.writeFloat(expenseList.get(0).getExpenses());
            parcel.writeString(expenseList.get(0).getComments());
        }

    }
}
