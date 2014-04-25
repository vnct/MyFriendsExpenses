package com.example.myfriendsexpenses.app.controler;

import com.example.myfriendsexpenses.app.model.CSVAction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lapie on 19/04/14.
 */
public class CSVParse {

    public void addPerson(Person person,CSVAction csvAction)
    {
        try{
      //  System.out.println("addPerson Try Person = " + person.get_name() + " - " + person.get_phoneNumber());
        csvAction.addPersonCSV(person);
        }
        catch (Exception e)
        {
            System.out.println("CSVParse -> addPerson -> Exception");
            e.printStackTrace();
        }

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
                        if (group.get_name().equals(group1.get_name()) == true) {
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

   public String getPhone(String personname,String groupname, List<Person> persons)
   {

       for (Person person : persons) {
           if (person.get_name().equals(personname) == true) {
               return person.get_phoneNumber();
           }
       }
    return "";
   }
   public List<Group> fillGroups(List<Person> persons,List<Group> groups)
   {
       // Création des groupes
       List<Group> groups1 = groups;
       try
       {
           for (Person person : persons) {
               for (Group group : groups1) {
                   if (group.get_name().equals(person.get_groupname()) == true) {
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
       return groups1;

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
    public String[] namePerson(List<Person> persons)
    {
        List<String> stringsnamelist = new ArrayList<String>();
        for (Person person : persons) {
            stringsnamelist.add(person.get_name());
        }
        String[] strings = new String[stringsnamelist.size()];
        for(int istrings=0;istrings<stringsnamelist.size();istrings++)
        {
            strings[istrings]=stringsnamelist.get(istrings);
        }
        return  strings;
    }
    public String[] nameGroup(List<Group> groups)
    {
        List<String> stringsgrouplist = new ArrayList<String>();
        for (Group group : groups) {
            stringsgrouplist.add(group.get_name());
        }
        String[] strings = new String[stringsgrouplist.size()];
        for(int istrings=0;istrings<stringsgrouplist.size();istrings++)
        {
            strings[istrings]=stringsgrouplist.get(istrings);
        }
        return  strings;
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
                        person.set_expenses(Float.parseFloat(strings1[2]));
                        persons.add(person);
                    } else {
                        persons.get(positionPerson).set_expenses(persons.get(positionPerson).get_expenses() + Float.parseFloat(strings1[2].trim()));
                    }
                } else {
                    person.set_expenses(Float.valueOf(strings1[2].trim()));

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
