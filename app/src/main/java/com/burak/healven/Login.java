package com.burak.healven;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.burak.healven.helpful.Profile;
import com.burak.healven.helpful.helperFunctions;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {
    EditText email_inp, password_inp;
    Switch rememberBox;
    File saveFile;
    private boolean hashed_already = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final ImageButton signIn_IB = findViewById(R.id.btn_signin);
        final ImageButton singUp_IB = findViewById(R.id.btn_signup);
        email_inp = findViewById(R.id.email_inp);
        password_inp = findViewById(R.id.pass_ino);
        rememberBox = findViewById(R.id.sw_remember);



        saveFile = new File(getFilesDir(), "saved");

        if(saveFile.exists()){
            loadFromFile();
        }


        password_inp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hashed_already = false;
            }
        });

        signIn_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email_inp.getText().toString().isEmpty()){
                    email_inp.requestFocus();
                    Drawable erricon = getDrawable(R.drawable.ic_error_24dp);
                    erricon.setBounds(0,0, erricon.getIntrinsicWidth(),erricon.getIntrinsicHeight());
                    email_inp.setError("You've wasted your time",erricon);
                }else if(password_inp.getText().toString().isEmpty()){
                    password_inp.requestFocus();
                    Drawable erricon = getDrawable(R.drawable.ic_error_24dp);
                    erricon.setBounds(0,0, erricon.getIntrinsicWidth(),erricon.getIntrinsicHeight());
                    password_inp.setError("You've wasted your time",erricon);
                }else{

                    String hashedPassword;
                    if(hashed_already){
                        hashedPassword = password_inp.getText().toString();
                    }else{
                        hashedPassword = toHexString(hashString(password_inp.getText().toString()));
                    }


                    String mail = email_inp.getText().toString();
                    AlertDialog alertDialog = showLoading();
                    Profile user = tryToLogin(hashedPassword, mail);
                    if(user != null){
                        if(rememberBox.isChecked()){
                            save(mail, hashedPassword);
                        }else if(saveFile.exists() && !rememberBox.isChecked()){
                            saveFile.delete();
                        }
                        finish();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("profile", user);
                        startActivity(new Intent(Login.this, MainMenu.class).putExtras(bundle));
                    }else{
                        alertDialog.hide();
                        wrongInput();
                    }


                }
            }
        });


        singUp_IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });


    }

    private Profile tryToLogin(String pass, String mail) {



        mail = toHexString(hashString(mail));

        JSONObject jsonObject = null;
        try {
            jsonObject = helperFunctions.getJSONFromServer(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(jsonObject != null){

                try {
                    if(jsonObject.get("password").equals(pass)){
                        return new Profile(jsonObject.get("name").toString(), jsonObject.get("surname").toString(), mail);
                    }else{
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        return null;
    }

    private AlertDialog showLoading() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
        alertDialogBuilder.setView(R.layout.loading);
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    private void loadFromFile() {
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(saveFile);

            StringBuilder mail = new StringBuilder();
            StringBuilder passwordHashed = new StringBuilder();
            int ch = inputStream.read();
            while(((char)ch != '\n') && ch != -1){
                mail.append((char)ch);
                ch = inputStream.read();
            }
            ch = inputStream.read();
            while (ch != -1){
                passwordHashed.append((char)ch);
                ch =  inputStream.read();
            }



            if(mail.length() != 0 && passwordHashed.length()!=0) {
                email_inp.setText(mail.toString());
                password_inp.setText(passwordHashed.toString());
                rememberBox.setChecked(true);

                Log.e("Mail", mail.toString() + "/");
                Log.e("PW", passwordHashed.toString() + "/");
                hashed_already = true;
            }
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void save(CharSequence mail, CharSequence pw) {

        FileOutputStream outputStream = null;
        try {
                outputStream = new FileOutputStream(saveFile, false);

            //second argument of FileOutputStream constructor indicates whether
            //to append or create new file if one exists


            outputStream.write((mail + "\n" + pw).getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private byte[] hashString(CharSequence pw){
        MessageDigest md = null;
        try {
           md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (md != null) {
            return md.digest(pw.toString().getBytes(StandardCharsets.UTF_8));
        }else return null;
    }


    private void wrongInput() {
        final AlertDialog.Builder exit = new AlertDialog.Builder(this);
        exit.setTitle("Wrong");
        exit.setMessage("Mail or password is wrong!");
        exit.setNegativeButton("OK", null);
        exit.setPositiveButton("Forgot Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });
        exit.show();
    }

    @Override
    public void onBackPressed() {
        helperFunctions.exitOnBackButton(this);
    }

    //method to convert bytes into hexadecimal numbers
    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }



    //checks if the mail is valid or not using regex  expression
    public static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
