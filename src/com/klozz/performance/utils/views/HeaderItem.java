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

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.klozz.performance.R;
import com.klozz.performance.utils.interfaces.Item;
import com.klozz.performance.utils.views.HeaderListView.RowType;

public class HeaderItem implements Item {
    private final String name;

    public HeaderItem(String name) {
        this.name = name;
    }

    @Override
    public int getViewType() {
        return RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view = convertView == null ? (View) inflater.inflate(
                R.layout.list_header, null) : convertView;

        ((TextView) view.findViewById(R.id.list_header_title)).setText(name);

        return view;
    }

    @Override
    public Fragment getFragment() {
        return null;
    }

    @Override
    public String getTitle() {
        return name;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

}
