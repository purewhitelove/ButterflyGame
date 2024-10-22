// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewRipAndTearAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

public class NewRipAndTearAction extends AttackDamageRandomEnemyAction
{

    public NewRipAndTearAction(AbstractCard card)
    {
        super(card);
    }

    public void update()
    {
        if(!Settings.FAST_MODE)
            addToTop(new WaitAction(0.1F));
        super.update();
        if(Settings.FAST_MODE)
            addToTop(new WaitAction(0.05F));
        else
            addToTop(new WaitAction(0.2F));
        if(target != null)
            addToTop(new VFXAction(new RipAndTearEffect(target.hb.cX, target.hb.cY, Color.RED, Color.GOLD)));
    }
}
