package com.example.myfriendsexpenses.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.myfriendsexpenses.app.controler.CSVParse;
import com.example.myfriendsexpenses.app.controler.Person;
import com.example.myfriendsexpenses.app.model.CSVAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LastEntries extends Activity {

    ListView listViewlastentries = null;
    ListAdapter simpleAdapter = null;
    List<HashMap<String, String>> listeHaspMapLastEntries = null;
   // private CSVAction csvAction = new CSVAction();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_entries);
        initialization_widget();
        Bundle b = getIntent().getExtras();
        if (b != null) {
            String csvLocation = b.getString("CSVLocation");
            //csvAction.setPath_file(csvLocation);
        }
      //  List<String[]> strings = csvAction.getCSV();
        MainActivity.getDataForm().setStrings(MainActivity.getDataForm().getCsvAction().getCSV());
        List<Person> persons = MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(),false);
        listeHaspMapLastEntries = DisplayCSV(persons);
        simpleAdapter = new SimpleAdapter(this,listeHaspMapLastEntries,android.R.layout.simple_list_item_2,new String[] {"text1", "text2"},new int[] {android.R.id.text1, android.R.id.text2 });
        listViewlastentries.setOnItemClickListener(OnItemClickListenerlastentries);
        listViewlastentries.setAdapter(simpleAdapter);

    }

    private List<HashMap<String, String>>  DisplayCSV(List<Person> persons)
    {
        List<HashMap<String, String>> listeHaspMapLastEntriestmp = new ArrayList<HashMap<String, String>>();
        for(int ipersons=0;ipersons<persons.size();ipersons++)
        {
            HashMap<String, String> element = new HashMap<String, String>();
            Person person = persons.get(ipersons);
            element.put("text1", "" + person.get_name() + ", " + person.get_phoneNumber() +"");
            element.put("text2","" +  Float.toString(person.get_expenses()) + " €, " + person.get_groupname());

            listeHaspMapLastEntriestmp.add(element);
        }

        return listeHaspMapLastEntriestmp;
    }

    private Person HashMaptoPerson( HashMap<String, String> element)
    {
        String[] text1 = element.get("text1").split(",");
        String[] text2 = element.get("text2").split(",");
        Person person = new Person();
        person.set_name(text1[0].substring(0,text1[0].length()));
        person.set_phoneNumber(text1[1].substring(1, text1[1].length()));
        person.set_expenses(Float.valueOf(text2[0].substring(0,text2[0].length()-2)));
        person.set_groupname(text2[1].substring(1,text2[1].length()));
        return person;

    }
    private AdapterView.OnItemClickListener OnItemClickListenerlastentries = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            AlertDialog.Builder adb=new AlertDialog.Builder(LastEntries.this);
            adb.setTitle("Delete?");
            adb.setMessage("Are you sure you want to delete");
            final int positionToRemove = position;
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    HashMap<String, String> element = listeHaspMapLastEntries.get(positionToRemove);
                    Person person = HashMaptoPerson(element);
                    System.out.println(person.get_name() +  "-" + person.get_phoneNumber() + "-" + person.get_expenses());
                    System.out.println("position : " + positionToRemove);
                    MainActivity.getDataForm().getCsvAction().removePerson(person,positionToRemove);
                    //csvAction.removePerson(person,positionToRemove);
                    listeHaspMapLastEntries.remove(positionToRemove);

                    listViewlastentries.setAdapter(simpleAdapter);
                }});
            adb.show();
        }

    };

    private void initialization_widget()
    {
        listViewlastentries = (ListView)findViewById(R.id.lastentrieslistView);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.last_entries, menu);
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
        if (item.getItemId() == R.id.csvadd) {
            // Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            Intent ExpenditureActivity = new Intent(LastEntries.this,Expenditure.class);
           // ExpenditureActivity.putExtra("CSVLocation",csvAction.getPath_file());
            startActivity(ExpenditureActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
