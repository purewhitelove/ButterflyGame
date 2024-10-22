// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Hexaghost.java

package com.megacrit.cardcrawl.monsters.exordium;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.BurnIncreaseAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.monsters.exordium:
//            HexaghostBody, HexaghostOrb

public class Hexaghost extends AbstractMonster
{

    public Hexaghost()
    {
        super(NAME, "Hexaghost", 250, 20F, 0.0F, 450F, 450F, "images/monsters/theBottom/boss/ghost/core.png");
        orbs = new ArrayList();
        strengthenBlockAmt = 12;
        fireTackleCount = 2;
        infernoHits = 6;
        activated = false;
        burnUpgraded = false;
        orbActiveCount = 0;
        type = com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;
        body = new HexaghostBody(this);
        disposables.add(body);
        createOrbs();
        if(AbstractDungeon.ascensionLevel >= 9)
            setHp(264);
        else
            setHp(250);
        if(AbstractDungeon.ascensionLevel >= 19)
        {
            strAmount = 3;
            searBurnCount = 2;
            fireTackleDmg = 6;
            infernoDmg = 3;
        } else
        if(AbstractDungeon.ascensionLevel >= 4)
        {
            strAmount = 2;
            searBurnCount = 1;
            fireTackleDmg = 6;
            infernoDmg = 3;
        } else
        {
            strAmount = 2;
            searBurnCount = 1;
            fireTackleDmg = 5;
            infernoDmg = 2;
        }
        searDmg = 6;
        damage.add(new DamageInfo(this, fireTackleDmg));
        damage.add(new DamageInfo(this, searDmg));
        damage.add(new DamageInfo(this, -1));
        damage.add(new DamageInfo(this, infernoDmg));
    }

    public void usePreBattleAction()
    {
        UnlockTracker.markBossAsSeen("GHOST");
        CardCrawlGame.music.precacheTempBgm("BOSS_BOTTOM");
    }

    private void createOrbs()
    {
        orbs.add(new HexaghostOrb(-90F, 380F, orbs.size()));
        orbs.add(new HexaghostOrb(90F, 380F, orbs.size()));
        orbs.add(new HexaghostOrb(160F, 250F, orbs.size()));
        orbs.add(new HexaghostOrb(90F, 120F, orbs.size()));
        orbs.add(new HexaghostOrb(-90F, 120F, orbs.size()));
        orbs.add(new HexaghostOrb(-160F, 250F, orbs.size()));
    }

    public void takeTurn()
    {
        switch(nextMove)
        {
        case 5: // '\005'
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Activate"));
            int d = AbstractDungeon.player.currentHealth / 12 + 1;
            ((DamageInfo)damage.get(2)).base = d;
            applyPowers();
            setMove((byte)1, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(2)).base, 6, true);
            break;

        case 1: // '\001'
            for(int i = 0; i < 6; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new GhostIgniteEffect(AbstractDungeon.player.hb.cX + MathUtils.random(-120F, 120F) * Settings.scale, AbstractDungeon.player.hb.cY + MathUtils.random(-120F, 120F) * Settings.scale), 0.05F));
                if(MathUtils.randomBoolean())
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("GHOST_ORB_IGNITE_1", 0.3F));
                else
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("GHOST_ORB_IGNITE_2", 0.3F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(2), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
            }

            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Deactivate"));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 2: // '\002'
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.CHARTREUSE)));
            AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(0), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Activate Orb"));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 4: // '\004'
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new FireballEffect(hb.cX, hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(1), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));
            Burn c = new Burn();
            if(burnUpgraded)
                c.upgrade();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(c, searBurnCount));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Activate Orb"));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 3: // '\003'
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.5F));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, strengthenBlockAmt));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, strAmount), strAmount));
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Activate Orb"));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        case 6: // '\006'
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new ScreenOnFireEffect(), 1.0F));
            for(int i = 0; i < infernoHits; i++)
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, (DamageInfo)damage.get(3), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE));

            AbstractDungeon.actionManager.addToBottom(new BurnIncreaseAction());
            if(!burnUpgraded)
                burnUpgraded = true;
            AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "Deactivate"));
            AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
            break;

        default:
            logger.info((new StringBuilder()).append("ERROR: Default Take Turn was called on ").append(name).toString());
            break;
        }
    }

    protected void getMove(int num)
    {
        if(!activated)
        {
            activated = true;
            setMove((byte)5, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.UNKNOWN);
        } else
        {
            switch(orbActiveCount)
            {
            case 0: // '\0'
                setMove(SEAR_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                break;

            case 1: // '\001'
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, fireTackleCount, true);
                break;

            case 2: // '\002'
                setMove(SEAR_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                break;

            case 3: // '\003'
                setMove(STRENGTHEN_NAME, (byte)3, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.DEFEND_BUFF);
                break;

            case 4: // '\004'
                setMove((byte)2, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK, ((DamageInfo)damage.get(0)).base, fireTackleCount, true);
                break;

            case 5: // '\005'
                setMove(SEAR_NAME, (byte)4, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(1)).base);
                break;

            case 6: // '\006'
                setMove(BURN_NAME, (byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(3)).base, infernoHits, true);
                break;
            }
        }
    }

    public void changeState(String stateName)
    {
        String s = stateName;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1591330541: 
            if(s.equals("Activate"))
                byte0 = 0;
            break;

        case -1031980494: 
            if(s.equals("Activate Orb"))
                byte0 = 1;
            break;

        case -3298156: 
            if(s.equals("Deactivate"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        default:
            break;

        case 0: // '\0'
            if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
            {
                CardCrawlGame.music.unsilenceBGM();
                AbstractDungeon.scene.fadeOutAmbiance();
                CardCrawlGame.music.playPrecachedTempBgm();
            }
            HexaghostOrb orb;
            for(Iterator iterator = orbs.iterator(); iterator.hasNext(); orb.activate(drawX + animX, drawY + animY))
                orb = (HexaghostOrb)iterator.next();

            orbActiveCount = 6;
            body.targetRotationSpeed = 120F;
            break;

        case 1: // '\001'
            Iterator iterator1 = orbs.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                HexaghostOrb orb = (HexaghostOrb)iterator1.next();
                if(orb.activated)
                    continue;
                orb.activate(drawX + animX, drawY + animY);
                break;
            } while(true);
            orbActiveCount++;
            if(orbActiveCount == 6)
                setMove(BURN_NAME, (byte)6, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)damage.get(3)).base, infernoHits, true);
            break;

        case 2: // '\002'
            HexaghostOrb orb;
            for(Iterator iterator2 = orbs.iterator(); iterator2.hasNext(); orb.deactivate())
                orb = (HexaghostOrb)iterator2.next();

            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
            CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);
            orbActiveCount = 0;
            break;
        }
    }

    public void die()
    {
        useFastShakeAnimation(5F);
        CardCrawlGame.screenShake.rumble(4F);
        super.die();
        HexaghostOrb orb;
        for(Iterator iterator = orbs.iterator(); iterator.hasNext(); orb.hide())
            orb = (HexaghostOrb)iterator.next();

        onBossVictoryLogic();
        UnlockTracker.hardUnlockOverride("GHOST");
        UnlockTracker.unlockAchievement("GHOST_GUARDIAN");
    }

    public void update()
    {
        super.update();
        body.update();
        HexaghostOrb orb;
        for(Iterator iterator = orbs.iterator(); iterator.hasNext(); orb.update(drawX + animX, drawY + animY))
            orb = (HexaghostOrb)iterator.next();

    }

    public void render(SpriteBatch sb)
    {
        body.render(sb);
        super.render(sb);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/exordium/Hexaghost.getName());
    public static final String ID = "Hexaghost";
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String MOVES[];
    public static final String DIALOG[];
    public static final String IMAGE = "images/monsters/theBottom/boss/ghost/core.png";
    private static final int HP = 250;
    private static final int A_2_HP = 264;
    private ArrayList orbs;
    private static final int SEAR_DMG = 6;
    private static final int INFERNO_DMG = 2;
    private static final int FIRE_TACKLE_DMG = 5;
    private static final int BURN_COUNT = 1;
    private static final int STR_AMT = 2;
    private static final int A_4_INFERNO_DMG = 3;
    private static final int A_4_FIRE_TACKLE_DMG = 6;
    private static final int A_19_BURN_COUNT = 2;
    private static final int A_19_STR_AMT = 3;
    private int searDmg;
    private int strengthenBlockAmt;
    private int strAmount;
    private int searBurnCount;
    private int fireTackleDmg;
    private int fireTackleCount;
    private int infernoDmg;
    private int infernoHits;
    private static final byte DIVIDER = 1;
    private static final byte TACKLE = 2;
    private static final byte INFLAME = 3;
    private static final byte SEAR = 4;
    private static final byte ACTIVATE = 5;
    private static final byte INFERNO = 6;
    private static final String STRENGTHEN_NAME;
    private static final String SEAR_NAME;
    private static final String BURN_NAME;
    private static final String ACTIVATE_STATE = "Activate";
    private static final String ACTIVATE_ORB = "Activate Orb";
    private static final String DEACTIVATE_ALL_ORBS = "Deactivate";
    private boolean activated;
    private boolean burnUpgraded;
    private int orbActiveCount;
    private HexaghostBody body;

    static 
    {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Hexaghost");
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;
        STRENGTHEN_NAME = MOVES[0];
        SEAR_NAME = MOVES[1];
        BURN_NAME = MOVES[2];
    }
}
