package com.example.myfriendsexpenses.app;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.example.myfriendsexpenses.app.fragments.Main_Fragment_EveryBody;
import com.example.myfriendsexpenses.app.fragments.Main_Fragment_Group;
import com.example.myfriendsexpenses.app.controler.DataForm;



public class MainActivity extends Activity
     implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    static private DataForm dataForm = new DataForm();
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    public static DataForm getDataForm() {
        return dataForm;
    }
    public static void setDataForm(DataForm dataForm) {
        MainActivity.dataForm = dataForm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle =   getTitle();
        System.out.println("------------- MainActivity -------------");
        dataForm.getCsvAction().createFile();
        dataForm.setStrings(dataForm.getCsvAction().getCSV());
        dataForm.setGroups(dataForm.getCsvParse().createGroups(dataForm.getStrings()));
        dataForm.getCsvParse().fillGroups(dataForm.getCsvParse().fillPerson(dataForm.getStrings(),true), dataForm.getGroups());
        dataForm.fillgroupname();
        operatepayback();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager;
        switch (position) {
            case 0:
               fragmentManager = getFragmentManager();
               fragmentManager.beginTransaction()
                        .replace(R.id.container, Main_Fragment_EveryBody.newInstance(position + 1))
                        .commit();
                break;
            default:
                fragmentManager = getFragmentManager();
                 fragmentManager.beginTransaction()
                        .replace(R.id.container, Main_Fragment_Group.newInstance(position + 1))
                        .commit();
                break;
        }

        System.out.println("MainActivity --> onNavigationDrawerItemSelected ==> " + position );
    }

    public void operatepayback()
    {
     //   System.out.println("Groups " + dataForm.getGroups().size());
        for(int igroups=0;igroups<dataForm.getGroups().size();igroups++)
        {
            dataForm.getGroups().get(igroups).totalExpenses();
            float exp = dataForm.getGroups().get(igroups).expensesPerPerson();
            //System.out.println("Persons " + dataForm.getGroups().get(igroups).getPersons().size());
            for(int ipersons=0;ipersons<dataForm.getGroups().get(igroups).getPersons().size();ipersons++)
            {
                dataForm.getGroups().get(igroups).set_expensePerPerson(exp);
                dataForm.getGroups().get(igroups).getPersons().get(ipersons).operatePayback(exp);

       //         System.out.println("Calcul Payback Person " + exp);
            }
        }
    }
    // Action principale
    public void onSectionAttached(int number,String group) {
        mTitle = group;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            System.out.println("Settings");

            return true;
        }
        if (item.getItemId() == R.id.csvadd) {

            Intent AddActivity = new Intent(MainActivity.this,Add.class);
            startActivity(AddActivity);
            return true;
        }
        if (item.getItemId() == R.id.csvread) {
            // Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            Intent ExpenditureActivity = new Intent(MainActivity.this,LastEntries.class);
            startActivity(ExpenditureActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
