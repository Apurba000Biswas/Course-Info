package com.example.apurba.test.courseinfo.helper_classes;

/*
 * Created by Apurba on 9/16/2018.
 */

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.apurba.test.courseinfo.R;
import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;

import java.util.ArrayList;
import java.util.List;

public class AddCourse {

    private static final int START_DATE_SPINNER_KEY = 0;
    private static final int END_DATE_SPINNER_KEY = 1;
    private static final int DAY_SPINNER_ID = 100;
    private static final int MONTH_SPINNER_ID = 101;
    private static final int YEAR_SPINNER_ID = 102;

    private View mAddCourseView;
    private Context mcontext;
    private int status;
    private int result;
    private String startDate;
    private String endDate;

    private String startDay;
    private String startMonth;
    private String startyear;
    private String endDay;
    private String endMonth;
    private String endYear;

    public AddCourse(View addCourseView, Context context){
        mAddCourseView = addCourseView;
        mcontext = context;
        status = CourseEntry.STATUS_UNKNOWN;
        result = CourseEntry.RESULT_NOT_YET;
    }


    public void setUpResultSpinner(){
        Spinner resultSpinner = mAddCourseView.findViewById(R.id.spinner_result);

        ArrayAdapter<CharSequence> resultSpinnerAdapter =
                ArrayAdapter.createFromResource(mcontext
                        , R.array.result_option
                        , R.layout.drop_down_item);
        resultSpinnerAdapter.setDropDownViewResource(
                R.layout.drop_down_item_1line);
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

        ArrayAdapter<CharSequence> statusSpinnerAdapter =
                ArrayAdapter.createFromResource(mcontext
                        , R.array.status_option
                        , R.layout.drop_down_item);
        statusSpinnerAdapter.setDropDownViewResource(
                R.layout.drop_down_item_1line);
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



    public void setUpStartDaySpinner(){
        Spinner startDaySpinner = mAddCourseView.findViewById(R.id.start_day_spinner);

        setUpDateSpinner(getList(1, 32)
                , startDaySpinner
                , START_DATE_SPINNER_KEY
                , DAY_SPINNER_ID);

    }

    public void setUpStartMonthSpinner(){
        Spinner startMonthSpinner = mAddCourseView.findViewById(R.id.start_month_spinner);

        setUpDateSpinner(getList(1, 13)
                , startMonthSpinner
                , START_DATE_SPINNER_KEY
                , MONTH_SPINNER_ID);
    }

    public void setUPStartYearSpinner(){
        Spinner startYearSpinner = mAddCourseView.findViewById(R.id.start_year_spinner);

        setUpDateSpinner(getList(2010, 2031)
                , startYearSpinner
                , START_DATE_SPINNER_KEY
                , YEAR_SPINNER_ID);
    }

    public void setUpEndDaySpinner(){
        Spinner endDaySpinner = mAddCourseView.findViewById(R.id.end_day_spinner);

        setUpDateSpinner(getList(1, 32)
                , endDaySpinner
                , END_DATE_SPINNER_KEY
                , DAY_SPINNER_ID);
    }

    public void setUpEndMonthSpinner(){
        Spinner endMonthSpinner = mAddCourseView.findViewById(R.id.end_month_spinner);

        setUpDateSpinner(getList(1, 13)
                , endMonthSpinner
                , END_DATE_SPINNER_KEY
                , MONTH_SPINNER_ID);
    }

    public void setUpEndYearSpinner(){
        Spinner endYearSpinner = mAddCourseView.findViewById(R.id.end_year_spinner);

        setUpDateSpinner(getList(2010, 2031)
                , endYearSpinner
                , END_DATE_SPINNER_KEY
                , YEAR_SPINNER_ID);
    }

    private void setUpDateSpinner(List<String> list
            , Spinner dateSpinner
            , final int key
            , final int sId){
        ArrayAdapter<String> daysSpinnerAdapter =
                new ArrayAdapter<>(mcontext
                        , R.layout.drop_down_item
                        , list);
        daysSpinnerAdapter.setDropDownViewResource(
                R.layout.drop_down_item_1line);

        dateSpinner.setAdapter(daysSpinnerAdapter);
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent
                    , View view
                    , int position
                    , long id) {
                String selection = (String) parent.getItemAtPosition(position);
                addDate(sId, key, selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if (sId != YEAR_SPINNER_ID){
                    addDate(sId, key, "01");
                }else{
                    addDate(sId, key, "2000");
                }
            }
        });
    }
    private void addDate(int id, int key , String selection){

        switch (id){
            case DAY_SPINNER_ID:
                if (key == START_DATE_SPINNER_KEY){
                    startDay = selection;
                }else if (key == END_DATE_SPINNER_KEY){
                    endDay = selection;
                }
                break;
            case MONTH_SPINNER_ID:
                if (key == START_DATE_SPINNER_KEY){
                    startMonth = selection;
                }else if (key == END_DATE_SPINNER_KEY){
                    endMonth = selection;
                }
                break;
            case YEAR_SPINNER_ID:
                if (key == START_DATE_SPINNER_KEY){
                    startyear = selection;
                }else if (key == END_DATE_SPINNER_KEY){
                    endYear = selection;
                }
                break;
        }
    }



    private List<String> getList(int start, int end){
        List<String> days = new ArrayList<>();

        for (int i=start; i<end; i++){
            if (i<10){
                days.add("0" + (i));
            }else {
                days.add("" + (i));
            }
        }
        return days;
    }
}
