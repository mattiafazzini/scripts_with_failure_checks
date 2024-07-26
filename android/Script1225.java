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

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.provider.Contacts;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script1225 {

    private static final String PACKAGE_NAME
            = "org.gnucash.android";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "test";

    private UiDevice uiDevice;

    @Before
    public void startActivityFromHomeScreen() throws UiObjectNotFoundException, InterruptedException {


        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        mDevice.pressHome();
        // Change language to French
        mDevice.findObject(new UiSelector().description("Apps")).click();
        mDevice.findObject(new UiSelector().text("Settings")).click();
        mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/dashboard_container")).swipeUp(5);
        mDevice.findObject(new UiSelector().text("Language & input")).click();
        mDevice.findObject(new UiSelector().text("Language")).click();
        TimeUnit.SECONDS.sleep(2);
        mDevice.swipe(540,1400,540,100,200);
        TimeUnit.SECONDS.sleep(2);
        mDevice.findObject(new UiSelector().textContains("Espa")).click();
        TimeUnit.SECONDS.sleep(2);
        // Initialize UiDevice instance
        uiDevice = UiDevice.getInstance(getInstrumentation());

        // Start from the home screen
        uiDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        uiDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        uiDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
    }
// set system language to spanish (US) first
    @Test
    public void performGUIActions() throws UiObjectNotFoundException, IOException, RemoteException, InterruptedException {
        // Setup GUI actions
        // Click "next" three times
        uiDevice.findObject(new UiSelector().text("Siguiente")).click();
        uiDevice.findObject(new UiSelector().text("Siguiente")).click();
        uiDevice.findObject(new UiSelector().text("Siguiente")).click();
        // Click option 1
        uiDevice.findObject(new UiSelector().textContains("Enviar")).click();
        // Click "next"
        uiDevice.findObject(new UiSelector().text("Siguiente")).click();
        // Click "done"
        uiDevice.findObject(new UiSelector().text("Hecho")).click();
        uiDevice.findObject(new UiSelector().text("Permitir")).click();

        // Oracle
        UiObject2 element1 = uiDevice.findObject(By.text("Assets"));
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
