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
import android.os.RemoteException;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;

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
public class Script152 {

    private static final String BASIC_SAMPLE_PACKAGE = "com.beemdevelopment.aegis";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_BE_TYPED = "UiAutomator";

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() throws UiObjectNotFoundException, InterruptedException {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());

        // Change language to French
        mDevice.findObject(new UiSelector().description("Apps")).click();
        mDevice.findObject(new UiSelector().text("Settings")).click();
        mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/dashboard_container")).swipeUp(5);
        mDevice.findObject(new UiSelector().text("Languages & input")).click();
        mDevice.findObject(new UiSelector().text("Languages")).click();
        mDevice.findObject(new UiSelector().text("Add a language")).click();
        UiScrollable languageListSelection = new UiScrollable(new UiSelector().scrollable(true).resourceId("android:id/list"));
        UiSelector frenchLanguage = new UiSelector().description("French");
        languageListSelection.scrollIntoView(frenchLanguage);
        mDevice.findObject(new UiSelector().description("French")).click();
        mDevice.findObject(new UiSelector().text("France")).click();
        TimeUnit.SECONDS.sleep(2);
        mDevice.findObject(new UiSelector().description("More options")).click();
        mDevice.findObject(new UiSelector().text("Remove")).click();
        mDevice.findObject(new UiSelector().text("English (United States)")).click();
        mDevice.findObject(new UiSelector().description("Remove")).click();
        mDevice.findObject(new UiSelector().text("OK")).click();

        // Start from home
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
    public void performActions() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        // click "->"
        mDevice.findObject(new UiSelector().resourceId("com.beemdevelopment.aegis:id/next")).click();
        // click "none"
        mDevice.findObject(new UiSelector().resourceId("com.beemdevelopment.aegis:id/rb_none")).click();
        // click "->"
        mDevice.findObject(new UiSelector().resourceId("com.beemdevelopment.aegis:id/next")).click();
        // click "tick"
        mDevice.findObject(new UiSelector().resourceId("com.beemdevelopment.aegis:id/done")).click();

        // click top right 3 dot icon
        mDevice.findObject(new UiSelector().descriptionContains("Plus d'options")).click();
        // click settings
        mDevice.findObject(new UiSelector().text("Paramètres")).click();
        // click language
        mDevice.findObject(new UiSelector().text("System default")).click();
        // click French
        mDevice.findObject(new UiSelector().text("English")).click();
        // press home button
        mDevice.pressHome();
        // press tasks buttong
        mDevice.pressRecentApps();
        // close app
        mDevice.findObject(new UiSelector().description("Supprimer Aegis")).click();
        // press home button
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

        // click top right 3 dot icon
        mDevice.findObject(new UiSelector().descriptionContains("Plus d'options")).click();
        // click settings
        mDevice.findObject(new UiSelector().text("Paramètres")).click();
        // oracle
//        UiObject2 element2 = mDevice.findObject(By.text("Appearance"));
//        UiObject2 element1 = mDevice.findObject(By.text("Langue"));
//        assertTrue(element1 != null && element2 != null);
        UiObject2 element1 = mDevice.findObject(By.text("Langue"));
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
