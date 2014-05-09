package com.example.myfriendsexpenses.app.controler;

@SuppressWarnings("unused")
public class Balance {
    private Person personput;
    private Person personget;
    private float value;

    public Balance() {
    }

    public Balance(Person _personput,Person _personget,float _value)
    {
        personput = _personput;
        personget = _personget;
        value = _value;

    }

    public Person getPersonget() {
        return personget;
    }


    public void setPersonget(Person personget) {
        this.personget = personget;
    }

    public Person getPersonput() {
        return personput;
    }


    public void setPersonput(Person personput) {
        this.personput = personput;
    }

    public float getValue() {
        return value;
    }


    public void setValue(float value) {
        this.value = value;
    }
}
