// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FollowUpAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;
import java.util.ArrayList;

public class FollowUpAction extends AbstractGameAction
{

    public FollowUpAction()
    {
    }

    public void update()
    {
        if(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
        {
            addToTop(new GainEnergyAction(1));
            if(Settings.FAST_MODE)
                addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.0F));
            else
                addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.3F));
        }
        isDone = true;
    }
}
