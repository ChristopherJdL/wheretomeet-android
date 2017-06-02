package com.example.wheretomeet;

import android.view.View;
import android.widget.ImageButton;

/**
 * Created by 선희 on 2017-06-01.
 */

public class ImageButtonOnClickListener implements View.OnClickListener {

    boolean check;
    private ImageButton button;
    private int click;
    private int notclick;

    public ImageButtonOnClickListener(ImageButton button,final int click,final int notclick) {
       check=false;
        this.button=button;
        this.click=click;
        this.notclick=notclick;
    }

    @Override
    public void onClick(View v) {
        if (check) {
            button.setImageResource(notclick);
            check=false;

        } else {
            button.setImageResource(click);
            check=true;
        }
    }
}

