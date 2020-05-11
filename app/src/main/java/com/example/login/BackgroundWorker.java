package com.example.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;




public class BackgroundWorker extends AsyncTask<String, String, String> {

    public String rez;

    public String getRez() {
        return rez;
    }

    public void setRez(String result) {
        this.rez = rez;
    }


    Context context;
    AlertDialog alertDialog;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }



    @Override
    public String doInBackground(String... params) {

        String type = params[0];
        // kupi login iz MainActivity.java klase PV OnLogin


        String login_url = "http://192.168.0.16/login.php"; //localhost
        String register_url = "http://192.168.0.16/register.php";
        if (type.equals("login")) {

            try {
                String user_name = params[1];
                String password = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                    return result;
                }


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("register")) {
            try {
                String location = params[1];
                String username = params[2];
                String password = params[3];

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8") + "&"
                        + URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return line;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");



    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);

        String tacno = "Login uspjesan! Dobrodosli!";
        String tacno2 ="Registracija uspjesna! Molimo prijavite se.";

        if (result.equals(tacno)) {
            context.startActivity(new Intent(context, WeatherActivity.class));


        }

        else if (result.equals(tacno2)){
            alertDialog.show();
        }

        else {
            alertDialog.show();
        }
    }



    @Override
    protected void onProgressUpdate (String...values){
        super.onProgressUpdate(values);
    }


}

