// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinWheelGame.java

package com.megacrit.cardcrawl.events.shrines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GremlinWheelGame extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/GremlinWheelGame$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN LEAVE;
        public static final CUR_SCREEN SPIN;
        public static final CUR_SCREEN COMPLETE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            LEAVE = new CUR_SCREEN("LEAVE", 1);
            SPIN = new CUR_SCREEN("SPIN", 2);
            COMPLETE = new CUR_SCREEN("COMPLETE", 3);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, LEAVE, SPIN, COMPLETE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public GremlinWheelGame()
    {
        super(NAME, INTRO_DIALOG, "images/events/spinTheWheel.jpg");
        screen = CUR_SCREEN.INTRO;
        startSpin = false;
        finishSpin = false;
        doneSpinning = false;
        bounceIn = true;
        bounceTimer = 1.0F;
        animTimer = 3F;
        spinVelocity = 200F;
        purgeResult = false;
        buttonPressed = false;
        buttonHb = new Hitbox(450F * Settings.scale, 300F * Settings.scale);
        imgX = (float)Settings.WIDTH / 2.0F;
        imgY = START_Y;
        wheelAngle = 0.0F;
        color = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        hpLossPercent = 0.1F;
        wheelImg = ImageMaster.loadImage("images/events/wheel.png");
        arrowImg = ImageMaster.loadImage("images/events/wheelArrow.png");
        buttonImg = ImageMaster.loadImage("images/events/spinButton.png");
        noCardsInRewards = true;
        if(AbstractDungeon.ascensionLevel >= 15)
            hpLossPercent = 0.15F;
        imageEventText.setDialogOption(OPTIONS[0]);
        setGold();
        hasDialog = true;
        hasFocus = true;
        buttonHb.move(500F * Settings.scale, -500F * Settings.scale);
    }

    private void setGold()
    {
        if(Objects.equals(AbstractDungeon.id, "Exordium"))
            goldAmount = 100;
        else
        if(Objects.equals(AbstractDungeon.id, "TheCity"))
            goldAmount = 200;
        else
        if(Objects.equals(AbstractDungeon.id, "TheBeyond"))
            goldAmount = 300;
    }

    public void update()
    {
        super.update();
        updatePosition();
        purgeLogic();
        if(bounceTimer == 0.0F && startSpin)
            if(!buttonPressed)
            {
                buttonHb.cY = MathHelper.cardLerpSnap(buttonHb.cY, Settings.OPTION_Y - 330F * Settings.scale);
                buttonHb.move(buttonHb.cX, buttonHb.cY);
                buttonHb.update();
                if(buttonHb.hovered && InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed())
                {
                    buttonPressed = true;
                    buttonHb.hovered = false;
                    CardCrawlGame.sound.play("WHEEL");
                }
            } else
            {
                buttonHb.cY = MathHelper.cardLerpSnap(buttonHb.cY, -500F * Settings.scale);
            }
        if(startSpin && bounceTimer == 0.0F && buttonPressed)
        {
            imgY = TARGET_Y;
            if(animTimer > 0.0F)
            {
                animTimer -= Gdx.graphics.getDeltaTime();
                wheelAngle += spinVelocity * Gdx.graphics.getDeltaTime();
            } else
            {
                finishSpin = true;
                animTimer = 3F;
                startSpin = false;
                tmpAngle = resultAngle;
            }
        } else
        if(finishSpin)
        {
            if(animTimer > 0.0F)
            {
                animTimer -= Gdx.graphics.getDeltaTime();
                if(animTimer < 0.0F)
                {
                    animTimer = 1.0F;
                    finishSpin = false;
                    doneSpinning = true;
                }
                wheelAngle = Interpolation.elasticIn.apply(tmpAngle, -180F, animTimer / 3F);
            }
        } else
        if(doneSpinning)
            if(animTimer > 0.0F)
            {
                animTimer -= Gdx.graphics.getDeltaTime();
                if(animTimer <= 0.0F)
                {
                    bounceTimer = 1.0F;
                    bounceIn = false;
                }
            } else
            if(bounceTimer == 0.0F)
            {
                doneSpinning = false;
                imageEventText.clearAllDialogs();
                preApplyResult();
                GenericEventDialog.show();
                screen = CUR_SCREEN.COMPLETE;
            }
        if(!GenericEventDialog.waitForInput)
            buttonEffect(GenericEventDialog.getSelectedOption());
    }

    private void updatePosition()
    {
        if(bounceTimer != 0.0F)
        {
            bounceTimer -= Gdx.graphics.getDeltaTime();
            if(bounceTimer < 0.0F)
                bounceTimer = 0.0F;
            if(bounceIn && startSpin)
            {
                color.a = Interpolation.fade.apply(1.0F, 0.0F, bounceTimer);
                imgY = Interpolation.bounceIn.apply(TARGET_Y, START_Y, bounceTimer);
            } else
            if(doneSpinning)
            {
                color.a = Interpolation.fade.apply(0.0F, 1.0F, bounceTimer);
                imgY = Interpolation.swingOut.apply(START_Y, TARGET_Y, bounceTimer);
            }
        }
    }

    private void preApplyResult()
    {
        switch(result)
        {
        case 0: // '\0'
            imageEventText.updateBodyText(DESCRIPTIONS[1]);
            imageEventText.setDialogOption(OPTIONS[1]);
            AbstractDungeon.effectList.add(new RainingGoldEffect(goldAmount));
            AbstractDungeon.player.gainGold(goldAmount);
            break;

        case 1: // '\001'
            imageEventText.updateBodyText(DESCRIPTIONS[2]);
            imageEventText.setDialogOption(OPTIONS[2]);
            break;

        case 2: // '\002'
            imageEventText.updateBodyText(DESCRIPTIONS[3]);
            imageEventText.setDialogOption(OPTIONS[3]);
            break;

        case 3: // '\003'
            imageEventText.updateBodyText(DESCRIPTIONS[4]);
            imageEventText.setDialogOption(OPTIONS[4]);
            break;

        case 4: // '\004'
            imageEventText.updateBodyText(DESCRIPTIONS[5]);
            imageEventText.setDialogOption(OPTIONS[5]);
            break;

        default:
            imageEventText.updateBodyText(DESCRIPTIONS[6]);
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[6]).append((int)((float)AbstractDungeon.player.maxHealth * hpLossPercent)).append(OPTIONS[7]).toString());
            color = new Color(0.5F, 0.5F, 0.5F, 1.0F);
            break;
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinWheelGame$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinWheelGame$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinWheelGame$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinWheelGame$CUR_SCREEN[CUR_SCREEN.COMPLETE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinWheelGame$CUR_SCREEN[CUR_SCREEN.LEAVE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.GremlinWheelGame.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            if(buttonPressed == 0)
            {
                GenericEventDialog.hide();
                result = AbstractDungeon.miscRng.random(0, 5);
                resultAngle = (float)result * 60F + MathUtils.random(-10F, 10F);
                wheelAngle = 0.0F;
                startSpin = true;
                bounceTimer = 2.0F;
                animTimer = 2.0F;
                spinVelocity = 1500F;
            }
            break;

        case 2: // '\002'
            applyResult();
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption(OPTIONS[8]);
            screen = CUR_SCREEN.LEAVE;
            break;

        case 3: // '\003'
            openMap();
            break;

        default:
            logger.info("UNHANDLED CASE");
            break;
        }
    }

    private void applyResult()
    {
        switch(result)
        {
        case 0: // '\0'
            hasFocus = false;
            logMetricGainGold("Wheel of Change", "Gold", goldAmount);
            break;

        case 1: // '\001'
            AbstractDungeon.getCurrRoom().rewards.clear();
            AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
            AbstractDungeon.getCurrRoom().addRelicToRewards(r);
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.combatRewardScreen.open();
            logMetric("Wheel of Change", "Relic");
            hasFocus = false;
            break;

        case 2: // '\002'
            logMetricHeal("Wheel of Change", "Full Heal", AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
            AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
            hasFocus = false;
            break;

        case 3: // '\003'
            AbstractCard curse = new Decay();
            logMetricObtainCard("Wheel of Change", "Cursed", curse);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            hasFocus = false;
            break;

        case 4: // '\004'
            if(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).size() > 0)
            {
                AbstractDungeon.gridSelectScreen.open(CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()), 1, OPTIONS[9], false, false, false, true);
                roomEventText.hide();
                purgeResult = true;
            }
            break;

        default:
            imageEventText.updateBodyText(DESCRIPTIONS[7]);
            CardCrawlGame.sound.play("ATTACK_DAGGER_6");
            CardCrawlGame.sound.play("BLOOD_SPLAT");
            int damageAmount = (int)((float)AbstractDungeon.player.maxHealth * hpLossPercent);
            AbstractDungeon.player.damage(new DamageInfo(null, damageAmount, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
            logMetricTakeDamage("Wheel of Change", "Damaged", damageAmount);
            break;
        }
    }

    private void purgeLogic()
    {
        if(purgeResult && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            logMetricCardRemoval("Wheel of Change", "Card Removal", c);
            AbstractDungeon.player.masterDeck.removeCard(c);
            AbstractDungeon.effectList.add(new PurgeCardEffect(c));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            hasFocus = false;
            purgeResult = false;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(color);
        sb.draw(wheelImg, imgX - 512F, imgY - 512F, 512F, 512F, 1024F, 1024F, Settings.scale, Settings.scale, wheelAngle, 0, 0, 1024, 1024, false, false);
        sb.draw(arrowImg, (imgX - 256F) + ARROW_OFFSET_X + 180F * Settings.scale, imgY - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        if(buttonHb.hovered)
            sb.draw(buttonImg, buttonHb.cX - 256F, buttonHb.cY - 256F, 256F, 256F, 512F, 512F, Settings.scale * 1.05F, Settings.scale * 1.05F, 0.0F, 0, 0, 512, 512, false, false);
        else
            sb.draw(buttonImg, buttonHb.cX - 256F, buttonHb.cY - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        sb.setBlendFunction(770, 1);
        if(buttonHb.hovered)
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
        else
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, (MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) + 1.25F) / 3.5F));
        if(buttonHb.hovered)
            sb.draw(buttonImg, buttonHb.cX - 256F, buttonHb.cY - 256F, 256F, 256F, 512F, 512F, Settings.scale * 1.05F, Settings.scale * 1.05F, 0.0F, 0, 0, 512, 512, false, false);
        else
            sb.draw(buttonImg, buttonHb.cX - 256F, buttonHb.cY - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 512, false, false);
        if(Settings.isControllerMode)
            sb.draw(CInputActionSet.proceed.getKeyImg(), buttonHb.cX - 32F - 160F * Settings.scale, buttonHb.cY - 32F - 70F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
        buttonHb.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch spritebatch)
    {
    }

    public void dispose()
    {
        super.dispose();
        if(wheelImg != null)
            wheelImg.dispose();
        if(arrowImg != null)
            arrowImg.dispose();
        if(buttonImg != null)
            buttonImg.dispose();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/events/shrines/GremlinWheelGame.getName());
    public static final String ID = "Wheel of Change";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    public static final String INTRO_DIALOG;
    private CUR_SCREEN screen;
    private int result;
    private float resultAngle;
    private float tmpAngle;
    private boolean startSpin;
    private boolean finishSpin;
    private boolean doneSpinning;
    private boolean bounceIn;
    private float bounceTimer;
    private float animTimer;
    private float spinVelocity;
    private int goldAmount;
    private boolean purgeResult;
    private boolean buttonPressed;
    private Hitbox buttonHb;
    private Texture wheelImg;
    private Texture arrowImg;
    private Texture buttonImg;
    private static final float START_Y;
    private static final float TARGET_Y;
    private float imgX;
    private float imgY;
    private float wheelAngle;
    private static final int WHEEL_W = 1024;
    private static final int ARROW_W = 512;
    private static final float ARROW_OFFSET_X;
    private Color color;
    private float hpLossPercent;
    private static final float A_2_HP_LOSS = 0.15F;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Wheel of Change");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_DIALOG = DESCRIPTIONS[0];
        START_Y = Settings.OPTION_Y + 1000F * Settings.scale;
        TARGET_Y = Settings.OPTION_Y;
        ARROW_OFFSET_X = 300F * Settings.scale;
    }
}
