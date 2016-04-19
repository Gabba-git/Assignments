package app.gabba.contact;


import android.app.Activity;
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

public class PagerAdapter extends BaseAdapter {
    private Activity activity;
    ArrayList<String> name;
    ArrayList<String> email;
    ArrayList<String> numbers;
    //private static LayoutInflater inflater=null;


    public PagerAdapter(Activity context, ArrayList<String> name, ArrayList<String> email,ArrayList<String> numbers) {
        super();
        this.activity = context;
        this.name = name;
//        Log.d(Lis.class.getSimpleName(), time.get(2) + "loop");

        this.email = email;
        this.numbers = numbers;

    }

    @Override
    public int getCount() {
        return name.size();
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
        TextView Name;
        TextView Email;
        TextView Numbers;
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
            convertView = inflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.Name = (TextView) convertView.findViewById(R.id.Name);
            holder.Email = (TextView) convertView.findViewById(R.id.email);
            holder.Numbers = (TextView) convertView.findViewById(R.id.number);

            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        //Log.d(Lis.class.getSimpleName(),time.get(position)+"bulls"+position);

        holder.Name.setText(name.get(position));
        holder.Email.setText(email.get(position));
        holder.Numbers.setText(numbers.get(position));


        return convertView;
    }
}
