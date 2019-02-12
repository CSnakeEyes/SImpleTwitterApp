package com.codepath.apps.restclienttemplate.models;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;

public class ComposeDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    public interface ComposeDialogListener {
        void onFinishEditDialog(String inputText);
    }

    private EditText nameEt;
    private TextView nameTv;
    private ImageView profileEt;

    public ComposeDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_compose_fragment, container);

        nameEt = view.findViewById(R.id.name_et);
        nameTv = view.findViewById(R.id.name_tv);

        getDialog().setTitle("Hello!");

        nameEt.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        nameEt.setOnEditorActionListener(this);

        return view;
    }

    //@Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            ComposeDialogListener activity = (ComposeDialogListener) getActivity();
            activity.onFinishEditDialog(nameEt.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }
}
