package com.arrow.contacts.activities;

import android.content.ContentProviderOperation;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arrow.contacts.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditActivity extends AppCompatActivity {

    private  static final String[] nType = {
            "住宅",
            "手机",
            "单位",
            "单位传真",
            "住宅传真",
            "其他"
    };

    private static final String[] eType = {
            "住宅",
            "工作",
            "其他",
            "个人"
    };

    private Map<String, String> numberTypeMap = new HashMap<>();
    private Map<String, String> emailTypeMap = new HashMap<>();

    private static List<String> numberType = Arrays.asList(nType);
    private static List<String> emailType = Arrays.asList(eType);
    private MaterialEditText lastName;
    private MaterialEditText firstName;
    private MaterialEditText domain;
    private MaterialEditText number;
    private MaterialEditText email;
    private MaterialSpinner nTypeSpinner;
    private MaterialSpinner eTypeSpinner;

    private String name;
    private String cDomain;
    private String pNumber;
    private String numType;
    private String pEmail;
    private String emaType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);

        numberTypeMap.put("住宅", "1");
        numberTypeMap.put("手机", "2");
        numberTypeMap.put("单位", "3");
        numberTypeMap.put("单位传真", "4");
        numberTypeMap.put("住宅传真", "5");
        numberTypeMap.put("其他", "7");

        emailTypeMap.put("住宅", "1");
        emailTypeMap.put("工作", "2");
        emailTypeMap.put("其他", "3");
        emailTypeMap.put("个人", "4");

        lastName = (MaterialEditText) findViewById(R.id.person_last_name);
        firstName = (MaterialEditText) findViewById(R.id.person_first_name);
        domain = (MaterialEditText) findViewById(R.id.person_company_name);
        number = (MaterialEditText) findViewById(R.id.person_number);
        email = (MaterialEditText) findViewById(R.id.person_email);

        nTypeSpinner = (MaterialSpinner) findViewById(R.id.person_number_type);
        nTypeSpinner.setItems(numberType);
        nTypeSpinner.setSelectedIndex(0);

        eTypeSpinner = (MaterialSpinner) findViewById(R.id.person_email_type);
        eTypeSpinner.setItems(emailType);
        eTypeSpinner.setSelectedIndex(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_action_save:
                getUIData();
                saveContact();
                finish();
                return true;
                default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void getUIData() {
        name = lastName.getText().toString() + firstName.getText().toString();
        cDomain = domain.getText().toString();
        pNumber = number.getText().toString();
        pEmail = email.getText().toString();

        numType = numberTypeMap.get(nType[nTypeSpinner.getSelectedIndex()]);
        emaType = emailTypeMap.get(eType[eTypeSpinner.getSelectedIndex()]);
    }

    private void saveContact() {
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)  // 这里先传入空值，用于添加一个raw_contact空项
                .build()
        );
        ops.add(
                ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)  // 插入姓名
                .build()
        );
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, pNumber)  //插入号码
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, numType)    //插入号码类型
                .build()
        );
        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, pEmail)  //插入电子邮件
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE, emaType)    //插入电子邮件类型
                .build()
        );

        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
