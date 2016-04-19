package com.arvind.mycomplain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by arvind on 29/3/16.
 */
public class Setting extends Fragment implements View.OnClickListener {

    private RadioGroup setting , vote;
    private Button button;
    private TextView personal;
    String data="";
    String url;
    Button apply_changes;
    RadioGroup sort,order;
    RadioButton rb1,rb2;
    static String radiotext1,radiotext2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootview = inflater.inflate(R.layout.settings,container,false);
        personal = (TextView)rootview.findViewById(R.id.profile);
        personal.setOnClickListener(this);
        data = MainActivity.res;
        url = getString(R.string.common_url)+"/cms/";
        apply_changes = (Button)rootview.findViewById(R.id.apply_changes);
        sort = (RadioGroup)rootview.findViewById(R.id.radio_setting);
        order = (RadioGroup)rootview.findViewById(R.id.radio_setting2);
        apply_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int select = sort.getCheckedRadioButtonId();
                rb1 = (RadioButton) getActivity().findViewById(select);
                radiotext1 = rb1.getText().toString();
                int select2 = order.getCheckedRadioButtonId();
                rb2 = (RadioButton) getActivity().findViewById(select2);
                radiotext2 = rb2.getText().toString();
                Toast.makeText(getActivity(),radiotext1+"-"+radiotext2,Toast.LENGTH_LONG).show();
            }
        });

        return rootview;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),User.class);
        intent.putExtra("profile",data);
        startActivity(intent);
    }
}
