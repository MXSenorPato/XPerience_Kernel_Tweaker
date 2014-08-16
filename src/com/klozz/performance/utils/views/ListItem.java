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

import com.klozz.performance.R;
import com.klozz.performance.utils.interfaces.Item;
import com.klozz.performance.utils.views.HeaderListView.RowType;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ListItem implements Item {
    private final String text;
    private final Fragment fragment;

    public ListItem(String text, Fragment fragment) {
        this.text = text;
        this.fragment = fragment;
    }

    @Override
    public int getViewType() {
        return RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view = convertView == null ? (View) inflater.inflate(
                R.layout.list_item, null) : convertView;

        ((TextView) view.findViewById(R.id.list_item_title)).setText(text);

        return view;
    }

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public String getTitle() {
        return text;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
