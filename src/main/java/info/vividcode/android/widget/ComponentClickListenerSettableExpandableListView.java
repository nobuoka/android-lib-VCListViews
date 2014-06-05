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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.Map;

public class ComponentClickListenerSettableExpandableListView extends ExpandableListView {

    private static final String TAG =
            ComponentClickListenerSettableExpandableListView.class.getSimpleName();

    public ComponentClickListenerSettableExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 指定の View が含まれているのが何番目のグループかを返す。
     * @param view 調査対象の view。
     * @return {@code view} が何番目のグループに含まれているか。 {@code view} が不可視の場合やリストに含まれていない場合は -1。
     */
    private int getGroupPositionForView(View view) {
        final int flatPos = getPositionForView(view);
        if (flatPos == AdapterView.INVALID_POSITION) {
            return -1;
        }
        final long packedPos = getExpandableListPosition(flatPos);
        return getPackedPositionGroup(packedPos);
    }

    private Map<Integer, OnClickListener> mGroupViewComponentClickListeners =
            new HashMap<Integer, OnClickListener>();

    public void setOnGroupViewComponentClickListener(int targetViewId, final OnGroupViewComponentClickListener listener) {
        if (listener != null) {
            mGroupViewComponentClickListeners.put(targetViewId, new OnClickListener() {
                @Override
                public void onClick(View clickedView) {
                    ComponentClickListenerSettableExpandableListView listView = ComponentClickListenerSettableExpandableListView.this;
                    int flatPos = getPositionForView(clickedView);
                    if (flatPos == AdapterView.INVALID_POSITION) {
                        return;
                    }
                    int groupPos = getGroupPositionForView(clickedView);
                    long groupId = getItemIdAtPosition(flatPos);
                    listener.onClick(listView, clickedView, groupPos, groupId);
                }
            });
        } else {
            mGroupViewComponentClickListeners.remove(targetViewId);
        }
    }

    public static interface OnGroupViewComponentClickListener {
        void onClick(ComponentClickListenerSettableExpandableListView listView, View clickedView, int groupPosition, long id);
    }

    private class MyExpandableListAdapterWrapper extends ExpandableListAdapterWrapper {
        public MyExpandableListAdapterWrapper(ExpandableListAdapter adapter) {
            super(adapter);
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View groupView = super.getGroupView(groupPosition, isExpanded, convertView, parent);
            if (groupView == null) return null;

            for (int targetId : mGroupViewComponentClickListeners.keySet()) {
                View clickableView = groupView.findViewById(targetId);
                if (clickableView == null) continue;
                clickableView.setOnClickListener(mGroupViewComponentClickListeners.get(targetId));
            }
            return groupView;
        }
    }

    private ExpandableListAdapter mRawAdapter = null;

    @Override
    public void setAdapter(ExpandableListAdapter adapter) {
        mRawAdapter = adapter;
        ExpandableListAdapter wrappedAdapter;
        if (adapter != null) {
            wrappedAdapter = new MyExpandableListAdapterWrapper(adapter);
        } else {
            wrappedAdapter = null;
        }
        super.setAdapter(wrappedAdapter);
    }

    @Override
    public ExpandableListAdapter getExpandableListAdapter() {
        return mRawAdapter;
    }

    public void collapseAllGroups() {
        ExpandableListAdapter adapter = getExpandableListAdapter();
        if (adapter == null) return;
        int count = adapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            if (isGroupExpanded(i)) collapseGroup(i);
        }
    }

}
