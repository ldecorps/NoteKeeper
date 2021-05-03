package com.example.notekeeper;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;

import static org.hamcrest.Matchers.*;


@RunWith(AndroidJUnit4.class)
public class NoteCreationTest {
    static DataManager sDataManager;

    @BeforeClass
    public static void classSetUp(){
        sDataManager = DataManager.getInstance();
    }

    @Rule
    public ActivityTestRule<NoteListActivity> mNoteListActivityActivityRule =
            new ActivityTestRule(NoteListActivity.class);

    @Test
    public void createNewNote(){
        CourseInfo course = sDataManager.getCourse("java_lang");
        final String noteText = "This is the body of our test note";
        final String noteTitle = "Test note title";

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.spinner_courses)).perform(click());
        onData(allOf(instanceOf(CourseInfo.class), equalTo(course))).perform(click());
        onView(withId(R.id.spinner_courses)).check(matches(withSpinnerText(
                containsString(course.getTitle()))));

        onView(withId(R.id.text_note_title)).perform(typeText(noteTitle)).
                check(matches(withText(noteTitle)));
        onView(withId(R.id.text_note_text)).perform(typeText(noteText),
                closeSoftKeyboard());
        onView(withId(R.id.text_note_text)).check(matches(withText(noteText)));

        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (OS.indexOf("linux") != 0)
        {
            pressBack();
            int noteIndex = sDataManager.getNotes().size()-1;
            NoteInfo note = sDataManager.getNotes().get(noteIndex);
            assertEquals(course, note.getCourse());
            assertEquals(noteTitle, note.getTitle());
            assertEquals(noteText, note.getText());
        }
    }
}