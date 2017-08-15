package wifi.hack.com.hacking;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by SR on 14/08/17.
 */

public class ScanResultActivity extends AppCompatActivity {

    TextView textView;

    String title;
    String result = "";
    ClipboardManager myClipboard;

    private InterstitialAd interstitialAd;
    private int i;
    private ProgressDialog progressDialog;
    private String randomPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan_result);

        textView = (TextView) findViewById(R.id.textScan);

        title = getIntent().getExtras().getString("TITLE");
        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        printResults();
    }

    private void printResults() {
        final Handler handler = new Handler();

        final String[] results = {"Processing " + title, "Starting bash shell...", "Getting network " + title, "Resolving network " + title,
                "Analysis of the data packet success",
                   "Send Authorization code...", "Authorization successful",
               "The " + title + " is successful",  "Data processing...",
                "Congratulations to decipher passwords success"};

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        handler.post(new Runnable() {
            @Override
            public void run() {


                textView.append(results[i] + "\n");
                i++;
                if (i < results.length) {
                    handler.postDelayed(this, 2000);
                } else {

                            showDialog();


                }
            }
        });
    }

    private void showDialog() {

        loadFBAd();


        final AlertDialog.Builder builder;

        randomPassword=nextSessionId();

            builder = new AlertDialog.Builder(this);

        builder.setTitle("Password")
                .setMessage("Password to "+title+" " + randomPassword)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("COPY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        ClipData myClip;

                        myClip = ClipData.newPlainText("text", randomPassword);
                        myClipboard.setPrimaryClip(myClip);

                        dialog.dismiss();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        builder.show();
                    }
                },10000);
    }

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        return new BigInteger(66, random).toString(32);
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
}
