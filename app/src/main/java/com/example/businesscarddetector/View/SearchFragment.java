package com.example.businesscarddetector.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.businesscarddetector.Adapter.ContactAdapter;
import com.example.businesscarddetector.Local.AppDatabase;
import com.example.businesscarddetector.Model.ContactModel;
import com.example.businesscarddetector.Model.VCardModel;
import com.example.businesscarddetector.Presenter.Impl.SearchPresenterImpl;
import com.example.businesscarddetector.Presenter.SearchPresenter;
import com.example.businesscarddetector.R;
import com.example.businesscarddetector.View.ViewInterface.ContactNavigationListener;
import com.example.businesscarddetector.View.ViewInterface.SearchView;
import com.example.businesscarddetector.View.dialog.FilterDialog;
import com.example.businesscarddetector.utils.EqualSpacingItemDecoration;
import com.example.businesscarddetector.utils.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements ContactNavigationListener, SearchView, View.OnClickListener {
    private View rootView;
    private EditText searchEditText;
    private RecyclerView searchContactRv;
    private ContactAdapter contactAdapter;
    private List<ContactModel> contactModelList = new ArrayList<>();
    private SearchPresenter mPresenter;
    private TextView emptyView;
    private EqualSpacingItemDecoration equalSpacingItemDecoration;
    private ImageView filterSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View searchInflater = inflater.inflate(R.layout.fragment_search, container, false);
        rootView = searchInflater.getRootView();
        initViews();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = AppDatabase.getInstance(getContext());
        mPresenter = new SearchPresenterImpl(db.contactDao(), this);
        mPresenter._getContacts();
    }

    private void initViews() {
        filterSearch = rootView.findViewById(R.id.img_filter_search);
        searchEditText = rootView.findViewById(R.id.search_edt);
        searchContactRv = rootView.findViewById(R.id.search_contact_list_rv);
        emptyView = rootView.findViewById(R.id.empty_view);
        equalSpacingItemDecoration = new EqualSpacingItemDecoration(5, 1);
        contactAdapter = new ContactAdapter(contactModelList, this);
        searchContactRv.setLayoutManager(new LinearLayoutManager(getContext()));
        searchContactRv.addItemDecoration(equalSpacingItemDecoration);
        searchContactRv.setAdapter(contactAdapter);
        filterSearch.setOnClickListener(this);
        searchEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                mPresenter._getContactsByCName(searchEditText.getText().toString());
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

    }

    @Override
    public void navigateToContactDetail(ContactModel contactModel) {
        KeyboardUtil.hideKeyboard(getActivity());
        VCardModel vCardModel = new VCardModel(contactModel);
        Bundle bundle = new Bundle();
        bundle.putSerializable("contactModel", vCardModel);
    }

    @Override
    public void setContactList(List<ContactModel> contactModelList) {
        emptyView.setVisibility(View.GONE);
        searchContactRv.setVisibility(View.VISIBLE);
        // contactTextTitle.setVisibility(View.VISIBLE);
        this.contactModelList.clear();
        this.contactModelList.addAll(contactModelList);
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyMessage() {
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(SearchPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onClick(View view) {
        if (view == filterSearch) {
            FilterDialog filterDialog = new FilterDialog(this);
            filterDialog.show(getFragmentManager(), "filter_dialog");
        }
    }
}