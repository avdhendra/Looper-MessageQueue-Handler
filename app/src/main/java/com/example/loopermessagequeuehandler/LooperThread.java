package com.example.loopermessagequeuehandler;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class LooperThread extends Thread{
    private static final String TAG = "Looper Thread";
    public Handler handler; //assoicate to the message queue of background thread
    public Looper looper;
    @Override
    public void run(){
        Looper.prepare(); //set looper in background thread and also mesage queue they tie together
        // handler=new Handler(); //it know how to interpret the runnable interface

        handler=new ExampleHandler(); //it know how to interpret our messages
        looper=Looper.myLooper();
        Looper.loop(); //it run until we dont return from it

        /*This order is important
        because this handler wont find looper for this thread


        */
     /*   for (int i = 0; i < 5; i++) {
            Log.d(TAG, "run: "+i);
            SystemClock.sleep(1000); //this is similar to thread.sleep we dont need to surround it will the try catch block

        }*/
        Log.d(TAG, "End of Run() ");
    }
}
