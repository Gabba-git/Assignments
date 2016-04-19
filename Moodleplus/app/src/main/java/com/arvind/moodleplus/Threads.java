package com.arvind.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/// fragment class for posting threads in the particular course

public class Threads extends Fragment implements View.OnClickListener {
    EditText Title,descrip;
    Button button;
    ListView threadlist;
    ArrayList thread = new ArrayList();
    ArrayList thread_details = new ArrayList();

    ////////////     set up the pagelayout  /////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.course_forum, container, false);
        Title = (EditText)rootView.findViewById(R.id.tit);
        descrip = (EditText)rootView.findViewById(R.id.des);
        button = (Button) rootView.findViewById(R.id.butt);
        //final ListView threadlist = (ListView)rootView.findViewById(R.id.listView2);
        threadlist = (ListView)rootView.findViewById(R.id.listView2);

        button.setOnClickListener(this);
        List();
        return rootView;
    }

    /////////////        posting the thread to the sever ////////////

    @Override
    public void onClick(View v) {
        postthread();
    }
int x=0;
    private void postthread() {
        final String title = Title.getText().toString();
        if (title.length() == 0) {
            Title.setError("Invalid");
        } else x++;
        final String description = descrip.getText().toString();
        if (description.length() == 0) {
            descrip.setError("Invalid");
        } else x++;
        if (x == 2) {
            VolleyRequest Thread = new VolleyRequest(Request.Method.POST, getString(R.string.common_url)+":5000/threads/new.json", null
                    , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject obj = new JSONObject(response);
                        Boolean success = obj.getBoolean("success");


                        if (success == true) {
                            thread.clear();
                            List();
                            Title.setText("");
                            descrip.setText("");
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                        } else if (success == false) {
                            Toast.makeText(getActivity(), "Try Again!", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
            ) {

                @Override

                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("course_code", Crs.Coursecode.get(Crs.Id).toString());
                    params.put("description", description);
                    params.put("title", title);

                    return params;
                }

            };

            RequestQueue req = Volley.newRequestQueue(getActivity());
            req.add(Thread);
        }
    }

    //////////     listview to show the all threads posted on the server /////////

    public void List(){
      //  Log.d(Threads.class.getSimpleName(), "before request : ".toString());

        VolleyRequest get = new VolleyRequest(Request.Method.GET,getString(R.string.common_url)+":5000/courses/course.json/"+Crs.Coursecode.get(Crs.Id)+"/threads",null
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject thrd = new JSONObject((response));
                    Log.d(Threads.class.getSimpleName(), "show me: "+response);
                    JSONArray course_thread = thrd.getJSONArray("course_threads");
                    for (int i=0 ;i<course_thread.length();i++){
                        JSONObject data = (JSONObject)course_thread.get(i);
                        thread_details.add(i,data);
                        String Titles = data.getString("title");
                        thread.add(i,Titles);
                    }
                }

                catch (JSONException e) {
              //      Log.d(Threads.class.getSimpleName(), "error occured");

                    e.printStackTrace();
                }
             //   Log.d(Threads.class.getSimpleName(), "after request: "+thread.get(0));

                ArrayAdapter hadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, thread);
                threadlist.setAdapter(hadapter);
                threadlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent data = new Intent(getActivity().getApplicationContext(), com.arvind.moodleplus.Data.class);
                        Log.d(Threads.class.getSimpleName(), "after request: "+position);

                        data.putExtra("data",thread_details.get(position).toString());
                        startActivity(data);
                    }
                });

            }
        },new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        );
      //  Log.d(Threads.class.getSimpleName(), "end of the func ");
        RequestQueue sreq = Volley.newRequestQueue(getActivity());
        sreq.add(get);

    }
}
