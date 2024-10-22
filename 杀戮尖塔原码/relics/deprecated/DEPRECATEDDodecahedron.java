// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DEPRECATEDDodecahedron.java

package com.megacrit.cardcrawl.relics.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import java.util.ArrayList;

public class DEPRECATEDDodecahedron extends AbstractRelic
{

    public DEPRECATEDDodecahedron()
    {
        super("Dodecahedron", "dodecahedron.png", com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON, com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound.HEAVY);
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

    public void atBattleStart()
    {
        controlPulse();
    }

    public void onVictory()
    {
        stopPulse();
    }

    public void atTurnStart()
    {
        addToBot(new AbstractGameAction() {

            public void update()
            {
                if(isActive())
                {
                    flash();
                    addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, DEPRECATEDDodecahedron.this));
                    addToBot(new GainEnergyAction(1));
                }
                isDone = true;
            }

            final DEPRECATEDDodecahedron this$0;

            
            {
                this.this$0 = DEPRECATEDDodecahedron.this;
                super();
            }
        }
);
    }

    public int onPlayerHeal(int healAmount)
    {
        controlPulse();
        return super.onPlayerHeal(healAmount);
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if(damageAmount > 0)
            stopPulse();
        return super.onAttacked(info, damageAmount);
    }

    public AbstractRelic makeCopy()
    {
        return new DEPRECATEDDodecahedron();
    }

    private boolean isActive()
    {
        return AbstractDungeon.player.currentHealth >= AbstractDungeon.player.maxHealth;
    }

    private void controlPulse()
    {
        if(isActive())
            beginLongPulse();
        else
            stopPulse();
    }

    public static final String ID = "Dodecahedron";
    private static final int ENERGY_AMT = 1;

}
