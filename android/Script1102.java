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
import android.os.RemoteException;
import android.provider.Contacts;

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
public class Script1102{

    private static final String BASIC_SAMPLE_PACKAGE
            = "org.kiwix.kiwixmobile";
    private static final String AndroidOS = "com.android.packageinstaller";
    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "UiAutomator";

    private UiDevice mDevice;
    public void sleepOneSecond(){
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
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

        sleepOneSecond();

        mDevice.click(550,1050);
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        // Permissions
        UiObject2 allow = mDevice.wait(Until.findObject(By.res("com.android.packageinstaller","permission_allow_button")),2000);
        allow.click();

        UiObject2 toDownload =  mDevice.wait(Until.findObject(By.textStartsWith("InstallGentoo")),2000);
        // Setting this to the smallest downloadable thing here for ease of memory
        while(null == toDownload){
            mDevice.swipe(170, 1050, 170, 450, 10);
            toDownload =  mDevice.wait(Until.findObject(By.textStartsWith("InstallGentoo")),2000);
        }
        toDownload.click();

        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();
        sleepOneSecond();


        UiObject2 device = mDevice.wait(Until.findObject(By.text("DEVICE")),2000);
        device.click();

        UiObject2 wiki = mDevice.wait(Until.findObject(By.textStartsWith( "InstallGentoo")),2000);
        wiki.click();

        wiki = mDevice.wait(Until.findObject(By.desc("More options")),2000);
        wiki.click();

        wiki = mDevice.wait(Until.findObject(By.text("Help")),2000);
        wiki.click();

        sleepOneSecond();
        sleepOneSecond();

        // manual assertion 
        UiObject2 element1 = mDevice.findObject(By.text("InstallGentoo Wiki"));
        assertTrue(element1 != null);
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
