// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MusicMaster.java

package com.megacrit.cardcrawl.audio;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Prefs;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.audio:
//            MainMusic, TempMusic

public class MusicMaster
{

    public MusicMaster()
    {
        mainTrack = new ArrayList();
        tempTrack = new ArrayList();
        Settings.MASTER_VOLUME = Settings.soundPref.getFloat("Master Volume", 0.5F);
        Settings.MUSIC_VOLUME = Settings.soundPref.getFloat("Music Volume", 0.5F);
        logger.info((new StringBuilder()).append("Music Volume: ").append(Settings.MUSIC_VOLUME).toString());
    }

    public void update()
    {
        updateBGM();
        updateTempBGM();
    }

    public void updateVolume()
    {
        MainMusic m;
        for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.updateVolume())
            m = (MainMusic)iterator.next();

        TempMusic m;
        for(Iterator iterator1 = tempTrack.iterator(); iterator1.hasNext(); m.updateVolume())
            m = (TempMusic)iterator1.next();

    }

    private void updateBGM()
    {
        Iterator i = mainTrack.iterator();
        do
        {
            if(!i.hasNext())
                break;
            MainMusic e = (MainMusic)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
    }

    private void updateTempBGM()
    {
        Iterator i = tempTrack.iterator();
        do
        {
            if(!i.hasNext())
                break;
            TempMusic e = (TempMusic)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
    }

    public void fadeOutTempBGM()
    {
        Iterator iterator = tempTrack.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            TempMusic m = (TempMusic)iterator.next();
            if(!m.isFadingOut)
                m.fadeOut();
        } while(true);
        MainMusic m;
        for(Iterator iterator1 = mainTrack.iterator(); iterator1.hasNext(); m.unsilence())
            m = (MainMusic)iterator1.next();

    }

    public void justFadeOutTempBGM()
    {
        Iterator iterator = tempTrack.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            TempMusic m = (TempMusic)iterator.next();
            if(!m.isFadingOut)
                m.fadeOut();
        } while(true);
    }

    public void playTempBGM(String key)
    {
        if(key != null)
        {
            logger.info((new StringBuilder()).append("Playing ").append(key).toString());
            tempTrack.add(new TempMusic(key, false));
            MainMusic m;
            for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.silence())
                m = (MainMusic)iterator.next();

        }
    }

    public void playTempBgmInstantly(String key)
    {
        if(key != null)
        {
            logger.info((new StringBuilder()).append("Playing ").append(key).toString());
            tempTrack.add(new TempMusic(key, true));
            MainMusic m;
            for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.silenceInstantly())
                m = (MainMusic)iterator.next();

        }
    }

    public void precacheTempBgm(String key)
    {
        if(key != null)
        {
            logger.info((new StringBuilder()).append("Pre-caching ").append(key).toString());
            tempTrack.add(new TempMusic(key, true, true, true));
        }
    }

    public void playPrecachedTempBgm()
    {
        if(!tempTrack.isEmpty())
        {
            ((TempMusic)tempTrack.get(0)).playPrecached();
            MainMusic m;
            for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.silenceInstantly())
                m = (MainMusic)iterator.next();

        }
    }

    public void playTempBgmInstantly(String key, boolean loop)
    {
        if(key != null)
        {
            logger.info((new StringBuilder()).append("Playing ").append(key).toString());
            tempTrack.add(new TempMusic(key, true, loop));
            MainMusic m;
            for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.silenceInstantly())
                m = (MainMusic)iterator.next();

        }
    }

    public void changeBGM(String key)
    {
        mainTrack.add(new MainMusic(key));
    }

    public void fadeOutBGM()
    {
        Iterator iterator = mainTrack.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            MainMusic m = (MainMusic)iterator.next();
            if(!m.isFadingOut)
                m.fadeOut();
        } while(true);
    }

    public void silenceBGM()
    {
        MainMusic m;
        for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.silence())
            m = (MainMusic)iterator.next();

    }

    public void silenceBGMInstantly()
    {
        MainMusic m;
        for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.silenceInstantly())
            m = (MainMusic)iterator.next();

    }

    public void unsilenceBGM()
    {
        MainMusic m;
        for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.unsilence())
            m = (MainMusic)iterator.next();

    }

    public void silenceTempBgmInstantly()
    {
        for(Iterator iterator = tempTrack.iterator(); iterator.hasNext();)
        {
            TempMusic m = (TempMusic)iterator.next();
            m.silenceInstantly();
            m.isFadingOut = true;
        }

    }

    public void dispose()
    {
        MainMusic m;
        for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.kill())
            m = (MainMusic)iterator.next();

        TempMusic m;
        for(Iterator iterator1 = tempTrack.iterator(); iterator1.hasNext(); m.kill())
            m = (TempMusic)iterator1.next();

    }

    public void fadeAll()
    {
        MainMusic m;
        for(Iterator iterator = mainTrack.iterator(); iterator.hasNext(); m.fadeOut())
            m = (MainMusic)iterator.next();

        TempMusic m;
        for(Iterator iterator1 = tempTrack.iterator(); iterator1.hasNext(); m.fadeOut())
            m = (TempMusic)iterator1.next();

    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/audio/MusicMaster.getName());
    private ArrayList mainTrack;
    private ArrayList tempTrack;

}
