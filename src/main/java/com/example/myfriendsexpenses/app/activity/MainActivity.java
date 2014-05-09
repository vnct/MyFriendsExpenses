package com.example.myfriendsexpenses.app.activity;

import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.ShakeDetector;
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

    public MainActivity() {
    }


    public static DataForm getDataForm() {
        return dataForm;
    }
 /*   public static void setDataForm(DataForm dataForm) {
        MainActivity.dataForm = dataForm;
    }*/


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle =   getTitle();
        fillDataForm(false);

// ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                /*
                 * The following method, "handleShakeEvent(count):" is a stub //
                 * method you would use to setup whatever you want done once the
                 * device has been shook.
                 */
                             handleShakeEvent(count);
            }
        });
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    private void  handleShakeEvent(int count)
    {
        if(count==3)
        {
            Intent EasterEggActivity = new Intent(this,EasterEgg.class);
            // ExpenditureActivity.putExtra("CSVLocation",csvAction.getPath_file());
            startActivity(EasterEggActivity);
        }
    }
    static public void fillDataForm(boolean personconcatenation)
    {
        dataForm.getCsvControl().createCSVFile();
        dataForm.setStrings(dataForm.getCsvControl().getdataCSV());
        dataForm.setGroups(dataForm.getCsvParse().createGroups(dataForm.getStrings()));
        dataForm.setPersons(dataForm.getCsvParse().fillPerson(dataForm.getStrings(),personconcatenation));
        dataForm.getCsvParse().fillGroups(dataForm.getCsvParse().fillPerson(dataForm.getStrings(),true), dataForm.getGroups());
        dataForm.fillgroupname();
        operatepayback();



    }

    public void onPause()
    {

        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
        //    System.out.println("------------- MainActivity - onPause -------------");
    }

    public void onResume()
    {
        super.onResume();
        fillDataForm(false);
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,    SensorManager.SENSOR_DELAY_UI);

        //    System.out.println("------------- MainActivity - onResume -------------");
    }

    public void onRestart()
    {
        super.onRestart();
        //         System.out.println("------------- MainActivity - onRestart -------------");
    }
    public void onStart()
    {
        super.onStart();

        //      System.out.println("------------- MainActivity - onStart -------------");
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager;
        switch (position) {
            case 0:
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Main_Fragment_EveryBody.newInstance(position + 1),"everybody")
                        .commit();
                break;
            default:
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Main_Fragment_Group.newInstance(position + 1),"group")
                        .commit();
                break;
        }

        //     System.out.println("MainActivity --> onNavigationDrawerItemSelected ==> " + position );
    }

    static public void operatepayback()
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
    @SuppressWarnings("unused")
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
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }


}
