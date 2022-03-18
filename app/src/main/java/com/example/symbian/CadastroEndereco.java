package com.example.symbian;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import database.SQLHelper;

public class CadastroEndereco extends AppCompatActivity {

    private EditText txtCep;
    private EditText txtNumero;
    private EditText txtComplemento;
    private Button btnCadastrarEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        txtCep = findViewById(R.id.CadastroEndereco_txtCep);
        txtNumero = findViewById(R.id.CadastroEndereco_txtNumero);
        txtComplemento = findViewById(R.id.CadastroEndereco_txtComplemento);

        /*
         * Listener
         */

        btnCadastrarEndereco.setOnClickListener(view -> {
            if (validate()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Deu bom", Toast.LENGTH_SHORT).show();

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.CadastroEndereco_titulo))
                        .setMessage(getString(R.string.CadastroEndereco_insercao))
                        .setPositiveButton("Salvar", (dialog1, which) -> {

                            /*
                             * Ação do botão positivo
                             */

                            String cepEndereco = txtCep.getText().toString();
                            String numeroEndereco = txtNumero.getText().toString(); // Precisa ser um inteiro
                            String complementoEndereco = txtComplemento.getText().toString();
                                                                                                    // Trazer o id da activity de usuário
                            boolean cadastrarEndereco = SQLHelper.getINSTANCE(this).addAddress(1, cepEndereco, numeroEndereco, complementoEndereco);

                        }).setNegativeButton(getString(R.string.CadastroUsuario_cancelarInsercao), (dialog1, which) -> {


                            /*
                             * Ação do negative button
                             */

                        }).create();

            }

        });
    }

    private boolean validate() {

        return txtCep.getText().toString().isEmpty() ||
                txtNumero.getText().toString().isEmpty() ||
                txtComplemento.getText().toString().isEmpty();


    }
}