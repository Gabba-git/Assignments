package com.arvind.mycomplain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class Navdrawer extends AppCompatActivity implements Navfragment.FragmentDrawerListener{
    private Toolbar tool ;
    private Navfragment drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navdrawer);
        tool = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(tool);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawer = (Navfragment)getSupportFragmentManager().findFragmentById(R.id.fragment);

        drawer.setup(R.id.fragment,(DrawerLayout)findViewById(R.id.drawerlayout),tool);
        drawer.setDrawerListener(this);

        displayView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            signout();
            return true;
        }
        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signout() {
        VolleyRequest req = new VolleyRequest(Request.Method.GET, getString(R.string.common_url) + "/cms/signout", null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Successfully logged out!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Navdrawer.this,MainActivity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue sreq = Volley.newRequestQueue(this);
        sreq.add(req);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new Notice();
                title = getString(R.string.title1);
                break;
            case 1:
                fragment = new Add();
                title = getString(R.string.title2);
                break;
            case 2:
                fragment = new My();
                title = getString(R.string.title3);
                break;
            case 3:
                fragment = new All();
                title = getString(R.string.title4);
                break;
            case 4:
                fragment = new Setting();
                title = getString(R.string.title5);
                break;
            default:
                break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
