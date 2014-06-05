/*
 * Copyright (C) 2014 NOBUOKA Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.vividcode.android.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;

class ExpandableListAdapterWrapper implements ExpandableListAdapter {

    protected final ExpandableListAdapter mAdapter;

    public ExpandableListAdapterWrapper(ExpandableListAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getGroupCount() {
        return mAdapter.getGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mAdapter.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mAdapter.getGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mAdapter.getChild(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mAdapter.getGroupId(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mAdapter.getChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return mAdapter.getGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return mAdapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return mAdapter.isChildSelectable(groupPosition, childPosition);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEmpty() {
        return mAdapter.isEmpty();
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        mAdapter.onGroupExpanded(groupPosition);
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        mAdapter.onGroupCollapsed(groupPosition);
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return mAdapter.getCombinedChildId(groupId, childId);
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return mAdapter.getCombinedGroupId(groupId);
    }
}
