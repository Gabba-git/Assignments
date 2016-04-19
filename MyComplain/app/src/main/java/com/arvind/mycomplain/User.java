package com.arvind.mycomplain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arvind on 14/4/16.
 */
public class User extends AppCompatActivity {
    EditText name,email,entry_num,hostel,phone,address,usertype;
    LinearLayout admin;
    Button add,delete,update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1);

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email_profile);
        entry_num = (EditText)findViewById(R.id.entry_num);
        usertype = (EditText)findViewById(R.id.user_type);
        phone = (EditText)findViewById(R.id.phone);
        address = (EditText)findViewById(R.id.address);
        hostel = (EditText)findViewById(R.id.hostel);
        admin = (LinearLayout)findViewById(R.id.admin_panel);
        add = (Button)findViewById(R.id.add_user);
        delete =(Button)findViewById(R.id.delete_user);
        update = (Button)findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String n = name.getText().toString();
                final String[]Name = n.split(" ");
                final String Email = email.getText().toString();
                final String Phone = phone.getText().toString();
                final String Address = address.getText().toString();
                final String Hostel = hostel.getText().toString();
                final String userid = entry_num.getText().toString();
                final String Usertype = usertype.getText().toString();

                VolleyRequest req = new VolleyRequest(Request.Method.POST, getString(R.string.common_url) + "/cms/updateprofile/", null,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(User.this,response.toString(),Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", userid);
                        params.put("first_name", Name[0]);
                        params.put("last_name", Name[1]);
                        params.put("email", Email);
                        params.put("hostel", Hostel);
                        params.put("phone", Phone);
                        params.put("address", Address);
                        params.put("user_type", Usertype);





                        return params;
                    }
                };
                RequestQueue sreq = Volley.newRequestQueue(User.this);
                sreq.add(req);

            }
        });

        String profie = getIntent().getStringExtra("profile");

        try {
            JSONObject data = new JSONObject(MainActivity.res);
            JSONObject obj = data.getJSONObject("data");
           // Toast.makeText(User.this, obj.toString(), Toast.LENGTH_LONG).show();

            String user_id = obj.getString("user_id");
            String first = obj.getString("first_name");
            String last = obj.getString("last_name");
            String Name = first +" "+last;
            String Email = obj.getString("email");
            String User_type = obj.getString("user_type");
            String Hostel = obj.getString("hostel");
            String Phone = obj.getString("phone");
            String Address = obj.getString("address");
            name.setText(Name);
            email.setText(Email);
            entry_num.setText(user_id);
            usertype.setText(User_type);
            phone.setText(Phone);
            hostel.setText(Hostel);
            address.setText(Address);
            if(User_type.equals("Admin")){
                admin.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(User.this, e.toString(), Toast.LENGTH_LONG).show();
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this,Adduser.class);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this,Deleteuser.class);
                startActivity(intent);
            }
        });
    }
}
