// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BanditBear.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
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
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

public class BanditBear extends AbstractMonster
{

    public BanditBear(float x, float y)
    {
        super(NAME, "BanditBear", 42, -5F, -4F, 180F, 280F, null, x, y);
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(40, 44);
        else
            setHp(38, 42);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            maulDmg = 20;
            lungeDmg = 10;
        } else
        {
            maulDmg = 18;
            lungeDmg = 9;
        }
        if(AbstractDungeon.ascensionLevel >= 17)
            con_reduction = -4;
        else
            con_reduction = -2;
        damage.add(new DamageInfo(this, maulDmg));
        damage.add(new DamageInfo(this, lungeDmg));
        loadAnimation("images/monsters/theCity/bear/skeleton.atlas", "images/monsters/theCity/bear/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        state.setTimeScale(1.0F);
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new DexterityPower(AbstractDungeon.player, con_reduction), con_reduction));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(1)).base));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "MAUL"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEFEND, ((DamageInfo)damage.get(1)).base));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 9));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base));
            break;
        }
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 2359083: 
            if(s.equals("MAUL"))
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
            state.setTimeScale(1.0F);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    public void die()
    {
        super.die();
        Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDead && !m.isDying)
                m.deathReact();
        } while(true);
    }

    protected void getMove(int num)
    {
        setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
    }

    public static final String ID = "BanditBear";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP_MIN = 38;
    public static final int HP_MAX = 42;
    public static final int A_2_HP_MIN = 40;
    public static final int A_2_HP_MAX = 44;
    private static final int MAUL_DMG = 18;
    private static final int A_2_MAUL_DMG = 20;
    private static final int LUNGE_DMG = 9;
    private static final int A_2_LUNGE_DMG = 10;
    private static final int LUNGE_DEFENSE = 9;
    private static final int CON_AMT = -2;
    private static final int A_17_CON_AMT = -4;
    private int maulDmg;
    private int lungeDmg;
    private int con_reduction;
    private static final byte MAUL = 1;
    private static final byte BEAR_HUG = 2;
    private static final byte LUNGE = 3;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BanditBear");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
