package com.example.symbian;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;

public class LoginUsuario extends AppCompatActivity {

    private EditText txtLoginUsuario;
    private EditText txtSenhaUsuario;
    private Button btnLogarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        txtLoginUsuario = findViewById(R.id.LoginUsuario_txtLogin);
        txtSenhaUsuario = findViewById(R.id.LoginUsuario_txtSenha);
        btnLogarUsuario = findViewById(R.id.LoginUsuario_btnLogar);

        btnLogarUsuario.setOnClickListener(view -> {

            if (validate()) {

                Toast.makeText(this,
                        getString(R.string.preencha_todos_os_campos),
                        Toast.LENGTH_LONG).show();

            } else {

                Intent activityCadastroEndereco = new Intent(this, CadastroEndereco.class);
                startActivity(activityCadastroEndereco);


            }

        });


    }

    private boolean validate() {

        return txtLoginUsuario.getText().toString().isEmpty() ||
                txtSenhaUsuario.getText().toString().isEmpty();

    }
}