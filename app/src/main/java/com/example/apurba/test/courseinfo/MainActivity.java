package com.example.apurba.test.courseinfo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;
import com.example.apurba.test.courseinfo.selection_activities.*;

public class MainActivity extends AppCompatActivity {

    private int status;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewGroup courseStateHolder = findViewById(R.id.course_holder);
        ImageView newCourseImageView = findViewById(R.id.new_course_image);
        TextView newCourseTextView = findViewById(R.id.new_course_text);
        setOptionClickListener(courseStateHolder,
                newCourseImageView,
                newCourseTextView);

        ImageView runningCourseImageView = findViewById(R.id.running_course_image);
        TextView runningCourseTextView = findViewById(R.id.running_course_text);
        setOptionClickListener(courseStateHolder,
                runningCourseImageView,
                runningCourseTextView);

        ImageView completeCourseImageView = findViewById(R.id.complete_course_image);
        TextView completeCourseTextView = findViewById(R.id.complete_course_text);
        setOptionClickListener(courseStateHolder,
                completeCourseImageView,
                completeCourseTextView);

        Button insertDummyCourse = findViewById(R.id.insert_dummy_course);
        setInsertDummyCourse(insertDummyCourse);

        Button showDummyCourseButt = findViewById(R.id.show_dummy_course);
        TextView dummyCourseTextView = findViewById(R.id.dummy_text);
        setShowDummyCourseButt(showDummyCourseButt, dummyCourseTextView);

        Button deleteDummyCourseButt = findViewById(R.id.delete_dummy_course);
        setDeleteDummyCourseButt(deleteDummyCourseButt);

        Button updateDummyCourseButt = findViewById(R.id.update_dummy_course);
        setUpdateDummyCourseButt(updateDummyCourseButt);


    }

    private void setUpdateDummyCourseButt(Button updateDummyCourseButt){
        updateDummyCourseButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = getUpdatedDummyContentValues();
                StringBuilder selection = new StringBuilder();
                selection.append(CourseEntry._ID);
                selection.append(" = ");
                selection.append("\"");
                selection.append("CSE-207");
                selection.append("\"");
                int updatedRow = getContentResolver().update(CourseEntry.CONTENT_URI
                        , values
                        , selection.toString()
                        , null);
                if (updatedRow == 0){
                    //faild
                    Toast.makeText(MainActivity.this
                            ,"Failed Update Dummy Course"
                            , Toast.LENGTH_SHORT).show();
                }else{
                    //successfull
                    Toast.makeText(MainActivity.this
                            , "Successfully updated Dummy Course"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ContentValues getUpdatedDummyContentValues(){
        ContentValues values = new ContentValues();
        values.put(CourseEntry._ID, "CSE-207");
        values.put(CourseEntry.COLUMN_COURSE_NAME, "Database Lab");
        values.put(CourseEntry.COLUMN_STATUS, CourseEntry.STATUS_RUNNING);
        values.put(CourseEntry.COLUMN_START_TIME, "09-06-2018");
        values.put(CourseEntry.COLUMN_INSTRUCTOR_ID, "MMH");
        values.put(CourseEntry.COLUMN_OBJECTIVE, "To Learn about basic database Lab");
        values.put(CourseEntry.COLUMN_RESULT, CourseEntry.RESULT_NOT_YET);
        return values;
    }

    private void setDeleteDummyCourseButt(Button deleteDummyCourseButt){
        deleteDummyCourseButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder selection = new StringBuilder();
                selection.append(CourseEntry._ID);
                selection.append(" = ");
                selection.append("\"");
                selection.append("CSE-207");
                selection.append("\"");
                int rowsDeletd = getContentResolver().delete(CourseEntry.CONTENT_URI
                        , selection.toString()
                        , null);
                if (rowsDeletd != 0){
                    Toast.makeText(MainActivity.this
                            , "Deleted Successfully"
                            , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this
                            , "Error with Deleting"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setShowDummyCourseButt(Button showDummyCourseButt, final TextView dummyCourseTextView){
        showDummyCourseButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] projection = {CourseEntry._ID
                        , CourseEntry.COLUMN_COURSE_NAME
                        , CourseEntry.COLUMN_STATUS
                        , CourseEntry.COLUMN_START_TIME
                        , CourseEntry.COLUMN_END_TIME
                        , CourseEntry.COLUMN_INSTRUCTOR_ID
                        , CourseEntry.COLUMN_OBJECTIVE
                        , CourseEntry.COLUMN_RESULT};
                Cursor cursor = getContentResolver().query(CourseEntry.CONTENT_URI
                        , projection
                        , null
                        , null
                        , null);
                if (cursor.getCount() != 0){
                    cursor.moveToFirst();
                    String id = cursor.getString(cursor.getColumnIndex(CourseEntry._ID));
                    String name = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_COURSE_NAME));
                    int status = cursor.getInt(cursor.getColumnIndex(CourseEntry.COLUMN_STATUS));
                    String startTime = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_START_TIME));
                    String endTime = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_END_TIME));
                    String instructorId = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_INSTRUCTOR_ID));
                    String objective = cursor.getString(cursor.getColumnIndex(CourseEntry.COLUMN_OBJECTIVE));
                    int result = cursor.getInt(cursor.getColumnIndex(CourseEntry.COLUMN_RESULT));
                    StringBuilder displayText = new StringBuilder();
                    displayText.append(id);
                    displayText.append(" ");
                    displayText.append(name);
                    displayText.append(" ");
                    displayText.append(status);
                    displayText.append(" ");
                    displayText.append(startTime);
                    displayText.append(" ");
                    displayText.append(endTime);
                    displayText.append(" ");
                    displayText.append(instructorId);
                    displayText.append(" ");
                    displayText.append(objective);
                    displayText.append(" ");
                    displayText.append(result);

                    dummyCourseTextView.setText(displayText.toString());
                }else{
                    dummyCourseTextView.setText("Empty");
                }

            }
        });
    }

    private void setInsertDummyCourse(Button insertDummyCourse){
        insertDummyCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = getDummyContentValues();
                Uri responseUri = getContentResolver().insert(CourseEntry.CONTENT_URI, values);
                if (responseUri == null){
                    Toast.makeText(MainActivity.this
                            , "Failed to insert Dummy course"
                            , Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this
                            , "Successful"
                            , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private ContentValues getDummyContentValues(){
        ContentValues values = new ContentValues();
        values.put(CourseEntry._ID, "CSE-207");
        values.put(CourseEntry.COLUMN_COURSE_NAME, "Database");
        values.put(CourseEntry.COLUMN_STATUS, CourseEntry.STATUS_RUNNING);
        values.put(CourseEntry.COLUMN_START_TIME, "09-06-2018");
        values.put(CourseEntry.COLUMN_INSTRUCTOR_ID, "MMH");
        values.put(CourseEntry.COLUMN_OBJECTIVE, "To Learn about basic database");
        values.put(CourseEntry.COLUMN_RESULT, CourseEntry.RESULT_NOT_YET);
        return values;
    }


    private void setOptionClickListener(final ViewGroup transitionsContainer,
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
                status = CourseEntry.STATUS_UNKNOWN;
                result = CourseEntry.RESULT_NOT_YET;

                Spinner statusSpinner= addCourseView.findViewById(R.id.spinner_status);
                EditText endTimeEditText = addCourseView.findViewById(R.id.end_time_edit_text);
                endTimeEditText.setVisibility(View.GONE);
                View resultHolder = addCourseView.findViewById(R.id.result_holder);
                resultHolder.setVisibility(View.GONE);
                setupStatusSpinner(statusSpinner, endTimeEditText, resultHolder);

                Spinner resultSpinner = addCourseView.findViewById(R.id.spinner_result);
                setUpResultSpinner(resultSpinner);

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

    private void setUpResultSpinner(final Spinner resultSpinner){
        ArrayAdapter resultSpinnerAdapter =
                ArrayAdapter.createFromResource(this
                        , R.array.result_option
                        , android.R.layout.simple_spinner_item);
        resultSpinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);
        resultSpinner.setAdapter(resultSpinnerAdapter);

        resultSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent
                    , View view
                    , int position
                    , long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)){
                    switch (selection){
                        case "A+":
                            result = CourseEntry.RESULT_A_PLUS;
                            break;
                        case "A":
                            result = CourseEntry.RESULT_A;
                            break;
                        case "A-":
                            result = CourseEntry.RESULT_A_MINUS;
                            break;
                        case "B+":
                            result = CourseEntry.RESULT_B_PLUS;
                            break;
                        case "B":
                            result = CourseEntry.RESULT_B;
                            break;
                        case "B-":
                            result = CourseEntry.RESULT_B_MINUS;
                            break;
                        case "C+":
                            result = CourseEntry.RESULT_C_PLUS;
                            break;
                        case "C":
                            result = CourseEntry.RESULT_C;
                            break;
                        case "C-":
                            result = CourseEntry.RESULT_C_MINU;
                            break;
                        case "D":
                            result = CourseEntry.RESULT_D;
                            break;
                        case "F":
                            result = CourseEntry.RESULT_F;
                            break;
                        case "Not yet":
                            result = CourseEntry.RESULT_NOT_YET;
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupStatusSpinner(final Spinner statusSpinner
            , final EditText endTimeEditText
            , final View resultHolder) {
        ArrayAdapter statusSpinnerAdapter =
                ArrayAdapter.createFromResource(this
                        , R.array.status_option
                        , android.R.layout.simple_spinner_item);
        statusSpinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line);
        statusSpinner.setAdapter(statusSpinnerAdapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent
                    , View view
                    , int position
                    , long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.status_option_running))) {
                        status = CourseEntry.STATUS_RUNNING;
                        endTimeEditText.setVisibility(View.GONE);
                        resultHolder.setVisibility(View.GONE);
                    } else if(selection.equals(getString(R.string.status_option_complete))){
                        status = CourseEntry.STATUS_COMPLETE;
                        endTimeEditText.setVisibility(View.VISIBLE);
                        resultHolder.setVisibility(View.VISIBLE);
                    } else {
                        status = CourseEntry.STATUS_UNKNOWN;
                        endTimeEditText.setVisibility(View.GONE);
                        resultHolder.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status = CourseEntry.STATUS_UNKNOWN; // Unknown
            }
        });
    }

}
