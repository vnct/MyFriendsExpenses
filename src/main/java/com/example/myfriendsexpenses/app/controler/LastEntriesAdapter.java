package com.example.myfriendsexpenses.app.controler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;

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
        TextView textViewName = (TextView) vue.findViewById(R.id.textViewLastEntries);
        textViewName.setText(personList.get(position).get_name() + " - " + personList.get(position).get_phoneNumber()  + " - " + personList.get(position).get_expenses() + " - " + personList.get(position).get_groupname() );



        return vue;
    }
    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
