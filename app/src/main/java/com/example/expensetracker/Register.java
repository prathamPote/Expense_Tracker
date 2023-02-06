package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    ConstraintLayout parent;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    EditText Name,EmailID,Password,Budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button Reg = findViewById(R.id.RegBtn);
        Name = findViewById(R.id.editTextTextPersonName2);
        EmailID = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        Budget = findViewById(R.id.editTextBudget);
        mAuth = FirebaseAuth.getInstance();
        Reg.setOnClickListener(view -> {
            String name = Name.getText().toString();
            String email = EmailID.getText().toString();
            String password = Password.getText().toString();
            String budget = Budget.getText().toString();
            System.out.println("Before Auth");
                    if(name.isEmpty())
                        Name.setError("Enter Name");
                    else if (email.isEmpty()) {
                        EmailID.setError("Enter EmailId");
                    }
                    else if (password.isEmpty()) {
                        Password.setError("Enter password");
                    }
                    else if (budget.isEmpty()) {
                        Budget.setError("Enter Budget");
                    }
                    else{
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            String UID = mAuth.getCurrentUser().getUid();
                                            System.out.println(budget);
                                            User usr = new User(name,UID,budget);
                                            fstore = FirebaseFirestore.getInstance();
                                            DocumentReference docref = fstore.collection("Users").document(UID).collection("UserData").getParent();
                                            docref.set(usr).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Register.this, "Registration Successfull.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            System.out.println("After Auth");
                                            Intent next = new Intent(Register.this,MainActivity.class);
                                            startActivity(next);
                                            finish();
                                            Toast.makeText(Register.this, "Auth Successfull.", Toast.LENGTH_SHORT).show();

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                    }


        });

    }

//    private String ShowPopUp() {
//        View vpopup =  View.inflate(this,R.layout.budgetpopup,null);
//        parent = findViewById(R.id.parentlayout);
//        int Width = LinearLayout.LayoutParams.WRAP_CONTENT;
//        int Height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        PopupWindow budgetWindow = new PopupWindow(vpopup,Width,Height,false);
//        budgetWindow.showAtLocation(parent,Gravity.CENTER,0,0);
//        EditText budget = findViewById(R.id.Budget);
//        Button submit = findViewById(R.id.submitpopup);
//    }
}