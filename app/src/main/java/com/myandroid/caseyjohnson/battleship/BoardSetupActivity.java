package com.myandroid.caseyjohnson.battleship;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BoardSetupActivity extends BaseActivity {

    TextView txtGameID, txtGameHeader;
    Spinner shipSpinner, directionSpinner, letterSpinner, numberSpinner;
    ArrayAdapter shipSpinnerArrayAdapter, directionSpinnerArrayAdapter;
    String[] shipsArray, directionsArray;
    static TreeMap<String, Integer> shipsMap = new TreeMap<String, Integer>();
    static TreeMap<String, Integer> directionsMap = new TreeMap<String, Integer>();
    ImageView imgDefensiveGrid, imgAttackingGrid;
    int directionNum;
    RadioButton radioAttack, radioDefend;
    Button btnAddShip;

    //---------------------------------------------- On Create -------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_setup);

        InitializeApp();

        txtGameHeader = findViewById(R.id.txtGameHeader);
        txtGameID = findViewById(R.id.txtGameID);
        shipSpinner = findViewById(R.id.shipSpinner);
        directionSpinner = findViewById(R.id.directionSpinner);
        letterSpinner = findViewById(R.id.letterSpinner);
        numberSpinner = findViewById(R.id.numberSpinner);
        imgDefensiveGrid = findViewById(R.id.imageDefensiveGrid);
        imgAttackingGrid = findViewById(R.id.imageAttackingGrid);
        radioAttack = findViewById(R.id.radioAttack);
        radioDefend = findViewById(R.id.radioDefend);
        txtGameID.setText("GameID: " + gameId);
        btnAddShip = findViewById(R.id.btnAddShip);

        GetAvailableShips();
        GetDirections();
    }
//------------------------------------------- Radio Button Grid Switch -----------------------------------------------

    public void RadioOnClick(View v) {
        if (radioDefend.isChecked()) {
            imgDefensiveGrid.setVisibility(View.VISIBLE);
            imgAttackingGrid.setVisibility(View.INVISIBLE);
            imgDefensiveGrid.invalidate();
        } else {
            imgDefensiveGrid.setVisibility(View.INVISIBLE);
            imgAttackingGrid.setVisibility(View.VISIBLE);
            imgAttackingGrid.invalidate();
        }
    }

//--------------------------------------------- Initialize Both Grid Boards -----------------------------------------------

    private void InitializeApp() {
        for (int y = 0; y < 11; y++) {
            for (int x = 0; x < 11; x++) {
                defendingGrid[x][y] = new GameCell();
                attackingGrid[x][y] = new GameCell();
            }
        }
    }

//----------------------------------------------- Get Available Ships -----------------------------------------------------

    private void GetAvailableShips() {
        String url = Battle_Server_Url + "api/v1/available_ships.json";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Do something with the returned data
                        //Log.d("INTERNET", response.toString());
                        Iterator iterator = response.keys();
                        while (iterator.hasNext()) {
                            String key = (String) iterator.next();
                            try {
                                Integer value = (Integer) response.get(key);
                                shipsMap.put(key, value);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        SetShipSelection();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something with the error
                        Log.d("INTERNET", error.toString());
                        toastIt("Internet Failer: " + error.toString() + ": INVALID LOGIN INFORMATION");
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
        requestQueue.add(request);
    }
//----------------------------------------------- Get Directions ------------------------------------------------------

    public void GetDirections() {
        String url = Battle_Server_Url + "api/v1/available_directions.json";
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Iterator iterator = response.keys();
                        while (iterator.hasNext()) {
                            String key = (String) iterator.next();
                            try {
                                Integer value = (Integer) response.get(key);
                                directionsMap.put(key, value);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        int size = directionsMap.keySet().size();
                        directionsArray = new String[size];
                        directionsArray = directionsMap.keySet().toArray(new String[]{});
                        directionSpinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, directionsArray);
                        directionSpinner.setAdapter(directionSpinnerArrayAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("INTERNET", error.toString());
                        toastIt("Internet Failer: " + error.toString() + ": INVALID LOGIN INFORMATION");
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
        requestQueue.add(request);
    }
//--------------------------------------------- Add Ship Button ---------------------------------------------------

    public void onClickAddShip(View v) {
        String ship = shipSpinner.getSelectedItem().toString();
        directionNum = directionsMap.get(directionSpinner.getSelectedItem().toString());  //int numPegs = shipsMap.get("carrier"); //5    //int directionNum = directionsMap.get("north"); //0
        String letter = letterSpinner.getSelectedItem().toString();
        String number = numberSpinner.getSelectedItem().toString();
        final int row = letterSpinner.getSelectedItemPosition() + 1;
        final int col = numberSpinner.getSelectedItemPosition() + 1;
        final int numPegs = shipsMap.get(ship);
        Log.d("ROW", "Row: " + row);
        Log.d("COL", "Col: " + col);

        String url = Battle_Server_Url + "/api/v1/game/" + gameId + "/add_ship/" + ship + "/" + letter + "/" + number + "/" + directionNum + ".json";
        StringRequest request = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Do something with the returned data
                        Log.d("INTERNET", response);
                        if (response.contains("error")) {
                            toastIt("Cannot Place Ship!");
                        } else {
                            drawShip(col, row, directionNum, numPegs);
                            shipsMap.remove(shipSpinner.getSelectedItem());
                            SetShipSelection();
                            shipSpinnerArrayAdapter.notifyDataSetChanged();
                            ClickCell();
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

//--------------------------------------------- Draw Ship Method ---------------------------------------------------

    private void drawShip(int startX, int startY, int direction, int length) {
        switch (direction) {
            case 0: //North
                for (int i = 0; i < length; i++) {
                    defendingGrid[startX][startY - i].setHasShip(true);
                    imgDefensiveGrid.invalidate();
                }
                break;
            case 2: //East
                for (int i = 0; i < length; i++) {
                    defendingGrid[startX + i][startY].setHasShip(true);
                    imgDefensiveGrid.invalidate();
                }
                break;
            case 4: //South
                for (int i = 0; i < length; i++) {
                    defendingGrid[startX][startY + i].setHasShip(true);
                    imgDefensiveGrid.invalidate();
                }
                break;
            case 6: //West
                for (int i = 0; i < length; i++) {
                    defendingGrid[startX - i][startY].setHasShip(true);
                    imgDefensiveGrid.invalidate();
                }
                break;
            default:
                break;
        }
    }

//--------------------------------------------- Set Ship List -----------------------------------------------------

    private void SetShipSelection() {
        // shipsMap convert into array that the spinner can use.
        int size = shipsMap.keySet().size();
        shipsArray = new String[size];
        shipsArray = shipsMap.keySet().toArray(new String[]{});
        shipSpinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, shipsArray);
        shipSpinner.setAdapter(shipSpinnerArrayAdapter);
    }

//--------------------------------------------- Click On Game Cells ---------------------------------------------------

    private void ClickCell() {
        if (shipsArray.length == 0) {
            txtGameHeader.setText("BattleShip!");
            btnAddShip.setEnabled(false);
            toastIt("GAME START!");
            imgDefensiveGrid.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        final int x = (int) ((Math.floor(event.getX() / DefenseBoardView.cellWidth)));
                        final int y = (int) (Math.floor(event.getY() / DefenseBoardView.cellWidth));
                        String letterRow = "";
                        Log.i("SHIP", "onTouch: x( " + event.getX() + ") y(" + event.getY() + ")");
                        Log.i("SHIP", "onTouch: cellX( " + event.getX() / DefenseBoardView.cellWidth + ") cellY(" + event.getY() / DefenseBoardView.cellWidth + ")");
                        if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 1) {
                            letterRow = "a";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 2) {
                            letterRow = "b";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 3) {
                            letterRow = "c";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 4) {
                            letterRow = "d";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 5) {
                            letterRow = "e";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 6) {
                            letterRow = "f";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 7) {
                            letterRow = "g";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 8) {
                            letterRow = "h";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 9) {
                            letterRow = "i";
                        } else if (Math.floor(event.getY() / DefenseBoardView.cellWidth) == 10) {
                            letterRow = "j";
                        }
                        //Make Attack API Call
                        String url = Battle_Server_Url + "api/v1/game/" + gameId + "/attack/" + letterRow + "/" + (Math.round(Math.floor(event.getX() / DefenseBoardView.cellWidth))) + ".json";
                        StringRequest request = new StringRequest(
                                Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        int compRow = 0;
                                        atkInfo = gson.fromJson(response, AttackInfo.class);
                                        atkInfo.setCompCol(atkInfo.getCompCol() + 1);
                                        int compCol = atkInfo.getCompCol();
                                        Log.d("INTERNET", response.toString());
                                        Log.d("ATTACKINFO", atkInfo.toString());
                                        switch (atkInfo.getCompRow()) {
                                            case "a":
                                                compRow = 1;
                                                break;
                                            case "b":
                                                compRow = 2;
                                                break;
                                            case "c":
                                                compRow = 3;
                                                break;
                                            case "d":
                                                compRow = 4;
                                                break;
                                            case "e":
                                                compRow = 5;
                                                break;
                                            case "f":
                                                compRow = 6;
                                                break;
                                            case "g":
                                                compRow = 7;
                                                break;
                                            case "h":
                                                compRow = 8;
                                                break;
                                            case "i":
                                                compRow = 9;
                                                break;
                                            case "j":
                                                compRow = 10;
                                                break;
                                        }
                                        if (atkInfo.getHit()) {
                                            attackingGrid[x][y].setHit(true);
                                            imgAttackingGrid.invalidate();
                                            toastIt("Hit");
                                        } else {
                                            attackingGrid[x][y].setMiss(true);
                                            imgAttackingGrid.invalidate();
                                            toastIt("Miss");
                                        }
                                        if (atkInfo.getCompHit()) {
                                            defendingGrid[compCol][compRow].setHasShip(false);
                                            defendingGrid[compCol][compRow].setHit(true);
                                            imgDefensiveGrid.invalidate();
                                        } else {
                                            defendingGrid[compCol][compRow].setMiss(true);
                                            imgDefensiveGrid.invalidate();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("INTERNET", error.toString());
                                        toastIt("Internet Failer: " + error.toString());
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
                    return true; // We have handled the event
                }
            });
        }
    }
}
