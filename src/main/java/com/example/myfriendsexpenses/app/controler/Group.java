package com.example.myfriendsexpenses.app.controler;

import java.util.List;

/**
 * Created by lapie on 19/04/14.
 */
public class Group {
    private float _expensePerPerson;
    private float _totalExpenses;
    private String _name;
    private List<Person> persons;

    public float totalExpenses()
    {
        _totalExpenses = 0;
        for (int iPerson=0; iPerson < persons.size(); iPerson++)
        {
            _totalExpenses += persons.get(iPerson).get_expenses();

        }
        return _totalExpenses;
    }
    public float expensesPerPerson()
    {
        float aExpense = _totalExpenses / persons.size();
        return aExpense;
    }

    public float get_expensePerPerson() {
        return _expensePerPerson;
    }

    public void set_expensePerPerson(float _expensePerPerson) {
        this._expensePerPerson = _expensePerPerson;
    }

    public float get_totalExpenses() {
        return _totalExpenses;
    }

    public void set_totalExpenses(float _totalExpenses) {
        this._totalExpenses = _totalExpenses;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
    }

}
