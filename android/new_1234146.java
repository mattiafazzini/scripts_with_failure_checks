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
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Basic example for unbundled UiAutomator.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class new_1234146{

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.android.camera2";
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
    // create a file called abc.txt first. this is to prevent too many files being made
    @Test
    public void testChangeText_sameActivity() throws InterruptedException, RemoteException {
        mDevice = UiDevice.getInstance(getInstrumentation());
        UiObject2 target = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.res("com.android.camera2:id/confirm_button")),2000);
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.swipe(5,900,1080,900,200);
        TimeUnit.MILLISECONDS.sleep(1000);
        target = mDevice.wait(Until.findObject(By.text("Video")),2000);
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);
        target =  mDevice.wait(Until.findObject(By.desc("Shutter")),2000);
        target.click();
        TimeUnit.MILLISECONDS.sleep(3000);
        target =  mDevice.wait(Until.findObject(By.desc("Shutter")),2000);
        target.click();
        TimeUnit.MILLISECONDS.sleep(3000);
        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage("com.amaze.filemanager");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg("com.amaze.filemanager").depth(0)), LAUNCH_TIMEOUT);

        target = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("DCIM")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Camera")),2000);
        target.click();
        TimeUnit.MILLISECONDS.sleep(3000);
        target = mDevice.wait(Until.findObject(By.res("com.amaze.filemanager:id/properties")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Rename")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.textContains("VID")),2000);
        target.setText("VID1.mp4");
        target = mDevice.wait(Until.findObject(By.textContains("SAVE")),2000);
        target.click();
        mDevice.pressHome();



        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.swipe(540,1500,540,200,200);
        TimeUnit.MILLISECONDS.sleep(1000);
        target = mDevice.wait(Until.findObject(By.text("Photos")),2000);
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.click(540,900);
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.click(540,900);
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.click(540,900);
        TimeUnit.MILLISECONDS.sleep(2000);

        // Oracle
        UiObject2 element1 = mDevice.findObject(By.text("Can't play this video."));
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
