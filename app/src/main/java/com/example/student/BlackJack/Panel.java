package com.example.student.BlackJack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class Panel extends SurfaceView implements SurfaceHolder.Callback{
    Paint paint;
    private CanvasThread canvasthread;
    CardDeck cardDeck;
    int localscore;
    public Panel(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
        canvasthread = new CanvasThread(getHolder(), this);
        setFocusable(true);
       paint = new Paint();
       cardDeck = new CardDeck(context);
    }
    public Panel(Context context) {
        super(context);
        getHolder().addCallback(this);
        canvasthread = new CanvasThread(getHolder(), this);
        setFocusable(true);
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if(GetterSetter.startHand==0){
        }else{
            for (int q = 0; q <=1; q++) {
                if(q==0&& GetterSetter.dealerhit<3){
                    cardDeck.deal(canvas,999,(80*q),-600);
                }else{
                    cardDeck.deal(canvas,q,(80*q),-600);
                }
                if(GetterSetter.buttonpressed==1){
                    setScore(q,true,false);
                }
            }
            for (int q = 2; q <= GetterSetter.playerhit; q++) {
                if(q==GetterSetter.playerhit){
                    if(GetterSetter.fly<81){
                        GetterSetter.fly=GetterSetter.fly+4;
                        GetterSetter.vertfly=GetterSetter.vertfly-20;
                    }
                    if(GetterSetter.vertfly<0){
                        GetterSetter.vertfly=0;
                    }
                    cardDeck.deal(canvas,q,(GetterSetter.fly*q),GetterSetter.vertfly);
                }else{
                    cardDeck.deal(canvas,q,(80*q),0);
                }
                if(GetterSetter.buttonpressed==1){
                    setScore(q,false,true);
                }
            }
            for (int x = (GetterSetter.playerhit +1); x <= GetterSetter.dealerhit; x++) {
                if(x>2){
                    cardDeck.deal(canvas,x,(80*(x-(GetterSetter.playerhit -1))),-600);
                }else{
                    cardDeck.deal(canvas,x,(80*x),-600);
                }
                if(GetterSetter.buttonpressed==1){
                    setScore(x,true,false);
                }
            }
            GetterSetter.buttonpressed=0;
        }
    }
    public void setScore(int number, boolean dealer, boolean player){
        if(number==0&&GetterSetter.dealerhit<3){
            localscore=0;
        }else{
            if(GetterSetter.card[number].rank>=8 &&GetterSetter.card[number].rank<12){
                localscore=10;
            }
            if (GetterSetter.card[number].rank==12){
                if(player){
                    if(GetterSetter.playerScore>10){
                        localscore=1;
                    }else{
                        localscore=11;
                    }
                }
                if(dealer){
                    if(GetterSetter.dealerScore>10){
                        localscore=1;
                    }else{
                        localscore=11;
                    }
                }
            }
            if(GetterSetter.card[number].rank<=8){
                localscore=GetterSetter.card[number].rank+2;
            }
            if(player){
                GetterSetter.playerScore=GetterSetter.playerScore+localscore;
            }
            if(dealer){
                GetterSetter.dealerScore=GetterSetter.dealerScore+localscore;
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    public void surfaceCreated(SurfaceHolder holder) {
        canvasthread.setRunning(true);
        canvasthread.start();
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        canvasthread.setRunning(false);
        while (retry) {
            try {
                canvasthread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }
}