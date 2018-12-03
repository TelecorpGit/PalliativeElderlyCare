package com.telecorp.teledev.pec;

import android.util.Log;

public final class Debug {
    private Debug(){

    }

    public static void out (Object msg){
         Log.w ("wristband", msg.toString ());
    }
}