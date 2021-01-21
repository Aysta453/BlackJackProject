package com.example.student.BlackJack;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivityFragment extends Fragment {
    View rootView;
    Card[] card;
    TextView tv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    int z =0;
    Handler mainHandler;
    SeekBar seek;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        seek=(SeekBar) rootView.findViewById(R.id.seekBar);
        seek.setMax(GetterSetter.cash);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int progressChanged=0;
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser){
                GetterSetter.bet=progress; }
            public void onStartTrackingTouch(SeekBar seekBar){
                seek.setMax(GetterSetter.cash);
            }
            public void onStopTrackingTouch(SeekBar seekBar){ }
        });
        
        rootView.setBackgroundColor(Color.BLACK);

        tv = (TextView) rootView.findViewById(R.id.textViewPlayer);
        tv.setTextColor(Color.RED);
        tv.setTextSize(20);

        tv1 = (TextView) rootView.findViewById(R.id.textViewDealer);
        tv1.setTextColor(Color.RED);
        tv1.setTextSize(20);

        tv2 = (TextView) rootView.findViewById(R.id.textViewCash);
        tv2.setTextColor(Color.RED);
        tv2.setTextSize(20);

        tv3 = (TextView) rootView.findViewById(R.id.textViewBet);
        tv3.setTextColor(Color.RED);
        tv3.setTextSize(20);


        card=new Card[52];
        for (int suit = 0; suit < 4; suit++) {
            for (int rank = 0; rank < 13; rank++) {
                card[z]=new Card(suit,rank);
                z++;
            }
        }
        card= shuffleCards(card);
        GetterSetter.card=card;

        mainHandler =new Handler();
        mainHandler.post(mainRunnable);

        return rootView;
    }
    private Runnable mainRunnable = new Runnable() {
        @Override
        public void run() {
            if(GetterSetter.startHand==0){
                tv2.setText("Cash: "+(GetterSetter.cash-GetterSetter.bet));
                tv3.setText("Place Bet: "+GetterSetter.bet);
            }else{
                tv3.setText("Placed Bet: "+GetterSetter.bet);
                if(GetterSetter.playerScore<=21){
                    tv.setText("Player: "+GetterSetter.playerScore);
                    tv1.setText("Dealer: "+GetterSetter.dealerScore);
                    tv2.setText("Cash: "+(GetterSetter.cash-GetterSetter.bet));
                }
                if(GetterSetter.playerScore!=0&&GetterSetter.playerScore>21){
                    tv.setText("Bust");
                    removeCash();
                    GetterSetter.isStanding=true;
                    seek.setProgress(0);
                    GetterSetter.bet=0;
                }
                if(GetterSetter.buttonpressed==0){
                    if(GetterSetter.dealerhit>1){
                        if(GetterSetter.dealerScore<17 && GetterSetter.dealerScore!=0){
                            GetterSetter.playerScore=0;
                            GetterSetter.dealerScore=0;
                            GetterSetter.dealerhit++;
                            GetterSetter.buttonpressed=1;
                        }
                        if(GetterSetter.dealerScore>=17){
                            checkWin();
                        }
                    }
                }
            }
            mainHandler.postDelayed(this,1);

        }
    };
    public void checkWin(){
        if((GetterSetter.playerScore>GetterSetter.dealerScore) ||GetterSetter.dealerScore>21){
                GetterSetter.cash=GetterSetter.cash-(GetterSetter.bet)+(GetterSetter.bet*2);
                seek.setProgress(0);
                GetterSetter.bet=0;
                tv.setText("Player Win "+GetterSetter.playerScore);
                tv1.setText("Dealer Lost "+GetterSetter.dealerScore);

        }else{
            tv1.setText("Dealer Win "+GetterSetter.dealerScore);
            tv.setText("Player Lost "+GetterSetter.playerScore);
            removeCash();
            seek.setProgress(0);
            GetterSetter.bet=0;
        }
    }
    public void removeCash(){
        GetterSetter.cash=GetterSetter.cash-GetterSetter.bet;
        seek.setProgress(0);
        GetterSetter.bet=0;

    }
    public Card[] shuffleCards(Card[] deck){
        Random random=new Random();
        Card temp=new Card(0,0);
        for (int i = 0; i < 52; i++) {
            int randomCard=random.nextInt(52);
            temp=deck[randomCard];
            deck[randomCard]=deck[i];
            deck[i]=temp;
        }
        return deck;
    }
}
