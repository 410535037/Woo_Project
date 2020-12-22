package com.example.woo_project.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<String> mainArray ;//父層總陣列
    private HashMap<String, ArrayList<String>> childArray ;//子層陣列

    public ExpandableListAdapter(ArrayList<String> mainArray, HashMap<String, ArrayList<String>> childArray) {
        this.mainArray = mainArray;
        this.childArray = childArray;
    }

    @Override
    public int getGroupCount() {
        return mainArray.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childArray.get(mainArray.get(i)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childArray.get(mainArray.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_select_dropdown_item,viewGroup,false);
        TextView title_tv = view.findViewById(R.id.tv);
        String group_title = String.valueOf(getGroup(groupPosition));
        title_tv.setText(group_title);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.record_select_dropdown_item,viewGroup,false);
        TextView child_tv = view.findViewById(R.id.tv);
        String child_text = String.valueOf(getChild(groupPosition, childPosition));
        child_tv.setText(child_text);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
