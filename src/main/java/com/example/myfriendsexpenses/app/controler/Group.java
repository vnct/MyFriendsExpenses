package com.example.myfriendsexpenses.app.controler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 19/04/14.
 */
public class Group {
    private float _expensePerPerson;
    private float _totalExpenses;
    private String _name;
    private List<Person> persons;
    private List<Balance> balances;

    public void doBalance()
    {
        balances = new ArrayList<Balance>();
        List<Person> personspositive= new ArrayList<Person>();
        List<Person> personsnegative= new ArrayList<Person>();
        float positive = 0;
        float negative = 0;
        for (int iPerson=0; iPerson < persons.size(); iPerson++)
        {
            persons.get(iPerson).set_balance(persons.get(iPerson).get_payback());
            if(persons.get(iPerson).get_payback()<0)
            {
                personspositive.add(persons.get(iPerson));

                positive = positive + persons.get(iPerson).get_payback();
            }
            else
            {
                personsnegative.add(persons.get(iPerson));

                negative = negative + persons.get(iPerson).get_payback();
            }
        }
        for(int iPersonNegative=0;iPersonNegative<personsnegative.size();iPersonNegative++)
        {
            for(int iPersonPositive=0;iPersonPositive<personspositive.size();iPersonPositive++)
            {
                if(personsnegative.get(iPersonNegative).get_balance()>0)
                {
                    if(personspositive.get(iPersonPositive).get_balance()<0)
                    {

                        float giveto = personsnegative.get(iPersonNegative).get_balance();
                        float receivefrom = 0-personspositive.get(iPersonPositive).get_balance();
                       // System.out.println("Cette personne doit : " + giveto + " à " + personspositive.get(iPersonPositive).get_name() + " sachant qu'il peut recevoir " + receivefrom);
                        if(giveto>receivefrom) {
                      //      System.out.println("Cette personne donne donc : " + receivefrom + " à  " + personspositive.get(iPersonPositive).get_name());
                            personsnegative.get(iPersonNegative).set_balance(personsnegative.get(iPersonNegative).get_balance()-receivefrom);
                            personspositive.get(iPersonPositive).set_balance(personspositive.get(iPersonPositive).get_balance()+receivefrom);
                            balances.add(new Balance(personsnegative.get(iPersonNegative),personspositive.get(iPersonPositive),receivefrom));
                        }
                        else
                        {
                         //   System.out.println("Cette personne donne donc : " + giveto + " à  " + personspositive.get(iPersonPositive).get_name());
                            personsnegative.get(iPersonNegative).set_balance(personsnegative.get(iPersonNegative).get_balance()-giveto);
                            personspositive.get(iPersonPositive).set_balance(personspositive.get(iPersonPositive).get_balance()+giveto);
                            balances.add(new Balance(personsnegative.get(iPersonNegative),personspositive.get(iPersonPositive),giveto));
                        }
                        //System.out.println("Il me reste à rembourser " + personsnegative.get(iPersonNegative).get_balance());
                        //System.out.println("Suis je a l'équilibre " + personspositive.get(iPersonPositive).get_balance());
                      /* if(diff>0)
                        {
                            System.out.println("Cette personne peut recevoir seulement  " + toto + " - des " + personsnegative.get(iPersonNegative).get_payback() + " possibles " );
                            /*personspositive.get(iPersonPositive).set_payback(personspositive.get(iPersonPositive).get_payback() - diff);
                            personsnegative.get(iPersonNegative).set_payback(personsnegative.get(iPersonNegative).get_payback() + diff);
                            balances.add(new Balance(personsnegative.get(iPersonNegative),personspositive.get(iPersonPositive),diff));

                        }
                        else
                        {
                            System.out.println("Cette personne va tout recevoir cad " + toto + " - des " + personsnegative.get(iPersonNegative).get_payback() + " possibles " );
                           /* personspositive.get(iPersonPositive).set_payback(personspositive.get(iPersonPositive).get_payback() - personspositive.get(iPersonPositive).get_payback());
                            personsnegative.get(iPersonNegative).set_payback(personsnegative.get(iPersonNegative).get_payback() + personspositive.get(iPersonPositive).get_payback());
                            balances.add(new Balance(personsnegative.get(iPersonNegative),personspositive.get(iPersonPositive),diff));
                        }*/
                    }
                }
           }
        }
        for(int ibalances=0;ibalances<balances.size();ibalances++)
        {
            System.out.println(balances.get(ibalances).getPersonput().get_name() + " Envoie : " + balances.get(ibalances).getValue());
            System.out.println("Recoit  " + balances.get(ibalances).getPersonget().get_name()+ " : " + balances.get(ibalances).getValue());
        }

    }
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

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances1) {
        this.balances = balances1;
    }
}
