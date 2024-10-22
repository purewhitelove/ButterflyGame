// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelicAboveCreatureAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RelicAboveCreatureEffect;
import java.util.ArrayList;

public class RelicAboveCreatureAction extends AbstractGameAction
{

    public RelicAboveCreatureAction(AbstractCreature source, AbstractRelic relic)
    {
        used = false;
        setValues(source, source);
        this.relic = relic;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.TEXT;
        duration = Settings.ACTION_DUR_XFAST;
    }

    public void update()
    {
        if(!used)
        {
            AbstractDungeon.effectList.add(new RelicAboveCreatureEffect(source.hb.cX - source.animX, (source.hb.cY + source.hb.height / 2.0F) - source.animY, relic));
            used = true;
        }
        tickDuration();
    }

    private boolean used;
    private AbstractRelic relic;
}
