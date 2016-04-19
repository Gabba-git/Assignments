package com.arvind.moodleplus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// grades awarded in overall courses

public class Grade extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        String grades = getIntent().getStringExtra("grades");  //gettting response from the Lis activity
        ArrayList jsonresponse = new ArrayList();
        String display = "";
        JSONObject grade = null;
        text = (TextView) findViewById(R.id.Text);
        try {
            grade = new JSONObject(grades);
            JSONArray course = grade.getJSONArray("courses");
            JSONArray Grades = grade.getJSONArray("grades");

            for (int i=0; i<course.length(); i++) {
                JSONObject courses = (JSONObject)course.get(i);
                String code = courses.getString("code");
                String name = courses.getString("name");
                String description = courses.getString("description");
                int credits = courses.getInt("credits");
                int id = courses.getInt("id");
                String l_t_p =  courses.getString("l_t_p");

                JSONObject grad = (JSONObject)Grades.get(i);
                double weightage = grad.getDouble("weightage");
                int user_id = grad.getInt("user_id");
                String Name = grad.getString("name");
                double out_of = grad.getDouble("out_of");
                int registered_course_id = grad.getInt("registered_course_id");
                double score = grad.getDouble("score");
                int ID = grad.getInt("id");
                double marks = score * weightage/out_of ;
                String s = String.format("%.2f",marks);
                marks = Double.parseDouble(s);
                //marks.setPrecision(2);
                display += (i+1)+".\n" +code + "  " + Name +"\n "+"Score : " + score+"/"+out_of+"\n"+"Weight : " +weightage +"\n"+ "Absolute Marks : " +marks +"\n\n\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

            text.setText(display);
    }
}
