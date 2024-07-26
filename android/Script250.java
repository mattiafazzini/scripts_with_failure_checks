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
public class Script250 {

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.moez.QKSMS";
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
        final String launcherPackage1 = getLauncherPackageName();
        assertThat(launcherPackage1, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage1).depth(0)), LAUNCH_TIMEOUT);

        // Launch the contacts app
        Context context1 = getApplicationContext();
        final Intent intent1 = context1.getPackageManager()
                .getLaunchIntentForPackage("com.android.contacts");
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context1.startActivity(intent1);
        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg("com.android.contacts").depth(0)), LAUNCH_TIMEOUT);
        //click create new contact
        mDevice.wait(Until.findObject(By.res("com.android.contacts:id/create_contact_button")),2000).click();
        //keep local
        mDevice.wait(Until.findObject(By.text("Keep local")),2000).click();
        //set name
        mDevice.wait(Until.findObject(By.clazz("android.widget.EditText").text("Name")),2000).setText("Test");
        //set phone number
        mDevice.wait(Until.findObject(By.clazz("android.widget.EditText").text("Phone")),2000).setText("5155562537");
        //save
        mDevice.wait(Until.findObject(By.res("com.android.contacts:id/menu_save")),2000).click();
        //add to faves
        mDevice.wait(Until.findObject(By.res("com.android.contacts:id/menu_star")),2000).click();

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
        // Launch the contacts app
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

        UiObject2 Skip = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "welcome_skip")),2000);
        Skip.click();

        UiObject2 Menu = mDevice.wait(Until.findObject(By.desc( "More options")),2000);
        Menu.click();

        UiObject2 Settings = mDevice.wait(Until.findObject(By.text( "Settings")),2000);
        Settings.click();

        UiObject2 General = mDevice.wait(Until.findObject(By.text( "General")),2000);
        General.click();
        mDevice.drag(300,1500,300,300,150);
        mDevice.drag(300,1500,300,300,150);
        mDevice.drag(300,1500,300,300,150);
        mDevice.drag(300,1500,300,300,150);
        mDevice.drag(300,1500,300,300,150);


        UiObject2 Starred = mDevice.wait(Until.findObject(By.text( "Starred contacts")),2000);
        Starred.click();


        try {
            TimeUnit.SECONDS.sleep(1);
            mDevice.pressBack();
            TimeUnit.SECONDS.sleep(1);
            mDevice.pressBack();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UiObject2 Plus = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "fab")),2000);
        Plus.click();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //manual assertion 
        UiObject2 element1 = mDevice.findObject(By.text("STARRED CONTACTS"));
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
