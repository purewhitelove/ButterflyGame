// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BanditLeader.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.monsters.city:
//            BanditBear

public class BanditLeader extends AbstractMonster
{

    public BanditLeader(float x, float y)
    {
        super(NAME, "BanditLeader", 39, -10F, -7F, 180F, 285F, null, x, y);
        dialogX = 0.0F * Settings.scale;
        dialogY = 50F * Settings.scale;
        if(AbstractDungeon.ascensionLevel >= 17)
            weakAmount = 3;
        else
            weakAmount = 2;
        if(AbstractDungeon.ascensionLevel >= 7)
            setHp(37, 41);
        else
            setHp(35, 39);
        if(AbstractDungeon.ascensionLevel >= 2)
        {
            slashDmg = 17;
            agonizeDmg = 12;
        } else
        {
            slashDmg = 15;
            agonizeDmg = 10;
        }
        damage.add(new DamageInfo(this, slashDmg));
        damage.add(new DamageInfo(this, agonizeDmg));
        loadAnimation("images/monsters/theCity/romeo/skeleton.atlas", "images/monsters/theCity/romeo/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle", 0.2F);
        state.setTimeScale(0.8F);
    }

    public void deathReact()
    {
        if(!isDeadOrEscaped())
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[2]));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        default:
            break;

        case 2: // '\002'
            boolean bearLives = true;
            Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(!(m instanceof BanditBear) || !m.isDying)
                    continue;
                bearLives = false;
                break;
            } while(true);
            if(bearLives)
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0]));
            else
                AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[1]));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAB"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, weakAmount, true), weakAmount));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base));
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "STAB"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            if(AbstractDungeon.ascensionLevel >= 17 && !lastTwoMoves((byte)1))
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base));
            else
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base));
            break;
        }
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 2555458: 
            if(s.equals("STAB"))
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
            state.setTimeScale(0.8F);
            state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    protected void getMove(int num)
    {
        setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
    }

    public static final String ID = "BanditLeader";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP_MIN = 35;
    public static final int HP_MAX = 39;
    public static final int A_2_HP_MIN = 37;
    public static final int A_2_HP_MAX = 41;
    private static final int SLASH_DAMAGE = 15;
    private static final int AGONIZE_DAMAGE = 10;
    private static final int A_2_SLASH_DAMAGE = 17;
    private static final int A_2_AGONIZE_DAMAGE = 12;
    private static final int WEAK_AMT = 2;
    private static final int A_17_WEAK = 3;
    private int slashDmg;
    private int agonizeDmg;
    private int weakAmount;
    private static final byte CROSS_SLASH = 1;
    private static final byte MOCK = 2;
    private static final byte AGONIZING_SLASH = 3;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("BanditLeader");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
