package com.arrow.contacts.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.arrow.contacts.R;
import com.arrow.contacts.models.Contact;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> implements SectionIndexer {
    private List<Contact> mContactList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView firstLetter;
        TextView name;
        ImageView photo;
        View contactView;

        public ViewHolder(View view) {
            super(view);
            contactView = view;
            firstLetter = (TextView) view.findViewById(R.id.prefix_letter);
            name = (TextView) view.findViewById(R.id.name_textView);
            photo = (ImageView) view.findViewById(R.id.image_view);
        }
    }

    public ContactAdapter(List<Contact> contactList) {
        mContactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_contact, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Contact person = mContactList.get(position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact person = mContactList.get(position);

        //获得当前position是属于哪个分组
        int sectionForPostion = getSectionForPosition(position);
        //获得该分组第一项的position
        int positionForSection = getPositionForSection(sectionForPostion);
        //查看当前position是不是当前item所在分组的第一个item
        //如果是，则显示showLetter，否则隐藏
        if (position == positionForSection) {

        } else {

        }
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i=0; i<mContactList.size(); i++) {
            if (mContactList.get(i).getFirstLetter().charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mContactList.get(position).getFirstLetter().charAt(0);
    }
}
