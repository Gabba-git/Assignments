package com.arvind.moodleplus;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by arvind on 19/3/16.
 */

///////////  adapter for custom listview  ////////////////

public class PageAdapter extends BaseAdapter {
    private Activity activity;
    ArrayList<String> time;
    ArrayList<String> description;
    //private static LayoutInflater inflater=null;


    public PageAdapter(Activity context, ArrayList<String> time, ArrayList<String> description) {
        super();
        this.activity = context;
        this.time = time;
//        Log.d(Lis.class.getSimpleName(), time.get(2) + "loop");

        this.description = description;

    }

    @Override
    public int getCount() {
        return time.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder {
        TextView txtViewTime;
        TextView txtViewDescription;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();
       // Log.d(Lis.class.getSimpleName(),time.get(2)+"inflat");

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.list, null);
            holder = new ViewHolder();
            holder.txtViewDescription = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtViewTime = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.d(Lis.class.getSimpleName(),time.get(position)+"bulls"+position);

        holder.txtViewTime.setText(time.get(position));
        holder.txtViewDescription.setText(Html.fromHtml(description.get(position)));

        return convertView;
    }
}
