package com.ed.android.mocktask.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ed.android.mocktask.BaseActivity;
import com.ed.android.mocktask.R;

public class AddNewCompanyDialog extends DialogFragment {

    private TextInputLayout mTextInputEmpCount;
    private TextInputLayout mTextInputCompanyName;

    private TextInputLayout mTextInputEmpAddress;
    private TextInputLayout mTextInputEmpClaps;


    private TextInputEditText mEditEmpCount;
    private TextInputEditText mEditCompanyName;

    private TextInputEditText mEditEmpAddress;
    private TextInputEditText mEditEmpClaps;


    private String mEmpCount;
    private String mCompanyName;
    private String mEmpAddress;
    private String mEmpClaps;


    private View rootView;
    private BaseActivity activity;

    public static AddNewCompanyDialog newInstance(BaseActivity activity) {
        AddNewCompanyDialog dialog = new AddNewCompanyDialog();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle(R.string.dialog_company_title)
                .setCancelable(false)
                .setPositiveButton(R.string.done, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
//        alertDialog.setOnShowListener(dialog -> {
//            onDialogShow(alertDialog);
//        });
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.add_new_company_dialog, null, false);

        mTextInputEmpCount = rootView.findViewById(R.id.text_input_emp_count);
        mTextInputCompanyName = rootView.findViewById(R.id.text_input_emp_name);
        mTextInputEmpAddress = rootView.findViewById(R.id.text_input_emp_address);
        mTextInputEmpClaps = rootView.findViewById(R.id.text_input_company_claps);


        mEditEmpCount = rootView.findViewById(R.id.et_employee_count);
        mEditCompanyName = rootView.findViewById(R.id.et_employee_name);
        mEditEmpAddress = rootView.findViewById(R.id.et_emp_address);
        mEditEmpClaps = rootView.findViewById(R.id.et_company_claps);


        addTextWatchers();
    }

    private void onDialogShow(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoneClicked();
            }
        });
    }

    private void onDoneClicked() {

        if (isValidInfo(mTextInputEmpCount, mEmpCount) && isValidInfo(mTextInputCompanyName, mCompanyName) &&
                isValidInfo(mTextInputEmpAddress, mEmpAddress) && isValidInfo(mTextInputEmpClaps, mEmpClaps)) {
            activity.onPlayersSet(player1, player2);
            dismiss();
        }
    }

    private boolean isValidInfo(TextInputLayout layout, String inputInfo) {
        if (TextUtils.isEmpty(inputInfo)) {
            layout.setErrorEnabled(true);
            layout.setError(getString(R.string.error_string));
            return false;
        }

        layout.setErrorEnabled(false);
        layout.setError("");
        return true;
    }

    private void addTextWatchers() {


        mEditEmpCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmpCount = s.toString();
            }
        });
        mEditCompanyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mCompanyName = s.toString();
            }
        });

        mEditEmpAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmpAddress = s.toString();
            }
        });


        mEditEmpClaps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmpClaps = s.toString();
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}