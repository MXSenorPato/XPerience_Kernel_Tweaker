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

public class IOHelper implements Constants {

    public enum StorageType {
        INTERNAL, EXTERNAL
    }

    public int getReadahead(StorageType type) {
        String file = type == StorageType.INTERNAL ? IO_INTERNAL_READ_AHEAD
                : IO_EXTERNAL_READ_AHEAD;
        if (mUtils.existFile(file)) {
            String values = mUtils.readFile(file);
            if (values != null) return Integer.parseInt(values);
        }
        return 0;
    }

    public String[] getSchedulers(StorageType type) {
        String file = type == StorageType.INTERNAL ? IO_INTERNAL_SCHEDULER
                : IO_EXTERNAL_SCHEDULER;
        if (mUtils.existFile(file)) {
            String values = mUtils.readFile(file);
            if (values != null) {
                String[] valueArray = values.split(" ");
                String[] out = new String[valueArray.length];

                for (int i = 0; i < valueArray.length; i++)
                    out[i] = valueArray[i].replace("[", "").replace("]", "");

                return out;
            }
        }
        return null;
    }

    public String getScheduler(StorageType type) {
        String file = type == StorageType.INTERNAL ? IO_INTERNAL_SCHEDULER
                : IO_EXTERNAL_SCHEDULER;
        if (mUtils.existFile(file)) {
            String values = mUtils.readFile(file);
            if (values != null) {
                String[] valueArray = values.split(" ");

                for (String value : valueArray)
                    if (value.contains("[")) return value.replace("[", "")
                            .replace("]", "");
            }
        }
        return "";
    }

    public boolean hasExternalStorage() {
        return mUtils.existFile(IO_EXTERNAL_READ_AHEAD)
                || mUtils.existFile(IO_EXTERNAL_SCHEDULER);
    }

}
