// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BustedCrown.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class BustedCrown extends AbstractRelic
{

    public BustedCrown()
    {
        super("Busted Crown", "crown.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        if(AbstractDungeon.player != null)
            return setDescription(AbstractDungeon.player.chosenClass);
        else
            return setDescription(null);
    }

    private String setDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        return (new StringBuilder()).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[0]).toString();
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        description = setDescription(c);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster--;
    }

    public int changeNumberOfCardsInReward(int numberOfCards)
    {
        return numberOfCards - 2;
    }

    public AbstractRelic makeCopy()
    {
        return new BustedCrown();
    }

    private static final int CARDS_SUBTRACTED = 2;
    public static final String ID = "Busted Crown";
}
