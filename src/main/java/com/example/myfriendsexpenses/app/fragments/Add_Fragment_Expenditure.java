package com.example.myfriendsexpenses.app.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.myfriendsexpenses.app.LastEntries;
import com.example.myfriendsexpenses.app.MainActivity;
import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Expense;
import com.example.myfriendsexpenses.app.controler.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lapie on 24/04/14.
 */
public class Add_Fragment_Expenditure extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String iposa = "section_number";
    private static final String myPerson = "section_number";

    private boolean ready_to_validate = false;
    private EditText editTextexpense = null, editTextphone = null, editTextcomment = null;
    private AutoCompleteTextView editTextname = null, editTextGroupname = null;
    private ListView listViewEntries = null;
    private static String[] namePerson = null, nameGroup = null;
    ArrayAdapter<String> adaptername = null, adaptergroup = null;
    ListAdapter simpleAdapter = null;
    List<HashMap<String, String>> listeHaspMapEntries = null;
    private String My_Position="";
    private String My_action="";
    Person person_old;
    Expense expense_old;
    private boolean bargs=false;
    MenuItem menu_add_update=null,menu_add_validate=null,menu_add_person=null;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add, menu);
       // System.out.println("onCreateOptionsMenu");
        menu_add_update = menu.findItem(R.id.menu_add_update);
        menu_add_validate = menu.findItem(R.id.menu_add_validate);
        menu_add_person = menu.findItem(R.id.menu_add_person);
        if (My_action.equals("1")) {
            menu_add_update.setVisible(true);
            menu_add_validate.setVisible(false);
            menu_add_person.setVisible(false);
        }
        else
        {
            menu_add_update.setVisible(false);
            menu_add_validate.setVisible(true);
            menu_add_person.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add_validate:
               OnclickButtonAdd();
                return true;
            case R.id.menu_add_update:
                OnclickButtonUpdate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        setHasOptionsMenu(true);


        initialization_widget(rootView);
        Bundle args = getArguments();

        if(args != null)
        {
            ArrayList<String> strings1 = args.getStringArrayList(myPerson);
            generate_widget(strings1);
            if(strings1.size()>0)
            {
                bargs=true;

            }
            else
            {
                bargs=false;

            }
        }
      //  System.out.println("Mon My_action " + My_action);
        //System.out.println("Mon My_Position " + My_Position);

        listeHaspMapEntries = new ArrayList<HashMap<String, String>>();
        simpleAdapter = new SimpleAdapter(getActivity(), listeHaspMapEntries, android.R.layout.simple_list_item_2, new String[]{"text1", "text2"}, new int[]{android.R.id.text1, android.R.id.text2});

        editTextname.setAdapter(adaptername);
        editTextGroupname.setAdapter(adaptergroup);
        editTextname.setOnItemClickListener(onItemClickListenertextname);





        return rootView;
    }



    private void CheckItem()
    {

        if((editTextname.getText().toString().trim().length()>0)&&(editTextGroupname.getText().toString().trim().length()>0)&&(editTextphone.getText().toString().trim().length()>0)&&(editTextexpense.getText().toString().trim().length()>0))
        {

            ready_to_validate=true;
        }
        else
        {
            ready_to_validate=false;
        }
        if(ready_to_validate)
        {
            menu_add_validate.setIcon(R.drawable.ic_action_navigation_accept);
        }
        else
        {
            menu_add_validate.setIcon(R.drawable.ic_action_content_new);
        }
    }


    private void generate_widget(ArrayList<String> strings1) {
        for(int i=0;i<strings1.size();i++) {
            switch (i) {
                case 0:
                    My_Position = strings1.get(i).toString();
                    break;
                case 1:
                    My_action = strings1.get(i).toString();
                    break;
                case 2:
                    editTextname.setText(strings1.get(i).toString());
                    break;
                case 3:
                    editTextphone.setText(strings1.get(i).toString());

                    break;
                case 4:
                    editTextGroupname.setText(strings1.get(i).toString());
                    break;
                case 5:
                    editTextexpense.setText(strings1.get(i).toString());
                    break;
                case 6:
                    editTextcomment.setText(strings1.get(i).toString());
                    break;
            }

        }
            if (My_action.equals("1")) {


                String Expenses = editTextexpense.getText().toString();
                editTextphone.setFocusable(false);
                float ExpensesValue = Float.valueOf(Expenses);
                expense_old = new Expense(ExpensesValue, editTextcomment.getText().toString().trim());
                person_old = new Person(editTextname.getText().toString().trim(), editTextphone.getText().toString().trim(), expense_old, editTextGroupname.getText().toString().trim());

            }
            if (My_action.equals("2")) {


                editTextexpense.getText().clear();

            }



    }

    public static Add_Fragment_Expenditure newInstance(int sectionNumber, ArrayList<String> Position) {
        Add_Fragment_Expenditure fragment = new Add_Fragment_Expenditure();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putStringArrayList(myPerson,Position);
        fragment.setArguments(args);

        return fragment;
    }


    public Add_Fragment_Expenditure() {
    }

    public void onResume() {
        super.onResume();
        MainActivity.fillDataForm();
        MainActivity.getDataForm().setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(), true));
        namePerson = MainActivity.getDataForm().getCsvParse().namePerson(MainActivity.getDataForm().getPersons());
        MainActivity.getDataForm().setGroups(MainActivity.getDataForm().getCsvParse().createGroups(MainActivity.getDataForm().getCsvAction().getCSV()));
        nameGroup = MainActivity.getDataForm().getCsvParse().nameGroup(MainActivity.getDataForm().getGroups());
        adaptername = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namePerson);
        adaptergroup = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, nameGroup);

    }



    private void OnclickButtonUpdate() {


            if (editTextexpense.getText().equals("") == true || editTextphone.getText().equals("") || editTextname.getText().equals("") || editTextGroupname.getText().equals("")) {
            } else {
                try {
                    String Expenses = editTextexpense.getText().toString();
                    float ExpensesValue = Float.valueOf(Expenses);
                    Expense expense_new = new Expense(ExpensesValue, editTextcomment.getText().toString().trim());
                    Person person_new = new Person(editTextname.getText().toString().trim(), editTextphone.getText().toString().trim(), expense_new, editTextGroupname.getText().toString().trim());
                   // MainActivity.getDataForm().getCsvAction().changePerson(person_old,expense_old,person_new, expense_new,Integer.parseInt(My_Position));
                    MainActivity.getDataForm().getCsvAction().changePerson(person_old,expense_old,person_new, expense_new);
                    HashMap<String, String> element = new HashMap<String, String>();
                    element.put("text1", "" + person_new.get_name() + " (" + person_new.get_groupname() + ")");
                    String expenditure = editTextexpense.getText().toString() + " €";
                    if (expense_new.getComments().length() > 0) {
                        expenditure = expenditure.concat(" for " + expense_new.getComments());
                    }
                    element.put("text2", "" + expenditure);
                    listeHaspMapEntries.add(element);
                    listViewEntries.setAdapter(simpleAdapter);
                    clear_EditText();
                    editTextphone.setFocusable(true);
                    My_action="0";
                  /*  Intent ExpenditureActivity = new Intent(getActivity(),LastEntries.class);
                    startActivity(ExpenditureActivity);*/
                    getActivity().finish();


                } catch (Exception e) {
                    System.out.println("Expenditure -> OnclickButtonUpdate -> Exception");
                    e.printStackTrace();
                    editTextexpense.getText().clear();
                    editTextexpense.setHint("10.0");
                }
            }

    };


    private AdapterView.OnItemClickListener onItemClickListenertextname = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(), editTextGroupname.getText().toString().trim(), MainActivity.getDataForm().getPersons()));
              }
        };
        private void OnclickButtonAdd() {

                if (editTextexpense.getText().equals("") == true || editTextphone.getText().equals("") || editTextname.getText().equals("") || editTextGroupname.getText().equals("")) {
                } else {
                    try {
                        String Expenses = editTextexpense.getText().toString();
                        float ExpensesValue = Float.valueOf(Expenses);
                        Expense expense = new Expense(ExpensesValue, editTextcomment.getText().toString().trim());
                        Person person = new Person(editTextname.getText().toString().trim(), editTextphone.getText().toString().trim(), expense, editTextGroupname.getText().toString().trim());
                        MainActivity.getDataForm().getCsvParse().addPerson(person, expense, MainActivity.getDataForm().getCsvAction());
                        HashMap<String, String> element = new HashMap<String, String>();
                        element.put("text1", "" + person.get_name() + " (" + person.get_groupname() + ")");
                        String expenditure = editTextexpense.getText().toString() + " €";
                        if (expense.getComments().length() > 0) {
                            expenditure = expenditure.concat(getString(R.string.add_expenditure_msg) + expense.getComments());
                        }
                        element.put("text2", "" + expenditure);
                        listeHaspMapEntries.add(element);
                        listViewEntries.setAdapter(simpleAdapter);
                        clear_EditText();
                        if(bargs)
                        {
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.add_person_toast_ok), Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    } catch (Exception e) {
                        System.out.println("Expenditure -> OnclickButtonAdd -> Exception");
                        e.printStackTrace();
                        editTextexpense.getText().clear();
                        editTextexpense.setHint("10.0");

                    }

            }
        };

        private void clear_EditText() {
            editTextexpense.getText().clear();
            editTextphone.getText().clear();
            editTextname.getText().clear();
            editTextGroupname.getText().clear();
            editTextcomment.getText().clear();
            MainActivity.getDataForm().setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(),true));
            namePerson = MainActivity.getDataForm().getCsvParse().namePerson(MainActivity.getDataForm().getPersons());
            MainActivity.getDataForm().setGroups(MainActivity.getDataForm().getCsvParse().createGroups(MainActivity.getDataForm().getCsvAction().getCSV()));
            nameGroup = MainActivity.getDataForm().getCsvParse().nameGroup(MainActivity.getDataForm().getGroups());
            adaptername = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namePerson);
            adaptergroup = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, nameGroup);
            editTextname.setAdapter(adaptername);
            editTextGroupname.setAdapter(adaptergroup);
            menu_add_validate.setIcon(R.drawable.ic_action_content_new);
        }

        private void initialization_widget(View rootView) {
            editTextexpense = (EditText) rootView.findViewById(R.id.addexpense);
            editTextphone = (EditText) rootView.findViewById(R.id.addphone);
            editTextname = (AutoCompleteTextView) rootView.findViewById(R.id.addname);
            editTextcomment = (EditText) rootView.findViewById(R.id.addcomment);
            editTextGroupname = (AutoCompleteTextView) rootView.findViewById(R.id.addgroupe);

            listViewEntries = (ListView) rootView.findViewById(R.id.addlistView);
            //     buttonaddValidate = (Button) rootView.findViewById(R.id.menu_add_validate);

        }




}
