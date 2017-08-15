package wifi.hack.com.hacking;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.util.ArrayList;




public class WiFiDemo extends Activity {
    private static final String TAG = "WiFiDemo";
    public WifiManager wifi;
    private BroadcastReceiver receiver;
    private InterstitialAd interstitialAd;
    private ListView listView;
    private ArrayList<WifiModel> wifiList=new ArrayList<>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.listItem);





        setWiFiStatus();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadFBAd();
                Intent intent = new Intent (WiFiDemo.this, ScanResultActivity.class);

                intent.putExtra("TITLE",wifiList.get(position).getTitle());

                startActivity(intent);
            }
        });
    }

    private void setWiFiStatus() {
        // Setup WiFi
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        // Get WiFi status
        WifiInfo info = wifi.getConnectionInfo();
        

        for (ScanResult s : wifi.getScanResults()) {
            String item = s.toString();
            String[] vector_item = item.split(",");
            String item_essid = vector_item[0];
            String item_capabillities = vector_item[2];
            String item_level = vector_item[3];
            String ssid = item_essid.split(":")[1];
            String conName = item_essid.split(":")[0];

            String seurity = item_capabillities.split(":")[1];
            String level = item_level.split(":")[1];

            wifiList.add(new WifiModel(ssid, seurity, level, conName));

        }

        UsersAdapter adapterElements = new UsersAdapter(this,wifiList);
        

        listView.setAdapter(adapterElements);



        // Register Broadcast Receiver
        if (receiver == null)
            receiver = new WiFiScanReceiver(this);

        registerReceiver(receiver, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }



    public void loadFBAd() {
        // 757612227748135_757612487748109 (fb id)
        interstitialAd = new InterstitialAd(this, "1911594269114974_1911596535781414");
        AdSettings.addTestDevice("963f714e1f8e049eb5378bcdd7688326");
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                try {
                    interstitialAd.show();
                } catch (Exception e) {

                }
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        interstitialAd.loadAd();
    }


    public class UsersAdapter extends ArrayAdapter<WifiModel> {
        public UsersAdapter(Context context, ArrayList<WifiModel> users) {
            super(context, 0, users);
        }

        @Override
        public View getView(int position, View item, ViewGroup parent) {
            // Get the data item for this position
            WifiModel user = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (item == null) {
                item = LayoutInflater.from(getContext()).inflate(R.layout.items, parent, false);
            }
            // Lookup view for data population
            TextView tvSsid = (TextView) item.findViewById(R.id.tvSSID);
            tvSsid.setText(user.getTitle());

            TextView tvSecurity = (TextView) item.findViewById(R.id.tvSecurity);
            tvSecurity.setText(user.getSecurity());

            TextView conName = (TextView) item.findViewById(R.id.conName);
            conName.setText(user.getConName());

            TextView tvLevel = (TextView) item.findViewById(R.id.tvLevel);
            String level = user.getLevel().replaceAll("\\s+","");

            try {

                int i = Integer.parseInt(level);

                if (i > -50) {
                    tvLevel.setText("High");
                } else if (i < 50 && i > -80) {

                    tvLevel.setText("Medium");
                } else if (i <= -80) {
                    tvLevel.setText("Low");
                }

            } catch (NumberFormatException e) {

                e.printStackTrace();
            }
            // Return the completed view to render on screen
            return item;
        }
    }

	/*public  class AdapterElements extends ArrayAdapter<Object>
    {

		Activity context;

		public  AdapterElements (Activity context, List<WifiModel> wifiModels)
		{

			super(context,R.layout.items,wifiModels);
			this.context = context;
		}

		public  View getView (int position, View  convertView, ViewGroup parent)
		{

			LayoutInflater inflater = context.getLayoutInflater();
			View item =  inflater.inflate(R.layout.items,null);

			TextView tvSsid = (TextView) item.findViewById(R.id.tvSSID);
			tvSsid.setText(nets[position].getTitle());

			TextView tvSecurity = (TextView) item.findViewById(R.id.tvSecurity);
			tvSecurity.setText(nets[position].getSecurity());

			TextView conName = (TextView) item.findViewById(R.id.conName);
			conName.setText(nets[position].getConName());

			TextView tvLevel = (TextView) item.findViewById(R.id.tvLevel);
			String level = nets[position].getLevel();

			try
			{

				int i = Integer.parseInt(level);

				if (i >-50)
				{
					tvLevel.setText("High");
				}
				else if (i < 50 && i > -80)
				{

					tvLevel.setText("Medium");
				}
				else  if (i<= -80)
				{
					tvLevel.setText("Low");
				}

			}
			catch (NumberFormatException e)
			{


			}

			return  item;
		}
	}*/

}