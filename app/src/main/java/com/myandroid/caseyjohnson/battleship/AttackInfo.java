package com.myandroid.caseyjohnson.battleship;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttackInfo {

    @SerializedName("game_id")
    @Expose
    private Integer gameId;
    @SerializedName("row")
    @Expose
    private String row;
    @SerializedName("col")
    @Expose
    private Integer col;
    @SerializedName("hit")
    @Expose
    private Boolean hit;
    @SerializedName("comp_row")
    @Expose
    private String compRow;
    @SerializedName("comp_col")
    @Expose
    private Integer compCol;
    @SerializedName("comp_hit")
    @Expose
    private Boolean compHit;
    @SerializedName("user_ship_sunk")
    @Expose
    private String userShipSunk;
    @SerializedName("comp_ship_sunk")
    @Expose
    private String compShipSunk;
    @SerializedName("num_computer_ships_sunk")
    @Expose
    private Integer numComputerShipsSunk;
    @SerializedName("num_your_ships_sunk")
    @Expose
    private Integer numYourShipsSunk;
    @SerializedName("winner")
    @Expose
    private String winner;

//    public AttackInfo() {
//    }
//
//    public AttackInfo(Integer gameId, String row, Integer col, Boolean hit, String compRow, Integer compCol, Boolean compHit, String userShipSunk, String compShipSunk, Integer numComputerShipsSunk, Integer numYourShipsSunk, String winner) {
//        super();
//        this.gameId = gameId;
//        this.row = row;
//        this.col = col;
//        this.hit = hit;
//        this.compRow = compRow;
//        this.compCol = compCol;
//        this.compHit = compHit;
//        this.userShipSunk = userShipSunk;
//        this.compShipSunk = compShipSunk;
//        this.numComputerShipsSunk = numComputerShipsSunk;
//        this.numYourShipsSunk = numYourShipsSunk;
//        this.winner = winner;
//    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Boolean getHit() {
        return hit;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }

    public String getCompRow() {
        return compRow;
    }

    public void setCompRow(String compRow) {
        this.compRow = compRow;
    }

    public Integer getCompCol() {
        return compCol;
    }

    public void setCompCol(Integer compCol) {
        this.compCol = compCol;
    }

    public Boolean getCompHit() {
        return compHit;
    }

    public void setCompHit(Boolean compHit) {
        this.compHit = compHit;
    }

    public String getUserShipSunk() {
        return userShipSunk;
    }

    public void setUserShipSunk(String userShipSunk) {
        this.userShipSunk = userShipSunk;
    }

    public String getCompShipSunk() {
        return compShipSunk;
    }

    public void setCompShipSunk(String compShipSunk) {
        this.compShipSunk = compShipSunk;
    }

    public Integer getNumComputerShipsSunk() {
        return numComputerShipsSunk;
    }

    public void setNumComputerShipsSunk(Integer numComputerShipsSunk) {
        this.numComputerShipsSunk = numComputerShipsSunk;
    }

    public Integer getNumYourShipsSunk() {
        return numYourShipsSunk;
    }

    public void setNumYourShipsSunk(Integer numYourShipsSunk) {
        this.numYourShipsSunk = numYourShipsSunk;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "AttackInfo{" +
                "gameId=" + gameId +
                ", row='" + row + '\'' +
                ", col=" + col +
                ", hit=" + hit +
                ", compRow='" + compRow + '\'' +
                ", compCol=" + compCol +
                ", compHit=" + compHit +
                ", userShipSunk='" + userShipSunk + '\'' +
                ", compShipSunk='" + compShipSunk + '\'' +
                ", numComputerShipsSunk=" + numComputerShipsSunk +
                ", numYourShipsSunk=" + numYourShipsSunk +
                ", winner='" + winner + '\'' +
                '}';
    }
}