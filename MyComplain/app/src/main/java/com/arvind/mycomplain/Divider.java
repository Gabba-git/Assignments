package com.arvind.mycomplain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by arvind on 2/4/16.
 */
public class Divider extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable mDivider;
    private int orient;

    public Divider (Context context,int orientation){
        if(orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST){
            throw new IllegalArgumentException("Invalid Orientation");
        }
        orient = orientation;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (orient == VERTICAL_LIST)
            drawVertical(c, parent);
        else
            drawHorizontal(c,parent);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {

    }

    private void drawVertical(Canvas c, RecyclerView parent) {

    }

}
