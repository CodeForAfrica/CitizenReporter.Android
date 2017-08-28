package org.codeforafrica.citizenreporterandroid.data.sources;

import org.codeforafrica.citizenreporterandroid.data.models.Assignment;
import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.utils.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
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

        String CREATE_STORIES_TABLE = "CREATE TABLE " + Constants.STORIES_TABLE_NAME
                + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_TITLE + " TEXT,"
                + Constants.KEY_ASSIGNMENT_ID + " TEXT,"
                + Constants.KEY_WHO + " TEXT,"
                + Constants.KEY_AUTHOR + " TEXT,"
                + Constants.KEY_AUTHOR_ID + " TEXT,"
                + Constants.KEY_WHEN + " LONG,"
                + Constants.KEY_UPDATED + " LONG,"
                + Constants.KEY_MEDIA + " TEXT,"
                + Constants.KEY_UPLOADED + " TEXT,"
                + Constants.KEY_WHERE + " TEXT,"
                + Constants.KEY_WHY + " TEXT);";

        db.execSQL(CREATE_STORIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXITS " + Constants.ASSIGNMENTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXITS " + Constants.STORIES_TABLE_NAME);
        onCreate(db);

    }

    public List<Assignment> getAssignments(){
            SQLiteDatabase db = this.getReadableDatabase();
            List<Assignment> assignmentsList = new ArrayList<>();
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
                    Assignment assignment = new Assignment();
                    assignment.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_TITLE)));
                    assignment.setDescription(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_DESCRIPTION)));
                    assignment.setRequiredMedia(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_MEDIA)));
                    assignment.setNumberOfResponses(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_RESPONSES)));
                    assignment.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_AUTHOR)));
                    assignment.setDeadline(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_DEADLINE)));
                    assignment.setAssignmentLocation(cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_LOCATION)));

                    assignmentsList.add(assignment);
                } while(cursor.moveToNext());
            }
            return assignmentsList;
    }

    public void saveAssignment(Assignment assignment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_ASSIGNMENT_TITLE, assignment.getTitle());
        values.put(Constants.KEY_ASSIGNMENT_DESCRIPTION, assignment.getDescription());
        values.put(Constants.KEY_ASSIGNMENT_MEDIA, assignment.getRequiredMedia());
        values.put(Constants.KEY_ASSIGNMENT_RESPONSES, assignment.getNumberOfResponses());
        values.put(Constants.KEY_ASSIGNMENT_AUTHOR, assignment.getAuthor());
        values.put(Constants.KEY_ASSIGNMENT_DEADLINE, assignment.getDeadline());
        values.put(Constants.KEY_ASSIGNMENT_LOCATION, assignment.getAssignmentLocation());
        values.put(Constants.KEY_ASSIGNMENT_UPDATED, java.lang.System.currentTimeMillis());

        // insert row
        db.insert(Constants.ASSIGNMENTS_TABLE_NAME, null, values);
        Log.d("SAVED", "Saved to DB");
    }

    public void bulkSaveAssignments(List<Assignment> assignments) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Assignment assignment : assignments) {
            saveAssignment(assignment);
        }

    }
    public int getAssignmentsCount(){
        String countQuery = "SELECT * FROM " + Constants.ASSIGNMENTS_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    public Assignment getAssignment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
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

        if (cursor != null)
            cursor.moveToFirst();
        Assignment assignment = new Assignment();
        assignment.setTitle(
                cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_TITLE)));
        assignment.setDescription(
                cursor.getString(
                        cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_DESCRIPTION)));
        assignment.setRequiredMedia(
                cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_MEDIA)));
        assignment.setNumberOfResponses(
                cursor.getInt(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_RESPONSES)));
        assignment.setAuthor(
                cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_AUTHOR)));
        assignment.setDeadline(
                cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_DEADLINE)));
        assignment.setAssignmentLocation(
                cursor.getString(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_LOCATION)));

        return assignment;
    }

    public long saveStory(Story story){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TITLE, story.getTitle());
        values.put(Constants.KEY_WHY, story.getSummary());
        values.put(Constants.KEY_WHEN, story.getWhen().toString());
        values.put(Constants.KEY_WHO, story.getWho());
        values.put(Constants.KEY_AUTHOR, story.getAuthor());
        values.put(Constants.KEY_AUTHOR_ID, story.getAuthorId());
        values.put(Constants.KEY_MEDIA, story.putMediaIntoDB());
        values.put(Constants.KEY_WHERE, story.getWhere());
        values.put(Constants.KEY_UPLOADED, story.isUploaded());
        values.put(Constants.KEY_ASSIGNMENT_ID, story.getAssignmentId());
        values.put(Constants.KEY_UPDATED, java.lang.System.currentTimeMillis());

        // insert row
        long id = db.insert(Constants.STORIES_TABLE_NAME, null, values);
        Log.d("SAVED", "Saved to DB");
        return id;
    }

    public void bulkSaveStories(List<Story> stories) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Story story : stories) {
            saveStory(story);
        }

    }

    public Story getStory(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Constants.STORIES_TABLE_NAME,
                new String[] {
                        Constants.KEY_ID,
                        Constants.KEY_TITLE,
                        Constants.KEY_WHY,
                        Constants.KEY_WHO,
                        Constants.KEY_AUTHOR,
                        Constants.KEY_AUTHOR_ID,
                        Constants.KEY_MEDIA,
                        Constants.KEY_UPLOADED,
                        Constants.KEY_WHERE,
                        Constants.KEY_ASSIGNMENT_ID,
                        Constants.KEY_UPDATED,
                        Constants.KEY_WHEN},
                Constants.KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Story story = new Story();
        story.setLocal_id(Integer.parseInt(cursor.getString(
                cursor.getColumnIndex(Constants.KEY_ID))));
        story.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_TITLE)));
        story.setWho(cursor.getString(cursor.getColumnIndex(Constants.KEY_WHO)));
        story.setSummary(cursor.getString(cursor.getColumnIndex(Constants.KEY_WHY)));
        story.setWhen(cursor.getString(cursor.getColumnIndex(Constants.KEY_WHEN)));
        story.setWhere(cursor.getString(cursor.getColumnIndex(Constants.KEY_WHERE)));
        story.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
        story.setAuthorId(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR_ID)));
        story.setMediaFromDB(cursor.getString(cursor.getColumnIndex(Constants.KEY_MEDIA)));
        story.setUploaded(Boolean.
                parseBoolean(cursor.getString(cursor.getColumnIndex(Constants.KEY_UPLOADED))));
        story.setAssignmentId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_ID)));

        // convert timestamp to something readable
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(cursor.getLong(
                cursor.getColumnIndex(Constants.KEY_UPDATED))));
        story.setUpdated(formattedDate);
        return story;

    }

    public List<Story> getAllStories() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Story> storyList = new ArrayList<>();
        Cursor cursor = db.query(Constants.STORIES_TABLE_NAME,
                new String[] {
                        Constants.KEY_ID,
                        Constants.KEY_TITLE,
                        Constants.KEY_WHY,
                        Constants.KEY_WHO,
                        Constants.KEY_AUTHOR,
                        Constants.KEY_AUTHOR_ID,
                        Constants.KEY_MEDIA,
                        Constants.KEY_UPLOADED,
                        Constants.KEY_WHERE,
                        Constants.KEY_ASSIGNMENT_ID,
                        Constants.KEY_UPDATED,
                        Constants.KEY_WHEN}, null, null,
                null, null, Constants.KEY_TITLE + " DESC");
        if(cursor.moveToFirst()){
            do{
                Story story = new Story();
                story.setLocal_id(Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(Constants.KEY_ID))));
                story.setTitle(cursor.getString(cursor.getColumnIndex(Constants.KEY_TITLE)));
                story.setWho(cursor.getString(cursor.getColumnIndex(Constants.KEY_WHO)));
                story.setSummary(cursor.getString(cursor.getColumnIndex(Constants.KEY_WHY)));
                story.setWhen(cursor.getString(cursor.getColumnIndex(Constants.KEY_WHEN)));
                story.setWhere(cursor.getString(cursor.getColumnIndex(Constants.KEY_WHERE)));
                story.setAuthor(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR)));
                story.setAuthorId(cursor.getString(cursor.getColumnIndex(Constants.KEY_AUTHOR_ID)));
                story.setMediaFromDB(cursor.getString(cursor.getColumnIndex(Constants.KEY_MEDIA)));
                story.setUploaded(Boolean.
                        parseBoolean(cursor.getString(cursor.getColumnIndex(Constants.KEY_UPLOADED))));
                story.setAssignmentId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ASSIGNMENT_ID)));

                // convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(
                        cursor.getColumnIndex(Constants.KEY_UPDATED))));
                story.setUpdated(formattedDate);

                storyList.add(story);
            } while (cursor.moveToNext());
        }
        return storyList;
    }

    public int updateStory(Story story){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.KEY_TITLE, story.getTitle());
        values.put(Constants.KEY_WHY, story.getSummary());
        values.put(Constants.KEY_WHEN, story.getWhen().toString());
        values.put(Constants.KEY_WHO, story.getWho());
        values.put(Constants.KEY_AUTHOR, story.getAuthor());
        values.put(Constants.KEY_AUTHOR_ID, story.getAuthorId());
        values.put(Constants.KEY_MEDIA, story.putMediaIntoDB());
        values.put(Constants.KEY_UPLOADED, story.isUploaded());
        values.put(Constants.KEY_WHERE, story.getWhere());
        values.put(Constants.KEY_ASSIGNMENT_ID, story.getAssignmentId());
        values.put(Constants.KEY_UPDATED, java.lang.System.currentTimeMillis());

        return db.update(Constants.STORIES_TABLE_NAME,
                values, Constants.KEY_ID + "=?", new String[]
                        {String.valueOf(story.getLocal_id())});
    }

    public void deleteStory(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.STORIES_TABLE_NAME, Constants.KEY_ID + "=?",
                new String[] {String.valueOf(id)});
        db.close();
    }

    public int getStoriesCount(){
        String countQuery = "SELECT * FROM " + Constants.STORIES_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();
    }



}