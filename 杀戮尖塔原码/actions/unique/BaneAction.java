// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BaneAction.java

package com.megacrit.cardcrawl.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

public class BaneAction extends AbstractGameAction
{

    public BaneAction(AbstractMonster target, DamageInfo info)
    {
        this.info = info;
        setValues(target, info);
        m = target;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_VERTICAL;
        duration = 0.01F;
    }

    public void update()
    {
        if(target == null)
        {
            isDone = true;
            return;
        }
        if(m.hasPower("Poison"))
        {
            if(duration == 0.01F && target != null && target.currentHealth > 0)
            {
                if(info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.owner.isDying)
                {
                    isDone = true;
                    return;
                }
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect));
            }
            tickDuration();
            if(isDone && target != null && target.currentHealth > 0)
            {
                target.damage(info);
                if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                    AbstractDungeon.actionManager.clearPostCombatActions();
                addToTop(new WaitAction(0.1F));
            }
        } else
        {
            isDone = true;
        }
    }

    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    private AbstractMonster m;
}
