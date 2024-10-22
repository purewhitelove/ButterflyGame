// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SoulGroup.java

package com.megacrit.cardcrawl.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.cards:
//            Soul, AbstractCard

public class SoulGroup
{

    public SoulGroup()
    {
        souls = new ArrayList();
        for(int i = 0; i < 20; i++)
            souls.add(new Soul());

    }

    public void discard(AbstractCard card, boolean visualOnly)
    {
        boolean needMoreSouls = true;
        Iterator iterator = souls.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                continue;
            card.untip();
            card.unhover();
            s.discard(card, visualOnly);
            needMoreSouls = false;
            break;
        } while(true);
        if(needMoreSouls)
        {
            logger.info("Not enough souls, creating...");
            Soul s = new Soul();
            s.discard(card, visualOnly);
            souls.add(s);
        }
    }

    public void discard(AbstractCard card)
    {
        discard(card, false);
    }

    public void empower(AbstractCard card)
    {
        boolean needMoreSouls = true;
        Iterator iterator = souls.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                continue;
            card.untip();
            card.unhover();
            s.empower(card);
            needMoreSouls = false;
            break;
        } while(true);
        if(needMoreSouls)
        {
            logger.info("Not enough souls, creating...");
            Soul s = new Soul();
            s.empower(card);
            souls.add(s);
        }
    }

    public void obtain(AbstractCard card, boolean obtainCard)
    {
        CardCrawlGame.sound.play("CARD_OBTAIN");
        boolean needMoreSouls = true;
        Iterator iterator = souls.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                continue;
            if(obtainCard)
                s.obtain(card);
            needMoreSouls = false;
            break;
        } while(true);
        if(needMoreSouls)
        {
            logger.info("Not enough souls, creating...");
            Soul s = new Soul();
            if(obtainCard)
                s.obtain(card);
            souls.add(s);
        }
    }

    public void shuffle(AbstractCard card, boolean isInvisible)
    {
        card.untip();
        card.unhover();
        card.darken(true);
        card.shrink(true);
        boolean needMoreSouls = true;
        Iterator iterator = souls.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                continue;
            s.shuffle(card, isInvisible);
            needMoreSouls = false;
            break;
        } while(true);
        if(needMoreSouls)
        {
            logger.info("Not enough souls, creating...");
            Soul s = new Soul();
            s.shuffle(card, isInvisible);
            souls.add(s);
        }
    }

    public void onToBottomOfDeck(AbstractCard card)
    {
        boolean needMoreSouls = true;
        Iterator iterator = souls.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                continue;
            card.untip();
            card.unhover();
            s.onToBottomOfDeck(card);
            needMoreSouls = false;
            break;
        } while(true);
        if(needMoreSouls)
        {
            logger.info("Not enough souls, creating...");
            Soul s = new Soul();
            s.onToBottomOfDeck(card);
            souls.add(s);
        }
    }

    public void onToDeck(AbstractCard card, boolean randomSpot, boolean visualOnly)
    {
        boolean needMoreSouls = true;
        Iterator iterator = souls.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                continue;
            card.untip();
            card.unhover();
            s.onToDeck(card, randomSpot, visualOnly);
            needMoreSouls = false;
            break;
        } while(true);
        if(needMoreSouls)
        {
            logger.info("Not enough souls, creating...");
            Soul s = new Soul();
            s.onToDeck(card, randomSpot, visualOnly);
            souls.add(s);
        }
    }

    public void onToDeck(AbstractCard card, boolean randomSpot)
    {
        onToDeck(card, randomSpot, false);
    }

    public void update()
    {
        Iterator iterator = souls.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                s.update();
        } while(true);
    }

    public void render(SpriteBatch sb)
    {
        Iterator iterator = souls.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                s.render(sb);
        } while(true);
    }

    public void remove(AbstractCard card)
    {
        Iterator s = souls.iterator();
        do
        {
            if(!s.hasNext())
                break;
            Soul derp = (Soul)s.next();
            if(derp.card != card)
                continue;
            s.remove();
            logger.info((new StringBuilder()).append(derp).append(" removed.").toString());
            break;
        } while(true);
    }

    public static boolean isActive()
    {
        for(Iterator iterator = AbstractDungeon.getCurrRoom().souls.souls.iterator(); iterator.hasNext();)
        {
            Soul s = (Soul)iterator.next();
            if(!s.isReadyForReuse)
                return true;
        }

        return false;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/cards/SoulGroup.getName());
    private ArrayList souls;
    private static final int DEFAULT_SOUL_CACHE = 20;

}
