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

public class CompanyListViewModel extends ViewModel implements DBWriteCallback, RealmChangeListener {


    private CompanyListRecyclerAdapter mCompanyRecycleAdapter;
    private MutableLiveData<Company> mCompanyListItemClicked = new MutableLiveData<>();
    private RealmResults<Company> mCompanySearchedList;
    private MutableLiveData<Company> companyListClapsIconClick = new MutableLiveData<>();
    private MenuSortStatus menuSortStatus;
    private int itemPosition = 0;


    private OnFragmentInteractionListener mOnFragmentInteractionListener;

    @Override
    public void onChange(Object o) {

        mCompanyRecycleAdapter.notifyDataSetChanged();
    }

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
    public void onDBWriteSuccess(RealmResults realmResults) {
        menuSortStatus.setMenuSort(MenuSort.DSC);

        setCompanyDataToList(realmResults);
        realmResults.addChangeListener(this);
    }


    private void setCompanyDataToList(RealmResults companyDataToList) {
        this.mCompanyRecycleAdapter.setCompanyList(companyDataToList);
        this.mCompanyRecycleAdapter.notifyDataSetChanged();
    }

    public void onItemClick(Company companyObj) {
        mCompanyListItemClicked.setValue(companyObj);
    }

    public MutableLiveData<Company> getSelected() {
        return mCompanyListItemClicked;
    }


    public void getSearchedCompany(String companyName) {
        if (!TextUtils.isEmpty(companyName)) {
            mCompanySearchedList = RealmHelper.getSingleToneInstance().getCompanyListBySearch(companyName);
            setCompanyDataToList(mCompanySearchedList);
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


    public void onClapsItemClick(Company companyObj, int listItemPosition) {
        RealmHelper.getSingleToneInstance().updateCompanyClapsCount(companyObj.getId(), getUpdatedClapsCount(companyObj));
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

    public void removeChangeListner() {
        removeChangeListner();
    }

}
