package com.mrk.karan.securityanalyzer;

/**
 * Created by karan on 6/14/15.
 */

import java.util.*;
import java.util.regex.Pattern;
import java.lang.String.*;

import android.util.Log;

public class permission_seperator {
    //Initialzation of lists
    ArrayList<ArrayList<String>> newpermissions;
    ArrayList<String> al1 = new ArrayList<String>();
    ArrayList<String> al2 = new ArrayList<String>();
    ArrayList<String> al3 = new ArrayList<String>();
    ArrayList<String> al4 = new ArrayList<String>();

    public permission_seperator() {
        newpermissions = new ArrayList<ArrayList<String>>();
    }

    /**
     * @param app_permissions -pass arraylist of permissions
     * @return -  return arraylist separated into 4 categories.
     */
    public ArrayList tp1(ArrayList<String> app_permissions) {


        int count = 0;
        if (app_permissions != null) {
            for (int k = 0; k < app_permissions.size(); k++) {
                String text = app_permissions.get(k);
                // we separate the string given to extract the permissions by using the find function
                int find = text.lastIndexOf(".");
                String s = text.substring(find + 1, text.length());
                String[] element = s.split(" ");
                if (element[1].equals("signature")) {
                    al1.add(element[0]);
                } else if (element[1].equals("normal")) {
                    al2.add(element[0]);
                } else if (element[1].equals("dangerous")) {
                    al3.add(element[0]);
                } else if (element[1].equals("nil")) {
                    al4.add(element[0]);
                }

            }
        }
        // add the permissions found and store to their respective arraylist
        newpermissions.add(al1);
        newpermissions.add(al2);
        newpermissions.add(al3);
        newpermissions.add(al4);

        // return arraylist of arraylist
        return newpermissions;
    }
}


