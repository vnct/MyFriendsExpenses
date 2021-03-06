package com.myfriendsexpenses.app.activity.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;
import com.myfriendsexpenses.app.activity.classes.Balance;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lapie on 24/04/14
 */
@SuppressWarnings({"unused", "UnusedAssignment", "ConstantConditions", "FieldCanBeLocal"})
public class BalanceAdapter extends BaseAdapter {
    private List<Balance> balanceList;
    private LayoutInflater mInflater;
    private Context mContext;

    public BalanceAdapter(Context context){
        mContext = context;
        boolean expense = true;
        mInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return balanceList.size();
    }

    @Override
    public Object getItem(int i) {
        return balanceList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vue;
        if(view != null)
        {
            vue = view;
        }
        else
        {
            vue = mInflater.inflate(R.layout.balancebaseadapter, viewGroup, false);
        }

        TextView textViewName = (TextView) vue.findViewById(R.id.textViewBalanceName);
        TextView textViewPayback = (TextView) vue.findViewById(R.id.textViewBalancePayback);
        TextView textViewGroup = (TextView) vue.findViewById(R.id.textViewBalanceGroup);
        textViewName.setText(balanceList.get(position).getPersonput().get_name());
        float v = balanceList.get(position).getValue();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.format(v);

        textViewPayback.setText(df.format(v) +  " " + vue.getResources().getString(R.string.EUR) +  " " + vue.getResources().getString(R.string.to) +  " " + balanceList.get(position).getPersonget().get_name());
        textViewGroup.setText(R.string.balancetextpay);
        return vue;
    }
    public void setBalanceList(List<Balance> balanceList) {
        this.balanceList = balanceList;
    }
}
