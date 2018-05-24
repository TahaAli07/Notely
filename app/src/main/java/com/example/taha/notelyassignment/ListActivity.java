package com.example.taha.notelyassignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.like.LikeButton;
import com.like.OnLikeListener;
import java.util.Date;

public class ListActivity extends AppCompatActivity {

    private  ListView mListView;
    private  String[] headings;
    private  String[] descriptions;
    private  String[] times;
    private  Integer[] stars;
    private  Integer[] hearts;

    private SQLiteDatabase mDb;
    private DrawerLayout mDrawerLayout;

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
        mDrawerLayout=findViewById(R.id.drawer_layout);

        Cursor cursor = mDb.rawQuery("SELECT * FROM NOTELY",null);

        headings = new String[cursor.getCount()];
        descriptions= new String[cursor.getCount()];
        times= new String[cursor.getCount()];
        stars= new Integer[cursor.getCount()];
        hearts= new Integer[cursor.getCount()];

        int i=0;

        while(cursor.moveToNext()){

            headings[i]= cursor.getString(cursor.getColumnIndex("TITLE"));
            descriptions[i]= cursor.getString(cursor.getColumnIndex("DESCRIPTION"));

            long time = cursor.getLong(cursor.getColumnIndex("TIME"));
            Date date = new Date(time);
            times[i]= date.toString().substring(0,16);

            stars[i]=cursor.getInt(cursor.getColumnIndex("STAR"));
            hearts[i]=cursor.getInt(cursor.getColumnIndex("HEART"));

            i++;
        }

        cursor.close();

        /*headings = new String[]{"Note 1 heading ", "Note 2 heading" , "Note 3 heading" , "Note 4 heading" ,
                "Note 5 heading" , "Note 6 heading" , "Note 7 heading" , "Note 8 heading"};
        descriptions = new String[]{"Note 1 description ", "Note 2 description" , "Note 3 description" , "Note 4 description" ,
                "Note 5 description" , "Note 6 description" , "Note 7 description" , "Note 8 description"};
        times = new String[]{" 1 time ", " 2 time" , " 3 time" , " 4 time" ,
                " 5 time" , " 6 time" , " 7 time" , " 8 time"};*/

        customAdapter customAdapter= new customAdapter();
        mListView.setAdapter(customAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView heading = view.findViewById(R.id.HeadingNote_txtview);
                TextView description = view.findViewById(R.id.Description_txtview);
                TextView time = view.findViewById(R.id.time_txtview);

                String title = heading.getText().toString();
                String desc = description.getText().toString();
                String t = time.getText().toString();

                Toast.makeText(ListActivity.this, title+desc+t, Toast.LENGTH_SHORT).show();

                Cursor cursor1 = mDb.rawQuery("SELECT ID FROM NOTELY WHERE TITLE=? AND DESCRIPTION = ? ", new String[]{title, desc});
                Integer ID=0;

                if (cursor1.moveToNext()) {

                    ID = cursor1.getInt(cursor1.getColumnIndex("ID"));
                }

                cursor1.close();
                Intent viewNoteIntent = new Intent(ListActivity.this,ViewNote.class);
                viewNoteIntent.putExtra("ID",ID);
                Toast.makeText(ListActivity.this, String.valueOf(ID), Toast.LENGTH_SHORT).show();
                startActivity(viewNoteIntent);

            }
        });

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
        public View getView(final int position, View view, ViewGroup parent) {

            view = getLayoutInflater().inflate(R.layout.listitem,null);

            TextView heading= view.findViewById(R.id.HeadingNote_txtview);
            TextView description= view.findViewById(R.id.Description_txtview);
            TextView time = view.findViewById(R.id.time_txtview);
            LikeButton star = view.findViewById(R.id.star);
            LikeButton heart = view.findViewById(R.id.heart);

            heading.setText(headings[position]);
            description.setText(descriptions[position]);
            time.setText(times[position]);

            Boolean starBoolean;
            Boolean heartBoolean;

            if(stars[position]==0){
                starBoolean = false;
            }
            else{
                starBoolean=true;
            }

            if(hearts[position]==0){
                heartBoolean = false;
            }
            else{
                heartBoolean=true;
            }

            star.setLiked(starBoolean);
            heart.setLiked(heartBoolean);

            star.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                    mDb.execSQL("UPDATE NOTELY SET STAR = 1 " + "WHERE TITLE = '" + headings[position]+"'");
                }

                @Override
                public void unLiked(LikeButton likeButton) {

                    mDb.execSQL("UPDATE NOTELY SET STAR = 0 " + "WHERE TITLE = '" + headings[position]+"'");
                }
            });

            heart.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {

                    mDb.execSQL("UPDATE NOTELY SET HEART = 1 " + "WHERE TITLE = '" + headings[position]+"'");
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    mDb.execSQL("UPDATE NOTELY SET HEART = 0 " + "WHERE TITLE = '" + headings[position] +"'");

                }
            });

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
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            return true;
        }

        return true;
    }
}
