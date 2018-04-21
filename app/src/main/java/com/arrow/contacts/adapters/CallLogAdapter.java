package com.arrow.contacts.adapters;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrow.contacts.R;
import com.arrow.contacts.models.CallLogs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.ViewHolder> {

    private List<CallLogs> mCallLogsList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView callLogNumber;
        TextView callLogNumberType;
        TextView callLogDateTime;
        TextView callLogDuration;
        ImageView callLogType;
        View callLogView;

        public ViewHolder(View view) {
            super(view);
            callLogView = view;
            callLogNumber = (TextView) view.findViewById(R.id.call_log_number);
            callLogNumberType = (TextView) view.findViewById(R.id.call_log_number_type);
            callLogDateTime = (TextView) view.findViewById(R.id.call_log_datetime);
            callLogDuration = (TextView) view.findViewById(R.id.call_log_duration);
            callLogType = (ImageView) view.findViewById(R.id.call_log_type_image);
        }
    }

    public CallLogAdapter(List<CallLogs> callLogsList) {
        this.mCallLogsList = callLogsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_call_log, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.callLogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CallLogs callLogs = mCallLogsList.get(position);
        holder.callLogNumber.setText(callLogs.getNumber());
        holder.callLogNumberType.setText(callLogs.typeToString());
        holder.callLogDuration.setText(formatDuration(callLogs.getDuration()));
        holder.callLogDateTime.setText(formatDate(callLogs.getDate()));

        holder.callLogType.setImageResource(callLogs.getCallTypeImageId());
    }

    public String formatDuration(long time) {
        long s = time % 60;
        long m = time / 60;
        long h = time / 60 / 60;
        StringBuilder sb = new StringBuilder();
        if (h > 0) {
            sb.append(h).append("小时");
        }
        if (m > 0) {
            sb.append(m).append("分");
        }
        sb.append(s).append("秒");
        return sb.toString();
    }

    public String formatDate(long time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return format.format(new Date(time));
    }

    @Override
    public int getItemCount() {
        return mCallLogsList.size();
    }
}
