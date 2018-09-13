package com.example.apurba.test.courseinfo.selection_activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.example.apurba.test.courseinfo.R;

public class RunningCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_course);

        setCustomTittle();
    }

    private void setCustomTittle(){
        SpannableString s = new SpannableString(getResources().
                getString(R.string.tittle_running_course));
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appLevel)),
                0,
                s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setElevation(10);
    }

}
