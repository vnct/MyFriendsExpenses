package com.example.myfriendsexpenses.app.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myfriendsexpenses.app.MainActivity;
import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lapie on 24/04/14.
 */
public class Add_Fragment_Expenditure extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button buttonaddPerson = null;
    private EditText editTextexpense = null,editTextphone = null;
    private AutoCompleteTextView editTextname = null,editTextGroupname = null;
    private ListView listViewEntries = null;

    private static String[] namePerson = null,nameGroup=null;
    ArrayAdapter<String> adaptername = null,adaptergroup = null;
    ListAdapter simpleAdapter = null;
    List<HashMap<String, String>> listeHaspMapEntries = null;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
             initialization_widget(rootView);
        buttonaddPerson.setOnClickListener(OnclickButtonAdd);
        listeHaspMapEntries = new ArrayList<HashMap<String, String>>();
        simpleAdapter = new SimpleAdapter(getActivity(),listeHaspMapEntries,android.R.layout.simple_list_item_2,new String[] {"text1", "text2"},new int[] {android.R.id.text1, android.R.id.text2 });
        MainActivity.getDataForm().setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(),true));
        namePerson = MainActivity.getDataForm().getCsvParse().namePerson(MainActivity.getDataForm().getPersons());
        nameGroup = MainActivity.getDataForm().getCsvParse().nameGroup(MainActivity.getDataForm().getGroups());
        adaptername = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, namePerson);
        adaptergroup = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, nameGroup);
        editTextname.setAdapter(adaptername);
        editTextGroupname.setAdapter(adaptergroup);

        editTextname.setOnItemClickListener(onItemClickListenertextname);



        return rootView;
    }
    public static Add_Fragment_Expenditure newInstance(int sectionNumber) {
        Add_Fragment_Expenditure fragment = new Add_Fragment_Expenditure();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Add_Fragment_Expenditure() {
    }
    private AdapterView.OnItemClickListener onItemClickListenertextname = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(),editTextGroupname.getText().toString().trim(),MainActivity.getDataForm().getPersons()));
        }
    };
    private View.OnClickListener OnclickButtonAdd = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(editTextexpense.getText().equals("")==true||editTextphone.getText().equals("")||editTextname.getText().equals("")||editTextGroupname.getText().equals(""))
            {
            }
            else
            {
                try{
                    String Expenses = editTextexpense.getText().toString();
                    float ExpensesValue = Float.valueOf(Expenses);
                    Person person = new Person(editTextname.getText().toString().trim(),editTextphone.getText().toString().trim(),ExpensesValue,editTextGroupname.getText().toString().trim());
                    MainActivity.getDataForm().getCsvParse().addPerson(person, MainActivity.getDataForm().getCsvAction());
                    HashMap<String, String> element = new HashMap<String, String>();
                    element.put("text1", "" + person.get_name() + " (" + person.get_groupname() +")");
                    element.put("text2","Expenditure : " +  editTextexpense.getText().toString() + " €");
                    listeHaspMapEntries.add(element);
                    listViewEntries.setAdapter(simpleAdapter);
                    clear_EditText();
                }
                catch(Exception e)
                {
                    System.out.println("Expenditure -> OnclickButtonAdd -> Exception");
                    e.printStackTrace();
                    editTextexpense.getText().clear();
                    editTextexpense.setHint("10.0");

                }
             }
        }
    };
    private void clear_EditText()
    {
        editTextexpense.getText().clear();
        editTextphone.getText().clear();
        editTextname.getText().clear();
        editTextGroupname.getText().clear();
    }
    private void initialization_widget(View rootView)
    {
        editTextexpense = (EditText)rootView.findViewById(R.id.addexpense);
        editTextphone = (EditText)rootView.findViewById(R.id.addphone);
        editTextname = (AutoCompleteTextView) rootView.findViewById(R.id.addname);
        editTextGroupname = (AutoCompleteTextView)rootView.findViewById(R.id.addgroupe);
        buttonaddPerson = (Button) rootView.findViewById(R.id.addaddbutton);
        listViewEntries = (ListView) rootView.findViewById(R.id.addlistView);
         }
}
