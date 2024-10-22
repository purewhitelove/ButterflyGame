// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SpireHeart.java

package com.megacrit.cardcrawl.events.beyond;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.Skeleton;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.screens.*;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.DamageHeartEffect;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpireHeart extends AbstractEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/beyond/SpireHeart$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN MIDDLE;
        public static final CUR_SCREEN MIDDLE_2;
        public static final CUR_SCREEN DEATH;
        public static final CUR_SCREEN GO_TO_ENDING;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            MIDDLE = new CUR_SCREEN("MIDDLE", 1);
            MIDDLE_2 = new CUR_SCREEN("MIDDLE_2", 2);
            DEATH = new CUR_SCREEN("DEATH", 3);
            GO_TO_ENDING = new CUR_SCREEN("GO_TO_ENDING", 4);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, MIDDLE, MIDDLE_2, DEATH, GO_TO_ENDING
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public SpireHeart()
    {
        screen = CUR_SCREEN.INTRO;
        npc = null;
        x = 1300F * Settings.xScale;
        y = (float)Settings.HEIGHT / 2.0F + 40F * Settings.scale;
        startHeartAnimation = false;
        eventFadeTimer = 3F;
        fadeColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        npc = new AnimatedNpc(1300F * Settings.xScale, AbstractDungeon.floorY - 80F * Settings.scale, "images/npcs/heart/skeleton.atlas", "images/npcs/heart/skeleton.json", "idle");
        npc.setTimeScale(1.5F);
        npc.addListener(new HeartAnimListener());
        body = DESCRIPTIONS[0];
        roomEventText.clear();
        roomEventText.addDialogOption(OPTIONS[0]);
        hasDialog = true;
        hasFocus = true;
        globalDamageDealt = CardCrawlGame.publisherIntegration.getGlobalStat("test_stat");
        GameOverScreen.resetScoreChecks();
        damageDealt = GameOverScreen.calcScore(true);
        CardCrawlGame.publisherIntegration.incrementStat("test_stat", damageDealt);
        String winStreakStatId;
        if(Settings.isBeta)
            winStreakStatId = (new StringBuilder()).append(AbstractDungeon.player.getWinStreakKey()).append("_BETA").toString();
        else
            winStreakStatId = AbstractDungeon.player.getWinStreakKey();
        CardCrawlGame.publisherIntegration.incrementStat(winStreakStatId, 1);
        logger.info((new StringBuilder()).append("WIN STREAK  ").append(CardCrawlGame.publisherIntegration.getStat(winStreakStatId)).toString());
        totalDamageDealt = CardCrawlGame.publisherIntegration.getStat("test_stat");
        boolean skipUpload = Settings.isModded || !Settings.isStandardRun();
        if(!skipUpload)
        {
            winstreak = CardCrawlGame.publisherIntegration.getStat(winStreakStatId);
            String leaderboardWinStreakStatId;
            if(Settings.isBeta)
                leaderboardWinStreakStatId = (new StringBuilder()).append(AbstractDungeon.player.getLeaderboardWinStreakKey()).append("_BETA").toString();
            else
                leaderboardWinStreakStatId = AbstractDungeon.player.getLeaderboardWinStreakKey();
            CardCrawlGame.publisherIntegration.uploadLeaderboardScore(leaderboardWinStreakStatId, winstreak);
        }
        CardCrawlGame.playerPref.putInteger("DMG_DEALT", damageDealt + CardCrawlGame.playerPref.getInteger("DMG_DEALT", 0));
        if(totalDamageDealt <= 0L)
            totalDamageDealt = CardCrawlGame.playerPref.getInteger("DMG_DEALT", 0);
    }

    private void goToFinalAct()
    {
        fadeColor.a = 0.0F;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DOOR_UNLOCK;
        CardCrawlGame.mainMenuScreen.doorUnlockScreen.open(true);
    }

    public void update()
    {
        super.update();
        if(!RoomEventDialog.waitForInput)
            buttonEffect(roomEventText.getSelectedOption());
        if(startHeartAnimation && hasFocus)
        {
            eventFadeTimer -= Gdx.graphics.getDeltaTime();
            if(eventFadeTimer < 0.0F)
                eventFadeTimer = 0.0F;
            npc.skeleton.setY(Interpolation.pow2Out.apply(Settings.HEIGHT, AbstractDungeon.floorY - 80F * Settings.scale, eventFadeTimer / 3F));
            fadeColor.a = MathHelper.slowColorLerpSnap(fadeColor.a, 1.0F);
            npc.setTimeScale(Interpolation.fade.apply(0.8F, 1.5F, eventFadeTimer / 3F));
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$beyond$SpireHeart$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$beyond$SpireHeart$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SpireHeart$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SpireHeart$CUR_SCREEN[CUR_SCREEN.MIDDLE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SpireHeart$CUR_SCREEN[CUR_SCREEN.MIDDLE_2.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SpireHeart$CUR_SCREEN[CUR_SCREEN.DEATH.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$SpireHeart$CUR_SCREEN[CUR_SCREEN.GO_TO_ENDING.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.beyond.SpireHeart.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            screen = CUR_SCREEN.MIDDLE;
            roomEventText.updateBodyText(AbstractDungeon.player.getSpireHeartText());
            roomEventText.updateDialogOption(0, OPTIONS[1]);
            break;

        case 2: // '\002'
            screen = CUR_SCREEN.MIDDLE_2;
            roomEventText.updateBodyText((new StringBuilder()).append(DESCRIPTIONS[1]).append(damageDealt).append(DESCRIPTIONS[2]).toString());
            roomEventText.updateDialogOption(0, OPTIONS[0]);
            Color color = AbstractDungeon.player.getSlashAttackColor();
            com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect effects[] = AbstractDungeon.player.getSpireHeartSlashEffect();
            int HITS = effects.length;
            int damagePerTick = damageDealt / HITS;
            int remainder = damageDealt % HITS;
            int damages[] = new int[HITS];
            for(int i = 0; i < HITS; i++)
            {
                damages[i] = damagePerTick;
                if(remainder > 0)
                {
                    damages[i]++;
                    remainder--;
                }
            }

            float tmp = 0.0F;
            AbstractDungeon.effectList.add(new BorderFlashEffect(color, true));
            for(int i = 0; i < HITS; i++)
            {
                tmp += MathUtils.random(0.05F, 0.2F);
                AbstractDungeon.effectList.add(new DamageHeartEffect(tmp, x, y, effects[i], damages[i]));
            }

            break;

        case 3: // '\003'
            if(Settings.isFinalActAvailable && Settings.hasRubyKey && Settings.hasEmeraldKey && Settings.hasSapphireKey)
            {
                startHeartAnimation = true;
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.XLONG, false);
                CardCrawlGame.screenShake.rumble(5F);
                screen = CUR_SCREEN.GO_TO_ENDING;
                roomEventText.updateBodyText((new StringBuilder()).append(DESCRIPTIONS[11]).append(DESCRIPTIONS[12]).append(DESCRIPTIONS[13]).append(DESCRIPTIONS[14]).toString());
                roomEventText.updateDialogOption(0, OPTIONS[3]);
                break;
            }
            screen = CUR_SCREEN.DEATH;
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
            if(globalDamageDealt <= 0L)
                roomEventText.updateBodyText((new StringBuilder()).append(DESCRIPTIONS[3]).append(numberFormat.format(totalDamageDealt)).append(DESCRIPTIONS[4]).append(DESCRIPTIONS[7]).toString());
            else
                roomEventText.updateBodyText((new StringBuilder()).append(DESCRIPTIONS[3]).append(numberFormat.format(totalDamageDealt)).append(DESCRIPTIONS[4]).append(DESCRIPTIONS[5]).append(numberFormat.format(globalDamageDealt)).append(DESCRIPTIONS[6]).append(DESCRIPTIONS[7]).toString());
            roomEventText.updateDialogOption(0, OPTIONS[2]);
            break;

        case 4: // '\004'
            AbstractDungeon.player.isDying = true;
            hasFocus = false;
            roomEventText.hide();
            AbstractDungeon.player.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(null);
            break;

        case 5: // '\005'
            roomEventText.clear();
            hasFocus = false;
            roomEventText.hide();
            goToFinalAct();
            break;

        default:
            logger.info("WHY YOU CALLED?");
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(fadeColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if(npc != null)
            npc.render(sb);
    }

    public void dispose()
    {
        super.dispose();
        if(npc != null)
        {
            npc.dispose();
            npc = null;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/events/beyond/SpireHeart.getName());
    public static final String ID = "Spire Heart";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private CUR_SCREEN screen;
    private AnimatedNpc npc;
    private float x;
    private float y;
    private boolean startHeartAnimation;
    private float eventFadeTimer;
    private Color fadeColor;
    private long globalDamageDealt;
    private long totalDamageDealt;
    private int damageDealt;
    private int winstreak;
    private static final String HEART_DMG_KEY = "test_stat";

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Spire Heart");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
    }
}
