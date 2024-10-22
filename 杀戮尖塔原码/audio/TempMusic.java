// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TempMusic.java

package com.megacrit.cardcrawl.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.audio:
//            MainMusic

public class TempMusic
{

    public TempMusic(String key, boolean isFast)
    {
        this(key, isFast, true);
    }

    public TempMusic(String key, boolean isFast, boolean loop)
    {
        isSilenced = false;
        silenceTimer = 0.0F;
        silenceTime = 0.0F;
        isFadingOut = false;
        isDone = false;
        this.key = key;
        music = getSong(key);
        if(isFast)
        {
            fadeTimer = 0.25F;
            fadeTime = 0.25F;
        } else
        {
            fadeTimer = 4F;
            fadeTime = 4F;
        }
        music.setLooping(loop);
        music.play();
        music.setVolume(0.0F);
    }

    public TempMusic(String key, boolean isFast, boolean loop, boolean precache)
    {
        isSilenced = false;
        silenceTimer = 0.0F;
        silenceTime = 0.0F;
        isFadingOut = false;
        isDone = false;
        this.key = key;
        music = getSong(key);
        if(isFast)
        {
            fadeTimer = 0.25F;
            fadeTime = 0.25F;
        } else
        {
            fadeTimer = 4F;
            fadeTime = 4F;
        }
        music.setLooping(loop);
        music.setVolume(0.0F);
    }

    public void playPrecached()
    {
        if(!music.isPlaying())
            music.play();
        else
            logger.info("[WARNING] Attempted to play music that is already playing.");
    }

    private Music getSong(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 2544374: 
            if(s.equals("SHOP"))
                byte0 = 0;
            break;

        case -1849738749: 
            if(s.equals("SHRINE"))
                byte0 = 1;
            break;

        case -843017007: 
            if(s.equals("MINDBLOOM"))
                byte0 = 2;
            break;

        case -1671294147: 
            if(s.equals("BOSS_BOTTOM"))
                byte0 = 3;
            break;

        case 29569789: 
            if(s.equals("BOSS_CITY"))
                byte0 = 4;
            break;

        case -1680385247: 
            if(s.equals("BOSS_BEYOND"))
                byte0 = 5;
            break;

        case -1586817479: 
            if(s.equals("BOSS_ENDING"))
                byte0 = 6;
            break;

        case 66059891: 
            if(s.equals("ELITE"))
                byte0 = 7;
            break;

        case 1746616442: 
            if(s.equals("CREDITS"))
                byte0 = 8;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return MainMusic.newMusic("audio/music/STS_Merchant_NewMix_v1.ogg");

        case 1: // '\001'
            return MainMusic.newMusic("audio/music/STS_Shrine_NewMix_v1.ogg");

        case 2: // '\002'
            return MainMusic.newMusic("audio/music/STS_Boss1MindBloom_v1.ogg");

        case 3: // '\003'
            return MainMusic.newMusic("audio/music/STS_Boss1_NewMix_v1.ogg");

        case 4: // '\004'
            return MainMusic.newMusic("audio/music/STS_Boss2_NewMix_v1.ogg");

        case 5: // '\005'
            return MainMusic.newMusic("audio/music/STS_Boss3_NewMix_v1.ogg");

        case 6: // '\006'
            return MainMusic.newMusic("audio/music/STS_Boss4_v6.ogg");

        case 7: // '\007'
            return MainMusic.newMusic("audio/music/STS_EliteBoss_NewMix_v1.ogg");

        case 8: // '\b'
            return MainMusic.newMusic("audio/music/STS_Credits_v5.ogg");
        }
        return MainMusic.newMusic((new StringBuilder()).append("audio/music/").append(key).toString());
    }

    public void fadeOut()
    {
        isFadingOut = true;
        fadeOutStartVolume = music.getVolume();
        fadeTimer = 4F;
    }

    public void silenceInstantly()
    {
        isSilenced = true;
        silenceTimer = 0.25F;
        silenceTime = 0.25F;
        silenceStartVolume = music.getVolume();
    }

    public void kill()
    {
        logger.info((new StringBuilder()).append("Disposing TempMusic: ").append(key).toString());
        music.dispose();
        isDone = true;
    }

    public void update()
    {
        if(music.isPlaying())
        {
            if(!isFadingOut)
                updateFadeIn();
            else
                updateFadeOut();
        } else
        if(isFadingOut)
            kill();
    }

    private void updateFadeIn()
    {
        if(!isSilenced)
        {
            fadeTimer -= Gdx.graphics.getDeltaTime();
            if(fadeTimer < 0.0F)
            {
                fadeTimer = 0.0F;
                if(!Settings.isBackgrounded)
                    music.setVolume(Interpolation.fade.apply(0.0F, 1.0F, 1.0F - fadeTimer / fadeTime) * (Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME));
                else
                    music.setVolume(MathHelper.slowColorLerpSnap(music.getVolume(), 0.0F));
            } else
            if(!Settings.isBackgrounded)
                music.setVolume(Interpolation.fade.apply(0.0F, 1.0F, 1.0F - fadeTimer / fadeTime) * (Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME));
            else
                music.setVolume(MathHelper.slowColorLerpSnap(music.getVolume(), 0.0F));
        } else
        {
            silenceTimer -= Gdx.graphics.getDeltaTime();
            if(silenceTimer < 0.0F)
                silenceTimer = 0.0F;
            if(!Settings.isBackgrounded)
                music.setVolume(Interpolation.fade.apply(silenceStartVolume, 0.0F, 1.0F - silenceTimer / silenceTime));
            else
                music.setVolume(MathHelper.slowColorLerpSnap(music.getVolume(), 0.0F));
        }
    }

    private void updateFadeOut()
    {
        fadeTimer -= Gdx.graphics.getDeltaTime();
        if(fadeTimer < 0.0F)
        {
            fadeTimer = 0.0F;
            isDone = true;
            logger.info((new StringBuilder()).append("Disposing TempMusic: ").append(key).toString());
            music.dispose();
        } else
        {
            music.setVolume(Interpolation.fade.apply(fadeOutStartVolume, 0.0F, 1.0F - fadeTimer / 4F));
        }
    }

    public void updateVolume()
    {
        if(!isFadingOut && !isSilenced)
            music.setVolume(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/audio/TempMusic.getName());
    private Music music;
    public String key;
    private static final String DIR = "audio/music/";
    private static final String SHOP_BGM = "STS_Merchant_NewMix_v1.ogg";
    private static final String SHRINE_BGM = "STS_Shrine_NewMix_v1.ogg";
    private static final String MINDBLOOM_BGM = "STS_Boss1MindBloom_v1.ogg";
    private static final String LEVEL_1_BOSS_BGM = "STS_Boss1_NewMix_v1.ogg";
    private static final String LEVEL_2_BOSS_BGM = "STS_Boss2_NewMix_v1.ogg";
    private static final String LEVEL_3_BOSS_BGM = "STS_Boss3_NewMix_v1.ogg";
    private static final String LEVEL_4_BOSS_BGM = "STS_Boss4_v6.ogg";
    private static final String ELITE_BGM = "STS_EliteBoss_NewMix_v1.ogg";
    private static final String CREDITS = "STS_Credits_v5.ogg";
    public boolean isSilenced;
    private float silenceTimer;
    private float silenceTime;
    private static final float FAST_SILENCE_TIME = 0.25F;
    private float silenceStartVolume;
    private static final float FADE_IN_TIME = 4F;
    private static final float FAST_FADE_IN_TIME = 0.25F;
    private static final float FADE_OUT_TIME = 4F;
    private float fadeTimer;
    private float fadeTime;
    public boolean isFadingOut;
    private float fadeOutStartVolume;
    public boolean isDone;

}
