package com.edge.greasy.billing;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.android.vending.billing.IInAppBillingService;

/**
 * Created by Patil on 8/7/2016.
 */
public class WordlistBillingManager {
    public static boolean checkForSubscription(IInAppBillingService mService){
       return false; 
    }
}
