package com.example.taha.notelyassignment;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    private  ListView mListView;
    private  String[] headings;
    private  String[] descriptions;
    private  String[] times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + "Notely" + "</font>"));

        mListView= (ListView)findViewById(R.id.listView);

        headings = new String[]{"Note 1 heading ", "Note 2 heading" , "Note 3 heading" , "Note 4 heading" ,
                "Note 5 heading" , "Note 6 heading" , "Note 7 heading" , "Note 8 heading"};
        descriptions = new String[]{"Note 1 description ", "Note 2 description" , "Note 3 description" , "Note 4 description" ,
                "Note 5 description" , "Note 6 description" , "Note 7 description" , "Note 8 description"};
        times = new String[]{" 1 time ", " 2 time" , " 3 time" , " 4 time" ,
                " 5 time" , " 6 time" , " 7 time" , " 8 time"};

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
}
