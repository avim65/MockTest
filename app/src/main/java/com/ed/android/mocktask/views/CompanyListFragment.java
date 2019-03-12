package com.ed.android.mocktask.views;

import android.arch.lifecycle.Observer;
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
import android.widget.Toast;

import com.ed.android.mocktask.R;
import com.ed.android.mocktask.databinding.CompanyListFragmentBinding;
import com.ed.android.mocktask.interfaces.OnFragmentInteractionListener;
import com.ed.android.mocktask.models.Company;
import com.ed.android.mocktask.utils.MenuSort;
import com.ed.android.mocktask.viewmodels.CompanyListViewModel;

import java.io.InputStream;


public class CompanyListFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener, MenuItem.OnMenuItemClickListener {

    private OnFragmentInteractionListener mListener;
    private CompanyListViewModel mCompanyViewModel;

    private static final String TAG = CompanyListFragment.class.getSimpleName();

    public static CompanyListFragment newInstance() {
        CompanyListFragment fragment = new CompanyListFragment();
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
        CompanyListFragmentBinding binding = DataBindingUtil.inflate(inflater, R
                .layout.company_list_fragment, container, false);
        mCompanyViewModel = ViewModelProviders.of(this).get(CompanyListViewModel.class);
        binding.setModel(mCompanyViewModel);
        m_SetupViewModel();
        return binding.getRoot();
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
//        mCompanyViewModel.removeChangeListner();
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
        if (!TextUtils.isEmpty(s))
            mCompanyViewModel.getSearchedCompany(s);
        return true;
    }

    @Override
    public boolean onClose() {
        mCompanyViewModel.getSearchedCompany("");
        return false;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        mCompanyViewModel.sortCompanyList();

        return false;
    }


    private void m_SetupListItemClick() {
        mCompanyViewModel.getSelected().observe(this, new Observer<Company>() {
            @Override
            public void onChanged(Company company) {
                if (company != null) {
                    mListener.onFragmentInteraction(company);
                }

            }
        });

        mCompanyViewModel.getCompanyListClapsIconClick().observe(this, new Observer<Company>() {
            @Override
            public void onChanged(Company company) {
                if (company != null) {
                    mCompanyViewModel.updateClapsCount(company);
                }

            }
        });
    }

    private void m_SetupViewModel() {
        InputStream inputStream = getResources().openRawResource(R.raw.company_info);
        mCompanyViewModel.init();
        mCompanyViewModel.createCompanyFromJson(inputStream);
        mCompanyViewModel.setFragmentTransactionListner(mListener);
        m_SetupListItemClick();
    }


}
