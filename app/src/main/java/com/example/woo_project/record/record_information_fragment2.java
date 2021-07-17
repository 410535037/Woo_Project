package com.example.woo_project.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woo_project.R;

import java.util.ArrayList;
import java.util.List;

public class record_information_fragment2 extends Fragment {
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.record_information_fragment, container, false);


        List<record_information_cardview> cardviewList = new ArrayList<>();
        // cardviewList.add(new record_information_cardview(0, "第一週(19/04/27~19/04/28)", "澆水25次", "施肥1次", "間拔5次", "澆水25次", "施肥1次", "間拔5次", "澆水25次"));
        cardviewList.add(new record_information_cardview(0,"01","2020.05.13 10:01","第一週"));
        cardviewList.add(new record_information_cardview(1,"02","2020.06.20 11:20","第二週"));
        cardviewList.add(new record_information_cardview(2,"03","2020.07.27 12:35","第三週"));
        //public record_information_cardview(int id,String day_or_week_num,String datetime,String content)


        recyclerView = view.findViewById(R.id.record_info_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new record_information_cardviewAdapter.record_info_CardAdapter(getActivity(), cardviewList));

        return view;
    }

}
