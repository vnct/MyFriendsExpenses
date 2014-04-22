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
            for (int istrings=0;istrings<strings.size();istrings++)
            {
                String[] strings1 = strings.get(istrings);
                Group group = new Group();
                group.set_name(strings1[3].trim());
                group.setPersons(new ArrayList<Person>());
                if(groups.isEmpty()==true)
                {
                    groups.add(group);
                }
                else
                {
                    int correspondancegroup=0;
                    for(int igroups=0;igroups<groups.size();igroups++)
                    {
                        if(group.get_name().equals(groups.get(igroups).get_name())==true)
                        {
                            correspondancegroup=1;
                            break;
                        }
                    }
                    if(correspondancegroup==0)
                    {
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

   public String getPhone(String personname,String groupname, List<Group> groups)
   {
       for(int igroups=0;igroups<groups.size();igroups++)
       {
           if(groups.get(igroups).get_name().equals(groupname)==true)
           {
               for(int ipersons=0;ipersons<groups.get(igroups).getPersons().size();ipersons++)
               {
                   if(groups.get(igroups).getPersons().get(ipersons).get_name().equals(personname)==true)
                   {
                    return groups.get(igroups).getPersons().get(ipersons).get_phoneNumber();
                   }
               }
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
           for (int ipersons=0;ipersons<persons.size();ipersons++)
           {
               Person person = persons.get(ipersons);
               for(int igroups=0;igroups<groups1.size();igroups++)
               {
                   Group group = groups1.get(igroups);
                   if(group.get_name().equals(person.get_groupname())==true)
                   {
                       groups1.get(igroups).addPerson(person);

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
        for(int igroups=0;igroups<groups.size();igroups++)
        {
            for(int ipersons=0;ipersons<groups.get(igroups).getPersons().size();ipersons++)
            {
                persons.add(groups.get(igroups).getPersons().get(ipersons));
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
        for(int ipersons=0;ipersons<persons.size();ipersons++)
        {
            stringsnamelist.add(persons.get(ipersons).get_name());
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
        for(int igroups=0;igroups<groups.size();igroups++)
        {
            stringsgrouplist.add(groups.get(igroups).get_name());
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
            for (int istrings=0;istrings<strings.size();istrings++)
            {
                String[] strings1 = strings.get(istrings);
                Person person=new Person();
                person.set_name(strings1[0].trim());
                person.set_phoneNumber(strings1[1].trim());
                person.set_groupname(strings1[3].trim());
                if(concatenation==true)
                {
                    int positionPerson=personExist(person,persons);
                    if(positionPerson<0)
                    {
                        person.set_expenses(Float.parseFloat(strings1[2]));
                        persons.add(person);
                    }
                    else
                    {
                        persons.get(positionPerson).set_expenses(persons.get(positionPerson).get_expenses()+Float.parseFloat(strings1[2].trim()));
                    }
                }
                else
                {
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
