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
import com.example.myfriendsexpenses.app.controler.LastEntriesAdapter;
import com.example.myfriendsexpenses.app.controler.Person;
import com.example.myfriendsexpenses.app.model.CSVAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LastEntries extends Activity {

    ListView listViewlastentries = null;

    LastEntriesAdapter lastEntriesAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_entries);
        initialization_widget();

        MainActivity.getDataForm().setStrings(MainActivity.getDataForm().getCsvAction().getCSV());
        List<Person> persons = MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(),false);
        lastEntriesAdapter = new LastEntriesAdapter(this);
        lastEntriesAdapter.setPersonList(persons);
        listViewlastentries.setAdapter(lastEntriesAdapter);
        listViewlastentries.setOnItemClickListener(OnItemClickListenerlastentries);
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
                    Person person = (Person) lastEntriesAdapter.getItem(positionToRemove);
                    MainActivity.getDataForm().getCsvAction().removePerson(person,positionToRemove);
                    MainActivity.getDataForm().setStrings(MainActivity.getDataForm().getCsvAction().getCSV());
                    List<Person> persons = MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(),false);
                    lastEntriesAdapter.setPersonList(persons);
                    listViewlastentries.setAdapter(lastEntriesAdapter);
                    listViewlastentries.setOnItemClickListener(OnItemClickListenerlastentries);
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
