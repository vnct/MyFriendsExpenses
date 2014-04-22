package com.example.myfriendsexpenses.app.controler;

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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lapie on 20/04/14.
 */
public class MainAdapter extends BaseAdapter {

    private List<Person> personList;
    private LayoutInflater mInflater;
    private Boolean samegroup = false;
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
        float v = personList.get(position).get_payback();
        if (v>0)
            textViewPayback.setTextColor(Color.parseColor("#088A08"));
        else
            textViewPayback.setTextColor(Color.RED);

        if(samegroup.equals(true)==true)
        {
            textViewGroup.setVisibility(View.INVISIBLE);
        }
        {
            textViewGroup.setVisibility(View.VISIBLE);
            textViewGroup.setText("to " +personList.get(position).get_groupname());
        }
        DecimalFormat df = new DecimalFormat ( ) ;
        df.setMaximumFractionDigits ( 2) ;
        df.format(v);
        textViewPayback.setText(df.format(v) + " €");


        return vue;

    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void setSamegroup(Boolean samegroup) {
        this.samegroup = samegroup;
    }
}
