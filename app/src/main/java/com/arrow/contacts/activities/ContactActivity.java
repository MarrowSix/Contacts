package com.arrow.contacts.activities;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arrow.contacts.R;
import com.arrow.contacts.adapters.CallLogAdapter;
import com.arrow.contacts.adapters.ContactAdapter;
import com.arrow.contacts.adapters.DetailAdapter;
import com.arrow.contacts.models.CallLogs;
import com.arrow.contacts.models.Contact;
import com.arrow.contacts.models.Detail;
import com.arrow.contacts.models.Temp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    public static final String CONTACT = "contact";
    private List<Detail> mdetailList = new ArrayList<>();
    private List<CallLogs> mCallLogsList = new ArrayList<>();
    private Contact person;
    private RecyclerView callLogRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initFragment();
//        setDetailContent();
        setDetail();

        readCallLog();
        TextView callLogTitle = (TextView) findViewById(R.id.call_log_title);
        if (mCallLogsList.size() == 0) {
            callLogTitle.setVisibility(View.GONE);
        }

        CallLogAdapter callLogAdapter = new CallLogAdapter(mCallLogsList);
        callLogRecyclerView = (RecyclerView) findViewById(R.id.contact_call_log_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        callLogRecyclerView.setLayoutManager(linearLayoutManager);
        callLogRecyclerView.setAdapter(callLogAdapter);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_action_delete:
                deleteContact(person);
                finish();
                default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFragment() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.contact_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.edit_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent("com.arrow.contacts.activities.EDIT_START");
                intent.putExtra("show_type", false);
                Temp temp = new Temp(
                        !person.getSurName().isEmpty() ? person.getSurName() : new String(""),
                        !person.getFirstName().isEmpty() ? person.getFirstName() : new String(""),
                        "",
                        !person.getPhoneNumber().isEmpty() ? person.getPhoneNumber().get(0) : new String(""),
                        !person.getPhoneType().isEmpty() ? person.getPhoneType().get(0) : 1,
                        !person.getEmails().isEmpty() ? person.getEmails().get(0) : new String(""),
                        !person.getEmailsType().isEmpty() ? person.getEmailsType().get(0) : 1
                );
                intent.putExtra(AddActivity.EDIT, temp);
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setDetail() {
        Intent intent = getIntent();
        person = (Contact) intent.getSerializableExtra(CONTACT);

        List<String> contactNumber = new ArrayList<>();
        List<Integer> numberType = new ArrayList<>();
        // 根据联系人的ID获取此人的电话号码
        String[] phoneProjection = new String[] {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE
        };
        Cursor phoneCursor = this.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                phoneProjection,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + person.getId(),
                null,
                null
        );
        // 因为同一联系人可能有多个电话号码，需要遍历
        if (phoneCursor.moveToFirst()) {
            do {
                contactNumber.add(phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                numberType.add(phoneCursor.getInt(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)));
            } while (phoneCursor.moveToNext());
        }
        phoneCursor.close();

        List<String> emails = new ArrayList<>();
        List<Integer> emailsType = new ArrayList<>();
        String[] emailProjection = new String[] {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.TYPE
        };
        Cursor emailCursor = this.getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                emailProjection,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + person.getId(),
                null,
                null
        );
        if (emailCursor.moveToFirst()) {
            do {
                emails.add(emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)));
                emailsType.add(emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)));
            } while (emailCursor.moveToNext());
        }
        emailCursor.close();

        person.setPhoneNumber(contactNumber);
        person.setPhoneType(numberType);
        person.setEmails(emails);
        person.setEmailsType(emailsType);

        for (int i=0; i<person.getPhoneNumber().size(); i++) {
            Detail temp = new Detail(
                    person.getPhoneNumber().get(i),
                    person.getPhoneType().get(i),
                    "0",
                    R.drawable.ic_number,
                    R.drawable.ic_message
            );
            mdetailList.add(temp);
        }

        for (int i=0; i<person.getEmails().size(); i++) {
            Detail temp = new Detail(
                    person.getEmails().get(i),
                    person.getEmailsType().get(i),
                    "@",
                    R.drawable.ic_email,
                    R.drawable.ic_message
            );
            mdetailList.add(temp);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView contactImageView = (ImageView) findViewById(R.id.contact_photo_image_view);

        collapsingToolbarLayout.setTitle(person.getName());
        contactImageView.setImageResource(person.getImageID());

        // 显示联系人号码和邮箱地址
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contact_detail_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DetailAdapter detailAdapter = new DetailAdapter(mdetailList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(detailAdapter);
    }

    private void readCallLog() {
        String[] callLogProjection = new String[]{
                CallLog.Calls.NUMBER,
                CallLog.Calls.CACHED_FORMATTED_NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION
        };
        Cursor cursor = this.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                callLogProjection,
                null,
                null,
                CallLog.Calls.DEFAULT_SORT_ORDER
        );
//
        if (cursor != null && cursor.moveToFirst()) {
            int count = 0;
            do {
                CallLogs temp = new CallLogs(
                        cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER)),
                        2,
                        cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE)),
                        cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)),
                        cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION))
                );
                mCallLogsList.add(temp);
                count++;
            } while (cursor.moveToNext() && count<3);
        }
        cursor.close();
    }

    private void deleteContact(Contact person) {
        // 创建内容提供器操作列表
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        // 添加内容提供器操作，删除联系人在raw_contact和data表中的数据（才能彻底删除）
        ops.add(
                ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=" + person.getId(), null)
                .build()
        );
        ops.add(
                ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                .withSelection(ContactsContract.Data.RAW_CONTACT_ID + "=" + person.getId(), null)
                .build()
        );

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
