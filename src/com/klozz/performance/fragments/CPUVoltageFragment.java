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
        import android.text.InputType;

public class CPUVoltageFragment extends PreferenceFragment implements Constants {

    private Preference[] mVoltage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PreferenceScreen root = getPreferenceManager()
                .createPreferenceScreen(getActivity());

        new Thread() {
            public void run() {
                mVoltage = new Preference[cpuVoltageHelper.getFreqs().length];
                for (int i = 0; i < cpuVoltageHelper.getFreqs().length; i++) {
                    mVoltage[i] = prefHelper.setPreference(
                            cpuVoltageHelper.getFreqs()[i]
                                    + getString(R.string.mhz),
                            cpuVoltageHelper.getVoltages()[i]
                                    + getString(R.string.mv), getActivity());

                    root.addPreference(mVoltage[i]);
                }
            }
        }.start();

        setPreferenceScreen(root);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {

        for (int i = 0; i < cpuVoltageHelper.getFreqs().length; i++)
            if (preference == mVoltage[i]) {
                final int position = i;
                mDialog.showDialogGeneric(
                        CPU_VOLTAGE,
                        cpuVoltageHelper.getVoltages()[i],
                        new DialogReturn() {
                            @Override
                            public void dialogReturn(String value) {
                                String commandvalue = "";
                                String[] currentvalue = cpuVoltageHelper
                                        .getVoltages();
                                for (int i = 0; i < currentvalue.length; i++) {
                                    String command = i == position ? value
                                            : currentvalue[i];

                                    commandvalue = !commandvalue.isEmpty() ? commandvalue
                                            + " " + command
                                            : command;
                                }
                                mCommandControl.runCommand(commandvalue,
                                        CPU_VOLTAGE, CommandType.CPU,
                                        getActivity());

                                new Thread() {
                                    public void run() {
                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        getActivity().runOnUiThread(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        for (int i = 0; i < cpuVoltageHelper
                                                                .getFreqs().length; i++)
                                                            mVoltage[i]
                                                                    .setSummary(cpuVoltageHelper
                                                                            .getVoltages()[i]
                                                                            + getString(R.string.mv));
                                                    }
                                                });
                                    }
                                }.start();
                            }
                        }, false, InputType.TYPE_CLASS_NUMBER, null,
                        getActivity());
            }

        return true;
    }
}
