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
 * Created by lapie on 24/04/14.
 */
public class Add_Fragment_Person extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText editTextphone = null;
    private AutoCompleteTextView editTextname = null,editTextGroupname = null;
    private ListView listViewEntries = null;
    private static List<String> namePerson = new ArrayList<String>(),nameGroup=new ArrayList<String>(),namePhone=new ArrayList<String>();
    ArrayAdapter<String> adaptername = null,adaptergroup = null;
    ListAdapter simpleAdapter = null;
    List<HashMap<String, String>> listeHaspMapEntries = null;
    MenuItem menu_add_update=null,menu_add_validate=null,menu_add_person=null;
    private static final String myPerson = "section_number";
    private boolean bargs=false;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // set the menu
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_add_person, container, false);
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
        listeHaspMapEntries = new ArrayList<HashMap<String, String>>();
        simpleAdapter = new SimpleAdapter(getActivity(),listeHaspMapEntries,android.R.layout.simple_list_item_2,new String[] {"text1", "text2"},new int[] {android.R.id.text1, android.R.id.text2 });
        editTextname.setOnItemClickListener(onItemClickListenertextname);
        editTextname.setOnFocusChangeListener(onFocusChangetextname);
        editTextname.setOnClickListener(onClickListenertextname);
        editTextphone.setOnFocusChangeListener(onFocusChangetextphone);
        editTextphone.setOnClickListener(onClickListenertextphone);
        return rootView;
    }

    private void updateDataform()
    {
        MainActivity.fillDataForm(false);
     //   MainActivity.getDataForm().setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(), false));
        namePerson = MainActivity.getDataForm().getCsvParse().namePerson(MainActivity.getDataForm().getPersons());
       // MainActivity.getDataForm().setGroups(MainActivity.getDataForm().getCsvParse().createGroups(MainActivity.getDataForm().getStrings()));
        nameGroup = MainActivity.getDataForm().getCsvParse().nameGroup(MainActivity.getDataForm().getGroups());
        namePhone = MainActivity.getDataForm().getCsvParse().phonePerson(MainActivity.getDataForm().getPersons());
   /*     MainActivity.getDataForm().setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(), false));
        namePerson = MainActivity.getDataForm().getCsvParse().namePerson(MainActivity.getDataForm().getPersons());
        MainActivity.getDataForm().setGroups(MainActivity.getDataForm().getCsvParse().createGroups(MainActivity.getDataForm().getCsvAction().getCSV()));
        nameGroup = MainActivity.getDataForm().getCsvParse().nameGroup(MainActivity.getDataForm().getGroups());
        namePhone = MainActivity.getDataForm().getCsvParse().phonePerson(MainActivity.getDataForm().getPersons());*/
    }
    public void onResume() {
        super.onResume();

        updateDataform();

        adaptername = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namePerson);
        adaptergroup = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, nameGroup);
        editTextname.setAdapter(adaptername);
        editTextGroupname.setAdapter(adaptergroup);

    }

    private View.OnClickListener onClickListenertextname = new View.OnClickListener()
    {
        @Override
        public void onClick(View view) {
            editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(),editTextGroupname.getText().toString().trim(),MainActivity.getDataForm().getPersons()));
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
            editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(),editTextGroupname.getText().toString().trim(),MainActivity.getDataForm().getPersons()));
            Testingtextname();
        }
    };
    private AdapterView.OnItemClickListener onItemClickListenertextname = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(),editTextGroupname.getText().toString().trim(),MainActivity.getDataForm().getPersons()));
            Testingtextname();
        }
    };

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
                    editTextGroupname.setText(strings1.get(i).toString());
                    break;

            }

        }
    }

    public static Add_Fragment_Person newInstance(int sectionNumber, ArrayList<String> strings_person) {
        Add_Fragment_Person fragment = new Add_Fragment_Person();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putStringArrayList(myPerson, strings_person);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add, menu);
        menu_add_update = menu.findItem(R.id.menu_add_update);
        menu_add_validate = menu.findItem(R.id.menu_add_validate);
        menu_add_person = menu.findItem(R.id.menu_add_person);
        menu_add_update.setVisible(false);
        menu_add_validate.setVisible(false);
        menu_add_person.setVisible(true);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_add_person:
                OnclickButtonAdd();
                return true;
            case R.id.menu_add_update:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public Add_Fragment_Person() {
    }
    private void OnclickButtonAdd()
    {

            if(editTextphone.getText().toString().trim().equals("")||editTextname.getText().toString().trim().equals("")||editTextGroupname.getText().toString().trim().equals(""))
            {
            }
            else
            {
                try {

                    float ExpensesValue = Float.valueOf("0");
                    Expense expense = new Expense(ExpensesValue, "");
                    Person person = new Person(editTextname.getText().toString().trim(), editTextphone.getText().toString().trim(), expense, editTextGroupname.getText().toString().trim());
                    boolean alreadyexist = MainActivity.getDataForm().getCsvParse().existPersoninGroup(person, MainActivity.getDataForm().getPersons());
                    if (!alreadyexist)
                    {
                    //    MainActivity.getDataForm().getCsvParse().addPerson(person, expense, MainActivity.getDataForm().getCsvAction());
                        MainActivity.getDataForm().getCsvControl().addPersonCSV(person, expense);
                        HashMap<String, String> element = new HashMap<String, String>();
                        element.put("text1", person.get_name());
                        element.put("text2",person.get_groupname());
                        listeHaspMapEntries.add(element);
                        listViewEntries.setAdapter(simpleAdapter);
                        if(bargs)
                        {
                            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.add_person_toast_ok), Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.add_person_toast_fail), Toast.LENGTH_SHORT).show();
                    }

                    clear_EditText();

                }
                catch(Exception e)
                {
                    System.out.println("Expenditure -> OnclickButtonAdd -> Exception");
                    e.printStackTrace();

                }
             }

    };
    private void clear_EditText()
    {

        editTextphone.getText().clear();
        editTextname.getText().clear();
        editTextGroupname.getText().clear();
        updateDataform();
        adaptername = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namePerson);
        adaptergroup = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, nameGroup);
        editTextname.setAdapter(adaptername);
        editTextGroupname.setAdapter(adaptergroup);
    }
    private void initialization_widget(View rootView)
    {
        editTextphone  = (EditText)rootView.findViewById(R.id.addpersonphone);
        editTextname = (AutoCompleteTextView) rootView.findViewById(R.id.addpersonname);
        editTextGroupname = (AutoCompleteTextView)rootView.findViewById(R.id.addpersongroupe);
        listViewEntries = (ListView) rootView.findViewById(R.id.addpersonlistView);

    }
}
