package com.ed.android.mocktask.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;
import android.view.Menu;

import com.ed.android.mocktask.R;
import com.ed.android.mocktask.adapters.CompanyListRecyclerAdapter;
import com.ed.android.mocktask.helpers.RealmHelper;
import com.ed.android.mocktask.interfaces.DBWriteCallback;
import com.ed.android.mocktask.interfaces.OnFragmentInteractionListener;
import com.ed.android.mocktask.models.Company;
import com.ed.android.mocktask.models.MenuSortStatus;
import com.ed.android.mocktask.utils.MenuSort;

import java.io.InputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CompanyListViewModel extends ViewModel implements DBWriteCallback {


    private CompanyListRecyclerAdapter mCompanyRecycleAdapter;
    private MutableLiveData<Company> companyListItemClicked = new MutableLiveData<>();
    private List<Company> companySearchedList;
    private List<Company> sortedCompanyList;
    private MutableLiveData<Company> companyListClapsIconClick = new MutableLiveData<>();
    private RealmResults<Company> companyUpdatedData;
    private int listItemPosition;
    private MenuSortStatus menuSortStatus;
    public MutableLiveData<String> mError = new MutableLiveData<>();
    public MutableLiveData<String> mEmpCount = new MutableLiveData<>();
    public MutableLiveData<String> mCompanyName = new MutableLiveData<>();
    public MutableLiveData<String> mCompanyClaps = new MutableLiveData<>();
    public MutableLiveData<String> mCompanyAddress = new MutableLiveData<>();


    private OnFragmentInteractionListener mOnFragmentInteractionListener;

    public void init() {
        menuSortStatus = new MenuSortStatus();
        mCompanyRecycleAdapter = new CompanyListRecyclerAdapter(R.layout.company_list_item, this);
    }

    public void createCompanyFromJson(InputStream inputStream) {
        RealmHelper.getSingleToneInstance().insertListIntoDB(inputStream, this);
    }

    public void setFragmentTransactionListner(OnFragmentInteractionListener onFragmentInteractionListener) {
        mOnFragmentInteractionListener = onFragmentInteractionListener;

    }


    public CompanyListRecyclerAdapter getCompanyRecyclerAdapter() {
        return mCompanyRecycleAdapter;
    }

    @Override
    public void onDBWriteSuccess(List listData) {
        menuSortStatus.setMenuSort(MenuSort.DSC);
        setCompanyDataToList(listData);
    }

    private void setCompanyDataToList(List companyDataToList) {
        this.mCompanyRecycleAdapter.setCompanyList(companyDataToList);
        this.mCompanyRecycleAdapter.notifyDataSetChanged();
    }

    public void onItemClick(Company companyObj) {
        companyListItemClicked.setValue(companyObj);
    }

    public MutableLiveData<Company> getSelected() {
        return companyListItemClicked;
    }


    public void getSearchedCompany(String companyName) {
        if (!TextUtils.isEmpty(companyName)) {
            companySearchedList = RealmHelper.getSingleToneInstance().getCompanyListBySearch(companyName);
            setCompanyDataToList(companySearchedList);
        } else {
            setCompanyDataToList(RealmHelper.getSingleToneInstance().getAllCompanyData());
        }
    }


    public void sortCompanyList() {

        if (menuSortStatus.getMenuSort() == MenuSort.ASC) {
            menuSortStatus.setMenuSort(MenuSort.DSC);
            setCompanyDataToList(RealmHelper.getSingleToneInstance().getAllCompanyData());
        } else {
            menuSortStatus.setMenuSort(MenuSort.ASC);
            setCompanyDataToList(RealmHelper.getSingleToneInstance().getASCSortedCompanyList());
        }

    }


    public void onClapsItemClick(Company companyObj,int listItemPosition) {
        companyListClapsIconClick.setValue(companyObj);
        mCompanyRecycleAdapter.getmCompanyList().get(listItemPosition).setClaps(getUpdatedClapsCount(companyObj));
        mCompanyRecycleAdapter.notifyDataSetChanged();
    }


    public int getUpdatedClapsCount(Company company) {
        int clapsCount = company.getClaps() + 1;
        return clapsCount;
    }


    public MutableLiveData<Company> getCompanyListClapsIconClick() {
        return companyListClapsIconClick;
    }

    public void updateClapsCount(Company companyObj) {


        RealmHelper.getSingleToneInstance().updateCompanyClapsCount(companyObj.getId(), getUpdatedClapsCount(companyObj));
    }


    public int getListItemPosition() {
        return listItemPosition;
    }

    public void setListItemPosition(int listItemPosition) {
        this.listItemPosition = listItemPosition;
    }


}
