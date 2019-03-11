package com.ed.android.mocktask.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ed.android.mocktask.BR;
import com.ed.android.mocktask.models.Employee;
import com.ed.android.mocktask.viewmodels.EmployeeListViewModel;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class EmployeeListRecyclerAdapter extends RecyclerView.Adapter<EmployeeListRecyclerAdapter.EmployeeListViewHolder> {

    private int layoutId;
    private EmployeeListViewModel employeeListViewModel;
    private RealmResults<Employee> mEmployeeResults;

    public EmployeeListRecyclerAdapter(@LayoutRes int layoutId, EmployeeListViewModel viewModel) {
        this.layoutId = layoutId;
        this.employeeListViewModel = viewModel;
    }

    @NonNull
    @Override
    public EmployeeListRecyclerAdapter.EmployeeListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, viewGroup, false);
        return new EmployeeListRecyclerAdapter.EmployeeListViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListRecyclerAdapter.EmployeeListViewHolder employeeListViewHolder, int i) {
        Employee obj = getObjForPosition(i);
        employeeListViewHolder.bind(obj, employeeListViewModel);
        employeeListViewHolder.setDataChangeTracking(obj);
    }

    @Override
    public int getItemCount() {
        return mEmployeeResults == null ? 0 : mEmployeeResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    public void seEmployeeList(RealmResults employeeList) {
        this.mEmployeeResults = employeeList;
        notifyDataSetChanged();
    }

    private Employee getObjForPosition(int position) {
        return mEmployeeResults.get(position);

    }


    class EmployeeListViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;
        private RealmObject mObject;

        private RealmChangeListener companyDataChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {
                notifyItemChanged(getAdapterPosition());
            }
        };

        EmployeeListViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Employee obj, EmployeeListViewModel viewModel) {
            binding.setVariable(BR.employeeObject, obj);
            binding.setVariable(BR.viewModel, viewModel);
            binding.executePendingBindings();
        }

        void setDataChangeTracking(RealmObject object) {
            if (mObject != null)
                mObject.removeChangeListener(companyDataChangeListener);
            this.mObject = object;
            this.mObject.addChangeListener(companyDataChangeListener);
        }

    }

}







