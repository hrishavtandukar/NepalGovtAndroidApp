package com.govnepal.mycontact;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class ContactDisplayAdapter extends BaseExpandableListAdapter {
    private List<String> header_titles;
    private HashMap<String,List<String>> child_title;
    private Context ctx;

    public ContactDisplayAdapter(Context ctx,List<String> header_titles, HashMap<String,List<String>> child_title)
    {
        this.header_titles=header_titles;
        this.child_title=child_title;
        this.ctx=ctx;
    }


    @Override
    public int getGroupCount() {
        return header_titles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String headerItem=header_titles.get(groupPosition);
        List<String> childs=child_title.get(headerItem);
        int noOfChilds=childs.size();
        return noOfChilds;
    }

    @Override
    public Object getGroup(int groupPosition) {
        String headerItem=header_titles.get(groupPosition);
        return headerItem;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String headerItem=header_titles.get(groupPosition);
        List<String> childs=child_title.get(headerItem);
        String childItem=childs.get(childPosition);
        return childItem;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       String title=(String)this.getGroup(groupPosition);
        if (convertView==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.parent_layout,null);
        }
        TextView textView=(TextView)convertView.findViewById(R.id.heading_item);//
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String title =(String)this.getChild(groupPosition,childPosition);
        LayoutInflater layoutInflater=(LayoutInflater)this.ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if ((convertView==null) || (((Integer)convertView.getTag())!=childPosition)){

            if (childPosition==0)
            {
                convertView=layoutInflater.inflate(R.layout.mobile_layout,null);
                convertView.setTag(0);

                Button callBtn_mobile=(Button)convertView.findViewById(R.id.callBtn_mobile);

                final Button smsBtn_sms=(Button)convertView.findViewById(R.id.smsBtn_sms);

                final TextView mobile_item = (TextView) convertView.findViewById(R.id.mobile_item);

                callBtn_mobile.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        String mobile_no=(String)mobile_item.getText();
                        mobile_no=mobile_no.substring(mobile_no.indexOf(":")+1,mobile_no.length());
                        Intent phoneCallIntent=new Intent(Intent.ACTION_CALL);
                        phoneCallIntent.setData(Uri.parse("tel:"+mobile_no));

                        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                        {
                            Log.i("ERROR:","Cannot have permission");
                            return;
                        }
                      ctx.startActivity(phoneCallIntent);
                    }
                });

                smsBtn_sms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mobile_no=(String)mobile_item.getText();
                        mobile_no=mobile_no.substring(mobile_no.indexOf(":")+1,mobile_no.length());

                        Intent phoneSmsIntent = new Intent(Intent.ACTION_SENDTO);
                        phoneSmsIntent.setType("vnd.android-dir/mms-sms");
                        phoneSmsIntent.setData(Uri.parse("sms:" +mobile_no));

                        ctx.startActivity(phoneSmsIntent);
                    }
                });
            }

            if (childPosition==1)
            {
                convertView=layoutInflater.inflate(R.layout.office_layout,null);
                convertView.setTag(1);

                Button callBtn_office=(Button)convertView.findViewById(R.id.callBtn_office);
                final TextView office_Item = (TextView) convertView.findViewById(R.id.office_item);

                callBtn_office.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String office_no=(String)office_Item.getText();
                        office_no=office_no.substring(office_no.indexOf(":")+1,office_no.length());
                        Intent phoneCallInent=new Intent(Intent.ACTION_CALL);
                        phoneCallInent.setData(Uri.parse("tel:"+office_no));
                        if (ActivityCompat.checkSelfPermission(ctx,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                        {
                            return;
                        }
                        ctx.startActivity(phoneCallInent);
                    }
                });
            }

            if (childPosition==2){
                convertView=layoutInflater.inflate(R.layout.home_layout,null);
                convertView.setTag(2);

                Button callBtn_home=(Button)convertView.findViewById(R.id.callBtn_home);
                final TextView home_Item = (TextView) convertView.findViewById(R.id.home_item);

                callBtn_home.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String home_no=(String)home_Item.getText();
                        home_no=home_no.substring(home_no.indexOf(":")+1,home_no.length());
                        Log.i("INfo:",home_no);
                        Intent phoneCallInent=new Intent(Intent.ACTION_CALL);
                        phoneCallInent.setData(Uri.parse("tel:"+home_no));


                        if (ActivityCompat.checkSelfPermission(ctx,Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                        {
                            return;
                        }
                        ctx.startActivity(phoneCallInent);
                    }
                });
            }

            if (childPosition==3){
                convertView=layoutInflater.inflate(R.layout.email_layout,null);
                convertView.setTag(3);
            }
        }

        if (childPosition==0){
            final TextView mobile_item= (TextView) convertView.findViewById(R.id.mobile_item);
            mobile_item.setText(title);
        }

        if (childPosition==1){
            final TextView office_item= (TextView) convertView.findViewById(R.id.office_item);
            office_item.setText(title);
        }

        if (childPosition==2){
            final TextView home_item= (TextView) convertView.findViewById(R.id.home_item);
            home_item.setText(title);
        }

        if (childPosition==3){
            final TextView email_item= (TextView) convertView.findViewById(R.id.email_item);
            email_item.setText(title);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
