package com.example.taha.notelyassignment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

//Class to View/Edit a Note
public class ViewNote extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private EditText mTitle;
    private EditText mDescription;
    private Integer ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "" + "</font>"));
        getSupportActionBar().setElevation(0);

        Helper helper = new Helper(this);
        mDb=helper.getWritableDatabase();

        ID = getIntent().getExtras().getInt("ID");
        Log.d("ABC",ID.toString());
        Cursor cursor=mDb.rawQuery("SELECT * FROM NOTELY WHERE ID = "+ID,null);
        cursor.moveToNext();

        String title = cursor.getString(cursor.getColumnIndex("TITLE"));
        String desc = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
        cursor.close();

        mTitle=findViewById(R.id.viewNote_TitleEditText);
        mDescription=findViewById(R.id.viewNote_DescEditText);

        mTitle.setText(title);
        mDescription.setText(desc);
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

            ContentValues cv = new ContentValues();
            cv.put("TITLE",mTitle.getText().toString());
            cv.put("DESCRIPTION",mDescription.getText().toString());
            mDb.update("NOTELY",cv,"ID = "+ID,null);

            Intent intent = new Intent(ViewNote.this,ListActivity.class);
            startActivity(intent);

            mTitle.clearFocus();
            mDescription.clearFocus();
            mTitle.getText().clear();
            mDescription.getText().clear();
        }
        return true;
    }
}
