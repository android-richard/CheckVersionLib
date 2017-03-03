package com.allenliu.sample;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.allenliu.versionchecklib.AVersionService;
import com.allenliu.versionchecklib.HttpRequestMethod;
import com.allenliu.versionchecklib.VersionParams;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;


import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DAYU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //所要申请的权限
        String[] perms =
                {
                        Manifest.permission.INTERNET
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_PHONE_STATE
                };

        if (EasyPermissions.hasPermissions(this, perms)) {//检查是否获取该权限
            Log.i(TAG, "已获取权限");
        } else {
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }



    }

    public void onClick(View view) {
        VersionParams versionParams=null;
       stopService(new Intent(this,DemoService.class));
        switch (view.getId()) {
            case R.id.btn1:
                versionParams = new VersionParams()
                        .setRequestUrl("http://www.baidu.com")
                        .setRequestMethod(HttpRequestMethod.GET);
                break;
            case R.id.btn2:
                HttpParams params = new HttpParams();
                HttpHeaders header = new HttpHeaders();
                versionParams = new VersionParams()
                        .setRequestUrl("http://www.baidu.com")
                        .setRequestParams(params)
                        .setRequestMethod(HttpRequestMethod.GET)
                        .setHttpHeaders(header)
                        .setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
                break;
            case R.id.btn3:
                versionParams = new VersionParams()
                        .setRequestUrl("http://www.baidu.com")
                        .setRequestMethod(HttpRequestMethod.GET)
                        .setCustomDownloadActivityClass(CustomVersionDialogTwoActivity.class);
                break;

        }
        Intent intent = new Intent(this, DemoService.class);
        intent.putExtra(AVersionService.VERSION_PARAMS_KEY, versionParams);
        startService(intent);

    }
}
