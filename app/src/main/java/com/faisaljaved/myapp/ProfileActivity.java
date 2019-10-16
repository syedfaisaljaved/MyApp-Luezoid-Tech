package com.faisaljaved.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfileActivity extends Fragment implements LoaderManager.LoaderCallbacks<List<Cars>> {


    private static final int URL_LOADER_ID = 1;

    public static final String LOG_TAG = MainActivity.class.getName();

    /** Adapter for the list of cars */
    private CarsAdapter mAdapter;

    /** URL for cars data from the given url */
    private static final String REQUEST_URL = "http://www.mocky.io/v2/5d15fc1b0e00003311a1167a";

    private TextView mEmptyStateTextView;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View profileActivity = inflater.inflate(R.layout.activity_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        getActivity().setTitle(firebaseUser.getEmail());


        ListView carsListView = (ListView)profileActivity.findViewById(R.id.listView);

        mAdapter = new CarsAdapter(getActivity(), new ArrayList<Cars>());

        carsListView.setAdapter(mAdapter);

        carsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cars currentCar = mAdapter.getItem(position);

                String title = currentCar.getmTitle();
                String subtitle = currentCar.getmSubtitle();
                String imageId = currentCar.getmImageId();

                Intent intent = new Intent(getActivity(),DetailVIew.class)
                        .putExtra("titlepassed",title)
                        .putExtra("subtitlepassed", subtitle)
                        .putExtra("imageIdpassed", imageId);

                startActivity(intent);

            }
        });

        mEmptyStateTextView = (TextView) profileActivity.findViewById(R.id.empty_view);
        carsListView.setEmptyView(mEmptyStateTextView);

        ConnectivityManager cm =(ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()){

            // Start the AsyncTask to fetch the earthquake data
//            EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//            task.execute(REQUEST_URL);

            LoaderManager loaderManager = getActivity().getSupportLoaderManager();

            Log.i(LOG_TAG,"net active");
            loaderManager.initLoader(URL_LOADER_ID,null,this);

        }else {
            View progressBar = profileActivity.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No Internet Connection");
        }


        return profileActivity;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.share){
            firebaseAuth.signOut();
            LoginActivity loginFragment = new LoginActivity();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmment_container, loginFragment)
                    .commit();
            Toast.makeText(getActivity(),"Logged Out",Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @NonNull
    @Override
    public Loader<List<Cars>> onCreateLoader(int id, @Nullable Bundle args) {
        return new CarsLoader(getActivity(),REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Cars>> loader, List<Cars> cars) {
        View progressBar = getView().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        mEmptyStateTextView.setText("No Data Found");

        mAdapter.clear();

        if ( cars != null || !cars.isEmpty()){
            mAdapter.addAll(cars);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Cars>> loader) {
        mAdapter.clear();
    }

    public ProfileActivity() {

    }
}
