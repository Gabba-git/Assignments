package com.arvind.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    // showing the description of the particular assignment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String description = getIntent().getStringExtra("description");
        String deadline = getIntent().getStringExtra("deadline");
        String created = getIntent().getStringExtra("created");
        String last_day = getIntent().getStringExtra("last_day");
        TextView des,create,dead,last;
        des = (TextView)findViewById(R.id.description);
        dead = (TextView)findViewById(R.id.deadline);
        create = (TextView)findViewById(R.id.created);
        last = (TextView)findViewById(R.id.last_day);

        des.setText(Html.fromHtml(description));
        dead.setText(deadline);
        create.setText(created);
        last.setText(last_day);

    }
}
