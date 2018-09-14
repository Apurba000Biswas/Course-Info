package com.example.apurba.test.courseinfo.database;

/*
 * Created by Apurba on 9/14/2018.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.apurba.test.courseinfo.database.CourseDatabaseContract.*;

public class CourseDatabaseProvider extends ContentProvider{

    private static final int COURSES = 100;
    private static final int COURSE_ID = 101;

    private static final int INSTRUCTORS = 200;
    private static final int INSTRUCTOR_ID = 201;

    private static final int TAKEN = 300;
    private static final int TAKEN_ID = 301;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(CourseDatabaseContract.CONTENT_AUTHORITY,
                CourseDatabaseContract.PATH_COURSE_INFO, COURSES);
        sUriMatcher.addURI(CourseDatabaseContract.CONTENT_AUTHORITY,
                CourseDatabaseContract.PATH_COURSE_INFO + "/#", COURSE_ID);

        sUriMatcher.addURI(CourseDatabaseContract.CONTENT_AUTHORITY,
                CourseDatabaseContract.PATH_INSTRUCTOR_INFO, INSTRUCTORS);
        sUriMatcher.addURI(CourseDatabaseContract.CONTENT_AUTHORITY,
                CourseDatabaseContract.PATH_INSTRUCTOR_INFO + "/#", INSTRUCTOR_ID);

        sUriMatcher.addURI(CourseDatabaseContract.CONTENT_AUTHORITY,
                CourseDatabaseContract.PATH_TAKEN, TAKEN);
        sUriMatcher.addURI(CourseDatabaseContract.CONTENT_AUTHORITY,
                CourseDatabaseContract.PATH_TAKEN + "/#", TAKEN_ID);
    }


    private CourseDatabaseHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new CourseDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri
            , @Nullable String[] projection
            , @Nullable String selection
            , @Nullable String[] selectionArgs
            , @Nullable String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = sUriMatcher.match(uri);
        switch (match){
            case COURSES:
                cursor = db.query(CourseEntry.TABLE_NAME
                        , projection
                        , selection
                        , selectionArgs
                        , null
                        , null
                        , sortOrder);
                break;
            case COURSE_ID:
                selection = CourseEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(CourseEntry.TABLE_NAME
                        , projection
                        , selection
                        , selectionArgs
                        , null
                        , null
                        , sortOrder);
                break;
            case INSTRUCTORS:
                cursor = db.query(InstructorEntry.TABLE_NAME
                        , projection
                        , selection
                        , selectionArgs
                        , null
                        , null
                        , sortOrder);
                break;
            case INSTRUCTOR_ID:
                selection = InstructorEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(InstructorEntry.TABLE_NAME
                        , projection
                        , selection
                        , selectionArgs
                        , null
                        , null
                        , sortOrder);
                break;
            case TAKEN:
                cursor = db.query(TakenEntry.TABLE_NAME
                        , projection
                        , selection
                        , selectionArgs
                        , null
                        , null
                        , sortOrder);
                break;
            case TAKEN_ID:
                selection = TakenEntry.INSTRUCTOR_ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TakenEntry.TABLE_NAME
                        , projection
                        , selection
                        , selectionArgs
                        , null
                        , null
                        , sortOrder);
                break;
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }




    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case COURSES:
                return CourseEntry.CONTENT_LIST_TYPE;
            case COURSE_ID:
                return CourseEntry.CONTENT_ITEM_TYPE;
            case INSTRUCTORS:
                return InstructorEntry.CONTENT_LIST_TYPE;
            case INSTRUCTOR_ID:
                return InstructorEntry.CONTENT_ITEM_TYPE;
            case TAKEN:
                return TakenEntry.CONTENT_LIST_TYPE;
            case TAKEN_ID:
                return TakenEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException
                        ("Unknown URI " + uri + " with match " + match);
        }
    }




    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case COURSES:
                return insertCourse(uri, contentValues);
            case INSTRUCTORS:
                return insertInstructor(uri, contentValues);
            case TAKEN:
                return insertTaken(uri, contentValues);
            default:
                throw new IllegalArgumentException
                        ("Insertion is not supported for " + uri);
        }
    }

    private Uri insertCourse(Uri uri, ContentValues values){

        checkForValidCourseValue(values);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(CourseEntry.TABLE_NAME
                , null
                , values);
        if (id == -1){
            System.out.println("(Course table)Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertInstructor(Uri uri, ContentValues values){

        checkForValidInstructorValue(values);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(InstructorEntry.TABLE_NAME
                , null
                , values);
        if (id == -1){
            System.out.println("(Instructor table)Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertTaken(Uri uri, ContentValues values){

        checkForValidTaken(values);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(TakenEntry.TABLE_NAME, null, values);
        if (id == -1){
            System.out.println("(Taken table)Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }




    @Override
    public int delete(@NonNull Uri uri
            , @Nullable String selection
            , @Nullable String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case COURSES:
                rowsDeleted = db.delete(CourseEntry.TABLE_NAME
                        , selection
                        , selectionArgs);
                break;
            case COURSE_ID:
                selection = CourseEntry._ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(CourseEntry.TABLE_NAME
                        , selection
                        , selectionArgs);
                break;
            case INSTRUCTORS:
                rowsDeleted = db.delete(InstructorEntry.TABLE_NAME
                        , selection
                        , selectionArgs);
                break;
            case INSTRUCTOR_ID:
                selection = InstructorEntry._ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(InstructorEntry.TABLE_NAME
                        , selection
                        , selectionArgs);
                break;
            case TAKEN:
                rowsDeleted = db.delete(TakenEntry.TABLE_NAME
                        , selection
                        , selectionArgs);
                break;
            case TAKEN_ID:
                selection = TakenEntry.INSTRUCTOR_ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(TakenEntry.TABLE_NAME
                        , selection
                        ,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }




    @Override
    public int update(@NonNull Uri uri
            , @Nullable ContentValues contentValues
            , @Nullable String selection
            , @Nullable String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case COURSES:
                return updateCourse(uri
                        , contentValues
                        , selection
                        , selectionArgs);
            case COURSE_ID:
                selection = CourseEntry._ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                return updateCourse(uri
                        , contentValues
                        , selection
                        , selectionArgs);
            case INSTRUCTORS:
                return updateInstructor(uri
                        , contentValues
                        , selection
                        , selectionArgs);
            case INSTRUCTOR_ID:
                selection = InstructorEntry._ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                return updateInstructor(uri
                        , contentValues
                        , selection
                        , selectionArgs);
            case TAKEN:
                return updateTaken(uri
                        , contentValues
                        , selection
                        , selectionArgs);
            case TAKEN_ID:
                selection = TakenEntry.INSTRUCTOR_ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                return updateTaken(uri
                        , contentValues
                        , selection
                        , selectionArgs);
            default:
                throw new IllegalArgumentException
                        ("Update is not supported for " + uri);
        }
    }

    private int updateTaken(Uri uri
            , ContentValues values
            , String selection
            , String[] selectionArgs){
        checkForValidTaken(values);
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(TakenEntry.TABLE_NAME
                , values
                , selection
                , selectionArgs);
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    private void checkForValidTaken(ContentValues values){
        if (values.containsKey(TakenEntry.INSTRUCTOR_ID)){
            String instructorId = values.getAsString(TakenEntry.INSTRUCTOR_ID);
            if (TextUtils.isEmpty(instructorId)){
                throw new IllegalArgumentException
                        ("Taken requires an instructor id");
            }
        }
        if (values.containsKey(TakenEntry.COURSE_ID)){
            String courseId = values.getAsString(TakenEntry.COURSE_ID);
            if (TextUtils.isEmpty(courseId)){
                throw new IllegalArgumentException
                        ("Taken requires a course id");
            }
        }
    }

    private int updateInstructor(Uri uri
            , ContentValues values
            , String selection
            , String[] selectionArgs){
        checkForValidInstructorValue(values);
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(InstructorEntry.TABLE_NAME
                , values
                , selection
                , selectionArgs);
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    private void checkForValidInstructorValue(ContentValues values){
        if (values.containsKey(InstructorEntry._ID)){
            String instructorId = values.getAsString(InstructorEntry._ID);
            if (TextUtils.isEmpty(instructorId)){
                throw new IllegalArgumentException
                        ("Instructor requires a id");
            }
        }
        if (values.containsKey(InstructorEntry.COLUMN_INSTRUCTOR_NAME)){
            String instructorName = values.getAsString(InstructorEntry.COLUMN_INSTRUCTOR_NAME);
            if (TextUtils.isEmpty(instructorName)){
                throw new IllegalArgumentException
                        ("Instructor requires a name");
            }
        }
        if (values.containsKey(InstructorEntry.COLUMN_INSTRUCTOR_DEPT)){
            String instructorDept = values.getAsString(InstructorEntry.COLUMN_INSTRUCTOR_DEPT);
            if (TextUtils.isEmpty(instructorDept)){
                throw new IllegalArgumentException
                        ("Instructor requires a Department");
            }
        }
    }

    private int updateCourse(Uri uri
            , ContentValues values
            , String selection
            , String[] selectionArgs){
        checkForValidCourseValue(values);
        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int rowsUpdated = database.update(CourseEntry.TABLE_NAME
                , values
                , selection
                , selectionArgs);
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

    }

    private void checkForValidCourseValue(ContentValues values){
        if (values.containsKey(CourseEntry._ID)){
            String courseId = values.getAsString(CourseEntry._ID);
            if (TextUtils.isEmpty(courseId)){
                throw new IllegalArgumentException
                        ("Course requires a id");
            }
        }
        if (values.containsKey(CourseEntry.COLUMN_COURSE_NAME)){
            String courseName = values.getAsString(CourseEntry.COLUMN_COURSE_NAME);
            if (TextUtils.isEmpty(courseName)){
                throw new IllegalArgumentException
                        ("Course requires a name");
            }
        }
        if (values.containsKey(CourseEntry.COLUMN_STATUS)){
            Integer status = values.getAsInteger(CourseEntry.COLUMN_STATUS);
            if (status == null || !CourseEntry.isValidStatus(status)){
                throw new IllegalArgumentException
                        ("Course requires a valid status(Running, Complete)");
            }
        }
        if (values.containsKey(CourseEntry.COLUMN_START_TIME)){
            String startTime = values.getAsString(CourseEntry.COLUMN_START_TIME);
            if (TextUtils.isEmpty(startTime)){
                throw  new IllegalArgumentException
                        ("Course requires a start time");
            }
        }
        if (values.containsKey(CourseEntry.COLUMN_INSTRUCTOR_ID)){
            String instructorId = values.getAsString(CourseEntry.COLUMN_INSTRUCTOR_ID);
            if (TextUtils.isEmpty(instructorId)){
                throw new IllegalArgumentException
                        ("Course requires an instructor id");
            }
        }
    }
}
