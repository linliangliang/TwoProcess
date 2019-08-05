package liang.wust.com.twoprocess.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import liang.wust.com.twoprocess.IMyAidlInterface;
import liang.wust.com.twoprocess.constans.Constans;

/**
 * Created by lenovo on 2019/8/5.
 */

public class LocalService extends Service {

    private final static String TAG = LocalService.class.getName();
    private static int count = 0;
    private static Timer timer = null;
    private MyBinder mBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                Log.i("LocalService", "connected with " + iMyAidlInterface.getServiceName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (Constans.INSTANCE.isOpenServiceDefend == 1) {
                Log.i(TAG, "链接断开，重新启动 RemoteService......");
                startService(new Intent(LocalService.this, RemoteService.class));
                bindService(new Intent(LocalService.this, RemoteService.class), connection, Context.BIND_IMPORTANT);
            }

        }
    };

    public LocalService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "--" + count++);
                }
            }, 0, 1000);
        }


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Constans.INSTANCE.isOpenServiceDefend == 1) {
            Log.i(TAG, "本地服务启动......");
            startService(new Intent(LocalService.this, RemoteService.class));

            bindService(new Intent(LocalService.this, RemoteService.class), connection, Context.BIND_IMPORTANT);
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new MyBinder();
        return mBinder;
    }

    private class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public String getServiceName() throws RemoteException {
            return LocalService.class.getName();
        }

    }

    @Override
    public void onDestroy() {
        // TODO 自动生成的方法存根
        super.onDestroy();
        try {
            if (timer != null) {
                timer.cancel();
                timer=null;
            }
            unbindService(connection);
        } catch (Exception e) {
            System.exit(0);
        }

    }
}


