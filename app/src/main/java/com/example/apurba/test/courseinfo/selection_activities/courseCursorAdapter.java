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
import com.example.apurba.test.courseinfo.helper_classes.CursorExtractor;

import java.util.Map;

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
        Map<String, String> courseData = CursorExtractor.getDataFromCursor(cursor);

        TextView courseCodeText = view.findViewById(R.id.course_code);
        TextView courseNameText = view.findViewById(R.id.course_name);
        TextView instructorIdText = view.findViewById(R.id.instructor_id);
        TextView statusText = view.findViewById(R.id.status);
        TextView startTime = view.findViewById(R.id.start_time);
        TextView endTime = view.findViewById(R.id.end_time);

        courseCodeText.setText(courseData.get(CourseEntry._ID));
        courseNameText.setText(courseData.get(CourseEntry.COLUMN_COURSE_NAME));
        instructorIdText.setText(courseData.get(CourseEntry.COLUMN_INSTRUCTOR_ID));
        statusText.setText(CourseEntry.getStatus(Integer.parseInt(courseData.get(CourseEntry.COLUMN_STATUS))));
        startTime.setText(courseData.get(CourseEntry.COLUMN_START_TIME));
        endTime.setText(courseData.get(CourseEntry.COLUMN_END_TIME));

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
        view.startAnimation(animation);

    }
}
