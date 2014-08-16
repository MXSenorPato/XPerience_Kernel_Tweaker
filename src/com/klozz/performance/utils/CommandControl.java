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

package com.klozz.performance.utils;

import android.app.Activity;

public class CommandControl implements Constants {

    public final String fileSplit = "qwerfghuioplmbb";

    public enum CommandType {
        GENERIC, CPU
    }

    public void commandSaver(String file, String value, Activity activity) {
        mUtils.saveString(file, value, activity);

        String name = mUtils.getString(COMMAND_NAME, "nothing_found", activity);
        if (!name.contains(file)) mUtils.saveString(COMMAND_NAME,
                name.equals("nothing_found") ? file : name + fileSplit + file,
                activity);
    }

    public void runGeneric(String file, String value, Activity activity) {
        rootHelper.runCommand("echo " + value + " > " + file);

        commandSaver(file, "echo " + value + " > " + file, activity);
    }

    public void startModule(String module, boolean save, Activity activity) {
        rootHelper.runCommand("start " + module);

        if (save) commandSaver(module, "start " + module, activity);
    }

    public void stopModule(String module, boolean save, Activity activity) {
        rootHelper.runCommand("stop " + module);
        if (module.equals(CPU_MPDEC)) bringCoresOnline();

        if (save) commandSaver(module, "stop " + module, activity);
    }

    public void bringCoresOnline() {
        new Thread() {
            public void run() {
                try {
                    for (int i = 0; i < cpuHelper.getCoreCount(); i++)
                        rootHelper.runCommand("echo 1 > "
                                + String.format(CPU_CORE_ONLINE, i));

                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void runCommand(final String value, final String file,
            CommandType command, final Activity activity) {
        if (command == CommandType.CPU) {
            if (cpuHelper.getCoreCount() > 1) {
                new Thread() {
                    public void run() {
                        boolean stoppedMpdec = false;
                        if (rootHelper.moduleActive(CPU_MPDEC)) {
                            stopModule(CPU_MPDEC, false, activity);
                            stoppedMpdec = true;
                        }

                        for (int i = 0; i < cpuHelper.getCoreCount(); i++)
                            runGeneric(String.format(file, i), value, activity);

                        if (stoppedMpdec) startModule(CPU_MPDEC, false,
                                activity);
                    }
                }.start();
            }
        } else if (command == CommandType.GENERIC) {
            runGeneric(file, value, activity);
        }
    }

}
