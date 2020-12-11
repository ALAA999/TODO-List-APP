package com.alaa.todolistapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.databinding.ActivitySignUpBinding;
import com.alaa.todolistapp.list.ListActivity;
import com.alaa.todolistapp.utils.AppController;
import com.alaa.todolistapp.utils.UIUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;

public class SignUpActivity extends BaseActivity implements View.OnClickListener {

    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        super.setOnClickListeners(new View[]{binding.createProfile, binding.login}, this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_profile) {
            if (UIUtil.EditTextsFilled(new EditText[]{binding.email, binding.password}, this)) {
                signUp(binding.email.getText().toString(), binding.password.getText().toString());
            }
        } else if (view.getId() == R.id.login) {
            onBackPressed();
        }
    }

    private void signUp(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        AppController.getInstance().getAppPreferences().setUser(mAuth.getCurrentUser());
                        Intent i = new Intent(SignUpActivity.this, ListActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        UIUtil.showLongToast(getString(R.string.auth_faild), SignUpActivity.this);
                    }
                });
    }
}