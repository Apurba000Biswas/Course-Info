package com.example.apurba.test.courseinfo;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewGroup courseStateHolder = findViewById(R.id.course_holder);
        ImageView newCourseImageView = findViewById(R.id.new_course_image);
        TextView newCourseTextView = findViewById(R.id.new_course_text);
        setClickListener(courseStateHolder,
                newCourseImageView,
                newCourseTextView);

        ImageView runningCourseImageView = findViewById(R.id.running_course_image);
        TextView runningCourseTextView = findViewById(R.id.running_course_text);
        setClickListener(courseStateHolder,
                runningCourseImageView,
                runningCourseTextView);

        ImageView completeCourseImageView = findViewById(R.id.complete_course_image);
        TextView completeCourseTextView = findViewById(R.id.complete_course_text);
        setClickListener(courseStateHolder,
                completeCourseImageView,
                completeCourseTextView);

    }

    private void setClickListener(final ViewGroup transitionsContainer,
                                  final View imageView,
                                  final View textView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transitionsContainer);
                if (textView.getVisibility() == View.GONE){
                    textView.setVisibility(View.VISIBLE);
                }else{
                    textView.setVisibility(View.GONE);
                }
            }
        });
    }
}
