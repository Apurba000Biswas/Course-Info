package com.example.apurba.test.courseinfo.helper_classes;

/*
 * Created by Apurba on 9/16/2018.
 */

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.apurba.test.courseinfo.R;
import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;

public class AddCourse {

    private View mAddCourseView;
    private Context mcontext;
    private int status;
    private int result;

    public AddCourse(View addCourseView, Context context){
        mAddCourseView = addCourseView;
        mcontext = context;
        status = CourseEntry.STATUS_UNKNOWN;
        result = CourseEntry.RESULT_NOT_YET;
    }


    public void setUpResultSpinner(){
        Spinner resultSpinner = mAddCourseView.findViewById(R.id.spinner_result);

        ArrayAdapter resultSpinnerAdapter =
                ArrayAdapter.createFromResource(mcontext
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
                result = CourseEntry.RESULT_NOT_YET;
            }
        });
    }







    public void setupStatusSpinner() {
        Spinner statusSpinner= mAddCourseView.findViewById(R.id.spinner_status);
        final View endTimeHolder = mAddCourseView.findViewById(R.id.end_time_holder);
        endTimeHolder.setVisibility(View.GONE);
        final TextView endTimeLevel = mAddCourseView.findViewById(R.id.end_time_level);
        endTimeLevel.setVisibility(View.GONE);
        final View resultHolder = mAddCourseView.findViewById(R.id.result_holder);
        resultHolder.setVisibility(View.GONE);

        ArrayAdapter statusSpinnerAdapter =
                ArrayAdapter.createFromResource(mcontext
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
                    if (selection.equals(mcontext.getString(R.string.status_option_running))) {
                        status = CourseEntry.STATUS_RUNNING;
                        endTimeHolder.setVisibility(View.GONE);
                        resultHolder.setVisibility(View.GONE);
                        endTimeLevel.setVisibility(View.GONE);
                    } else if(selection.equals(mcontext.getString(R.string.status_option_complete))){
                        status = CourseEntry.STATUS_COMPLETE;
                        endTimeHolder.setVisibility(View.VISIBLE);
                        resultHolder.setVisibility(View.VISIBLE);
                        endTimeLevel.setVisibility(View.VISIBLE);

                    } else {
                        status = CourseEntry.STATUS_UNKNOWN;
                        endTimeHolder.setVisibility(View.GONE);
                        resultHolder.setVisibility(View.GONE);
                        endTimeLevel.setVisibility(View.GONE);

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
