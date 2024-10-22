// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExpungeVFXAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;

public class ExpungeVFXAction extends AbstractGameAction
{

    public ExpungeVFXAction(AbstractMonster m)
    {
        source = m;
    }

    public void update()
    {
        if(!source.isDeadOrEscaped())
        {
            addToTop(new VFXAction(new AnimatedSlashEffect(source.hb.cX, source.hb.cY - 30F * Settings.scale, -500F, -500F, 135F, 4F, Color.VIOLET, Color.MAGENTA)));
            addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.7F, true));
            addToTop(new SFXAction("ATTACK_IRON_3", 0.2F));
        }
        isDone = true;
    }
}
