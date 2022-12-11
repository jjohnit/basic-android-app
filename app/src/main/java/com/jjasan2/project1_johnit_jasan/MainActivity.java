package com.jjasan2.project1_johnit_jasan;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String enteredPhone = null;
    boolean isValidNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the values of enteredPhone & isValidNumber when there is a saved state
        if(savedInstanceState != null){
            enteredPhone = savedInstanceState.getString("enteredPhone");
            isValidNumber = savedInstanceState.getBoolean("isValidNumber");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // To disable button 2 when no phone number is entered.
        findViewById(R.id.button2).setEnabled(enteredPhone != null);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("enteredPhone", enteredPhone);
        outState.putBoolean("isValidNumber", isValidNumber);
    }

    //    Method to be called when button 1 is clicked
    public void onClickButton1(View view) {
        Log.i("DebugTest", "Button 1 clicked");
        Intent intent = new Intent(getApplicationContext(), Activity2.class);
        activity2Result.launch(intent);
    }

    //    Method to be called when button 2 is clicked
    public void onClickButton2(View view) {
        // Display a toast when the entered number is incorrect
        if(!isValidNumber){
            Toast.makeText(getApplicationContext(), "The entered number " + enteredPhone
                    + " is incorrect.", Toast.LENGTH_LONG).show();
            return;
        }
        // Open the dialer with the entered number when the number is valid
        Intent dialerIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + enteredPhone));
        startActivity(dialerIntent);
    }

    // result contract with the result callback for the intent calling Activity 2
    ActivityResultLauncher<Intent> activity2Result = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                isValidNumber = (result.getResultCode() == RESULT_OK);
                enteredPhone = result.getData().getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            });
}