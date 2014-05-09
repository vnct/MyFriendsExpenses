package com.example.myfriendsexpenses.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.myfriendsexpenses.app.activity.MainActivity;
import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Expense;
import com.example.myfriendsexpenses.app.controler.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lapie on 24/04/14
 */
@SuppressWarnings(value = {"unused", "EqualsBetweenInconvertibleTypes", "UnusedAssignment", "ConstantConditions", "FieldCanBeLocal", "StatementWithEmptyBody"})

public class Add_Fragment_Expenditure extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String iposa = "section_number";
    private static final String myPerson = "section_number";

    private boolean ready_to_validate = false;
    private EditText editTextexpense = null, editTextphone = null, editTextcomment = null;
    private AutoCompleteTextView editTextname = null, editTextGroupname = null;
    private ListView listViewEntries = null;
    private static List<String> namePerson = new ArrayList<String>(),nameGroup=new ArrayList<String>(),namePhone=new ArrayList<String>(),nameComments=new ArrayList<String>();
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
            bargs = strings1.size() > 0;
        }
        //  System.out.println("Mon My_action " + My_action);
        //System.out.println("Mon My_Position " + My_Position);

        listeHaspMapEntries = new ArrayList<HashMap<String, String>>();
        simpleAdapter = new SimpleAdapter(getActivity(), listeHaspMapEntries, android.R.layout.simple_list_item_2, new String[]{"text1", "text2"}, new int[]{android.R.id.text1, android.R.id.text2});


        editTextname.setOnItemClickListener(onItemClickListenertextname);

        editTextname.setOnItemClickListener(onItemClickListenertextname);
        editTextname.setOnFocusChangeListener(onFocusChangetextname);
        editTextname.setOnClickListener(onClickListenertextname);
        editTextphone.setOnFocusChangeListener(onFocusChangetextphone);
        editTextphone.setOnClickListener(onClickListenertextphone);


        editTextcomment.setOnClickListener(onItemClickListenertextcomment);
        editTextcomment.setOnFocusChangeListener(onFocusChangetextcomment);


        return rootView;
    }

    private View.OnClickListener onItemClickListenertextcomment = new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
            Testingtextcomment();
        }
    };
    private View.OnFocusChangeListener onFocusChangetextcomment = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            Testingtextcomment();
        }
    };

    private View.OnClickListener onClickListenertextname = new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
            editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(), editTextGroupname.getText().toString().trim(), MainActivity.getDataForm().getPersons()));
            Testingtextname();
        }
    };
    private View.OnClickListener onClickListenertextphone = new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
            Testingtextphone();
        }
    };
    private View.OnFocusChangeListener onFocusChangetextphone = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            Testingtextphone();
        }
    };
    public View.OnFocusChangeListener onFocusChangetextname = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View view, boolean b) {
            editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(), editTextGroupname.getText().toString().trim(), MainActivity.getDataForm().getPersons()));
            Testingtextname();
        }
    };
    private AdapterView.OnItemClickListener onItemClickListenertextname = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(), editTextGroupname.getText().toString().trim(), MainActivity.getDataForm().getPersons()));
            Testingtextname();
        }
    };

    private void Testingtextcomment()
    {
        // if (My_action.equals("1")==false) {


        List<Person> personList = new ArrayList<Person>();
        personList = MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(MainActivity.getDataForm().getPersons(), editTextGroupname.getText().toString().trim(), false);
        personList = MainActivity.getDataForm().getCsvParse().parsePersonbyName(personList, editTextname.getText().toString().trim());
        nameComments = MainActivity.getDataForm().getCsvParse().nameComment(personList);
        if (My_action.equals("1"))
        {
            nameComments.remove(nameComments.indexOf(person_old.getExpenseList().get(0).getComments()));
        }
        if (nameComments.contains(editTextcomment.getText().toString().trim())) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.add_expenditure_toast_fail), Toast.LENGTH_SHORT).show();
            String new_name_comment = editTextcomment.getText().toString().trim();
            int i = 1;
            while (nameComments.contains(editTextcomment.getText().toString().trim())) {
                editTextcomment.setText(new_name_comment + " " + i);
                i++;
                if (i > 20) {
                    break;
                }
            }
        }

    }
    private void Testingtextphone()
    {
        if(namePhone.contains(editTextphone.getText().toString().trim()))
        {
            if(editTextphone.isEnabled())
            {
                // J'existe deja donc j'efface
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.add_person_toast_phone), Toast.LENGTH_SHORT).show();
                editTextphone.getText().clear();
            }
            else
            {
                // J'existe deja mais c'est normal
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.add_person_toast_phone_ok), Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void Testingtextname()
    {
        if(namePerson.contains(editTextname.getText().toString().trim()))
        {
            // J'existe donc on touche pas à mon numéro
            editTextphone.setEnabled(false);
        }
        else
        {
            // J'existe pas donc on touche mon numéro si il existe
            editTextphone.setEnabled(true);
            if(namePhone.contains(editTextphone.getText().toString().trim())) {
                editTextphone.getText().clear();
            }
        }
    }

    private void generate_widget(ArrayList<String> strings1) {
        for(int i=0;i<strings1.size();i++) {
            switch (i) {
                case 0:
                    My_Position = strings1.get(i);
                    break;
                case 1:
                    My_action = strings1.get(i);
                    break;
                case 2:
                    editTextname.setText(strings1.get(i));
                    break;
                case 3:
                    editTextphone.setText(strings1.get(i));

                    break;
                case 4:
                    editTextGroupname.setText(strings1.get(i));
                    break;
                case 5:
                    editTextexpense.setText(strings1.get(i));
                    break;
                case 6:
                    editTextcomment.setText(strings1.get(i));
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
        updateDataform();
        adaptername = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namePerson);
        adaptergroup = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, nameGroup);
        editTextname.setAdapter(adaptername);
        editTextGroupname.setAdapter(adaptergroup);

    }

    private void OnclickButtonUpdate() {


        if (editTextexpense.getText().equals("") || editTextphone.getText().equals("") || editTextname.getText().equals("") || editTextGroupname.getText().equals("")) {
        } else {
            try {
                String Expenses = editTextexpense.getText().toString();
                float ExpensesValue = Float.valueOf(Expenses);
                Testingtextcomment();
                Expense expense_new = new Expense(ExpensesValue, editTextcomment.getText().toString().trim());
                Person person_new = new Person(editTextname.getText().toString().trim(), editTextphone.getText().toString().trim(), expense_new, editTextGroupname.getText().toString().trim());

          //      MainActivity.getDataForm().getCsvAction().changePerson(person_old,expense_old,person_new, expense_new);
                MainActivity.getDataForm().getCsvControl().changePerson(person_old,expense_old,person_new, expense_new);
                HashMap<String, String> element = new HashMap<String, String>();
                element.put("text1", "" + person_new.get_name() + " (" + person_new.get_groupname() + ")");
                String expenditure = editTextexpense.getText().toString() + " €";
                if (expense_new.getComments().length() > 0) {
                    expenditure = expenditure.concat(" for " + expense_new.getComments());
                }
                element.put("text2", "" + expenditure);
                listeHaspMapEntries.add(element);
                listViewEntries.setAdapter(simpleAdapter);
                clear_EditText(true);
                editTextphone.setFocusable(true);
                My_action="0";
                getActivity().finish();


            } catch (Exception e) {
                System.out.println("Expenditure -> OnclickButtonUpdate -> Exception");
                e.printStackTrace();
                editTextexpense.getText().clear();
                editTextexpense.setHint("10.0");
            }
        }

    }


    private void OnclickButtonAdd() {

        if (editTextexpense.getText().equals("") || editTextphone.getText().equals("") || editTextname.getText().equals("") || editTextGroupname.getText().equals("")) {
        } else {
            try {
                String Expenses = editTextexpense.getText().toString();
                float ExpensesValue = Float.valueOf(Expenses);
                Expense expense = new Expense(ExpensesValue, editTextcomment.getText().toString().trim());
                Person person = new Person(editTextname.getText().toString().trim(), editTextphone.getText().toString().trim(), expense, editTextGroupname.getText().toString().trim());
                boolean alreadyexist = MainActivity.getDataForm().getCsvParse().existExpenseinGroup(person, MainActivity.getDataForm().getPersons());
                if (!alreadyexist)
                {
            //        MainActivity.getDataForm().getCsvParse().addPerson(person, expense, MainActivity.getDataForm().getCsvAction());
                    MainActivity.getDataForm().getCsvControl().addPersonCSV(person, expense);
                    HashMap<String, String> element = new HashMap<String, String>();
                    element.put("text1", "" + person.get_name() + " (" + person.get_groupname() + ")");
                    String expenditure = editTextexpense.getText().toString() + " €";
                    if (expense.getComments().length() > 0) {
                        expenditure = expenditure.concat(getString(R.string.add_expenditure_msg) + expense.getComments());
                    }
                    element.put("text2", "" + expenditure);
                    listeHaspMapEntries.add(element);
                    listViewEntries.setAdapter(simpleAdapter);
                    clear_EditText(true);
                    if (bargs) {
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.add_expenditure_toast_ok), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }
                }
                else
                {
                    clear_EditText(false);
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.add_expenditure_toast_fail), Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                System.out.println("Expenditure -> OnclickButtonAdd -> Exception");
                e.printStackTrace();
                editTextexpense.getText().clear();
                editTextexpense.setHint("10.0");

            }

        }
    }

    private void clear_EditText(boolean all) {

        editTextcomment.getText().clear();
        if(all)
        {
            editTextexpense.getText().clear();
            editTextphone.getText().clear();
            editTextname.getText().clear();
            editTextGroupname.getText().clear();

        }

        updateDataform();
        adaptername = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namePerson);
        adaptergroup = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, nameGroup);
        editTextname.setAdapter(adaptername);
        editTextGroupname.setAdapter(adaptergroup);
        menu_add_validate.setIcon(R.drawable.ic_action_content_new);
    }

    private void updateDataform()
    {
        MainActivity.fillDataForm(false);
     //   MainActivity.getDataForm().setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(), false));
        namePerson = MainActivity.getDataForm().getCsvParse().namePerson(MainActivity.getDataForm().getPersons());
       // MainActivity.getDataForm().setGroups(MainActivity.getDataForm().getCsvParse().createGroups(MainActivity.getDataForm().getCsvAction().getCSV()));
        nameGroup = MainActivity.getDataForm().getCsvParse().nameGroup(MainActivity.getDataForm().getGroups());
        namePhone = MainActivity.getDataForm().getCsvParse().phonePerson(MainActivity.getDataForm().getPersons());

        nameComments = MainActivity.getDataForm().getCsvParse().nameComment(MainActivity.getDataForm().getPersons());
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
