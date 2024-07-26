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
import android.os.Environment;
import android.os.RemoteException;
import android.provider.Contacts;
import android.util.Log;

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

import java.io.File;
import java.io.IOException;
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
public class new_1234117{

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
    public void testChangeText_sameActivity() throws InterruptedException, IOException, RemoteException {
        int a = 0;
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(getInstrumentation());
        UiObject2 target = mDevice.wait(Until.findObject(By.text("OK")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.desc("Add")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Create deck")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.clazz("android.widget.EditText")),2000);
        target.setText("test");
        target = mDevice.wait(Until.findObject(By.text("OK")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("test")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.desc("Add")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Add")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.desc("Attach multimedia content to the Front field")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Add image")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("CAMERA")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.desc("Shutter")),2000);
        if(target==null){
            target = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
            target.click();
            target = mDevice.wait(Until.findObject(By.text("NEXT")),2000);
            target.click();
            target = mDevice.wait(Until.findObject(By.desc("Shutter")),2000);
        }
        target.click();
        target = mDevice.wait(Until.findObject(By.desc("Done")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("NO")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.desc("Done")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.desc("Back")),2000);
        target.setText("test");
        target = mDevice.wait(Until.findObject(By.desc("Save")),2000);
        target.click();
        TimeUnit.MILLISECONDS.sleep(2000);
        mDevice.click(100,100);
        TimeUnit.MILLISECONDS.sleep(2000);
        mDevice.pressHome();
        TimeUnit.MILLISECONDS.sleep(2000);
        mDevice.swipe(540,1700,540,200,20);
        TimeUnit.MILLISECONDS.sleep(2000);
        target = mDevice.wait(Until.findObject(By.text("Files")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.desc("More options")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Show internal storage")),2000);
        if (target!=null) {
            target.click();
        }
        TimeUnit.MILLISECONDS.sleep(2000);
        mDevice.click(100,100);
        TimeUnit.MILLISECONDS.sleep(2000);
        target = mDevice.wait(Until.findObject(By.textContains("Emulator")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("AnkiDroid")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("collection.media")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.textContains("img")),2000);
        mDevice.swipe(target.getVisibleCenter().x,target.getVisibleCenter().y,target.getVisibleCenter().x,target.getVisibleCenter().y,200);
        TimeUnit.MILLISECONDS.sleep(2000);
        target = mDevice.wait(Until.findObject(By.desc("Delete")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("OK")),2000);
        target.click();
        mDevice.pressRecentApps();
        TimeUnit.MILLISECONDS.sleep(2000);
        target = mDevice.wait(Until.findObject(By.desc("AnkiDroid")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("test")),2000);
        mDevice.swipe(target.getVisibleCenter().x,target.getVisibleCenter().y,target.getVisibleCenter().x,target.getVisibleCenter().y,200);
        TimeUnit.MILLISECONDS.sleep(2000);
        target = mDevice.wait(Until.findObject(By.text("Export deck")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Include media")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("OK")),2000);
        target.click();

        //oracle
        UiObject2 element1 = mDevice.findObject(By.text("Send Anki package?"));
        UiObject2 element2 = mDevice.findObject(By.text("test"));
        assertTrue(element1 != null && element2 == null);










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
