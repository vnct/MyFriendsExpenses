package com.example.myfriendsexpenses.app.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.CentralDisplay;
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
public  class Holder_Fragments_Test extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Holder_Fragments_Test newInstance(int sectionNumber) {
        Holder_Fragments_Test fragment = new Holder_Fragments_Test();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public Holder_Fragments_Test() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_central_display, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)) + " -- " + CentralDisplay.getDataForm().getGroups().size());
    //    GroupAdapter groupAdapter = new GroupAdapter(getActivity());
        List<MainAdapter> mainAdapter = new ArrayList<MainAdapter>();

        MergeAdapter mergeAdapter = new MergeAdapter();
        for(int iGroups=0;iGroups<MainActivity.getDataForm().getGroups().size();iGroups++)
        {
            MainAdapter mainAdapter1 = new MainAdapter(getActivity());
            mainAdapter1.setPersonList(MainActivity.getDataForm().getGroups().get(iGroups).getPersons());
            mainAdapter1.setGroup(MainActivity.getDataForm().getGroups().get(iGroups));

            mainAdapter.add(mainAdapter1);
            System.out.println("------------>   mainAdapter.add(mainAdapter1); " + mainAdapter.size());
            System.out.println("------------>   mainAdapter.add(mainAdapter1); " + mainAdapter.get(iGroups).getGroup().get_name());
           // mergeAdapter.addAdapter(new ListTitleAdapter(getActivity(), mainAdapter.get(iGroups).getGroup() , mainAdapter1));
            //ListTitleAdapter listTitleAdapter = new ListTitleAdapter(this.getActivity(), mainAdapter.get(iGroups).getGroup() , mainAdapter1);
           // mergeAdapter.addAdapter(listTitleAdapter);
            mergeAdapter.addAdapter(mainAdapter1);
            System.out.println("------------>  mergeAdapter.addAdapter(new ListTitleAdapter) " + mergeAdapter.getViewTypeCount());

        }
        for(int iMainadapter=0;iMainadapter<mainAdapter.size();iMainadapter++)

        {
         /*   System.out.println("----> mergeAdapter.add");
            System.out.println("--------> " + mainAdapter.get(iMainadapter).getGroup().get_name());
            System.out.println("--------> " + mainAdapter.get(iMainadapter).toString());
            System.out.println("--------> " + getActivity().toString());
            mergeAdapter.addAdapter(new ListTitleAdapter(getActivity(), mainAdapter.get(iMainadapter).getGroup() , mainAdapter.get(iMainadapter)));
            System.out.println("------------>  mergeAdapter.addAdapter(new ListTitleAdapter) " + mergeAdapter.getCount());
            mergeAdapter.addAdapter(mainAdapter.get(iMainadapter));
            System.out.println("------------>  mergeAdapter.addAdapter(mainAdapter.get(iMainadapter)); " + mergeAdapter.getCount());*/
        }
        System.out.println("----> mergeAdapter : " + mergeAdapter.getCount());
       // mergeAdapter.setNoItemsText("Nothing to display. This list is empty.");
        ((ListView)rootView.findViewById(R.id.listViewtoto)).setAdapter(mergeAdapter);
        //listViewtoto.setAdapter(mergeAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((CentralDisplay) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }

}
