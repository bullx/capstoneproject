package com.mrk.karan.securityanalyzer;

/**
 * Created by karan on 6/7/15.
 */

    import android.app.Application;
    import android.content.pm.PackageInfo;

    public class AppData extends Application {

        PackageInfo packageInfo;

        public PackageInfo getPackageInfo() {
            return packageInfo;
        }

        public void setPackageInfo(PackageInfo packageInfo) {
            this.packageInfo = packageInfo;
        }
    }

