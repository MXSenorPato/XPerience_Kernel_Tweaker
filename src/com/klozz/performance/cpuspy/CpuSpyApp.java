/*
 * Copyright (C) 2014 Carlos Jesús <TeamMEX@XDA-Developers>
 * Copyright (C) Brandon Valosek, 2011 <bvalosek@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package com.klozz.performance.cpuspy;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

/** main application class */
public class CpuSpyApp extends Application {

    private static final String PREF_NAME = "CpuSpyPreferences";
    private static final String PREF_OFFSETS = "offsets";

    /** the long-living object used to monitor the system frequency states */
    private CpuStateMonitor _monitor = new CpuStateMonitor();

    /**
     * On application start, load the saved offsets and stash the current kernel
     * version string
     */
    @Override
    public void onCreate() {
        loadOffsets();
    }

    /** @return the internal CpuStateMonitor object */
    public CpuStateMonitor getCpuStateMonitor() {
        return _monitor;
    }

    /**
     * Load the saved string of offsets from preferences and put it into the
     * state monitor
     */
    public void loadOffsets() {
        SharedPreferences settings = getSharedPreferences(PREF_NAME,
                MODE_PRIVATE);
        String prefs = settings.getString(PREF_OFFSETS, "");

        if (prefs == null || prefs.length() < 1) { return; }

        // split the string by peroids and then the info by commas and load
        Map<Integer, Long> offsets = new HashMap<Integer, Long>();
        String[] sOffsets = prefs.split(",");
        for (String offset : sOffsets) {
            String[] parts = offset.split(" ");
            offsets.put(Integer.parseInt(parts[0]), Long.parseLong(parts[1]));
        }

        _monitor.setOffsets(offsets);
    }

    /**
     * Save the state-time offsets as a string e.g. "100 24, 200 251, 500 124
     * etc
     */
    public void saveOffsets(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(PREF_NAME,
                MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // build the string by iterating over the freq->duration map
        String str = "";
        for (Map.Entry<Integer, Long> entry : _monitor.getOffsets().entrySet()) {
            str += entry.getKey() + " " + entry.getValue() + ",";
        }

        editor.putString(PREF_OFFSETS, str);
        editor.commit();
    }
}