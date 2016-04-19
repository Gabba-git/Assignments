package com.arvind.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Assignments extends Fragment {

   /* int position;
    public Assignments(int pos) {
        this.position = pos;
    } */
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        String Url = getString(R.string.common_url)+":5000/courses/course.json/" + Crs.Coursecode.get(Crs.Id) + "/assignments" ;

        final ArrayList  Name = new ArrayList();
        final ArrayList  Description = new ArrayList();
        final ArrayList  Created = new ArrayList();
        final ArrayList  Last_day = new ArrayList();
        final ArrayList  Deadline = new ArrayList();

        //final ArrayList <JSONObject> assn = new ArrayList<JSONObject>();
      //  final JSONArray[] assignments = new JSONArray[1];
        //Log.d(Resources.class.getSimpleName(), Crs.Coursecode.get(Crs.Id) + "fuck" + Url);

        final View rootView = inflater.inflate(R.layout.course_assignment, container, false);
        final ListView mlist = (ListView) rootView.findViewById(R.id.list);

        final VolleyRequest assignment = new VolleyRequest(Request.Method.GET,Url, null,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // course.putExtra("Assignments", assignments);
                        //ass = response;
                        //Log.d(Resources.class.getSimpleName(), Crs.Coursecode.get(Crs.Id) + "fuck" + response);
                        //  Bundle bundle = new Bundle();
                        // bundle.putString("data", ass);
                        // TabFragment5 obj = new TabFragment5(ass);
                        // obj.setArguments(bundle);
                        //getSupportFragmentManager().beginTransaction().add(android.R.id.content,obj).commit();
                        //Toast.makeText(TabActivity.this, "Success", Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject(response);

                            JSONArray assignments = obj.getJSONArray("assignments");
                            if(assignments.length() == 0){
                                Toast.makeText(getActivity(), "No Assignment has been posted yet!", Toast.LENGTH_LONG).show();
                            }else {
                                for (int i = 0; i < assignments.length(); i++) {
                                    JSONObject details = (JSONObject) assignments.get(i);
                                    //assn.add(i,details);
                                    String name = details.getString("name");
                                    final String description = details.getString("description");
                                    String created = details.getString("created_at");
                                    String last_day = details.getString("late_days_allowed");
                                    String deadline = details.getString("deadline");
                                    Name.add(name);
                                    Description.add(description);
                                    Deadline.add(deadline);
                                    Last_day.add(last_day);
                                    Created.add(created);
                                  //  Log.d(Assignments.class.getSimpleName(), Name.get(i) + "found");

                                    ArrayAdapter hadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, Name);
                                    //Toast.makeText(getActivity(),"Success", Toast.LENGTH_LONG).show();
                                    mlist.setAdapter(hadapter);
                                    mlist.setOnItemClickListener(
                                            new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Intent data = new Intent(getActivity().getApplicationContext(),Details.class);
                                                    data.putExtra("description",Description.get(position).toString());
                                                    data.putExtra("deadline",Deadline.get(position).toString());
                                                    data.putExtra("last_day",Last_day.get(position).toString());
                                                    data.putExtra("created",Created.get(position).toString());
                                                    startActivity(data);
                                                }
                                            });

                                }
                            }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_LONG).show();
            }

        });

        RequestQueue request_assignment = Volley.newRequestQueue(getActivity());
        request_assignment.add(assignment);

       //Log.d(Assignments.class.getSimpleName(),assn.get(0)+"six");


        return rootView;
    }
}
