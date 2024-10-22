// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConjureBladeAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ConjureBladeAction extends AbstractGameAction
{

    public ConjureBladeAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse)
    {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        duration = Settings.ACTION_DUR_XFAST;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update()
    {
        int effect = EnergyPanel.totalCount;
        if(energyOnUse != -1)
            effect = energyOnUse;
        if(p.hasRelic("Chemical X"))
        {
            effect += 2;
            p.getRelic("Chemical X").flash();
        }
        Expunger c = new Expunger();
        c.setX(effect);
        addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
        if(!freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);
        isDone = true;
    }

    public int multiDamage[];
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
}
