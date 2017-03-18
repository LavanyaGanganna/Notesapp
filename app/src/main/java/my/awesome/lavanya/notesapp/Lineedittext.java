package my.awesome.lavanya.notesapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by lavanya on 3/6/17.
 */

public class Lineedittext extends EditText {
    // the paint we will use to draw the lines
    private Paint dashedLinePaint;

    // a reusable rect object
    private Rect reuseableRect;

    public Lineedittext(Context context) {
        super(context);
        init();
    }

    public Lineedittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Lineedittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Initialize the paint object.
     */
    private void init() {

        // instantiate the rect
        reuseableRect = new Rect();

        // instantiate the paint
        dashedLinePaint = new Paint();
        dashedLinePaint.setARGB(200, 0, 0, 0);
        dashedLinePaint.setStyle(Paint.Style.STROKE);
        dashedLinePaint.setPathEffect(new DashPathEffect(new float[]{4, 6}, 0));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.e("LINE_HEIGHT", "Line height for typeface is " + getLineHeight());

        // get the height of the view
        int height = getHeight();

        // get the height of each line
        int lineHeight = getLineHeight();

        // the number of lines equals the height divided by the line height
        int numberOfLines = height / lineHeight;

        // if there are more lines than what appear on screen
        if (getLineCount() > numberOfLines) {

            // set the number of lines to the line count
            numberOfLines = getLineCount();
        }

        // get the rectangle instance
        Rect r = reuseableRect;

        // get the baseline for the first line
        int baseline = getLineBounds(0, r);

        // for each line
        for (int i = 0; i < numberOfLines; i++) {

            // draw the line
            canvas.drawLine(
                    r.left,                 // left
                    baseline,               // top
                    r.right,                // right
                    baseline,               // bottom
                    dashedLinePaint);       // paint instance

            // get the baseline for the next line
            baseline += getLineHeight();
        }

        super.onDraw(canvas);
    }
}
