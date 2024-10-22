// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheCollector.java

package com.megacrit.cardcrawl.monsters.city;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.monsters.city:
//            TorchHead

public class TheCollector extends AbstractMonster
{

    public TheCollector()
    {
        super(NAME, "TheCollector", 282, 15F, -40F, 300F, 390F, null, 60F, 135F);
        turnsTaken = 0;
        spawnX = -100F;
        fireTimer = 0.0F;
        ultUsed = false;
        initialSpawn = true;
        enemySlots = new HashMap();
        dialogX = -90F * Settings.scale;
        dialogY = 10F * Settings.scale;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        if(AbstractDungeon.ascensionLevel >= 9)
        {
            setHp(300);
            blockAmt = 18;
        } else
        {
            setHp(282);
            blockAmt = 15;
        }
        if(AbstractDungeon.ascensionLevel >= 19)
        {
            rakeDmg = 21;
            strAmt = 5;
            megaDebuffAmt = 5;
        } else
        if(AbstractDungeon.ascensionLevel >= 4)
        {
            rakeDmg = 21;
            strAmt = 4;
            megaDebuffAmt = 3;
        } else
        {
            rakeDmg = 18;
            strAmt = 3;
            megaDebuffAmt = 3;
        }
        damage.add(new DamageInfo(this, rakeDmg));
        loadAnimation("images/monsters/theCity/collector/skeleton.atlas", "images/monsters/theCity/collector/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void usePreBattleAction()
    {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_CITY");
        UnlockTracker.markBossAsSeen("COLLECTOR");
    }

    public void takeTurn()
    {
label0:
        switch(nextMove)
        {
        case 1: // '\001'
            for(int i = 1; i < 3; i++)
            {
                AbstractMonster m = new TorchHead(spawnX + -185F * (float)i, MathUtils.random(-5F, 25F));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_SUMMON"));
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(m, true));
                enemySlots.put(Integer.valueOf(i), m);
            }

            initialSpawn = false;
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            break;

        case 3: // '\003'
            if(AbstractDungeon.ascensionLevel >= 19)
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blockAmt + 5));
            else
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, blockAmt));
            Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            do
            {
                AbstractMonster m;
                do
                {
                    if(!iterator.hasNext())
                        break label0;
                    m = (AbstractMonster)iterator.next();
                } while(m.isDead || m.isDying || m.isEscaping);
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, this, new StrengthPower(m, strAmt), strAmt));
            } while(true);

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[0]));
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 2.0F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, megaDebuffAmt, true), megaDebuffAmt));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, megaDebuffAmt, true), megaDebuffAmt));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new FrailPower(AbstractDungeon.player, megaDebuffAmt, true), megaDebuffAmt));
            ultUsed = true;
            break;

        case 5: // '\005'
            Iterator iterator1 = enemySlots.entrySet().iterator();
            do
            {
                java.util.Map.Entry m;
                do
                {
                    if(!iterator1.hasNext())
                        break label0;
                    m = (java.util.Map.Entry)iterator1.next();
                } while(!((AbstractMonster)m.getValue()).isDying);
                AbstractMonster newMonster = new TorchHead(spawnX + -185F * (float)((Integer)m.getKey()).intValue(), MathUtils.random(-5F, 25F));
                int key = ((Integer)m.getKey()).intValue();
                enemySlots.put(Integer.valueOf(key), newMonster);
                AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(newMonster, true));
            } while(true);

        default:
            logger.info((new StringBuilder()).append("ERROR: Default Take Turn was called on ").append(name).toString());
            break;
        }
        turnsTaken++;
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    protected void getMove(int num)
    {
        if(initialSpawn)
        {
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            return;
        }
        if(turnsTaken >= 3 && !ultUsed)
        {
            setMove((byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.STRONG_DEBUFF);
            return;
        }
        if(num <= 25 && isMinionDead() && !lastMove((byte)5))
        {
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            return;
        }
        if(num <= 70 && !lastTwoMoves((byte)2))
        {
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
            return;
        }
        if(!lastMove((byte)3))
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
        else
            setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base);
    }

    private boolean isMinionDead()
    {
        for(Iterator iterator = enemySlots.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry m = (java.util.Map.Entry)iterator.next();
            if(((AbstractMonster)m.getValue()).isDying)
                return true;
        }

        return false;
    }

    public void update()
    {
        super.update();
        if(!isDying)
        {
            fireTimer -= Gdx.graphics.getDeltaTime();
            if(fireTimer < 0.0F)
            {
                fireTimer = 0.07F;
                AbstractDungeon.effectList.add(new GlowyFireEyesEffect(skeleton.getX() + skeleton.findBone("lefteyefireslot").getX(), skeleton.getY() + skeleton.findBone("lefteyefireslot").getY() + 140F * Settings.scale));
                AbstractDungeon.effectList.add(new GlowyFireEyesEffect(skeleton.getX() + skeleton.findBone("righteyefireslot").getX(), skeleton.getY() + skeleton.findBone("righteyefireslot").getY() + 140F * Settings.scale));
                AbstractDungeon.effectList.add(new StaffFireEffect((skeleton.getX() + skeleton.findBone("fireslot").getX()) - 120F * Settings.scale, skeleton.getY() + skeleton.findBone("fireslot").getY() + 390F * Settings.scale));
            }
        }
    }

    public void die()
    {
        useFastShakeAnimation(5F);
        CardCrawlGame.screenShake.rumble(4F);
        deathTimer += 1.5F;
        super.die();
        onBossVictoryLogic();
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
                AbstractDungeon.actionManager.addToTop(new VFXAction(m, new InflameEffect(m), 0.2F));
            }
        } while(true);
        UnlockTracker.hardUnlockOverride("COLLECTOR");
        UnlockTracker.unlockAchievement("COLLECTOR");
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/city/TheCollector.getName());
    public static final String ID = "TheCollector";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final int HP = 282;
    public static final int A_2_HP = 300;
    private static final int FIREBALL_DMG = 18;
    private static final int STR_AMT = 3;
    private static final int BLOCK_AMT = 15;
    private static final int A_2_FIREBALL_DMG = 21;
    private static final int A_2_STR_AMT = 4;
    private static final int A_2_BLOCK_AMT = 18;
    private int rakeDmg;
    private int strAmt;
    private int blockAmt;
    private int megaDebuffAmt;
    private static final int MEGA_DEBUFF_AMT = 3;
    private int turnsTaken;
    private float spawnX;
    private float fireTimer;
    private static final float FIRE_TIME = 0.07F;
    private boolean ultUsed;
    private boolean initialSpawn;
    private HashMap enemySlots;
    private static final byte SPAWN = 1;
    private static final byte FIREBALL = 2;
    private static final byte BUFF = 3;
    private static final byte MEGA_DEBUFF = 4;
    private static final byte REVIVE = 5;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("TheCollector");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
    }
}
