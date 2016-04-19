package com.arvind.moodleplus;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static int type;
    EditText  ema , pass;
    Button login;
    String email="userid",passwd="password";
    Boolean usr,suc;


    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ema = (EditText) findViewById(R.id.email_id);
        pass = (EditText) findViewById(R.id.passwrd);
        login = (Button) findViewById(R.id.butt);

        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        send();
    }
    public void send(){
       final String Email = ema.getText().toString();
        final String Passwd = pass.getText().toString();

// sending login credentials to the server
        VolleyRequest sreq = new VolleyRequest(Request.Method.POST, getString(R.string.common_url)+":5000/default/login.json",null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                         /*   JSONObject user = obj.getJSONObject("user");
                            String last_name = user.getString("last_name");
                            String resetpasswrd = user.getString("reset_password_key");
                            String res_key = user.getString("registration_key");
                            int id = user.getInt("id");
                            String first_name = user.getString("first_name");
                            String entry_no = user.getString("entry_no");
                            String email = user.getString("email");
                            String username = user.getString("username");
                            String res_id = user.getString("registration_id");
                            String password = user.getString("password");
                            type = user.getInt("type_"); */
                            Boolean success = obj.getBoolean("success");
                            //Toast.makeText(MainActivity.this, success+" vghvh", Toast.LENGTH_LONG).show();
                            if(success == true) {
                                Intent lis = new Intent(getApplicationContext(),Lis.class);
                                lis.putExtra("respon",response);   // new activity started after successfull login
                                startActivity(lis);
                            } else if (success == false){
                                Toast.makeText(MainActivity.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                           //
                        }

                    }
               /*     @Override
                    public void onResponse(JSONObject response) {
                        //JSONObject c = new JSONObject(response);
                        try {
                            Boolean user = response.getBoolean("user");
                            Boolean success = response.getBoolean("success");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } */
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put(email,Email);
                params.put(passwd,Passwd);
                return  params;
            }
        };

        RequestQueue req = Volley.newRequestQueue(this);
        req.add(sreq);
    }
    @Override
    public void onDestroy() {

        super.onDestroy();

    }
}

