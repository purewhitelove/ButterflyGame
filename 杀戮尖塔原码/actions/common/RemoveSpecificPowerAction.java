// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemoveSpecificPowerAction.java

package com.megacrit.cardcrawl.actions.common;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.PowerExpireTextEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class RemoveSpecificPowerAction extends AbstractGameAction
{

    public RemoveSpecificPowerAction(AbstractCreature target, AbstractCreature source, String powerToRemove)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DEBUFF;
        duration = 0.1F;
        this.powerToRemove = powerToRemove;
    }

    public RemoveSpecificPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerInstance)
    {
        setValues(target, source, amount);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DEBUFF;
        duration = 0.1F;
        this.powerInstance = powerInstance;
    }

    public void update()
    {
        if(duration == 0.1F)
        {
            if(target.isDeadOrEscaped())
            {
                isDone = true;
                return;
            }
            AbstractPower removeMe = null;
            if(powerToRemove != null)
                removeMe = target.getPower(powerToRemove);
            else
            if(powerInstance != null && target.powers.contains(powerInstance))
                removeMe = powerInstance;
            if(removeMe != null)
            {
                AbstractDungeon.effectList.add(new PowerExpireTextEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, removeMe.name, removeMe.region128));
                removeMe.onRemove();
                target.powers.remove(removeMe);
                AbstractDungeon.onModifyPower();
                AbstractOrb o;
                for(Iterator iterator = AbstractDungeon.player.orbs.iterator(); iterator.hasNext(); o.updateDescription())
                    o = (AbstractOrb)iterator.next();

            } else
            {
                duration = 0.0F;
            }
        }
        tickDuration();
    }

    private String powerToRemove;
    private AbstractPower powerInstance;
    private static final float DURATION = 0.1F;
}
