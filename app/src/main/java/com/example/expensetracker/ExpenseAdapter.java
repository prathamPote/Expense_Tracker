package com.example.expensetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyviewHolder> {
    private Context context;
    private List<UserExpenseModel> expenseModelList;

    public ExpenseAdapter(Context context) {
        this.context = context;
        expenseModelList = new ArrayList<>();
    }
        public  void add(UserExpenseModel expenseModel)
        {
            expenseModelList.add(expenseModel);
            notifyDataSetChanged();
        }
        public void clear(){
        expenseModelList.clear();
        notifyDataSetChanged();
        }
    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        UserExpenseModel expenseModel = expenseModelList.get(position);
        holder.note.setText(expenseModel.getNote());
        holder.amount.setText(String.valueOf(expenseModel.getAmount()));
        holder.date.setText(expenseModel.getDate());

    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        private TextView note,amount,date;
    public MyviewHolder(@NonNull View itemView) {
        super(itemView);
        note = itemView.findViewById(R.id.noteview);
        amount = itemView.findViewById(R.id.amountview);
        date = itemView.findViewById(R.id.dateview);
    }

}
}
