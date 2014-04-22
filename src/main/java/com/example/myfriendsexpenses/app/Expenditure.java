package com.example.myfriendsexpenses.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myfriendsexpenses.app.controler.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Expenditure extends Activity {

    private Button buttonaddPerson = null;
    private EditText editTextexpense = null,editTextphone = null;
    private AutoCompleteTextView editTextname = null,editTextGroupname = null;
    private CheckBox checkBoxdonator = null;
    private ListView listViewEntries = null;
    private static String[] namePerson = null,nameGroup=null;
    ArrayAdapter<String> adaptername = null,adaptergroup = null;
    // In the onCreate method

   // private CSVAction csvAction = new CSVAction();
    ListAdapter simpleAdapter = null;
    List<HashMap<String, String>> listeHaspMapEntries = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure);
        initialization_widget();
        buttonaddPerson.setOnClickListener(OnclickButtonAdd);
        listeHaspMapEntries = new ArrayList<HashMap<String, String>>();
        simpleAdapter = new SimpleAdapter(this,listeHaspMapEntries,android.R.layout.simple_list_item_2,new String[] {"text1", "text2"},new int[] {android.R.id.text1, android.R.id.text2 });
        MainActivity.getDataForm().setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(),true));
        namePerson = MainActivity.getDataForm().getCsvParse().namePerson(MainActivity.getDataForm().getPersons());
        nameGroup = MainActivity.getDataForm().getCsvParse().nameGroup(MainActivity.getDataForm().getGroups());
        adaptername = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, namePerson);
        adaptergroup = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameGroup);
        editTextname.setAdapter(adaptername);
        editTextGroupname.setAdapter(adaptergroup);
        editTextname.setOnItemClickListener(onItemClickListenertextname);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }
    private AdapterView.OnItemClickListener onItemClickListenertextname = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            editTextphone.setText(MainActivity.getDataForm().getCsvParse().getPhone(editTextname.getText().toString().trim(),editTextGroupname.getText().toString().trim(),MainActivity.getDataForm().getGroups()));
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
                    editTextexpense.setHint("00.00");

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
    private void initialization_widget()
    {
        editTextexpense = (EditText)findViewById(R.id.addexpense);
        editTextphone = (EditText)findViewById(R.id.addphone);
       // editTextname = (EditText)findViewById(R.id.addname);
        editTextname = (AutoCompleteTextView) findViewById(R.id.addname);
        editTextGroupname = (AutoCompleteTextView)findViewById(R.id.addgroupe);
        buttonaddPerson = (Button) findViewById(R.id.addaddbutton);
        checkBoxdonator = (CheckBox) findViewById(R.id.adddonator);
        listViewEntries = (ListView) findViewById(R.id.addlistView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.expenditure, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (item.getItemId() == R.id.csvread) {
            // Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            Intent ExpenditureActivity = new Intent(Expenditure.this,LastEntries.class);
            ExpenditureActivity.putExtra("CSVLocation", MainActivity.getDataForm().getCsvAction().getPath_file());
          //  ExpenditureActivity.putExtra("CSVLocation",csvAction.getPath_file());
            startActivity(ExpenditureActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_expenditure, container, false);
            return rootView;
        }
    }
}
