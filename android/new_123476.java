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

import org.junit.Assert;
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
import androidx.test.uiautomator.Direction;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.lang.Object.*;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class new_123476{

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.ichi2.anki";
    private static final String AndroidOS = "com.android.packageinstaller";
    private static final int LAUNCH_TIMEOUT = 5000;

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
    public void testChangeText_sameActivity() throws InterruptedException, androidx.test.uiautomator.UiObjectNotFoundException {
        mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.findObject(By.text("OK")).click();
        UiObject2 Allow = mDevice.wait(Until.findObject(By.text("ALLOW")), 2000);
        Allow.click();
        UiObject2 Drawerlayout = mDevice.wait(Until.findObject(By.desc("Navigate up")), 2000);
        Drawerlayout.click();
        UiObject2 Settings = mDevice.wait(Until.findObject(By.text("Settings")), 2000);
        Settings.click();
        TimeUnit.SECONDS.sleep(1);
        UiObject2  General_settings= mDevice.wait(Until.findObject(By.text("AnkiDroid")), 2000);
        General_settings.click();
        UiObject2  Language= mDevice.wait(Until.findObject(By.text("System language")), 2000);
        Language.click();
        //UiScrollable Scrollableobject = new UiScrollable().flingToEnd(10);
        //public boolean flingToEnd(int maxSwipes)
        //new android.widget.ListView().flingToEnd(10);
        TimeUnit.SECONDS.sleep(1);
        UiObject2 scrollable = mDevice.wait(Until.findObject(By.scrollable(true)), 2000);
        scrollable.fling(Direction.DOWN,4000);
        scrollable.fling(Direction.DOWN,4000);
        scrollable.fling(Direction.DOWN,4000);
        scrollable.fling(Direction.DOWN,4000);
        scrollable.fling(Direction.DOWN,4000);

        //manual assertion
        UiObject2 element1 = mDevice.findObject(By.text("yu"));
        assertTrue(element1 !=null);
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
