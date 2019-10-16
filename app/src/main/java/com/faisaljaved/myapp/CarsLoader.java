package com.faisaljaved.myapp;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;


class CarsLoader extends AsyncTaskLoader {

    private static final String LOG_TAG = CarsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public CarsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        if (mUrl == null){
            return null;
        }

        List<Cars> result = QueryUtils.fetchCarsData(mUrl);
        return result;
    }
}
