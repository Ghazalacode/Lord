package com.example.hossam.lord.StatsActivity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hossam.lord.R;
import com.example.hossam.lord.StatsActivity.Data.Model.Employee;

import java.util.ArrayList;

public class CustomListAdapterDialog extends BaseAdapter {

private ArrayList<Employee> listData;

private LayoutInflater layoutInflater;

public CustomListAdapterDialog(Context context, ArrayList<Employee> listData) {
    this.listData = listData;
    layoutInflater = LayoutInflater.from(context);
}

@Override
public int getCount() {
    return listData.size();
}

@Override
public Object getItem(int position) {
    return listData.get(position);
}

@Override
public long getItemId(int position) {
    return position;
}

public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
        convertView = layoutInflater.inflate(R.layout.list_row_dialog, null);
        holder = new ViewHolder();
        holder.employeeName = (TextView) convertView.findViewById(R.id.tv_employee_name);
        holder.date = (TextView) convertView.findViewById(R.id.tv_employee_no);
        convertView.setTag(holder);
    } else {
        holder = (ViewHolder) convertView.getTag();
    }

    holder.employeeName.setText(listData.get(position).getEmployeeName());
    holder.date.setText(String.valueOf(listData.get(position).getdate()));


    return convertView;
}

static class ViewHolder {
    TextView employeeName;
    TextView date;
}

}