package com.pavel.a692group;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pavel.a692group.room.entity.User;

import java.util.List;

/**
 * Created by Pavel Mazhnik on 03.03.19.
 * Description:
 * адаптер для отображния списка пользователей в GridView в EditDbActivity
 */

public class UserAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> mUsers;

    public UserAdapter(Context context, List<User> uers) {
        mContext = context;
        mUsers = uers;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mUsers.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout fragm;

        if (convertView == null) {
            convertView = new LinearLayout(mContext);
            fragm = (LinearLayout) convertView;

            fragm.setOrientation(LinearLayout.VERTICAL);

            TextView Text1 = new TextView(mContext);
            TextView Text2 = new TextView(mContext);
            Text1.setText(mUsers.get(position).getName());
            Text2.setText(mUsers.get(position).getGroup());
            fragm.addView(Text1);
            fragm.addView(Text2);
        } else {
            fragm = (LinearLayout) convertView;
        }
        //TODO: здесь не сделана замена текста при непустом converView, возможно, это и не нужно.
        return (convertView);
    }
}
