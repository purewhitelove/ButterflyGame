// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MainMusic.java

package com.megacrit.cardcrawl.audio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.random.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.audio:
//            MockMusic

public class MainMusic
{

    public MainMusic(String key)
    {
        isSilenced = false;
        silenceTimer = 0.0F;
        silenceTime = 0.0F;
        fadeTimer = 0.0F;
        isFadingOut = false;
        isDone = false;
        this.key = key;
        music = getSong(key);
        fadeTimer = 4F;
        music.setLooping(true);
        music.play();
        music.setVolume(0.0F);
    }

    private Music getSong(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1887678253: 
            if(s.equals("Exordium"))
                byte0 = 0;
            break;

        case 313705820: 
            if(s.equals("TheCity"))
                byte0 = 1;
            break;

        case 791401920: 
            if(s.equals("TheBeyond"))
                byte0 = 2;
            break;

        case 884969688: 
            if(s.equals("TheEnding"))
                byte0 = 3;
            break;

        case 2362719: 
            if(s.equals("MENU"))
                byte0 = 4;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            switch(AbstractDungeon.miscRng.random(1))
            {
            case 0: // '\0'
                return newMusic("audio/music/STS_Level1_NewMix_v1.ogg");
            }
            return newMusic("audio/music/STS_Level1-2_v2.ogg");

        case 1: // '\001'
            switch(AbstractDungeon.miscRng.random(1))
            {
            case 0: // '\0'
                return newMusic("audio/music/STS_Level2_NewMix_v1.ogg");
            }
            return newMusic("audio/music/STS_Level2-2_v2.ogg");

        case 2: // '\002'
            switch(AbstractDungeon.miscRng.random(1))
            {
            case 0: // '\0'
                return newMusic("audio/music/STS_Level3_v2.ogg");
            }
            return newMusic("audio/music/STS_Level3-2_v2.ogg");

        case 3: // '\003'
            return newMusic("audio/music/STS_Act4_BGM_v2.ogg");

        case 4: // '\004'
            return newMusic("audio/music/STS_MenuTheme_NewMix_v1.ogg");
        }
        logger.info((new StringBuilder()).append("NO SUCH MAIN BGM (playing level_1 instead): ").append(key).toString());
        return newMusic("audio/music/STS_Level1_NewMix_v1.ogg");
    }

    public static Music newMusic(String path)
    {
        if(Gdx.audio == null)
        {
            logger.info("WARNING: Gdx.audio is null so no Music instance can be initialized.");
            return new MockMusic();
        } else
        {
            return Gdx.audio.newMusic(Gdx.files.internal(path));
        }
    }

    public void updateVolume()
    {
        if(!isFadingOut && !isSilenced)
            music.setVolume(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME);
    }

    public void fadeOut()
    {
        isFadingOut = true;
        fadeOutStartVolume = music.getVolume();
        fadeTimer = 4F;
    }

    public void silence()
    {
        isSilenced = true;
        silenceTimer = 4F;
        silenceTime = 4F;
        silenceStartVolume = music.getVolume();
    }

    public void silenceInstantly()
    {
        isSilenced = true;
        silenceTimer = 0.25F;
        silenceTime = 0.25F;
        silenceStartVolume = music.getVolume();
    }

    public void unsilence()
    {
        if(isSilenced)
        {
            logger.info((new StringBuilder()).append("Unsilencing ").append(key).toString());
            isSilenced = false;
            fadeTimer = 4F;
        }
    }

    public void kill()
    {
        music.dispose();
        isDone = true;
    }

    public void update()
    {
        if(!isFadingOut)
            updateFadeIn();
        else
            updateFadeOut();
    }

    private void updateFadeIn()
    {
        if(!isSilenced)
        {
            fadeTimer -= Gdx.graphics.getDeltaTime();
            if(fadeTimer < 0.0F)
                fadeTimer = 0.0F;
            if(!Settings.isBackgrounded)
                music.setVolume(Interpolation.fade.apply(0.0F, 1.0F, 1.0F - fadeTimer / 4F) * (Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME));
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
        if(!isSilenced)
        {
            fadeTimer -= Gdx.graphics.getDeltaTime();
            if(fadeTimer < 0.0F)
            {
                fadeTimer = 0.0F;
                isDone = true;
                logger.info((new StringBuilder()).append("Disposing MainMusic: ").append(key).toString());
                music.dispose();
            } else
            {
                music.setVolume(Interpolation.fade.apply(fadeOutStartVolume, 0.0F, 1.0F - fadeTimer / 4F));
            }
        } else
        {
            silenceTimer -= Gdx.graphics.getDeltaTime();
            if(silenceTimer < 0.0F)
                silenceTimer = 0.0F;
            music.setVolume(Interpolation.fade.apply(silenceStartVolume, 0.0F, 1.0F - silenceTimer / silenceTime));
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/audio/MainMusic.getName());
    private Music music;
    public String key;
    private static final String DIR = "audio/music/";
    private static final String TITLE_BGM = "STS_MenuTheme_NewMix_v1.ogg";
    private static final String LEVEL_1_1_BGM = "STS_Level1_NewMix_v1.ogg";
    private static final String LEVEL_1_2_BGM = "STS_Level1-2_v2.ogg";
    private static final String LEVEL_2_1_BGM = "STS_Level2_NewMix_v1.ogg";
    private static final String LEVEL_2_2_BGM = "STS_Level2-2_v2.ogg";
    private static final String LEVEL_3_1_BGM = "STS_Level3_v2.ogg";
    private static final String LEVEL_3_2_BGM = "STS_Level3-2_v2.ogg";
    private static final String LEVEL_4_1_BGM = "STS_Act4_BGM_v2.ogg";
    public boolean isSilenced;
    private float silenceTimer;
    private float silenceTime;
    private static final float SILENCE_TIME = 4F;
    private static final float FAST_SILENCE_TIME = 0.25F;
    private float silenceStartVolume;
    private static final float FADE_IN_TIME = 4F;
    private static final float FADE_OUT_TIME = 4F;
    private float fadeTimer;
    public boolean isFadingOut;
    private float fadeOutStartVolume;
    public boolean isDone;

}
