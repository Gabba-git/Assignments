package com.arvind.mycomplain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arvind on 28/3/16.
 */

public class Adduser extends AppCompatActivity {

    EditText entry,email,hostel,usertype;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);

        entry = (EditText)findViewById(R.id.adduser_entry_num);
        email = (EditText)findViewById(R.id.adduser_email);
        hostel = (EditText)findViewById(R.id.adduser_hostel);
        usertype = (EditText)findViewById(R.id.adduser_user_type);
        add = (Button)findViewById(R.id.adduser_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Entry = entry.getText().toString();
                final String Email = email.getText().toString();
                final String Hostel = hostel.getText().toString();
                final String Usertype = usertype.getText().toString();

                VolleyRequest req = new VolleyRequest(Request.Method.POST, getString(R.string.common_url) + "/cms/adduser/", null,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Adduser.this, response, Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", Entry);
                        params.put("email", Email);
                        params.put("hostel", Hostel);
                        params.put("pass", Usertype);

                        return params;
                    }
                };
                RequestQueue sreq = Volley.newRequestQueue(Adduser.this);
                sreq.add(req);
                entry.setText("");
                email.setText("");
                hostel.setText("");
                usertype.setText("");

            }
        });

    }
}
