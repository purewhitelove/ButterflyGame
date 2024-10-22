// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Shovel.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.DigOption;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic, PeacePipe, Girya

public class Shovel extends AbstractRelic
{

    public Shovel()
    {
        super("Shovel", "shovel.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
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
        options.add(new DigOption());
    }

    public AbstractRelic makeCopy()
    {
        return new Shovel();
    }

    public static final String ID = "Shovel";
}
