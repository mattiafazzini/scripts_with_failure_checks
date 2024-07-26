/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script1149 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.android.gpstest";
    private static final String AndroidOS = "com.android.packageinstaller";
    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "UiAutomator";

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testChangeText_sameActivity() {

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        UiObject2 allow = mDevice.wait(Until.findObject(By.res(AndroidOS, "permission_allow_button")),2000);
        allow.click();

        UiObject2 close = mDevice.wait(Until.findObject(By.res("android", "button3")),2000);
        close.click();

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDevice.click(75,133);

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDevice.click(75,1500);


        UiObject2 about = mDevice.wait(Until.findObject(By.text( "About")),2000);
        about.click();

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Manual Assertion 
        UiObject2 element1 = mDevice.findObject(By.res("com.android.gpstest:id/help_text"));
        assertTrue(element1.getText().equals(" Frequently Asked Questions (FAQ) - https://github.com/barbeau/gpstest/blob/master/FAQ.md\n\n *** Status View ***\n\n•  Latitude - In decimal degrees (WGS 84)\n•  Longitude - In decimal degrees (WGS 84)\n•  Alt - Meters above the WGS 84 ellipsoid\n•  Alt (MSL) - Meters above Mean Sea Level (requires NMEA support)\n•  Acc - Estimated horizontal accuracy of the location (radius in meters of 68% confidence). In other words, if you draw a circle centered at this location's latitude and longitude, and with a radius equal to the accuracy, then there is a 68% probability that the true location is inside the circle.\n•  H/V Acc - Estimated horizontal and vertical accuracy of the location, both at the 68% confidence. Horizontal accuracy is defined the same as Acc field above. Vertical accuracy is available on Android 8.0 Oreo and higher. Vertical accuracy example - if Alt is 150, and V Acc is 20 then there is a 68% probability of the true altitude being between 130 and 170 meters.\n•  Speed - Speed, in meters/second over ground\n•  S. Acc - Estimated speed accuracy of this location at 68% confidence, in meters per second. For example, if Speed is 5, and Speed Acc is 1, then there is a 68% probability of the true speed being between 4 and 6 meters per second.\n•  Bearing - The horizontal direction of travel of the device (in degrees 0-360), and is not related to the device orientation.\n•  B. Acc - Estimated bearing accuracy of this location at 68% confidence, in degrees. For example, if Bearing is 60, and Bearing Acc is 10, then there is a 68% probability of the true bearing being between 50 and 70 degrees.\n•  # Sats - # satellites used in fix / # satellites in view\n•  PDOP - Position (3D) dilution of precision (requires NMEA support)\n•  H/V DOP - Horizontal / vertical dilution of precision (requires NMEA support)\n•  TTFF - Time to first fix\n•  ID or PRN - identifier of this satellite (pseudorandom noise for GPS)\n•  CF - Label (e.g., L1) for carrier frequency of signal being tracked. If an unknown frequency, it shows the raw frequency in MHz (available on Android 8.0+, but not supported on all devices)\n•  SNR - Signal-to-noise ratio (Android M and lower) (http://bit.ly/SNR-CN0)\n•  C/N0 - Carrier-to-noise density (Android N and higher) (http://bit.ly/SNR-CN0)\n•  Flags - (E) if the device has ephemeris data for the satellite, (A) if the device has almanac data for the satellite, (U) if the satellite was used in the latest fix\n\n *** Sky View ***\n\n•  GPS (USA Navstar) - Circles\n•  GLONASS (Russia) - Squares\n•  Galileo (European Union) - Triangles\n•  QZSS (Japan) - Hexagons\n•  BeiDou/COMPASS (China) - Pentagons\n•  SBAS (WAAS, EGNOS, GAGAN, MSAS, SDCM, SNAS, SACCSA) - Diamonds\n\n •  Transparent satellites - Not in view\n •  Thin outline - In view but not used in fix\n •  Bold outline - Used in fix\n\n •  Expected SNR range is 0 dB (red) to 30 dB (green) on Android versions less than 7.0. \n •  Expected SNR range is 10 dB (red) to 45 dB (green) on Android 7.0 and higher. \n •  Expected C/N0 range is 10 dB-Hz (red) to 45 dB-Hz (green) on Android 7.0 and higher. \n •  If there is no SNR or C/N0 value for a satellite (i.e., it is not in view of the device), it will have a transparent light gray color. \n\n *** Acknowledgments ***\n\n •  Mike Lockwood - original developer for v1.x\n •  Sean Barbeau - developer for v2.x and higher\n •  GPSTest launcher icon is derived from the Android Material Design satellite icon, licensed by Google under Apache v2.0. Other Google material design icons are also used under Apache v2.0.\n •  Chinese translation provided by wangkf, liuyunli, SakuraSa233 & Skimige\n •  German translation provided by SIRSteiner\n •  Dutch translation provided by Dennis Dolman\n •  Greek translation provided by Jimmy Golfi\n •  Japanese translation provided by SakuraSa233\n "));

    }

    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
