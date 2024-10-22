// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FungiBeast.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SporeCloudPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import java.util.ArrayList;

public class FungiBeast extends AbstractMonster
{

    public FungiBeast(float x, float y)
    {
        super(NAME, "FungiBeast", 28, 0.0F, -16F, 260F, 170F, null, x, y);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(24, 28);
        else
            setHp(22, 28);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            strAmt = 4;
            biteDamage = 6;
        } else
        {
            strAmt = 3;
            biteDamage = 6;
        }
        loadAnimation("images/monsters/theBottom/fungi/skeleton.atlas", "images/monsters/theBottom/fungi/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(MathUtils.random(0.7F, 1.0F));
        damage.add(new DamageInfo(this, biteDamage));
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SporeCloudPower(this, 2)));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            break;

        case 2: // '\002'
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, strAmt + 1), strAmt + 1));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, strAmt), strAmt));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(num < 60)
        {
            if(lastTwoMoves((byte)1))
                setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            else
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
        } else
        if(lastMove((byte)2))
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
        else
            setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
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

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public static final String ID = "FungiBeast";
    public static final String DOUBLE_ENCOUNTER = "TwoFungiBeasts";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 22;
    private static final int HP_MAX = 28;
    private static final int A_2_HP_MIN = 24;
    private static final int A_2_HP_MAX = 28;
    private static final float HB_X = 0F;
    private static final float HB_Y = -16F;
    private static final float HB_W = 260F;
    private static final float HB_H = 170F;
    private int biteDamage;
    private int strAmt;
    private static final int BITE_DMG = 6;
    private static final int GROW_STR = 3;
    private static final int A_2_GROW_STR = 4;
    private static final byte BITE = 1;
    private static final byte GROW = 2;
    private static final int VULN_AMT = 2;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("FungiBeast");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
