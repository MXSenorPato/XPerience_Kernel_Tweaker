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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.klozz.performance.utils.Constants;

public class VirtualMachineHelper implements Constants {

    private List<String> vmFiles = new ArrayList<String>();

    private static final String[] supportedVM = { "dirty_ratio",
            "dirty_background_ratio", "dirty_expire_centisecs",
            "dirty_writeback_centisecs", "min_free_kbytes", "overcommit_ratio",
            "swappiness", "vfs_cache_pressure" };

    public String getVMValue(String file) {
        if (mUtils.existFile(VM_PATH + "/" + file)) {
            String value = mUtils.readFile(VM_PATH + "/" + file);
            if (value != null) return value;
        }
        return null;
    }

    public List<String> getVMfiles() {
        if (vmFiles.size() < 1) {
            File[] files = new File(VM_PATH).listFiles();
            if (files.length > 0) {
                for (String supported : supportedVM)
                    for (int i = 0; i < files.length; i++)
                        if (files[i].getName().equals(supported)) vmFiles
                                .add(files[i].getName());
            }
        }
        return vmFiles;
    }
}
