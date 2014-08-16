/*
 * Copyright (C) 2014 Carlos Jesús <TeamMEX@XDA-Developers>
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

package com.klozz.performance.fragments;

import com.klozz.performance.R;
import com.klozz.performance.utils.Constants;
import com.klozz.performance.utils.CommandControl.CommandType;
import com.klozz.performance.utils.interfaces.DialogReturn;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

public class LowMemoryKillerFragment extends PreferenceFragment implements
        Constants {

    private Preference[] mMinFree;
    private Preference[] mProfile;

    private int maxValue = 200;

    private String[] mProfileValues = new String[] {
            "512,1024,1280,2048,3072,4096", "1024,2048,2560,4096,6144,8192",
            "1024,2048,4096,8192,12288,16384",
            "2048,4096,8192,16384,24576,32768",
            "4096,8192,16384,32768,49152,65536" };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(
                getActivity());

        String[] minfrees = lowmemorykillerHelper.getMinFrees();
        mMinFree = new Preference[minfrees.length];
        for (int i = 0; i < minfrees.length; i++) {
            mMinFree[i] = prefHelper.setPreference(getResources()
                    .getStringArray(R.array.lmk_names)[i],
                    lowmemorykillerHelper.getMinFree(i) / 256
                            + getString(R.string.mb), getActivity());

            root.addPreference(mMinFree[i]);
        }

        root.addPreference(prefHelper.setPreferenceCategory(
                getString(R.string.profiles), getActivity()));

        mProfile = new Preference[mProfileValues.length];
        for (int i = 0; i < mProfileValues.length; i++) {
            mProfile[i] = prefHelper.setPreference(getResources()
                    .getStringArray(R.array.lmk_profiles)[i],
                    mProfileValues[i], getActivity());

            root.addPreference(mProfile[i]);
        }

        setPreferenceScreen(root);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            final Preference preference) {

        for (int i = 0; i < lowmemorykillerHelper.getMinFrees().length; i++)
            if (preference == mMinFree[i]) {
                final int position = i;
                String[] modifiedvalues = new String[maxValue + 1];
                String[] values = new String[maxValue + 1];
                for (int x = 0; x <= maxValue; x++) {
                    modifiedvalues[x] = x + getString(R.string.mb);
                    values[x] = String.valueOf(x * 256);
                }
                mDialog.showSeekBarDialog(modifiedvalues, values, mMinFree[i]
                        .getSummary().toString(), new DialogReturn() {
                    @Override
                    public void dialogReturn(String value) {
                        String commandvalue = "";
                        String[] currentvalue = lowmemorykillerHelper
                                .getMinFrees();
                        for (int i = 0; i < currentvalue.length; i++) {
                            String command = i == position ? value
                                    : currentvalue[i];

                            commandvalue = !commandvalue.isEmpty() ? commandvalue
                                    + "," + command
                                    : command;
                        }
                        mCommandControl.runCommand(commandvalue, LMK_MINFREE,
                                CommandType.GENERIC, getActivity());
                        refresh();
                    }
                }, getActivity());
            }

        for (int i = 0; i < mProfileValues.length; i++)
            if (preference == mProfile[i]) {
                mCommandControl.runCommand(preference.getSummary().toString(),
                        LMK_MINFREE, CommandType.GENERIC, getActivity());
                refresh();
            }

        return true;
    }

    private void refresh() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(10);
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            String[] minfrees = lowmemorykillerHelper
                                    .getMinFrees();
                            for (int i = 0; i < minfrees.length; i++) {
                                mMinFree[i].setSummary(lowmemorykillerHelper
                                        .getMinFree(i)
                                        / 256
                                        + getString(R.string.mb));
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
