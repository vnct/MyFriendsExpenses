package com.example.myfriendsexpenses.app.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.MainActivity;
import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Group;
import com.example.myfriendsexpenses.app.controler.Person;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 20/04/14.
 */
public class MainAdapter extends BaseAdapter {

    private List<Person> personList;
    private LayoutInflater mInflater;

    private Group group;
    // Si samegroup on calcul les balances

    private Context mContext;

    public MainAdapter(Context context){
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
            vue = mInflater.inflate(R.layout.mainbaseadapter, viewGroup, false);
        }
        TextView textViewName = (TextView) vue.findViewById(R.id.textViewName);
        TextView textViewPayback = (TextView) vue.findViewById(R.id.textViewPayback);
        TextView textViewGroup = (TextView) vue.findViewById(R.id.textViewGroup);


            textViewName.setText(personList.get(position).get_name());
            // Calcul 0 - la_valeur pour mettre la donnée au bon format
            float v = 0 - personList.get(position).get_payback();
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.format(v);
            if (v <= 0) {
                textViewPayback.setTextColor(Color.parseColor("#088A08"));
                textViewGroup.setText("is owed from " + personList.get(position).get_groupname());
            } else {
                textViewPayback.setTextColor(Color.RED);
                textViewGroup.setText("owes to " + personList.get(position).get_groupname() + "");
            }
            textViewPayback.setText(df.format(v) + " €");

        return vue;

    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}