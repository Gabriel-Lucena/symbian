package com.example.symbian;

import androidx.appcompat.app.AlertDialog;
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

        /*
         * Listener
         */

        btnLogarUsuario.setOnClickListener(view -> {

            if (validate()) {

                Toast.makeText(this,
                        getString(R.string.preencha_todos_os_campos),
                        Toast.LENGTH_LONG).show();

            } else {


                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.LoginUsuario_titulo))
                        .setMessage(getString(R.string.LoginUsuario_mensagem))
                        .setPositiveButton(R.string.LoginUsuario_logar, (dialog1, which) -> {

                            /*
                             * Ação do botão positivo
                             */

                            String loginUsuario = txtLoginUsuario.getText().toString();
                            String senhaUsuario = txtSenhaUsuario.getText().toString();

                            int idUsuario = SQLHelper.getINSTANCE(this).logarUser(loginUsuario, senhaUsuario);

                            if (idUsuario > 0) {

                                Intent activityCadastroEndereco = new Intent(this, CadastroEndereco.class);
                                activityCadastroEndereco.putExtra("idUsario", idUsuario);
                                startActivity(activityCadastroEndereco);

                            } else {

                                Toast.makeText(this,
                                        "Não deu",
                                        Toast.LENGTH_LONG).show();

                                Intent activityCadastroUsuario = new Intent(this, CadastroUsuario.class);
                                startActivity(activityCadastroUsuario);

                            }

                        }).setNegativeButton(getString(R.string.CadastroUsuario_cancelarInsercao), (dialog1, which) -> {

                            /*
                             * Ação do negative button
                             */

                        }).create();

                dialog.show();

            }

        });

    }

    private boolean validate() {

        return txtLoginUsuario.getText().toString().isEmpty() ||
                txtSenhaUsuario.getText().toString().isEmpty();

    }
}