package com.arvind.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Crs extends AppCompatActivity {
    public static ArrayList Coursecode = new ArrayList();
    public static int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crs);
        String Course_data = getIntent().getStringExtra("course");   /// receive data from the list activity
        ArrayList jsonresponse = new ArrayList();

        int numofcrs=0;

        try {
            JSONObject course_object = new JSONObject(Course_data);

            int cur_sem = course_object.getInt("current_sem");
            int i ;
            //Toast.makeText(Crs.this,cur_sem+"",Toast.LENGTH_LONG).show();
            JSONArray course = course_object.getJSONArray("courses");

            for ( i=0; i<course.length();i++){
                JSONObject courses = (JSONObject)course.get(i);
                 //Log.d(Crs.class.getSimpleName(),courses+"pok");
                String code = courses.getString("code");
                String name = courses.getString("name");
               // Log.d(Crs.class.getSimpleName(), code + "safa");

                    jsonresponse.add(i, code + " : " + name);
                    Coursecode.add(i,code);
               // Log.d(Crs.class.getSimpleName(), jsonresponse.get(i) +"safa");
                String description = courses.getString("description");
                int credits = courses.getInt("credits");
                int id = courses.getInt("id");
                String l_t_p =  courses.getString("l_t_p");
            }
            numofcrs = i;
            JSONObject user = new JSONObject("user");
            String username = user.getString("username");
            String first_name = user.getString("first_name");
            String last_name = user.getString("last_name");
            String entry_no = user.getString("entry_no");
            String registration_id = user.getString("registration_id");
            int ID = user.getInt("id");
            String reset_password_key = user.getString("reset_password_key");
            int type_ = user.getInt("type_");
            String registration_key = user.getString("registration_key");
            String email = user.getString("email");
            int current_year = course_object.getInt("current_year");
          //  Toast.makeText(Crs.this, first_name+"mniun", Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();

        }
       // Log.d(Crs.class.getSimpleName(), jsonresponse.get(0) + "safa");
        ArrayAdapter hadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,jsonresponse);
        ListView mlist = (ListView) findViewById(R.id.lst);
        mlist.setAdapter(hadapter);

        final int finalNumofcrs = numofcrs;
        mlist.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        String click = String.valueOf(parent.getItemAtPosition(position));
                        Id = position;

                        /// intent to start a new activity represting the details of the course

                        final Intent course = new Intent(getApplicationContext(),TabActivity.class);

                        //course.putExtra("Id",position);
                        startActivity(course);
                     /*   String url = "http://192.168.211.102:5000/courses/course.json/"+Coursecode.get(Id)+"/assignments" ;
                        Log.d(Crs.class.getSimpleName(), Coursecode.get(Id) + "fuck"+url);
                        VolleyRequest assignment = new VolleyRequest(Request.Method.GET,url , null,
                                new Response.Listener<String>() {

                                    @Override
                                    public void onResponse(String assignments) {
                                       // course.putExtra("Assignments", assignments);
                                        Log.d(Crs.class.getSimpleName(), Coursecode.get(Id) + "fuck"+assignments);

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Crs.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                            }

                    });
                        RequestQueue request_assignment = Volley.newRequestQueue(Crs.this);
                        request_assignment.add(assignment);
                        //startActivity(course); */
    }
});
    }
}