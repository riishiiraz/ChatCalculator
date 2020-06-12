package com.riishiiraz.chatcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static MainActivity mainActivity;

    public ConstraintLayout constraintLayout;
    public static RecyclerView RCV;
    public static MessegeAdapter messegeAdapter;
    public static DataBaseHandler db;
    public static ArrayList<Messege> Messeges;

    private EditText editText;
    private FloatingActionButton fab,fab_action_bar,fab_down_scroll;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        final float PX_CONSTANT = ((float)getApplicationContext().getResources().getDisplayMetrics().densityDpi/ DisplayMetrics.DENSITY_DEFAULT);
        final Bitmap bmp = BitmapFactory.decodeResource(getResources() , R.drawable.chat_bg_img_5);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.my_action_bar);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bar_bg));  // setting bg of action bar.

        constraintLayout = findViewById(R.id.activity_main_layout);
        fab_down_scroll = findViewById(R.id.fab_scroll_to_down_id);
        fab_down_scroll.hide();
        fab_down_scroll.setOnClickListener(this);

        fab_action_bar = findViewById(R.id.fab_my_icon_id);
        fab_action_bar.setOnClickListener(this);
        editText = findViewById(R.id.msg_box_id);
        fab = findViewById(R.id.send_btn_id);
        fab.setOnClickListener(this);
        //editText.setOnClickListener(this);


        db = new DataBaseHandler(this);

        Messeges = db.getAllMesseges();

        RCV = findViewById(R.id.RCV);
        RCV.setLayoutManager(new LinearLayoutManager(this));
        messegeAdapter = new MessegeAdapter(Messeges);
        RCV.setAdapter(messegeAdapter);

        RCV.scrollToPosition(Messeges.size()-1);

        RCV.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                if(bottom<oldBottom)
                {
                    //bottom =1000
                    //height =1920

                    double h =((float)bmp.getHeight()/(float)oldBottom)*(float)bottom;
                    float px_of_editText = 60 * PX_CONSTANT;
                    h+=px_of_editText;
                    Bitmap Bmp = Bitmap.createBitmap(bmp , 0,0,bmp.getWidth(),(int)h);
                    constraintLayout.setBackground(new BitmapDrawable(getResources() , Bmp));

                    RCV.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(Messeges.size()>0){RCV.smoothScrollToPosition(Messeges.size()-1);}
                            else {RCV.scrollToPosition(Messeges.size()-1);}

                        }
                    },100);

                }
                else if (bottom>oldBottom)
                {
                    constraintLayout.setBackgroundResource(R.drawable.chat_bg_img_5);
                }

            }
        });

        RCV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //This is invoked when Scrolled .
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //This is invoked when Scrolling .

                if(RCV.canScrollVertically(1))
                {
                    fab_down_scroll.show();
                }
                else
                {
                    fab_down_scroll.hide();
                }

            }
        });

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == fab.getId())
        {
            String msg = editText.getText().toString();
            if(msg.equals("")){return;}
            String time = String.format("%02d:%02d",Calendar.getInstance().get(Calendar.HOUR_OF_DAY) , Calendar.getInstance().get(Calendar.MINUTE) );
            final Messege NewMsg = new Messege(msg ,"Me" , time);   //Creating New Messege Object.
            Messeges.add(NewMsg);  //Adding Messege to the ArrayList.
            db.addMessege(NewMsg); //Adding Messenge to DataBase.
            editText.setText("");

            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AnswerManager.sendAnswer(NewMsg); // Recieving for Answer.
                }
            } ,500);

            //AnswerManager.sendAnswer(NewMsg); // Recieving for Answer.

            messegeAdapter.notifyItemInserted(Messeges.size()-1);
            RCV.scrollToPosition(Messeges.size()-1);
        }

        else if(v.getId() == editText.getId())
        {
            RCV.scrollToPosition(Messeges.size()-1);
        }

        else if(v.getId() == fab_action_bar.getId())
        {
            Toast.makeText(mainActivity , "Rishitosh Anand",Toast.LENGTH_LONG).show();
        }

        else if(v.getId() == fab_down_scroll.getId())
        {
            RCV.scrollToPosition(Messeges.size()-1);
        }
    }



}
