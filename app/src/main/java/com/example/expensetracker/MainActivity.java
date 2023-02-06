package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView reg;
    Button login;
    EditText email,password;
    private FirebaseAuth mAuth;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
            updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        Intent next = new Intent(MainActivity.this,Dashboard.class);
        startActivity(next);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reg = findViewById(R.id.register);
        login = findViewById(R.id.Login);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.EmailID);
        password = findViewById(R.id.Password);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAct = new Intent(MainActivity.this,Register.class);
                startActivity(newAct);
            }
        });
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String emaiid = email.getText().toString();
            String pass = password.getText().toString();
            if(emaiid.isEmpty())
                email.setError("Enter EmailID");
            else if (pass.isEmpty()) {
                password.setError("Enter password");
            } else{mAuth.signInWithEmailAndPassword(emaiid, pass)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Invalid Email or password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });}

        }
    });
    }
}