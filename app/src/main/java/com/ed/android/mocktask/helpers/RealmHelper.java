package com.ed.android.mocktask.helpers;

import com.ed.android.mocktask.interfaces.DBWriteCallback;
import com.ed.android.mocktask.models.Company;
import com.ed.android.mocktask.models.Employee;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmHelper {


    private static RealmHelper mRealmHelper = null;

    public static final String DB_NAME = "myConmpany.realm";


    public static RealmHelper getSingleToneInstance() {
        if (mRealmHelper == null) {
            mRealmHelper = new RealmHelper();
        }

        return mRealmHelper;
    }


    public Realm getRealmInstance() {
        return Realm.getDefaultInstance();
    }

    public void insertListIntoDB(final InputStream inputStream, final DBWriteCallback callback) {

        if (hasCompanyData()) {
            if (callback != null) {
                callback.onDBWriteSuccess(getAllCompanyData());

            }
            return;

        }
        getRealmInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                try {
                    getRealmInstance().createAllFromJson(Company.class, inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (callback != null) {
                    callback.onDBWriteSuccess(getAllCompanyData());
                }
            }
        });
    }


    public RealmResults<Company> getAllCompanyData() {
        RealmResults<Company> companyRealmResults = getRealmInstance().where(Company.class)
                .sort("id", Sort.DESCENDING)
                .findAll()
                .where()
                .sort("claps", Sort.DESCENDING)
                .findAll()
                .where()
                .sort("name", Sort.DESCENDING)
                .findAll();


        return companyRealmResults;
        //  return getRealmInstance().copyFromRealm(companyRealmResults);
    }


    public boolean hasCompanyData() {
        if (getRealmInstance().where(Company.class).count() > 0L) return true;
        else return false;
    }

    public RealmResults<Company> getCompanyListBySearch(String companyName) {
        RealmResults<Company> companyRealmResults = getRealmInstance().where(Company.class)
                .contains("name", companyName, Case.INSENSITIVE)
                .findAll();
        return companyRealmResults;
        //return getRealmInstance().copyFromRealm(companyRealmResults);
    }

    public RealmResults<Company> getASCSortedCompanyList() {

        RealmResults<Company> sortedCompanyList = getRealmInstance().where(Company.class)
                .sort("id", Sort.ASCENDING)
                .findAll()
                .where()
                .sort("claps", Sort.ASCENDING)
                .findAll()
                .where()
                .sort("name", Sort.ASCENDING)
                .findAll();


        return sortedCompanyList;
      //  return getRealmInstance().copyFromRealm(sortedCompanyList);
    }


    public void updateCompanyClapsCount(final int companyId, final int clapsCount) {


        getRealmInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Company companyObj = realm.where(Company.class).equalTo("id", companyId).findFirst();
                companyObj.setClaps(clapsCount);
                realm.copyToRealmOrUpdate(companyObj);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        });
    }


    public void addNewCompany(final Company companyModel) {
        getRealmInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number companyId = realm.where(Company.class).max("id");
                companyModel.setId(companyId.intValue() + 1);
                realm.copyToRealmOrUpdate(companyModel);


            }
        });

    }


    public RealmResults<Employee> getASCSortedEmployeeList(int companyId) {

        RealmResults<Employee> sortedCompanyList = getRealmInstance().where(Employee.class)
                .equalTo("company.id", companyId)
                .findAll()
                .where()
                .sort("name", Sort.ASCENDING)
                .findAll()
                .where()
                .sort("id", Sort.ASCENDING)
                .findAll()

                .where()
                .sort("age", Sort.ASCENDING)
                .findAll();

        return sortedCompanyList;
        //return getRealmInstance().copyFromRealm(sortedCompanyList);
    }

    public RealmResults<Employee> getEmployeeListBySearch(String employeeName, int companyId) {
        RealmResults<Employee> companyRealmResults = getRealmInstance().where(Employee.class)
                .equalTo("company.id", companyId)
                .contains("name", employeeName, Case.INSENSITIVE)
                .findAll();
        return companyRealmResults;
        // return getRealmInstance().copyFromRealm(companyRealmResults);

    }


    public RealmResults<Employee> getAllEmployee(int companyId) {
        RealmResults<Employee> companyRealmResults = getRealmInstance().where(Employee.class).findAll()
                .where()
                .sort("name", Sort.DESCENDING)
                .findAll()
                .where()
                .sort("id", Sort.DESCENDING)
                .findAll()
                .where()
                .sort("age", Sort.DESCENDING)
                .findAll()
                .where()
                .equalTo("company.id", companyId)
                .findAll();

        return companyRealmResults;
        //  return getRealmInstance().copyFromRealm(companyRealmResults);

    }

    public void addNewEmployee(final Employee employeeModel, final int companyId) {
        getRealmInstance().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxEmployeeId = realm.where(Employee.class)
                        .equalTo("company.id",companyId).max("id");
                Company parentCompany = realm.where(Company.class).equalTo("id", companyId).findFirst();

                if(maxEmployeeId==null)
                {
                    maxEmployeeId=0;
                }
                employeeModel.setId(maxEmployeeId.intValue() + 1);
                parentCompany.getEmloyees().add(employeeModel);
                realm.copyToRealm(employeeModel);


            }
        });


    }


}
