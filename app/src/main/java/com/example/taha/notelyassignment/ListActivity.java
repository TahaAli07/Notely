package com.example.taha.notelyassignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;

import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private  ListView mListView;
    private  String[] headings;
    private  String[] descriptions;
    private  String[] times;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //Initializing Stetho Library used to view thw databases in the app
        Stetho.initializeWithDefaults(this);

        Helper helper= new Helper(this);
        mDb=helper.getWritableDatabase();

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Notely" + "</font>"));
        getSupportActionBar().setElevation(0);

        mListView= findViewById(R.id.listView);

        Cursor cursor = mDb.rawQuery("SELECT * FROM NOTELY",null);

        headings = new String[cursor.getCount()];
        descriptions= new String[cursor.getCount()];
        times= new String[cursor.getCount()];
        int i=0;

        while(cursor.moveToNext()){

            headings[i]= cursor.getString(cursor.getColumnIndex("TITLE"));
            descriptions[i]= cursor.getString(cursor.getColumnIndex("DESCRIPTION"));

            long time = cursor.getLong(cursor.getColumnIndex("TIME"));
            Date date = new Date(time);
            times[i]= date.toString().substring(0,16);
            i++;
        }

        /*headings = new String[]{"Note 1 heading ", "Note 2 heading" , "Note 3 heading" , "Note 4 heading" ,
                "Note 5 heading" , "Note 6 heading" , "Note 7 heading" , "Note 8 heading"};
        descriptions = new String[]{"Note 1 description ", "Note 2 description" , "Note 3 description" , "Note 4 description" ,
                "Note 5 description" , "Note 6 description" , "Note 7 description" , "Note 8 description"};
        times = new String[]{" 1 time ", " 2 time" , " 3 time" , " 4 time" ,
                " 5 time" , " 6 time" , " 7 time" , " 8 time"};*/

        customAdapter customAdapter= new customAdapter();

        mListView.setAdapter(customAdapter);



    }

    class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return headings.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            view = getLayoutInflater().inflate(R.layout.listitem,null);

            TextView heading= view.findViewById(R.id.HeadingNote_txtview);
            TextView description= view.findViewById(R.id.Description_txtview);
            TextView time = view.findViewById(R.id.time_txtview);

            heading.setText(headings[position]);
            description.setText(descriptions[position]);
            time.setText(times[position]);


            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.add){
            Intent intent = new Intent(ListActivity.this,CreateNote.class);
            startActivity(intent);

            return true;
        }
        else if(item.getItemId()==R.id.filter){
            //open that side thingy
            return true;
        }

        return true;
    }
}
