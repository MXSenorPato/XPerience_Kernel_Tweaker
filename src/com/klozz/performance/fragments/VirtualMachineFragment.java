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

import com.klozz.performance.utils.Constants;
import com.klozz.performance.utils.CommandControl.CommandType;
import com.klozz.performance.utils.interfaces.DialogReturn;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

public class VirtualMachineFragment extends PreferenceFragment implements
        Constants {

    private Preference[] mVMs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(
                getActivity());

        mVMs = new Preference[virtualmachineHelper.getVMfiles().size()];
        for (int i = 0; i < virtualmachineHelper.getVMfiles().size(); i++) {
            mVMs[i] = prefHelper.setPreference(virtualmachineHelper
                            .getVMfiles().get(i).replace("_", " "),
                    virtualmachineHelper.getVMValue(virtualmachineHelper
                            .getVMfiles().get(i)), getActivity());

            root.addPreference(mVMs[i]);
        }

        setPreferenceScreen(root);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         final Preference preference) {

        for (int i = 0; i < virtualmachineHelper.getVMfiles().size(); i++)
            if (preference == mVMs[i]) mDialog.showDialogGeneric(VM_PATH + "/"
                    + virtualmachineHelper.getVMfiles().get(i), preference
                    .getSummary().toString(), new DialogReturn() {
                @Override
                public void dialogReturn(String value) {
                    preference.setSummary(value);
                }
            }, true, 0, CommandType.GENERIC, getActivity());

        return true;
    }

}
