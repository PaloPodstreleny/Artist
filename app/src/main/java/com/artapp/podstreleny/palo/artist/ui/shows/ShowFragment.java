package com.artapp.podstreleny.palo.artist.ui.shows;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.Show;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.network.Status;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;
import com.artapp.podstreleny.palo.artist.utils.TokenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    private static final String TAG = ShowFragment.class.getSimpleName();


    @BindView(R.id.artworks_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.artworks_pb)
    ProgressBar mProgressBar;

    @BindView(R.id.errorMessage)
    TextView mErrorMessage;

    @BindView(R.id.error_token_button)
    TextView mErrorTokeButton;

    @BindView(R.id.mButtonRefetch)
    Button mButtonRefetch;

    @BindView(R.id.horizontalFragment)
    ProgressBar mHorizontalProgressBar;


    private String newToken;
    private TokenUtil mTokenUtil;
    private SharedPreferences mSharedPreferences;
    private ShowViewModel mViewModel;
    private ShowListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.artworks_fragment,container,false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Context context = getContext();
        if (context != null) {
            mSharedPreferences = context.getSharedPreferences(getString(R.string.token_file_key), Context.MODE_PRIVATE);
            mTokenUtil = new TokenUtil(context, mSharedPreferences);
        }

        mAdapter = new ShowListAdapter(this, getActivity());
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mViewModel = ViewModelProviders.of(this).get(ShowViewModel.class);
        mViewModel.getShows().observe(this, new Observer<PagedList<Show>>() {
            @Override
            public void onChanged(@Nullable PagedList<Show> shows) {
                if (shows != null) {
                    mAdapter.submitList(shows);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }

            }
        });

        mViewModel.getStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(@Nullable Status status) {
                if (status != null) {
                    switch (status) {
                        case LOADING:
                            mHorizontalProgressBar.setVisibility(View.VISIBLE);
                            break;
                        case ERROR:
                        case SUCCESS:
                        case EMPTY:
                        case UNAUTHORIZED:
                            mHorizontalProgressBar.setVisibility(View.GONE);
                            break;
                        case FIRST_LOAD_ERROR:
                            //show first load error
                            mErrorMessage.setText(R.string.error_no_internet_connection);
                            mButtonRefetch.setVisibility(View.VISIBLE);
                            mErrorMessage.setVisibility(View.VISIBLE);
                            mHorizontalProgressBar.setVisibility(View.GONE);
                            break;
                        default:
                            throw new IllegalArgumentException("Illegal status value");

                    }
                }
            }
        });

        mViewModel.getToken().observe(this, new Observer<Resource<ArtysToken>>() {
            @Override
            public void onChanged(@Nullable Resource<ArtysToken> tokenResource) {
                if (tokenResource != null) {
                    switch (tokenResource.getStatus()) {
                        case LOADING:
                            mProgressBar.setVisibility(View.VISIBLE);
                            mErrorMessage.setVisibility(View.GONE);
                            break;
                        case ERROR:
                            mErrorMessage.setVisibility(View.VISIBLE);
                            mErrorTokeButton.setVisibility(View.VISIBLE);
                            mProgressBar.setVisibility(View.GONE);
                            mErrorMessage.setText(getString(R.string.error_no_internet_connection));
                            break;
                        case SUCCESS:
                            mProgressBar.setVisibility(View.GONE);
                            mErrorTokeButton.setVisibility(View.GONE);
                            mErrorMessage.setVisibility(View.GONE);
                            if (mTokenUtil != null && tokenResource.getData() != null) {
                                mTokenUtil.saveToken(tokenResource.getData());
                            }
                            break;
                        case UNAUTHORIZED:
                            mProgressBar.setVisibility(View.GONE);
                            mErrorMessage.setText(R.string.error_unauthorized_connection);
                            mErrorMessage.setVisibility(View.VISIBLE);
                            mErrorTokeButton.setVisibility(View.VISIBLE);
                            break;
                    }
                }
            }
        });

        if (mTokenUtil != null && !mTokenUtil.hasToken()) {
            mViewModel.fetchToken();
        } else if (mTokenUtil != null && mTokenUtil.getToken() != null) {
            newToken = mTokenUtil.getToken();
            mViewModel.setToken(newToken);
        }

        mErrorTokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.fetchToken();
                mErrorTokeButton.setVisibility(View.GONE);
                mErrorMessage.setVisibility(View.GONE);
            }
        });

        mButtonRefetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonRefetch.setVisibility(View.GONE);
                mErrorMessage.setVisibility(View.GONE);
                if (newToken != null) {
                    mViewModel.setToken(newToken);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        final Context context = getContext();
        if(context != null){
            context.getSharedPreferences(getString(R.string.token_file_key),Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        final Context context = getContext();
        if(context != null){
            context.getSharedPreferences(getString(R.string.token_file_key),Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.show_menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_menu_upcoming:
                //impelment upcoming
                mViewModel.setFilter(ShowPeriod.UPCOMING);
                if(newToken != null){
                    mViewModel.setToken(newToken);
                }
                return true;
            case R.id.show_menu_running:
                //implement running
                mViewModel.setFilter(ShowPeriod.RUNNING);
                if(newToken != null){
                    mViewModel.setToken(newToken);
                }
                return true;
            case R.id.show_menu_closed:
                //implement closed
                mViewModel.setFilter(ShowPeriod.CLOSED);
                if(newToken != null){
                    mViewModel.setToken(newToken);
                }
                return true;
            default:
                throw new IllegalArgumentException("menuitem id is not recognized!");
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.token_entry_value))){
            newToken = sharedPreferences.getString(getString(R.string.token_entry_value),null);
            if(newToken != null){
                mViewModel.setToken(newToken);
            }
        }
    }
}
