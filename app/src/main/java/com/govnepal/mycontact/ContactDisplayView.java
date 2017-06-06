package com.govnepal.mycontact;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactDisplayView extends AppCompatActivity {
    ExpandableListView expandableListView;

    EditText Search;

    DatabaseInserter inserter;
    ContactDisplayAdapter contactDisplayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_display_view);

        expandableListView=(ExpandableListView)findViewById(R.id.expandableListView);
        Search = (EditText) findViewById(R.id.etSearch);

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                setSearchedData(Search.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try
        {
            final List<String> heading_item=new ArrayList<String>();
            final HashMap<String,List<String>> child_items=new HashMap<String, List<String>>();
            String empName,mobileNo,homeNo,officeNo,email;
            int count=0;
            List<String> L3;

            inserter=new DatabaseInserter(ContactDisplayView.this);
            SQLiteDatabase db=inserter.getWritableDatabase();
            Cursor res=inserter.getAllData(db);
            while (res.moveToNext())
            {
                empName=res.getString(1);
                mobileNo=res.getString(2);
                homeNo=res.getString(3);
                officeNo=res.getString(4);
                email=res.getString(5);
                heading_item.add(empName);
                L3=new ArrayList<String>();
                L3.add("MobileNo:"+(mobileNo));
                L3.add ("HomeNo:"+homeNo);
                L3.add("OfficeNo:"+officeNo);
                L3.add("Email:"+email);
                child_items.put(empName,L3);
            }
            contactDisplayAdapter=new ContactDisplayAdapter(this,heading_item,child_items);
            expandableListView.setAdapter(contactDisplayAdapter);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setSearchedData(String name)
    {

            final List<String> heading_item=new ArrayList<String>();
            final HashMap<String,List<String>> child_items=new HashMap<String, List<String>>();
            String empName,mobileNo,homeNo,officeNo,email;
            int count=0;
            List<String> L3;

            inserter=new DatabaseInserter(ContactDisplayView.this);
            //SQLiteDatabase db=inserter.getWritableDatabase();
            Cursor res=inserter.getSearchedData(name);
            while (res.moveToNext())
            {
                empName=res.getString(1);
                mobileNo=res.getString(2);
                homeNo=res.getString(3);
                officeNo=res.getString(4);
                email=res.getString(5);
                heading_item.add(empName);
                L3=new ArrayList<String>();
                L3.add("MobileNo:"+(mobileNo));
                L3.add ("HomeNO:"+homeNo);
                L3.add("OfficeNo:"+officeNo);
                L3.add("Email:"+email);
                child_items.put(empName,L3);
            }
            contactDisplayAdapter=new ContactDisplayAdapter(this,heading_item,child_items);
            expandableListView.setAdapter(contactDisplayAdapter);
    }
}
