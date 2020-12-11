package com.alaa.todolistapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.alaa.todolistapp.R;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class UIUtil {

    public static void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideEditTextKeyboardInDialog(View view) {
        view.postDelayed((Runnable) () -> {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }, 1);
    }

    public static void openKeyboardWithFocus(Activity activity, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(editText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        editText.requestFocus();
    }

    public static void showShortToast(@StringRes int stringId, Context context) {
        showToast(stringId, Toast.LENGTH_SHORT, context);
    }

    public static void showLongToast(@StringRes int stringId, Context context) {
        showToast(stringId, Toast.LENGTH_LONG, context);
    }

    private static void showToast(@StringRes int stringId, int duration, Context context) {
        Toast.makeText(context, stringId, duration).show();
    }

    public static void showShortToast(String text, Context context) {
        showToast(text, Toast.LENGTH_SHORT, context);
    }

    public static void showLongToast(String text, Context context) {
        showToast(text, Toast.LENGTH_LONG, context);
    }

    private static void showToast(String text, int duration, Context context) {
        Toast.makeText(context, text, duration).show();
    }

    public static boolean EditTextsFilled(EditText[] editTexts, Context context) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().trim().equals("")) {
                editText.setError(context.getString(R.string.field_required));
                return false;
            }
        }
        return true;
    }
}
