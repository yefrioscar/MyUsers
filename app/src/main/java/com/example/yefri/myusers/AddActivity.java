package com.example.yefri.myusers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText edId, edNombre, edApellido, edEdad;
    Button btnAgregarAdd, btnCancelarMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edId   = (EditText) findViewById(R.id.etIdAdd);
        edNombre   = (EditText) findViewById(R.id.etNombreAdd);
        edApellido = (EditText) findViewById(R.id.etApellidoAdd);
        edEdad      = (EditText) findViewById(R.id.etEdadAdd);
        btnAgregarAdd  = (Button)   findViewById(R.id.btnAgregarAdd);
        btnCancelarMain = (Button) findViewById(R.id.btnCancelarMain);
        edId.setVisibility(View.GONE);
        edNombre.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(edNombre, InputMethodManager.SHOW_IMPLICIT);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        if(bundle!=null) {
            edId.setVisibility(View.VISIBLE);
            edId.setText(String.valueOf(bundle.get("id")));
            edNombre.setText(String.valueOf(bundle.get("nombre")));
            edApellido.setText(String.valueOf(bundle.get("apellido")));
            edEdad.setText(String.valueOf(bundle.get("edad")));


            btnAgregarAdd.setText("Actualizar");
        }



        btnCancelarMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        btnAgregarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  User user = new User();

                    user.setNombre(edNombre.getText().toString());
                    user.setApellido(edApellido.getText().toString());
                    user.setEdad(Integer.parseInt(edEdad.getText().toString()));

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(AddActivity.this);
                    dataBaseHelper.openDataBase();

                    if(btnAgregarAdd.getText().toString().equals("Agregar")) {
                        Log.d("c", user.getNombre());
                        Log.d("c", user.getApellido());
                        Log.d("c", String.valueOf(user.getEdad()));

                        long userDAO = new UserDAO(AddActivity.this).addUser(user);
                        if(userDAO == 1) {
                            Toast.makeText(getApplicationContext(),"Se agrego al usuario",Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {
                        user.setId(Integer.parseInt(edId.getText().toString()));
                        new UserDAO(AddActivity.this).update(user);
                        Toast.makeText(getApplicationContext(),"Se actualizo al usuario",Toast.LENGTH_SHORT).show();

                    }

                    Intent i = new Intent();
                    setResult(RESULT_OK, i);
                    finish();
                }


        });
    }
}
