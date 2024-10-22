// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SlaverBlue.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import java.util.ArrayList;

public class SlaverBlue extends AbstractMonster
{

    public SlaverBlue(float x, float y)
    {
        super(NAME, "SlaverBlue", 50, 0.0F, 0.0F, 170F, 230F, null, x, y);
        stabDmg = 12;
        rakeDmg = 7;
        weakAmt = 1;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(48, 52);
        else
            setHp(46, 50);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            stabDmg = 13;
            rakeDmg = 8;
        } else
        {
            stabDmg = 12;
            rakeDmg = 7;
        }
        damage.add(new DamageInfo(this, stabDmg));
        damage.add(new DamageInfo(this, rakeDmg));
        loadAnimation("images/monsters/theBottom/blueSlaver/skeleton.atlas", "images/monsters/theBottom/blueSlaver/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, weakAmt + 1, true), weakAmt + 1));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, weakAmt, true), weakAmt));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERBLUE_1A"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERBLUE_1B"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_SLAVERBLUE_2A");
        else
            CardCrawlGame.sound.play("VO_SLAVERBLUE_2B");
    }

    protected void getMove(int num)
    {
        if(num >= 40 && !lastTwoMoves((byte)1))
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
        if(AbstractDungeon.ascensionLevel >= 17)
            if(!lastMove((byte)4))
            {
                setMove(MOVES[0], (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                return;
            } else
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
                return;
            }
        if(!lastTwoMoves((byte)4))
        {
            setMove(MOVES[0], (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
            return;
        } else
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
    }

    public void die()
    {
        super.die();
        playDeathSfx();
    }

    public static final String ID = "SlaverBlue";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 46;
    private static final int HP_MAX = 50;
    private static final int A_2_HP_MIN = 48;
    private static final int A_2_HP_MAX = 52;
    private static final int STAB_DMG = 12;
    private static final int A_2_STAB_DMG = 13;
    private static final int RAKE_DMG = 7;
    private static final int A_2_RAKE_DMG = 8;
    private int stabDmg;
    private int rakeDmg;
    private int weakAmt;
    private static final byte STAB = 1;
    private static final byte RAKE = 4;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SlaverBlue");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
