package com.example.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.login.CurrentWeatherService.CurrentWeatherCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


public class WeatherActivity extends AppCompatActivity {


    private CurrentWeatherService currentWeatherService;
    private View weatherContainer;
    private ProgressBar weatherProgressBar;
    private TextView temperature, location, weatherCondition;
    private TextView humidityT, temp_minT, temp_maxT, pressureT, windspeedT;
    private ImageView weatherConditionIcon;
    private EditText locationField;
    private Button button;
    private boolean fetchingWeather = false;
    private int textCount = 0;
    public String currentLocation = "Sarajevo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        currentWeatherService = new CurrentWeatherService(this);
        weatherContainer = findViewById(R.id.weather_container);
        weatherProgressBar = findViewById(R.id.weather_progress_bar);
        temperature = findViewById(R.id.temperature);
        location = findViewById(R.id.location);
        weatherCondition = findViewById(R.id.weather_condition);
        weatherConditionIcon = findViewById(R.id.weather_condition_icon);
        locationField = findViewById(R.id.location_field);
        button = findViewById(R.id.fab);

        humidityT = findViewById(R.id.humid);
        pressureT = findViewById(R.id.press);
        temp_maxT = findViewById(R.id.tempmax);
        temp_minT = findViewById(R.id.tempmin);
        windspeedT = findViewById(R.id.speed);



        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        humidityT.setVisibility(View.GONE);
        pressureT.setVisibility(View.GONE);
        temp_maxT.setVisibility(View.GONE);
        temp_minT.setVisibility(View.GONE);
        windspeedT.setVisibility(View.GONE);



        locationField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                count = s.toString().trim().length();
                textCount = count;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textCount == 0) {
                    refreshWeather();
                } else {
                    searchForWeather(locationField.getText().toString());
                    locationField.setText("");
                }
            }
        });
        // Start a search for the default location
        searchForWeather(currentLocation);

//        new Connection().execute();



    }

    Runnable hide = new Runnable() {
        @Override
        public void run() {
            humidityT.setVisibility(View.GONE);
            pressureT.setVisibility(View.GONE);
            temp_maxT.setVisibility(View.GONE);
            temp_minT.setVisibility(View.GONE);
            windspeedT.setVisibility(View.GONE);
        }
    };




//    class Connection extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... params) {
//
//            String result;
//            String host = "http://192.168.0.16/gecity.php";
//            try{
//
//                HttpClient client = new DefaultHttpClient();
//                HttpGet request = new HttpGet();
//                request.setURI(new URI(host));
//                HttpResponse response = client.execute(request);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//
//                StringBuffer stringBuffer = new StringBuffer("");
//                String line = "";
//                while ((line = reader.readLine()) != null){
//                    stringBuffer.append(line);
//                    break;
//                }
//                reader.close();
//                result = stringBuffer.toString();
//            }
//            catch (Exception e){
//                return new String("There exception "+ e.getMessage());
//
//            }
//            return result;
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
////            Toast.makeText(WeatherActivity.this, result, Toast.LENGTH_SHORT).show();
////            refreshWeather();
//            try {
//                JSONObject jsonResult = new JSONObject(result);
//                int success = jsonResult.getInt("success");
//                if (success == 1) {
//                    JSONArray locs = jsonResult.getJSONArray("locs");
//                    for (int i = 0; i < locs.length(); i++) {
//                        JSONObject loc = locs.getJSONObject(i);
//                        String locations = loc.getString("location");
//                        Toast.makeText(WeatherActivity.this, locations, Toast.LENGTH_SHORT).show();
//
//
//                        }
//                    }
//                    else{
//                        Toast.makeText(WeatherActivity.this, "Greska", Toast.LENGTH_SHORT).show();
//                    }
//
//            }catch (JSONException e){
//                Toast.makeText(WeatherActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }
//
//    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch(item.getItemId()) {
                case R.id.nav_exit:
                    Intent i = new Intent(WeatherActivity.this, MainActivity.class);
                    startActivity(i);
                    break;
                case R.id.nav_more:
                    humidityT.setVisibility(View.VISIBLE);
                    pressureT.setVisibility(View.VISIBLE);
                    temp_maxT.setVisibility(View.VISIBLE);
                    temp_minT.setVisibility(View.VISIBLE);
                    windspeedT.setVisibility(View.VISIBLE);

                    humidityT.postDelayed(hide, 10000);
                    pressureT.postDelayed(hide, 10000);
                    temp_maxT.postDelayed(hide, 10000);
                    temp_minT.postDelayed(hide, 10000);
                    windspeedT.postDelayed(hide, 10000);

                    break;
                case R.id.nav_web:
                    Intent inte = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.0.16/ProjekatRWA/validacija.php"));
                    startActivity(inte);
                    break;

            }
            return true;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentWeatherService.cancel();
    }

    private void refreshWeather() {
        if (fetchingWeather) {
            return;
        }
        searchForWeather(currentLocation);
    }


    private void searchForWeather(@NonNull final String location) {
        toggleProgress(true);

        fetchingWeather = true;
        currentWeatherService.getCurrentWeather(location, currentWeatherCallback);
    }

    private void toggleProgress(final boolean showProgress) {
        weatherContainer.setVisibility(showProgress ? View.GONE : View.VISIBLE);
        weatherProgressBar.setVisibility(showProgress ? View.VISIBLE : View.GONE);
    }

    public final CurrentWeatherCallback currentWeatherCallback = new CurrentWeatherCallback() {

        @SuppressLint("SetTextI18n")
        @Override
        public void onCurrentWeather(@NonNull CurrentWeather currentWeather) {
            currentLocation = String.valueOf(currentWeather.location);
            temperature.setText(String.valueOf(currentWeather.getTempCelsius()));
            location.setText(currentWeather.location);
            weatherCondition.setText(currentWeather.weatherCondition);
            weatherConditionIcon.setImageResource(CurrentWeatherUtils.getWeatherIconResId
                    (currentWeather.conditionId));
            toggleProgress(false);
            fetchingWeather = false;
            humidityT.setText(getString(R.string.razina_vlage)+ " " + (currentWeather.humidity) + getString(R.string.post));
            pressureT.setText(getString(R.string.pritisak) + " " + (currentWeather.pressure) + getString(R.string.hpa));
            temp_maxT.setText(getString(R.string.maxtemp) + " " + (currentWeather.getTempMaxCelsius()) + getString(R.string.c));
            temp_minT.setText(getString(R.string.mintemp) + " " + (currentWeather.getTempMinCelsius()) + getString(R.string.c));
            windspeedT.setText(getString(R.string.brzvjet) + " " + (currentWeather.windspeed) + getString(R.string.ms));

        }

        @Override
        public void onError(@Nullable Exception exception) {
            toggleProgress(false);
            fetchingWeather = false;
            Toast.makeText(WeatherActivity.this, "There was an error fetching weather, " +
                    "try again.", Toast.LENGTH_SHORT).show();
        }
    };
}
