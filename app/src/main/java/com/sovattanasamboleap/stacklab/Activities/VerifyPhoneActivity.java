package com.sovattanasamboleap.stacklab.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import com.sovattanasamboleap.stacklab.Authentication.SessionManager;
import com.sovattanasamboleap.stacklab.Database.AppDatabase;
import com.sovattanasamboleap.stacklab.Models.CategoryModel;
import com.sovattanasamboleap.stacklab.Models.ItemModel;
import com.sovattanasamboleap.stacklab.databinding.ActivityVerifyPhoneBinding;

import java.util.Random;

public class VerifyPhoneActivity extends AppCompatActivity {

    ActivityVerifyPhoneBinding binding;
    ProgressDialog progressDialog;
    String verificationId, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog = new ProgressDialog(VerifyPhoneActivity.this);
        progressDialog.setTitle("Please wait.");
        phone = getIntent().getStringExtra("phone");

        if(phone==null){
            Toast.makeText(this, "Invalid phone.", Toast.LENGTH_SHORT).show();
            finish();
        }

        binding.message.setText("We sent the code to "+phone);

        sendVerificationCode("+855"+phone);

        binding.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = binding.otpView.getText().toString();
                if (otp.length() == 6) {
                    verifyCode(otp);
                } else {
                    Toast.makeText(VerifyPhoneActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        progressDialog.show();
        // Skip OTP verification by directly calling the method to handle successful verification
        signInWithPhoneAuthCredential(null); // Pass null instead of actual PhoneAuthCredential
    }

    private void verifyCode(String codeByUser) {
        progressDialog.show();
        // Verify with any OTP provided by the user
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, codeByUser);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        // Here you are bypassing the actual OTP verification process
        // You can directly proceed with the user authentication
        progressDialog.dismiss();
        Toast.makeText(VerifyPhoneActivity.this, "Verification Successful", Toast.LENGTH_SHORT).show();

        createDatabase();
        SessionManager sessionManager = new SessionManager(VerifyPhoneActivity.this);
        sessionManager.createLoginSession(FirebaseAuth.getInstance().getUid(), phone);
        Intent intent = new Intent(VerifyPhoneActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void createDatabase() {
        progressDialog.show();


        AppDatabase db = AppDatabase.getInstance(VerifyPhoneActivity.this);

        Random random = new Random();

        String[] categories = {"Nike", "Puma", "Rebook", "Adidas"};
        int[] prices = {500,600,300,200,700,800,900,1100};
        String[] types = {"Sneaker", "Sport", "Casual", "Formal"};
        String[] links = {"https://freepngimg.com/png/9299-adidas-shoes-picture",
                "https://freepngimg.com/thumb/adidas_shoes/3-2-adidas-shoes-png-clipart-thumb.png",
                "https://freepngimg.com/thumb/adidas_shoes/6-2-adidas-shoes-png-image-thumb.png",
                "https://freepngimg.com/thumb/adidas_shoes/7-2-adidas-shoes-free-download-png-thumb.png",
                "https://freepngimg.com/thumb/men%20shoes/16-men-shoes-png-image-thumb.png"};


        for (String s : categories) {
            db.categoryDao().insertCategory(new CategoryModel(s));
            for (int i = 0; i < 10; i++) {
                ItemModel randomShoe = new ItemModel();
                randomShoe.name = s + String.valueOf(i);
                randomShoe.price = prices[random.nextInt(prices.length)];
                randomShoe.image = links[random.nextInt(links.length)];
                randomShoe.type = types[random.nextInt(types.length)];
                randomShoe.category = categories[random.nextInt(categories.length)];
                db.itemDao().insertItem(randomShoe);
            }
        }

    }
}
