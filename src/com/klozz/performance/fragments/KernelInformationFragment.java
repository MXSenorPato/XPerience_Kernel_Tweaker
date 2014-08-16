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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        ScrollView scroll = new ScrollView(getActivity());
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(10, 0, 10, 0);
        scroll.addView(layout);

        TextView header = (TextView) inflater.inflate(R.layout.list_header,
                container, false);

        header.setText(getString(R.string.kernel_version));
        layout.addView(header);

        TextView kernelVersion = new TextView(getActivity());
        kernelVersion.setText(mUtils.readFile(PROC_VERSION));
        layout.addView(kernelVersion);

        TextView header1 = (TextView) inflater.inflate(R.layout.list_header,
                container, false);

        header1.setText(getString(R.string.cpu_info));
        layout.addView(header1);

        TextView cpuInfo = new TextView(getActivity());
        cpuInfo.setText(mUtils.readFile(PROC_CPUINFO));
        layout.addView(cpuInfo);

        TextView header2 = (TextView) inflater.inflate(R.layout.list_header,
                container, false);

        header2.setText(getString(R.string.memory_info));
        layout.addView(header2);

        TextView memInfo = new TextView(getActivity());
        memInfo.setText(mUtils.readFile(PROC_MEMINFO));
        layout.addView(memInfo);

        return scroll;
    }
}
