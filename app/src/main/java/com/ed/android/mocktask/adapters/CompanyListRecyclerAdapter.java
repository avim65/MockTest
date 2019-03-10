package com.ed.android.mocktask.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.ed.android.mocktask.BR;
import com.ed.android.mocktask.models.Company;
import com.ed.android.mocktask.viewmodels.CompanyListViewModel;

import java.util.List;

public class CompanyListRecyclerAdapter extends RecyclerView.Adapter<CompanyListRecyclerAdapter.CompanyListViewHolder> {

    private int layoutId;
    private CompanyListViewModel companyListViewModel;
    private List<Company> mCompanyList;

    public CompanyListRecyclerAdapter(@LayoutRes int layoutId, CompanyListViewModel viewModel) {
        this.layoutId = layoutId;
        this.companyListViewModel = viewModel;
    }


    @NonNull
    @Override
    public CompanyListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, viewGroup, false);
        return new CompanyListViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull CompanyListViewHolder companyListViewHolder, int i) {
        Company obj = getObjForPosition(i);
        companyListViewModel.setListItemPosition(i);
        companyListViewHolder.bind(obj, companyListViewModel, i);
    }

    @Override
    public int getItemCount() {
        return mCompanyList == null ? 0 : mCompanyList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    public void setCompanyList(List companyList) {
        this.mCompanyList = companyList;
        notifyDataSetChanged();
    }

    public List<Company> getmCompanyList()
    {
        return mCompanyList;
    }


    private Company getObjForPosition(int position) {
        return mCompanyList.get(position);

    }


    class CompanyListViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        CompanyListViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Company obj, CompanyListViewModel viewModel, int position) {
            binding.setVariable(BR.companyObj, obj);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }

    }

}







