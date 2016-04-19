package app.gabba.contact;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar tool;
    ViewPager pager;
    PagerTabStrip tab_strp;
    PageAdapter page;
     ArrayList<String> name  = new ArrayList ();
     ArrayList <String> email  = new ArrayList ();
     ArrayList <String> numbers = new ArrayList ();
     ArrayList <location> points =  new ArrayList ();
    ProgressBar spinner;
    String url;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tool = (Toolbar)findViewById(R.id.tool);
        setSupportActionBar(tool);
        tool.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        page = new PageAdapter(getSupportFragmentManager());
        pager = (ViewPager)findViewById(R.id.pager);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        tab_strp = (PagerTabStrip)findViewById(R.id.tab_strip);
        url = "http://private-b08d8d-nikitest.apiary-mock.com/contacts";

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else{
        // pager.setAdapter(page);


            JsonArrayRequest req = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i=0; i<response.length();i++){
                                try {
                                    JSONObject obj = (JSONObject)response.get(i);
                                    Log.d(Tab1.class.getSimpleName(), obj.toString() + "root");
                                    JSONArray contacts = obj.getJSONArray("contacts");
                                    // Log.d(Tab1.class.getSimpleName(),contacts.length().toString()+"root");
                                    //  Toast.makeText(getActivity(),contacts.length()+"",Toast.LENGTH_LONG).show();

                                    for(int j=0; j<contacts.length();j++){
                                        JSONObject data = (JSONObject)contacts.get(j);
                                        String n = "-",e="-";
                                        if(data.has("name" )) {
                                            n = data.getString("name");
                                        }
                                        if(data.has("email")) {
                                            e = data.getString("email");
                                        }
                                        String combined ="-";
                                        if(data.has("phone") && data.has("officePhone")) {
                                            long  phone = data.getLong("phone");
                                            long office = data.getLong("officePhone");
                                            combined = phone +"/"+office;
                                        }
                                        if(data.has("officePhone") && data.has("phone") == false) {
                                            long office = data.getLong("officePhone");
                                            combined = "- /"+office;
                                        }
                                        if(data.has("officePhone")==false && data.has("phone") ) {
                                            long phone = data.getLong("phone");
                                            combined = phone + "/ -";
                                        }double latitude = data.getDouble("latitude");
                                        double longitude = data.getDouble("longitude");

                                        location loc = new location(latitude,longitude);

                                        name.add(j, n);
                                        email.add(j,e);
                                        numbers.add(j,combined);
                                        points.add(j,loc);
                                        // Log.d(Tab1.class.getSimpleName(), email.get(j) + "rent");
                                        // Log.d(Tab1.class.getSimpleName(), numbers.get(j) + "rent");


                                    }

                                } catch (JSONException e) {

                                    Toast.makeText(MainActivity.this, "Fuck That Shit!", Toast.LENGTH_LONG).show();

                                    e.printStackTrace();
                                }


                            }
                            if(name.size()!=0) {
                                spinner.setVisibility(View.GONE);
                                pager.setVisibility(View.VISIBLE);
                                page.setVals(name, email, numbers, points);
                                pager.setAdapter(page);
                            }
                            //  PagerAdapter listadapter = new PagerAdapter(getActivity(),name,email,numbers);
                            // lview.setAdapter(listadapter);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    finish();
                }
            });
            RequestQueue sreq = Volley.newRequestQueue(this);
            sreq.add(req);
        }


       // Bundle send = new Bundle();
      //  Log.d(Tab1.class.getSimpleName(), name.size() + "rent");



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
                JsonArrayRequest req = new JsonArrayRequest(url,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                for(int i=0; i<response.length();i++){
                                    try {
                                        JSONObject obj = (JSONObject)response.get(i);
                                        Log.d(Tab1.class.getSimpleName(), obj.toString() + "root");
                                        JSONArray contacts = obj.getJSONArray("contacts");
                                        // Log.d(Tab1.class.getSimpleName(),contacts.length().toString()+"root");
                                        //  Toast.makeText(getActivity(),contacts.length()+"",Toast.LENGTH_LONG).show();

                                        for(int j=0; j<contacts.length();j++){
                                            JSONObject data = (JSONObject)contacts.get(j);
                                            String n = "-",e="-";
                                            if(data.has("name" )) {
                                                n = data.getString("name");
                                            }
                                            if(data.has("email")) {
                                                e = data.getString("email");
                                            }
                                            String combined ="-";
                                            if(data.has("phone") && data.has("officePhone")) {
                                                long  phone = data.getLong("phone");
                                                long office = data.getLong("officePhone");
                                                combined = phone +"/"+office;
                                            }
                                            if(data.has("officePhone") && data.has("phone") == false) {
                                                long office = data.getLong("officePhone");
                                                combined = "- /"+office;
                                            }
                                            if(data.has("officePhone")==false && data.has("phone") ) {
                                                long phone = data.getLong("phone");
                                                combined = phone + "/ -";
                                            }double latitude = data.getDouble("latitude");
                                            double longitude = data.getDouble("longitude");

                                            location loc = new location(latitude,longitude);

                                            name.add(j, n);
                                            email.add(j,e);
                                            numbers.add(j,combined);
                                            points.add(j,loc);
                                            // Log.d(Tab1.class.getSimpleName(), email.get(j) + "rent");
                                            // Log.d(Tab1.class.getSimpleName(), numbers.get(j) + "rent");


                                        }

                                    } catch (JSONException e) {

                                        Toast.makeText(MainActivity.this, "Fuck That Shit!", Toast.LENGTH_LONG).show();

                                        e.printStackTrace();
                                    }


                                }
                                if(name.size()!=0) {
                                    spinner.setVisibility(View.GONE);
                                    pager.setVisibility(View.VISIBLE);
                                    page.setVals(name, email, numbers, points);
                                    pager.setAdapter(page);
                                }
                                //  PagerAdapter listadapter = new PagerAdapter(getActivity(),name,email,numbers);
                                // lview.setAdapter(listadapter);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                RequestQueue sreq = Volley.newRequestQueue(this);
                sreq.add(req);

            } else {
                Toast.makeText(this, "Read Contacts permission denied", Toast.LENGTH_SHORT).show();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    class location {
        double latitude;
        double longitude;

        public location (double h , double k){
            this.latitude = h;
            this.longitude = k;
        }
    }


    //public void send()
}
