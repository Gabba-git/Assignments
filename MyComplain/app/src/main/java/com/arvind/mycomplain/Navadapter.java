package com.arvind.mycomplain;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by arvind on 29/3/16.
 */
public class Navadapter  extends RecyclerView.Adapter<Navadapter.MyViewHolder> {
    List<Navitems> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public Navadapter(Context context, List<Navitems> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public Navadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Navadapter.MyViewHolder holder, int position) {
        Navitems current = data.get(position);
        holder.title.setText(current.getTitle());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.Title);
        }
    }
}
