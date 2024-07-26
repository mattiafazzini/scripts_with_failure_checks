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

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import androidx.test.filters.SdkSuppress;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class Script21 {
    // Android 8 (google play)
    private static final String PACKAGE_NAME
            = "com.markuspage.android.atimetracker";

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String STRING_TO_TYPE = "test";

    private UiDevice uiDevice;

    @Before
    public void startActivityFromHomeScreen() {
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
        // t grown dim
        uiDevice.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void performGUIActionsAndAssertions() throws UiObjectNotFoundException, IOException, InterruptedException {
        // Perform setup GUI actions
        // Press ok button
        uiDevice.findObject(new UiSelector().className("android.widget.Button")).click();

        // Preform bug-specific GUI actions
        // Press menu option
        UiObject2 target = uiDevice.findObject(By.descContains("More options"));
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);
        // Press add activity option
        target = uiDevice.findObject(By.text("Add activity"));
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);//
        // Press text box
        target = uiDevice.findObject(By.clazz("android.widget.EditText"));
        target.setText(STRING_TO_TYPE);
        TimeUnit.MILLISECONDS.sleep(1000);//
        // Press add activity button
        target = uiDevice.findObject(By.res("android:id/button1"));
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);//
        // Press "test" timer
        target = uiDevice.findObject(By.text("test"));
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);//
        // Long-press "test" timer
        uiDevice.findObject(By.text(STRING_TO_TYPE)).click(1000);

        TimeUnit.MILLISECONDS.sleep(1000);//
        // Manual Assertion
        List<UiObject2> elements = uiDevice.findObjects(By.res("android:id/title"));
        assertTrue(elements.size() == 1);
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
