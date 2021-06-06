package com.example.notekeeper;

public final class NoteKeeperDatabaseContract {
    private NoteKeeperDatabaseContract(){}

    public static class CourseInfoEntry{
        public static final String TABLE_NAME = "course_info";
        public static final String COLUMN_COURSE_ID = "course_id";
        public static final String COLUMN_COURSE_TITLE = "course_title";

        // CREATE TABLE course_info (course_id, course_title)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE "+TABLE_NAME+" ("+
                        COLUMN_COURSE_ID+", "+
                        COLUMN_COURSE_TITLE+")";

    }

    public static class NoteInfoEntry{
        public static final String TABLE_NAME = "note_info";
        public static final String COLUMN_NOTE_TITLE = "note_title";
        public static final String COLUMN_NOTE_TEXT = "note_text";
        public static final String COLUMN_COURSE_ID = "course_id";

        // CREATE TABLE course_info (course_id, course_title)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE "+TABLE_NAME+" ("+
                        COLUMN_NOTE_TITLE+", "+
                        COLUMN_NOTE_TEXT+", "+
                        COLUMN_COURSE_ID+")";

    }
}