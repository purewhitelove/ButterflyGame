// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EndTurnButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EndTurnButton
{

    public EndTurnButton()
    {
        label = TEXT[0];
        current_x = HIDE_X;
        current_y = SHOW_Y;
        target_x = current_x;
        isHidden = true;
        enabled = false;
        isDisabled = false;
        glowList = new ArrayList();
        glowTimer = 0.0F;
        isGlowing = false;
        isWarning = false;
        hb = new Hitbox(0.0F, 0.0F, 230F * Settings.scale, 110F * Settings.scale);
        holdProgress = 0.0F;
        holdBarColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
    }

    public void update()
    {
        glow();
        updateHoldProgress();
        if(current_x != target_x)
        {
            current_x = MathUtils.lerp(current_x, target_x, Gdx.graphics.getDeltaTime() * 9F);
            if(Math.abs(current_x - target_x) < Settings.UI_SNAP_THRESHOLD)
                current_x = target_x;
        }
        hb.move(current_x, current_y);
        if(enabled)
        {
            isDisabled = false;
            if(AbstractDungeon.isScreenUp || AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode)
                isDisabled = true;
            if(AbstractDungeon.player.hoveredCard == null)
                hb.update();
            if(!Settings.USE_LONG_PRESS && InputHelper.justClickedLeft && hb.hovered && !isDisabled && !AbstractDungeon.isScreenUp)
            {
                hb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            if(hb.hovered && !isDisabled && !AbstractDungeon.isScreenUp)
            {
                isWarning = showWarning();
                if(hb.justHovered && AbstractDungeon.player.hoveredCard == null)
                {
                    CardCrawlGame.sound.play("UI_HOVER");
                    Iterator iterator = AbstractDungeon.player.hand.group.iterator();
                    do
                    {
                        if(!iterator.hasNext())
                            break;
                        AbstractCard c = (AbstractCard)iterator.next();
                        if(c.isGlowing)
                            c.superFlash(c.glowColor);
                    } while(true);
                }
            }
        }
        if(holdProgress == 0.4F && !isDisabled && !AbstractDungeon.isScreenUp)
        {
            disable(true);
            holdProgress = 0.0F;
            AbstractDungeon.effectsQueue.add(new EndTurnLongPressBarFlashEffect());
        }
        if((!Settings.USE_LONG_PRESS || !Settings.isControllerMode && !InputActionSet.endTurn.isPressed()) && (hb.clicked || (InputActionSet.endTurn.isJustPressed() || CInputActionSet.proceed.isJustPressed()) && !isDisabled && enabled))
        {
            hb.clicked = false;
            if(!isDisabled && !AbstractDungeon.isScreenUp)
                disable(true);
        }
    }

    private void updateHoldProgress()
    {
        if(!Settings.USE_LONG_PRESS || !Settings.isControllerMode && !InputActionSet.endTurn.isPressed() && !InputHelper.isMouseDown)
        {
            holdProgress -= Gdx.graphics.getDeltaTime();
            if(holdProgress < 0.0F)
                holdProgress = 0.0F;
            return;
        }
        if((hb.hovered && InputHelper.isMouseDown || CInputActionSet.proceed.isPressed() || InputActionSet.endTurn.isPressed()) && !isDisabled && enabled)
        {
            holdProgress += Gdx.graphics.getDeltaTime();
            if(holdProgress > 0.4F)
                holdProgress = 0.4F;
        } else
        {
            holdProgress -= Gdx.graphics.getDeltaTime();
            if(holdProgress < 0.0F)
                holdProgress = 0.0F;
        }
    }

    private boolean showWarning()
    {
        for(Iterator iterator = AbstractDungeon.player.hand.group.iterator(); iterator.hasNext();)
        {
            AbstractCard card = (AbstractCard)iterator.next();
            if(card.isGlowing)
                return true;
        }

        return false;
    }

    public void enable()
    {
        enabled = true;
        updateText(END_TURN_MSG);
    }

    public void disable(boolean isEnemyTurn)
    {
        InputHelper.moveCursorToNeutralPosition();
        AbstractDungeon.actionManager.addToBottom(new NewQueueCardAction());
        enabled = false;
        hb.hovered = false;
        isGlowing = false;
        if(isEnemyTurn)
        {
            updateText(ENEMY_TURN_MSG);
            CardCrawlGame.sound.play("END_TURN");
            AbstractDungeon.player.endTurnQueued = true;
            AbstractDungeon.player.releaseCard();
        } else
        {
            updateText(END_TURN_MSG);
        }
    }

    public void disable()
    {
        enabled = false;
        hb.hovered = false;
        isGlowing = false;
    }

    public void updateText(String msg)
    {
        label = msg;
    }

    private void glow()
    {
        if(isGlowing && !isHidden)
            if(glowTimer < 0.0F)
            {
                glowList.add(new EndTurnGlowEffect());
                glowTimer = 1.2F;
            } else
            {
                glowTimer -= Gdx.graphics.getDeltaTime();
            }
        Iterator i = glowList.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
    }

    public void hide()
    {
        if(!isHidden)
        {
            target_x = HIDE_X;
            isHidden = true;
        }
    }

    public void show()
    {
        if(isHidden)
        {
            target_x = SHOW_X;
            isHidden = false;
            if(isGlowing)
                glowTimer = -1F;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(!Settings.hideEndTurn)
        {
            float tmpY = current_y;
            renderHoldEndTurn(sb);
            if(isDisabled || !enabled)
            {
                if(label.equals(ENEMY_TURN_MSG))
                    textColor = Settings.CREAM_COLOR;
                else
                    textColor = Color.LIGHT_GRAY;
            } else
            {
                if(hb.hovered)
                {
                    if(isWarning)
                        textColor = Settings.RED_TEXT_COLOR;
                    else
                        textColor = Color.CYAN;
                } else
                if(isGlowing)
                    textColor = Settings.GOLD_COLOR;
                else
                    textColor = Settings.CREAM_COLOR;
                if(hb.hovered && !AbstractDungeon.isScreenUp && !Settings.isTouchScreen)
                    TipHelper.renderGenericTip(current_x - 90F * Settings.scale, current_y + 300F * Settings.scale, (new StringBuilder()).append(LABEL[0]).append(" (").append(InputActionSet.endTurn.getKeyString()).append(")").toString(), (new StringBuilder()).append(MSG[0]).append(AbstractDungeon.player.gameHandSize).append(MSG[1]).toString());
            }
            if(hb.clickStarted && !AbstractDungeon.isScreenUp)
                tmpY -= 2.0F * Settings.scale;
            else
            if(hb.hovered && !AbstractDungeon.isScreenUp)
                tmpY += 2.0F * Settings.scale;
            if(enabled)
            {
                if(isDisabled || hb.clickStarted && hb.hovered)
                    sb.setColor(DISABLED_COLOR);
                else
                    sb.setColor(Color.WHITE);
            } else
            {
                ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.GRAYSCALE);
            }
            Texture buttonImg;
            if(isGlowing && !hb.clickStarted)
                buttonImg = ImageMaster.END_TURN_BUTTON_GLOW;
            else
                buttonImg = ImageMaster.END_TURN_BUTTON;
            if(hb.hovered && !isDisabled && !AbstractDungeon.isScreenUp)
                sb.draw(ImageMaster.END_TURN_HOVER, current_x - 128F, tmpY - 128F, 128F, 128F, 256F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
            sb.draw(buttonImg, current_x - 128F, tmpY - 128F, 128F, 128F, 256F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
            if(!enabled)
                ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.DEFAULT);
            renderGlowEffect(sb, current_x, current_y);
            if((hb.hovered || holdProgress > 0.0F) && !isDisabled && !AbstractDungeon.isScreenUp)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(Settings.HALF_TRANSPARENT_WHITE_COLOR);
                sb.draw(buttonImg, current_x - 128F, tmpY - 128F, 128F, 128F, 256F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(770, 771);
            }
            if(Settings.isControllerMode && enabled)
            {
                sb.setColor(Color.WHITE);
                sb.draw(CInputActionSet.proceed.getKeyImg(), current_x - 32F - 42F * Settings.scale - FontHelper.getSmartWidth(FontHelper.panelEndTurnFont, label, 99999F, 0.0F) / 2.0F, tmpY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            }
            FontHelper.renderFontCentered(sb, FontHelper.panelEndTurnFont, label, current_x - 0.0F * Settings.scale, tmpY - 3F * Settings.scale, textColor);
            if(!isHidden)
                hb.render(sb);
        }
    }

    private void renderHoldEndTurn(SpriteBatch sb)
    {
        if(!Settings.USE_LONG_PRESS)
        {
            return;
        } else
        {
            holdBarColor.r = 0.0F;
            holdBarColor.g = 0.0F;
            holdBarColor.b = 0.0F;
            holdBarColor.a = holdProgress * 1.5F;
            sb.setColor(holdBarColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, current_x - 107F * Settings.scale, (current_y + 53F * Settings.scale) - 7F * Settings.scale, 525F * Settings.scale * holdProgress + 14F * Settings.scale, 20F * Settings.scale);
            holdBarColor.r = holdProgress * 2.5F;
            holdBarColor.g = 0.6F + holdProgress;
            holdBarColor.b = 0.6F;
            holdBarColor.a = 1.0F;
            sb.setColor(holdBarColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, current_x - 100F * Settings.scale, current_y + 53F * Settings.scale, 525F * Settings.scale * holdProgress, 6F * Settings.scale);
            return;
        }
    }

    private void renderGlowEffect(SpriteBatch sb, float x, float y)
    {
        EndTurnGlowEffect e;
        for(Iterator iterator = glowList.iterator(); iterator.hasNext(); e.render(sb, x, y))
            e = (EndTurnGlowEffect)iterator.next();

    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private String label;
    public static final String END_TURN_MSG;
    public static final String ENEMY_TURN_MSG;
    private static final Color DISABLED_COLOR = new Color(0.7F, 0.7F, 0.7F, 1.0F);
    private static final float SHOW_X;
    private static final float SHOW_Y;
    private static final float HIDE_X;
    private float current_x;
    private float current_y;
    private float target_x;
    private boolean isHidden;
    public boolean enabled;
    private boolean isDisabled;
    private Color textColor;
    private ArrayList glowList;
    private static final float GLOW_INTERVAL = 1.2F;
    private float glowTimer;
    public boolean isGlowing;
    public boolean isWarning;
    private Hitbox hb;
    private float holdProgress;
    private static final float HOLD_DUR = 0.4F;
    private Color holdBarColor;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("End Turn Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        uiStrings = CardCrawlGame.languagePack.getUIString("End Turn Button");
        TEXT = uiStrings.TEXT;
        END_TURN_MSG = TEXT[0];
        ENEMY_TURN_MSG = TEXT[1];
        SHOW_X = 1640F * Settings.xScale;
        SHOW_Y = 210F * Settings.yScale;
        HIDE_X = SHOW_X + 500F * Settings.xScale;
    }
}
