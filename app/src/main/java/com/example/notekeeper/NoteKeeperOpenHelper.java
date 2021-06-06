package com.example.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteKeeperOpenHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "NoteKeeper.db";
    public static int DATABASE_VERSION = 1;
    public NoteKeeperOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteKeeperDatabaseContract.CourseInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(NoteKeeperDatabaseContract.NoteInfoEntry.SQL_CREATE_TABLE);

        DatabaseDataWorker worker = new DatabaseDataWorker(db);
        worker.insertCourses();
        worker.insertSampleNotes();
    }
    /*

    export PATH="/Users/ldecorps/Library/Android/sdk/platform-tools/:/Users/ldecorps/Downloads/sqlite-tools-osx-x86-3350500/:$PATH"

    /Users/ldecorps/Library/Android/sdk/platform-tools/adb exec-out "run-as com.example.notekeeper cat databases/NoteKeeper.db > /Users/ldecorps/AndroidStudioProjects/NoteKeeper/NoteKeeper.db"

    /Users/ldecorps/Downloads/sqlite-tools-osx-x86-3350500/sqlite3 /Users/ldecorps/AndroidStudioProjects/NoteKeeper/NoteKeeper.db

    /Users/ldecorps/Downloads/sqlite-tools-osx-x86-3350500/sqlite3 /Users/ldecorps/Downloads/android-managing-app-data-sqlite/01/exercise-files/after/NoteKeeper/NoteKeeper.db

    rm NoteKeeper.db*
    adb exec-out "run-as com.example.notekeeper cat databases/NoteKeeper.db " > NoteKeeper.db
    sqlite3  NoteKeeper.db

*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
