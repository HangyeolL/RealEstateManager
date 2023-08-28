package com.openclassrooms.realestatemanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars) {
        return (int) Math.round(dollars * 0.812);
    }

    public static int convertEuroToDollar(int euros) {
        return (int) Math.round(euros * 1.23);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return
     */
    public static String changeDateFormatToDaysFirstFromYearsFirst(String inputDate) {
        try {
            // Create a SimpleDateFormat object to parse the input date
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Parse the input date string to a Date object
            Date date = inputFormat.parse(inputDate);

            // Create a SimpleDateFormat object for the desired output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");

            // Format the Date object to the desired output format
            return outputFormat.format(date);

        } catch (ParseException e) {
            // Handle parsing exceptions here
            e.printStackTrace();
            return null; // or throw an exception if needed
        }
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context
     * @return
     */
//    public static Boolean isInternetAvailable(Context context) {
//        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        return wifiManager.isWifiEnabled();
//    }

    @SuppressLint("MissingPermission")
    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);

            // return true if those conditions are met else false
            return networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI && networkInfo.isConnected();
        }
    }
}
