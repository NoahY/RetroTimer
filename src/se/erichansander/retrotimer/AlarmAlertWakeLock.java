/*
 * Copyright (C) 2008 The Android Open Source Project
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

package se.erichansander.retrotimer;

import android.content.Context;
import android.os.PowerManager;

/**
 * Hold a wakelock that can be acquired in the AlarmReceiver and
 * released in the TimerAlert activity.
 * 
 * This file is borrowed almost without modification from the
 * DeskClock app included with Android.
 */
class AlarmAlertWakeLock {

	private static final String TAG = "AlarmAlertWakeLock";

    private static PowerManager.WakeLock sCpuWakeLock;

    static void acquireCpuWakeLock(Context context) {
        if (sCpuWakeLock != null) {
            return;
        }

        PowerManager pm =
                (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        sCpuWakeLock = pm.newWakeLock(
        		PowerManager.SCREEN_DIM_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE,
                TAG);
        sCpuWakeLock.acquire();
    }

    static void releaseCpuLock() {
        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }
}
