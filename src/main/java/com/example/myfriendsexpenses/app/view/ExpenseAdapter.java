package com.example.myfriendsexpenses.app.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;
import com.example.myfriendsexpenses.app.controler.Expense;
import com.example.myfriendsexpenses.app.controler.Person;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lapie on 25/04/14.
 */
public class ExpenseAdapter extends BaseAdapter {
    private List<Person> personList;
    private LayoutInflater mInflater;
    private Context mContext;
    public ExpenseAdapter(Context context){
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

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

}
