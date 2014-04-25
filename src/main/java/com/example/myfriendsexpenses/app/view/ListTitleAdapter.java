package com.example.myfriendsexpenses.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Group;

import java.text.DecimalFormat;

/**
 * Created by lapie on 24/04/14.
 */
public class ListTitleAdapter extends BaseAdapter {

    private Context context;
    private Group group;
    private BaseAdapter parentAdapter;
    private LayoutInflater mInflater;

    public ListTitleAdapter(Context c, Group _group) {
        this(c, _group, null);
    }

    public ListTitleAdapter(Context c, Group _group, BaseAdapter dependentAdapter) {
        super();
        context = c;
        group = _group;
        mInflater = LayoutInflater.from(c);
        if(dependentAdapter != null){
            parentAdapter = dependentAdapter;
        }
    }

    public int getCount() {
        if(parentAdapter != null){
            if(parentAdapter.getCount() == 0){
                return 0;
            }
        }
        return 1;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vue = null;
        if(convertView != null)
        {
            vue = convertView;
        }
        else
        {
            vue = mInflater.inflate(R.layout.groupbaseadapter, parent, false);
        }
        TextView textViewGroupTitle = (TextView) vue.findViewById(R.id.textViewGroupBaseAdapterName);
        TextView textViewGroupTotalExpenses = (TextView) vue.findViewById(R.id.textViewGroupBaseAdapterTotalExpense);
        TextView textViewGroupExpensesPerson = (TextView) vue.findViewById(R.id.textViewGroupBaseAdapterExpensesPerson);

        textViewGroupTitle.setText(group.get_name().toUpperCase());
        textViewGroupTotalExpenses.setText(group.get_totalExpenses()+ " €");
        float v = group.get_expensePerPerson();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        textViewGroupExpensesPerson.setText(df.format(v) + " €");

        return vue;
    }
}
