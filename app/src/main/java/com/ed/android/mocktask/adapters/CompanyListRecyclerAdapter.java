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

import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CompanyListRecyclerAdapter extends RecyclerView.Adapter<CompanyListRecyclerAdapter.CompanyListViewHolder> {

    private int mLayoutId;
    private CompanyListViewModel mCompanyListViewModel;
    private RealmResults<Company> mCompanyResults;


    public CompanyListRecyclerAdapter(@LayoutRes int layoutId, CompanyListViewModel viewModel) {
        this.mLayoutId = layoutId;
        this.mCompanyListViewModel = viewModel;
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
        companyListViewHolder.bind(obj, mCompanyListViewModel, i);
        companyListViewHolder.setDataChangeTracking(obj);
    }

    @Override
    public int getItemCount() {
        return mCompanyResults == null ? 0 : mCompanyResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return mLayoutId;
    }

    public void setCompanyList(RealmResults companyList) {
        this.mCompanyResults = companyList;
        notifyDataSetChanged();
    }

    public List<Company> getmCompanyList()
    {
        return mCompanyResults;
    }


    private Company getObjForPosition(int position) {
        return mCompanyResults.get(position);

    }


    class CompanyListViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;
        private RealmObject mObject;


        private RealmChangeListener companyDataChangeListner = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                notifyItemChanged(getAdapterPosition());
            }
        };

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

         void setDataChangeTracking(RealmObject object){
            if (mObject != null)
                mObject.removeChangeListener(companyDataChangeListner);
            this.mObject = object;
            this.mObject.addChangeListener(companyDataChangeListner);
        }


    }

}







