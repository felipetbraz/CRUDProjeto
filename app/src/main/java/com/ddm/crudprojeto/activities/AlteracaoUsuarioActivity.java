package com.ddm.crudprojeto.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.ddm.crudprojeto.R;
import com.ddm.crudprojeto.dto.DtoUser;
import com.ddm.crudprojeto.services.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlteracaoUsuarioActivity extends AppCompatActivity {
    private static final String TAG = "AlteracaoUsuarioActivit";
    EditText et_nome, et_email, et_telefone;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_usuario);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        String nome = intent.getStringExtra("nome");
        String email = intent.getStringExtra("email");
        String tel = intent.getStringExtra("tel");

        et_email = findViewById(R.id.et_cadastro_usuario_email);
        et_nome = findViewById(R.id.et_cadastro_usuario_nome);
        et_telefone = findViewById(R.id.et_cadastro_usuario_telefone);

        et_telefone.setText(tel);
        et_nome.setText(nome);
        et_email.setText(email);
    }

    public void Cadastrar(View view){
        String nome = ((EditText) findViewById(R.id.et_cadastro_usuario_nome)).getText().toString();
        String telefone = ((EditText) findViewById(R.id.et_cadastro_usuario_telefone)).getText().toString();
        String email = ((EditText) findViewById(R.id.et_cadastro_usuario_email)).getText().toString();
        String senha = ((EditText) findViewById(R.id.et_cadastro_usuario_senha)).getText().toString();
        DtoUser dtoUser;
        if(senha.isEmpty())
            dtoUser = new DtoUser(email, nome, telefone);
        else
            dtoUser = new DtoUser(email, nome, senha, telefone);
        String token = getToken();
        RetrofitService.getServico(this).alteraUsuario(dtoUser, id, "Bearer" + token).enqueue(new Callback<DtoUser>() {
            @Override
            public void onResponse(Call<DtoUser> call, Response<DtoUser> response) {
                if(response.code()==200)
                    Toast.makeText(AlteracaoUsuarioActivity.this, "Usuário alterado com sucesso", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(AlteracaoUsuarioActivity.this, "Erro: " +response.code(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DtoUser> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private String getToken(){
        SharedPreferences sp = getSharedPreferences("dados", 0);
        return sp.getString("token", null);
    }
}
