/*
 * Copyright 2014 the original author or authors.
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

package pl.allegro.fogger.ui.context.menu;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.MenuItem;

public class StubMenuItemData implements Parcelable {

    private int groupId;
    private int itemId;
    private int order;
    private String title;

    public static StubMenuItemData create(MenuItem menuItem) {
        return new StubMenuItemData(menuItem.getGroupId(),
                menuItem.getItemId(), menuItem.getOrder(), menuItem.getTitle().toString());
    }

    public StubMenuItemData(int groupId, int itemId, int order, String title) {
        this.groupId = groupId;
        this.itemId = itemId;
        this.order = order;
        this.title = title;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getOrder() {
        return order;
    }

    public String getTitle() {
        return title;
    }

    protected StubMenuItemData(Parcel in) {
        groupId = in.readInt();
        itemId = in.readInt();
        order = in.readInt();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(groupId);
        dest.writeInt(itemId);
        dest.writeInt(order);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StubMenuItemData> CREATOR = new Parcelable.Creator<StubMenuItemData>() {
        @Override
        public StubMenuItemData createFromParcel(Parcel in) {
            return new StubMenuItemData(in);
        }

        @Override
        public StubMenuItemData[] newArray(int size) {
            return new StubMenuItemData[size];
        }
    };
}
