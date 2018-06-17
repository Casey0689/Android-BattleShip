package com.myandroid.caseyjohnson.battleship;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class DefenseBoardView extends AppCompatImageView {

    Paint paint;
    public static int cellWidth;
    String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    public DefenseBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = canvas.getHeight() - 1;
        int width = canvas.getWidth() - 1;
        int cellHeight = height / 11;
        cellWidth = width / 11;
        paint.setTextSize(cellWidth);

//        canvas.drawLine(0, 0, width, 0, paint); // ------ drawLine(X-Start, Y-Start, X-Stop, Y-Stop, PaintObject)

        for (int i = 0; i < 12; i++) {
            // Horizontal
            canvas.drawLine(0, (cellWidth * i), width, (cellWidth * i), paint);
            // Vertical
            canvas.drawLine((cellWidth * i), 0, (cellWidth * i), width, paint);
        }

        for (int y = 0; y < 11; y++) {
            for (int x = 0; x < 11; x++) {
                BoardSetupActivity.defendingGrid[x][y].setTopLeft(new Point(x * cellWidth, y * cellWidth));
                BoardSetupActivity.defendingGrid[x][y].setBottomRight(new Point((x + 1) * cellWidth, (y + 1) * cellWidth));
            }
        }

        Rect textBounds = new Rect();
        paint.getTextBounds("A", 0, 1, textBounds);
        int textHeight = textBounds.height();
        int textWidth = textBounds.width();

        int textX = cellWidth / 2;
        int textY = cellWidth + ((cellWidth / 2) - textHeight);
        for (String letter : letters) {
            textY += cellWidth;
            canvas.drawText(letter, textX, textY, paint);
        }

        textX = cellWidth / 2;
        textY = cellWidth + ((cellWidth / 2) - textHeight);
        for (String number : numbers) {
            textX += cellWidth;
            canvas.drawText(number, textX, textY, paint);
        }

        float w = paint.measureText("W", 0, 0);
        float center = (cellWidth / 2) - (w / 2);
        for (int y = 0; y < 11; y++) {
            for (int x = 0; x < 11; x++) {
                if (BoardSetupActivity.defendingGrid[x][y].getHasShip()) {
                    drawCell("S", x, y, center, canvas);
                } else if (BoardSetupActivity.defendingGrid[x][y].getHit()) {
                    drawCell("*", x, y, center, canvas);
                } else if (BoardSetupActivity.defendingGrid[x][y].getMiss()) {
                    drawCell("-", x, y, center, canvas);
                } else if (BoardSetupActivity.defendingGrid[x][y].getWaiting()) {
                    drawCell("W", x, y, center, canvas);
                }
            }
        }
    }

    void drawCell(String contents, int x, int y, float center, Canvas canvas){
        canvas.drawText(contents,
                BoardSetupActivity.defendingGrid[x][y].getTopLeft().x + center,
                BoardSetupActivity.defendingGrid[x][y].getBottomRight().y - center / 3,
                paint);
    }
}
