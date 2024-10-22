// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinNob.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AngerPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import java.util.ArrayList;

public class GremlinNob extends AbstractMonster
{

    public GremlinNob(float x, float y)
    {
        this(x, y, true);
    }

    public GremlinNob(float x, float y, boolean setVuln)
    {
        super(NAME, "GremlinNob", 86, -70F, -10F, 270F, 380F, null, x, y);
        usedBellow = false;
        intentOffsetX = -30F * Settings.scale;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        dialogX = -60F * Settings.scale;
        dialogY = 50F * Settings.scale;
        canVuln = setVuln;
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(85, 90);
        else
            setHp(82, 86);
        if(AbstractDungeon.ascensionLevel >= 3)
        {
            bashDmg = 8;
            rushDmg = 16;
        } else
        {
            bashDmg = 6;
            rushDmg = 14;
        }
        damage.add(new DamageInfo(this, rushDmg));
        damage.add(new DamageInfo(this, bashDmg));
        loadAnimation("images/monsters/theBottom/nobGremlin/skeleton.atlas", "images/monsters/theBottom/nobGremlin/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 3: // '\003'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0], 1.0F, 3F));
            if(AbstractDungeon.ascensionLevel >= 18)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngerPower(this, 3), 3));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new AngerPower(this, 2), 2));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            if(canVuln)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1A"));
        else
        if(roll == 1)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1B"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1C"));
    }

    protected void getMove(int num)
    {
        if(!usedBellow)
        {
            usedBellow = true;
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.BUFF);
            return;
        }
        if(AbstractDungeon.ascensionLevel >= 18)
        {
            if(!lastMove((byte)2) && !lastMoveBefore((byte)2))
            {
                if(canVuln)
                    setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                else
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                return;
            }
            if(lastTwoMoves((byte)1))
            {
                if(canVuln)
                    setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                else
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            } else
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            }
        } else
        {
            if(num < 33)
            {
                if(canVuln)
                    setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                else
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
                return;
            }
            if(lastTwoMoves((byte)1))
            {
                if(canVuln)
                    setMove(MOVES[0], (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                else
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            } else
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            }
        }
    }

    public static final String ID = "GremlinNob";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 82;
    private static final int HP_MAX = 86;
    private static final int A_2_HP_MIN = 85;
    private static final int A_2_HP_MAX = 90;
    private static final int BASH_DMG = 6;
    private static final int RUSH_DMG = 14;
    private static final int A_2_BASH_DMG = 8;
    private static final int A_2_RUSH_DMG = 16;
    private static final int DEBUFF_AMT = 2;
    private int bashDmg;
    private int rushDmg;
    private static final byte BULL_RUSH = 1;
    private static final byte SKULL_BASH = 2;
    private static final byte BELLOW = 3;
    private static final int ANGRY_LEVEL = 2;
    private boolean usedBellow;
    private boolean canVuln;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GremlinNob");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
