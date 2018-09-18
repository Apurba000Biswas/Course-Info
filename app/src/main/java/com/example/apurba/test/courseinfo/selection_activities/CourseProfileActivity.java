package com.example.apurba.test.courseinfo.selection_activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apurba.test.courseinfo.R;

public class CourseProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cousre_profile);

        setCustomTittle();
        setAnimationToHeaders();

    }

    private void setCustomTittle(){
        SpannableString s = new SpannableString("");
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appLevel)),
                0,
                s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setElevation(0);
    }

    private void setAnimationToHeaders(){
        final View headerHolder = findViewById(R.id.header_holder);
        TextView courseCodeHeader = findViewById(R.id.course_code_header);
        TextView courseNameHeder = findViewById(R.id.course_name_header);
        FloatingActionButton editButton = findViewById(R.id.edit_button);
        final ScrollView mScrollView = findViewById(R.id.scroll_view);

        final Animation headerAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        headerAnimation.setDuration(500);
        headerHolder.startAnimation(headerAnimation);

        Animation headerCodeAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
        headerCodeAnimation.setDuration(500);
        courseCodeHeader.startAnimation(headerCodeAnimation);

        Animation headerNameAnimaton = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        headerNameAnimaton.setDuration(500);
        courseNameHeder.startAnimation(headerNameAnimaton);

        final Animation editAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        editAnimation.setDuration(500);
        editButton.startAnimation(editAnimation);


        final Animation headerOutAnimation =
                AnimationUtils.loadAnimation(CourseProfileActivity.this
                        , R.anim.fade_out);
        mScrollView
                .getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = mScrollView.getScrollY();
                if (scrollY>100){
                    if (headerHolder.getVisibility() == View.VISIBLE){
                        headerOutAnimation.setDuration(800);
                        headerHolder.startAnimation(headerOutAnimation);
                        headerHolder.setVisibility(View.GONE);
                    }
                }else {
                    if (headerHolder.getVisibility() == View.GONE){
                        headerHolder.setVisibility(View.VISIBLE);
                        headerOutAnimation.setDuration(800);
                        headerHolder.startAnimation(editAnimation);
                    }
                }
            }
        });
    }


}
