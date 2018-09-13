package com.example.apurba.test.courseinfo.database;

/*
 * Created by Apurba on 9/13/2018.
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class CourseDatabaseContract {
    private CourseDatabaseContract(){}





    public static final String CONTENT_AUTHORITY = "com.example.apurba.test.courseinfo";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_COURSE_INFO = "course";
    public static final String PATH_INSTRUCTOR_INFO = "instructor";
    public static final String PATH_TAKEN = "taken";


    public static class CourseEntry implements BaseColumns{
        public static final String TABLE_NAME = "course";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COURSE_INFO);

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_COURSE_NAME = "course_name";
        public static final String COLUMN_STATUS = "course_status";
        public static final String COLUMN_START_TIME = "course_start_time";
        public static final String COLUMN_END_TIME = "course_end_time";
        public static final String COLUMN_INSTRUCTOR_ID = "course_instructor_id";
        public static final String COLUMN_OBJECTIVE = "course_objective";
        public static final String COLUMN_RESULT = "course_result";

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/" + CONTENT_AUTHORITY
                        + "/" + PATH_COURSE_INFO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/" + CONTENT_AUTHORITY
                        + "/" + PATH_COURSE_INFO;

        public static final int STATUS_RUNNING = 1;
        public static final int STATUS_COMPLETE = 0;

        public static boolean isValidStatus(int status){
            if (status == STATUS_RUNNING || status == STATUS_COMPLETE){
                return true;
            }
            return false;
        }

        public static final int RESULT_A_PLUS = 1;
        public static final int RESULT_A = 2;
        public static final int RESULT_A_MINUS = 3;
        public static final int RESULT_B_PLUS = 4;
        public static final int RESULT_B = 5;
        public static final int RESULT_B_MINUS = 6;
        public static final int RESULT_C_PLUS = 7;
        public static final int RESULT_C = 8;
        public static final int RESULT_C_MINU = 9;
        public static final int RESULT_D = 10;
        public static final int RESULT_F = 11;
        public static final int RESULT_NOT_YET = 0;

        public static boolean isValidResult(int result){
            if (result == RESULT_A_PLUS
                    || result == RESULT_A
                    || result == RESULT_A_MINUS
                    || result == RESULT_B_PLUS
                    || result == RESULT_B
                    || result == RESULT_B_MINUS
                    || result == RESULT_C_PLUS
                    || result == RESULT_C
                    || result == RESULT_C_MINU
                    || result == RESULT_D
                    || result == RESULT_F
                    || result == RESULT_NOT_YET){
                return true;
            }
            return false;
        }

    }

    public static class InstructorEntry implements BaseColumns{
        public static final String TABLE_NAME = "instructor";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INSTRUCTOR_INFO);

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_INSTRUCTOR_NAME = "instructor_name";
        public static final String COLUMN_INSTRUCTOR_DEPT = "instructor_department";
        public static final String COLUMN_INSTRUCTOR_CONTACT = "instructor_contact";

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/" + CONTENT_AUTHORITY
                        + "/" + PATH_INSTRUCTOR_INFO;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/" + CONTENT_AUTHORITY
                        + "/" + PATH_INSTRUCTOR_INFO;
    }

    public static class TakenEntry implements BaseColumns{
        public static final String TABLE_NAME = "taken";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TAKEN);

        public static final String INSTRUCTOR_ID = "instructor_id";
        public static final String COURSE_ID = "course_id";

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE
                        + "/" + CONTENT_AUTHORITY
                        + "/" + PATH_TAKEN;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE
                        + "/" + CONTENT_AUTHORITY
                        + "/" + PATH_TAKEN;
    }
}
