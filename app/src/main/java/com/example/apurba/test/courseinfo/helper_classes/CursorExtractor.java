package com.example.apurba.test.courseinfo.helper_classes;

/*
 * Created by Apurba on 9/20/2018.
 */

import android.database.Cursor;

import java.util.HashMap;
import java.util.Map;

public class CursorExtractor {

    public  static Map getDataFromCursor(Cursor cursor){

        Map<String, String> data = new HashMap<>();
        int runningColumnIndex;
        cursor.moveToFirst();
        for (String column : cursor.getColumnNames()){
            runningColumnIndex = cursor.getColumnIndex(column);
            switch (cursor.getType(runningColumnIndex)) {
                case Cursor.FIELD_TYPE_INTEGER:
                    data.put(column, String.valueOf(cursor.getInt(runningColumnIndex)));
                    break;
                case Cursor.FIELD_TYPE_BLOB:
                    ///put(column, cursor.getBlob(runningColumnIndex));
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    data.put(column, cursor.getString(runningColumnIndex));
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    //put(column, cursor.getFloat(runningColumnIndex));
                    break;
                case Cursor.FIELD_TYPE_NULL:
                    //put(column, false);
                    break;
            }
        }
        return data ;
    }
}
