package com.example.taha.notelyassignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.facebook.stetho.Stetho;
import com.like.LikeButton;
import com.like.OnLikeListener;
import java.util.Date;

//Main class that displays the list of notes
public class ListActivity extends AppCompatActivity {

    private SwipeMenuListView mListView;
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

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Notely" + "</font>"));
        getSupportActionBar().setElevation(0);

        mListView= findViewById(R.id.listView);
        mDrawerLayout=findViewById(R.id.drawer_layout);

        //creating instance of the helper class to get the database
        Helper helper= new Helper(this);
        mDb=helper.getWritableDatabase();

        // <item android:id="@+id/switch" title="Switch" ... />
        NavigationView nav = findViewById(R.id.navigation_view);
        final MenuItem HeartItem = nav.getMenu().findItem(R.id.Hearted);
        final MenuItem FavItem = nav.getMenu().findItem(R.id.Favourite);
        final CompoundButton FavView = (CompoundButton) MenuItemCompat.getActionView(FavItem);
        final CompoundButton HeartView = (CompoundButton) MenuItemCompat.getActionView(HeartItem);

        HeartView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(FavView.isChecked()){
                    HeartView.setChecked(false);
                }

                Cursor cursor;
                //If both hearted and favourite are unchecked
                if(!HeartView.isChecked() && !FavView.isChecked()) {
                    cursor = mDb.rawQuery("SELECT * FROM NOTELY", null);
                }

                //If heart is checked
                else if(HeartView.isChecked() && !FavView.isChecked()){
                    cursor= mDb.rawQuery("SELECT * FROM NOTELY ORDER BY HEART DESC", null);
                }

                else if(!HeartView.isChecked()  && FavView.isChecked()){
                    cursor= mDb.rawQuery("SELECT * FROM NOTELY ORDER BY STAR DESC", null);
                }
                else{
                    cursor = mDb.rawQuery("SELECT * FROM NOTELY", null);
                }

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

                customAdapter customAdapter= new customAdapter();
                mListView.setAdapter(customAdapter);
            }
        });

        FavView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(HeartView.isChecked()){
                    FavView.setChecked(false);
                }

                Cursor cursor;
                //If both hearted and favourite are unchecked
                if(!HeartView.isChecked() && !FavView.isChecked()) {
                    cursor = mDb.rawQuery("SELECT * FROM NOTELY", null);
                }

                //If heart is checked
                else if(HeartView.isChecked() && !FavView.isChecked()){
                    cursor= mDb.rawQuery("SELECT * FROM NOTELY ORDER BY HEART DESC", null);
                }

                else if(!HeartView.isChecked()  && FavView.isChecked()){
                    cursor= mDb.rawQuery("SELECT * FROM NOTELY ORDER BY STAR DESC", null);
                }
                else{
                    cursor = mDb.rawQuery("SELECT * FROM NOTELY", null);
                }

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

                customAdapter customAdapter= new customAdapter();
                mListView.setAdapter(customAdapter);
            }
        });


        Cursor cursor;
        //If both hearted and favourite are unchecked
        if(!HeartView.isChecked() && !FavView.isChecked()) {
            cursor = mDb.rawQuery("SELECT * FROM NOTELY", null);
        }

        //If heart is checked
        else if(HeartView.isChecked()){
            cursor= mDb.rawQuery("SELECT * FROM NOTELY ORDER BY HEART DESC", null);
        }

        else {
            cursor= mDb.rawQuery("SELECT * FROM NOTELY ORDER BY STAR DESC", null);
        }

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

        customAdapter customAdapter= new customAdapter();
        mListView.setAdapter(customAdapter);

        //OnclickListener for the List View
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView heading = view.findViewById(R.id.HeadingNote_txtview);
                TextView description = view.findViewById(R.id.Description_txtview);

                String title = heading.getText().toString();
                String desc = description.getText().toString();

                Cursor cursor1 = mDb.rawQuery("SELECT ID FROM NOTELY WHERE TITLE=? AND DESCRIPTION = ? ", new String[]{title, desc});
                Integer ID=0;

                if (cursor1.moveToNext()) {

                    ID = cursor1.getInt(cursor1.getColumnIndex("ID"));
                }

                cursor1.close();
                Intent viewNoteIntent = new Intent(ListActivity.this,ViewNote.class);
                viewNoteIntent.putExtra("ID",ID);
                viewNoteIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(viewNoteIntent);
            }
        });

        //SwipeMenuListView
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(270);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_name);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(ListActivity.this, "DELETE IS PRESSED", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    //This is the custom adapter for the list view
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

            //when user clicks on the star button
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

            //when user clicks on the heart button
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

    //These two methods are used to inflate and initialise the menu xml the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.add){
            Intent intent = new Intent(ListActivity.this,CreateNote.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
