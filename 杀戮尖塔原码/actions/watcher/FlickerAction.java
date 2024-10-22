// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FlickerAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.actions.watcher:
//            FlickerReturnToHandAction

public class FlickerAction extends AbstractGameAction
{

    public FlickerAction(AbstractCreature target, DamageInfo info, AbstractCard card)
    {
        this.info = info;
        this.card = card;
        setValues(target, info);
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        duration = Settings.ACTION_DUR_FASTER;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FASTER && target != null)
        {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            target.damage(info);
            if(((AbstractMonster)target).isDying || target.currentHealth <= 0)
                addToBot(new FlickerReturnToHandAction(card));
            if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions();
        }
        tickDuration();
    }

    private DamageInfo info;
    private AbstractCard card;
}
