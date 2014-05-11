package com.myfriendsexpenses.app.activity.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.myfriendsexpenses.app.R;


public class EasterEgg extends Activity {

    TextView textView1 = null;
    TextView textView2 = null;
    TextView textView3 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg);
        textView1 = (TextView) findViewById(R.id.eastereggtextView);
        textView2 = (TextView) findViewById(R.id.eastereggtextView2);
        textView3 = (TextView) findViewById(R.id.eastereggtextView3);
        textView1.setText("Développé par Vincent");
        textView2.setText("Polytech Nice Sophia - France (ITII P12)");
        textView3.setText("https://github.com/vnct/MyFriendsExpenses");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.easter_egg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
    public void onDestroy() {
        this.finish();
        super.onDestroy();
    }
}
