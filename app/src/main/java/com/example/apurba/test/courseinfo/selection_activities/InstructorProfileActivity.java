package com.example.apurba.test.courseinfo.selection_activities;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apurba.test.courseinfo.R;
import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;

public class InstructorProfileActivity extends AppCompatActivity {

    private static final int INSTRUCTOR_LOADER_ID = 0;
    private static final int TAKEN_LOADER_ID = 1;

    private String selectedInstructorId;
    private courseIdCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_profile);

        selectedInstructorId = getIntent().getStringExtra("instructorId");
        String instructorName = getIntent().getStringExtra("instructorName");
        setAnimationToHeaders(selectedInstructorId, instructorName);

        ListView takenListView = findViewById(R.id.taken_list_view);
        mAdapter = new courseIdCursorAdapter(this, null);
        takenListView.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(INSTRUCTOR_LOADER_ID
                , null
                , instructorLoaderListener);

        getSupportLoaderManager().initLoader(TAKEN_LOADER_ID
                , null
                , takenLoaderListener);
    }


    private void setAnimationToHeaders(String instructorId
            , String instructorName) {

        TextView instructorIdTextView = findViewById(R.id.instructor_profile_ins_id);
        TextView instructorNameTextView = findViewById(R.id.instructor_profile_ins_name);
        View imageHolder = findViewById(R.id.profile_image_holder);

        Animation headerIdAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_left);
        headerIdAnimation.setDuration(700);
        instructorIdTextView.setText(instructorId);
        instructorIdTextView.startAnimation(headerIdAnimation);

        Animation headerNameAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right);
        headerNameAnimation.setDuration(700);
        instructorNameTextView.setText(instructorName);
        instructorNameTextView.startAnimation(headerNameAnimation);

        final Animation editAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        editAnimation.setDuration(500);
        imageHolder.startAnimation(editAnimation);
    }

    private LoaderManager.LoaderCallbacks<Cursor> takenLoaderListener =
            new LoaderManager.LoaderCallbacks<Cursor>(){

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            String[] projection = {TakenEntry._ID,TakenEntry.COURSE_ID};
            String selection = TakenEntry._ID
                    + " LIKE "
                    + "'%"
                    + selectedInstructorId
                    + "%'";
            return new CursorLoader(InstructorProfileActivity.this,
                    TakenEntry.CONTENT_URI,
                    projection,
                    selection,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.swapCursor(null);
        }
    };

    private LoaderManager.LoaderCallbacks<Cursor> instructorLoaderListener =
            new LoaderManager.LoaderCallbacks<Cursor>() {
                @Override
                public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                    String[] projection = {InstructorEntry.COLUMN_INSTRUCTOR_DEPT
                            , InstructorEntry.COLUMN_INSTRUCTOR_CONTACT};
                    String selection = InstructorEntry._ID
                            + " LIKE "
                            + "'%"
                            + selectedInstructorId
                            + "%'";
                    return new CursorLoader(InstructorProfileActivity.this,
                            InstructorEntry.CONTENT_URI,
                            projection,
                            selection,
                            null,
                            null);
                }

                @Override
                public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                    data.moveToFirst();
                    TextView instructorDeptTextView = findViewById(R.id.instructor_profile_dept);
                    TextView instructorContactTextView = findViewById(R.id.instructor_profile_contact);

                    instructorDeptTextView.setText(data.getString(data.getColumnIndex(InstructorEntry.COLUMN_INSTRUCTOR_DEPT)));
                    instructorContactTextView.setText(data.getString(data.getColumnIndex(InstructorEntry.COLUMN_INSTRUCTOR_CONTACT)));
                }

                @Override
                public void onLoaderReset(Loader<Cursor> loader) {

                }
            };
}
