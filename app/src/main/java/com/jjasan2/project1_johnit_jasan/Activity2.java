package com.jjasan2.project1_johnit_jasan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Activity2 extends AppCompatActivity {

    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        phone = findViewById(R.id.phone_entered);
        // Retrieve the value of enteredPhone when there is a saved state
        if(savedInstanceState != null)
            phone.setText(savedInstanceState.getString("enteredPhone"));
        phone.setOnEditorActionListener(editorActionListener);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("enteredPhone", phone.getText().toString());
    }

    // Method to be called on clicking the done button in the soft keyboard
    public TextView.OnEditorActionListener editorActionListener = (v, actionId, event) -> {
        enterPhone();
        return true;
    };

    protected void enterPhone(){
        // Pattern for digits appearing consecutively
        String phonePattern1 = "[\\d]{10}";
        // Pattern using parentheses, an optional space and a mandatory dash
        String phonePattern2 = "[(]\\d{3}[)](\\s)?(\\d){3}[-](\\d){4}";

//        Log.i("DebugTest", "Pattern 1 is " +
//                String.valueOf(Pattern.matches(phonePattern1, phone.getText().toString())));
//        Log.i("DebugTest", "Pattern 2 is " +
//                String.valueOf(Pattern.matches(phonePattern2, phone.getText().toString())));

        Intent intent = getIntent();
        // Assign the entered phone number as an intent extra
        intent.putExtra(Intent.EXTRA_PHONE_NUMBER, phone.getText().toString());
        // Set the result to send back to the main activity
        // When either of the pattern matches, set result as ok
        if(Pattern.matches(phonePattern1, phone.getText().toString()) ||
                Pattern.matches(phonePattern2, phone.getText().toString())){
            setResult(RESULT_OK, intent);
        }
        else
            setResult(RESULT_CANCELED, intent);
        finish();
    }

    // Called on click of the OK button,
    // which can be used instead of the done button in the soft keyboard
    public void onClickOkButton(View view) {
        enterPhone();
    }

    // Called on click of the cancel button,
    // which can be used when the user wants to return to the main activity without entering a number
    public void onClickCancelButton(View view) {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }
}