package com.myandroid.caseyjohnson.battleship;

import android.graphics.Point;

public class GameCell {
    private Boolean hasShip;
    private Boolean miss;
    private Boolean hit;
    private Boolean waiting;
    private Point topLeft;
    private Point bottomRight;
    private Point viewOrigin;
    private int cellWidth, cellHeigh;

    public GameCell() {
        this.hasShip = false;
        this.miss = false;
        this.hit = false;
        this.waiting = false;
    }

    public GameCell(Boolean hasShip, Boolean miss, Boolean hit, Boolean waiting, Point topLeft, Point bottomRight) {
        this.hasShip = hasShip;
        this.miss = miss;
        this.hit = hit;
        this.waiting = waiting;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Boolean getHasShip() {
        return hasShip;
    }

    public void setHasShip(Boolean hasShip) {
        this.hasShip = hasShip;
    }

    public Boolean getMiss() {
        return miss;
    }

    public void setMiss(Boolean miss) {
        this.miss = miss;
    }

    public Boolean getHit() {
        return hit;
    }

    public void setHit(Boolean hit) {
        this.hit = hit;
    }

    public Boolean getWaiting() {
        return waiting;
    }

    public void setWaiting(Boolean waiting) {
        this.waiting = waiting;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public Point getViewOrigin() {
        return viewOrigin;
    }

    public void setViewOrigin(Point viewOrigin) {
        this.viewOrigin = viewOrigin;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public int getCellHeigh() {
        return cellHeigh;
    }

    public void setCellHeigh(int cellHeigh) {
        this.cellHeigh = cellHeigh;
    }
}
