package com.arvind.mycomplain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by arvind on 29/3/16.
 */
public class Add extends Fragment implements View.OnClickListener{
    private EditText title,description,location;
    private Button butt;
    private RadioGroup grp;
    private RadioButton rb;
    String radiotext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.add_complain, container, false);
        title = (EditText)rootview.findViewById(R.id.complain_title);
        description = (EditText)rootview.findViewById(R.id.body);
        location = (EditText)rootview.findViewById(R.id.location);
        butt = (Button)rootview.findViewById(R.id.button);
        grp = (RadioGroup)rootview.findViewById(R.id.radio);

        butt.setOnClickListener(this);
        return rootview;
    }

    @Override
    public void onClick(View v) {
        int select = grp.getCheckedRadioButtonId();
        rb = (RadioButton)getActivity().findViewById(select);
        radiotext = rb.getText().toString();
        //Log.d(Add.class.getSimpleName(),select + "radio id" );
        post_complain();
    }

    private void post_complain() {
        int x=0;
        final String t = title.getText().toString();
        if(t.length()==0)
            title.setError("Invalid");
        else
            x++;
        final String des = description.getText().toString();
        if(des.length()==0)
            description.setError("Invalid");
        else
            x++;
        final String loc = location.getText().toString();
        if(loc.length()==0)
            location.setError("Invalid");
        else
            x++;
        Log.d(Add.class.getSimpleName(), radiotext + "radio id" + t);

        // final String rad = rb.getText().toString();
        if (x ==3) {
            VolleyRequest req = new VolleyRequest(Request.Method.POST, getString(R.string.common_url)+"/cms/addcomplaint/", null,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject obj = new JSONObject(response);
                                String success = obj.getString("success");
                                if (success.contentEquals("1")) {
                                    title.setText("");
                                    description.setText("");
                                    location.setText("");
                                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                  //  Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override

                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("title", t);
                    params.put("location", loc);
                    params.put("category", radiotext);
                    params.put("body", des);

                    return params;
                }

            };

            RequestQueue sreq = Volley.newRequestQueue(getActivity());
            sreq.add(req);
        }
    }
}
