package com.pavel.a692group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pavel.a692group.room.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel Mazhnik on 03.03.19.
 * Description:
 * адаптер для отображния списка пользователей в GridView в EditDbActivity
 */

public class UserAdapter extends BaseAdapter {
    private Context mContext;
    private List<User> mUsers;

    public UserAdapter(Context context) {
        mContext = context;
        mUsers = new ArrayList<>();
    }

    public UserAdapter(Context context, List<User> users) {
        mContext = context;
        mUsers = users;
    }

    public void setData(List<User> user){
        mUsers = user;
    }

    public List<User> getData(){
        return mUsers;
    }

    public void updateUserFromPos(int position, User user){
        mUsers.get(position).updateUser(user);
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
        if (convertView == null) {
            convertView = new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate(R.layout.user_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.Text_name =
                    (TextView)convertView.findViewById(R.id.item_user_name);
            viewHolder.Text_group =
                    (TextView)convertView.findViewById(R.id.item_user_group);
            // Store results of findViewById
            convertView.setTag(viewHolder);
        }
        TextView Text_name = ((ViewHolder)convertView.getTag()).Text_name;
        TextView Text_group = ((ViewHolder)convertView.getTag()).Text_group;
        Text_name.setText(mUsers.get(position).getName());
        Text_group.setText(mUsers.get(position).getGroup());
        return (convertView);
    }

    static class ViewHolder{
        TextView Text_name;
        TextView Text_group;
    }
}
