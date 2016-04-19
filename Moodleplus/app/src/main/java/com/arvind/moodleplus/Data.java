package com.arvind.moodleplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

// Activity to show the list of the comments on the particular threads

public class Data extends AppCompatActivity implements View.OnClickListener {
    ArrayList comment = new ArrayList();
    String title = "", description, created = "", updated,Comment;
    EditText cmnt;
    int id;
    ListView comment_list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        String data = getIntent().getStringExtra("data");
        cmnt = (EditText) findViewById(R.id.editText);
        TextView Title = (TextView) findViewById(R.id.titleview);
        TextView time = (TextView) findViewById(R.id.time);
        Button post = (Button) findViewById(R.id.post);
        comment_list = (ListView) findViewById(R.id.listView3);

        post.setOnClickListener(this);
        try {
            JSONObject thread = new JSONObject(data);
            title = thread.getString("title");
            description = thread.getString("description");
            created = thread.getString("created_at");
            updated = thread.getString("updated_at");
            id = thread.getInt("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        List();
        Title.setText(Html.fromHtml("<b>"+title+"</b>"+"<p>"+description+"</p>"));
        time.setText("- " + created);

        //String let = id+"";
        //  Toast.makeText(Data.this, data, Toast.LENGTH_LONG).show();

    }
    @Override
    public void onClick(View v) {
        post_comment();
    }

    private void post_comment() {
        Comment = cmnt.getText().toString();
        if(Comment.length()==0){
            cmnt.setError("fill the required field");
        } else {
            VolleyRequest postcmnt = new VolleyRequest(Request.Method.POST, getString(R.string.common_url)+":5000/threads/post_comment.json", null
                    , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(Data.this.getApplication(), "Success", Toast.LENGTH_LONG).show();
                    comment.clear();
                    List();
                    cmnt.setText("");
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Data.this.getApplication(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
            ) {

                @Override

                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("thread_id", id + "");
                    params.put("description", Comment);
                    Log.d(Data.class.getSimpleName(), "check " + Comment);
                    // params.put("title", title);

                    return params;
                }

            };

            RequestQueue req = Volley.newRequestQueue(Data.this);
            req.add(postcmnt);
        }
    }
    public void List(){
        //  Log.d(Threads.class.getSimpleName(), "before request : ".toString());

        VolleyRequest get = new VolleyRequest(Request.Method.GET,getString(R.string.common_url)+":5000/threads/thread.json/"+id,null

                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject thrd = new JSONObject((response));

                    JSONArray cmnts = thrd.getJSONArray("comments");
                    JSONArray user = thrd.getJSONArray("comment_users");
                    for (int i=0 ;i<cmnts.length();i++){
                        JSONObject data = (JSONObject)cmnts.get(i);
                        JSONObject name = (JSONObject)user.get(i);
                        String Titles = data.getString("description");
                        String frst =  name.getString("first_name");
                        String last = name.getString("last_name");
                        String comb = frst+" "+last +"\n -"+Titles;
                        comment.add(comb);
                    }
                }

                catch (JSONException e) {
                    //      Log.d(Threads.class.getSimpleName(), "error occured");

                    e.printStackTrace();
                }
                //   Log.d(Threads.class.getSimpleName(), "after request: "+thread.get(0));
                ArrayAdapter hadapter = new ArrayAdapter<String>(Data.this, android.R.layout.simple_list_item_1, comment);
                comment_list.setAdapter(hadapter);

            }
        },new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Data.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        );
        //  Log.d(Threads.class.getSimpleName(), "end of the func ");
        RequestQueue sreq = Volley.newRequestQueue(Data.this);
        sreq.add(get);

    }
}
