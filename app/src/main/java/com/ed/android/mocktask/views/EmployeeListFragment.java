package com.ed.android.mocktask.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ed.android.mocktask.R;
import com.ed.android.mocktask.databinding.EmployeeListFragmentBinding;
import com.ed.android.mocktask.helpers.RealmHelper;
import com.ed.android.mocktask.interfaces.OnFragmentInteractionListener;
import com.ed.android.mocktask.models.Employee;
import com.ed.android.mocktask.viewmodels.EmployeeListViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.ed.android.mocktask.utils.Util.TAG_COMPANY_ID;


public class EmployeeListFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, MenuItem.OnMenuItemClickListener {

    private OnFragmentInteractionListener mListener;

    private EmployeeListViewModel mEmployeeListViewModel;

    private List<Employee> employeeArrayList = new ArrayList<>();

    private int mCompanyId = 0;

    private static final String TAG_EMPLOYEE_DATA = "EmployeeList";

    private static final String TAG = EmployeeListFragment.class.getSimpleName();

    public EmployeeListFragment() {
        // Required empty public constructor
    }

    public static EmployeeListFragment newInstance(List<Employee> employeeArrayList, int companyId) {
        EmployeeListFragment fragment = new EmployeeListFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_COMPANY_ID, companyId);
        args.putParcelableArrayList(TAG_EMPLOYEE_DATA, new ArrayList<Employee>(employeeArrayList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        EmployeeListFragmentBinding binding = DataBindingUtil.inflate(inflater, R
                .layout.employee_list_fragment, container, false);
        mEmployeeListViewModel = ViewModelProviders.of(this).get(EmployeeListViewModel.class);
        binding.setModel(mEmployeeListViewModel);
        setupEmployeeViewModel();
        return binding.getRoot();
    }

    private void setupEmployeeViewModel() {
        Bundle args = this.getArguments();
        mCompanyId = args.getInt(TAG_COMPANY_ID, 0);
        employeeArrayList = args.getParcelableArrayList(TAG_EMPLOYEE_DATA);
        mEmployeeListViewModel.init();
        mEmployeeListViewModel.setEmployeeDataToList(RealmHelper.getSingleToneInstance().getAllEmployee(mCompanyId));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menuSearch);
        MenuItem filterItem = menu.findItem(R.id.filter);

        SearchView mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.search_hint));
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
        filterItem.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mEmployeeListViewModel.getSearchedCompany(s
                , mCompanyId);
        return true;
    }

    @Override
    public boolean onClose() {
        mEmployeeListViewModel.getSearchedCompany("", mCompanyId);
        return false;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        mEmployeeListViewModel.getSortedEmployeeList(mCompanyId);
        return false;
    }
}
