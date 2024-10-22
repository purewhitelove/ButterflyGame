// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Spiker.java

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
import com.megacrit.cardcrawl.powers.ThornsPower;
import java.util.ArrayList;

public class Spiker extends AbstractMonster
{

    public Spiker(float x, float y)
    {
        super(NAME, "Spiker", 56, -8F, -10F, 150F, 150F, null, x, y + 10F);
        thornsCount = 0;
        loadAnimation("images/monsters/theForest/spiker/skeleton.atlas", "images/monsters/theForest/spiker/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(44, 60);
        else
            setHp(42, 56);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            startingThorns = 4;
            attackDmg = 9;
        } else
        {
            startingThorns = 3;
            attackDmg = 7;
        }
        damage.add(new DamageInfo(this, attackDmg));
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.ascensionLevel >= 17)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, startingThorns + 3)));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, startingThorns)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            break;

        case 2: // '\002'
            thornsCount++;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ThornsPower(this, 2), 2));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(thornsCount > 5)
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
        if(num < 50 && !lastMove((byte)1))
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        } else
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        }
    }

    public static final String ID = "Spiker";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final String ENCOUNTER_NAME = "Ancient Shapes";
    private static final int HP_MIN = 42;
    private static final int HP_MAX = 56;
    private static final int A_2_HP_MIN = 44;
    private static final int A_2_HP_MAX = 60;
    private static final float HB_X = -8F;
    private static final float HB_Y = -10F;
    private static final float HB_W = 150F;
    private static final float HB_H = 150F;
    private static final int STARTING_THORNS = 3;
    private static final int A_2_STARTING_THORNS = 4;
    private int startingThorns;
    private static final byte ATTACK = 1;
    private static final int ATTACK_DMG = 7;
    private static final int A_2_ATTACK_DMG = 9;
    private int attackDmg;
    private static final byte BUFF_THORNS = 2;
    private static final int BUFF_AMT = 2;
    private int thornsCount;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Spiker");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
