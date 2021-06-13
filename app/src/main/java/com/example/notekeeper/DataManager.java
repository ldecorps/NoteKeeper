package com.example.notekeeper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.notekeeper.NoteKeeperDatabaseContract.CourseInfoEntry;
import com.example.notekeeper.NoteKeeperDatabaseContract.NoteInfoEntry;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;

    private List<CourseInfo> mCourses = new ArrayList<>();
    private List<NoteInfo> mNotes = new ArrayList<>();

    public static DataManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public static void loadFromDatabase(NoteKeeperOpenHelper dbHelper){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] courseColumns = {
                CourseInfoEntry.COLUMN_COURSE_ID
                , CourseInfoEntry.COLUMN_COURSE_TITLE};
        Cursor courseCursor = db.query(CourseInfoEntry.TABLE_NAME, courseColumns, null, null, null, null
                ,  CourseInfoEntry.COLUMN_COURSE_TITLE + " DESC");
        loadCoursesFromDatabase(courseCursor);
        String[] noteColumns = {
                NoteInfoEntry.COLUMN_NOTE_TITLE
                , NoteInfoEntry.COLUMN_NOTE_TEXT
                , NoteInfoEntry.COLUMN_COURSE_ID
                , NoteInfoEntry._ID};
        String noteOrderBy =NoteInfoEntry.COLUMN_COURSE_ID + "," + NoteInfoEntry.COLUMN_NOTE_TITLE;
        Cursor noteCursor = db.query(NoteInfoEntry.TABLE_NAME, noteColumns, null, null, null, null
                , noteOrderBy);
        loadNotesFromDatabase(noteCursor);
    }

    private static void loadNotesFromDatabase(Cursor cursor) {
        int noteTitlePos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TITLE);
        int noteTextPos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_NOTE_TEXT);
        int courseIdPos = cursor.getColumnIndex(NoteInfoEntry.COLUMN_COURSE_ID);
        int idPos = cursor.getColumnIndex(NoteInfoEntry._ID);

        DataManager dm =getInstance();
        dm.mNotes.clear();
        while (cursor.moveToNext()){
            String noteTitle = cursor.getString(noteTitlePos);
            String noteText = cursor.getString(noteTextPos);
            String courseId = cursor.getString(courseIdPos);
            int id = cursor.getInt(idPos);

            CourseInfo noteCourse =dm.getCourse(courseId);
            NoteInfo note = new NoteInfo(id, noteCourse, noteTitle, noteText);
            dm.mNotes.add(note);
        }
        cursor.close();
    }

    private static void loadCoursesFromDatabase(Cursor cursor) {
        int courseIdPos =cursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_ID);
        int courseTitlePos =cursor.getColumnIndex(CourseInfoEntry.COLUMN_COURSE_TITLE);

        DataManager dm =getInstance();
        dm.mCourses.clear();
        while (cursor.moveToNext()){
            String courseId =cursor.getString(courseIdPos);
            String courseTitle = cursor.getString(courseTitlePos);
            CourseInfo course = new CourseInfo(courseId, courseTitle, null);
            dm.mCourses.add(course);
        }
        cursor.close();
    }

    public List<NoteInfo> getNotes() {
        return mNotes;
    }

    public int createNewNote() {
        NoteInfo note = new NoteInfo(null, null, null);
        mNotes.add(note);
        return mNotes.size() - 1;
    }

    public int findNote(NoteInfo note) {
        for(int index = 0; index < mNotes.size(); index++) {
            if(note.equals(mNotes.get(index)))
                return index;
        }

        return -1;
    }

    public void removeNote(int index) {
        mNotes.remove(index);
    }

    public List<CourseInfo> getCourses() {
        return mCourses;
    }

    public CourseInfo getCourse(String id) {
        for (CourseInfo course : mCourses) {
            if (id.equals(course.getCourseId()))
                return course;
        }
        return null;
    }

    private DataManager() {
    }

    //region Initialization code

    public void initializeExampleNotes() {
        final DataManager dm = getInstance();

        CourseInfo course = dm.getCourse("android_intents");
        course.getModule("android_intents_m01").setComplete(true);
        course.getModule("android_intents_m02").setComplete(true);
        course.getModule("android_intents_m03").setComplete(true);
        mNotes.add(new NoteInfo(course, "Dynamic intent resolution",
                "Wow, intents allow components to be resolved at runtime"));
        mNotes.add(new NoteInfo(course, "Delegating intents",
                "PendingIntents are powerful; they delegate much more than just a component invocation"));

        course = dm.getCourse("android_async");
        course.getModule("android_async_m01").setComplete(true);
        course.getModule("android_async_m02").setComplete(true);
        mNotes.add(new NoteInfo(course, "Service default threads",
                "Did you know that by default an Android Service will tie up the UI thread?"));
        mNotes.add(new NoteInfo(course, "Long running operations",
                "Foreground Services can be tied to a notification icon"));

        course = dm.getCourse("java_lang");
        course.getModule("java_lang_m01").setComplete(true);
        course.getModule("java_lang_m02").setComplete(true);
        course.getModule("java_lang_m03").setComplete(true);
        course.getModule("java_lang_m04").setComplete(true);
        course.getModule("java_lang_m05").setComplete(true);
        course.getModule("java_lang_m06").setComplete(true);
        course.getModule("java_lang_m07").setComplete(true);
        mNotes.add(new NoteInfo(course, "Parameters",
                "Leverage variable-length parameter lists"));
        mNotes.add(new NoteInfo(course, "Anonymous classes",
                "Anonymous classes simplify implementing one-use types"));

        course = dm.getCourse("java_core");
        course.getModule("java_core_m01").setComplete(true);
        course.getModule("java_core_m02").setComplete(true);
        course.getModule("java_core_m03").setComplete(true);
        mNotes.add(new NoteInfo(course, "Compiler options",
                "The -jar option isn't compatible with with the -cp option"));
        mNotes.add(new NoteInfo(course, "Serialization",
                "Remember to include SerialVersionUID to assure version compatibility"));
    }

    public int createNewNote(CourseInfo course, String noteTitle, String noteText) {
        int index = createNewNote();
        NoteInfo note = getNotes().get(index);
        note.setCourse(course);
        note.setTitle(noteTitle);
        note.setText(noteText);
        return index;
    }
    //endregion

}
