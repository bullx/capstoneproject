package com.mrk.karan.securityanalyzer;

import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Bull-X on 13-Nov-16.
 */

public class reset extends MainActivity {

    void textview_reset(){
        TextView tv3 = (TextView) findViewById(R.id.app_pol);
        tv3.setText("Polarity ");
        TextView tv4 = (TextView) findViewById(R.id.app_sub);
        tv4.setText("Subjectivity ");
        TextView data_size = (TextView) findViewById(R.id.datasize);
        data_size.setText("DataSize ");
        TextView tv5 = (TextView) findViewById(R.id.pol_excellent_text);
        tv5.setText("Excellent ");
        TextView tv6 = (TextView) findViewById(R.id.pol_good_text);
        tv6.setText("Good ");
        TextView tv7 = (TextView) findViewById(R.id.pol_bad_text);
        tv7.setText("Bad ");
        TextView tv8 = (TextView) findViewById(R.id.pol_worst_text);
        tv8.setText("Worst ");
        TextView tv9 = (TextView) findViewById(R.id.sub_high_text);
        tv9.setText("");
        TextView tv10 = (TextView) findViewById(R.id.sub_med_text);
        tv10.setText("");
        TextView tv11 = (TextView) findViewById(R.id.sub_low_text);
        tv11.setText("");
    }

    void seekbar_reset(){
        polarity = (SeekBar) findViewById(R.id.polarity_seek);
        subjectivity = (SeekBar) findViewById(R.id.subjectivity_seek);
        polarity.setProgress(0);
        subjectivity.setProgress(0);
        pol_excel = (SeekBar) findViewById(R.id.pol_excellent_seek);
        pol_excel.setProgress(0);
        pol_good = (SeekBar) findViewById(R.id.pol_good_seek);
        pol_good.setProgress(0);
        pol_bad = (SeekBar) findViewById(R.id.pol_bad_seek);
        pol_bad.setProgress(0);
        pol_worst = (SeekBar) findViewById(R.id.pol_worst_seek);
        pol_worst.setProgress(0);
    }

}
