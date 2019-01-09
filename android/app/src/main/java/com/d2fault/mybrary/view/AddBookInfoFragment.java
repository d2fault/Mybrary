package com.d2fault.mybrary.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.d2fault.mybrary.R;
import com.google.zxing.integration.android.IntentIntegrator;
/**
 * A simple {@link Fragment} subclass.
 */
public class AddBookInfoFragment extends Fragment {

    private View view;
    private Activity activity;
    private FloatingActionButton fab_find;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_book_info, container, false);
        initModel();
        initView();
        initListener();

        return view;
    }

    private void initModel() {
        activity = getActivity();
    }

    private void initView() {
        fab_find = view.findViewById(R.id.fab_find);
    }

    private void initListener() {
        fab_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }
}
