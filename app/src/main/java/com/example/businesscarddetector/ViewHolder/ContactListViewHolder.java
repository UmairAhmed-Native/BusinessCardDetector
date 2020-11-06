package com.example.businesscarddetector.ViewHolder;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.LandingFragment;
import com.example.businesscarddetector.View.ViewInterface.ContactNavigationListener;

public class ContactListViewHolder extends RecyclerView.ViewHolder {
    private View mitemView;
    private ContactNavigationListener mListener;

    public ContactListViewHolder(View itemView, ContactNavigationListener listener) {
        super(itemView);
        this.mitemView = itemView;
        mListener = listener;

    }

    public void bind(ContactModel contactModel, int colorType) {
        ConstraintLayout ccCardLayout = mitemView.findViewById(R.id.cc_card_layout);
        TextView companyTxt = mitemView.findViewById(R.id.company_name_view);
        TextView personTxt = mitemView.findViewById(R.id.person_name_view);
        TextView desginationTxt = mitemView.findViewById(R.id.desgination_view);
        TextView contactTxt = mitemView.findViewById(R.id.contact_view);
        TextView emailTxt = mitemView.findViewById(R.id.email_view);
        if (colorType == 0) {
            ccCardLayout.setBackgroundResource(R.drawable.list_item_drawable);
        } else {
            ccCardLayout.setBackgroundResource(R.drawable.list_item_drawable_secondary);
        }

        companyTxt.setText(contactModel.getCompanyName());
        personTxt.setText(contactModel.getPersonName());
        desginationTxt.setText(contactModel.getDesignation());
        contactTxt.setText(contactModel.getContactNumber());
        emailTxt.setText(contactModel.getEmailAddress());
        mitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.navigateToContactDetail(contactModel);
//                landingFragment.navigateToContactDetail(contactModel);
            }
        });
        {

        }
    }


}
