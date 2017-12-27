package com.example.nneeranjun.qrcode;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by nneeranjun on 10/7/17.
 */
public class CustomUserAdapter extends BaseAdapter {
    List<User> users;
    Context context;

    public CustomUserAdapter(Context context, List<User> users) {
        this.users=users;
        this.context=context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View customView= inflater.inflate(R.layout.custom_user_row,null);
        ImageView imageView = (ImageView) customView.findViewById(R.id.profile);
        TextView name = (TextView) customView.findViewById(R.id.name);
        User user= (User) getItem(i);
        imageView.setImageBitmap(user.getPicture());
        name.setText(user.getFirstName()+" "+user.getLastName());

        return customView;




    }
}
