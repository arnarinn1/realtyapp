package is.arnar.realty.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionHandler
{
    public static boolean isNetworkAvailable(Context mContext)
    {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiStatus   = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileStatus = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ((wifiStatus != null && wifiStatus.isConnected()) || ((mobileStatus != null) && mobileStatus.isConnected()));
    }
}
