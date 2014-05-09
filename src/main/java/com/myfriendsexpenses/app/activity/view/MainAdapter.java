package com.myfriendsexpenses.app.activity.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;
import com.myfriendsexpenses.app.activity.classes.Group;
import com.myfriendsexpenses.app.activity.classes.Person;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lapie on 20/04/14
 */
@SuppressWarnings({"unused", "UnusedAssignment", "ConstantConditions", "FieldCanBeLocal"})
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

                textViewGroup.setText(vue.getResources().getString(R.string.mainadapter_is_owed_from) + " " + personList.get(position).get_groupname());
            } else {
                textViewPayback.setTextColor(Color.RED);
                textViewGroup.setText(vue.getResources().getString(R.string.mainadapter_owes_to) + " " + personList.get(position).get_groupname() + "");
            }
            textViewPayback.setText(df.format(v) +  " " +vue.getResources().getString(R.string.EUR));

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
