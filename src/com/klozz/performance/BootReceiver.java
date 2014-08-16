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
package com.klozz.performance;

import com.klozz.performance.utils.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver implements Constants {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Check first if root is accessable and busyox is installed
        if (!rootHelper.rootAccess() || !rootHelper.busyboxInstalled()) return;

        // Run set on boot
        if (mUtils.getBoolean("setonboot", false, context)) {
            String savedCommands = mUtils.getString(COMMAND_NAME, "", context);

            if (!savedCommands.isEmpty()) {
                String[] files = savedCommands.split(mCommandControl.fileSplit);
                for (String file : files)
                    rootHelper
                            .runCommand(mUtils.getString(file, "ls", context));
            }

            mUtils.toast(
                    context.getString(R.string.booted_up,
                            context.getString(R.string.app_name)), context);
        }

        // Run custom commander
        if (mUtils.getBoolean("customcommander", false, context)) {
            String savedCommands = mUtils.getString(
                    mCustomCommanderFragment.prefName, "", context);

            if (!savedCommands.isEmpty()) {
                for (String command : mCustomCommanderFragment
                        .getSavedCommands(savedCommands))
                    rootHelper.runCommand(command);
            }

            mUtils.toast(
                    context.getString(R.string.booted_up,
                            context.getString(R.string.custom_commander)),
                    context);
        }

        return;
    }
}
