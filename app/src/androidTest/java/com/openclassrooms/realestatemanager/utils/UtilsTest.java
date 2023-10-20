package com.openclassrooms.realestatemanager.utils;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UtilsTest {

    private Context appContext;

    @Before
    public void setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void testWifiAvailable() {
        boolean wifiAvailable = Utils.isInternetAvailable(appContext);
        assertTrue("Wi-Fi should be available", wifiAvailable);
    }

    @Test
    public void testWifiNotAvailable() {
        boolean wifiAvailable = Utils.isInternetAvailable(appContext);
        assertFalse("Wi-Fi should not be available", wifiAvailable);
    }


}