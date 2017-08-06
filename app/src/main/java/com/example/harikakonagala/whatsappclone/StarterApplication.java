package com.example.harikakonagala.whatsappclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by Harika Konagala on 8/6/2017.
 */

public class StarterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("4a0dd39302c77133026c55fb08a23fcfc6b44d7e")
                .clientKey( "62c4feffd96d1b2712ada35cc98727eaab64a4b7")
                .server("http://ec2-18-220-67-171.us-east-2.compute.amazonaws.com/parse/")
                .build()
        );


        //disabling automaticUser gives us the control over sign up and login in parse server
        //ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
