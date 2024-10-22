// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TripleYourBlockAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class TripleYourBlockAction extends AbstractGameAction
{

    public TripleYourBlockAction(AbstractCreature target)
    {
        duration = 0.5F;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.BLOCK;
        this.target = target;
    }

    public void update()
    {
        if(duration == 0.5F && target != null && target.currentBlock > 0)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SHIELD));
            target.addBlock(target.currentBlock * 2);
        }
        tickDuration();
    }
}
