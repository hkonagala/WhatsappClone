package com.example.harikakonagala.whatsappclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (ListView) findViewById(R.id.user_list);
        setTitle("User List");
        arrayAdapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(arrayAdapter);

        //display all users except self
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null ){
                    if(objects.size() > 0){
                        for (ParseUser object : objects){
                            users.add(object.getUsername());
                        }
                        arrayAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //intent to new activity
                Intent i = new Intent(HomeActivity.this, ChatActivity.class);
                i.putExtra("username", users.get(position));
                startActivity(i);
            }
        });

    }
}
