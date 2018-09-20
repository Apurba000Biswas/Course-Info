package com.example.apurba.test.courseinfo.selection_activities;

/*
 * Created by Apurba on 9/20/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.apurba.test.courseinfo.R;
import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;

public class courseIdCursorAdapter extends CursorAdapter {

    public courseIdCursorAdapter(Context context, Cursor c){
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.course_id_list_item
                , viewGroup
                , false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String courseCode = cursor.getString(cursor.getColumnIndex(TakenEntry.COURSE_ID));

        TextView courseCodeTextView = view.findViewById(R.id.course_code_text_view);
        courseCodeTextView.setText(courseCode);
    }


}
