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

import java.io.IOException;
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
public class new_1234170{

    private static final String BASIC_SAMPLE_PACKAGE
            = "com.fsck.k9";
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
    public void testChangeText_sameActivity() throws InterruptedException, RemoteException, IOException {

        mDevice = UiDevice.getInstance(getInstrumentation());
        UiObject2 target = mDevice.wait(Until.findObject(By.text("Next")),2000);
        if(target==null){
            target = mDevice.wait(Until.findObject(By.text("NEXT")),2000);
        }
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Email address")),2000);
        target.setText("androidtesting1231@outlook.com");
        target = mDevice.wait(Until.findObject(By.text("Password")),2000);
        target.setText("XafLJ8EsFZt2dbH");
        target = mDevice.wait(Until.findObject(By.text("Next")),2000);
        if(target==null){
            target = mDevice.wait(Until.findObject(By.text("NEXT")),2000);
        }
        target.click();
        TimeUnit.MILLISECONDS.sleep(5000);
        target = mDevice.wait(Until.findObject(By.textContains("account a name")),2000);
        target.setText("test");
        target = mDevice.wait(Until.findObject(By.textContains("your name")),2000);
        target.setText("test");
        target = mDevice.wait(Until.findObject(By.text("Done")),2000);
        if(target==null){
            target = mDevice.wait(Until.findObject(By.text("DONE")),2000);
        }
        target.click();
        TimeUnit.MILLISECONDS.sleep(5000);
        target = mDevice.wait(Until.findObject(By.text("OK")),2000);
        if(target==null){
            target = mDevice.wait(Until.findObject(By.text("ALLOW")),2000);
        }
        target.click();
        target = mDevice.wait(Until.findObject(By.text("test")),2000);
        if(target==null){
            TimeUnit.MILLISECONDS.sleep(1000);
            mDevice.click(100,100);
            TimeUnit.MILLISECONDS.sleep(1000);
            target = mDevice.wait(Until.findObject(By.text("Settings")),2000);
            target.click();
            target = mDevice.wait(Until.findObject(By.text("test")),2000);
            target.click();
        } else {
            target.click();
            target = mDevice.wait(Until.findObject(By.desc("More options")), 2000);
            target.click();
            target = mDevice.wait(Until.findObject(By.text("Settings")), 2000);
            target.click();
            target = mDevice.wait(Until.findObject(By.text("Account settings")), 2000);
            target.click();
        }
        target = mDevice.wait(Until.findObject(By.text("Sending mail")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.text("Composition defaults")),2000);
        target.click();
        target = mDevice.wait(Until.findObject(By.res("com.fsck.k9:id/account_always_bcc")),2000);
        target.setText("test@test.com");
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.pressBack();
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.pressBack();
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.pressBack();
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.pressBack();
        TimeUnit.MILLISECONDS.sleep(1000);
        target = mDevice.wait(Until.findObject(By.desc("Compose")),2000);
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);
        Runtime.getRuntime().exec(new String[] {"am", "force-stop", BASIC_SAMPLE_PACKAGE});
        TimeUnit.MILLISECONDS.sleep(5000);
        startMainActivityFromHomeScreen();
        TimeUnit.MILLISECONDS.sleep(1000);
        mDevice.click(100,100);
        TimeUnit.MILLISECONDS.sleep(1000);
        target = mDevice.wait(Until.findObject(By.textContains("Drafts")),2000);
        if(target==null){
            target = mDevice.wait(Until.findObject(By.text("Unified Inbox")),2000);
            target.click();
            target = mDevice.wait(Until.findObject(By.text("test")),2000);
            target.click();
            TimeUnit.MILLISECONDS.sleep(1000);
            mDevice.click(100,100);
            TimeUnit.MILLISECONDS.sleep(1000);
            target = mDevice.wait(Until.findObject(By.textContains("Drafts")),2000);
        }
        target.click();
        TimeUnit.MILLISECONDS.sleep(1000);


        // Oracle
        UiObject2 element1 = mDevice.findObject(By.textContains("(no subject)"));
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
