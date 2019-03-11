package com.ed.android.mocktask.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.ed.android.mocktask.BaseActivity;
import com.ed.android.mocktask.R;
import com.ed.android.mocktask.interfaces.OnDialogFragmentInteractionListener;
import com.ed.android.mocktask.models.Company;
import com.ed.android.mocktask.models.Employee;

public class AddNewCompanyDialog extends DialogFragment {

    private TextInputLayout mTextInputEmpCount;
    private TextInputLayout mTextInputCompanyName;
    private TextInputLayout mTextInputAddress;
    private TextInputLayout mTextInputEmpClaps;
    private TextInputLayout mTextInputEmpDept;
    private TextInputLayout mTextInputEmpAge;

    private TextInputEditText mEditEmpCount;
    private TextInputEditText mEditCompanyName;
    private TextInputEditText mEditEmpAddress;
    private TextInputEditText mEditEmpClaps;
    private TextInputEditText mEditEmpDept;
    private TextInputEditText mEditEmpAge;

    private String mEmpCount;
    private String mCompanyName;
    private String mEmpAddress;
    private String mEmpClaps;
    private String mEmpDept;
    private String mEmpAge;
    private OnDialogFragmentInteractionListener mListener;
    private View rootView;
    private Fragment mFragment;
    private AlertDialog alertDialog;

    public static AddNewCompanyDialog newInstance() {
        AddNewCompanyDialog dialog = new AddNewCompanyDialog();
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        m_InitViews();
        alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setCancelable(true)
                .setPositiveButton(R.string.done, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                onDialogShow(alertDialog);
            }
        });
        return alertDialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDialogFragmentInteractionListener) {
            mListener = (OnDialogFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private void m_InitViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.add_new_company_dialog, null, false);

        mTextInputEmpCount = rootView.findViewById(R.id.text_input_emp_count);
        mTextInputCompanyName = rootView.findViewById(R.id.text_input_emp_name);
        mTextInputAddress = rootView.findViewById(R.id.text_input_address);
        mTextInputEmpClaps = rootView.findViewById(R.id.text_input_company_claps);
        mTextInputEmpDept = rootView.findViewById(R.id.text_input_emp_dept);
        mTextInputEmpAge = rootView.findViewById(R.id.text_input_emp_age);


        mEditEmpCount = rootView.findViewById(R.id.et_employee_count);
        mEditCompanyName = rootView.findViewById(R.id.et_employee_name);
        mEditEmpAddress = rootView.findViewById(R.id.et_emp_address);
        mEditEmpClaps = rootView.findViewById(R.id.et_company_claps);
        mEditEmpDept = rootView.findViewById(R.id.et_emp_dept);
        mEditEmpAge = rootView.findViewById(R.id.et_emp_age);

        m_SetLayoutVisibility();
        m_AddTextWatchers();
    }

    private void m_SetLayoutVisibility() {
        mFragment = mListener.getCurrentFragment();
        if (mFragment instanceof CompanyListFragment) {
            mTextInputEmpDept.setVisibility(View.GONE);
            mTextInputEmpAge.setVisibility(View.GONE);
        } else if (mFragment instanceof EmployeeListFragment) {
            mTextInputEmpCount.setVisibility(View.GONE);
            mTextInputEmpClaps.setVisibility(View.GONE);
        }
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

        if (mFragment instanceof CompanyListFragment) {
            if (m_IsValidInfo(mTextInputEmpCount, mEmpCount) && m_IsValidInfo(mTextInputCompanyName, mCompanyName) &&
                    m_IsValidInfo(mTextInputAddress, mEmpAddress) && m_IsValidInfo(mTextInputEmpClaps, mEmpClaps)) {
                mListener.onDataModelPrepared(m_GetCompanyModel());
            }

        } else {
            if (m_IsValidInfo(mTextInputCompanyName, mCompanyName) &&
                    m_IsValidInfo(mTextInputAddress, mEmpAddress) && m_IsValidInfo(mTextInputEmpAge, mEmpAge) && m_IsValidInfo(mTextInputEmpDept, mEmpDept)) {
                mListener.onDataModelPrepared(m_GetEmployeeModel());
            }

        }
        dismiss();


    }

    private Object m_GetCompanyModel() {
        Company companyObj = new Company();
        companyObj.setEmpCount(Integer.parseInt(mEmpCount));
        companyObj.setName(mCompanyName);
        companyObj.setAddress(mEmpAddress);
        companyObj.setClaps(Integer.parseInt(mEmpClaps));
        return companyObj;

    }

    private Object m_GetEmployeeModel() {
        Employee companyObj = new Employee();
        companyObj.setAddress(mEmpAddress);
        companyObj.setName(mCompanyName);
        companyObj.setDepartment(mEmpDept);
        companyObj.setAge(Integer.parseInt(mEmpAge));
        return companyObj;
    }

    private boolean m_IsValidInfo(TextInputLayout layout, String inputInfo) {
        if (TextUtils.isEmpty(inputInfo)) {
            layout.setErrorEnabled(true);
            layout.setError(getString(R.string.error_string));
            return false;
        }

        layout.setErrorEnabled(false);
        layout.setError("");
        return true;
    }

    private void m_AddTextWatchers() {
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

        mEditEmpAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmpAge = s.toString();
            }
        });

        mEditEmpDept.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEmpDept = s.toString();
            }
        });
    }


}