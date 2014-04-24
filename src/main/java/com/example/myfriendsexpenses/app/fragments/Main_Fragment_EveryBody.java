package com.example.myfriendsexpenses.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.MainActivity;
import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.GroupAdapter;
import com.example.myfriendsexpenses.app.controler.ListTitleAdapter;
import com.example.myfriendsexpenses.app.controler.MainAdapter;
import com.example.myfriendsexpenses.app.controler.MergeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 24/04/14.
 */
public class Main_Fragment_EveryBody extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listViewMainAdapter = (ListView) rootView.findViewById(R.id.listViewMainAdapter);

       // GroupAdapter groupAdapter  = new GroupAdapter(getActivity());
        List<MainAdapter> mainAdapter = new ArrayList<MainAdapter>();
        MergeAdapter mergeAdapter = new MergeAdapter();
        //System.out.println("-- Groups : " + MainActivity.getDataForm().getGroups().size());
        for(int iGroups=0;iGroups<MainActivity.getDataForm().getGroups().size();iGroups++)
        {
            MainAdapter mainAdapter1 = new MainAdapter(getActivity());
            mainAdapter1.setPersonList(MainActivity.getDataForm().getGroups().get(iGroups).getPersons());
            mainAdapter1.setGroup(MainActivity.getDataForm().getGroups().get(iGroups));
            mainAdapter.add(mainAdapter1);

        }
        for(int iMainadapter=0;iMainadapter<mainAdapter.size();iMainadapter++)
        {
            mergeAdapter.addAdapter(new ListTitleAdapter(getActivity(), mainAdapter.get(iMainadapter).getGroup() , mainAdapter.get(iMainadapter)));
            mergeAdapter.addAdapter(mainAdapter.get(iMainadapter));
        }
        //mergeAdapter.setNoItemsText("Nothing to display. This list is empty.");
        listViewMainAdapter = ((ListView)rootView.findViewById(R.id.listViewMainAdapter));
        listViewMainAdapter.setAdapter(mergeAdapter);
        System.out.println("Main_Fragment_EveryBody --> findeonCreateView");
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Main_Fragment_EveryBody newInstance(int sectionNumber) {
        Main_Fragment_EveryBody fragment = new Main_Fragment_EveryBody();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public Main_Fragment_EveryBody() {
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER),MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1));
        System.out.println("Main_Fragment_EveryBody --> onAttach");
    }

}

