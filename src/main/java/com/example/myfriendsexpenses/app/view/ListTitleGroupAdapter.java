package com.example.myfriendsexpenses.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Group;

import java.text.DecimalFormat;

/**
 * Created by lapie on 24/04/14.
 */
public class ListTitleGroupAdapter extends BaseAdapter {

    private Context context;
    private boolean bexpense;
    private BaseAdapter parentAdapter;
    private LayoutInflater mInflater;

    public ListTitleGroupAdapter(Context c,boolean expense) {
        this(c, null,expense);
    }

    public ListTitleGroupAdapter(Context c, BaseAdapter dependentAdapter,boolean expense) {
        super();
        context = c;
        bexpense=expense;
        mInflater = LayoutInflater.from(c);
        if(dependentAdapter != null){
            parentAdapter = dependentAdapter;
        }
    }

    public int getCount() {
        if(parentAdapter != null)
            if(parentAdapter.getCount() == 0){
                return 0;
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
            vue = mInflater.inflate(R.layout.groupname, parent, false);
        }
        TextView textViewGroupExpenseorBalance = (TextView) vue.findViewById(R.id.textViewGroupExpenseorBalance);
        if(bexpense)
        {
            textViewGroupExpenseorBalance.setText(R.string.grouptextExpenses);
        }
        else
        {
            textViewGroupExpenseorBalance.setText(R.string.grouptextBalancing);
        }
        return vue;
    }
}
