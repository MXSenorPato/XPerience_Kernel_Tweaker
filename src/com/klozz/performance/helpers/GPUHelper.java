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

public class GPUHelper implements Constants {

    private String GPU_2D_CUR_FREQ;
    public String GPU_2D_MAX_FREQ;
    private String GPU_2D_AVAILABLE_FREQS;
    public String GPU_2D_SCALING_GOVERNOR;

    public String[] mGpu2dFreqs;

    private String GPU_3D_CUR_FREQ;
    public String GPU_3D_MAX_FREQ;
    private String GPU_3D_AVAILABLE_FREQS;
    public String GPU_3D_SCALING_GOVERNOR;
    private String[] GPU_3D_AVAILABLE_GOVERNORS;

    public String[] mGpu3dFreqs;

    public String[] getGpu2dGovernors() {
        return GPU_GENERIC_GOVERNORS.split(" ");
    }

    public String getGpu2dGovernor() {
        if (GPU_2D_SCALING_GOVERNOR != null) if (mUtils
                .existFile(GPU_2D_SCALING_GOVERNOR)) {
            String value = mUtils.readFile(GPU_2D_SCALING_GOVERNOR);
            if (value != null) return value;
        }
        return "";
    }

    public boolean hasGpu2dGovernor() {
        if (GPU_2D_SCALING_GOVERNOR == null) for (String file : GPU_2D_SCALING_GOVERNOR_ARRAY)
            if (mUtils.existFile(file)) GPU_2D_SCALING_GOVERNOR = file;
        return GPU_2D_SCALING_GOVERNOR != null;
    }

    public String[] getGpu2dFreqs() {
        if (GPU_2D_AVAILABLE_FREQS != null) if (mGpu2dFreqs == null) if (mUtils
                .existFile(GPU_2D_AVAILABLE_FREQS)) {
            String value = mUtils.readFile(GPU_2D_AVAILABLE_FREQS);
            if (value != null) return value.split(" ");
        }
        return mGpu2dFreqs;
    }

    public boolean hasGpu2dFreqs() {
        if (GPU_2D_AVAILABLE_FREQS == null) {
            for (String file : GPU_2D_AVAILABLE_FREQS_ARRAY)
                if (mUtils.existFile(file)) GPU_2D_AVAILABLE_FREQS = file;
        }
        return GPU_2D_AVAILABLE_FREQS != null;
    }

    public int getGpu2dMaxFreq() {
        if (GPU_2D_MAX_FREQ != null) if (mUtils.existFile(GPU_2D_MAX_FREQ)) {
            String value = mUtils.readFile(GPU_2D_MAX_FREQ);
            if (value != null) return Integer.parseInt(value);
        }
        return 0;
    }

    public boolean hasGpu2dMaxFreq() {
        if (GPU_2D_MAX_FREQ == null) {
            for (String file : GPU_2D_MAX_FREQ_ARRAY)
                if (mUtils.existFile(file)) GPU_2D_MAX_FREQ = file;
        }
        return GPU_2D_MAX_FREQ != null;
    }

    public int getGpu2dCurFreq() {
        if (GPU_2D_CUR_FREQ != null) if (mUtils.existFile(GPU_2D_CUR_FREQ)) {
            String value = mUtils.readFile(GPU_2D_CUR_FREQ);
            if (value != null) return Integer.parseInt(value);
        }
        return 0;
    }

    public boolean hasGpu2dCurFreq() {
        if (GPU_2D_CUR_FREQ == null) {
            for (String file : GPU_2D_CUR_FREQ_ARRAY)
                if (mUtils.existFile(file)) GPU_2D_CUR_FREQ = file;
        }
        return GPU_2D_CUR_FREQ != null;
    }

    public String[] getGpu3dGovernors() {
        if (GPU_3D_AVAILABLE_GOVERNORS == null) for (String file : GPU_3D_AVAILABLE_GOVERNORS_ARRAY)
            if (GPU_3D_AVAILABLE_GOVERNORS == null) if (mUtils.existFile(file)) {
                String value = mUtils.readFile(file);
                if (value != null) GPU_3D_AVAILABLE_GOVERNORS = value
                        .split(" ");
            }
        return GPU_3D_AVAILABLE_GOVERNORS == null ? GPU_GENERIC_GOVERNORS
                .split(" ") : GPU_3D_AVAILABLE_GOVERNORS;
    }

    public String getGpu3dGovernor() {
        if (GPU_3D_SCALING_GOVERNOR != null) if (mUtils
                .existFile(GPU_3D_SCALING_GOVERNOR)) {
            String value = mUtils.readFile(GPU_3D_SCALING_GOVERNOR);
            if (value != null) return value;
        }
        return "";
    }

    public boolean hasGpu3dGovernor() {
        if (GPU_3D_SCALING_GOVERNOR == null) for (String file : GPU_3D_SCALING_GOVERNOR_ARRAY)
            if (mUtils.existFile(file)) GPU_3D_SCALING_GOVERNOR = file;
        return GPU_3D_SCALING_GOVERNOR != null;
    }

    public String[] getGpu3dFreqs() {
        if (GPU_3D_AVAILABLE_FREQS != null) if (mGpu3dFreqs == null) if (mUtils
                .existFile(GPU_3D_AVAILABLE_FREQS)) {
            String value = mUtils.readFile(GPU_3D_AVAILABLE_FREQS);
            if (value != null) return value.split(" ");
        }
        return mGpu3dFreqs;
    }

    public boolean hasGpu3dFreqs() {
        if (GPU_3D_AVAILABLE_FREQS == null) {
            for (String file : GPU_3D_AVAILABLE_FREQS_ARRAY)
                if (mUtils.existFile(file)) GPU_3D_AVAILABLE_FREQS = file;
        }
        return GPU_3D_AVAILABLE_FREQS != null;
    }

    public int getGpu3dMaxFreq() {
        if (GPU_3D_MAX_FREQ != null) if (mUtils.existFile(GPU_3D_MAX_FREQ)) {
            String value = mUtils.readFile(GPU_3D_MAX_FREQ);
            if (value != null) return Integer.parseInt(value);
        }
        return 0;
    }

    public boolean hasGpu3dMaxFreq() {
        if (GPU_3D_MAX_FREQ == null) {
            for (String file : GPU_3D_MAX_FREQ_ARRAY)
                if (mUtils.existFile(file)) GPU_3D_MAX_FREQ = file;
        }
        return GPU_3D_MAX_FREQ != null;
    }

    public int getGpu3dCurFreq() {
        if (GPU_3D_CUR_FREQ != null) if (mUtils.existFile(GPU_3D_CUR_FREQ)) {
            String value = mUtils.readFile(GPU_3D_CUR_FREQ);
            if (value != null) return Integer.parseInt(value);
        }
        return 0;
    }

    public boolean hasGpu3dCurFreq() {
        if (GPU_3D_CUR_FREQ == null) {
            for (String file : GPU_3D_CUR_FREQ_ARRAY)
                if (mUtils.existFile(file)) GPU_3D_CUR_FREQ = file;
        }
        return GPU_3D_CUR_FREQ != null;
    }

}
