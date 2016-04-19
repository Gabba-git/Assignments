package com.arvind.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

////// activity showing just after login //////

public class Lis extends AppCompatActivity {
    String[] list = {"Notification","Grades","My Courses","Profile","Log out"};
    TextView Txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lis);

        //listview for above titles

        ArrayAdapter hadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,list);
        ListView mlist = (ListView) findViewById(R.id.listView);
        Txt = (TextView) findViewById(R.id.txt1);

        mlist.setAdapter(hadapter);
       // String data = getIntent().getStringExtra("respon");
        //Toast.makeText(Lis.this,data, Toast.LENGTH_LONG).show();
        ///    list item listener on clicking , which sends get request to server to recieve json response
        mlist.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String click = String.valueOf(parent.getItemAtPosition(position));
                        if(position == 0){
                            VolleyRequest notification = new VolleyRequest(Request.Method.GET, getString(R.string.common_url)+":5000/default/notifications.json"
                                    , null, new Response.Listener<String>() {

                                @Override
                                public void onResponse(String respose) {

                                    String notice = "";

                                  //  Log.d(Lis.class.getSimpleName(), respose + "catch");

                                    // converting string to json object

                                    try {
                                        JSONObject notification = new JSONObject(respose);
                                        JSONArray notify = notification.getJSONArray("notifications");

                                        // if no notification has been posted then it toasts otherwise start a new activity showing all the notifications

                                        if(notify.length() == 0) {
                                            notice = "No Notification found yet!";
                                            Toast.makeText(Lis.this, notice, Toast.LENGTH_LONG).show();

                                        }else {
                                            Intent notifi = new Intent(getApplicationContext(),Notifi.class);
                                            notifi.putExtra("notiArray",respose);
                                            startActivity(notifi);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                   /*  Txt.setText("hello");
                                    Lis.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Dialog dialog = new Dialog(Lis.this);

                                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            dialog.setContentView(R.layout.dialogbox);
                                            dialog.findViewById(R.id.txt1);

                                            dialog.setCancelable(true);
                                            dialog.show();
                                        }
                                    }*/
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Lis.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                            RequestQueue reqt = Volley.newRequestQueue(Lis.this);
                            reqt.add(notification);

                        }

                        // Starts a new activity showing grades of the student

                        if(position == 1) {
                            VolleyRequest grades = new VolleyRequest(Request.Method.GET, getString(R.string.common_url)+":5000/default/grades.json", null,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d(Lis.class.getSimpleName(), MainActivity.type + "arbit");
                                            if(MainActivity.type == 1) {
                                                Toast.makeText(Lis.this, "No Grades are awarded yet!", Toast.LENGTH_LONG).show();
                                            } else {
                                                Intent grade = new Intent(getApplicationContext(), Grade.class);
                                                grade.putExtra("grades", response);
                                                startActivity(grade);
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Lis.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                            RequestQueue gradereq = Volley.newRequestQueue(Lis.this);
                            gradereq.add(grades);
                        }

                        // list of courses is showed in a new activity

                        if(position == 2 ) {
                       //     Log.d(Lis.class.getSimpleName(),"arbit");
                            VolleyRequest course = new VolleyRequest(Request.Method.GET, getString(R.string.common_url)+":5000/courses/list.json"
                                    , null, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        //String crs = response.toString();

                                        Intent nxt = new Intent(getApplicationContext(), Crs.class);
                                        nxt.putExtra("course", response);
                                        startActivity(nxt);


                                    //Toast.makeText(Lis.this, response, Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Lis.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                            RequestQueue reqt = Volley.newRequestQueue(Lis.this);
                            reqt.add(course);
                        }
                        if (position == 3) {
                            Intent details = new Intent(getApplicationContext(),Prfle.class);
                            details.putExtra("respon",getIntent().getStringExtra("respon"));
                            startActivity(details);
                        }

                        //  showing the profile of the user

                        if (position == 4) {
                            VolleyRequest course = new VolleyRequest(Request.Method.GET, getString(R.string.common_url)+":5000/default/logout.json"
                                    , null, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //String crs = response.toString();

                                    Intent nxt = new Intent(getApplicationContext(), MainActivity.class);
                                   // nxt.putExtra("course", response);
                                    startActivity(nxt);


                                    //Toast.makeText(Lis.this, response, Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Lis.this, error.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                            RequestQueue reqt = Volley.newRequestQueue(Lis.this);
                            reqt.add(course);
                        }
                    }
                }
        );
    }
}
