package com.google.android.gms.samples.vision.ocrreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by K Shah on 4/15/2017.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    List<String> wordList;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, List<String> wordList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.wordList = wordList;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Object getItem(int position) {
        return wordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.listrow, null);
        TextView text = (TextView) vi.findViewById(R.id.item);
        text.setText(wordList.get(position));
        return vi;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        inflater.inflate(R.layout.listrow, null);
    }
}
