package com.kaoru.yalive;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout l=new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); l.setPadding(40,60,40,40);
        TextView t=new TextView(this); t.setText("やぁ Live Character Test\n\nホーム画面の壁紙として設定してください。\nキャラクターをタップすると反応します。\nウィジェットからも呼びかけられます。\n\nV0.1: 連続モーション / タップ反応 / 機嫌"); t.setTextSize(18); l.addView(t);
        Button btn=new Button(this); btn.setText("ライブ壁紙を設定"); btn.setOnClickListener(v -> {
            Intent i=new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            i.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(this,YaWallpaperService.class)); startActivity(i);
        }); l.addView(btn); setContentView(l);
    }
}
