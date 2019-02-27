package ir.alirezaeasazade.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import ir.alirezaeasazade.server.IComman.Stub;

public class MyService extends Service {

    private MyImpl myImpl = new MyImpl();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myImpl;
    }

    private class MyImpl extends Stub {

        @Override
        public int calculate(final int num1, final int num2) throws RemoteException {
            return num1 + num2;
        }
    }

}
