package org.androidtown.shutterwordbook.Helper;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import org.androidtown.shutterwordbook.Activity.LockScreenActivity;

/**
 * Created by lab on 15. 7. 15..
 */
public class ScreenReceiver extends BroadcastReceiver {

    private KeyguardManager km = null;
    private KeyguardManager.KeyguardLock keyLock = null;
    private TelephonyManager telephonyManager = null;
    private boolean isPhoneIdle = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            if (km == null)
                km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

            if (keyLock == null)
                keyLock = km.newKeyguardLock(Context.KEYGUARD_SERVICE);

            if (telephonyManager == null) {
                telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
            }

            if (isPhoneIdle) {
                disableKeyguard();


                Intent i = new Intent(context, LockScreenActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }

    public void reenableKeyguard() {    // 기존 잠금화면 나타내기
        keyLock.reenableKeyguard();
    }

    public void disableKeyguard() { // 기존 잠금화면 없애기
        keyLock.disableKeyguard();
    }

    // 전화중인지 확인
    private PhoneStateListener phoneListener = new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, String incomingNumber){
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE :
                    isPhoneIdle = true;
                    break;
                case TelephonyManager.CALL_STATE_RINGING :
                    isPhoneIdle = false;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK :
                    isPhoneIdle = false;
                    break;
            }
        }
    };
}