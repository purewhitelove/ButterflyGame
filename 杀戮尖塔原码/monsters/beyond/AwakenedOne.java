// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AwakenedOne.java

package com.megacrit.cardcrawl.monsters.beyond;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.animations.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.blights.Shield;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Colossus;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.*;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AwakenedOne extends AbstractMonster
{

    public AwakenedOne(float x, float y)
    {
        super(NAME, "AwakenedOne", 300, 40F, -30F, 460F, 250F, null, x, y);
        form1 = true;
        firstTurn = true;
        saidPower = false;
        fireTimer = 0.0F;
        animateParticles = false;
        wParticles = new ArrayList();
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(320);
        else
            setHp(300);
        loadAnimation("images/monsters/theForest/awakenedOne/skeleton.atlas", "images/monsters/theForest/awakenedOne/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle_1", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        stateData.setMix("Hit", "Idle_1", 0.3F);
        stateData.setMix("Hit", "Idle_2", 0.2F);
        stateData.setMix("Attack_1", "Idle_1", 0.2F);
        stateData.setMix("Attack_2", "Idle_2", 0.2F);
        state.getData().setMix("Idle_1", "Idle_2", 1.0F);
        eye = skeleton.findBone("Eye");
        Bone b;
        for(Iterator iterator = skeleton.getBones().iterator(); iterator.hasNext(); logger.info(b.getData().getName()))
            b = (Bone)iterator.next();

        back = skeleton.findBone("Hips");
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        dialogX = -200F * Settings.scale;
        dialogY = 10F * Settings.scale;
        damage.add(new DamageInfo(this, 20));
        damage.add(new DamageInfo(this, 6));
        damage.add(new DamageInfo(this, 40));
        damage.add(new DamageInfo(this, 18));
        damage.add(new DamageInfo(this, 10));
    }

    public void usePreBattleAction()
    {
        CardCrawlGame.music.unsilenceBGM();
        AbstractDungeon.scene.fadeOutAmbiance();
        AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        AbstractDungeon.getCurrRoom().cannotLose = true;
        if(AbstractDungeon.ascensionLevel >= 19)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 15)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CuriosityPower(this, 2)));
        } else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new RegenerateMonsterPower(this, 10)));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new CuriosityPower(this, 1)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new UnawakenedPower(this)));
        if(AbstractDungeon.ascensionLevel >= 4)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
        UnlockTracker.markBossAsSeen("CROW");
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 4: // '\004'
        case 7: // '\007'
        default:
            break;

        case 1: // '\001'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AWAKENED_POUNCE"));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_1"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            break;

        case 2: // '\002'
            for(int i = 0; i < 4; i++)
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));

            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_1"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new IntenseZoomEffect(hb.cX, hb.cY, true), 0.05F, true));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "REBIRTH"));
            break;

        case 5: // '\005'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_2"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
            firstTurn = false;
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_AWAKENEDONE_3"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(hb.cX, hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ShockWaveEffect(hb.cX, hb.cY, new Color(0.3F, 0.2F, 0.4F, 1.0F), com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect.ShockWaveType.CHAOTIC), 1.0F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SMASH));
            break;

        case 6: // '\006'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK_2"));
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(3), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON));
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
            break;

        case 8: // '\b'
            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_AWAKENED_ATTACK"));
            for(int i = 0; i < 3; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.06F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(4), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, true));
            }

            break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    public void changeState(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 1799477836: 
            if(s.equals("REBIRTH"))
                byte0 = 0;
            break;

        case 1321368570: 
            if(s.equals("ATTACK_1"))
                byte0 = 1;
            break;

        case 1321368571: 
            if(s.equals("ATTACK_2"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        default:
            break;

        case 0: // '\0'
            if(AbstractDungeon.ascensionLevel >= 9)
                maxHealth = 320;
            else
                maxHealth = 300;
            if(Settings.isEndless && AbstractDungeon.player.hasBlight("ToughEnemies"))
            {
                float mod = AbstractDungeon.player.getBlight("ToughEnemies").effectFloat();
                maxHealth = (int)((float)maxHealth * mod);
            }
            if(ModHelper.isModEnabled("MonsterHunter"))
                currentHealth = (int)((float)currentHealth * 1.5F);
            state.setAnimation(0, "Idle_2", true);
            halfDead = false;
            animateParticles = true;
            AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, maxHealth));
            AbstractDungeon.actionManager.addToBottom(new CanLoseAction());
            break;

        case 1: // '\001'
            state.setAnimation(0, "Attack_1", false);
            state.addAnimation(0, "Idle_1", true, 0.0F);
            break;

        case 2: // '\002'
            state.setAnimation(0, "Attack_2", false);
            state.addAnimation(0, "Idle_2", true, 0.0F);
            break;
        }
    }

    protected void getMove(int num)
    {
        if(form1)
        {
            if(firstTurn)
            {
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 20);
                firstTurn = false;
                return;
            }
            if(num < 25)
            {
                if(!lastMove((byte)2))
                    setMove(SS_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 6, 4, true);
                else
                    setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 20);
            } else
            if(!lastTwoMoves((byte)1))
                setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 20);
            else
                setMove(SS_NAME, (byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 6, 4, true);
        } else
        {
            if(firstTurn)
            {
                setMove(DARK_ECHO_NAME, (byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 40);
                return;
            }
            if(num < 50)
            {
                if(!lastTwoMoves((byte)6))
                    setMove(SLUDGE_NAME, (byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, 18);
                else
                    setMove((byte)8, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 10, 3, true);
            } else
            if(!lastTwoMoves((byte)8))
                setMove((byte)8, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, 10, 3, true);
            else
                setMove(SLUDGE_NAME, (byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, 18);
        }
    }

    public void damage(DamageInfo info)
    {
        super.damage(info);
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output > 0)
        {
            state.setAnimation(0, "Hit", false);
            if(form1)
                state.addAnimation(0, "Idle_1", true, 0.0F);
            else
                state.addAnimation(0, "Idle_2", true, 0.0F);
        }
        if(currentHealth <= 0 && !halfDead)
        {
            if(AbstractDungeon.getCurrRoom().cannotLose)
                halfDead = true;
            AbstractPower p;
            for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.onDeath())
                p = (AbstractPower)iterator.next();

            AbstractRelic r;
            for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onMonsterDeath(this))
                r = (AbstractRelic)iterator1.next();

            addToTop(new ClearCardQueueAction());
            Iterator s = powers.iterator();
            do
            {
                if(!s.hasNext())
                    break;
                AbstractPower p = (AbstractPower)s.next();
                if(p.type == com.megacrit.cardcrawl.powers.AbstractPower.PowerType.DEBUFF || p.ID.equals("Curiosity") || p.ID.equals("Unawakened") || p.ID.equals("Shackled"))
                    s.remove();
            } while(true);
            setMove((byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
            createIntent();
            AbstractDungeon.actionManager.addToBottom(new ShoutAction(this, DIALOG[0]));
            AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN));
            applyPowers();
            firstTurn = true;
            form1 = false;
            if(GameActionManager.turn <= 1)
                UnlockTracker.unlockAchievement("YOU_ARE_NOTHING");
        }
    }

    public void update()
    {
        super.update();
        if(!isDying && animateParticles)
        {
            fireTimer -= Gdx.graphics.getDeltaTime();
            if(fireTimer < 0.0F)
            {
                fireTimer = 0.1F;
                AbstractDungeon.effectList.add(new AwakenedEyeParticle(skeleton.getX() + eye.getWorldX(), skeleton.getY() + eye.getWorldY()));
                wParticles.add(new AwakenedWingParticle());
            }
        }
        Iterator p = wParticles.iterator();
        do
        {
            if(!p.hasNext())
                break;
            AwakenedWingParticle e = (AwakenedWingParticle)p.next();
            e.update();
            if(e.isDone)
                p.remove();
        } while(true);
    }

    public void render(SpriteBatch sb)
    {
        Iterator iterator = wParticles.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AwakenedWingParticle p = (AwakenedWingParticle)iterator.next();
            if(p.renderBehind)
                p.render(sb, skeleton.getX() + back.getWorldX(), skeleton.getY() + back.getWorldY());
        } while(true);
        super.render(sb);
        iterator = wParticles.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AwakenedWingParticle p = (AwakenedWingParticle)iterator.next();
            if(!p.renderBehind)
                p.render(sb, skeleton.getX() + back.getWorldX(), skeleton.getY() + back.getWorldY());
        } while(true);
    }

    public void die()
    {
        if(!AbstractDungeon.getCurrRoom().cannotLose)
        {
            super.die();
            useFastShakeAnimation(5F);
            CardCrawlGame.screenShake.rumble(4F);
            if(saidPower)
            {
                CardCrawlGame.sound.play("VO_AWAKENEDONE_2");
                AbstractDungeon.effectList.add(new SpeechBubble(hb.cX + dialogX, hb.cY + dialogY, 2.5F, DIALOG[1], false));
                saidPower = true;
            }
            Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(!m.isDying && (m instanceof Cultist))
                    AbstractDungeon.actionManager.addToBottom(new EscapeAction(m));
            } while(true);
            onBossVictoryLogic();
            UnlockTracker.hardUnlockOverride("CROW");
            UnlockTracker.unlockAchievement("CROW");
            onFinalBossVictoryLogic();
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/beyond/AwakenedOne.getName());
    public static final String ID = "AwakenedOne";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    private boolean form1;
    private boolean firstTurn;
    private boolean saidPower;
    public static final int STAGE_1_HP = 300;
    public static final int STAGE_2_HP = 300;
    public static final int A_9_STAGE_1_HP = 320;
    public static final int A_9_STAGE_2_HP = 320;
    private static final int A_4_STR = 2;
    private static final byte SLASH = 1;
    private static final byte SOUL_STRIKE = 2;
    private static final byte REBIRTH = 3;
    private static final String SS_NAME;
    private static final int SLASH_DMG = 20;
    private static final int SS_DMG = 6;
    private static final int SS_AMT = 4;
    private static final int REGEN_AMT = 10;
    private static final int STR_AMT = 1;
    private static final byte DARK_ECHO = 5;
    private static final byte SLUDGE = 6;
    private static final byte TACKLE = 8;
    private static final String DARK_ECHO_NAME;
    private static final String SLUDGE_NAME;
    private static final int ECHO_DMG = 40;
    private static final int SLUDGE_DMG = 18;
    private static final int TACKLE_DMG = 10;
    private static final int TACKLE_AMT = 3;
    private float fireTimer;
    private static final float FIRE_TIME = 0.1F;
    private Bone eye;
    private Bone back;
    private boolean animateParticles;
    private ArrayList wParticles;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("AwakenedOne");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        SS_NAME = MOVES[0];
        DARK_ECHO_NAME = MOVES[1];
        SLUDGE_NAME = MOVES[3];
    }
}
