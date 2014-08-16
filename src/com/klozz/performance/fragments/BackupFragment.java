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

import java.io.File;
import java.text.DateFormat;
import java.util.Locale;

import com.klozz.performance.R;
import com.klozz.performance.helpers.RootHelper.PartitionType;
import com.klozz.performance.utils.Constants;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.widget.EditText;
import android.widget.LinearLayout;

public class BackupFragment extends PreferenceFragment implements Constants {

    private final String nameSplit = "XPerience";

    private PreferenceScreen root;
    private Preference mBoot, mRecovery, mFota;

    private Preference[] mBackups;

    private ProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new File(XPe_BACKUP).mkdirs();

        root = getPreferenceManager().createPreferenceScreen(getActivity());

        refresh();

        setPreferenceScreen(root);
    }

    private void refresh() {
        root.removeAll();

        if (rootHelper.getPartitionName(PartitionType.BOOT) != null) {
            mBoot = prefHelper
                    .setPreference(
                            getString(R.string.boot),
                            getString(R.string.backup_summary,
                                    getString(R.string.boot)), getActivity());

            root.addPreference(mBoot);
        }

        if (rootHelper.getPartitionName(PartitionType.RECOVERY) != null) {
            mRecovery = prefHelper.setPreference(
                    getString(R.string.recovery),
                    getString(R.string.backup_summary,
                            getString(R.string.recovery)), getActivity());

            root.addPreference(mRecovery);
        }

        if (rootHelper.getPartitionName(PartitionType.FOTA) != null) {
            mFota = prefHelper
                    .setPreference(
                            getString(R.string.fota),
                            getString(R.string.backup_summary,
                                    getString(R.string.fota)), getActivity());

            root.addPreference(mFota);
        }

        root.addPreference(prefHelper.setPreferenceCategory(
                getString(R.string.backups), getActivity()));

        File[] backups = new File(XPe_BACKUP).listFiles();

        if (backups.length < 1) root.addPreference(prefHelper.setPreference(
                null, getString(R.string.no_backups), getActivity()));
        else {
            // Date formater
            DateFormat f = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                    DateFormat.SHORT, Locale.getDefault());

            mBackups = new Preference[backups.length];
            for (int i = 0; i < backups.length; i++) {
                mBackups[i] = prefHelper.setPreference(
                        backups[i].getName().split(nameSplit)[0],
                        backups[i].getName().split(nameSplit)[1] + " "
                                + (backups[i].length() / 1024 / 1024)
                                + getString(R.string.mb) + ", "
                                + getString(R.string.last_modified) + ": "
                                + f.format(backups[i].lastModified()),
                        getActivity());
                root.addPreference(mBackups[i]);
            }
        }
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {

        if (preference == mBoot) backupDialog(PartitionType.BOOT);
        if (preference == mRecovery) backupDialog(PartitionType.RECOVERY);
        if (preference == mFota) backupDialog(PartitionType.FOTA);

        File[] backups = new File(XPe_BACKUP).listFiles();
        for (int i = 0; i < backups.length; i++)
            if (preference == mBackups[i]) showMenu(mBackups[i].getTitle()
                    .toString());

        return true;
    }

    private void backupDialog(final PartitionType partition) {

        String name = null;
        switch (partition) {
            case BOOT:
                name = getString(R.string.boot);
                break;
            case RECOVERY:
                name = getString(R.string.recovery);
                break;
            case FOTA:
                name = getString(R.string.fota);
                break;
        }
        final String partitionName = name;

        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 20, 30, 20);

        final EditText editor = new EditText(getActivity());
        editor.setHint(getString(R.string.name_of_backup));

        layout.addView(editor);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout)
                .setNegativeButton(getString(android.R.string.cancel),
                        new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {}
                        })
                .setPositiveButton(getString(android.R.string.ok),
                        new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                if (editor.getText().toString().isEmpty()) {
                                    mUtils.toast(
                                            getString(R.string.empty,
                                                    editor.getHint()),
                                            getActivity());
                                    return;
                                }
                                backup(editor.getText().toString() + ".img"
                                        + nameSplit + partitionName,
                                        rootHelper.getPartitionName(partition));
                            }
                        }).show();
    }

    private void backup(final String name, final String partition) {
        if (partition == null) {
            mUtils.toast(getString(R.string.partiton_failed), getActivity());
            return;
        }

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.backing_up));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new Thread() {
            public void run() {
                rootHelper.runCommand("rm -f /cache/tmp");
                rootHelper.getPartition(
                /*
                 * On device it is only possible /sdcard as external path when
                 * running as root
                 */
                XPe_BACKUP.replace(EXTERNAL_STORAGE_DIRECTORY, "/sdcard") + "/"
                        + name, partition);
                // Create a tmp file so we know when it is finished
                rootHelper.runCommand("touch /cache/tmp");
                while (true) {
                    if (mUtils.existFile("/cache/tmp")) {
                        rootHelper.runCommand("rm -f /cache/tmp");
                        dialog.dismiss();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refresh();
                            }
                        });
                        break;
                    }
                }
            }
        }.start();
    }

    private void showMenu(final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(getResources().getStringArray(R.array.backup_menu),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        String file = null;
                        File[] backups = new File(XPe_BACKUP).listFiles();
                        for (File backup : backups)
                            if (backup.getName().startsWith(name)) file = backup
                                    .getName();
                        switch (item) {
                            case 0:
                                restoreDialog(file);
                                break;
                            case 1:
                                new File(XPe_BACKUP + "/" + file).delete();
                                refresh();
                                break;
                        }
                    }
                }).show();
    }

    private void restoreDialog(final String file) {
        String partition = file.split(nameSplit)[1];
        PartitionType type = null;
        if (partition.equals(getString(R.string.boot))) type = PartitionType.BOOT;
        if (partition.equals(getString(R.string.recovery))) type = PartitionType.RECOVERY;
        if (partition.equals(getString(R.string.fota))) type = PartitionType.FOTA;
        final PartitionType partitionType = type;

        if (rootHelper.getPartitionName(partitionType) == null) {
            mUtils.toast(getString(R.string.partiton_failed), getActivity());
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(
                getString(R.string.overwrite,
                        rootHelper.getPartitionName(partitionType)))
                .setNegativeButton(getString(android.R.string.cancel),
                        new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {}
                        })
                .setPositiveButton(getString(android.R.string.ok),
                        new OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                restore(file, rootHelper
                                        .getPartitionName(partitionType));
                            }
                        }).show();
    }

    private void restore(final String name, final String partition) {

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.restoring));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new Thread() {
            public void run() {
                rootHelper.runCommand("rm -f /cache/tmp");
                rootHelper.writePartition(
                /*
                 * On device it is only possible /sdcard as external path when
                 * running as root
                 */
                XPe_BACKUP.replace(EXTERNAL_STORAGE_DIRECTORY, "/sdcard") + "/"
                        + name, partition);
                // Create a tmp file so we know when it is finished
                rootHelper.runCommand("touch /cache/tmp");
                while (true) {
                    if (mUtils.existFile("/cache/tmp")) {
                        rootHelper.runCommand("rm -f /cache/tmp");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }
                        });
                        break;
                    }
                }
            }
        }.start();
    }
}
