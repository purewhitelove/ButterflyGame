// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PeacePipe.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.TokeOption;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic, Shovel, Girya

public class PeacePipe extends AbstractRelic
{

    public PeacePipe()
    {
        super("Peace Pipe", "peacePipe.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public boolean canSpawn()
    {
        if(AbstractDungeon.floorNum >= 48 && !Settings.isEndless)
            return false;
        int campfireRelicCount = 0;
        Iterator iterator = AbstractDungeon.player.relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if((r instanceof PeacePipe) || (r instanceof Shovel) || (r instanceof Girya))
                campfireRelicCount++;
        } while(true);
        return campfireRelicCount < 2;
    }

    public void addCampfireOption(ArrayList options)
    {
        options.add(new TokeOption(!CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).isEmpty()));
    }

    public AbstractRelic makeCopy()
    {
        return new PeacePipe();
    }

    public static final String ID = "Peace Pipe";
}
