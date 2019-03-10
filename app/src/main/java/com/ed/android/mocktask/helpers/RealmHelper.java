package com.ed.android.mocktask.helpers;

import com.ed.android.mocktask.interfaces.DBWriteCallback;
import com.ed.android.mocktask.models.Company;
import com.ed.android.mocktask.models.Employee;
import com.ed.android.mocktask.utils.RealmLiveData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmHelper {


    private static RealmHelper realmHelper = null;

    public static final String DB_NAME = "myConmpany.realm";


    public static RealmHelper getSingleToneInstance() {
        if (realmHelper == null) {
            realmHelper = new RealmHelper();
        }

        return realmHelper;
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


    public List<Company> getAllCompanyData() {
        RealmResults<Company> companyRealmResults = getRealmInstance().where(Company.class)
                .sort("id", Sort.DESCENDING)
                .findAll()
                .where()
                .sort("claps", Sort.DESCENDING)
                .findAll()
                .where()
                .sort("name", Sort.DESCENDING)
                .findAll();
        return getRealmInstance().copyFromRealm(companyRealmResults);
    }


    public boolean hasCompanyData() {
        if (getRealmInstance().where(Company.class).count() > 0L) return true;
        else return false;
    }

    public List<Company> getCompanyListBySearch(String companyName) {
        RealmResults<Company> companyRealmResults = getRealmInstance().where(Company.class)
                .contains("name", companyName, Case.INSENSITIVE)
                .findAll();
        return getRealmInstance().copyFromRealm(companyRealmResults);
    }

    public List<Company> getASCSortedCompanyList() {

        RealmResults<Company> sortedCompanyList = getRealmInstance().where(Company.class)
                .sort("id", Sort.ASCENDING)
                .findAll()
                .where()
                .sort("claps", Sort.ASCENDING)
                .findAll()
                .where()
                .sort("name", Sort.ASCENDING)
                .findAll();

        return getRealmInstance().copyFromRealm(sortedCompanyList);
    }


    public List<Employee> getASCSortedEmployeeList(int companyId) {

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


        return getRealmInstance().copyFromRealm(sortedCompanyList);
    }

    public List<Employee> getEmployeeListBySearch(String employeeName, int companyId) {
        RealmResults<Employee> companyRealmResults = getRealmInstance().where(Employee.class)
                .equalTo("company.id", companyId)
                .contains("name", employeeName, Case.INSENSITIVE)
                .findAll();

        return getRealmInstance().copyFromRealm(companyRealmResults);

    }


    public List<Employee> getAllEmployee(int companyId) {
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


        return getRealmInstance().copyFromRealm(companyRealmResults);

    }


    public void updateCompanyClapsCount(final int companyId,final int  clapsCount) {


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


}
