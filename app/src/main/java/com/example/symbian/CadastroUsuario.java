package com.example.symbian;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import database.SQLHelper;

public class CadastroUsuario extends AppCompatActivity {

    private EditText txtNomeUsuario;
    private EditText txtSobrenomeUsuario;
    private EditText txtLoginUsuario;
    private EditText txtSenhaUsuario;
    private Button btnCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        txtNomeUsuario = findViewById(R.id.CadastroUsuario_txtNome);
        txtSobrenomeUsuario = findViewById(R.id.CadastroUsuario_txtSobrenome);
        txtLoginUsuario = findViewById(R.id.CadastroUsuario_txtLogin);
        txtSenhaUsuario = findViewById(R.id.CadastroUsuario_txtSenha);
        btnCadastrarUsuario = findViewById(R.id.CadastroUsuario_btnCadastrar);

        /*
         * Listener
         */

        btnCadastrarUsuario.setOnClickListener(view -> {


            if (validate()) {
                Toast.makeText(this, getString(R.string.preencha_todos_os_campos), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Deu bom", Toast.LENGTH_SHORT).show();


                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.CadastroUsuario_titulo))
                        .setMessage(getString(R.string.CadastroUsuario_mensagem))
                        .setPositiveButton(R.string.salvar, (dialog1, which) -> {

                            /*
                             * Ação do botão positivo
                             */

                            String nomeUsuario = txtNomeUsuario.getText().toString();
                            String sobrenomeUsuario = txtSobrenomeUsuario.getText().toString();
                            String loginUsuario = txtLoginUsuario.getText().toString();
                            String senhaUsuario = txtSenhaUsuario.getText().toString();

                            int idUsuario = SQLHelper.getINSTANCE(this).addUser(nomeUsuario, sobrenomeUsuario, loginUsuario, senhaUsuario);


                            if (idUsuario != 0) {

                                Toast.makeText(this,
                                        getString(R.string.CadastroUsuario_insercao),
                                        Toast.LENGTH_LONG).show();

                                Intent activityCadastroEndereco = new Intent(this, CadastroEndereco.class);
                                activityCadastroEndereco.putExtra("idUsuario", idUsuario);
                                startActivity(activityCadastroEndereco);

                            } else {

                                Toast.makeText(this,
                                        getString(R.string.CadastroUsuario_insercaoErro),
                                        Toast.LENGTH_LONG).show();
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

        return txtNomeUsuario.getText().toString().isEmpty() ||
                txtSobrenomeUsuario.getText().toString().isEmpty() ||
                txtLoginUsuario.getText().toString().isEmpty() ||
                txtSenhaUsuario.getText().toString().isEmpty();


    }

}