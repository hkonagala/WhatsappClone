package com.example.harikakonagala.whatsappclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.message;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{

    EditText chatText;
    Button sendChat;
    ListView listView;
    String activeUser = "";
    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent i = getIntent();
        activeUser =i.getStringExtra("username");
        setTitle("chat with " + activeUser);

        listView = (ListView) findViewById(R.id.chat_list);
        sendChat = (Button) findViewById(R.id.sendChat_button);
        chatText = (EditText) findViewById(R.id.chat_et);

        sendChat.setOnClickListener(this);
        arrayAdapter = new ArrayAdapter(ChatActivity.this, android.R.layout.simple_list_item_1, messages);
        listView.setAdapter(arrayAdapter);

        //two queries for sending and receiving messages
        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Message");
        query1.whereEqualTo("sender", ParseUser.getCurrentUser().getUsername());
        query1.whereEqualTo("recepient", activeUser);

        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Message");
        query2.whereEqualTo("sender", activeUser);
        query2.whereEqualTo("recepient", ParseUser.getCurrentUser().getUsername());

        List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
        queries.add(query1);
        queries.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(queries);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        messages.clear();

                        for(ParseObject object : objects){

                            String messageContent = object.getString("message");
                            if(object.getString("sender").equals(ParseUser.getCurrentUser().getUsername())){
                                messageContent = "> " + messageContent;
                            }

                            messages.add(messageContent);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }

    @Override
    public void onClick(View v) {

        ParseObject message = new ParseObject("Message");
        final String messageContent = chatText.getText().toString();
        message.put("sender", ParseUser.getCurrentUser().getUsername());
        message.put("recepient", activeUser);
        message.put("message", messageContent);

        chatText.setText("");

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    messages.add(messageContent);
                    arrayAdapter.notifyDataSetChanged();
                    //Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
