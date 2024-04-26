package com.example.unitconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.unitconverter.RoomDB.AppDatabase;
import com.example.unitconverter.RoomDB.Results;
import com.example.unitconverter.SQLite.DatabaseManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //Firebase and Firestore
        ArrayList<Result> PreviousResult = new ArrayList<>();
        recyclerView = findViewById(R.id.recentResult);

        //Room DB approach
//        new Thread(() ->{
//            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
//            List<Results> results = db.resultsDAO().getAll();
//            for(Results r : results){
//                PreviousResult.add(new Result(r.result));
//                Log.d("Result", ""+r);
//            }
//            Adapter adapter = new Adapter(ResultActivity.this, PreviousResult);
//            recyclerView.setAdapter(adapter);
//        }).start();

        //SQLite DB approach
        DatabaseManager databaseManager = new DatabaseManager(this);
        databaseManager.open();
        List<com.example.unitconverter.SQLite.Result> list = databaseManager.getAllData();
        for (com.example.unitconverter.SQLite.Result r : list) {
            PreviousResult.add(new Result(r.result));
        }
        databaseManager.close();
        Adapter adapter = new Adapter(ResultActivity.this, PreviousResult);
        recyclerView.setAdapter(adapter);



        //Realtime Database approach
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("Results");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                PreviousResult.clear();
//                for (DataSnapshot ds : snapshot.getChildren()) {
//                    Result r = ds.getValue(Result.class);
//                    PreviousResult.add(new Result(r.getResult()));
//                    Log.d("Result", String.valueOf(r.getResult()));
//                }
//                Adapter adapter = new Adapter(ResultActivity.this, PreviousResult);
//                recyclerView.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //Firestore approach
//        FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
//        dbFirestore.collection("Results").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                PreviousResult.clear();
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document : task.getResult()){
//                        Result r = document.toObject(Result.class);
//                        PreviousResult.add(new Result(r.getResult()));
//                        Log.d("Result", String.valueOf(r.getResult()));
//                    }
//                    Adapter adapter = new Adapter(ResultActivity.this, PreviousResult);
//                    recyclerView.setAdapter(adapter);
//                }else{
//                    Log.d("Result", "Error getting documents: ", task.getException());
//                }
//            }
//        });






        TextView textViewResult = findViewById(R.id.textViewResult);
        Button returnButton = findViewById(R.id.buttonBack);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            double result = extras.getDouble("Result");
            textViewResult.setText(String.format("Result: %.2f ", result));

        }
        returnButton.setOnClickListener(v -> {
            finish();
        });
    }
}