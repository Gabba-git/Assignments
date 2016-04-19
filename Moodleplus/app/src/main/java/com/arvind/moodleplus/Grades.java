package com.arvind.moodleplus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// fragment class to represent the grades of the particular course

public class Grades extends Fragment {

    String[] gradeobject = new String[1];
    double totalscore = 0;
    double totalweight = 0;
    String display = "";
    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.course_grades, container, false);
        String Url = getString(R.string.common_url)+":5000/courses/course.json/" + Crs.Coursecode.get(Crs.Id) + "/grades";
        final boolean[] got = {false};
        text = (TextView) rootView.findViewById(R.id.gradeView);
      //  Log.d(Grades.class.getSimpleName(), Url + "sucks");

        VolleyRequest grade = new VolleyRequest(Request.Method.GET, Url, null,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //got[0] = true ;
                        //Log.d(Grades.class.getSimpleName(),gradeobject[0] + "sucks");
                        Log.d(Grades.class.getSimpleName(),response+ "scam");
                        try {
                            JSONObject grades = new JSONObject(response);
                            Log.d(Grades.class.getSimpleName(),response+ "first");
                            JSONArray Grades = grades.getJSONArray("grades");
                            Log.d(Grades.class.getSimpleName(), Grades.toString() + "second");
                            for (int i=0; i<Grades.length(); i++) {

                                JSONObject grad = (JSONObject)Grades.get(i);
                                double weightage = grad.getDouble("weightage");
                                int user_id = grad.getInt("user_id");
                                String Name = grad.getString("name");
                                double out_of = grad.getDouble("out_of");
                                int registered_course_id = grad.getInt("registered_course_id");
                                double score = grad.getDouble("score");
                                int ID = grad.getInt("id");
                                double marks = score * weightage/out_of ;
                                String s = String.format("%.2f",marks);    // setting precsion in the total marks scored
                                marks = Double.parseDouble(s);
                                totalscore += marks;
                                totalweight += weightage;
                                //marks.setPrecision(2);
                                display += (i+1)+".  "  +"" + Name +"\n "+"Score : " + score+"/"+out_of+"\n"+"Weight : " +weightage +"\n"+ "Absolute Marks : " +marks +"\n\n";
                            }

                        } catch (JSONException e) {
                            Log.d(Assignments.class.getSimpleName(), "Error Occured" );
                            e.printStackTrace();
                        }
                        if(display.contentEquals("")){
                            text.setText("No Grades are awarded yet!");
                        } else {
                            display += "Total : " + totalscore + "/" + totalweight + "\nTotal Score : " + totalscore + "\nTotal Weight :" + totalweight + "\n";
                            text.setText(display);
                        }

                    }
                }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Error occurred", Toast.LENGTH_LONG).show();
                        }
                    }
                );

        RequestQueue grads = Volley.newRequestQueue(getActivity());
        grads.add(grade);

        /*while ( got[0] == false) {
            ;
        } */
//        Thread timer = new Thread() {
//            public void run() {
//                try {
//                    sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        timer.start();
//                Log.d(Grades.class.getSimpleName(), gradeobject[0] + "ss");

        return rootView;
    }
}
