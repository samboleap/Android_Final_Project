package com.sovattanasamboleap.stacklab.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.sovattanasamboleap.stacklab.Activities.LoginActivity;
import com.sovattanasamboleap.stacklab.Activities.MainActivity;
import com.sovattanasamboleap.stacklab.Authentication.SessionManager;
import com.sovattanasamboleap.stacklab.Helpers.CartManager;
import com.sovattanasamboleap.stacklab.R;
import com.sovattanasamboleap.stacklab.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SessionManager s = new SessionManager(getContext());
        binding.phone.setText(s.getUsersDetailsFromSessions().get(SessionManager.KEY_PHONE));

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.logoutSession();
                FirebaseAuth.getInstance().signOut();
                CartManager.clearCart(getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                ((Activity) getContext()).finish();
            }
        });

    }
}