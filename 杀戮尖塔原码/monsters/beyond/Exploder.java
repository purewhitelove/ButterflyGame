// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Exploder.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ExplosivePower;
import java.util.ArrayList;

public class Exploder extends AbstractMonster
{

    public Exploder(float x, float y)
    {
        super(NAME, "Exploder", 30, -8F, -10F, 150F, 150F, null, x, y + 10F);
        turnCount = 0;
        loadAnimation("images/monsters/theForest/exploder/skeleton.atlas", "images/monsters/theForest/exploder/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(30, 35);
        else
            setHp(30, 30);
        if(AbstractDungeon.ascensionLevel >= 2)
            attackDmg = 11;
        else
            attackDmg = 9;
        damage.add(new DamageInfo(this, attackDmg));
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ExplosivePower(this, 3)));
    }

    public void takeTurn()
    {
        turnCount++;
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(turnCount < 2)
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
        else
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
    }

    public static final String ID = "Exploder";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final String ENCOUNTER_NAME = "Ancient Shapes";
    private static final int HP_MIN = 30;
    private static final int HP_MAX = 30;
    private static final int A_2_HP_MIN = 30;
    private static final int A_2_HP_MAX = 35;
    private int turnCount;
    private static final float HB_X = -8F;
    private static final float HB_Y = -10F;
    private static final float HB_W = 150F;
    private static final float HB_H = 150F;
    private static final byte ATTACK = 1;
    private static final int ATTACK_DMG = 9;
    private static final int A_2_ATTACK_DMG = 11;
    private int attackDmg;
    private static final byte BLOCK = 2;
    private static final int EXPLODE_BASE = 3;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Exploder");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
