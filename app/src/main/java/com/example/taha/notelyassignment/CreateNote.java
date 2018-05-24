package com.example.taha.notelyassignment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNote extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private EditText mTitle;
    private EditText mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        mTitle= findViewById(R.id.createNote_Title_EditText);
        mDescription=findViewById(R.id.createNote_Description_EditText);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "" + "</font>"));
        getSupportActionBar().setElevation(0);

        Helper helper= new Helper(this);
        mDb=helper.getWritableDatabase();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuforcreatenote,menu);
        menu.findItem(R.id.save).setTitle(Html.fromHtml("<font color=\"black\">" + "SAVE" + "</font>"));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.save){

            String title = mTitle.getText().toString();
            String description = mDescription.getText().toString();

            if(title.equals("") && description.equals("")){
                addTodb(title,description);

                mTitle.clearFocus();
                mDescription.clearFocus();
                mTitle.getText().clear();
                mDescription.getText().clear();

                Intent intent = new Intent(CreateNote.this,ListActivity.class);
                startActivity(intent);
                finish();
            }

            return true;
        }

        return  true;
    }

    public void addTodb(String title, String description){

        ContentValues cv = new ContentValues();
        cv.put("TITLE",title);
        cv.put("DESCRIPTION",description);
        cv.put("TIME", System.currentTimeMillis());
        mDb.insert("NOTELY",null,cv);

        Toast.makeText(this, "SAVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

    }
}
