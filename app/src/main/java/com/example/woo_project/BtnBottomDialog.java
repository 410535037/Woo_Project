package com.example.woo_project;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.example.woo_project.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.lang.reflect.Method;

import info.hoang8f.android.segmented.SegmentedGroup;

public class BtnBottomDialog extends BottomSheetDialogFragment {

    TextView mm;
    SegmentedGroup checkornot;
    RadioButton bt1;
    ConstraintLayout bbgg;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity(), R.style.Theme_MaterialComponents_BottomSheetDialog);

        View root = LayoutInflater.from(getActivity()).inflate(R.layout.check_dialog, null);
        bbgg=root.findViewById(R.id.bbgg);
        mm=root.findViewById(R.id.mm);
        checkornot=root.findViewById(R.id.checkornot);
        bt1=root.findViewById(R.id.button22);
        dialog.setContentView(root);

        checkornot.setTintColor(Color.parseColor("#FC9D33"),Color.parseColor("#DCdCdC"));

//        Bundle bundle =getArguments();
//        String hh= bundle.getString("OKOK");
//        mm.setText(hh);

//        bbgg.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
//        ((View)bbgg.getParent()).setBackgroundColor(Color.TRANSPARENT);

//        //设置宽度
//        ViewGroup.LayoutParams params = root.getLayoutParams();
//        params.height = (int) (1 *
//                getResources().getDisplayMetrics().heightPixels);
//
//
//        root.setLayoutParams(params);

//        params.height = getHeight();

        Window window = dialog.getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BottomSheet);
            window.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.test));
        }
        return dialog;


    }

}