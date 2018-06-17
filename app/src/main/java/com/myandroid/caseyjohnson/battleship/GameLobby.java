package com.myandroid.caseyjohnson.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GameLobby extends BaseActivity {

    private ListView listViewUsers;
    ArrayAdapter<UserPreferences> adapter;
    String avatarName, avatarImage;
    TextView txtUser;
    ImageView imageView;
    Button btnChallengeComputer;

//--------------------------------- On Create ------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        avatarName = getIntent().getStringExtra("UserAvatarName");
        avatarImage = getIntent().getStringExtra("AvatarImage");

        txtUser = findViewById(R.id.txtUser);
        imageView = findViewById(R.id.imageViewUser);
        listViewUsers = findViewById(R.id.listViewUsers);
        btnChallengeComputer = findViewById(R.id.btnChallengeComputer);

        imageView.setImageResource(R.drawable.ic_launcher_background);

        txtUser.setText(avatarName);
        Picasso.with(getApplicationContext()).load(Battle_Server_Url + avatarImage).into(imageView);

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                toastIt("You clicked position " + position);
                //Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                //intent.putExtra("RecordID", records[position].getId());
                //startActivity(intent);
            }
        });
        String url = Battle_Server_Url + "api/v1/all_users.json";
        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Do something with the returned data
                        users = gson.fromJson(response, UserPreferences[].class);
                        adapter = new ArrayAdapter<UserPreferences>(getApplicationContext(), R.layout.activity_listview, users);
                        listViewUsers.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with the error
                        Log.d("INTERNET", error.toString());
                        toastIt("Internet Failer: " + error.toString() + ": INVALID LOGIN INFORMATION");
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

//--------------------------------- Challenge Computer Button ------------------------------------

    public void challengeComputerOnClick(View v) {
        btnChallengeComputer.setEnabled(false);
        toastIt("Loading New Game");
        String url = Battle_Server_Url + "api/v1/challenge_computer.json";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Do something with the returned data
                        //Log.d("INTERNET", response.toString());
                        try {
                            gameId = response.getInt("game_id");
                            Intent intent = new Intent(getApplicationContext(), BoardSetupActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with the error
                        Log.d("INTERNET", error.toString());
                        toastIt("Internet Failer: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                String credentials = username + ":" + password;
                String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return headers;
            }
        };
        request.setRetryPolicy( new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(request);

    }
}
