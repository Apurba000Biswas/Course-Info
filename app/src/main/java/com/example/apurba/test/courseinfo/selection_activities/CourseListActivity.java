package com.example.apurba.test.courseinfo.selection_activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apurba.test.courseinfo.R;
import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;

public class CourseListActivity extends AppCompatActivity {

    private int status;
    private courseCursorAdapter mCursorAdapter;
    private static final int COURSE_LOADER_ID = 0;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_activity);

        int running = getIntent().getIntExtra("running",0);
        int complete = getIntent().getIntExtra("complete", 0);
        int unknown = getIntent().getIntExtra("unknown", 0);
        setActivityStatus(running, complete, unknown);

        ListView courseListView = findViewById(R.id.course_list_view);
        mCursorAdapter = new courseCursorAdapter(this, null);
        courseListView.setAdapter(mCursorAdapter);

        View emptyView = findViewById(R.id.empty_state_view);
        courseListView.setEmptyView(emptyView);
        getSupportLoaderManager().initLoader(COURSE_LOADER_ID
                , null
                , courseLoaderListener);

        setListViewToClickResponse(courseListView);

    }

    private void setActivityStatus(int running
            , int complete
            , int unknown){
        if (running != 0){
            setCustomTittle(getResources().getString(R.string.tittle_running_course)
                    , getResources().getString(R.string.empty_running_course_state));
            status = CourseEntry.STATUS_RUNNING;
        }
        if (complete != 0){
            setCustomTittle(getResources().getString(R.string.tittle_complete_course)
                    , getResources().getString(R.string.empty_complete_course_state));
            status = CourseEntry.STATUS_COMPLETE;
        }
        if (unknown != 0){
            status = CourseEntry.STATUS_UNKNOWN;
        }
    }

    private void setListViewToClickResponse(ListView list){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView
                    , View view
                    , int position
                    , long id) {
                Intent profileIntent = new Intent(CourseListActivity.this
                        , CourseProfileActivity.class);
                TextView courseCodeTextView = view.findViewById(R.id.course_code);
                TextView courseNameTextView = view.findViewById(R.id.course_name);
                TextView instructorIdTextView = view.findViewById(R.id.instructor_id);
                String courseCode = courseCodeTextView.getText().toString();
                String courseName = courseNameTextView.getText().toString();
                String instructorId = instructorIdTextView.getText().toString();

                profileIntent.putExtra("courseCode", courseCode);
                profileIntent.putExtra("courseName", courseName);
                profileIntent.putExtra("instructorId", instructorId);
                startActivity(profileIntent);
            }
        });
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





    private LoaderManager.LoaderCallbacks<Cursor> courseLoaderListener =
            new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            String[] projection = {CourseEntry._ID
                    , CourseEntry.COLUMN_COURSE_NAME
                    , CourseEntry.COLUMN_INSTRUCTOR_ID
                    , CourseEntry.COLUMN_STATUS
                    , CourseEntry.COLUMN_START_TIME
                    , CourseEntry.COLUMN_END_TIME};
            String selection = CourseEntry.COLUMN_STATUS + " = " + status;
            return new CursorLoader(CourseListActivity.this,
                    CourseEntry.CONTENT_URI,
                    projection,
                    selection,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mCursorAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mCursorAdapter.swapCursor(null);
        }
    };

}
