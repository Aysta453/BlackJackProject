package com.example.student.BlackJack;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button button1;
    FragmentManager fm;
    MainActivityFragment fragment;
    TextView tv = null;
    TextView tv1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button) findViewById(R.id.button2);
        button1=(Button) findViewById(R.id.button3);
        fm=getSupportFragmentManager();
        fragment=(MainActivityFragment)fm.findFragmentById(R.id.fragment);
        tv=fragment.tv;
        tv1=fragment.tv1;
    }
    public void clickMethodHit(View view) {
        if(!GetterSetter.isStanding){
            GetterSetter.playerScore=0;
            GetterSetter.dealerScore=0;
            GetterSetter.playerhit++;
            GetterSetter.buttonpressed=1;
            GetterSetter.fly=0;
            GetterSetter.vertfly=400;
        }
    }
    public void clickMethodStand(View view) {
        GetterSetter.playerScore=0;
        GetterSetter.dealerScore=0;
        GetterSetter.dealerhit=GetterSetter.playerhit;
        GetterSetter.buttonpressed=1;
        GetterSetter.isStanding=true;
    }
    public void clickMethodReDeal(View view) {
        if(GetterSetter.startHand==1){
            GetterSetter.startHand=0;
            button.setVisibility(view.GONE);
            button1.setVisibility(view.VISIBLE);
            fragment.seek.setEnabled(true);
        }else{
            GetterSetter.playerScore=0;
            GetterSetter.dealerScore=0;
            GetterSetter.playerhit =3;
            GetterSetter.dealerhit=1;
            GetterSetter.buttonpressed=1;
            fragment.shuffleCards(GetterSetter.card);
            button1.setVisibility(view.GONE);
            button.setVisibility(view.VISIBLE);
            GetterSetter.isStanding=false;
            GetterSetter.startHand=1;
            fragment.seek.setEnabled(false);
        }
    }
}
