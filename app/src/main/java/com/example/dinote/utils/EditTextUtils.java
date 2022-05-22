package com.example.dinote.utils;

import android.graphics.Color;
import android.text.InputType;
import android.widget.EditText;

import com.example.dinote.R;

public class EditTextUtils {
    public static void disableEditText(EditText edit) {

        edit.setInputType(InputType.TYPE_NULL);
        edit.setBackgroundColor(Color.TRANSPARENT);
        edit.setTextColor(Color.BLACK);
    }

    public static void enableEditText(EditText edit) {
        edit.setInputType(InputType.TYPE_CLASS_TEXT);
    }


}
