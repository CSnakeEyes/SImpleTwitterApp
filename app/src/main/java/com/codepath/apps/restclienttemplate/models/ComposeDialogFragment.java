package com.codepath.apps.restclienttemplate.models;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;

public class ComposeDialogFragment extends DialogFragment {

    private EditText nameEt;
    private TextView nameTv;
    private ImageView profileEt;

//    public interface ComposeDialogListener {
//        void onFinishEditDialog(String inputText);
//    }
//
//    private EditText nameEt;
//    private TextView nameTv;
//    private ImageView profileEt;
//
//    public ComposeDialogFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_compose_fragment, container);
//
//        nameEt = view.findViewById(R.id.name_et);
//        nameTv = view.findViewById(R.id.name_tv);
//
//        getDialog().setTitle("Hello!");
//
//        nameEt.requestFocus();
//        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        nameEt.setOnEditorActionListener(this);
//
//        return view;
//    }
//
//    //@Override
//    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//        if (EditorInfo.IME_ACTION_DONE == actionId) {
//            // Return input text to activity
//            ComposeDialogListener activity = (ComposeDialogListener) getActivity();
//            activity.onFinishEditDialog(nameEt.getText().toString());
//            this.dismiss();
//            return true;
//        }
//        return false;
//    }

    public ComposeDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static ComposeDialogFragment newInstance(String title) {
        ComposeDialogFragment frag = new ComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_compose_fragment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        nameEt = view.findViewById(R.id.tweet_et);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        nameEt.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }
}
