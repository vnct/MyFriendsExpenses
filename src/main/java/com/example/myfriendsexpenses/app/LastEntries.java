package com.example.myfriendsexpenses.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfriendsexpenses.app.controler.Expense;
import com.example.myfriendsexpenses.app.view.LastEntriesAdapter;
import com.example.myfriendsexpenses.app.controler.Person;

import java.util.List;

public class LastEntries extends Activity {

    ListView listViewlastentries = null;

    LastEntriesAdapter lastEntriesAdapter = null;
    boolean selected=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_entries);

        initialization_widget();


        MainActivity.getDataForm().setStrings(MainActivity.getDataForm().getCsvAction().getCSV());
        List<Person> persons = MainActivity.getDataForm().getCsvParse().inverselistperson(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(),false));
        lastEntriesAdapter = new LastEntriesAdapter(this);
        lastEntriesAdapter.setPersonList(persons);
        listViewlastentries.setAdapter(lastEntriesAdapter);
        listViewlastentries.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewlastentries.setOnItemClickListener(OnItemClickListenerlastentries);

    }
    ActionMode actionMode;
    public void changeContextual(View view, final int position_to_remove) {
        final int positionToRemove = position_to_remove;
        final View view1= view;

        actionMode = startActionMode(new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.last_entries_contextual, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_last_entries_new:
                        Person person_new = (Person) lastEntriesAdapter.getItem(positionToRemove);
                        Intent AddActivity_new = new Intent(getApplication(), Add.class);
                        AddActivity_new.putExtra("Group", person_new.get_groupname());
                        AddActivity_new.putExtra("Phone", person_new.get_phoneNumber());
                        AddActivity_new.putExtra("What", "");
                        AddActivity_new.putExtra("Cost", "");
                        AddActivity_new.putExtra("Name", person_new.get_name());
                        AddActivity_new.putExtra("Action", "" + 2); // action = 1 --> NEW
                        int real_position_new = lastEntriesAdapter.getCount() - position_to_remove - 1;
                        AddActivity_new.putExtra("Position", "" + real_position_new);
                        startActivity(AddActivity_new);
                        return true;
                    case R.id.menu_last_entries_delete:
                        Person person_delete = (Person) lastEntriesAdapter.getItem(positionToRemove);
                        Expense expense_delete = new Expense(person_delete.getExpenseList().get(0).getExpenses(), person_delete.getExpenseList().get(0).getComments());
                        int real_position = lastEntriesAdapter.getCount() - position_to_remove - 1;
                        // System.out.println("positionToRemove = " +toto);
                        MainActivity.getDataForm().getCsvAction().removePerson(person_delete, expense_delete, real_position);
                        MainActivity.getDataForm().setStrings(MainActivity.getDataForm().getCsvAction().getCSV());
                        List<Person> persons = MainActivity.getDataForm().getCsvParse().inverselistperson(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(), false));
                        //  List<Person> persons = MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(),false);
                        lastEntriesAdapter.setPersonList(persons);
                        listViewlastentries.setAdapter(lastEntriesAdapter);
                        listViewlastentries.setOnItemClickListener(OnItemClickListenerlastentries);
                        actionMode.finish();
                        return true;
                    case R.id.menu_last_entries_edit:
                        Person person_edit = (Person) lastEntriesAdapter.getItem(positionToRemove);
                        Expense expense_edit = new Expense(person_edit.getExpenseList().get(0).getExpenses(), person_edit.getExpenseList().get(0).getComments());
                        Intent AddActivity = new Intent(getApplication(), Add.class);
                        AddActivity.putExtra("Group", person_edit.get_groupname());
                        AddActivity.putExtra("Phone", person_edit.get_phoneNumber());
                        AddActivity.putExtra("What", expense_edit.getComments());
                        AddActivity.putExtra("Cost", "" + expense_edit.getExpenses());
                        AddActivity.putExtra("Name", person_edit.get_name());
                        AddActivity.putExtra("Action", "" + 1); // action = 1 --> EDITER
                        int real_position_edit = lastEntriesAdapter.getCount() - position_to_remove - 1;
                        AddActivity.putExtra("Position", "" + real_position_edit);
                        startActivity(AddActivity);


                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                view1.setBackgroundColor(Color.TRANSPARENT);

            }
        });

    }
int position1=-1;
    private AdapterView.OnItemClickListener OnItemClickListenerlastentries = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            for (int j = 0; j < adapterView.getChildCount(); j++)
                adapterView.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

            if(position1==position)
            {
                actionMode.finish();
            }
            view.setBackgroundColor(Color.LTGRAY);
            changeContextual(view, position);
            position1=position;
          /* AlertDialog.Builder adb=new AlertDialog.Builder(LastEntries.this);
            adb.setTitle("Delete?");
            adb.setMessage("Are you sure you want to delete");
            final int positionToRemove = position;
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }});
            adb.show();*/
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
            Intent ExpenditureActivity = new Intent(LastEntries.this,Add.class);
           // ExpenditureActivity.putExtra("CSVLocation",csvAction.getPath_file());
            startActivity(ExpenditureActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
