package wifi.hack.com.hacking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

public class WiFiScanReceiver extends BroadcastReceiver {
  private static final String TAG = "WiFiScanReceiver";
  WiFiDemo wifiDemo;

  public WiFiScanReceiver(WiFiDemo wifiDemo) {
    super();
    this.wifiDemo = wifiDemo;
  }

  @Override
  public void onReceive(Context c, Intent intent) {

    try {
      List<ScanResult> results = wifiDemo.wifi.getScanResults();
      ScanResult bestSignal = null;
      for (ScanResult result : results) {
        if (bestSignal == null
                || WifiManager.compareSignalLevel(bestSignal.level, result.level) < 0)
          bestSignal = result;
      }


    }catch (Exception e){

    }
  }

}
