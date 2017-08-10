package org.codeforafrica.citizenreporterandroid.main.sources;

import com.google.gson.annotations.SerializedName;

import org.codeforafrica.citizenreporterandroid.main.models.Assignments;
import org.codeforafrica.citizenreporterandroid.main.utils.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mugiwara_Munyi on 30/07/2017.
 */

public class LocalDataHelper extends SQLiteOpenHelper {
    private Context context;

    public LocalDataHelper(Context context){
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE " + Constants.ASSIGNMENTS_TABLE_NAME
                +"("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_ASSIGNMENT_TITLE + " TEXT,"
                + Constants.KEY_ASSIGNMENT_DESCRIPTION + " TEXT,"
                + Constants.KEY_ASSIGNMENT_MEDIA + " TEXT,"
                + Constants.KEY_ASSIGNMENT_RESPONSES + " TEXT,"
                + Constants.KEY_ASSIGNMENT_AUTHOR + " TEXT,"
                + Constants.KEY_ASSIGNMENT_UPDATED + " LONG,"
                + Constants.KEY_ASSIGNMENT_DEADLINE + " DATE,"
                + Constants.KEY_ASSIGNMENT_LOCATION + " TEXT);";

        db.execSQL(CREATE_ASSIGNMENTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXITS " + Constants.ASSIGNMENTS_TABLE_NAME);
        onCreate(db);
    }

    public List<Assignments> getAssignments(){
            SQLiteDatabase db = this.getReadableDatabase();
            List<Assignments> assignmentsList = new ArrayList<>();
            Cursor cursor = db.query(Constants.ASSIGNMENTS_TABLE_NAME,

                    new String []{
                            Constants.KEY_ASSIGNMENT_TITLE,
                            Constants.KEY_ASSIGNMENT_DESCRIPTION,
                            Constants.KEY_ASSIGNMENT_MEDIA,
                            Constants.KEY_ASSIGNMENT_RESPONSES,
                            Constants.KEY_ASSIGNMENT_AUTHOR,
                            Constants.KEY_ASSIGNMENT_DEADLINE,
                            Constants.KEY_ASSIGNMENT_LOCATION,
                            Constants.KEY_ASSIGNMENT_UPDATED}, null, null,
                    null, null, Constants.KEY_ASSIGNMENT_TITLE + " DESC");
            if(cursor.moveToFirst()){
                do{
                    Assignments assignment = new Assignments();
                    assignment.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_TITLE)));
                    assignment.setDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_DESCRIPTION)));
                    assignment.setRequiredMedia(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_MEDIA)));
                    assignment.setNumberOfResponses(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_RESPONSES)));
                    assignment.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_AUTHOR)));
                    assignment.setDeadline(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_DEADLINE)));
                    assignment.setAssignmentLocation(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_LOCATION)));

                } while(cursor.moveToNext());
            }
            return assignmentsList;
    }

    public void saveAssignment(Assignments assignment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_ASSIGNMENT_TITLE, assignment.getTitle());
        values.put(Constants.KEY_ASSIGNMENT_DESCRIPTION, assignment.getDescription());
        values.put(Constants.KEY_ASSIGNMENT_MEDIA, assignment.getRequiredMedia().toString());
        values.put(Constants.KEY_ASSIGNMENT_RESPONSES, assignment.getNumberOfResponses());
        values.put(Constants.KEY_ASSIGNMENT_AUTHOR, assignment.getAuthor());
        values.put(Constants.KEY_ASSIGNMENT_DEADLINE, assignment.getDeadline());
        values.put(Constants.KEY_ASSIGNMENT_LOCATION, assignment.getAssignmentLocation());
        values.put(Constants.KEY_ASSIGNMENT_UPDATED, java.lang.System.currentTimeMillis());

        // insert row
        db.insert(Constants.ASSIGNMENTS_TABLE_NAME, null, values);
        Log.d("SAVED", "Saved to DB");
    }

    public void bulkSaveStories(List<Assignments> assignments) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Assignments assignment : assignments) {
            saveAssignment(assignment);
        }

    }
    public int getAssignmentsCount(){
        String countQuery = "SELECT * FROM " + Constants.ASSIGNMENTS_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }


}