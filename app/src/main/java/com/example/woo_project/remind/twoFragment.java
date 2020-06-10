package com.example.woo_project.remind;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;

public class twoFragment extends Fragment {
    private TextView mTextTitle;
    RecyclerView remind_rv;
    RecyclerView expanderRecyclerView;

    public twoFragment() {
        // Requires empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.twofragment_layout, container, false);

        expanderRecyclerView = root.findViewById(R.id.recyclerView);


        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> parentList = new ArrayList<>();
        ArrayList<ArrayList> childListHolder = new ArrayList<>();

        parentList.add("胡蘿蔔");
        parentList.add("番茄");
        parentList.add("青椒");

        ArrayList<String> childList = new ArrayList<>();
        childList.add("#A1");
        childList.add("#A2");
        childList.add("#A3");

        childListHolder.add(childList);

        childList = new ArrayList<>();
        childList.add("#B1");
        childList.add("#B2");
        childList.add("#B3");

        childListHolder.add(childList);

        childList = new ArrayList<>();
        childList.add("#C1");
        childList.add("#C2");
        childList.add("#C3");

        childListHolder.add(childList);

        ExpandableRecyclerViewAdapter expandableCategoryRecyclerViewAdapter =
                new ExpandableRecyclerViewAdapter(getActivity().getApplicationContext(), parentList,
                        childListHolder);

        expanderRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        expanderRecyclerView.setAdapter(expandableCategoryRecyclerViewAdapter);

    }


}
