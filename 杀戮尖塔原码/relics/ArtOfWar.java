// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ArtOfWar.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class ArtOfWar extends AbstractRelic
{

    public ArtOfWar()
    {
        super("Art of War", "artOfWar.png", AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
        gainEnergyNext = false;
        firstTurn = false;
        pulse = false;
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
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(DESCRIPTIONS[1]).toString();
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass c)
    {
        description = setDescription(c);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void atPreBattle()
    {
        flash();
        firstTurn = true;
        gainEnergyNext = true;
        if(!pulse)
        {
            beginPulse();
            pulse = true;
        }
    }

    public void atTurnStart()
    {
        beginPulse();
        pulse = true;
        if(gainEnergyNext && !firstTurn)
        {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new GainEnergyAction(1));
        }
        firstTurn = false;
        gainEnergyNext = true;
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if(card.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
        {
            gainEnergyNext = false;
            pulse = false;
        }
    }

    public void onVictory()
    {
        pulse = false;
    }

    public AbstractRelic makeCopy()
    {
        return new ArtOfWar();
    }

    public static final String ID = "Art of War";
    private boolean gainEnergyNext;
    private boolean firstTurn;
}
