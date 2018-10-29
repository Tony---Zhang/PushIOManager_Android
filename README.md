## PushIOManager for Android 

* [Integration Guide](http://docs.push.io)

## Release Notes

### Upgrading SDK to 6.38.1
#### Support for FCM
With the release of 6.38.1, the SDK is compatible with FCM (Firebase Cloud Messaging). If you wish to use FCM libraries in your app, it is recommended that you remove the GCM library dependency and vice-versa.

Here are some documents for FCM support

##### 1. Dependence
```
dependencies {
	// implementation 'com.google.android.gms:play-services-gcm:15.0.1'
	implementation 'com.google.firebase:firebase-messaging:17.3.2'
}
``` 

##### 2. Proguard config
Add this line to your proguard prevent proguard issue
```
# PushIO
-dontwarn com.pushio.manager.PIOInstanceIDListenerService
```

##### 3. Manifest declaration
Remove these two permissions, FCM will add them automatically

~~<uses-permission android:name="com.google.android.c2dm.permisson.RECEIVE" />~~

~~<uses-permission android:name="android.permission.WAKE_LOCK" />~~

Remove this old service in your Manifest
```
<service
    android:name="com.pushio.manager.PIOInstanceIDListenerService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.android.gms.iid.InstanceID" />
    </intent-filter>
</service>
```

Add this new service in your Manifest
```
<service
    android:name="com.pushio.manager.PIOFCMIntentService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.INSTANCE_ID_EVENT" />
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

### Upgrading SDK to 6.38
#### New API for SDK Crash Reporting
With the release of 6.38, we have introduced a new feature to report all SDK-related (internal) crash/issues back to Responsys. This feature is enabled by default. The following API can be used to toggle this feature,

```java
PushIOManager.getInstance(this).setCrashLoggingEnabled(true);
```

### Upgrading PushIO SDK 6.33.1 to 6.33.2
#### Changes in APIs for Registration
With the release of 6.33.2, we have modified the following registration API methods,
```
PushIOManager.getInstance(this).registerApp();

PushIOManager.getInstance(this).registerApp( boolean useLocation );
```

As with the previous release, the API - `PushIOManager.registerApp()` brings up a modal prompt at runtime for requesting location access. 

If you would like to defer requesting the location access to a later stage, you should use the new API - `PushIOManager.registerApp( boolean useLocation )`. The `boolean` parameter value here is persisted and subsequent registration calls to Responsys backend (for ex. via `registerApp()` or `registerUserId()`) use this value to check if location access is to be requested.


### Upgrading PushIO SDK 6.33 to 6.33.1
#### New API for Registration
With the release of 6.33.1, we have added a new API for registration. 

```
PushIOManager.getInstance(this).registerApp( boolean useLocation );
```

The existing API for registration - `PushIOManager.registerApp()` brings up a modal prompt for requesting location access, which may not be suitable for all the apps. With the new API, it is now possible to do a PushIO registration without location data. This might be useful in a scenario where your app would like to defer the request for location. 

If you do a PushIO registration without location data, it is recommended to also do a PushIO registration with location data at a later stage when your app is ready to request location access.


### Upgrading PushIO SDK 6.31/6.32 to 6.33
#### Minimum Android SDK Version
PushIO SDK now requires a minimum Android SDK version of 16. Update the module-level **`build.gradle`** file as follows:

```
android {
    defaultConfig {
        minSdkVersion 16
    }
}
```
 
#### Runtime Permissions
Requesting device location shows a prompt at runtime for apps targeting Android Marshmallow (API 23) and above. See [Permissions](https://developer.android.com/preview/features/runtime-permissions.html) for more details.


### Upgrading PushIO SDK 6.29.1 to 6.31
#### Minimum Android SDK Version 
- PushIO SDK now requires minimum SDK version of 14 (Ice Cream Sandwich). So remember to update your modules' `build.gradle` file,
```
    android {
      defaultConfig {
         minSdkVersion 14
      }
    }
```
#### GCM Update
- The internal GCM implementation has been updated to not conflict with other libraries using GCM within your app. As part of this change, you are required to declare the following services under the `<application>` tag in the AndroidManifest.xml file.
```xml
   <application>
    <service android:name="com.pushio.manager.PIOGCMRegistrationIntentService"
        android:exported="false"/>
    <service
        android:name="com.pushio.manager.PIOInstanceIDListenerService"
        android:exported="false">
      <intent-filter>
        <action android:name="com.google.android.gms.iid.InstanceID" />
      </intent-filter>
    </service>
   </application>
```
- Also, as part of this update, the following libraries are now required in the `dependencies` section of your modules' `build.gradle` file. 
```
dependencies {
  compile 'com.google.android.gms:play-services-location:10.2.0'
  compile 'com.google.android.gms:play-services-gcm:10.2.0'
}
```
#### GCM Update - Troubleshooting 
If you followed the above steps and registration was successful but have not received any push notifications since upgrading the PushIO SDK, 
- Verify that your Google project has been correctly imported into Firebase. If you have not yet done that, you will need to migrate your GCM project from Google Cloud to Firebase as Firebase is the recommended platform for Cloud Messaging going forward.
- Try changing the Server Key to the new format as generated by Firebase. 
  - In the Firebase console, select your project, click the Settings menu icon (the icon that resembles a gear), then choose 'Project Settings' and under 'Cloud Messaging' tab, you should see the new format `Server Key` along with the old Server Key.
  - Copy the new Server Key and add it to the PushIO/RI dashboard as shown in section [1.2] of our Step-by-Step guides (http://docs.oracle.com/cloud/latest/marketingcs_gs/OMCFA/android/step-by-step/, http://docs.oracle.com/cloud/latest/marketingcs_gs/OMCFB/android/step-by-step/)



## Other Resources
* [Downloads + Documenation] (http://docs.push.io)
* [Sign In / Sign Up] (https://manage.push.io)

## Contact
* Support: [My Oracle Support] (http://support.oracle.com)

Copyright © 2016, Oracle Corporation and/or its affiliates. All rights reserved. Oracle and Java are registered trademarks of Oracle and/or its affiliates. Other names may be trademarks of their respective owners.
