package ro.pub.cs.systems.eim.practicaltest01var07;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PracticalTest01Var07Service extends Service {

    ProcessingThread th = null;

    public PracticalTest01Var07Service() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String nameText = intent.getStringExtra("nameEditText");
        String groupText = intent.getStringExtra("groupEditText");

        th = new ProcessingThread(this, nameText, groupText);
        th.start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        th.stopThread();
        super.onDestroy();
    }
}
