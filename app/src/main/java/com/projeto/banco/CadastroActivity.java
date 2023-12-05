package com.projeto.banco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.InputFilter;
import android.text.Spanned;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {
    private EditText editText_email, editText_senha, editText_cpf, editText_nome,editText_data_nascimento;
    private Button bt_registrar;
    String usuarioID;
    String [] mensagens = {"Preencha todos os campos.", "Cadastro realizado com sucesso!"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getWindow().setStatusBarColor(Color.BLACK);
        iniciarComponents();

        bt_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editText_cpf.getText().toString();
                String email = editText_email.getText().toString();
                String senha = editText_senha.getText().toString();
                String nome = editText_nome.getText().toString();
                String data = editText_data_nascimento.getText().toString();

                if(nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty() || data.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v, mensagens[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.RED);
                    snackbar.show();
                }else{
                    cadastrarUsuario(v);
                }
            }
        });
    }

    private void cadastrarUsuario(View v){
        String email = editText_email.getText().toString();
        String senha = editText_senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    salvar_dadosUsuario();

                    Snackbar snackbar = Snackbar.make(v, mensagens[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha com pelo menos 6 caracteres.";
                    }catch(FirebaseAuthUserCollisionException e) {
                        erro = "Esta conta já está cadastrada.";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail inválido.";
                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário.";
                    }
                    Snackbar snackbar = Snackbar.make(v, erro,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    private void salvar_dadosUsuario(){
        String nome = editText_nome.getText().toString();
        String cpf = editText_cpf.getText().toString();
        String data_nascimento = editText_data_nascimento.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);
        usuarios.put("cpf", cpf);
        usuarios.put("data_nacimento", data_nascimento);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","Sucesso ao salvar os dados");
            }
        })
           .addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   Log.d("db_error", "Erro ao salvar os dados" + e.toString());

               }
           });

    }
    private void iniciarComponents(){
        editText_email = findViewById(R.id.editText_email);
        editText_senha = findViewById(R.id.editText_senha);
        editText_cpf = findViewById(R.id.editText_cpf);
        editText_nome = findViewById(R.id.editText_nome);
        editText_data_nascimento = findViewById(R.id.editText_data_nascimento);
        bt_registrar = findViewById(R.id.bt_registrar);

        editText_cpf.setFilters(new InputFilter[] {
            new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend){
                    for(int i = start; i < end; i++){
                        char character = source.charAt(i);
                        if(!Character.isDigit(character) && character != '.' && character != '-'){
                            return "";
                        }
                    }
                    return null;
                }
            }
        });
    }

}