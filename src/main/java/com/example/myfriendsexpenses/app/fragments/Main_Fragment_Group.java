package com.example.myfriendsexpenses.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.MainActivity;
import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Expense;
import com.example.myfriendsexpenses.app.controler.Group;
import com.example.myfriendsexpenses.app.controler.Person;
import com.example.myfriendsexpenses.app.view.BalanceAdapter;
import com.example.myfriendsexpenses.app.view.ExpenseAdapter;
import com.example.myfriendsexpenses.app.view.ListTitleAdapter;
import com.example.myfriendsexpenses.app.view.ListTitleGroupAdapter;
import com.example.myfriendsexpenses.app.view.MainAdapter;
import com.example.myfriendsexpenses.app.view.MergeAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 24/04/14.
 */
public class Main_Fragment_Group extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private BalanceAdapter balanceAdapter;
    private ExpenseAdapter expenseAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_group, container, false);

         /*   TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
           /* System.out.println("---> getArguments()" + getArguments().getInt(ARG_SECTION_NUMBER));
            System.out.println("---> getArguments()" + dataForm.getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1));*/


        List<ExpenseAdapter> expenseAdapter1 = new ArrayList<ExpenseAdapter>();
        MergeAdapter mergeAdapter = new MergeAdapter();
        TextView textViewGroupBaseAdapterExpensesPerson = (TextView) rootView.findViewById(R.id.textViewgrouptextExpensesPerson);
        TextView textViewGroupBaseAdapterTotalExpense = (TextView) rootView.findViewById(R.id.textViewgrouptextTotalExpenses);
        List<Group> groups = MainActivity.getDataForm().getGroups();
        String group = MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1);
        for(int igroups=0;igroups<groups.size();igroups++)
        {
            if(MainActivity.getDataForm().getGroups().get(igroups).get_name().equals(group)==true) {
                ExpenseAdapter expenseAdapterexpense = new ExpenseAdapter(getActivity(), true);
                Group group1 = MainActivity.getDataForm().getGroups().get(igroups);
                MainActivity.getDataForm().getGroups().get(igroups).doBalance();
                float v = group1.get_expensePerPerson();
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                textViewGroupBaseAdapterExpensesPerson.setText(df.format(v) + " €");
                textViewGroupBaseAdapterTotalExpense.setText(df.format(group1.get_totalExpenses()) + " €");
                expenseAdapterexpense.setPersonList(MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(), false), group, true));
                expenseAdapter1.add(expenseAdapterexpense);
                ExpenseAdapter expenseAdapterbalance = new ExpenseAdapter(getActivity(), false);
                expenseAdapterbalance.setBalanceList(MainActivity.getDataForm().getGroups().get(igroups).getBalances());
                expenseAdapter1.add(expenseAdapterbalance);
            }
        }
        for(int iExpenseAdapter=0;iExpenseAdapter<expenseAdapter1.size();iExpenseAdapter++)
        {
            mergeAdapter.addAdapter(new ListTitleGroupAdapter(getActivity(),expenseAdapter1.get(iExpenseAdapter),expenseAdapter1.get(iExpenseAdapter).getBooleanExpense()));
            mergeAdapter.addAdapter(expenseAdapter1.get(iExpenseAdapter));
        }
        //System.out.println("mergeAdapter.addAdapter = " + mergeAdapter.getCount());
        mergeAdapter.setNoItemsText("Nothing to display. Add an expenditure");
        ((ListView)rootView.findViewById(R.id.listViewExpenseAdapter)).setAdapter(mergeAdapter);
        return rootView;


       /* List<Group> groups = MainActivity.getDataForm().getGroups();
        TextView textViewGroupBaseAdapterExpensesPerson = (TextView) rootView.findViewById(R.id.textViewgrouptextExpensesPerson);
        TextView textViewGroupExpense = (TextView) rootView.findViewById(R.id.textViewGroupExpense);
        TextView textViewGroupBaseAdapterTotalExpense = (TextView) rootView.findViewById(R.id.textViewgrouptextTotalExpenses);
        TextView textViewGroupBalancing = (TextView) rootView.findViewById(R.id.textViewGroupBalancing);
        expenseAdapter = new ExpenseAdapter(getActivity());
        expenseAdapter.setPersonList(new ArrayList<Person>());
        balanceAdapter = new BalanceAdapter(getActivity());
        String group = MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1);
        for(int igroups=0;igroups<groups.size();igroups++)
        {
            if(MainActivity.getDataForm().getGroups().get(igroups).get_name().equals(group)==true)
            {
                Group group1 = MainActivity.getDataForm().getGroups().get(igroups);
                MainActivity.getDataForm().getGroups().get(igroups).doBalance();
                float v = group1.get_expensePerPerson();
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                textViewGroupBaseAdapterExpensesPerson.setText(df.format(v) + " €");
                textViewGroupBaseAdapterTotalExpense.setText(group1.get_totalExpenses()+ " €");
                expenseAdapter.setPersonList(MainActivity.getDataForm().getCsvParse().parsePersonbyGroups(MainActivity.getDataForm().getCsvParse().fillPerson(MainActivity.getDataForm().getCsvAction().getCSV(),false),group,true));
                if(expenseAdapter.getCount()==0)
                {
                    textViewGroupExpense.setVisibility(View.INVISIBLE);
                }
                else
                {
                    textViewGroupExpense.setVisibility(View.VISIBLE);
                }
                balanceAdapter.setBalanceList(MainActivity.getDataForm().getGroups().get(igroups).getBalances());
                if(balanceAdapter.getCount()==0)
                {
                    textViewGroupBalancing.setVisibility(View.INVISIBLE);
                }
                else
                {
                    textViewGroupBalancing.setVisibility(View.VISIBLE);
                }
            }
        }
        ((ListView)rootView.findViewById(R.id.listViewGroupAdapter)).setAdapter(balanceAdapter);
        ((ListView)rootView.findViewById(R.id.listViewExpenseAdapter)).setAdapter(expenseAdapter);
        return rootView;*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER),MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1));
    }
}

