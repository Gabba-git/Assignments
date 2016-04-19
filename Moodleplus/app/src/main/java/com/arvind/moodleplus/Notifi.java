package com.arvind.moodleplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
// showing the overall notificatons by the intstrutor or students in the various courses

public class Notifi extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lview;
    PageAdapter listadapter;
    ArrayList <String> time = new ArrayList();
    ArrayList <String> description = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifi);

        String notice = getIntent().getStringExtra("notiArray");
        try {
            JSONObject noti = new JSONObject(notice);
            JSONArray nArray = noti.getJSONArray("notifications");
            for(int i=0; i< nArray.length(); i++){
                JSONObject data = (JSONObject)nArray.get(i);
                String des = data.getString("description");
                String Time = data.getString("created_at");
                description.add(i,des);
                time.add(i, Time);
                Log.d(Lis.class.getSimpleName(), time.get(i) + "atta");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // Log.d(Lis.class.getSimpleName(),time.get(2)+"loop end");

        lview = (ListView) findViewById(R.id.noti);
        listadapter = new PageAdapter(this,time, description);  // custom list adapter

        //System.out.println("adapter => "+lviewAdapter.getCount());

        lview.setAdapter(listadapter);

       lview.setOnItemClickListener( this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
