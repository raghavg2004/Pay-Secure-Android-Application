package com.expense.moneytracker.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.expense.moneytracker.views.activites.MainActivity;

import java.util.concurrent.Executor;

public class BiometricAuthHelper {

    public void authenticate(Context context, BiometricPrompt.AuthenticationCallback callback) {
        // Get the main thread executor for the biometric prompt
        Executor executor = ContextCompat.getMainExecutor(context);

        // Create the BiometricPrompt instance
        BiometricPrompt biometricPrompt = new BiometricPrompt((MainActivity) context, executor, callback);

        // Create the prompt information with title, subtitle, and a fallback to PIN
        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock Pay Secure")
                .setSubtitle("Enter phone screen lock PIN or Fingerprint")
                .setNegativeButtonText(" ") // This text will be shown as a fallback option
                .build();

        // Start the biometric authentication
        biometricPrompt.authenticate(promptInfo);
    }
}