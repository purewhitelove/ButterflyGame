// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RegenAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.TintEffect;
import java.util.ArrayList;

public class RegenAction extends AbstractGameAction
{

    public RegenAction(AbstractCreature target, int amount)
    {
        this.target = target;
        this.amount = amount;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(AbstractDungeon.getCurrRoom().phase != com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            isDone = true;
            return;
        }
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(target.currentHealth > 0)
            {
                target.tint.color = Color.CHARTREUSE.cpy();
                target.tint.changeColor(Color.WHITE.cpy());
                target.heal(amount, true);
            }
            if(target.isPlayer)
            {
                AbstractPower p = target.getPower("Regeneration");
                if(p != null)
                {
                    p.amount--;
                    if(p.amount == 0)
                        target.powers.remove(p);
                    else
                        p.updateDescription();
                }
            }
        }
        tickDuration();
    }
}
