package com.example.myfriendsexpenses.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Balance;
import com.example.myfriendsexpenses.app.controler.Expense;
import com.example.myfriendsexpenses.app.controler.Person;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lapie on 25/04/14.
 */
public class ExpenseAdapter extends BaseAdapter {
    private List<Person> personList;
    private List<Balance> balanceList;
    private LayoutInflater mInflater;
    private boolean bexpense;
    private Context mContext;
    public ExpenseAdapter(Context context,boolean expense){
        mContext = context;
        bexpense = expense;
        mInflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        if(bexpense) {
            return personList.size();
        }
        else
        {
            return balanceList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if(bexpense) {
            return personList.get(i);
        }
        else
        {
            return balanceList.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View vue = null;
        if(bexpense)
        {
            if(view != null)
            {
                vue = view;
            }
            else
            {
                vue = mInflater.inflate(R.layout.expensesbaseadapter, viewGroup, false);
            }
            TextView textViewName = (TextView) vue.findViewById(R.id.textViewExpenseName);
            TextView textViewValue = (TextView) vue.findViewById(R.id.textViewExpenseValue);
            TextView textViewTitle = (TextView) vue.findViewById(R.id.textViewExpenseTitle);
            textViewName.setText("paid by " + personList.get(position).get_name());
            float v = personList.get(position).getExpenseList().get(0).getExpenses();
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            df.format(v);
            textViewValue.setText(df.format(v) + " €");
            String title = personList.get(position).getExpenseList().get(0).getComments().trim();
            if(title.equals(""))
            {
                title = "Untitled";
            }
            textViewTitle.setText(title);
            return vue;
        }
        else
        {
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
            textViewPayback.setText(df.format(v) + " € to " + balanceList.get(position).getPersonget().get_name());
            textViewGroup.setText(R.string.balancetextpay);
            return vue;
        }


    }

    public boolean getBooleanExpense()
    {
        return bexpense;
    }
    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void setBalanceList(List<Balance> balanceList) {
        this.balanceList = balanceList;
    }
}
