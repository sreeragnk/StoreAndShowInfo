package com.sparklingspur.sreeragnampoothiri.storeandshowinfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by sreerag.nampoothiri on 14-May-15.
 */
public class ContactInfo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactinfo);

        Intent i = getIntent();
        Contact contact = (Contact)i.getSerializableExtra("SelectedPerson");

        TextView selectedPersonName = (TextView)findViewById(R.id.selectedPersonName);
        TextView selectedPersonPhone = (TextView)findViewById(R.id.selectedPersonPhone);

        selectedPersonName.setText(contact.getName());
        selectedPersonPhone.setText(contact.getPhoneNumber());

    }
}
