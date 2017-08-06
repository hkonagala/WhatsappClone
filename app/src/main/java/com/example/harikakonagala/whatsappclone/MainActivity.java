package com.example.harikakonagala.whatsappclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    EditText username, password;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        signin = (Button) findViewById(R.id.signup_login);

        signin.setOnClickListener(this);

        //user is already logged in
        if(ParseUser.getCurrentUser() !=null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

    }

    @Override
    public void onClick(View v) {
        userSignup();
    }

    private void userSignup() {

        final String usernameText = username.getText().toString();
        final String pwdText = password.getText().toString();

        //login
        ParseUser.logInInBackground(usernameText, pwdText, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(user !=null){
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    Log.i("login", "sucessfully logged in");
                    Toast.makeText(getApplicationContext(),
                            "Successfully Logged in",
                            Toast.LENGTH_LONG).show();
                }else {

                    //signup
                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(usernameText);
                    parseUser.setPassword(pwdText);
                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if( e == null){
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                Log.i("signup", "sign up successfully done");
                                Toast.makeText(getApplicationContext(),
                                        "Successfully Signed up",
                                        Toast.LENGTH_LONG).show();
                            }else {
                                Log.i("signup", "signup failed");
                                Toast.makeText(getApplicationContext(),
                                        e.getMessage(), Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });

                }
            }
        });
    }

}

