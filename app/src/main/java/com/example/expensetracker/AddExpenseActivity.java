package com.example.expensetracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.UserDataReader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    EditText amount,description;
    Button SubmitExpense;
    FirebaseAuth mauth = FirebaseAuth.getInstance();
    final Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.note);
        SubmitExpense = findViewById(R.id.submitExpenseBtn);
        String UID = mauth.getCurrentUser().getUid().toString();
//        CollectionReference databaseReference = FirebaseFirestore.getInstance().collection("Users").document(UID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                User user = value.get();
//            }
//        });
//        if(!UID.isEmpty())
//        {
//           databaseReference.document(UID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//               @Override
//               public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                   String username = value.get();
//               }
//           });
//        }

        SubmitExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount.getText().toString().isEmpty())
                    amount.setError("Please enter the amount");
                else {
                    long Amount = Long.parseLong(amount.getText().toString());
                    String note = description.getText().toString();

                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    UserExpenseModel expense = new UserExpenseModel(UUID.randomUUID().toString(), note, Amount, date);
                    FirebaseFirestore.getInstance().collection("Users").document(UID).collection("Expenses").document(expense.getExpenseId()).set(expense).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddExpenseActivity.this, "Expense Created" +
                                    " "+expense.getDate()+ " "+
                                    expense.getAmount(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                }

            }
        });

//        datepicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(AddExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                    DateDisplay.setText(i2+"-"+(i1+1)+"-"+i);
//                    }
//                },year,month,date);
//                System.out.println(datePickerDialog.get);
//                datePickerDialog.show();
//            }
//        });
    }

}
