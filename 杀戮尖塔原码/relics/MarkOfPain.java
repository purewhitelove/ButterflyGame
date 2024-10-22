// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarkOfPain.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class MarkOfPain extends AbstractRelic
{

    public MarkOfPain()
    {
        super("Mark of Pain", "mark_of_pain.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new MakeTempCardInDrawPileAction(new Wound(), 2, true, true));
    }

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster++;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster--;
    }

    public AbstractRelic makeCopy()
    {
        return new MarkOfPain();
    }

    public static final String ID = "Mark of Pain";
    private static final int CARD_AMT = 2;
}
