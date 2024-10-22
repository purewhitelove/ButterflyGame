// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VelvetChoker.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class VelvetChoker extends AbstractRelic
{

    public VelvetChoker()
    {
        super("Velvet Choker", "redChoker.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
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
        return (new StringBuilder()).append(DESCRIPTIONS[2]).append(DESCRIPTIONS[0]).append(6).append(DESCRIPTIONS[1]).toString();
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

    public void atBattleStart()
    {
        counter = 0;
    }

    public void atTurnStart()
    {
        counter = 0;
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m)
    {
        if(counter < 6)
        {
            counter++;
            if(counter >= 6)
                flash();
        }
    }

    public boolean canPlay(AbstractCard card)
    {
        if(counter >= 6)
        {
            card.cantUseMessage = (new StringBuilder()).append(DESCRIPTIONS[3]).append(6).append(DESCRIPTIONS[1]).toString();
            return false;
        } else
        {
            return true;
        }
    }

    public void onVictory()
    {
        counter = -1;
    }

    public AbstractRelic makeCopy()
    {
        return new VelvetChoker();
    }

    public static final String ID = "Velvet Choker";
    private static final int PLAY_LIMIT = 6;
}
