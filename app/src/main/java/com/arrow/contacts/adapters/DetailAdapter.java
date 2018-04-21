package com.arrow.contacts.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.arrow.contacts.R;
import com.arrow.contacts.models.Detail;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> implements SectionIndexer{
    private List<Detail> mDetailList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView valueView;
        TextView typeView;
        ImageView iconView;
        ImageView iconBackView;
        View detailView;

        public ViewHolder(View view) {
            super(view);
            valueView = (TextView) view.findViewById(R.id.contact_value);
            typeView = (TextView) view.findViewById(R.id.contact_value_type);
            iconView = (ImageView) view.findViewById(R.id.contact_detail_main_image);
            iconBackView = (ImageView) view.findViewById(R.id.contact_detail_back_image);
            detailView = view;
        }
    }

    public DetailAdapter(List<Detail> detailList) {
        mDetailList = detailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_phone_number, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        if (mContext == null) {
            mContext = parent.getContext();
        }

        holder.detailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Detail detail = mDetailList.get(holder.getAdapterPosition());
                if (detail.getPrefix().contains("0")) {
                    callPhone(detail.getValue());
                } else if (detail.getPrefix().contains("@")){
                    sendEmail(detail.getValue());
                }
            }
        });

        holder.iconBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Detail detail = mDetailList.get(holder.getAdapterPosition());
//                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                sendSms(detail.getValue());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Detail detail = mDetailList.get(position);
        holder.valueView.setText(detail.getValue());
        holder.typeView.setText(detail.typeToString());

        if (detail.getPrefix() == "0") {
            holder.iconBackView.setImageResource(detail.getBackIconId());
        }

        int sectionForPosition = getSectionForPosition(position);
        int positionForSection = getPositionForSection(sectionForPosition);
        if (position == positionForSection) {
            holder.iconView.setImageResource(detail.getMainIconId());
            if (detail.getPrefix() == "@") {
//                holder.iconBackView.setImageAlpha(100);
//                holder.iconBackView.setClickable(false);
                holder.iconBackView.setVisibility(View.GONE);
            }
        } else {
            holder.iconView.setImageAlpha(100);
        }
    }

    @Override
    public int getItemCount() {
        return mDetailList.size();
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i=0; i<mDetailList.size(); i++) {
            if (mDetailList.get(i).getPrefix().charAt(0) == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return mDetailList.get(position).getPrefix().charAt(0);
    }

    private void callPhone(String value) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + value));
        mContext.startActivity(intent);
    }

    private void sendEmail(String value) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + value));
        mContext.startActivity(Intent.createChooser(intent, "通过以下应用发送"));
    }

    private void sendSms(String value) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("smsto:" + value));
        mContext.startActivity(intent);
    }
}
