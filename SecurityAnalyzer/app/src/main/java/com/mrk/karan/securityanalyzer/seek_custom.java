package com.mrk.karan.securityanalyzer;

/**
 * Created by Bull-X on 14-Nov-16.
 */

import java.util.ArrayList;

import android.graphics.*;
import android.widget.SeekBar;
import android.util.AttributeSet;
import android.content.Context;

public class seek_custom extends SeekBar {

    ArrayList<CustomProgress> list = new ArrayList<>(4);
    int canvasWidth =0;
    int canvasHeight =0;
    int offset=0;
    int lastProgress= 0;
    int progress_size = 100;
    int currProgess=0;
    int resize=0;
    Rect r=null;
    CustomProgress cp = null;
    Paint newpaint =null;
    public seek_custom(Context cont, AttributeSet att) {
        super(cont, att);
    }
    public seek_custom(Context cont, AttributeSet att, int defStyle) {
        super(cont, att, defStyle);
    }
    public seek_custom(Context cont) {
        super(cont);
    }
    public void set(ArrayList<CustomProgress> al) {
        this.list = al;
    }



    protected void onDraw(Canvas canvas) {
        if (list.size() > 0) {
            lastProgress = 0;
            int h=0;
            while (h < list.size()) {
                cp = list.get(h);
                currProgess = (int) (cp.completed * getWidth()/ progress_size);
                resize = lastProgress+ currProgess;
                resize = resize_bar(h,list ,resize,getWidth());
                canvas.drawRect(setnew_rectangle(), setpaint_new());
                lastProgress = resize;
                h++;
            }
            super.onDraw(canvas);
        }

    }

    private Rect setnew_rectangle() {
        r = new Rect();
        r.set(lastProgress, getThumbOffset() / 2,
                resize, getHeight() - offset / 2);
        return r;
    }

    private Paint setpaint_new(){
        newpaint = new Paint();
        newpaint.setColor(getResources().getColor(
                cp.color));
        return newpaint;
    }
    private  int resize_bar(int i, ArrayList<CustomProgress> list, int resize, int canvasWidth){
        if (i == list.size() - 1
                && resize != canvasWidth) {
            resize=canvasWidth;
        }
        return resize;
    }
    @Override
    protected synchronized void onMeasure(int set_width,
                                          int set_height) {

        super.onMeasure(set_width, set_height);
    }

}