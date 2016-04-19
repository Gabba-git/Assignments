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
public class Deleteuser extends AppCompatActivity {

    EditText entry;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteuser);
        delete = (Button)findViewById(R.id.deleteuser_button);
        entry = (EditText)findViewById(R.id.deleteuser_entry_num);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Entry = entry.getText().toString();

                VolleyRequest req = new VolleyRequest(Request.Method.POST, getString(R.string.common_url) + "/cms/deleteuser/", null,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Deleteuser.this, response, Toast.LENGTH_LONG).show();

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

                        return params;
                    }
                };
                RequestQueue sreq = Volley.newRequestQueue(Deleteuser.this);
                sreq.add(req);
                entry.setText("");
            }
        });

    }
}
