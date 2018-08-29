package com.artapp.podstreleny.palo.artist;



import android.support.test.espresso.accessibility.AccessibilityChecks;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;


import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AccessibilityChecksIntegrationTest {


    @Test
    public void runAccessibilityTest(){
        AccessibilityChecks.enable()
                .setRunChecksFromRootView(true);
    }
}