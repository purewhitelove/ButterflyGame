// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RestRoom.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom, CampfireUI

public class RestRoom extends AbstractRoom
{

    public RestRoom()
    {
        phase = AbstractRoom.RoomPhase.INCOMPLETE;
        mapSymbol = "R";
        mapImg = ImageMaster.MAP_NODE_REST;
        mapImgOutline = ImageMaster.MAP_NODE_REST_OUTLINE;
    }

    public void onPlayerEntry()
    {
        if(!AbstractDungeon.id.equals("TheEnding"))
            CardCrawlGame.music.silenceBGM();
        fireSoundId = CardCrawlGame.sound.playAndLoop("REST_FIRE_WET");
        lastFireSoundId = fireSoundId;
        campfireUI = new CampfireUI();
        AbstractRelic r;
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onEnterRestRoom())
            r = (AbstractRelic)iterator.next();

    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardRarity getCardRarity(int roll)
    {
        return getCardRarity(roll, false);
    }

    public void update()
    {
        super.update();
        if(campfireUI != null)
            campfireUI.update();
    }

    public void fadeIn()
    {
        if(!AbstractDungeon.id.equals("TheEnding"))
            CardCrawlGame.music.unsilenceBGM();
    }

    public void cutFireSound()
    {
        CardCrawlGame.sound.fadeOut("REST_FIRE_WET", ((RestRoom)AbstractDungeon.getCurrRoom()).fireSoundId);
    }

    public void updateAmbience()
    {
        CardCrawlGame.sound.adjustVolume("REST_FIRE_WET", fireSoundId);
    }

    public void render(SpriteBatch sb)
    {
        if(campfireUI != null)
            campfireUI.render(sb);
        super.render(sb);
    }

    public long fireSoundId;
    public static long lastFireSoundId = 0L;
    public CampfireUI campfireUI;

}
