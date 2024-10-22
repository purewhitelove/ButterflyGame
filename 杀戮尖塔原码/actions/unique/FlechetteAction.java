// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlechetteAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class FlechetteAction extends AbstractGameAction
{

    public FlechetteAction(AbstractCreature target, DamageInfo info)
    {
        duration = Settings.ACTION_DUR_XFAST;
        this.info = info;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
        this.target = target;
    }

    public void update()
    {
        Iterator iterator = AbstractDungeon.player.hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
            {
                addToTop(new DamageAction(target, info, true));
                if(target != null && target.hb != null)
                    addToTop(new VFXAction(new ThrowDaggerEffect(target.hb.cX, target.hb.cY)));
            }
        } while(true);
        isDone = true;
    }

    private DamageInfo info;
}
