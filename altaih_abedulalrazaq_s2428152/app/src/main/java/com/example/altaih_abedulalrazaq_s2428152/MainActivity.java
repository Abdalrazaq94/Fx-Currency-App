package com.example.altaih_abedulalrazaq_s2428152;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String urlSource = "https://www.fx-exchange.com/gbp/rss.xml";

    private ArrayList<CurrencyItem> currencyList = new ArrayList<>();
    private String feedDate = "";

    private final long REFRESH_INTERVAL = 10 * 60 * 1000;
    private Handler handler = new Handler();

    public ArrayList<CurrencyItem> getCurrencyList() { return currencyList; }
    public String getFeedDate() { return feedDate; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        startProgress();
        handler.postDelayed(refreshRunnable, REFRESH_INTERVAL);
    }

    private final Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            startProgress();
            handler.postDelayed(this, REFRESH_INTERVAL);
        }
    };

    public void startProgress() {
        new Thread(new DownloadTask(urlSource)).start();
    }

    private class DownloadTask implements Runnable {

        private String url;

        public DownloadTask(String url) {
            this.url = url;
        }

        @Override
        public void run() {

            StringBuilder builder = new StringBuilder();

            try {
                URL urlObj = new URL(url);
                URLConnection conn = urlObj.openConnection();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                reader.close();

            } catch (Exception e) {
                Log.e("Thread", "Download error: " + e.getMessage());
                return;
            }

            String rawXml = builder.toString();
            int start = rawXml.indexOf("<?");
            int end = rawXml.indexOf("</rss>") + 6;
            String cleanedXml = rawXml.substring(start, end);

            XmlParser parser = new XmlParser();
            currencyList = parser.parse(cleanedXml);


            feedDate = getCurrentTime();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadFragments();
                }
            });
        }
    }

    private void loadFragments() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(
                R.id.bottomFragmentContainer,
                AllCurrenciesFragment.newInstance(currencyList, feedDate)
        );
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(refreshRunnable);
    }

    //  NEW FUNCTION FOR TIMESTAMP
    private String getCurrentTime() {
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("MMM dd yyyy HH:mm:ss 'UTC'", java.util.Locale.UK);

        sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
        return sdf.format(new java.util.Date());
    }
}
