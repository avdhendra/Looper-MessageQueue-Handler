package com.example.loopermessagequeuehandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import static com.example.loopermessagequeuehandler.ExampleHandler.TASK_A;
import static com.example.loopermessagequeuehandler.ExampleHandler.TASK_B;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Activity";
    private LooperThread looperThread=new LooperThread();
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start=findViewById(R.id.btnStart);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                looperThread.start();


        /*

          start
           |
           | run()
           |
           |
           V
        terminate
        when we click on start btn we go to infinite looper
        and we perform taskA which count to some number
        and we can perform task A infinite time
        currently our thread look like this
        we want to to look like this

        and if we click the btn multiple time we execute thread one after other
not at the same time
        start
         |    *  messageQueue  |----<-|
         |    *                |      |
 run()   |    *                V      ^  looper
         |    *                |      |
         |    *                |-->---|
         V
       terminate

        */
            }
        });
    }

    public void stop(View view) {
        //looperThread.looper.quit(); //it will not run anymore messages
        //quitsaftely it will stop after our batch complete
        looperThread.looper.quit();
        //when we click on stop we return from infinite loop
        //we cannot start the task again because just like normal thread we cannot start multiple time

    }

    public void taskA(View view) {
        //create handler and asscoiate with ui to background thread
        Handler threadHandler=new Handler(looperThread.looper);
        //here we post it from ui thread to background thread

        //looperThread.handler.post(new Runnable() { can do both
        //one problme we face here that is memory leak
        //anonymous class is same as non static inner class
        //non static inner class have the reference to the outer class
        //it has implicit reference to main activity which cannot be destroyed
        //so if we rotate the device our activity goes through the destroy and activity is garbarge
        //but system clean the garbage if there is no reference to it but runnable has implicit reference to the activity
        //so as this is running our activity cant be clean
        //fixed it make runnable static class
//post create and sense the message under the hood


//        threadHandler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                for (int i = 0; i < 5; i++) {
//                    Log.d(TAG, "run: "+i);
//                    SystemClock.sleep(1000); //this is similar to thread.sleep we dont need to surround it will the try catch block
//
//                }
//            }
//        });
        // in runnable we pass peice of code to execute where we pass raw data

        Message msg=Message.obtain();
        msg.what=TASK_A; //what action u need the execute
        looperThread.handler.sendMessage(msg);



    }

    public void taskB(View view) {
        Message msg=Message.obtain();
        msg.what=TASK_B; //what action u need the execute
        looperThread.handler.sendMessage(msg);
    }
    //there is no reference to the main acitivty from runnable class
    //u cant access the activity variable anymore
    //if u want to access these variable use weak reference

    static class myrunnable implements Runnable{

        @Override
        public void run() {

        }
    }
}