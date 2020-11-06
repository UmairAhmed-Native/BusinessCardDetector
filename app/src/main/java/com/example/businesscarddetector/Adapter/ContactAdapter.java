package com.example.businesscarddetector.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.LandingFragment;
import com.example.businesscarddetector.View.ViewInterface.ContactNavigationListener;
import com.example.businesscarddetector.ViewHolder.ContactListViewHolder;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactListViewHolder> {
    private List<ContactModel> contactModels;
    private ContactNavigationListener listener;

    public ContactAdapter(List<ContactModel> contactModelList, ContactNavigationListener listener) {
        super();
        this.contactModels = contactModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_contact_list, parent, false);
        return new ContactListViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListViewHolder holder, int position) {
        ContactModel contactObj = contactModels.get(position);
        int colorType = 1;
        if (position % 2 == 0) {
            colorType = 0;
        } else {
            colorType = 1;
        }
        holder.bind(contactObj,colorType);
    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }
}

