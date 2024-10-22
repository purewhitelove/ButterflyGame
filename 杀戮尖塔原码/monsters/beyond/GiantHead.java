// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GiantHead.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.ShoutAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import java.util.ArrayList;

public class GiantHead extends AbstractMonster
{

    public GiantHead()
    {
        super(NAME, "GiantHead", 500, 0.0F, -40F, 460F, 300F, null, -70F, 40F);
        count = 5;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        dialogX = -100F * Settings.scale;
        dialogY -= 20F * Settings.scale;
        loadAnimation("images/monsters/theForest/head/skeleton.atlas", "images/monsters/theForest/head/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle_open", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.5F);
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(520, 520);
        else
            setHp(500, 500);
        if(AbstractDungeon.ascensionLevel >= 3)
            startingDeathDmg = 40;
        else
            startingDeathDmg = 30;
        damage.add(new DamageInfo(this, 13));
        damage.add(new DamageInfo(this, startingDeathDmg));
        damage.add(new DamageInfo(this, startingDeathDmg + 5));
        damage.add(new DamageInfo(this, startingDeathDmg + 10));
        damage.add(new DamageInfo(this, startingDeathDmg + 15));
        damage.add(new DamageInfo(this, startingDeathDmg + 20));
        damage.add(new DamageInfo(this, startingDeathDmg + 25));
        damage.add(new DamageInfo(this, startingDeathDmg + 30));
    }

    public void usePreBattleAction()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new SlowPower(this, 0)));
        if(AbstractDungeon.ascensionLevel >= 18)
            count--;
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, (new StringBuilder()).append("#r~").append(Integer.toString(count)).append("...~").toString(), 1.7F, 1.7F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
            break;

        case 3: // '\003'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, (new StringBuilder()).append("#r~").append(Integer.toString(count)).append("...~").toString(), 1.7F, 1.7F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            break;

        case 2: // '\002'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, getTimeQuote(), 1.7F, 2.0F));
            int index = 1 - count;
            if(index > 7)
                index = 7;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(index), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SMASH));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GIANTHEAD_1A"));
        else
        if(roll == 1)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GIANTHEAD_1B"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GIANTHEAD_1C"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_GIANTHEAD_2A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_GIANTHEAD_2B");
        else
            CardCrawlGame.sound.play("VO_GIANTHEAD_2C");
    }

    private String getTimeQuote()
    {
        ArrayList quotes = new ArrayList();
        quotes.add(DIALOG[0]);
        quotes.add(DIALOG[1]);
        quotes.add(DIALOG[2]);
        quotes.add(DIALOG[3]);
        return (String)quotes.get(MathUtils.random(0, quotes.size() - 1));
    }

    public void die()
    {
        super.die();
        playDeathSfx();
    }

    protected void getMove(int num)
    {
        if(count <= 1)
        {
            if(count > -6)
                count--;
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, startingDeathDmg - count * 5);
            return;
        }
        count--;
        if(num < 50)
        {
            if(!lastTwoMoves((byte)1))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
            else
                setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 13);
        } else
        if(!lastTwoMoves((byte)3))
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 13);
        else
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEBUFF);
    }

    public static final String ID = "GiantHead";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP = 500;
    private static final int A_2_HP = 520;
    private static final float HB_X_F = 0F;
    private static final float HB_Y_F = -40F;
    private static final float HB_W = 460F;
    private static final float HB_H = 300F;
    private static final int COUNT_DMG = 13;
    private static final int DEATH_DMG = 30;
    private static final int GLARE_WEAK = 1;
    private static final int INCREMENT_DMG = 5;
    private static final int A_2_DEATH_DMG = 40;
    private int startingDeathDmg;
    private static final byte GLARE = 1;
    private static final byte IT_IS_TIME = 2;
    private static final byte COUNT = 3;
    private int count;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("GiantHead");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
