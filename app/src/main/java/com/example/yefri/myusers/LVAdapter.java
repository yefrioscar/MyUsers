package com.example.yefri.myusers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by yefri on 29/06/2017.
 */

public class LVAdapter extends ArrayAdapter<User> {

    public LVAdapter(Context context, ArrayList<User> list) {
        super(context,0, list);
    }

    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }

    @NonNull
    @Override
    public View getView(int position, View  convertView, ViewGroup parent) {

        if(convertView == null) convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_main,parent,false);


        final TextView nombreCompleto, edad;
        Button eliminar;

        final TextView  id = (TextView) convertView.findViewById(R.id.tvIdItem);
        nombreCompleto = (TextView) convertView.findViewById(R.id.tvFullNameItem);
        edad = (TextView) convertView.findViewById(R.id.tvAgeItem);
        eliminar = (Button) convertView.findViewById(R.id.btnEliminarItem);
        User user = getItem(position);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                    dataBaseHelper.openDataBase();

                    User user = new User();

                    user.setId(Integer.parseInt(id.getText().toString()));
                    new UserDAO(getContext()).delete(user);

                    Toast.makeText(getContext(),"Se elimno al usuario con el id: "+id.getText().toString()+" con el nombre "+nombreCompleto.getText().toString().split(" ")[0],Toast.LENGTH_SHORT).show();


                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });


        id.setText(String.valueOf(user.getId()));
        nombreCompleto.setText(user.getNombre()+' '+user.getApellido());
        edad.setText(String.valueOf(user.getEdad()));



        return convertView;
    }
}
