package com.faisaljaved.myapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods.
     */
    private QueryUtils(){

    }

    public static ArrayList<Cars> extractFromJSON(String carsJson){

        ArrayList<Cars> cars = new ArrayList<>();

        try {
            JSONArray root = new JSONArray(carsJson);

            for (int i = 0; i < root.length(); i++) {
                JSONObject loop =root.getJSONObject(i);

                String title = loop.getString("product_name");
                String subtitle = loop.getString("product_desc");
                String image = loop.getString("product_image");

                Cars car = new Cars(title,subtitle,image);
                cars.add(car);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cars;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection =(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }catch (IOException exception){

        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);

        }catch (MalformedURLException exception){
            Log.d(LOG_TAG,"error with creating url", exception);
            return null;
        }
        return url;
    }

    public static List<Cars> fetchCarsData(String requestUrl){

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL url = createUrl(requestUrl);

        String jsonresponse = "";
        try {
            jsonresponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.d(LOG_TAG,"problem making a http request",e);
        }

        List<Cars> cars = extractFromJSON(jsonresponse);

        return cars;
    }
}
