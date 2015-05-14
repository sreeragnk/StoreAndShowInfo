package com.sparklingspur.sreeragnampoothiri.storeandshowinfo;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sreerag.nampoothiri on 12-May-15.
 */
public class ListAdapter extends ArrayAdapter<Contact>{


    Context context;
    LayoutInflater inflater;
    List<Contact> contactList;
    private SparseBooleanArray mSelectedItemsIds;
    public ListAdapter(Context context,int resourceId, List<Contact> contactList) {
        super(context, resourceId, contactList);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.contactList = contactList;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView showName;
        TextView showPhone;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;

        // Get the data item for this position
        //Contact contact = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.contact_view_item, null);

            //Locate the Textviews in Contact_view_item.xml
            holder.showName = (TextView) view.findViewById(R.id.showName);
            holder.showPhone = (TextView)view.findViewById(R.id.showPhone);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //Capture position and set to the Textviews
        holder.showName.setText(contactList.get(position).getName());
        holder.showPhone.setText(contactList.get(position).getPhoneNumber());

        return view;

    }

    @Override
    public void remove(Contact object) {
        contactList.remove(object);
        notifyDataSetChanged();
    }

    public List<Contact> getWorldPopulation() {
        return contactList;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }


    }



