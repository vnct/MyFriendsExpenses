package com.myfriendsexpenses.app.activity.controler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 19/04/14
 */
@SuppressWarnings("unused")
public class Group {
    private float _expensePerPerson;
    private float _totalExpenses;
    private String _name;
    private List<Person> persons;
    private List<Balance> balances;

    public List<Balance> doBalance()
    {
        balances = new ArrayList<Balance>();
        List<Person> personspositive= new ArrayList<Person>();
        List<Person> personsnegative= new ArrayList<Person>();
        float positive = 0;
        float negative = 0;
        for (Person person : persons) {
            person.set_balance(person.get_payback());
            if (person.get_payback() > 0) {
                personspositive.add(person);

                positive = positive + person.get_payback();
            } else {
                personsnegative.add(person);

                negative = negative + person.get_payback();
            }
        }
        for (Person aPersonsnegative : personsnegative) {
            for (Person aPersonspositive : personspositive) {
                if (aPersonsnegative.get_balance() < 0) {
                    if (aPersonspositive.get_balance() > 0) {

                        float giveto = 0 - aPersonsnegative.get_balance();
                        float receivefrom = aPersonspositive.get_balance();
                        //System.out.println("Cette personne doit : " + giveto + " dont à " + personspositive.get(iPersonPositive).get_name() + " sachant qu'il peut recevoir " + receivefrom);
                        if (giveto > receivefrom) {
                            //  System.out.println("Cette personne donne donc : " + receivefrom + " à  " + personspositive.get(iPersonPositive).get_name());
                            aPersonsnegative.set_balance(aPersonsnegative.get_balance() + receivefrom);
                            aPersonspositive.set_balance(aPersonspositive.get_balance() - receivefrom);
                            balances.add(new Balance(aPersonsnegative, aPersonspositive, receivefrom));
                        } else {
                            //System.out.println("Cette personne donne donc : " + giveto + " à  " + personspositive.get(iPersonPositive).get_name());
                            aPersonsnegative.set_balance(aPersonsnegative.get_balance() + giveto);
                            aPersonspositive.set_balance(aPersonspositive.get_balance() - giveto);
                            balances.add(new Balance(aPersonsnegative, aPersonspositive, giveto));
                        }
                        //System.out.println("Il me reste à rembourser " + personsnegative.get(iPersonNegative).get_balance());
                        //System.out.println("Suis je a l'équilibre " + personspositive.get(iPersonPositive).get_balance());

                    }
                }
            }
        }
      /*  for(int ibalances=0;ibalances<balances.size();ibalances++)
        {
        //    System.out.println(balances.get(ibalances).getPersonput().get_name() + " Envoie : " + balances.get(ibalances).getValue());
          //  System.out.println("Recoit  " + balances.get(ibalances).getPersonget().get_name()+ " : " + balances.get(ibalances).getValue());
        }*/
        return balances;

    }
    public float totalExpenses()
    {
        _totalExpenses = 0;
        for (Person person : persons) {
            _totalExpenses += person.get_expenses();

        }
        return _totalExpenses;
    }
    public float expensesPerPerson()
    {
        return _totalExpenses / persons.size();
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
