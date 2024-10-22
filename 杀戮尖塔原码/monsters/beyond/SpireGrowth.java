// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpireGrowth.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import java.util.ArrayList;

public class SpireGrowth extends AbstractMonster
{

    public SpireGrowth()
    {
        super(NAME, "Serpent", 170, -10F, -35F, 480F, 430F, null, 0.0F, 10F);
        tackleDmg = 16;
        smashDmg = 22;
        constrictDmg = 10;
        A_2_tackleDmg = 18;
        A_2_smashDmg = 25;
        loadAnimation("images/monsters/theForest/spireGrowth/skeleton.atlas", "images/monsters/theForest/spireGrowth/skeleton.json", 1.0F);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(190);
        else
            setHp(170);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            tackleDmgActual = A_2_tackleDmg;
            smashDmgActual = A_2_smashDmg;
        } else
        {
            tackleDmgActual = tackleDmg;
            smashDmgActual = smashDmg;
        }
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.3F);
        stateData.setMix("Hurt", "Idle", 0.2F);
        stateData.setMix("Idle", "Hurt", 0.2F);
        damage.add(new DamageInfo(this, tackleDmgActual));
        damage.add(new DamageInfo(this, smashDmgActual));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            if(AbstractDungeon.ascensionLevel >= 17)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, constrictDmg + 2)));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new ConstrictedPower(AbstractDungeon.player, this, constrictDmg)));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(AbstractDungeon.ascensionLevel >= 17 && !AbstractDungeon.player.hasPower("Constricted") && !lastMove((byte)2))
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            return;
        }
        if(num < 50 && !lastTwoMoves((byte)1))
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
        if(!AbstractDungeon.player.hasPower("Constricted") && !lastMove((byte)2))
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            return;
        }
        if(!lastTwoMoves((byte)3))
        {
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
            return;
        } else
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hurt", false);
            state.setTimeScale(1.3F);
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
            state.setTimeScale(1.3F);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;
        }
    }

    public static final String ID = "Serpent";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private static final int START_HP = 170;
    private static final int A_2_START_HP = 190;
    private int tackleDmg;
    private int smashDmg;
    private int constrictDmg;
    private int A_2_tackleDmg;
    private int A_2_smashDmg;
    private int tackleDmgActual;
    private int smashDmgActual;
    private static final byte QUICK_TACKLE = 1;
    private static final byte CONSTRICT = 2;
    private static final byte SMASH = 3;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Serpent");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
