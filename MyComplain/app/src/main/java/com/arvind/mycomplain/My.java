package com.arvind.mycomplain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
public class My extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private RadioGroup radio;
    private RadioButton rb;
    String cate="",url;
    ListView lview;
    PagerAdapter listadapter;
    ArrayList <String> time = new ArrayList();
    ArrayList <String> title = new ArrayList();
    ArrayList <Integer> complaint_id = new ArrayList<Integer>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_complain, container, false);

        radio = (RadioGroup)view.findViewById(R.id.rdgrp);
        rb = (RadioButton)view.findViewById(R.id.radio1);
        cate = rb.getText().toString();
        Toast.makeText(getActivity(), cate, Toast.LENGTH_LONG).show();

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb = (RadioButton) getActivity().findViewById(checkedId);
                cate = rb.getText().toString();
                url = getString(R.string.common_url)+"/cms/complaints/?user=self&category=" + cate+"&order_by="+Setting.radiotext1+"&order="+Setting.radiotext2;
                list();
            }
        });
        url = getString(R.string.common_url)+"/cms/complaints/?user=self&category="+cate+"&order_by="+Setting.radiotext1+"&order="+Setting.radiotext2;
        list();

        return view;
    }
    public void list(){


        VolleyRequest requ = new VolleyRequest(Request.Method.GET, url, null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

                        time.clear();
                        title.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            String message = obj.getString("message");

                            JSONArray complain = obj.getJSONArray("data");
                            for(int i=0; i<complain.length(); i++) {
                                //Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();

                                JSONObject comp = (JSONObject)complain.get(i);
                                int id = comp.getInt("complaint_id");
                                String tl = comp.getString("title");
                              //  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                String created = comp.getString("created_at");
                                int t = comp.getInt("complaint_id");
                                title.add(i, tl);
                                time.add(i, created);
                                complaint_id.add(i,t);
                                //Toast.makeText(getActivity(), title.get(i)+"data"+time.get(i), Toast.LENGTH_LONG).show();
                             //   Log.d(My.class.getSimpleName(), title.get(i)+"data"+time.get(i));

                            }
                        } catch (JSONException e) {
                            Log.d(My.class.getSimpleName(),"error found");
                           // Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();

                            e.printStackTrace();
                        }
                        lview = (ListView) getActivity().findViewById(R.id.comp);
                        listadapter = new PagerAdapter(getActivity(), time, title);
                        lview.setAdapter(listadapter);
                        lview.setOnItemClickListener(My.this);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
        RequestQueue sreq = Volley.newRequestQueue(getActivity());
        sreq.add(requ);
        //Toast.makeText(getActivity(), title +"data"+time , Toast.LENGTH_LONG).show();

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),Details.class);
        intent.putExtra("details",complaint_id.get(position));
        Log.d(My.class.getSimpleName(),complaint_id.get(position)+"i am here");
        startActivity(intent);
    }
}
