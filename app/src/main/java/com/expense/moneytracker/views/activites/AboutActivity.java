package com.expense.moneytracker.views.activites;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.expense.moneytracker.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Set title for toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // To show back button
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Closes the activity when the back button is pressed
        return true;
    }
}
