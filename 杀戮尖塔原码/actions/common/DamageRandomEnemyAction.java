// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamageRandomEnemyAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

// Referenced classes of package com.megacrit.cardcrawl.actions.common:
//            DamageAction

public class DamageRandomEnemyAction extends AbstractGameAction
{

    public DamageRandomEnemyAction(DamageInfo info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effect)
    {
        this.info = info;
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        attackEffect = effect;
    }

    public void update()
    {
        target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if(target != null)
            addToTop(new DamageAction(target, info, attackEffect));
        isDone = true;
    }

    private DamageInfo info;
}
