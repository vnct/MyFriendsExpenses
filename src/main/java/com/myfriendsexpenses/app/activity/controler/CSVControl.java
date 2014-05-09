package com.myfriendsexpenses.app.activity.controler;

import com.myfriendsexpenses.app.activity.model.CSVAction;

import java.util.List;


public class CSVControl {

    CSVAction csvAction = new CSVAction();

    public void createCSVFile()
    {
        csvAction.createFile();
    }
    public List<String[]> getdataCSV()
    {
        return csvAction.getCSV();
    }
    public void removeGroup(List<Person> persons)
    {
        try
        {
            List<String[]> strings1 = csvAction.getCSV();
            for (Person person : persons) {
                String[] strings = new String[]{person.get_name(), person.get_phoneNumber(), Float.toString(person.getExpenseList().get(0).getExpenses()), person.get_groupname(), person.getExpenseList().get(0).getComments()};

                for (int istrings1 = 0; istrings1 < strings1.size(); istrings1++) {
                    if ((strings1.get(istrings1)[0].equals(strings[0])) && (strings1.get(istrings1)[1].equals(strings[1])) && (Float.toString(Float.parseFloat(strings1.get(istrings1)[2])).equals(strings[2])) && (strings1.get(istrings1)[3].equals(strings[3])) && (strings1.get(istrings1)[4].equals(strings[4]))) {
                        strings1.remove(istrings1);
                    }
                }
            }
            csvAction.rewritefile(strings1);
        }
        catch (Exception e)
        {
            System.out.println("CSVControl -> removeGroup -> Exception");
        }

    }
    public void removePerson(Person person,Expense expense)
    {
        try
        {
            String[] strings = new String[]{person.get_name(),person.get_phoneNumber(),Float.toString(expense.getExpenses()),person.get_groupname(),expense.getComments()};
            List<String[]> strings1 = csvAction.getCSV();
            for(int istrings1=0;istrings1<strings1.size();istrings1++)
            {
                if((strings1.get(istrings1)[0].equals(strings[0]))&&(strings1.get(istrings1)[1].equals(strings[1]))&&(Float.toString(Float.parseFloat(strings1.get(istrings1)[2])).equals(strings[2]))&&(strings1.get(istrings1)[3].equals(strings[3]))&&(strings1.get(istrings1)[4].equals(strings[4])))
                {


                    strings1.remove(istrings1);


                }
            }
            csvAction.rewritefile(strings1);
        }
        catch (Exception e)
        {
            System.out.println("CSVControl -> removePerson -> Exception");
        }

    }
    public void changeGroup(List<Person> persons,String new_name)
    {
        try {
            List<String[]> strings1 = csvAction.getCSV();
            for (Person person : persons) {
                String[] strings = new String[]{person.get_name(), person.get_phoneNumber(), Float.toString(person.getExpenseList().get(0).getExpenses()), person.get_groupname(), person.getExpenseList().get(0).getComments()};
                for (String[] aStrings1 : strings1) {
                    if ((aStrings1[0].equals(strings[0])) && (aStrings1[1].equals(strings[1])) && (Float.toString(Float.parseFloat(aStrings1[2])).equals(strings[2])) && (aStrings1[3].equals(strings[3])) && (aStrings1[4].equals(strings[4]))) {
                        aStrings1[0] = person.get_name();
                        aStrings1[1] = person.get_phoneNumber();
                        aStrings1[2] = Float.toString(person.getExpenseList().get(0).getExpenses());
                        aStrings1[3] = new_name;
                        aStrings1[4] = person.getExpenseList().get(0).getComments();
                    }
                }
            }
            csvAction.rewritefile(strings1);
        }
        catch (Exception e)
        {
            System.out.println("CSVAction -> changeGroup -> Exception");
        }
    }
    public void changePerson(Person person_old,Expense expense_old,Person person_new,Expense expense_new)
    {
        try
        {
            // String[] strings = new String[]{person.get_name(),person.get_phoneNumber(),Float.toString(expense.getExpenses()),person.get_groupname(),expense.getComments()};

            String[] strings = new String[]{person_old.get_name(),person_old.get_phoneNumber(),Float.toString(expense_old.getExpenses()),person_old.get_groupname(),expense_old.getComments()};
            List<String[]> strings1 = csvAction.getCSV();
            for (String[] aStrings1 : strings1) {
                if ((aStrings1[0].equals(strings[0])) && (aStrings1[1].equals(strings[1])) && (Float.toString(Float.parseFloat(aStrings1[2])).equals(strings[2])) && (aStrings1[3].equals(strings[3])) && (aStrings1[4].equals(strings[4]))) {


                    aStrings1[0] = person_new.get_name();
                    aStrings1[1] = person_new.get_phoneNumber();
                    aStrings1[2] = Float.toString(expense_new.getExpenses());
                    aStrings1[3] = person_new.get_groupname();
                    aStrings1[4] = expense_new.getComments();


                }
            }
            csvAction.rewritefile(strings1);

        }
        catch (Exception e)
        {
            System.out.println("CSVAction -> removePerson -> Exception");
        }

    }
    public void addPersonCSV(Person person,Expense expense)
    {
        try {

            String[] strings = new String[]{person.get_name(),person.get_phoneNumber(),Float.toString(expense.getExpenses()),person.get_groupname(),expense.getComments()};
            csvAction.appendfile(strings);


        } catch (Exception e) {
            System.out.println("CSVAction -> addPersonCSV -> IOException");
            e.printStackTrace();
        }

    }
}
