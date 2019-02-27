package ir.alirezaeasazade.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import ir.alirezaeasazade.server.IComman;
import ir.alirezaeasazade.server.IComman.Stub;
import ir.alirezaeasazade.server.R;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "aidl-app";

    private IComman mComman;

    private ServiceConnection serviceCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            Log.d(TAG, "client connected to service");
            mComman = Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnBind).setOnClickListener(view -> {
            Intent intent = new Intent("ir.alirezaeasazade.service.AIDL");
            bindService(convertImplicitIntentToExplicitIntent(intent), serviceCon, BIND_AUTO_CREATE);
        });

        findViewById(R.id.btnCalculate).setOnClickListener(view -> {
            try {
                Log.d(TAG, "calculation is " + mComman.calculate(1, 2));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    private Intent convertImplicitIntentToExplicitIntent(Intent implicitIntent) {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentServices(implicitIntent, 0);
        if (resolveInfoList == null || resolveInfoList.size() != 1) {
            return null;
        }
        ResolveInfo serviceInfo = resolveInfoList.get(0);
        ComponentName component = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }


}
