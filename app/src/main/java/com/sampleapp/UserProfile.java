package com.sampleapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends Activity {
    TextView membership, validTill, memberSince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        membership = (TextView)findViewById(R.id.membershipStatus);
        validTill = (TextView)findViewById(R.id.validTill);
        memberSince = (TextView)findViewById(R.id.membershipSince);

      /*  intentToProfile.putExtra("membership_status",membership_status);
        intentToProfile.putExtra("member_since",member_since);
        intentToProfile.putExtra("valid_till",valid_till);*/
      String membership_status,member_since,valid_till;
      if(getIntent().hasExtra("membership_status")){
          membership_status = getIntent().getStringExtra("membership_status");
          membership.setText(membership_status);
      }
      if(getIntent().hasExtra("member_since")){
            member_since = getIntent().getStringExtra("member_since");
            memberSince.setText(member_since);
      }
      if(getIntent().hasExtra("valid_till")){
            valid_till = getIntent().getStringExtra("valid_till");
            validTill.setText(valid_till);

      }


    }
}
