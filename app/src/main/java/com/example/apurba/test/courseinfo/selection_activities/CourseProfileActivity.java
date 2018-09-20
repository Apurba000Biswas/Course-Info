package com.example.apurba.test.courseinfo.selection_activities;

import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;
import com.example.apurba.test.courseinfo.helper_classes.CursorExtractor;

import java.util.Map;

public class CourseProfileActivity extends AppCompatActivity {

    private static final int COURSE_LOADER_ID = 0;
    private String selectedCourseCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cousre_profile);

        setCustomTittle();
        selectedCourseCode = getIntent().getStringExtra("courseCode");
        String selectedCourseName = getIntent().getStringExtra("courseName");
        setAnimationToHeaders(selectedCourseCode, selectedCourseName);

        getSupportLoaderManager().initLoader(COURSE_LOADER_ID
                , null
                , courseLoaderListener);

    }

    private LoaderManager.LoaderCallbacks<Cursor> courseLoaderListener =
            new LoaderManager.LoaderCallbacks<Cursor>() {


                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    String[] projection = {CourseEntry.COLUMN_INSTRUCTOR_ID
                            , CourseEntry.COLUMN_STATUS
                            , CourseEntry.COLUMN_START_TIME
                            , CourseEntry.COLUMN_END_TIME
                            , CourseEntry.COLUMN_OBJECTIVE
                            , CourseEntry.COLUMN_RESULT};
                    String selection = CourseEntry._ID
                            + " LIKE "
                            + "'%"
                            + selectedCourseCode
                            + "%'";
                    return new CursorLoader(CourseProfileActivity.this,
                            CourseEntry.CONTENT_URI,
                            projection,
                            selection,
                            null,
                            null);
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    Map<String, String> courseData = CursorExtractor.getDataFromCursor(data);

                    TextView statusTextView = findViewById(R.id.course_profile_status);
                    TextView startDateTextView = findViewById(R.id.course_profile_start);
                    TextView endDateTextView = findViewById(R.id.course_profile_end);
                    TextView instructorIdTextView = findViewById(R.id.course_profile_instructor_id);
                    //TextView instructorNameTextView = findViewById(R.id.course_profile_instructor_name);
                    TextView resultTextView = findViewById(R.id.course_profile_result);
                    TextView objective = findViewById(R.id.course_profile_objective);

                    statusTextView.setText(CourseEntry.getStatus(Integer.parseInt(courseData.get(CourseEntry.COLUMN_STATUS))));
                    startDateTextView.setText(courseData.get(CourseEntry.COLUMN_START_TIME));
                    endDateTextView.setText(courseData.get(CourseEntry.COLUMN_END_TIME));
                    instructorIdTextView.setText(courseData.get(CourseEntry.COLUMN_INSTRUCTOR_ID));
                    //instructorNameTextView.setText(courseData.get(CourseEntry.));
                    resultTextView.setText(CourseEntry.getResult(Integer.parseInt(courseData.get(CourseEntry.COLUMN_RESULT))));
                    objective.setText(courseData.get(CourseEntry.COLUMN_OBJECTIVE));
                }


                @Override
                public void onLoaderReset(Loader<Cursor> loader) {

                }
            };



    private void setCustomTittle(){
        SpannableString s = new SpannableString("");
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appLevel)),
                0,
                s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setElevation(0);
    }

    private void setAnimationToHeaders(String courseCode, String courseName){
        final View headerHolder = findViewById(R.id.header_holder);
        TextView courseCodeHeader = findViewById(R.id.course_code_header);
        TextView courseNameHeader = findViewById(R.id.course_name_header);
        FloatingActionButton editButton = findViewById(R.id.edit_button);
        final ScrollView mScrollView = findViewById(R.id.scroll_view);

        final Animation headerAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        headerAnimation.setDuration(500);
        headerHolder.startAnimation(headerAnimation);

        Animation headerCodeAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
        headerCodeAnimation.setDuration(700);
        courseCodeHeader.setText(courseCode);
        courseCodeHeader.startAnimation(headerCodeAnimation);

        Animation headerNameAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        headerNameAnimation.setDuration(700);
        courseNameHeader.setText(courseName);
        courseNameHeader.startAnimation(headerNameAnimation);

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
                /*
                if (scrollY>100){
                    if (headerHolder.getVisibility() == View.VISIBLE){
                        headerOutAnimation.setDuration(800);
                        headerHolder.startAnimation(headerOutAnimation);
                        headerHolder.setVisibility(View.GONE);
                    }
                }else if (scrollY < 100){
                    if (headerHolder.getVisibility() == View.GONE){
                        headerHolder.setVisibility(View.VISIBLE);
                        headerOutAnimation.setDuration(800);
                        headerHolder.startAnimation(editAnimation);
                    }
                }*/
            }
        });
    }


}
