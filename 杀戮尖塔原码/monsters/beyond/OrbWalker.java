// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   OrbWalker.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GenericStrengthUpPower;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;

public class OrbWalker extends AbstractMonster
{

    public OrbWalker(float x, float y)
    {
        super(NAME, "Orb Walker", AbstractDungeon.monsterHpRng.random(90, 96), -20F, -14F, 280F, 250F, null, x, y);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(92, 102);
        else
            setHp(90, 96);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            clawDmg = 16;
            laserDmg = 11;
        } else
        {
            clawDmg = 15;
            laserDmg = 10;
        }
        damage.add(new DamageInfo(this, laserDmg));
        damage.add(new DamageInfo(this, clawDmg));
        loadAnimation("images/monsters/theForest/orbWalker/skeleton.atlas", "images/monsters/theForest/orbWalker/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.ascensionLevel >= 17)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GenericStrengthUpPower(this, MOVES[0], 5)));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new GenericStrengthUpPower(this, MOVES[0], 3)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAndDeckAction(new Burn()));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(num < 40)
        {
            if(!lastTwoMoves((byte)2))
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            else
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
        } else
        if(!lastTwoMoves((byte)1))
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base);
        else
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 1941037640: 
            if(s.equals("ATTACK"))
                byte0 = 0;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;
        }
    }

    public static final String ID = "Orb Walker";
    public static final String DOUBLE_ENCOUNTER = "Double Orb Walker";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 90;
    private static final int HP_MAX = 96;
    private static final int A_2_HP_MIN = 92;
    private static final int A_2_HP_MAX = 102;
    public static final int LASER_DMG = 10;
    public static final int CLAW_DMG = 15;
    public static final int A_2_LASER_DMG = 11;
    public static final int A_2_CLAW_DMG = 16;
    private int clawDmg;
    private int laserDmg;
    private static final byte LASER = 1;
    private static final byte CLAW = 2;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Orb Walker");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
