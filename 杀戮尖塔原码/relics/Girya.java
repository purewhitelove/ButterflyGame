// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Girya.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.campfire.LiftOption;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic, PeacePipe, Shovel

public class Girya extends AbstractRelic
{

    public Girya()
    {
        super("Girya", "kettlebell.png", AbstractRelic.RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
        counter = 0;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        if(counter != 0)
        {
            flash();
            addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, counter), counter));
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
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
        options.add(new LiftOption(counter < 3));
    }

    public AbstractRelic makeCopy()
    {
        return new Girya();
    }

    public static final String ID = "Girya";
    public static final int STR_LIMIT = 3;
}
