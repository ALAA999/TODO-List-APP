package com.alaa.todolistapp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alaa.todolistapp.R;
import com.alaa.todolistapp.common.BaseActivity;
import com.alaa.todolistapp.databinding.ActivityLogInBinding;
import com.alaa.todolistapp.list.ListActivity;
import com.alaa.todolistapp.utils.AppController;
import com.alaa.todolistapp.utils.UIUtil;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends BaseActivity implements View.OnClickListener {

    private ActivityLogInBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        super.setRootView(binding.getRoot());
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        super.setOnClickListeners(new View[]{binding.login, binding.createProfile, binding.forgetPassword}, this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.create_profile) {
            startActivity(new Intent(this, SignUpActivity.class));
        } else if (view.getId() == R.id.login) {
            if (UIUtil.EditTextsFilled(new EditText[]{binding.email, binding.password}, this)) {
                login(binding.email.getText().toString(), binding.password.getText().toString());
            }
        } else if (view.getId() == R.id.forget_password) {
            startActivity(new Intent(this, ForgetPasswordActivity.class));
        }
    }

    private void login(String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        AppController.getInstance().getAppPreferences().setUserUId(mAuth.getCurrentUser().getUid());
                        Intent i = new Intent(LogInActivity.this, ListActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    } else {
                        UIUtil.showLongToast(getString(R.string.auth_faild), LogInActivity.this);
                    }
                });
    }
}