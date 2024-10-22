// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LouseNormal.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.CurlUpPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;

public class LouseNormal extends AbstractMonster
{

    public LouseNormal(float x, float y)
    {
        super(NAME, "FuzzyLouseNormal", 15, 0.0F, -5F, 180F, 140F, null, x, y);
        isOpen = true;
        loadAnimation("images/monsters/theBottom/louseRed/skeleton.atlas", "images/monsters/theBottom/louseRed/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(11, 16);
        else
            setHp(10, 15);
        if(AbstractDungeon.ascensionLevel >= 2)
            biteDamage = AbstractDungeon.monsterHpRng.random(6, 8);
        else
            biteDamage = AbstractDungeon.monsterHpRng.random(5, 7);
        damage.add(new DamageInfo(this, biteDamage));
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.ascensionLevel >= 17)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(9, 12))));
        else
        if(AbstractDungeon.ascensionLevel >= 7)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(4, 8))));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CurlUpPower(this, AbstractDungeon.monsterHpRng.random(3, 7))));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 3: // '\003'
            if(!isOpen)
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "OPEN"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            }
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            break;

        case 4: // '\004'
            if(!isOpen)
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REAR"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(1.2F));
            } else
            {
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REAR_IDLE"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.9F));
            }
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 4), 4));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String stateName)
    {
        if(stateName.equals("CLOSED"))
        {
            state.setAnimation(0, "transitiontoclosed", false);
            state.addAnimation(0, "idle closed", true, 0.0F);
            isOpen = false;
        } else
        if(stateName.equals("OPEN"))
        {
            state.setAnimation(0, "transitiontoopened", false);
            state.addAnimation(0, "idle", true, 0.0F);
            isOpen = true;
        } else
        if(stateName.equals("REAR_IDLE"))
        {
            state.setAnimation(0, "rear", false);
            state.addAnimation(0, "idle", true, 0.0F);
            isOpen = true;
        } else
        {
            state.setAnimation(0, "transitiontoopened", false);
            state.addAnimation(0, "rear", false, 0.0F);
            state.addAnimation(0, "idle", true, 0.0F);
            isOpen = true;
        }
    }

    protected void getMove(int num)
    {
        if(AbstractDungeon.ascensionLevel >= 17)
        {
            if(num < 25)
            {
                if(lastMove((byte)4))
                    setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
                else
                    setMove(MOVES[0], (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            } else
            if(lastTwoMoves((byte)3))
                setMove(MOVES[0], (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            else
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
        } else
        if(num < 25)
        {
            if(lastTwoMoves((byte)4))
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            else
                setMove(MOVES[0], (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
        } else
        if(lastTwoMoves((byte)3))
            setMove(MOVES[0], (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
        else
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
    }

    public static final String ID = "FuzzyLouseNormal";
    public static final String THREE_LOUSE = "ThreeLouse";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 10;
    private static final int HP_MAX = 15;
    private static final int A_2_HP_MIN = 11;
    private static final int A_2_HP_MAX = 16;
    private static final byte BITE = 3;
    private static final byte STRENGTHEN = 4;
    private boolean isOpen;
    private static final String CLOSED_STATE = "CLOSED";
    private static final String OPEN_STATE = "OPEN";
    private static final String REAR_IDLE = "REAR_IDLE";
    private int biteDamage;
    private static final int STR_AMOUNT = 3;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("FuzzyLouseNormal");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
