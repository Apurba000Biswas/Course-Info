package com.example.apurba.test.courseinfo.selection_activities;


import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;


import com.example.apurba.test.courseinfo.R;

public class CourseListActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_activity);

        int running = getIntent().getIntExtra("running",0);
        int complete = getIntent().getIntExtra("complete", 0);
        if (running != 0){
            setCustomTittle(getResources().getString(R.string.tittle_running_course)
                    , getResources().getString(R.string.empty_running_course_state));
        }
        if (complete != 0){
            setCustomTittle(getResources().getString(R.string.tittle_complete_course)
                    , getResources().getString(R.string.empty_complete_course_state));
        }

    }

    private void setCustomTittle(String tittle, String emptyStateMessage){
        SpannableString s = new SpannableString(tittle);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appLevel)),
                0,
                s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setElevation(10);

        TextView emptyState = findViewById(R.id.empty_state_text_view);
        emptyState.setText(emptyStateMessage);
    }

}
