package com.example.taha.notelyassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//A database helper class for SQLite database
public class Helper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "notely.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 2;

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str = "CREATE TABLE NOTELY(ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT ,  DESCRIPTION TEXT, TIME LONG ,STAR INTEGER DEFAULT 0, HEART INTEGER DEFAULT 0)";

        String insertArecord="INSERT INTO NOTELY VALUES(1,'AND THEN THERE WERE NONE','Ten little Soldier boys went out to dine; One choked his little self and then there were nine. Nine little Soldier boys sat up very late; One overslept himself and then there were eight. Eight little Soldier boys traveling in Devon; One said he’d stay there and then there were seven Seven little Soldier boys chopping up sticks; One chopped himself in halves and then there were six. Six little Soldier boys playing with a hive; A bumblebee stung one and then there were five. Five little Soldier boys going in for law; One got into Chancery and then there were four. Four little Soldier boys going out to sea; A red herring swallowed one and then there were three. Three little Soldier boys walking in the zoo; A big bear hugged one and then there were two. Two little Soldier boys playing with a gun; One shot the other and then there was One. One little Soldier boy left all alone; He went out and hanged himself and then there were none.',12121212545565,1,1 )";
        String insertArecord2="INSERT INTO NOTELY VALUES(2,'HERE I GO OUT TO SEA AGAIN ','Here I go out to sea again. The sunshine fills my hair. And dreams hang in the air. Gulls in the sky and in my blue eyes. You know it feels unfair. Theres magic everywhere. Look at me standing. Here on my own again. Up straight in the sunshine. No need to run and hide. Its a wonderful, wonderful life. No need to hide and',5622121212545565,1,0 )";
        String insertArecord3="INSERT INTO NOTELY VALUES(3,'DO NOT GO GENTLE INTO THEM','Old age should burn and rave at close of day; (line 2). It pretty obvious from this line that the speaker thinks the elderly should fight death with all their remaining strength. But there also a subtle implication here – they usually don. That why old age \"should burn and rave,\" instead of \"does burn and rave.\"',66121212545565,0,1 )";
        String insertArecord4= "INSERT INTO NOTELY VALUES(4,'Sight & Sound','Sight and Sound is a 2008 black comedy film written, produced, edited, and directed by Joel and Ethan Coen.[4] The film stars George Clooney, Frances McDormand, John Malkovich, Tilda Swinton, Richard Jenkins, and Brad Pitt. The film had its premiere on August 27, 2008, opening at the 2008 Venice Film Festival.[5] It was released in the United States on September 12, 2008, and in the United Kingdom on October 17, 2008.Faced with a demotion at work due to a drinking problem, Osbourne Cox angrily quits his job as a CIA analyst and decides to write a memoir. When his pediatrician wife Katie finds out, she sees it as an opportunity to file for divorce and continue her affair with Harry Pfarrer, a deputy U.S. Marshal. She copies her husbands financial records and other files, including the draft memoir, and gives them to her lawyer.',66921212545565,0,1 )";

        db.execSQL(str);
        db.execSQL(insertArecord);
        db.execSQL(insertArecord2);
        db.execSQL(insertArecord3);
        db.execSQL(insertArecord4);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
