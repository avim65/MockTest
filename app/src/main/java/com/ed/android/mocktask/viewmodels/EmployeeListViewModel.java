package com.ed.android.mocktask.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.view.Menu;

import com.ed.android.mocktask.R;
import com.ed.android.mocktask.adapters.EmployeeListRecyclerAdapter;
import com.ed.android.mocktask.helpers.RealmHelper;
import com.ed.android.mocktask.interfaces.DBWriteCallback;
import com.ed.android.mocktask.interfaces.OnFragmentInteractionListener;
import com.ed.android.mocktask.models.Company;
import com.ed.android.mocktask.models.Employee;
import com.ed.android.mocktask.models.MenuSortStatus;
import com.ed.android.mocktask.utils.MenuSort;

import java.util.List;

import io.realm.Sort;

public class EmployeeListViewModel extends ViewModel implements DBWriteCallback {

    private EmployeeListRecyclerAdapter mEmployeeRecyclerAdapter;
    private OnFragmentInteractionListener mOnFragmentInteractionListener;
    private List<Employee> mEmployeeSearchedList;
    private MenuSortStatus menuSortStatus;

    public void init() {
        menuSortStatus = new MenuSortStatus();
        mEmployeeRecyclerAdapter = new EmployeeListRecyclerAdapter(R.layout.employee_list_item, this);
    }

    public EmployeeListRecyclerAdapter getEmployeeRecyclerAdapter() {
        return mEmployeeRecyclerAdapter;
    }

    @Override
    public void onDBWriteSuccess(List listData) {
        menuSortStatus.setMenuSort(MenuSort.DSC);
        setEmployeeDataToList(listData);
    }

    public void setEmployeeDataToList(List employeeList) {
        this.mEmployeeRecyclerAdapter.seEmployeeList(employeeList);
        this.mEmployeeRecyclerAdapter.notifyDataSetChanged();
    }

    public void getSearchedCompany(String companyName, int companyId) {

        if (!TextUtils.isEmpty(companyName)) {
            mEmployeeSearchedList = RealmHelper.getSingleToneInstance().getEmployeeListBySearch(companyName, companyId);
            setEmployeeDataToList(mEmployeeSearchedList);
        } else {
            setEmployeeDataToList(RealmHelper.getSingleToneInstance().getAllEmployee(companyId));
        }
    }

    public void getSortedEmployeeList(int companyId) {

        if (menuSortStatus.getMenuSort() == MenuSort.ASC) {
            menuSortStatus.setMenuSort(MenuSort.DSC);
            setEmployeeDataToList(RealmHelper.getSingleToneInstance().getAllEmployee(companyId));
        } else {
            menuSortStatus.setMenuSort(MenuSort.ASC);
            setEmployeeDataToList(RealmHelper.getSingleToneInstance().getASCSortedEmployeeList(companyId));
        }
    }


}