// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FearNoEvilAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.CalmStance;

// Referenced classes of package com.megacrit.cardcrawl.actions.watcher:
//            ChangeStanceAction

public class FearNoEvilAction extends AbstractGameAction
{

    public FearNoEvilAction(AbstractMonster m, DamageInfo info)
    {
        this.m = m;
        this.info = info;
    }

    public void update()
    {
        if(m != null && (m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_BUFF || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF || m.intent == com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND))
            addToTop(new ChangeStanceAction("Calm"));
        addToTop(new DamageAction(m, info, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
        isDone = true;
    }

    private AbstractMonster m;
    private DamageInfo info;
}
