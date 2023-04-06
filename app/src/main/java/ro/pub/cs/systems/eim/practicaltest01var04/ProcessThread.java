package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

public class ProcessThread extends Thread{
    String nameFirst;
    String groupFirst;
    Context context;

    private boolean isRunning = true;
    public ProcessThread(Context context, String name, String group) {
        this.context = context;
        nameFirst = name + group;
        groupFirst = group + name;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESS_THREAD_TAG, "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[0]);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, new Date(System.currentTimeMillis()) + " " + nameFirst);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            Log.e(Constants.PROCESS_THREAD_TAG, interruptedException.getMessage());
            interruptedException.printStackTrace();
        }
    }

    public void stopService() {
        isRunning = false;
    }
}
