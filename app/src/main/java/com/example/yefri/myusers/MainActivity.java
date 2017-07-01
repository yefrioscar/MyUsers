package com.example.yefri.myusers;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE_AGREGAR = 1;
    private final int REQUEST_CODE_ACTUALIZAR = 2;

    private TextView idtv, nombreCompleto, edad;


    private Button btnMain, btnActualizarListaMain;
    private ListView listviewmain;
    private LVAdapter Adapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_AGREGAR){
            if(resultCode == RESULT_OK) {
                listUsers();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMain = (Button) findViewById(R.id.btnAgregarMain);
        btnActualizarListaMain = (Button) findViewById(R.id.btnActualizarListaMain);
        listviewmain= (ListView) findViewById(R.id.listViewMain);

        btnActualizarListaMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listUsers();
            }
        });



        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(i,REQUEST_CODE_AGREGAR);
            }
        });

        listviewmain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(),"Se clickeo un item",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(MainActivity.this,AddActivity.class);

                idtv = (TextView) view.findViewById(R.id.tvIdItem);
                nombreCompleto = (TextView) view.findViewById(R.id.tvFullNameItem);
                edad = (TextView) view.findViewById(R.id.tvAgeItem);

                String[] nombreC = nombreCompleto.getText().toString().split(" ");

                i.putExtra("id", idtv.getText().toString());
                i.putExtra("nombre",nombreC[0]);
                i.putExtra("apellido",nombreC[1]);
                i.putExtra("edad", edad.getText().toString());

                startActivityForResult(i,REQUEST_CODE_AGREGAR);
            }
        });


        listUsers();


    }

    public void listUsers(){
        try {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

            dataBaseHelper.createDataBase();
            ArrayList<User> listUser = new UserDAO(MainActivity.this).listUsers();
            Adapter = new LVAdapter( this, listUser);
            listviewmain.setAdapter(Adapter);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
