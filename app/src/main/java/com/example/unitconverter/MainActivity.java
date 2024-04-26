package com.example.unitconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.unitconverter.RoomDB.AppDatabase;
import com.example.unitconverter.RoomDB.Results;
import com.example.unitconverter.SQLite.DatabaseManager;
import com.example.unitconverter.SQLite.Result;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private String selectedFrom, selectedTo;
    private double result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText InputEditText = findViewById(R.id.inputEt);
        Button convertButton = findViewById(R.id.buttonConvert);
        //Room DB approach
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        //SQLite DB approach
        DatabaseManager databaseManager = new DatabaseManager(this);
        //Realtime Database approach
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Firestore approach
        FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();



        spinnerFrom = findViewById(R.id.spinnerFrom);
        String[] items1 = getResources().getStringArray(R.array.convert_unit);
        ArrayAdapter<String> adapterFrom = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items1);
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapterFrom);
        spinnerFrom.setOnItemSelectedListener(new ConvertFromSpinner());

        spinnerTo = findViewById(R.id.spinnerTo);
        String[] items2 = getResources().getStringArray(R.array.convert_unit);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, items2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTo.setAdapter(adapter);
        spinnerTo.setOnItemSelectedListener(new ConvertToSpinner());


        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputEditText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
                    return;
                }
                double input = Double.parseDouble(InputEditText.getText().toString());
                Log.d("TAG", "onClick: " + selectedFrom + " " + selectedTo);
                if (selectedFrom.equals("Celsius") && selectedTo.equals("Fahrenheit")) {
                    result = (input * 9 / 5) + 32;
                } else if (selectedFrom.equals("Fahrenheit") && selectedTo.equals("Celsius")) {
                    result = (input - 32) * 5 / 9;
                } else {
                    result = input;
                }

                //Luu gia tri result vao db theo kieu sqlite
                databaseManager.open();
                databaseManager.addData(result);
                List<Result> list = databaseManager.getAllData();
                for (Result r : list) {
                    Log.d("TAG", "ID: " + r.id + " Result: " + r.result);
                }
                databaseManager.close();


                //Luu gia tri result vao db theo kieu room
//                new Thread(() ->{
//                    Results results = new Results();
//                    results.result = result;
//                    db.resultsDAO().insert(results);
//                }).start();

                //Luu gia tri result vao db theo kieu realtime database

                HashMap<String, Object> map = new HashMap<>();
                long timestamp = System.currentTimeMillis();
                //Tao hashmap chua cac thuoc tinh cua bang
                map.put("id", ""+timestamp);
                map.put("timestamp", timestamp);
                map.put("result", result);

//                DatabaseReference reference = database.getReference("Results"); // Bang Results
//                //Dat ten cho collection
//                reference.child(""+timestamp).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(MainActivity.this, "Saved to Realtime Database", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(MainActivity.this, "Failed to save to Realtime Database", Toast.LENGTH_SHORT).show();
//                    }
//                });

                //Luu du lieu kieu firestore
//                dbFirestore.collection("Results")
//                        .document(""+timestamp).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(MainActivity.this, "Saved to Firestore", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(MainActivity.this, "Failed to save to Firestore", Toast.LENGTH_SHORT).show();
//                            }
//                        });






                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("Result", result);
                startActivity(intent);
            }
        });

    }

    class ConvertFromSpinner implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.spinnerFrom) {
                selectedFrom = parent.getItemAtPosition(position).toString();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class ConvertToSpinner implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.spinnerTo) {
                selectedTo = parent.getItemAtPosition(position).toString();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}