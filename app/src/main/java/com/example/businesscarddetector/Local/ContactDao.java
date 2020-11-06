package com.example.businesscarddetector.Local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.businesscarddetector.Model.ContactModel;

import java.util.List;

@Dao
public interface ContactDao {
    @Insert
    long _insertContact(ContactModel contactModel);

    @Query("SELECT * FROM contact_model")
    LiveData<List<ContactModel>> _getContactsFromDb();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    int _updateContactModel(ContactModel contactModel);

    @Delete
    void _deleteContact(ContactModel contactModel);

    @Query("SELECT * FROM contact_model WHERE id=:id")
    ContactModel _getContactById(Long id);

    @Query("SELECT * FROM contact_model WHERE company_name=:cName")
    ContactModel _getContactByCName(String cName);

    @Query("SELECT * FROM contact_model WHERE company_name LIKE'%' ||:companyName|| '%'")
    LiveData<List<ContactModel>> _getSearchContactsFromDbByCName(String companyName);

    @Query("SELECT * FROM contact_model WHERE person_name LIKE'%' ||:personName|| '%'")
    LiveData<List<ContactModel>> _getSearchContactsFromDbByPName(String personName);

    @Query("SELECT * FROM contact_model WHERE designation LIKE'%' ||:designation|| '%'")
    LiveData<List<ContactModel>> _getSearchContactsFromDbByDesig(String designation);

    @Query("SELECT * FROM contact_model WHERE contact_no LIKE'%' ||:contactNumber|| '%'")
    LiveData<List<ContactModel>> _getSearchContactsFromDbByContact(String contactNumber);

    @Query("SELECT * FROM contact_model WHERE email_address LIKE'%' ||:emailAddr|| '%'")
    LiveData<List<ContactModel>> _getSearchContactsFromDbByEmail(String emailAddr);
}
