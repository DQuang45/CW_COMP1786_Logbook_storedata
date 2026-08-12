package com.example.storedata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameTxt;
    private EditText dobTxt;
    private EditText emailTxt;
    private int avatar = R.drawable.pic11;
    private Spinner spAvatar;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        nameTxt = findViewById(R.id.editTextText);
        dobTxt = findViewById(R.id.editTextText2);
        emailTxt = findViewById(R.id.editTextTextEmailAddress);
        spAvatar = findViewById(R.id.spAvatar);
        // Initialize the button
        Button viewDetailsBtn = findViewById(R.id.btnViewDetails);

        Button savebtn = findViewById(R.id.button);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
            }
        });

        // Open DetailsActivity when the button is clicked
        viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        MainActivity.this,
                        DetailsActivity.class
                );

                startActivity(i);
            }
        });

        String[] avatars = {
                "Avatar 1",
                "Avatar 2",
                "Avatar 3"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        avatars
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spAvatar.setAdapter(adapter);
        spAvatar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view,
                                       int position,
                                       long id) {

                switch (position) {

                    case 0:
                        avatar = R.drawable.donate;
                        break;

                    case 1:
                        avatar = R.drawable.employer;
                        break;

                    case 2:
                        avatar = R.drawable.employer_2;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void saveDetails() {

        String name = nameTxt.getText().toString();
        String dob = dobTxt.getText().toString();
        String email = emailTxt.getText().toString();

        if (name.isEmpty() || dob.isEmpty() || email.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        long personId = dbHelper.insertDetails(
                name,
                dob,
                email,
                avatar
        );

        Toast.makeText(
                this,
                "Person created with ID: " + personId,
                Toast.LENGTH_SHORT
        ).show();

        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
        startActivity(i);
    }

}