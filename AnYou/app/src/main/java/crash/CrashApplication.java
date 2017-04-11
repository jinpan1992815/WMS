package crash;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by Jinpan on 2017/3/30 0030.
 */
public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }

}
