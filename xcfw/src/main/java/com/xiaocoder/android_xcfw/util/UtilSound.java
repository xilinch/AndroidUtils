package com.xiaocoder.android_xcfw.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;

/**
 * @author xiaocoder on 2015/8/3
 * @email fengjingyu@foxmail.com
 * @description
 */
public class UtilSound {

    /**
     * 扬声器 听筒
     */
    public static void setSpeakerphoneOn(Activity activity, boolean on) {

        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);

        if (on) {
            audioManager.setSpeakerphoneOn(true);
        } else {
            //关闭扬声器
            audioManager.setSpeakerphoneOn(false);
            //把声音设定成Earpiece（听筒）出来，设定为正在通话中
            audioManager.setMode(AudioManager.MODE_IN_CALL);
        }
    }

    public static void setSpeakerphoneOn2(Activity activity, boolean on) {

        AudioManager audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);

        if (on) {
            audioManager.setSpeakerphoneOn(true);
            // add by xjs on 20151123 08:13 start
            // 修复：“当手机中启动过大白云诊后，打电话时，默认为扬声器模式”的问题。
            audioManager.setMode(AudioManager.MODE_NORMAL);
            // add by xjs on 20151123 08:13 end
        } else {
            //关闭扬声器
            audioManager.setSpeakerphoneOn(false);
            //把声音设定成Earpiece（听筒）出来，设定为正在通话中
            // modify by xjs on 20151121 15:25 start
            // 设置声音模式，使用 MODE_IN_COMMUNICATION 代替 MODE_IN_CALL，修复JIRA上904的问题。
            // audioManager.setMode(AudioManager.MODE_IN_CALL); // deleted by xjs on 20151121 15:28
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            // modify by xjs on 20151121 15:25 end
        }
    }
}
