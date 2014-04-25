package com.example.myfriendsexpenses.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Group;
import com.example.myfriendsexpenses.app.controler.MainAdapter;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lapie on 24/04/14.
 */
public class GroupAdapter extends BaseAdapter {
    private List<Group> GroupList;
    private LayoutInflater mInflater;
    private MainAdapter mainAdapter;
    // Si samegroup on calcul les balances
    private Context mContext;

    public GroupAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mainAdapter = new MainAdapter(context);
    }

    @Override
    public int getCount() {
        return GroupList.size();
    }

    @Override
    public Object getItem(int i) {
        return GroupList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vue = null;
        if(view != null)
        {
            vue = view;
        }
        else
        {
            vue = mInflater.inflate(R.layout.groupbaseadapter, viewGroup, false);
        }
        TextView textViewGroupTitle = (TextView) vue.findViewById(R.id.textViewGroupBaseAdapterName);
        TextView textViewGroupTotalExpenses = (TextView) vue.findViewById(R.id.textViewGroupBaseAdapterTotalExpense);
        TextView textViewGroupExpensesPerson = (TextView) vue.findViewById(R.id.textViewGroupBaseAdapterExpensesPerson);
        //ListView listViewperson = (ListView) vue.findViewById(R.id.listViewGroupBaseAdapter);
        textViewGroupTitle.setText(GroupList.get(position).get_name().toUpperCase());
        textViewGroupTotalExpenses.setText(GroupList.get(position).get_totalExpenses()+ " €");
        textViewGroupExpensesPerson.setText(GroupList.get(position).get_expensePerPerson()+ " €");

        mainAdapter.setPersonList(GroupList.get(position).getPersons());
        //System.out.println("J'ai ==> " + GroupList.get(position).getPersons().size());
      //  listViewperson.setAdapter(mainAdapter);


        return vue;
    }

    public void setGroupList(List<Group> groupList) {
        GroupList = groupList;
    }
    public  void setMainAdapter(MainAdapter mainadapter)
    {
        mainAdapter = mainadapter;
    }
}
