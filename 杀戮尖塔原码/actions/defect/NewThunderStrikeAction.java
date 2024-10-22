// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NewThunderStrikeAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class NewThunderStrikeAction extends AttackDamageRandomEnemyAction
{

    public NewThunderStrikeAction(AbstractCard card)
    {
        super(card);
    }

    public void update()
    {
        if(!Settings.FAST_MODE)
            addToTop(new WaitAction(0.1F));
        super.update();
        if(target != null)
        {
            addToTop(new VFXAction(new LightningEffect(target.drawX, target.drawY)));
            addToTop(new VFXAction(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect)));
            addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
        }
    }
}
