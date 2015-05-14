package com.sparklingspur.sreeragnampoothiri.storeandshowinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sreerag.nampoothiri on 12-May-15.
 */
public class ListAdapter extends ArrayAdapter<Contact>{


    public ListAdapter(Context context, List<Contact> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Contact contact = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_view,parent,false);
        }

        // Lookup view for data population
        TextView inflateName = (TextView) convertView.findViewById(R.id.showName);
        TextView inflatePhone = (TextView) convertView.findViewById(R.id.showPhone);

        // Populate the data into the template view using the data object
        inflateName.setText(contact.getName().toString());
        inflatePhone.setText(contact.getPhoneNumber().toString());

        // Return the completed view to render on screen


        return convertView;
        }

    }



