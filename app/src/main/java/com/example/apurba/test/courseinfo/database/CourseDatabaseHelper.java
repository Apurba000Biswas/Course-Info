package com.example.apurba.test.courseinfo.database;

/*
 * Created by Apurba on 9/13/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;

public class CourseDatabaseHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = CourseDatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "courseInfo.db";
    private static final  int DATABASE_VERSION = 1;

    public CourseDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String SQL_CREATE_COURSE_TABLE = "CREATE TABLE " + CourseEntry.TABLE_NAME + " ("
                + CourseEntry._ID + " TEXT PRIMARY KEY, "
                + CourseEntry.COLUMN_COURSE_NAME + " TEXT NOT NULL, "
                + CourseEntry.COLUMN_STATUS + " INTEGER NOT NULL, "
                + CourseEntry.COLUMN_START_TIME + " TEXT NOT NULL, "
                + CourseEntry.COLUMN_END_TIME + " TEXT NOT NULL, "
                + CourseEntry.COLUMN_INSTRUCTOR_ID + " TEXT NOT NULL, "
                + CourseEntry.COLUMN_OBJECTIVE + " TEXT, "
                + CourseEntry.COLUMN_RESULT + " INTEGER NOT NULL);";
        System.out.println(SQL_CREATE_COURSE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_COURSE_TABLE);


        String SQL_CREATE_INSTRUCTOR_TABLE = "CREATE TABLE " + InstructorEntry.TABLE_NAME + " ("
                + InstructorEntry._ID + " TEXT PRIMARY KEY, "
                + InstructorEntry.COLUMN_INSTRUCTOR_NAME + " TEXT NOT NULL, "
                + InstructorEntry.COLUMN_INSTRUCTOR_DEPT + " TEXT NOT NULL, "
                + InstructorEntry.COLUMN_INSTRUCTOR_CONTACT + " TEXT);";
        System.out.println(SQL_CREATE_INSTRUCTOR_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_INSTRUCTOR_TABLE);


        String SQL_CREATE_TAKEN_TABLE = "CREATE TABLE " + TakenEntry.TABLE_NAME + " ("
                + TakenEntry.INSTRUCTOR_ID + " TEXT NOT NULL, "
                + TakenEntry.COURSE_ID + " TEXT NOT NULL);";
        System.out.println(SQL_CREATE_TAKEN_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TAKEN_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // nothing
    }
}
