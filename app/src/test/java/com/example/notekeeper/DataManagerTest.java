package com.example.notekeeper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataManagerTest {

    @Before
    public void setUp(){
        DataManager dm = DataManager.getInstance();
        dm.getNotes().clear();
        dm.initializeExampleNotes();
    }

    @Test
    public void createNewNote() {
        DataManager dm = DataManager.getInstance();
        final CourseInfo course = dm.getCourse("android_asynch");
        final String noteTitle = "Test note title";
        final String noteText = "This is the body text of my test note";
        int noteIndex = dm.createNewNote();
        NoteInfo newNote = dm.getNotes().get(noteIndex);
        newNote.setCourse(course );
        newNote.setTitle(noteTitle);
        newNote.setText(noteText);

        NoteInfo compareNote = dm.getNotes().get(noteIndex);

        assertEquals(course, compareNote.getCourse());
        assertEquals(noteTitle, compareNote.getTitle());
        assertEquals(noteText, compareNote.getText());
    }

    @Test
    public void findSimilarNotes() {
        DataManager dm = DataManager.getInstance();
        final CourseInfo course = dm.getCourse("android_async");
        final String noteTitle = "Test note title";
        final String noteText1 = "This is the body text of my test note";
        final String noteText2  = "This is the body of my second test note";

        int noteIndex1 = dm.createNewNote();
        NoteInfo newNote1 = dm.getNotes().get(noteIndex1);
        newNote1.setCourse(course);
        newNote1.setTitle(noteTitle);
        newNote1.setText(noteText1);

        int noteIndex2 = dm.createNewNote();
        NoteInfo newNote2 = dm.getNotes().get(noteIndex2);
        newNote2.setCourse(course);
        newNote2.setTitle(noteTitle);
        newNote2.setText(noteText2);

        int foundIndex1 = dm.findNote(newNote1);
        assertEquals(noteIndex1, foundIndex1);

        int foundIndex2 = dm.findNote(newNote2);
        assertEquals(noteIndex2, foundIndex2);
    }
}