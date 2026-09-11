package com.kaoru.yalive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class YaWallpaperService extends WallpaperService {
    static final String PREF="ya_state"; static final String ACTION="com.kaoru.yalive.INTERACT";
    static void signalInteraction(Context c,String where){ c.sendBroadcast(new Intent(ACTION).setPackage(c.getPackageName()).putExtra("where",where)); }
    @Override public Engine onCreateEngine(){ return new YaEngine(); }

    class YaEngine extends Engine {
        Bitmap bmp; Paint p=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG); long start,lastTouch; float mood=70f, lonely=20f; float phase=0;
        SharedPreferences sp; boolean visible=false; Runnable tick=this::drawFrame;
        BroadcastReceiver receiver=new BroadcastReceiver(){ public void onReceive(Context c,Intent i){ if(ACTION.equals(i.getAction())) interact("widget"); }};
        YaEngine(){ bmp=BitmapFactory.decodeResource(getResources(),R.drawable.yaa_front); sp=getSharedPreferences(PREF,MODE_PRIVATE); mood=sp.getFloat("mood",70); lonely=sp.getFloat("lonely",20); start=System.currentTimeMillis(); }
        @Override public void onVisibilityChanged(boolean v){ visible=v; if(v){ register(); drawFrame(); } else unregister(); }
        void register(){ try{registerReceiver(receiver,new IntentFilter(ACTION), Context.RECEIVER_NOT_EXPORTED);}catch(Exception ignored){} }
        void unregister(){ try{unregisterReceiver(receiver);}catch(Exception ignored){} }
        @Override public void onSurfaceDestroyed(SurfaceHolder h){ visible=false; unregister(); super.onSurfaceDestroyed(h); }
        @Override public void onTouchEvent(MotionEvent e){ if(e.getAction()!=MotionEvent.ACTION_UP)return; float nx=e.getX()/Math.max(1,getSurfaceHolder().getSurfaceFrame().width()); float ny=e.getY()/Math.max(1,getSurfaceHolder().getSurfaceFrame().height());
            if(ny<0.38f){ mood=Math.min(100,mood+7); lonely=Math.max(0,lonely-12); lastTouch=System.currentTimeMillis(); }
            else { mood=Math.min(100,mood+3); lonely=Math.max(0,lonely-5); lastTouch=System.currentTimeMillis(); }
            sp.edit().putFloat("mood",mood).putFloat("lonely",lonely).apply(); drawFrame(); }
        void interact(String where){ mood=Math.min(100,mood+(where.equals("widget")?5:3)); lonely=Math.max(0,lonely-8); lastTouch=System.currentTimeMillis(); sp.edit().putFloat("mood",mood).putFloat("lonely",lonely).apply(); drawFrame(); }
        void drawFrame(){ if(!visible)return; long now=System.currentTimeMillis(); float dt=(now-start)/1000f; phase=dt*1.7f; long idle=lastTouch==0?Long.MAX_VALUE:now-lastTouch; if(idle>0 && idle<Long.MAX_VALUE){ float mins=idle/60000f; mood=Math.max(0,mood-mins*0.002f); lonely=Math.min(100,lonely+mins*0.004f); }
            SurfaceHolder h=getSurfaceHolder(); Canvas c=null; try{c=h.lockCanvas(); if(c==null)return; c.drawColor(0x00000000); float sw=c.getWidth(), sh=c.getHeight();
                float baseW=Math.min(sw*0.72f, sh*0.68f); float scale=baseW/bmp.getWidth(); float bob=(float)Math.sin(phase)*2.5f; float sway=(float)Math.sin(phase*0.63f)*0.018f; float cx=sw*0.50f; float top=sh*0.16f;
                // Very low mood: turn the character away smoothly (prototype uses a 2.5D rotation illusion).
                float away = mood<10 ? Math.min(1f,(10f-mood)/10f) : 0f;
                p.setAlpha(255); Matrix m=new Matrix(); m.postTranslate(-bmp.getWidth()/2f,-bmp.getHeight()/2f); m.postScale(scale*(1f-0.12f*away),scale); m.postRotate(sway*18f); m.postTranslate(cx, top+bob);
                c.drawBitmap(bmp,m,p);
                // Mood indicator kept subtle for testing; remove in production.
                p.setTextSize(Math.max(12,sw*0.028f)); p.setAlpha(170); p.setColor(0xFFFFFFFF); c.drawText("やぁ  mood:"+Math.round(mood), 12, sh-24, p);
            } finally { if(c!=null)h.unlockCanvasAndPost(c); } if(visible) getHandler().postDelayed(tick,16); }
        android.os.Handler getHandler(){ return handler; } final android.os.Handler handler=new android.os.Handler(android.os.Looper.getMainLooper());
    }
}
