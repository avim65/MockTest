package com.ed.android.mocktask;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;

import com.ed.android.mocktask.helpers.RealmHelper;
import com.ed.android.mocktask.interfaces.OnDialogFragmentInteractionListener;
import com.ed.android.mocktask.interfaces.OnFragmentInteractionListener;
import com.ed.android.mocktask.models.Company;
import com.ed.android.mocktask.models.Employee;
import com.ed.android.mocktask.views.AddNewCompanyDialog;
import com.ed.android.mocktask.views.CompanyListFragment;
import com.ed.android.mocktask.views.EmployeeListFragment;

import java.util.List;

import static com.ed.android.mocktask.utils.Util.TAG_COMPANY_FRAGMENT;
import static com.ed.android.mocktask.utils.Util.TAG_EMPLOYEE_FRAGMENT;


public class BaseActivity extends AppCompatActivity implements OnFragmentInteractionListener, SearchView.OnQueryTextListener, OnDialogFragmentInteractionListener<Object>, View.OnClickListener {


    private ImageView mAddNewImage;
    private static String TAG_BASE_ACTIVITY = BaseActivity.class.getSimpleName();
    private int mCompanyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        m_addFragmentToBase(TAG_COMPANY_FRAGMENT, null, 0);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onFragmentInteraction(Company companyObject) {
        m_addFragmentToBase(TAG_EMPLOYEE_FRAGMENT, companyObject.getEmloyees(), companyObject.getId());
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }


    @Override
    public void onDataModelPrepared(Object... args) {

        if (args != null && args[0] instanceof Company) {

            RealmHelper.getSingleToneInstance().addNewCompany((Company) args[0]);

        } else if (args != null && args[0] instanceof Employee) {
            RealmHelper.getSingleToneInstance().addNewEmployee((Employee) args[0], mCompanyId);
        }


    }

    @Override
    public Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.base_container_frame);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_add_new_image:
                promptNewDialog();
                break;
        }

    }

    private void initView() {
        mAddNewImage = (ImageView) findViewById(R.id.base_add_new_image);
        mAddNewImage.setOnClickListener(this);
    }


    private void m_addFragmentToBase(String tagName, List<Employee> employeeList, int companyId) {
        switch (tagName) {

            case TAG_COMPANY_FRAGMENT:
                addFragment(new CompanyListFragment(), null, companyId);

                break;

            case TAG_EMPLOYEE_FRAGMENT:
                mCompanyId = companyId;
                addFragment(new EmployeeListFragment(), employeeList, companyId);
                break;

            default:
                break;


        }
    }

    private void addFragment(Fragment mFragment, List<Employee> employeeList, int companyId) {

        String backStateName = "";
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (mFragment instanceof CompanyListFragment) {
            backStateName = CompanyListFragment.class.getSimpleName();
            transaction.add(R.id.base_container_frame, ((CompanyListFragment) mFragment).newInstance());
        } else {
            backStateName = EmployeeListFragment.class.getSimpleName();
            transaction.add(R.id.base_container_frame, ((EmployeeListFragment) mFragment).newInstance(employeeList, companyId));
        }
        transaction.addToBackStack(backStateName);
        transaction.commit();

    }

    public void promptNewDialog() {
        AddNewCompanyDialog dialog = AddNewCompanyDialog.newInstance();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), TAG_BASE_ACTIVITY);
    }


}
