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

package com.klozz.performance.utils.views;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.klozz.performance.R;

public class GenericListView extends ArrayAdapter<String> {

    private final Activity activity;
    private final List<String> filesString;
    private final List<String> values;

    public GenericListView(Activity activity, List<String> filesString,
            List<String> values) {
        super(activity, R.layout.list_generic, filesString);
        this.activity = activity;
        this.filesString = filesString;
        this.values = values;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (activity != null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_generic, null, true);
            TextView title = (TextView) rowView.findViewById(R.id.list_title);
            TextView value = (TextView) rowView.findViewById(R.id.list_value);
            if (filesString.size() > 0) title
                    .setText(filesString.get(position));
            if (values.size() > 0) value.setText(values.get(position));

            return rowView;
        }
        return null;
    }
}
