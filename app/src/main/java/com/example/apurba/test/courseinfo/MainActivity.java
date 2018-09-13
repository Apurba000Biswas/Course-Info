package com.example.apurba.test.courseinfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apurba.test.courseinfo.selection_activities.*;

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
        final int id = imageView.getId();
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(transitionsContainer);
                if (textView.getVisibility() == View.GONE){
                    textView.setVisibility(View.VISIBLE);
                }else{
                    textView.setVisibility(View.GONE);
                    switch (id){
                        case R.id.new_course_image:
                            showDialog(R.id.new_course_image);
                            break;
                        case R.id.running_course_image:
                            Intent intent_R = new Intent(MainActivity.this,
                                    RunningCourseActivity.class);
                            startActivity(intent_R);
                            break;
                        case R.id.complete_course_image:
                            Intent intent_C = new Intent(MainActivity.this,
                                    CompletedCourseAcitvity.class);
                            startActivity(intent_C);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        LayoutInflater factory = LayoutInflater.from(this);
        switch (id){
            case R.id.new_course_image:
                final View addCourseView = factory.inflate(
                        R.layout.add_new_course_dialog,
                        null);
                return new AlertDialog.Builder(this, R.style.myDialogTheme)
                        .setIcon(R.drawable.ic_new_course)
                        .setTitle(R.string.new_course)
                        .setView(addCourseView)
                        .setPositiveButton("Done", null)
                        .setNegativeButton("Cancel", null)
                        .create();
        }
        return null;
    }

}
