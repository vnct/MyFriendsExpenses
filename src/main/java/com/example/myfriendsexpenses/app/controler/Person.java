package com.example.myfriendsexpenses.app.controler;

/**
 * Created by lapie on 19/04/14.
 */
public class Person {
    private String _name;
    private String _phoneNumber;
    private float _expenses;
    private float _payback;
    private float _balance;
    private Group _group;
    private String _groupname;

    public Person()
    {

    }
    public Person(String name,String phone,Float expenses,String groupname)
    {
        _name = name;
        _expenses = expenses;
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

    public void set_expenses(float _expenses) {
        this._expenses = _expenses;
    }

    public float get_payback() {
        return _payback;
    }

    public void set_payback(float _payback) {
        this._payback = _payback;
    }

    public Group get_group() {
        return _group;
    }

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
}
