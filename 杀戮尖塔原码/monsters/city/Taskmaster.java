// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Taskmaster.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;

public class Taskmaster extends AbstractMonster
{

    public Taskmaster(float x, float y)
    {
        super(NAME, "SlaverBoss", AbstractDungeon.monsterHpRng.random(54, 60), -10F, -8F, 200F, 280F, null, x, y);
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(57, 64);
        else
            setHp(54, 60);
        if(AbstractDungeon.ascensionLevel >= 18)
            woundCount = 3;
        else
        if(AbstractDungeon.ascensionLevel >= 3)
            woundCount = 2;
        else
            woundCount = 1;
        damage.add(new DamageInfo(this, 4));
        damage.add(new DamageInfo(this, 7));
        loadAnimation("images/monsters/theCity/slaverMaster/skeleton.atlas", "images/monsters/theCity/slaverMaster/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 2: // '\002'
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(), woundCount));
            if(AbstractDungeon.ascensionLevel >= 18)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, 7);
    }

    private void playSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERLEADER_1A"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_SLAVERLEADER_1B"));
    }

    private void playDeathSfx()
    {
        int roll = MathUtils.random(1);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_SLAVERLEADER_2A");
        else
            CardCrawlGame.sound.play("VO_SLAVERLEADER_2B");
    }

    public void die()
    {
        super.die();
        playDeathSfx();
    }

    public static final String ID = "SlaverBoss";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int HP_MIN = 54;
    private static final int HP_MAX = 60;
    private static final int A_2_HP_MIN = 57;
    private static final int A_2_HP_MAX = 64;
    private static final int WHIP_DMG = 4;
    private static final int SCOURING_WHIP_DMG = 7;
    private static final int WOUNDS = 1;
    private static final int A_2_WOUNDS = 2;
    private int woundCount;
    private static final byte SCOURING_WHIP = 2;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("SlaverBoss");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
