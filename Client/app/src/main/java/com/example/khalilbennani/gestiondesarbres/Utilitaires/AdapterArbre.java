package com.example.khalilbennani.gestiondesarbres.Utilitaires;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.khalilbennani.gestiondesarbres.Arbre;
import com.example.khalilbennani.gestiondesarbres.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterArbre extends ArrayAdapter<Arbre> {
    private Activity activity;
    private List<Arbre> lArbre;
    private static LayoutInflater inflater = null;

    public AdapterArbre (Activity activity, int textViewResourceId,List<Arbre> _lArbre) {
        super(activity, textViewResourceId, _lArbre);
        try {
            this.activity = activity;
            this.lArbre = _lArbre;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lArbre.size();
    }

    public Arbre getItem(int position) {
        return lArbre.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView display_title;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.activity_list_view_arbre, null);
                holder = new ViewHolder();

                //holder.display_title = (TextView) vi.findViewById(R.id.);
                holder.display_name = (TextView) vi.findViewById(R.id.textEspece);


                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }


            holder.display_name.setText(lArbre.get(position).getType());


        } catch (Exception e) {


        }
        return vi;
    }
}