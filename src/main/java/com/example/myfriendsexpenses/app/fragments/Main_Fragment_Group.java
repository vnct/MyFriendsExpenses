package com.example.myfriendsexpenses.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myfriendsexpenses.app.MainActivity;
import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Group;
import com.example.myfriendsexpenses.app.view.BalanceAdapter;

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
        List<Group> groups = MainActivity.getDataForm().getGroups();
        balanceAdapter = new BalanceAdapter(getActivity());
        String group = MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1);
        for(int igroups=0;igroups<groups.size();igroups++)
        {
            if(MainActivity.getDataForm().getGroups().get(igroups).get_name().equals(group)==true)
            {
                MainActivity.getDataForm().getGroups().get(igroups).doBalance();
                balanceAdapter.setBalanceList(MainActivity.getDataForm().getGroups().get(igroups).getBalances());
            }
        }
        ((ListView)rootView.findViewById(R.id.listViewGroupAdapter)).setAdapter(balanceAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER),MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1));
    }
}

