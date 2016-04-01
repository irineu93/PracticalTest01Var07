package ro.pub.cs.systems.eim.practicaltest01var07;

import android.content.Context;
import android.content.Intent;

import java.util.Date;

/**
 * Created by irineu on 01.04.2016.
 */
public class ProcessingThread extends Thread {

    Context context = null;
    boolean continueRunning;
    String nameText, groupText;

    public ProcessingThread(Context context, String nameText, String groupText) {
        this.context = context;
        this.nameText = nameText;
        this.groupText = groupText;
        continueRunning = true;
    }


    @Override
    public void run() {
        while(continueRunning) {
            sendMessage(0);
            sleep();
            sendMessage(1);
            sleep();
        }
    }

    public void sendMessage(int type) {
        Intent intent = new Intent();

        if (type == 0) {
            intent.setAction("action1");
            intent.putExtra("message", new Date(System.currentTimeMillis()).toString() + " " + nameText + " " + groupText);
        }
        else if (type == 1) {
            intent.setAction("action2");
            intent.putExtra("message", new Date(System.currentTimeMillis()).toString() + " " + nameText + " " + groupText);
        }

        context.sendBroadcast(intent);
    }

    public void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopThread() {
        continueRunning = false;
    }

}
