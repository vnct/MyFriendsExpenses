package com.myfriendsexpenses.app.activity.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.myfriendsexpenses.app.activity.activity.Add;
import com.myfriendsexpenses.app.activity.activity.LastEntries;
import com.myfriendsexpenses.app.activity.activity.MainActivity;
import com.example.myfriendsexpenses.app.R;
import com.myfriendsexpenses.app.activity.classes.Person;
import com.myfriendsexpenses.app.activity.view.ListTitleAdapter;
import com.myfriendsexpenses.app.activity.view.MainAdapter;
import com.myfriendsexpenses.app.activity.view.MergeAdapter;

import java.util.ArrayList;

/**
 * Created by lapie on 24/04/14
 */
@SuppressWarnings({"ConstantConditions", "FieldCanBeLocal"})
public class Main_Fragment_EveryBody extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<MainAdapter> mainAdapter;
    MenuItem menu_edit=null,menu_delete=null,menu_read=null,menu_add=null;
    ListView listViewMergeAdapter = null;
    MergeAdapter mergeAdapter = new MergeAdapter();


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

    public void onResume()
    {
        super.onResume();
        MainActivity.fillDataForm(false);
        mainAdapter = new ArrayList<MainAdapter>();
        mergeAdapter = new MergeAdapter();

        for(int iGroups=0;iGroups<MainActivity.getDataForm().getGroups().size();iGroups++)
        {
            MainAdapter mainAdapter1 = new MainAdapter(getActivity());
            mainAdapter1.setPersonList(MainActivity.getDataForm().getGroups().get(iGroups).getPersons());
            mainAdapter1.setGroup(MainActivity.getDataForm().getGroups().get(iGroups));
            mainAdapter.add(mainAdapter1);

        }
        for (MainAdapter aMainAdapter : mainAdapter) {
            mergeAdapter.addAdapter(new ListTitleAdapter(getActivity(), aMainAdapter.getGroup(), aMainAdapter));
            mergeAdapter.addAdapter(aMainAdapter);
        }
        //System.out.println("mergeAdapter.addAdapter = " + mergeAdapter.getCount());
        mergeAdapter.setNoItemsText(getString(R.string.listview_everybody_message));

        listViewMergeAdapter.setAdapter(mergeAdapter);



    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.menu_main_csvadd:
                Intent AddActivity = new Intent(getActivity(),Add.class);
                startActivity(AddActivity);
                return true;
            case R.id.menu_main_csvread:
                Intent ExpenditureActivity = new Intent(getActivity(),LastEntries.class);
             //   ExpenditureActivity.putExtra("GroupName","Merci");
              //  ExpenditureActivity.putExtra("PersonName","Guillaume");

                startActivity(ExpenditureActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        menu_edit = menu.findItem(R.id.menu_main_group_edit);
        menu_delete = menu.findItem(R.id.menu_main_group_delete);
        menu_read = menu.findItem(R.id.menu_main_csvread);
        menu_add = menu.findItem(R.id.menu_main_csvadd);
        menu_edit.setVisible(false);
        menu_delete.setVisible(false);
        menu_read.setVisible(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
         listViewMergeAdapter =  ((ListView)rootView.findViewById(R.id.listViewMainAdapter));
        listViewMergeAdapter.setOnItemLongClickListener(onItemLongClickListener);
    return rootView;
    }

  private AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
          try {
              Person mypersonAdapter = (Person) adapterView.getItemAtPosition(i);

              Intent ExpenditureActivity = new Intent(getActivity(), LastEntries.class);
              ExpenditureActivity.putExtra("GroupName", mypersonAdapter.get_groupname());
              ExpenditureActivity.putExtra("PersonName", mypersonAdapter.get_name());
              ExpenditureActivity.putExtra("CommentExpense","");
              startActivity(ExpenditureActivity);
              return true;
          }
          catch (Exception ignored)
          {

          }
          return false;
      }
  };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER),MainActivity.getDataForm().getGroupname().get(getArguments().getInt(ARG_SECTION_NUMBER)-1));
    }
}

