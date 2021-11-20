package com.example.breatheeasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText username;
    EditText password;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editUsername);
        mAuth = FirebaseAuth.getInstance();
    }

    public void onClick(View view) {
        String mail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String userName = username.getText().toString().trim();
        String currentName = name.getText().toString();
        if(mail.isEmpty()) {
            email.setError("Email address is required!");
            email.requestFocus();
            return;
        }
        if(userName.isEmpty()) {
            username.setError("Username is required!");
            username.requestFocus();
            return;
        }
        if(currentName.isEmpty()) {
            name.setError("Name is required!");
            name.requestFocus();
            return;
        }
//        if(currentName.length()<8) {
//            username.setError("More than 8 characters username is required");
//            username.requestFocus();
//            return;
//        }
        if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Provide a valid email address");
            email.requestFocus();
            return;

        }
        if(pass.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(pass.length()<8) {
            password.setError("More than 8 characters password is required");
            password.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(mail,pass);

                    FirebaseDatabase.getInstance().getReference("Users").child(
                            FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(
                            user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignActivity.this,"The user has been registered",Toast.LENGTH_LONG);
                                Intent intent = new Intent(SignActivity.this,CalendarActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(SignActivity.this,"Failed to register!Try again!",Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignActivity.this,"Failed to register!Try again!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}