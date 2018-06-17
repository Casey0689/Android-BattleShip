package com.myandroid.caseyjohnson.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    EditText edtUsername, edtPassword;
    TextView txtError;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        txtError = findViewById(R.id.txtError);
        btnLogin = findViewById(R.id.btnLogin);
    }

    public void authOnClick(View v) {
        btnLogin.setEnabled(false);
        username = edtUsername.getText().toString();
        password = edtPassword.getText().toString();
        String url = Battle_Server_Url + "api/v1/login.json";
        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Do something with the returned data
                        userPrefs = gson.fromJson(response, UserPreferences.class);
                        toastIt(userPrefs.getAvatarName());
                        Intent intent = new Intent(getApplicationContext(), GameLobby.class);
                        intent.putExtra("UserAvatarName", userPrefs.getAvatarName());
                        intent.putExtra("AvatarImage", userPrefs.getAvatarImage());
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with the error
                        Log.d("INTERNET", error.toString());
                        toastIt("Internet Failer: " + error.toString() + ": INVALID LOGIN INFORMATION");
                        edtUsername.setText("");
                        edtPassword.setText("");
                        txtError.setText("Invalid Credentials");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String credentials = username + ":" + password;
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        requestQueue.add(request);
    }
}
