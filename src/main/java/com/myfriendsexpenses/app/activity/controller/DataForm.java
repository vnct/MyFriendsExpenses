package com.myfriendsexpenses.app.activity.controller;

import com.myfriendsexpenses.app.activity.classes.Group;
import com.myfriendsexpenses.app.activity.classes.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 20/04/14
 */
@SuppressWarnings("unused")
public class DataForm {
    private CSVControl csvControl = new CSVControl();
    private CSVParse csvParse = new CSVParse();
    private List<String[]> strings = new ArrayList<String[]>();
    private List<Group> groups = new ArrayList<Group>();
    private List<Person> persons = new ArrayList<Person>();
    private List<String> groupname = new ArrayList<String>();

    public void fillgroupname()
    {
        groupname = new ArrayList<String>();
        groupname.add("Everybody");
        for (Group group : groups) {
            groupname.add(group.get_name());
        }
    }


    public List<String[]> getStrings() {
        return strings;
    }

    public void setStrings(List<String[]> strings) {
        this.strings = strings;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<String> getGroupname() {
        return groupname;
    }


    public void setGroupname(List<String> groupname) {
        this.groupname = groupname;
    }

    public CSVParse getCsvParse() {
        return csvParse;
    }

    public void setCsvParse(CSVParse csvParse) {
        this.csvParse = csvParse;
    }

    public CSVControl getCsvControl() {
        return csvControl;
    }

    public void setCsvControl(CSVControl csvControl) {
        this.csvControl = csvControl;
    }
}
