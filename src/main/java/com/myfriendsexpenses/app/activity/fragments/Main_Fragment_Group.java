package com.myfriendsexpenses.app.activity.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myfriendsexpenses.app.activity.activity.Add;
import com.myfriendsexpenses.app.activity.activity.LastEntries;
import com.myfriendsexpenses.app.activity.activity.MainActivity;
import com.example.myfriendsexpenses.app.R;

import com.myfriendsexpenses.app.activity.classes.Balance;
import com.myfriendsexpenses.app.activity.classes.Group;

import com.myfriendsexpenses.app.activity.classes.Person;
import com.myfriendsexpenses.app.activity.view.ExpenseAdapter;

import com.myfriendsexpenses.app.activity.view.ListTitleGroupAdapter;

import com.myfriendsexpenses.app.activity.view.MergeAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 24/04/14
 */
@SuppressWarnings({"UnusedAssignment", "ConstantConditions", "FieldCanBeLocal"})
public class Main_Fragment_Group extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String group="";
    private Group group1=new Group();
    private int nbExpense=0,nbBalance=0;
    private MergeAdapter mergeAdapter = new MergeAdapter();
    TextView textViewGroupBaseAdapterExpensesPerson = null;
    TextView textViewGroupBaseAdapterTotalExpense = null;
    ListView listViewMergeAdapter = null;

    MenuItem menu_edit=null,menu_delete=null,menu_read=null,menu_add=null;
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Main_Fragment_Group newInstance(int sectionNumber) {
        Main_Fragment_Group fragment = new Main_Fragment_Group();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Main_Fragment_Group() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        menu_edit = menu.findItem(R.id.menu_main_group_edit);
        menu_delete = menu.findItem(R.id.menu_main_group_delete);
        menu_read = menu.findItem(R.id.menu_main_csvread);
        menu_add = menu.findItem(R.id.menu_main_csvadd);
        menu_edit.setVisible(true);
        menu_delete.setVisible(true);
        menu_read.setVisible(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_main_group_edit:
                alert_rename_group();
                return true;
            case R.id.menu_main_group_delete:
                alert_delete_group();
                return true;
            case R.id.menu_main_csvadd :
                Intent AddActivity = new Intent(getActivity(),Add.class);
                AddActivity.putExtra("Group", group);
                AddActivity.putExtra("Phone", "");
                AddActivity.putExtra("What", "");
                AddActivity.putExtra("Cost", "" + 0.0);
                AddActivity.putExtra("Name", "");
                AddActivity.putExtra("Action", "" + 2); // action = 2 --> EDITER
                AddActivity.putExtra("Position", "" + 0);
                startActivity(AddActivity);
                return true;
            case R.id.menu_main_csvread:
                Intent ExpenditureActivity = new Intent(getActivity(),LastEntries.class);
                ExpenditureActivity.putExtra("GroupName",group);
                ExpenditureActivity.putExtra("PersonName","");
                ExpenditureActivity.putExtra("CommentExpense","");
                startActivity(ExpenditureActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void alert_delete_group()
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
        adb.setTitle(getString(R.string.popup_delete_group_title));

        adb.setMessage(getString(R.string.popup_delete_group_message));
        adb.setNegativeButton(getString(R.string.popup_delete_group_negative_button), null);
        adb.setPositiveButton(getString(R.string.popup_delete_group_positive_button), new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                group1.setPersons(MainActivity.getDataForm().getPersons());
                //group1.setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(), false));
                group1.setPersons(MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(group1.getPersons(),group1.get_name(),false));
           //     MainActivity.getDataForm().getCsvAction().removeGroup(group1.getPersons());
                MainActivity.getDataForm().getCsvControl().removeGroup(group1.getPersons());
                Toast.makeText(getActivity().getApplicationContext(),getString(R.string.popup_delete_group_positive_button), Toast.LENGTH_SHORT).show();
                getActivity().finish();
                startActivity(getActivity().getIntent());


            }});
        adb.show();
    }
    void alert_rename_group()
    {
        AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());

        adb.setTitle(getString(R.string.popup_rename_group_title));
        adb.setMessage(getString(R.string.popup_rename_group_message));
        final EditText edittextinput = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        edittextinput.setLayoutParams(lp);
        adb.setView(edittextinput);
        adb.setNegativeButton(getString(R.string.popup_rename_group_negative_button), null);
        adb.setPositiveButton(getString(R.string.popup_rename_group_positive_button), new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String newnamegroup=edittextinput.getText().toString().trim();

                group1.setPersons(MainActivity.getDataForm().getPersons());
             //   group1.setPersons(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(), false));
                group1.setPersons(MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(group1.getPersons(), group1.get_name(), false));
              //  group1.setPersons(MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(group1.getPersons(),group1.get_name(),false));
                MainActivity.getDataForm().getCsvControl().changeGroup(group1.getPersons(),newnamegroup);
           //     MainActivity.getDataForm().getCsvAction().changeGroup(group1.getPersons(),newnamegroup);
                MainActivity.fillDataForm(false);
                getActivity().finish();
                startActivity(getActivity().getIntent());
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.popup_rename_group_positive_button), Toast.LENGTH_SHORT).show();
            }});
        adb.show();




    }
    public void onResume()
    {
        super.onResume();
        MainActivity.fillDataForm(false);
        List<Group> groups = MainActivity.getDataForm().getGroups();

        mergeAdapter = new MergeAdapter();
        group = MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER) - 1);

        for (int igroups = 0; igroups < groups.size(); igroups++) {
            if (MainActivity.getDataForm().getGroups().get(igroups).get_name().equals(group)) {

                group1 = MainActivity.getDataForm().getGroups().get(igroups);
                mergeAdapter=fillMergeAdapter(igroups);
                float v = group1.get_expensePerPerson();
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                textViewGroupBaseAdapterExpensesPerson.setText(df.format(v) + " €");
                textViewGroupBaseAdapterTotalExpense.setText(df.format(group1.get_totalExpenses()) + " €");

            }
        }

        listViewMergeAdapter.setAdapter(mergeAdapter);

       //System.out.println("------------- Add_Fragment_Person - onResume -------------");

    }
    public MergeAdapter fillMergeAdapter(int indexgroup)
    {
        List<ExpenseAdapter> expenseAdapter1 = new ArrayList<ExpenseAdapter>();

        ExpenseAdapter expenseAdapterexpense = new ExpenseAdapter(getActivity(), true);
        MainActivity.getDataForm().getGroups().get(indexgroup).doBalance();
       // expenseAdapterexpense.setPersonList(MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(), false), group, true));
        expenseAdapterexpense.setPersonList(MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(), false), group, true));
        expenseAdapterexpense.setBconcat(false);

        if(expenseAdapterexpense.getCount()>6) {
            //Si trop d'entrée, on concatène
            expenseAdapterexpense.setBconcat(true);
            expenseAdapterexpense.setPersonList(MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getStrings(), true), group, true));
        }
        nbExpense=expenseAdapterexpense.getCount();

        expenseAdapter1.add(expenseAdapterexpense);
        ExpenseAdapter expenseAdapterbalance = new ExpenseAdapter(getActivity(), false);
        expenseAdapterbalance.setBalanceList(MainActivity.getDataForm().getGroups().get(indexgroup).getBalances());
        expenseAdapter1.add(expenseAdapterbalance);

        nbBalance=expenseAdapterbalance.getCount();
        for (ExpenseAdapter anExpenseAdapter1 : expenseAdapter1) {
            mergeAdapter.addAdapter(new ListTitleGroupAdapter(getActivity(), anExpenseAdapter1, anExpenseAdapter1.getBooleanExpense()));
            mergeAdapter.addAdapter(anExpenseAdapter1);
        }
        mergeAdapter.setNoItemsText(getString(R.string.listview_group_message));

        return  mergeAdapter;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_main_group, container, false);
        mergeAdapter = new MergeAdapter();
        textViewGroupBaseAdapterExpensesPerson = (TextView) rootView.findViewById(R.id.textViewgrouptextExpensesPerson);
        textViewGroupBaseAdapterTotalExpense = (TextView) rootView.findViewById(R.id.textViewgrouptextTotalExpenses);
        listViewMergeAdapter = ((ListView) rootView.findViewById(R.id.listViewExpenseAdapter));



        listViewMergeAdapter.setOnItemClickListener(onItemClickListener);
        listViewMergeAdapter.setOnItemLongClickListener(onItemLongClickListener);
        return rootView;

    }

    private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            if(i>nbExpense+1)
            {
                Balance mybalanceAdapter = (Balance) adapterView.getItemAtPosition(i);

                Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mybalanceAdapter.getPersonput().get_phoneNumber()));
                sms.putExtra("sms_body", getString(R.string.sms_body) + " " + mybalanceAdapter.getValue() + " " + getString(R.string.EUR) + " " + getString(R.string.to) + " " + mybalanceAdapter.getPersonget().get_name());
                startActivity(sms);

            }
            return true;
        }
    };
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            if((i>0)&&(i<=nbExpense))
            {
                Person mypersonAdapter = (Person) adapterView.getItemAtPosition(i);
                Intent ExpenditureActivity = new Intent(getActivity(),LastEntries.class);
                ExpenditureActivity.putExtra("GroupName",mypersonAdapter.get_groupname());
                ExpenditureActivity.putExtra("PersonName", mypersonAdapter.get_name());
                if(mypersonAdapter.getExpenseList().size()>1)
                {
                    ExpenditureActivity.putExtra("CommentExpense","");
                }
                else
                {
                    ExpenditureActivity.putExtra("CommentExpense",mypersonAdapter.getExpenseList().get(0).getComments());
                }

                startActivity(ExpenditureActivity);
            }

            if(i>nbExpense)
            {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.popup_info), Toast.LENGTH_LONG).show();
            }
            //

        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER),MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1));
    }
}

