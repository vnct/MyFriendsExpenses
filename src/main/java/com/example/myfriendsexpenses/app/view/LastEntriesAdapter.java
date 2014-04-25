package com.example.myfriendsexpenses.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Person;

import java.util.List;

/**
 * Created by lapie on 23/04/14.
 */
public class LastEntriesAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    List<Person> personList;

    public LastEntriesAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int i) {
        return personList.get(i);
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
            vue = mInflater.inflate(R.layout.lastentriesbaseadapter, viewGroup, false);
        }
        TextView textViewName = (TextView) vue.findViewById(R.id.textViewLastEntriesName);
        TextView textViewGroup = (TextView) vue.findViewById(R.id.textViewLastEntriesGroup);
        TextView textViewExpense = (TextView) vue.findViewById(R.id.textViewLastEntriesExpenses);
        TextView textViewComment = (TextView) vue.findViewById(R.id.textViewLastEntriesComment);
        textViewName.setText(personList.get(position).get_name() + " - " + personList.get(position).get_phoneNumber());
        textViewComment.setText(personList.get(position).getExpenseList().get(0).getComments());
        textViewExpense.setText(""+personList.get(position).getExpenseList().get(0).getExpenses());
        textViewGroup.setText(personList.get(position).get_groupname().toUpperCase());
        return vue;
    }
    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
