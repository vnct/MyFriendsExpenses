package com.example.myfriendsexpenses.app;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.example.myfriendsexpenses.app.controler.BalanceAdapter;
import com.example.myfriendsexpenses.app.controler.DataForm;
import com.example.myfriendsexpenses.app.controler.Group;
import com.example.myfriendsexpenses.app.controler.GroupAdapter;
import com.example.myfriendsexpenses.app.controler.ListTitleAdapter;
import com.example.myfriendsexpenses.app.controler.MainAdapter;
import com.example.myfriendsexpenses.app.controler.MergeAdapter;
import com.example.myfriendsexpenses.app.fragments.Add_Fragment_Expenditure;
import com.example.myfriendsexpenses.app.fragments.Add_Fragment_Person;
import com.example.myfriendsexpenses.app.fragments.Main_Fragment_EveryBody;
import com.example.myfriendsexpenses.app.fragments.Main_Fragment_Group;

import java.util.ArrayList;
import java.util.List;

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
    private ListView listViewPerson = null;

    public static DataForm getDataForm() {
        return dataForm;
    }
    List<MainAdapter> mainAdapter = new ArrayList<MainAdapter>();
    BalanceAdapter balanceAdapter = null;
    GroupAdapter groupAdapter = null;
    public static void setDataForm(DataForm dataForm) {
        MainActivity.dataForm = dataForm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mainAdapter.add(new MainAdapter(this));
        groupAdapter = new GroupAdapter(this);
        balanceAdapter = new BalanceAdapter(this);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle =   getTitle();
        listViewPerson = (ListView) findViewById(R.id.listViewMainAdapter);
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
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
      /*  switch (position) {
            case 0:
               fragmentManager.beginTransaction()
                        .replace(R.id.container, Main_Fragment_EveryBody.newInstance(position + 1))
                        .commit();
            default:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, Main_Fragment_Group.newInstance(position + 1))
                        .commit();
        }*/

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
        System.out.println("MainActivity --> onSectionAttached ==> " + number + " - " +  group);
        if(group.equals("Everybody")==true)
        {
            mainAdapter = new ArrayList<MainAdapter>();
            MergeAdapter mergeAdapter = new MergeAdapter();
            for(int iGroups=0;iGroups<dataForm.getGroups().size();iGroups++)
            {
                MainAdapter mainAdapter1 = new MainAdapter(this);
                mainAdapter1.setPersonList(dataForm.getGroups().get(iGroups).getPersons());
                mainAdapter1.setGroup(dataForm.getGroups().get(iGroups));
                mainAdapter.add(mainAdapter1);

            }
            for(int iMainadapter=0;iMainadapter<mainAdapter.size();iMainadapter++)
            {
                mergeAdapter.addAdapter(new ListTitleAdapter(this, mainAdapter.get(iMainadapter).getGroup() , mainAdapter.get(iMainadapter)));
                mergeAdapter.addAdapter(mainAdapter.get(iMainadapter));
            }
            mergeAdapter.setNoItemsText("Nothing to display. This list is empty.");
            ((ListView)findViewById(R.id.listViewMainAdapter)).setAdapter(mergeAdapter);

        }
        else
        {
            List<Group> groups = dataForm.getGroups();
            for(int igroups=0;igroups<groups.size();igroups++)
            {
                if(dataForm.getGroups().get(igroups).get_name().equals(group)==true)
                {
                    dataForm.getGroups().get(igroups).doBalance();
                    balanceAdapter.setBalanceList(dataForm.getGroups().get(igroups).getBalances());
                    listViewPerson.setAdapter(balanceAdapter);
                }

            }

        }

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

    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
         /*   TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
           /* System.out.println("---> getArguments()" + getArguments().getInt(ARG_SECTION_NUMBER));
            System.out.println("---> getArguments()" + dataForm.getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1));*/
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER),dataForm.getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1));
        }
    }


}
