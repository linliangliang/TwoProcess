package liang.wust.com.twoprocess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import liang.wust.com.twoprocess.constans.Constans;
import liang.wust.com.twoprocess.service.LocalService;
import liang.wust.com.twoprocess.service.RemoteService;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btn_start_back_service;
    private Button btn_end_back_service;
    private Button btn_end_remote_service;
    private Button btn_end_all_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_back_service:
                startBackService();
                break;
            case R.id.btn_end_back_service:
                stopBackService();
                break;
            case R.id.btn_end_remote_service:
                stopRemoteService();
                break;
            case R.id.btn_end_all_service:
                endAllService();
                break;
            default:
                break;
        }
    }

    private void init() {
        btn_start_back_service = findViewById(R.id.btn_start_back_service);
        btn_end_back_service = findViewById(R.id.btn_end_back_service);
        btn_end_remote_service = findViewById(R.id.btn_end_remote_service);
        btn_end_all_service = findViewById(R.id.btn_end_all_service);

        btn_start_back_service.setOnClickListener(this);
        btn_end_back_service.setOnClickListener(this);
        btn_end_remote_service.setOnClickListener(this);
        btn_end_all_service.setOnClickListener(this);
    }

    private void startBackService() {

        startService(new Intent(this, LocalService.class));
    }

    private void stopBackService() {
        stopService(new Intent(this, LocalService.class));
    }

    private void stopRemoteService() {
        stopService(new Intent(this, RemoteService.class));
    }

    private void endAllService() {
        Constans.INSTANCE.isOpenServiceDefend = Constans.INSTANCE.stopServiceDefend;//结束进程守护
        stopRemoteService();
        stopBackService();
    }

}
