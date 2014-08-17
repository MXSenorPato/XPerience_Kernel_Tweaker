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

package com.klozz.performance.helpers;

import com.klozz.performance.utils.Constants;

public class CPUVoltageHelper implements Constants {

    private String[] mCpuFreqs;

    public String[] getVoltages() {
        if (mUtils.existFile(String.format(CPU_VOLTAGE, 0))) {
            String value = mUtils.readFile(String.format(CPU_VOLTAGE, 0));
            if (value != null) {
                String[] lines = value.split(" mV");
                String[] voltages = new String[lines.length];
                for (int i = 0; i < lines.length; i++)
                    voltages[i] = lines[i].split("mhz: ")[1];
                return voltages;
            }
        }
        return null;
    }

    public String[] getFreqs() {
        if (mCpuFreqs == null) if (mUtils.existFile(String.format(CPU_VOLTAGE,
                0))) {
            String value = mUtils.readFile(String.format(CPU_VOLTAGE, 0));
            if (value != null) {
                String[] lines = value.split(" mV");
                mCpuFreqs = new String[lines.length];
                for (int i = 0; i < lines.length; i++)
                    mCpuFreqs[i] = lines[i].split("mhz: ")[0].trim();
            }
        }
        return mCpuFreqs;
    }

    public boolean hasCpuVoltage() {
        return mUtils.existFile(String.format(CPU_VOLTAGE, 0));
    }

}
