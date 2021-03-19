package com.sampleapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button fetchUser;
    private String key=  "JIUNSdylLYUbLieJqYuFJpTnLgpZeAtr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = (EditText)findViewById(R.id.inputId);
        fetchUser = (Button)findViewById(R.id.fetchUser);

        RegisterApp.configureApp(key);
        //§  Test ID1: rc.test1 (will return a valid membership)
        //§  Test ID2: rc.test2 (will return an expired membership)
        //§  Test ID2: rc.test3


        fetchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId =  editText.getText().toString();
                if(userId.equals("")||userId==null){
                    editText.setError("Please enter user id");
                }
                else{
                    callApi(userId);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void callApi(String userID) {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Please wait, we are fetching your details");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        RegisterApp.fetchMember(userID, new FetchUser() {
            @Override
            public void onSuccess(MemberDetails memberDetails) {
                progress.dismiss();

                String  membership_status=null, member_since=null,valid_till=null;

                if(memberDetails.isMember()){
                    membership_status = "Active";
                } else{
                    membership_status = "Expired";
                }

                if(memberDetails.getExpiryDate()!=null){
                    valid_till = memberDetails.getExpiryDate();

                }
                else{
                    valid_till = "NA";
                    membership_status = "None";
                }
                if(memberDetails.getMemberSince()!=null){
                    member_since = memberDetails.getMemberSince();
                }
                else{
                    member_since = "NA";
                    membership_status = "None";
                }

                if(membership_status!=null&&member_since!=null&&valid_till!=null){
                    Intent intentToProfile = new Intent(MainActivity.this,UserProfile.class);
                    intentToProfile.putExtra("membership_status",membership_status);
                    intentToProfile.putExtra("member_since",member_since);
                    intentToProfile.putExtra("valid_till",valid_till);
                    startActivity(intentToProfile);
                }
                else{
                    Toast.makeText(MainActivity.this, "Something went wrong, please try again later", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(String errorMessage) {
                progress.dismiss();
                Log.e("error",errorMessage);

            }
        });
    }
}
