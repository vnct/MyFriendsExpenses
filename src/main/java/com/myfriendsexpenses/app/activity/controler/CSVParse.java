package com.myfriendsexpenses.app.activity.controler;



import java.util.ArrayList;

import java.util.List;

@SuppressWarnings("unused")
public class CSVParse {

    public boolean existPersoninGroup(Person person,List<Person> persons)
    {
        for (Person person1 : persons) {
            if (person1.get_phoneNumber().equals(person.get_phoneNumber())) {
                if (person1.get_groupname().equals(person.get_groupname())) {
                    if (person1.get_name().equals(person.get_name())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean existExpenseinGroup(Person person,List<Person> persons)
    {
        for (Person person1 : persons) {


            if (person1.getExpenseList().get(0).getComments().equals(person.getExpenseList().get(0).getComments())) {
                if (person1.get_groupname().equals(person.get_groupname())) {
                    if (person1.get_name().equals(person.get_name())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public List<Person> parsePersonbyName(List<Person> persons, String nameperson)
    {
        List<Person> personList = new ArrayList<Person>();
        for (Person person : persons) {
            if (person.get_name().equals(nameperson)) {


                    personList.add(person);
                }


        }
        return personList;
    }
    public List<Person> parsePersonbyComments(List<Person> persons, String comments)
    {
        List<Person> personList = new ArrayList<Person>();
        for (Person person : persons) {
            if (person.getExpenseList().get(0).getComments().equals(comments)) {


                personList.add(person);
            }


        }
        return personList;
    }
    public List<Group> createGroups(List<String[]> strings)
    {
        List<Group> groups = new ArrayList<Group>();
        try
        {
            for (String[] strings1 : strings) {
                Group group = new Group();
                group.set_name(strings1[3].trim());
                group.setPersons(new ArrayList<Person>());
                if (groups.isEmpty()) {
                    groups.add(group);
                } else {
                    int correspondancegroup = 0;
                    for (Group group1 : groups) {
                        if (group.get_name().equals(group1.get_name())) {
                            correspondancegroup = 1;
                            break;
                        }
                    }
                    if (correspondancegroup == 0) {
                        groups.add(group);
                    }
                }


            }
        }
        catch (Exception e)
        {
            System.out.println("CSVParse -> createGroups -> Exception");
            e.printStackTrace();
        }
        return groups;
   }
    @SuppressWarnings("unused")
    public String getPhone(String personname,String groupname, List<Person> persons)
    {

       for (Person person : persons) {
           if (person.get_name().equals(personname)) {
               return person.get_phoneNumber();
           }
       }
    return "";
   }
    public List<Group> fillGroups(List<Person> persons,List<Group> groups)
    {
       // Création des groupes
      // List<Group> groups1 = groups;
       try
       {
           for (Person person : persons) {
               for (Group group : groups) {
                   if (group.get_name().equals(person.get_groupname())) {
                       group.addPerson(person);

                   }
               }
           }
       }
       catch (Exception e)
       {
           System.out.println("CSVParse -> fillGroups -> Exception");
           e.printStackTrace();
       }
       return groups;

   }

    public List<Person> fillPersonbyGroups(List<Group> groups)
    {
        List<Person> persons = new ArrayList<Person>();
        for (Group group : groups) {
            for (int ipersons = 0; ipersons < group.getPersons().size(); ipersons++) {
                persons.add(group.getPersons().get(ipersons));
            }
        }
        return persons;
    }
    public int personExist(Person person,List<Person> persons)
    {
        int position=-1;
        for(int ipersons=0;ipersons<persons.size();ipersons++)
        {
            if(persons.get(ipersons).get_name().equals(person.get_name()))
            {
                if(persons.get(ipersons).get_phoneNumber().equals(person.get_phoneNumber()))
                {
                    if(persons.get(ipersons).get_groupname().equals(person.get_groupname()))
                    {
                        position=ipersons;
                    }
                }
            }
        }
        return position;
    }
    public List<String> namePerson(List<Person> persons)
    {
        List<String> stringsnamelist = new ArrayList<String>();
        for (Person person : persons) {
            if(!stringsnamelist.contains(person.get_name()))
            stringsnamelist.add(person.get_name());
        }

        return  stringsnamelist;
    }
    public List<String> nameComment(List<Person> persons)
    {
        List<String> stringsCommentlist = new ArrayList<String>();
        for (Person person : persons) {
            if(!stringsCommentlist.contains(person.getExpenseList().get(0).getComments()))
            {
                if(!person.getExpenseList().get(0).getComments().equals(""))
                {

                        stringsCommentlist.add(person.getExpenseList().get(0).getComments());


                 }

            }
        }

        return  stringsCommentlist;
    }
    public List<String> phonePerson(List<Person> persons)
    {
        List<String> stringsphonelist = new ArrayList<String>();
        for (Person person : persons) {
            if(!stringsphonelist.contains(person.get_phoneNumber()))
                stringsphonelist.add(person.get_phoneNumber());
        }

        return  stringsphonelist;
    }
    public List<String> nameGroup(List<Group> groups)
    {
        List<String> stringsgrouplist = new ArrayList<String>();
        for (Group group : groups) {
            if(!stringsgrouplist.contains(group.get_name()))
            stringsgrouplist.add(group.get_name());
        }

        return  stringsgrouplist;
    }
    public List<Person> inverselistperson(List<Person> persons)
    {
        List<Person> personList = new ArrayList<Person>();
        for(int i=persons.size()-1; i>=0; i--)
        {
            personList.add(persons.get(i));
        }
        return personList;
    }
    public List<Person> parsePersonbyGroups(List<Person> persons,String namegroup,boolean parseExpenses)
    {
        List<Person> personList = new ArrayList<Person>();
        for (Person person : persons) {
            if (person.get_groupname().equals(namegroup)) {
                if(parseExpenses)
                {
                    if(person.getExpenseList().get(0).getExpenses()!=0)
                    {
                        personList.add(person);
                    }
                }
                else
                {
                    personList.add(person);
                }

            }
        }
        return personList;
    }
    public List<Person> fillPerson(List<String[]> strings,boolean concatenation)
    {
        // La concatenation permet d'additionner les memes dépenses pour une seule personne.
        List<Person> persons = new ArrayList<Person>();
        try
        {
            for (String[] strings1 : strings) {
                Person person = new Person();
                person.set_name(strings1[0].trim());
                person.set_phoneNumber(strings1[1].trim());
                person.set_groupname(strings1[3].trim());
                if (concatenation) {
                    int positionPerson = personExist(person, persons);
                    if (positionPerson < 0) {
                        person.addExpenseList(new Expense(Float.parseFloat(strings1[2]),strings1[4].trim()));
                      //  person.set_expenses(Float.parseFloat(strings1[2]));
                        persons.add(person);
                    } else {
                        persons.get(positionPerson).addExpenseList(new Expense(Float.parseFloat(strings1[2]),strings1[4].trim()));
                      //  persons.get(positionPerson).set_expenses(persons.get(positionPerson).get_expenses() + Float.parseFloat(strings1[2].trim()));
                    }
                } else {
                    person.addExpenseList(new Expense(Float.parseFloat(strings1[2]),strings1[4].trim()));
                  //  person.set_expenses(Float.valueOf(strings1[2].trim()));

                    //person.set_expenses(Float.parseFloat(strings1[2].trim()));
                    persons.add(person);
                }

            }
        }
        catch (Exception e)
        {
            System.out.println("CSVParse -> fillPerson -> Exception");
            e.printStackTrace();
        }


        return persons;
    }
}
