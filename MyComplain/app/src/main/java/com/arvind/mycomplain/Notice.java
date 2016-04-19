package com.arvind.mycomplain;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by arvind on 29/3/16.
 */
public class Notice extends Fragment {

    ListView list;
    ArrayList<String> body = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    PagerAdapter listadapter;
    public Notice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notice, container, false);
        list=(ListView)rootView.findViewById(R.id.notification);
        Toast.makeText(getActivity(),"i am here",Toast.LENGTH_LONG).show();

        VolleyRequest req = new VolleyRequest(Request.Method.GET, getString(R.string.common_url) + "/cms/notifications/", null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray data = obj.getJSONArray("data");
                            for (int i=0; i<data.length(); i++){
                                JSONObject details  = (JSONObject)data.get(i);
                                String Time = details.getString("created_at");
                                String Body = details.getString("body");
                                time.add(i,Time);
                                body.add(i,Body);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        listadapter = new PagerAdapter(getActivity(),time,body);
                        list.setAdapter(listadapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue sreq = Volley.newRequestQueue(getActivity());
        sreq.add(req);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
