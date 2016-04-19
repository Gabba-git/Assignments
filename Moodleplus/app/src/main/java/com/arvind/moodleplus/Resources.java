package com.arvind.moodleplus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Resources extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.course_resources, container, false);

        /*VolleyRequest assignment = new VolleyRequest(Request.Method.GET, "http://192.168.0.114:5000/courses/course.json/" + Crs.Coursecode.get(Crs.Id) + "assignments", null,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(Resources.this, "No Grades are awarded yet!", Toast.LENGTH_LONG).show();
                        Log.d(Resources.class.getSimpleName(),response+"");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(Resources.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        ); */
        //ArrayAdapter hadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,);

        return rootView;
    }
}
