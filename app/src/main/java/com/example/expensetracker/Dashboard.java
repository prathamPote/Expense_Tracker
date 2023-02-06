package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import io.grpc.stub.StreamObserver;

public class Dashboard extends AppCompatActivity {
    Button addexp, editbudget,ok;
    EditText budgetedit;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String UID = mAuth.getCurrentUser().getUid().toString();
    private ExpenseAdapter expenseAdapter;
    private RecyclerView recyclerView;
    private TextView logout, textviewprog;
    private TextView totalexpense,budget,rembudget;
    private double prog=0;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addexp = findViewById(R.id.addExpense);
        editbudget = findViewById(R.id.editBudget);
        recyclerView = findViewById(R.id.recyclerview);
        expenseAdapter = new ExpenseAdapter(this);
        logout = findViewById(R.id.logout);
        recyclerView.setAdapter(expenseAdapter);
        totalexpense = findViewById(R.id.totalexpenses);
        budget = findViewById(R.id.budgetview);
        rembudget = findViewById(R.id.rembudget);
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         progressBar = findViewById(R.id.progress_bar);
         textviewprog = findViewById(R.id.text_view_progress);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setMessage("Do you want to Logout").setTitle("LOGOUT!").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
//                        finish();
                        finishAndRemoveTask();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();
            }
        });

        editbudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(Dashboard.this, com.airbnb.lottie.R.style.Base_Theme_AppCompat_Dialog);
                dialog.setContentView(R.layout.budgetpopup);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                 ok = dialog.findViewById(R.id.submitpopup);
                 budgetedit = dialog.findViewById(R.id.Budgetedittext);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Long budget = Long.parseLong(budgetedit.getText().toString());
                        FirebaseFirestore.getInstance().collection("Users").document(UID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                User user;
                                user = documentSnapshot.toObject(User.class);
                                user.setBudget(String.valueOf(budget));
                                FirebaseFirestore.getInstance().collection("Users").document(UID).set(user);
                            }
                        });
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        addexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,AddExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        FirebaseFirestore.getInstance().collection("Users").document(UID).collection("Expenses").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                expenseAdapter.clear();
                long totalExpense =0;
                List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot ds:
                     docs) {
                    UserExpenseModel expenses = ds.toObject(UserExpenseModel.class);
                    totalExpense+=expenses.getAmount();
                    expenseAdapter.add(expenses);
                }
                totalexpense.setText("Total Expense: Rs. "+String.valueOf(totalExpense));
                long finalTotalExpense = totalExpense;
                FirebaseFirestore.getInstance().collection("Users").document(UID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user1 = new User();
                        user1 = documentSnapshot.toObject(User.class);
                        budget.setText("Budget: Rs. "+ user1.getBudget());
                        rembudget.setText("Remaining Budget: Rs. "+(Long.parseLong(user1.getBudget())- finalTotalExpense));
                        double b = Long.parseLong(user1.getBudget());
                        double e = finalTotalExpense;
                        prog= (double) (e/b)*100;
                        Toast.makeText(Dashboard.this, String.valueOf(prog), Toast.LENGTH_SHORT).show();
                        progressBar.setProgress((int) prog);
                        textviewprog.setText(String.valueOf((int) prog)+"%");
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
 }
}