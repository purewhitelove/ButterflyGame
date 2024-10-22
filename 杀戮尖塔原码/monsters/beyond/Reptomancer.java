// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Reptomancer.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.monsters.beyond:
//            SnakeDagger

public class Reptomancer extends AbstractMonster
{

    public Reptomancer()
    {
        super(NAME, "Reptomancer", AbstractDungeon.monsterHpRng.random(180, 190), 0.0F, -30F, 220F, 320F, null, -20F, 10F);
        daggers = new AbstractMonster[4];
        firstMove = true;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.ELITE;
        loadAnimation("images/monsters/theForest/mage/skeleton.atlas", "images/monsters/theForest/mage/skeleton.json", 1.0F);
        if(AbstractDungeon.ascensionLevel >= 18)
            daggersPerSpawn = 2;
        else
            daggersPerSpawn = 1;
        if(AbstractDungeon.ascensionLevel >= 8)
            setHp(190, 200);
        else
            setHp(180, 190);
        if(AbstractDungeon.ascensionLevel >= 3)
        {
            damage.add(new DamageInfo(this, 16));
            damage.add(new DamageInfo(this, 34));
        } else
        {
            damage.add(new DamageInfo(this, 13));
            damage.add(new DamageInfo(this, 30));
        }
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Idle", "Sumon", 0.1F);
        stateData.setMix("Sumon", "Idle", 0.1F);
        stateData.setMix("Hurt", "Idle", 0.1F);
        stateData.setMix("Idle", "Hurt", 0.1F);
        stateData.setMix("Attack", "Idle", 0.1F);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.id.equals(id))
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MinionPower(this)));
            if(m instanceof SnakeDagger)
                if(AbstractDungeon.getMonsters().monsters.indexOf(m) > AbstractDungeon.getMonsters().monsters.indexOf(this))
                    daggers[0] = m;
                else
                    daggers[1] = m;
        } while(true);
    }

    public void takeTurn()
    {
label0:
        switch(nextMove)
        {
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50F, 50F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50F, 50F) * Settings.scale, Color.ORANGE.cpy()), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50F, 50F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50F, 50F) * Settings.scale, Color.ORANGE.cpy()), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 1, true), 1));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SUMMON"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
            int daggersSpawned = 0;
            int i = 0;
            do
            {
                if(daggersSpawned >= daggersPerSpawn || i >= daggers.length)
                    break label0;
                if(daggers[i] == null || daggers[i].isDeadOrEscaped())
                {
                    SnakeDagger daggerToSpawn = new SnakeDagger(POSX[i], POSY[i]);
                    daggers[i] = daggerToSpawn;
                    AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(daggerToSpawn, true));
                    daggersSpawned++;
                }
                i++;
            } while(true);

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-50F, 50F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-50F, 50F) * Settings.scale, Color.CHARTREUSE.cpy()), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private boolean canSpawn()
    {
        int aliveCount = 0;
        Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m != this && !m.isDying)
                aliveCount++;
        } while(true);
        return aliveCount <= 3;
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hurt", false);
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
            {
                AbstractDungeon.actionManager.addToTop(new HideHealthBarAction(m));
                AbstractDungeon.actionManager.addToTop(new SuicideAction(m));
            }
        } while(true);
    }

    protected void getMove(int num)
    {
        if(firstMove)
        {
            firstMove = false;
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            return;
        }
        if(num < 33)
        {
            if(!lastMove((byte)1))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base, 2, true);
            else
                getMove(AbstractDungeon.aiRng.random(33, 99));
        } else
        if(num < 66)
        {
            if(!lastTwoMoves((byte)2))
            {
                if(canSpawn())
                    setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
                else
                    setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base, 2, true);
            } else
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(0)).base, 2, true);
            }
        } else
        if(!lastMove((byte)3))
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(1)).base);
        else
            getMove(AbstractDungeon.aiRng.random(65));
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

        case -1837878047: 
            if(s.equals("SUMMON"))
                byte0 = 1;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            state.setAnimation(0, "Attack", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;

        case 1: // '\001'
            state.setAnimation(0, "Sumon", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            break;
        }
    }

    public static final String ID = "Reptomancer";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    private static final int HP_MIN = 180;
    private static final int HP_MAX = 190;
    private static final int A_2_HP_MIN = 190;
    private static final int A_2_HP_MAX = 200;
    private static final int BITE_DMG = 30;
    private static final int A_2_BITE_DMG = 34;
    private static final int SNAKE_STRIKE_DMG = 13;
    private static final int A_2_SNAKE_STRIKE_DMG = 16;
    private static final int DAGGERS_PER_SPAWN = 1;
    private static final int ASC_2_DAGGERS_PER_SPAWN = 2;
    private static final byte SNAKE_STRIKE = 1;
    private static final byte SPAWN_DAGGER = 2;
    private static final byte BIG_BITE = 3;
    public static final float POSX[] = {
        210F, -220F, 180F, -250F
    };
    public static final float POSY[] = {
        75F, 115F, 345F, 335F
    };
    private int daggersPerSpawn;
    private AbstractMonster daggers[];
    private boolean firstMove;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Reptomancer");
        NAME = monsterStrings.NAME;
    }
}
