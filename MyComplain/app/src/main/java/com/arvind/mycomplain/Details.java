package com.arvind.mycomplain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

/**
 * Created by arvind on 14/4/16.
 */
public class Details extends AppCompatActivity {
    TextView title, description, upvotes, downvotes;
    ListView comment_list;
    ImageView upvote, downvote, status_icon;
    LinearLayout comment;
    EditText comment_body;
    Button post;
    CardView comment_card;
    PagerAdapter listadapter;
    int id,upvote_number=0,downvote_number,vote=0;
    ArrayList<String> Body = new ArrayList<String>();
    ArrayList<String> Time = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2);
        id = getIntent().getIntExtra("details", 0);
        title = (TextView) findViewById(R.id.details_title);
        description = (TextView) findViewById(R.id.complain_description);
        upvotes = (TextView) findViewById(R.id.upvote_text);
        downvotes = (TextView) findViewById(R.id.downvote_text);
        upvote = (ImageView) findViewById(R.id.upvote_icon);
        downvote = (ImageView) findViewById(R.id.downvote_icon);
        status_icon = (ImageView) findViewById(R.id.status_icon);
        comment = (LinearLayout) findViewById(R.id.cmnt);
        comment_card = (CardView) findViewById(R.id.comment_card);
        comment_body = (EditText) findViewById(R.id.comment_box);
        post = (Button) findViewById(R.id.post);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_card.setVisibility(View.VISIBLE);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        final String a = comment_body.getText().toString();
                                        VolleyRequest request = new VolleyRequest(Request.Method.POST, getString(R.string.common_url)+"/cms/addcomment/", null
                                                , new Response.Listener<String>() {

                                            @Override
                                            public void onResponse(String response) {
                                                //Log.d(Details.class.getSimpleName(),response+"in comment");
                                                Toast.makeText(Details.this, "success", Toast.LENGTH_LONG).show();
                                                try {
                                                    JSONObject obj = new JSONObject(response);
                                                    JSONObject obj1 = obj.getJSONObject("data");
                                                    String user = obj1.getString("user_id");
                                                    String b = obj1.getString("body");
                                                    b = b + "\n\n -" + user;
                                                    String time = obj1.getString("created_at");
                                                    Body.add(b);
                                                    Time.add(time);
                                                    list(Body,Time);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(Details.this, error.toString(), Toast.LENGTH_LONG).show();

                                            }
                                        }) {

                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> params = new HashMap<String, String>();

                                                params.put("id", id+toString());
                                                params.put("body", a);
                                                return params;
                                            }
                                        };
                                        RequestQueue req = Volley.newRequestQueue(Details.this);
                                        req.add(request);
                                        comment_card.setVisibility(View.GONE);
                                        comment_body.setText("");
                                    }
                                }

            );

            downvote.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                downvote.setImageResource(R.drawable.thumbs_down_black);
                upvote.setImageResource(R.drawable.thumbs_up_grey);
                    VolleyRequest request = new VolleyRequest(Request.Method.POST, getString(R.string.common_url) + "/cms/vote/", null,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                if(vote != 2) {
                                    downvote_number++;
                                    downvotes.setText(downvote_number + "");
                                    Log.d(Details.class.getSimpleName(), vote + "votes downvote");

                                    if (vote == 1) {
                                        upvote_number--;

                                        //upvotes.setText(upvote_number+"");
                                    }
                                    vote = 2;
                                    Log.d(Details.class.getSimpleName(), vote + "votes after downvote");
                                    upvotes.setText(upvote_number + "");
                                }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                           // vote = 2;
                            params.put("id", id+toString());
                            params.put("vote", vote+toString());
                            return params;
                        }
                    };
                    RequestQueue req = Volley.newRequestQueue(Details.this);
                    req.add(request);
                }

            }

            );
        upvote.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick (View v){
                                          upvote.setImageResource(R.drawable.thumbs_up_black);
                                          downvote.setImageResource(R.drawable.thumbs_down_grey);
                                          VolleyRequest request = new VolleyRequest(Request.Method.POST, getString(R.string.common_url) + "/cms/vote/", null,
                                                  new Response.Listener<String>() {
                                                      @Override
                                                      public void onResponse(String response) {
                                                          if (vote != 1) {
                                                              upvote_number++;
                                                              upvotes.setText(upvote_number + "");
                                                              Log.d(Details.class.getSimpleName(), vote + "votes upvote");

                                                              if (vote == 2) {
                                                                  downvote_number--;
                                                                  // downvotes.setText(downvote_number+"");

                                                              }
                                                              vote = 1;
                                                              Log.d(Details.class.getSimpleName(), vote + "votes after upvote");

                                                              downvotes.setText(downvote_number + "");
                                                          }
                                                      }
                                                  }, new Response.ErrorListener() {
                                              @Override
                                              public void onErrorResponse(VolleyError error) {

                                              }
                                          }){
                                              @Override
                                              protected Map<String, String> getParams() throws AuthFailureError {
                                                  Map<String, String> params = new HashMap<String, String>();
                                                  //vote = 1;
                                                  params.put("id", id+toString());
                                                  params.put("vote", vote+toString());
                                                  return params;
                                              }
                                          };
                                          RequestQueue req = Volley.newRequestQueue(Details.this);
                                          req.add(request);
                                      }
                                  }

        );
            comment_list=(ListView)

            findViewById(R.id.comment_list);

            String url = getString(R.string.common_url) + "/cms/getcomplaint/?id=" + id;
            VolleyRequest req = new VolleyRequest(Request.Method.GET, url, null,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                Body.clear();
                                Time.clear();
                                JSONObject details = new JSONObject(response);
                                JSONObject data = details.getJSONObject("data");
                                String t = data.getString("title");
                                String location = data.getString("location");
                                String user = data.getString("user_id");
                                String body = data.getString("body");
                                body = body + "\n\n" + user + "   location: " + location;
                                upvote_number = data.getInt("upvote");
                                downvote_number = data.getInt("downvote");
                                int status = data.getInt("status");
                                Log.d(My.class.getSimpleName(), upvote + "i am here" + downvote);

                                title.setText(t);
                                upvotes.setText(upvote_number + "");
                                downvotes.setText(downvote_number + "");
                                description.setText(body);
                                if (status == 1) {
                                    status_icon.setImageResource(R.drawable.resolved);
                                }
                                vote = details.getInt("vote");
                                JSONArray comment_array = details.getJSONArray("comments");
                                for (int i = 0; i < comment_array.length(); i++) {
                                    JSONObject cmntdata = (JSONObject) comment_array.get(i);
                                    String c = cmntdata.getString("body");
                                    String date = cmntdata.getString("created_at");
                                    String User = cmntdata.getString("user_id");
                                    c = c + "\n\n -" +User;
                                    Body.add(c);
                                    Time.add(date);
                                }
                                list(Body,Time);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(My.class.getSimpleName(), "i am here in error");

                            }
                            if (vote == 1) {
                                upvote.setImageResource(R.drawable.thumbs_up_black);
                                downvote.setImageResource(R.drawable.thumbs_down_grey);
                            } else if (vote == 2) {
                                downvote.setImageResource(R.drawable.thumbs_down_black);
                                upvote.setImageResource(R.drawable.thumbs_up_grey);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue sreq = Volley.newRequestQueue(this);
            sreq.add(req);
        }
    public void list(ArrayList<String > body ,ArrayList<String> time){
        comment_list = (ListView)findViewById(R.id.comment_list);
        listadapter = new PagerAdapter(Details.this,time,body);
        comment_list.setAdapter(listadapter);
    }


    }
