package ph.coreproc.android.philippineincometax;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by johneris on 10/14/15.
 */
public class App extends Application {

    private Tracker mTracker;

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//            adb shell setprop log.tag.GAv4 DEBUG
//            adb logcat -s GAv4
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "76EDAHxfU1xx81pb0e7T7LBqQ92TjNn8AlM7iBEa", "YhsyzGxnAJMEIz9Nx92ZGICCp76GINMl2d8vvhfc");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
