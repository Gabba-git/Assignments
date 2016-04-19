package com.arvind.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//  showing the details of the students or instrutor

public class Prfle extends AppCompatActivity implements View.OnClickListener {
    EditText f,l,e,u,en,t;
    Button submit;
   // public static int type ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prfle);

        f = (EditText)findViewById(R.id.fnme);

        e = (EditText)findViewById(R.id.eml);
        l = (EditText)findViewById(R.id.lnme);
        u = (EditText)findViewById(R.id.usrnme);
        en = (EditText)findViewById(R.id.Enum);
        t = (EditText)findViewById(R.id.Type);
        submit =(Button)findViewById(R.id.butt);
        submit.setOnClickListener(this);
        String data = getIntent().getStringExtra("respon");
        JSONObject obj = null;
        try {
            obj = new JSONObject(data);
            JSONObject user = obj.getJSONObject("user");
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
            int  type = user.getInt("type_");
            f.setText(first_name);          ///// settext in the respective fields
            l.setText(last_name);
            e.setText(email);
            en.setText(entry_no);
            u.setText(username);
            t.setText(type+"");
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        change();
    }

    private void change() {
        final String fname = f.getText().toString();
        final String lnme = l.getText().toString();
        final String enumb = en.getText().toString();
        final String eml = e.getText().toString();
        final String usrnm = u.getText().toString();
        final String type = t.getText().toString();

        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.common_url)+":5000/user/profile?_next=/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String respns) {
                        Toast.makeText(Prfle.this, "changes saved", Toast.LENGTH_LONG).show();
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Prfle.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }

        ) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("last_name", lnme);
                params.put("first_name", fname);
                params.put("entry_no", enumb);
                params.put("email", eml);
                params.put("username", usrnm);
                params.put("type_", type);
                return params;
            }
        };
        RequestQueue req = Volley.newRequestQueue(this);
        req.add(sreq);
    }
}
