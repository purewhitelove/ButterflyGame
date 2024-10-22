// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShowMoveNameAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MoveNameEffect;
import java.util.ArrayList;

public class ShowMoveNameAction extends AbstractGameAction
{

    public ShowMoveNameAction(AbstractMonster source, String msg)
    {
        setValues(source, source);
        this.msg = msg;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
    }

    public ShowMoveNameAction(AbstractMonster source)
    {
        setValues(source, source);
        msg = source.moveName;
        source.moveName = null;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
    }

    public void update()
    {
        if(source != null && !source.isDying)
            AbstractDungeon.effectList.add(new MoveNameEffect(source.hb.cX - source.animX, source.hb.cY + source.hb.height / 2.0F, msg));
        isDone = true;
    }

    private String msg;
}
