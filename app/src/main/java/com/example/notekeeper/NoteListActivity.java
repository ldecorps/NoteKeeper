package com.example.notekeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    private NoteRecyclerAdapter mNoteRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
            startActivity(new Intent(NoteListActivity.this, NoteActivity.class)));

        initializeDisplayContent();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }

    private void initializeDisplayContent() {
       final RecyclerView recyclerNotes = (RecyclerView) findViewById(R.id.list_notes);
       final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(this);
       recyclerNotes.setLayoutManager(notesLayoutManager);

       List<NoteInfo> notes = DataManager.getInstance().getNotes();
       mNoteRecyclerAdapter = new NoteRecyclerAdapter(this, null);
       recyclerNotes.setAdapter(mNoteRecyclerAdapter);
    }
}