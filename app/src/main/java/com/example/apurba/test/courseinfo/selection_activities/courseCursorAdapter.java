package com.example.apurba.test.courseinfo.selection_activities;

/*
 * Created by Apurba on 9/17/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.apurba.test.courseinfo.R;
import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;

public class courseCursorAdapter extends CursorAdapter{

    public courseCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context
            , Cursor cursor
            , ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.course_list_item
                , viewGroup
                , false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView courseCodeText = view.findViewById(R.id.course_code);
        TextView courseNameText = view.findViewById(R.id.course_name);
        TextView instructorIdText = view.findViewById(R.id.instructor_id);
        TextView statusText = view.findViewById(R.id.status);
        TextView startTime = view.findViewById(R.id.start_time);
        TextView endTime = view.findViewById(R.id.end_time);

        int courseCodeColumnIndex = cursor.getColumnIndex(CourseEntry._ID);
        int courseNameColumnIndex = cursor.getColumnIndex(CourseEntry.COLUMN_COURSE_NAME);
        int instructorIdColumnIndex = cursor.getColumnIndex(CourseEntry.COLUMN_INSTRUCTOR_ID);
        int statusColumnIndex = cursor.getColumnIndex(CourseEntry.COLUMN_STATUS);
        int startTimeColumnIndex = cursor.getColumnIndex(CourseEntry.COLUMN_START_TIME);
        int endTimeColumnIndex = cursor.getColumnIndex(CourseEntry.COLUMN_END_TIME);

        String courseCode = cursor.getString(courseCodeColumnIndex);
        String courseName = cursor.getString(courseNameColumnIndex);
        String instructorID = cursor.getString(instructorIdColumnIndex);
        int status = cursor.getInt(statusColumnIndex);
        String startDate = cursor.getString(startTimeColumnIndex);
        String endDate = cursor.getString(endTimeColumnIndex);

        courseCodeText.setText(courseCode);
        courseNameText.setText(courseName);
        instructorIdText.setText(instructorID);
        statusText.setText(getStatus(status));
        startTime.setText(startDate);
        endTime.setText(endDate);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
        view.startAnimation(animation);

    }

    private String getStatus(int status){
        if (status == CourseEntry.STATUS_RUNNING){
            return "Running";
        }else if (status == CourseEntry.STATUS_COMPLETE){
            return "Completed";
        }else{
            return "Unknown";
        }
    }
}
