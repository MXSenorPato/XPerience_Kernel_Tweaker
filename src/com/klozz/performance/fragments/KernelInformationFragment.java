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

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class KernelInformationFragment extends Fragment implements Constants {

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {

        final ScrollView scroll = new ScrollView(getActivity());

        final LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 0, 10, 0);
        scroll.addView(layout);

        final TextView header = (TextView) inflater.inflate(
                R.layout.list_header, container, false);
        final TextView kernelVersion = new TextView(getActivity());
        final TextView header1 = (TextView) inflater.inflate(
                R.layout.list_header, container, false);
        final TextView cpuInfo = new TextView(getActivity());
        final TextView header2 = (TextView) inflater.inflate(
                R.layout.list_header, container, false);
        final TextView memInfo = new TextView(getActivity());

        layout.addView(header);
        layout.addView(kernelVersion);
        layout.addView(header1);
        layout.addView(cpuInfo);
        layout.addView(header2);
        layout.addView(memInfo);

        new Thread() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        header.setText(getString(R.string.kernel_version));
                        kernelVersion.setText(mUtils.readFile(PROC_VERSION));
                        header1.setText(getString(R.string.cpu_info));
                        cpuInfo.setText(mUtils.readFile(PROC_CPUINFO));
                        header2.setText(getString(R.string.memory_info));
                        memInfo.setText(mUtils.readFile(PROC_MEMINFO));
                    }
                });
            }
        }.start();

        return scroll;
    }
}
