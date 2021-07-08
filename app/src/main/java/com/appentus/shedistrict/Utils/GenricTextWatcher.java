package com.appentus.shedistrict.Utils;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.appentus.shedistrict.R;

public class GenricTextWatcher implements TextWatcher {
    private String type;
    private EditText editText;
    private ImageView imageView;
    public GenricTextWatcher(String type, EditText editText, ImageView imageView) {
        this.type = type;
        this.editText = editText;
        this.imageView = imageView;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (type.equals("email")) {
            if (!new Utility().isValidEmail(editText.getText().toString())) {
                setDrawable(false);
            } else {
                setDrawable(true);
            }
        }else if (type.equals("name")){
            if (editText.getText().toString().isEmpty()) {
                setDrawable(false);
            } else {
                setDrawable(true);
            }
        }else if (type.equals("psw")){
            if (!new Utility().passValidator(editText.getText().toString())) {
                setDrawable(false);
            } else {
                setDrawable(true);
            }
        }else if (type.equals("mobile")){
            if (editText.getText().toString().isEmpty()) {
                setDrawable(false);
            } else {
                setDrawable(true);
            }
        }
        else if(type.equals("message")){
            if (editText.getText().toString().isEmpty()) {
                setDrawableMessage(false);
            } else {
                setDrawableMessage(true);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    private void setDrawable(boolean isCorrect) {

        if (isCorrect) {
            Drawable img = editText.getContext().getResources().getDrawable(R.drawable.ic_check);
            img.setBounds(0, 0, 42, 42);/*
            editText.setCompoundDrawables(null, null, img, null);*/
            imageView.setImageDrawable(img);
        } else {
            Drawable img = editText.getContext().getResources().getDrawable(R.drawable.ic_uncheck);
            img.setBounds(0, 0, 42, 42);/*
            editText.setCompoundDrawables(null, null, img, null);*/
            imageView.setImageDrawable(img);
        }
    }



    private void setDrawableMessage(boolean isCorrect) {

        if (isCorrect) {
            Drawable img = editText.getContext().getResources().getDrawable(R.drawable.send1);
            img.setBounds(0, 0, 42, 42);/*
            editText.setCompoundDrawables(null, null, img, null);*/
            imageView.setImageDrawable(img);
        } else {
            Drawable img = editText.getContext().getResources().getDrawable(R.drawable.send);
            img.setBounds(0, 0, 42, 42);/*
            editText.setCompoundDrawables(null, null, img, null);*/
            imageView.setImageDrawable(img);
        }
    }

}
