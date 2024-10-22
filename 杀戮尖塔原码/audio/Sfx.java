// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Sfx.java

package com.megacrit.cardcrawl.audio;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sfx
{

    public Sfx(String url, boolean preload)
    {
        if(preload)
            sound = initSound(Gdx.files.internal(url));
        else
            this.url = url;
    }

    public Sfx(String url)
    {
        this(url, false);
    }

    public long play(float volume)
    {
        sound = initSound(Gdx.files.internal(url));
        if(sound != null)
            return sound.play(volume);
        else
            return 0L;
    }

    public long play(float volume, float y, float z)
    {
        sound = initSound(Gdx.files.internal(url));
        if(sound != null)
            return sound.play(volume, y, z);
        else
            return 0L;
    }

    public long loop(float volume)
    {
        sound = initSound(Gdx.files.internal(url));
        if(sound != null)
            return sound.loop(volume);
        else
            return 0L;
    }

    public void setVolume(long id, float volume)
    {
        if(sound != null)
            sound.setVolume(id, volume);
    }

    public void stop()
    {
        logger.info("stopping");
        if(sound != null)
            sound.stop();
    }

    public void stop(long id)
    {
        if(sound != null)
            sound.stop(id);
    }

    private Sound initSound(FileHandle file)
    {
        if(sound == null)
        {
            if(file != null)
            {
                if(Gdx.audio != null)
                {
                    return Gdx.audio.newSound(file);
                } else
                {
                    logger.info("WARNING: Gdx.audio is null");
                    return null;
                }
            } else
            {
                logger.info((new StringBuilder()).append("File: ").append(url).append(" was not found.").toString());
                return null;
            }
        } else
        {
            return sound;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/audio/Sfx.getName());
    private String url;
    private Sound sound;

}
