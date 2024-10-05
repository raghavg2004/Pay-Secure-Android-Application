package com.expense.moneytracker.views.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.biometric.BiometricPrompt;

import com.expense.moneytracker.utils.BiometricAuthHelper;
import com.expense.moneytracker.utils.Constants;
import com.expense.moneytracker.viewmodels.MainViewModel;
import com.expense.moneytracker.R;
import com.expense.moneytracker.databinding.ActivityMainBinding;
import com.expense.moneytracker.views.fragments.MoreFragment;
import com.expense.moneytracker.views.fragments.PayFragment;
import com.expense.moneytracker.views.fragments.StatsFragment;
import com.expense.moneytracker.views.fragments.TransactionsFragment;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Calendar calendar;
    public MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setSupportActionBar(binding.materialToolbar);
        getSupportActionBar().setTitle("Transaction");

        Constants.setCategories();

        // Set toolbar title color to black
        binding.materialToolbar.setTitleTextColor(getResources().getColor(android.R.color.black));

        calendar = Calendar.getInstance();

        // Initialize BiometricAuthHelper
        BiometricAuthHelper biometricAuthHelper = new BiometricAuthHelper();

        // Authenticate using fingerprint or PIN
        biometricAuthHelper.authenticate(this, new BiometricPrompt.AuthenticationCallback() {

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                // Load the default fragment on successful authentication
                loadDefaultFragment();
                Toast.makeText(MainActivity.this, "Authentication successful!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

                if (errorCode == BiometricPrompt.ERROR_CANCELED) {
                    // Show PIN unlock dialog if authentication is canceled
                    showPinUnlockDialog();
                } else if (errorCode == BiometricPrompt.ERROR_LOCKOUT) {
                    // Too many attempts, fallback to PIN unlock
                    Toast.makeText(MainActivity.this, "Too many attempts. Try again later.", Toast.LENGTH_LONG).show();
                    showPinUnlockDialog();
                } else if (errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
                    // User canceled the authentication
                    showPinUnlockDialog();
                } else {
                    // Handle other authentication errors
                    Toast.makeText(MainActivity.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                    finish(); // Close the app if authentication fails
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                // Notify the user when fingerprint is not recognized
                Toast.makeText(MainActivity.this, "Fingerprint not recognized. Try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDefaultFragment() {
        // Set default fragment (Transactions)
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new TransactionsFragment());
        transaction.commit();

        // Set up bottom navigation
        binding.bottomNavigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (item.getItemId() == R.id.transactions) {
                    transaction.replace(R.id.content, new TransactionsFragment(), "TransactionsFragment");
                    getSupportActionBar().setTitle("Transactions");
                    getSupportFragmentManager().popBackStack();
                } else if (item.getItemId() == R.id.stats) {
                    transaction.replace(R.id.content, new StatsFragment());
                    getSupportActionBar().setTitle("Stats");
                    transaction.addToBackStack(null);
                } else if (item.getItemId() == R.id.more) {
                    // Handle "More" button navigation
                    transaction.replace(R.id.content, new MoreFragment());
                    getSupportActionBar().setTitle("More");
                    transaction.addToBackStack(null);
                } else if (item.getItemId() == R.id.pay_now) {
                    // Handle Pay Button
                    transaction.replace(R.id.content, new PayFragment());
                    getSupportActionBar().setTitle("Payment Page");
                    transaction.addToBackStack(null);
                }
                transaction.commit();
                return true;
            }
        });
    }

    private void showPinUnlockDialog() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);

        if (keyguardManager.isKeyguardSecure()) {
            // Create an AlertDialog for PIN entry
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Your PIN");

            // Create an EditText for user input
            final EditText input = new EditText(this);
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(input);
            builder.setView(layout);

            builder.setPositiveButton("OK", (dialog, which) -> {
                // Check if the input matches the device's lock PIN or password
                String enteredPin = input.getText().toString();
                if (checkPin(enteredPin)) {
                    loadDefaultFragment(); // Load the default fragment on successful PIN entry
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect PIN", Toast.LENGTH_SHORT).show();
                    finish(); // Close the app if the PIN is incorrect
                }
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
                finish(); // Close the app if cancelled
            });

            builder.show();
        } else {
            Toast.makeText(this, "Device is not secured with a PIN", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPin(String enteredPin) {
        // Implement your logic to check the entered PIN against the device's lock PIN.
        // You can only check if the device is secured or not; you cannot retrieve the actual PIN.
        return true; // Replace this with actual validation logic if needed.
    }

    public void getTransactions() {
        viewModel.getTransaction(calendar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle "About" section menu item click Located at above with APP ICON
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_about) {
            // Start AboutActivity when "About" is clicked
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
