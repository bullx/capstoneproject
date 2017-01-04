package com.mrk.karan.securityanalyzer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.net.Uri;
import android.provider.Settings;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.content.Context;
import android.content.pm.*;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.*;

import android.util.Log;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.bluetooth.*;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.widget.SeekBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;

import android.nfc.NfcAdapter;
import android.app.admin.DevicePolicyManager;
import android.os.AsyncTask;
import android.app.KeyguardManager;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Main class which extends activity of android
 */
public class MainActivity extends Activity {

    permission_seperator t;
    private BluetoothAdapter ba;
    String system_infomation;
    double risk1;
    double risk2;
    double risk_final;
    String risk;
    NfcAdapter na;
    SeekBar sb_sys;
    SeekBar sb_app;
    SeekBar polarity;
    SeekBar subjectivity;
    SeekBar pol_excel;
    SeekBar pol_good;
    SeekBar pol_bad;
    SeekBar pol_worst;
    Button button;
    Button sentiment_analysis;
    ApplicationInfo app;
    int security_rating;
    PermissionInfo permission;
    String key_secure;
    String phonerootstatus;
    String nfc_status;
    boolean lock_pattern;
    boolean lock_pattern_visible;
    String download_score;
    double system_score;
    get_app_metadata gtm;
    ArrayList<String> appobjects = new ArrayList<String>();
    int appobjects_rating = 0;
    ArrayList<String> appnames = new ArrayList<String>();
    ArrayList<String> permissionandprotection = new ArrayList<String>();
    ArrayList<String> appratings = new ArrayList<String>();
    ArrayList<String> appdownloads = new ArrayList<String>();
    ArrayList<ArrayList<String>> al = new ArrayList<ArrayList<String>>();
    ArrayList<String> al1 = new ArrayList<String>();
    ArrayList<String> al2 = new ArrayList<String>();
    ArrayList<String> al3 = new ArrayList<String>();
    ArrayList<String> al4 = new ArrayList<String>();
    static int count;
    Button exit;
    TextView tv;
    String debug_status;
    String encryption_status;
    int counter = 0;
    int counter2 = 0;
    int polarity_excellent = 0;
    int polarity_good = 0;
    int polarity_bad = 0;
    int polarity_worst = 0;
    String item = null;
    int no_comments = 0;
    CustomProgress cp;
    ArrayList<CustomProgress> seekbarlist;
    float seek_size = 100;
    seek_custom seekbar ;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Intialization
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exit = (Button) findViewById(R.id.exit);
        button = (Button) findViewById(R.id.button);
        sentiment_analysis = (Button) findViewById(R.id.sent_ana);
        tv = (TextView) findViewById(R.id.output);
        t = new permission_seperator();
        gtm = new get_app_metadata();
        custom_seekbar_initialization();


        /**
         * button of evaluation
         */
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gettingpermissions();
                getappratingsanddownloads();
                getting_protection();
                getting_system_information();
                tv.setText(system_infomation);

            }

        });
        /**
         * button for reset
         */
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        sentiment_analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentiment();
            }
        });
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                seekbar_reset();
                textview_reset();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<String> applications = new ArrayList<String>();

        String dirPath = "//storage//emulated//0//data//";
        File dir = new File(dirPath);
        String[] files = dir.list();
        if (files.length == 0) {
            //     System.out.println("The directory is empty");

        } else {
            for (String aFile : files) {
                applications.add(aFile);
                Log.d("pappu ", aFile);
                //    System.out.println(aFile);
            }
        }

        //Spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, applications);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void custom_seekbar_initialization() {
        Log.d("", "in init");
        seekbar = ((seek_custom) findViewById(R.id.polarity_seek));
        seekbar.getThumb().mutate().setAlpha(0);
        displayonseek_polarity(888.0f, 888.0f);
        seekbar = ((seek_custom) findViewById(R.id.subjectivity_seek));
        seekbar.getThumb().mutate().setAlpha(0);
        displayonseek_polarity(888.0f, 888.0f);
        seekbar = ((seek_custom) findViewById(R.id.sub_high_seek));
        seekbar.getThumb().mutate().setAlpha(0);
        displayonseek_subjective(888.0f, 888.0f, 888.0f);
        seekbar = ((seek_custom) findViewById(R.id.sub_med_seek));
        seekbar.getThumb().mutate().setAlpha(0);
        displayonseek_subjective(888.0f, 888.0f, 888.0f);
        seekbar = ((seek_custom) findViewById(R.id.sub_low_seek));
        seekbar.getThumb().mutate().setAlpha(0);
        displayonseek_subjective(888.0f, 888.0f, 888.0f);
    }

    private void displayonseek_subjective(float one, float two,float three) {
        seekbarlist = new ArrayList<CustomProgress>();

        if (one == 888.0f || two == 888.0f) {

            cp = new CustomProgress();
            cp.completed = 100;
            cp.color = R.color.grey;
            seekbarlist.add(cp);
        } else {
            cp = new CustomProgress();
            cp.completed = (one / seek_size) * 100;

            cp.color = R.color.green;
            seekbarlist.add(cp);

            cp = new CustomProgress();
            cp.completed= (two / seek_size) * 100;
            cp.color = R.color.red;
            seekbarlist.add(cp);

        cp = new CustomProgress();
        cp.completed = (three /seek_size) * 100;
        cp.color = R.color.blue;
        seekbarlist.add(cp);


        }
        seekbar.set(seekbarlist);
        seekbar.invalidate();
    }
    private void displayonseek_polarity(float one, float two) {
        seekbarlist= new ArrayList<CustomProgress>();


        if (one == 888.0f || two == 888.0f) {

            cp = new CustomProgress();
            cp.completed= 100;
            cp.color = R.color.grey;
            seekbarlist.add(cp);
        } else {
           cp = new CustomProgress();
            cp.completed = (one / seek_size) * 100;
            cp.color = R.color.green;
            seekbarlist.add(cp);

            cp= new CustomProgress();
            cp.completed = (two / seek_size) * 100;
            cp.color = R.color.red;
            seekbarlist.add(cp);

        }
        seekbar.set(seekbarlist);
        seekbar.invalidate();
    }

    //get ratings of apps and downloads
    private void getappratingsanddownloads() {

        for (int i = 0; i < appobjects.size(); i++) {
//            for (int i = 0; i < 1; i++) {
            try {
                count++;
                new ReadAppMetadata().execute("https://data.42matters.com/api/v2.0/android/apps/lookup.json?p=" +
                        appobjects.get(i) +
                        "&access_token=684647493785df6198875a222a57f70325eef16a");
                Thread.currentThread().wait();
            } catch (Exception e) {

            }
        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /**
     * Getting infomation from JSON object of the api called
     */
    private class ReadAppMetadata extends AsyncTask
            <String, Void, String> {
        protected String doInBackground(String... s) {
            return get_String(s[0]);
        }

        /**
         * @param res - string of the json object from the website
         * @return
         */
        private String get_String(String res) {
            String input = res;
            String sb = new String();
            try {
                BufferedReader br = null;
                InputStream is = null;
                HttpEntity he = null;
                String s1;
                HttpClient hpc = new DefaultHttpClient();
                HttpResponse resp = hpc.execute(new HttpGet(input));

                if (status_checker(resp)) {
                    he = resp.getEntity();
                    is = he.getContent();
                    br = new BufferedReader(
                            new InputStreamReader(is));

                    while ((s1 = br.readLine()) != null) {
                        sb += s1;
                    }
                    is.close();
                }
            } catch (Exception e) {
                e.getLocalizedMessage();
            }

            return sb;
        }

        /**
         * @param resp - site code whether it is working or not
         * @return true or false
         */
        private boolean status_checker(HttpResponse resp) {
            StatusLine sl = resp.getStatusLine();
            int statusCode = sl.getStatusCode();

            return statusCode == HttpStatus.SC_OK;
        }

        /**
         * @param result- result passed from get_string to retriving app information
         *                and giving out the result
         *                <p/>
         *                Rules for application security written in the finally block
         */
        protected void onPostExecute(String result) {

            String packname = "";
            try {

                JSONObject js = new JSONObject(result);
                String rating = js.getString("rating");
                String downloads = js.getString("downloads");
                Log.d("rating " + rating, "");
                packname = js.getString("package_name");
                appratings.add(rating);
                appdownloads.add(downloads);

                // Rules for app security for checking the rating of each application
                if (Float.parseFloat(rating) > 1.0 && Float.parseFloat(rating) <= 3.0) {

                    risk = "high";
                } else if (Float.parseFloat(rating) > 3.0) {

                    risk = "low";
                }

                // classifying the number of downloads
                if (downloads.equals("1,000 - 5,000") || downloads.equals("10,000 - 50,000")) {
                    download_score = "low";
                } else if (downloads.equals("100,000 - 500,000") || downloads.equals("1,000,000 - 5,000,000")) {
                    download_score = "moderate";
                } else if (downloads.equals("10,000,000 - 50,000,000") || downloads.equals("100,000,000 - 500,000,000")
                        || downloads.equals("1,000,000,000 - 5,000,000,000")) {
                    download_score = "high";
                }

                // classifying the application risk level based on the the above two rules given
                if (risk.equals("low") && download_score.equals("high")) {
                    security_rating = 10;
                } else if (risk.equals("low") && download_score.equals("moderate")) {
                    security_rating = 9;
                } else if (risk.equals("low") && download_score.equals("low")) {
                    security_rating = 8;
                } else if (risk.equals("high") && download_score.equals("high")) {
                    security_rating = 6;
                } else if (risk.equals("high") && download_score.equals("moderate")) {
                    security_rating = 5;
                } else if (risk.equals("high") && download_score.equals("low")) {
                    security_rating = 4;
                }


                appobjects_rating = appobjects_rating + security_rating;

            } catch (Exception e) {
            } finally {

                count--;
                // setting out the progress bar for displaying the result
                if (count == 0) {

                    risk2 = ((appobjects_rating * 25) / (appobjects.size() * 10));
                    risk2 = 25 - risk2;
                    risk_final = risk1 + risk2;
                    sb_app = (SeekBar) findViewById(R.id.seek);
                    sb_app.setClickable(false);
                    sb_app.setFocusable(false);
                    sb_app.setEnabled(false);
                    sb_app.setProgress(((int) (risk_final)));
                    sb_app.setOnTouchListener(new OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            sb_app.setEnabled(false);

                            return true;
                        }
                    });

                }
            }

            /**
             *Application result for giving out as text
             */
            TextView tv = (TextView) findViewById(R.id.app_result);
            tv.setText("Application Security Risk level " + (int) risk_final);
        }
    }

    /**
     * gets the permissions of each installed apps using the inbuilt package manager
     */
    private void gettingpermissions() {
        // get metadata for apps and it is stored in the list of type packinfo
        List<PackageInfo> installed_packages = getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA);

        // run the loop for list size
        for (int i = 0; i < installed_packages.size(); i++) {

            PackageInfo pki = installed_packages.get(i);

            //check for if app is system or not
            boolean system_app_flag = (((pki.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) != true);
            /**
             * if it is not system app then add to the appobjects which stores
             * package name and appname which is name of the application
             */
            if (system_app_flag) {
                appobjects.add(pki.packageName);
                String AppName = pki.applicationInfo.loadLabel(getPackageManager()).toString();
                appnames.add(AppName);
            }
        }
    }

    /**
     * get the protection level of each applcaition classifying into categories
     */
    private void getting_protection() {
        String level;
        String android_perm = "android";
        PackageInfo pi = null;
        /**
         * this will store the permissions
         */
        try {
            pi = getPackageManager().getPackageInfo(android_perm, PackageManager.GET_PERMISSIONS);
        } catch (Exception e) {
            e.getLocalizedMessage();
        }

// check if permissions is not null
        if (pi.permissions != null) {

            // run loop for finding the permissions
            for (PermissionInfo pp : pi.permissions) {

                if (pp.protectionLevel == PermissionInfo.PROTECTION_NORMAL) {
                    level = "normal";
                } else if (pp.protectionLevel == PermissionInfo.PROTECTION_SIGNATURE) {
                    level = "signature";
                } else if (pp.protectionLevel == PermissionInfo.PROTECTION_DANGEROUS) {
                    level = "dangerous";
                } else if (pp.protectionLevel == PermissionInfo.PROTECTION_SIGNATURE_OR_SYSTEM) {
                    level = "signatureOrSystem";
                } else {
                    level = "nil";
                }
                permissionandprotection.add(pp.name + " " + level);
                ;
            }
        }
        // passed on to the permission_seperator class to retrive the seperated types and get their count
        al = t.tp1(permissionandprotection);
        al1 = al.get(0);
        al2 = al.get(1);
        al3 = al.get(2);
        al4 = al.get(3);
        // get count of dangerous, normal,signature apps
        int signature = al1.size();
        int normal = al2.size();
        int dangerous = al3.size();

        // formula for calculating the risk1 which is added to final risk
        risk1 = ((dangerous * 0.8 + normal * 0.4 + signature * 0.1) / (dangerous + signature + normal)) * 75;

    }

    /**
     * gets the system information of the device
     */
    private void getting_system_information() {

        // this gets the device information of the phone
        system_infomation = "\nBrand: " + Build.BRAND
                + "\nModel: " + Build.MODEL
                + "\nAndroid Version: " + Build.VERSION.RELEASE;

        WifiManager wm = (WifiManager) getSystemService(Context.
                WIFI_SERVICE);
        boolean wasEnabled
                = wm.isWifiEnabled();
        wm.setWifiEnabled(true);
        if (wm.isWifiEnabled()) {
        }


        //Root status check at the following locations to find out that su file present or not
        boolean rootcheck = false;
        String[] root_loc = {"/system/sd/xbin/su", "/system/bin/su", "/system/bin/failsafe/su", "/system/xbin/su", "/data/local/xbin/su",
                "/data/local/su", "/data/local/bin/su", "/sbin/su"};
        int t = 0;
        while (t < root_loc.length) {
            File f = new File(root_loc[t]);
            if (f.exists()) {
                rootcheck = true;
                break;
            }
            t++;
        }


        if (rootcheck) {
            phonerootstatus = "highrisk";
            system_score += 2.5;

        } else {
            phonerootstatus = "lowrisk";
            system_score += 5;
        }

        // NFC available check
        na = NfcAdapter.getDefaultAdapter(MainActivity.this);

        if (na == null) {
            nfc_status = "NFC not Available";
            system_score += 5;
        } else {
            if (na.isEnabled()) {
                nfc_status = " highrisk";
                system_score += 2.5;
            } else {
                nfc_status = "lowrisk";
                system_score += 5;
            }
        }

        // Debug mode check
        boolean debugging_flag = Secure.getInt(getContentResolver(), Secure.ADB_ENABLED, 0) == 1;

        if (debugging_flag) {
            debug_status = "highrisk";
            system_score += 1;
        } else {
            debug_status = "lowrisk";
            system_score += 5;
        }

        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        // encyption check on device
        if ((dpm.getStorageEncryptionStatus() == DevicePolicyManager.ENCRYPTION_STATUS_ACTIVE)) {
            encryption_status = " lowrisk";
            system_score += 5;
        } else {
            encryption_status = "highrisk";
            system_score += 1;
        }

        // keyguard check
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);

        // multiple rules for device lock checking
        if (km.isKeyguardSecure()) {
            key_secure = "lowrisk";
            //Lock screen check is pattern
            if (Secure.getInt(getContentResolver(), Secure.LOCK_PATTERN_ENABLED, 0) == 1) {
                lock_pattern = true;
                // lock screen pattern is visible or not
                if (Secure.getInt(getContentResolver(), Secure.LOCK_PATTERN_VISIBLE, 0) == 1) {
                    lock_pattern_visible = true;
                    system_score += 2.5;
                } else {
                    lock_pattern_visible = false;
                    system_score += 5;
                }
            } else {
                lock_pattern = false;
                system_score += 5;
            }
        } else {
            key_secure = "highrisk";
            system_score += 1;
        }
        // formula for getting the system result
        double sys_final_result = (system_score / 25) * 100;
        sys_final_result = 100 - sys_final_result;

        //setting the text view
        TextView tv1 = (TextView) findViewById(R.id.system_result);
        tv1.setText("System Security Risk level " + (int) sys_final_result);

        // setting the seekbar to show level of security
        sb_sys = (SeekBar) findViewById(R.id.seek2);
        sb_sys.setClickable(false);
        sb_sys.setFocusable(false);
        sb_sys.setEnabled(false);
        sb_sys.setProgress(((int) (sys_final_result)));
        sb_sys.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sb_sys.setEnabled(false);
                return true;
            }
        });

    }

    // Method which does the sentiment analysis
    private void sentiment() {
        int high_rating = 0;
        int medium_rating = 0;
        int low_rating = 0;
        polarity = (SeekBar) findViewById(R.id.polarity_seek);
        subjectivity = (SeekBar) findViewById(R.id.subjectivity_seek);
        Context context = getApplicationContext();
        InputStream inputStream = null;
        FileInputStream fis = null;
        try {
            inputStream = new FileInputStream(new File("//storage//emulated//0//data//" + item));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            String[] row = null;

            ArrayList<String> files = new ArrayList<>();
            while ((csvLine = reader.readLine()) != null) {
                row = csvLine.split("\n");
                files.add(row[0].toString());
                Log.d("hehe ", row[0].toString());

            }
            int file_size = files.size();
            TextView data_size = (TextView) findViewById(R.id.datasize);
            data_size.setText("DataSize " + file_size);
            Double sub_avg = 0.0;
            int not_high_rating=0;
            int not_med_rating=0;
            int not_low_rating=0;
            for (int j = 0; j < file_size; j++) {

                String[] values = files.get(j).split(",");
                int rating = Integer.valueOf(values[0]);
                Double pol_value = Double.valueOf(values[1]);
                Double sub_value = Double.valueOf(values[2]);

                if (pol_value > 0.0) {
                    counter++;
                }
                if (pol_value >= 0.5) {
                    polarity_excellent++;
                } else if (pol_value > 0.0 && pol_value < 0.5) {
                    polarity_good++;
                } else if (pol_value >= -0.4 && pol_value <= 0.0) {
                    polarity_bad++;
                } else if (pol_value < -0.4) {
                    polarity_worst++;
                }
                if (sub_value > 0.4) {
                    counter2++;
                }
                if (sub_value == 0) {
                    no_comments++;
                }


                if (rating >= 4 && sub_value > 0.4) {
                    high_rating++;
                } else if (rating >= 4 && sub_value <= 0.4) {
                    not_high_rating++;
                }else if (rating == 3 && sub_value > 0.4) {
                    medium_rating++;
                }else if (rating == 3 && sub_value <= 0.4) {
                    not_med_rating++;
                }
                else if (rating < 3 && sub_value > 0.4) {
                    low_rating++;
                }else if (rating < 3 && sub_value <= 0.4) {
                    not_low_rating++;
                }

                sub_avg += sub_value;

            }


            float pol = calc_percent(counter, file_size);
            float sub = calc_percent(counter2, file_size);
            float p_excel = calc_percent(polarity_excellent, file_size);
            float p_good = calc_percent(polarity_good, file_size);
            float p_bad = calc_percent(polarity_bad, file_size);
            float p_worst = calc_percent(polarity_worst, file_size);

            TextView high_tv = (TextView) findViewById(R.id.sub_high_text);
            float temp_high = calc_percent(high_rating, file_size);
            float not_temp_high = calc_percent(not_high_rating, file_size);
            high_tv.setText("Subjective info for High ratings " + (int)temp_high+"%"+"\n"
                    +"Objective info for High ratings " + (int)not_temp_high+"%");
            seekbar = (seek_custom) findViewById(R.id.sub_high_seek);
            seekbar.getThumb().mutate().setAlpha(0);
            displayonseek_subjective(temp_high, not_temp_high,100-temp_high-not_temp_high);

            TextView med_tv = (TextView) findViewById(R.id.sub_med_text);
            float temp_med = calc_percent(medium_rating, file_size);
            float not_temp_med =  calc_percent(not_med_rating, file_size);
            med_tv.setText("Subjective info for Medium ratings " + (int)temp_med+"%"+"\n"
                    +"Objective info for Medium ratings " + (int)not_temp_med+"%");
            seekbar = (seek_custom) findViewById(R.id.sub_med_seek);
            seekbar.getThumb().mutate().setAlpha(0);
            displayonseek_subjective(temp_med, not_temp_med,100-temp_med-not_temp_med);

            TextView low_tv = (TextView) findViewById(R.id.sub_low_text);
            float temp_low = calc_percent(low_rating, file_size);
            float not_temp_low =  calc_percent(not_low_rating, file_size);
            low_tv.setText("Subjectivity for Low ratings " + (int)temp_low+"%"+"\n"
                    +"Objective info for Low ratings " + (int)not_temp_low+"%");
            seekbar = (seek_custom) findViewById(R.id.sub_low_seek);
            seekbar.getThumb().mutate().setAlpha(0);
            displayonseek_subjective(temp_low,not_temp_low,100-temp_low-not_temp_low);
//            Log.d("high",String.valueOf(not_high_rating)+"\t"+String.valueOf(high_rating)+"\n"
//                    + String.valueOf(not_med_rating)+"\t"+String.valueOf(medium_rating)+"\n"
//                    +String.valueOf(not_low_rating)+"\t"+String.valueOf(low_rating));

//            Log.d("lol files ", String.valueOf(file_size));
//            Log.d("lol counter ", String.valueOf(counter));
//            Log.d("counter2 ", String.valueOf(counter2));
//            Log.d("polarity", String.valueOf((int)pol));
//            Log.d("subjectivity", String.valueOf((int)sub));
//            Log.d("excel", String.valueOf(polarity_excellent));
//            Log.d("good", String.valueOf(polarity_good));
//            Log.d("bad", String.valueOf(polarity_bad));
//            Log.d("worst", String.valueOf(polarity_worst));

            polarity = (SeekBar) findViewById(R.id.polarity_seek);

//            polarity.setClickable(false);
//            polarity.setFocusable(false);
//            polarity.setEnabled(false);
//            polarity.setProgress((Math.round(pol)));


            // polarity_display((int) pol,polarity,(TextView) findViewById(R.id.app_pol),"Polarity ");
            seekbar = ((seek_custom) findViewById(R.id.polarity_seek));
            seekbar.getThumb().mutate().setAlpha(0);
            displayonseek_polarity(pol, 100 - pol);
            TextView tv1 = (TextView) findViewById(R.id.app_pol);
            tv1.setText("Polarity Information " + "\n"+(int) pol+"% Positive "+(100- (int) pol)+"% Negative");

            pol_excel = (SeekBar) findViewById(R.id.pol_excellent_seek);
            polarity_display(p_excel, pol_excel, (TextView) findViewById(R.id.pol_excellent_text), "Excellent");
            pol_good = (SeekBar) findViewById(R.id.pol_good_seek);
            polarity_display(p_good, pol_good, (TextView) findViewById(R.id.pol_good_text), "Good");
            pol_bad = (SeekBar) findViewById(R.id.pol_bad_seek);
            polarity_display(p_bad, pol_bad, (TextView) findViewById(R.id.pol_bad_text), "Bad");
            pol_worst = (SeekBar) findViewById(R.id.pol_worst_seek);
            polarity_display(p_worst, pol_worst, (TextView) findViewById(R.id.pol_worst_text), "Worst");

            seekbar = (seek_custom) findViewById(R.id.subjectivity_seek);
            seekbar.getThumb().mutate().setAlpha(0);
            displayonseek_polarity(sub, 100 - sub);
//            subjectivity.setClickable(false);
//            subjectivity.setFocusable(false);
//            subjectivity.setEnabled(false);
//            subjectivity.setProgress((Math.round(sub)));
            TextView tv2 = (TextView) findViewById(R.id.app_sub);
            tv2.setText("Subjective info  " + (int) sub+"% \n" +"Objective info "+(100- (int) sub)+"%");
//            subjectivity.setOnTouchListener(new OnTouchListener() {
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    subjectivity.setEnabled(false);
//                    return true;
//                }
//            });
            files.clear();
            polarity_excellent = polarity_bad = polarity_worst = polarity_good = counter = counter2 = 0;
        } catch (IOException ex) {
            throw new RuntimeException("Error reading csv" + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("error: " + e);
            }
        }
    }

    private void polarity_display(float p, SeekBar ss, TextView tv, String st) {
        // ss = (SeekBar) findViewById(R.id.polarity_seek);
        ss.setClickable(false);
        ss.setFocusable(false);
        ss.setEnabled(false);
        ss.setProgress((Math.round(p)));
        tv.setText(st + " " + (int) p+"%");


    }

    void textview_reset() {
        TextView tv3 = (TextView) findViewById(R.id.app_pol);
        tv3.setText("Polarity ");
        TextView tv4 = (TextView) findViewById(R.id.app_sub);
        tv4.setText("Subjectivity ");
        TextView data_size = (TextView) findViewById(R.id.datasize);
        data_size.setText("DataSize ");
        TextView tv5 = (TextView) findViewById(R.id.pol_excellent_text);
        tv5.setText("Excellent ");
        TextView tv6 = (TextView) findViewById(R.id.pol_good_text);
        tv6.setText("Good ");
        TextView tv7 = (TextView) findViewById(R.id.pol_bad_text);
        tv7.setText("Bad ");
        TextView tv8 = (TextView) findViewById(R.id.pol_worst_text);
        tv8.setText("Worst ");
        TextView tv9 = (TextView) findViewById(R.id.sub_high_text);
        tv9.setText("");
        TextView tv10 = (TextView) findViewById(R.id.sub_med_text);
        tv10.setText("");
        TextView tv11 = (TextView) findViewById(R.id.sub_low_text);
        tv11.setText("");
    }

    void seekbar_reset() {
        polarity = (SeekBar) findViewById(R.id.polarity_seek);
        subjectivity = (SeekBar) findViewById(R.id.subjectivity_seek);
        polarity.setProgress(0);
        subjectivity.setProgress(0);
        pol_excel = (SeekBar) findViewById(R.id.pol_excellent_seek);
        pol_excel.setProgress(0);
        pol_good = (SeekBar) findViewById(R.id.pol_good_seek);
        pol_good.setProgress(0);
        pol_bad = (SeekBar) findViewById(R.id.pol_bad_seek);
        pol_bad.setProgress(0);
        pol_worst = (SeekBar) findViewById(R.id.pol_worst_seek);
        pol_worst.setProgress(0);
    }

    float calc_percent(int counter, int file_size) {
        return ((float) counter / (float) file_size) * 100;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
