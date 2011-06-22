package org.safermobile.jbox.controller;

import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: kevin
 * Date: 4/1/11
 * Time: 3:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class App {
	public static final String TAG = "JBC";
    public static void logi(String val)
    {
        Log.i(TAG, val);
    }
    public static void loge(String val, Exception e)
    {
        Log.e(TAG, val, e);
    }

}
