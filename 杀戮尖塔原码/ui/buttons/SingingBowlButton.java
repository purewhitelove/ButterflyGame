// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SingingBowlButton.java

package com.megacrit.cardcrawl.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.SingingBowl;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.buttons:
//            SkipCardButton

public class SingingBowlButton
{

    public SingingBowlButton()
    {
        current_x = HIDE_X;
        target_x = current_x;
        textColor = Color.WHITE.cpy();
        btnColor = Color.WHITE.cpy();
        isHidden = true;
        rItem = null;
        controllerImgTextWidth = 0.0F;
        hb = new Hitbox(0.0F, 0.0F, HITBOX_W, HITBOX_H);
        hb.move((float)Settings.WIDTH / 2.0F, SkipCardButton.TAKE_Y);
    }

    public void update()
    {
        if(isHidden)
            return;
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            hb.clickStarted = true;
            CardCrawlGame.sound.play("UI_CLICK_1");
        }
        if(hb.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            hb.clicked = false;
            onClick();
            AbstractDungeon.cardRewardScreen.closeFromBowlButton();
            AbstractDungeon.closeCurrentScreen();
            hide();
        }
        if(current_x != target_x)
        {
            current_x = MathUtils.lerp(current_x, target_x, Gdx.graphics.getDeltaTime() * 9F);
            if(Math.abs(current_x - target_x) < Settings.UI_SNAP_THRESHOLD)
            {
                current_x = target_x;
                hb.move(current_x, SkipCardButton.TAKE_Y);
            }
        }
        textColor.a = MathHelper.fadeLerpSnap(textColor.a, 1.0F);
        btnColor.a = textColor.a;
    }

    public void onClick()
    {
        AbstractDungeon.player.getRelic("Singing Bowl").flash();
        CardCrawlGame.sound.playA("SINGING_BOWL", MathUtils.random(-0.2F, 0.1F));
        AbstractDungeon.player.increaseMaxHp(2, true);
        AbstractDungeon.combatRewardScreen.rewards.remove(rItem);
    }

    public void hide()
    {
        if(!isHidden)
            isHidden = true;
    }

    public void show(RewardItem rItem)
    {
        isHidden = false;
        textColor.a = 0.0F;
        btnColor.a = 0.0F;
        current_x = HIDE_X;
        target_x = SHOW_X;
        this.rItem = rItem;
    }

    public void render(SpriteBatch sb)
    {
        if(isHidden)
        {
            return;
        } else
        {
            renderButton(sb);
            FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, TEXT[2], current_x, SkipCardButton.TAKE_Y, textColor);
            return;
        }
    }

    public boolean isHidden()
    {
        return isHidden;
    }

    private void renderButton(SpriteBatch sb)
    {
        sb.setColor(btnColor);
        sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, current_x - 256F, SkipCardButton.TAKE_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
        if(hb.hovered && !hb.clickStarted)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
            sb.draw(ImageMaster.REWARD_SCREEN_TAKE_BUTTON, current_x - 256F, SkipCardButton.TAKE_Y - 128F, 256F, 128F, 512F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 512, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
        if(Settings.isControllerMode)
        {
            if(controllerImgTextWidth == 0.0F)
                controllerImgTextWidth = FontHelper.getSmartWidth(FontHelper.buttonLabelFont, TEXT[2], 9999F, 0.0F);
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), current_x - 32F - controllerImgTextWidth / 2.0F - 38F * Settings.scale, SkipCardButton.TAKE_Y - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        hb.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final int W = 512;
    private static final int H = 256;
    private static final float SHOW_X;
    private static final float HIDE_X;
    private float current_x;
    private float target_x;
    private Color textColor;
    private Color btnColor;
    private boolean isHidden;
    private RewardItem rItem;
    private float controllerImgTextWidth;
    private static final float HITBOX_W;
    private static final float HITBOX_H;
    public Hitbox hb;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CardRewardScreen");
        TEXT = uiStrings.TEXT;
        SHOW_X = (float)Settings.WIDTH / 2.0F + 165F * Settings.scale;
        HIDE_X = (float)Settings.WIDTH / 2.0F;
        HITBOX_W = 260F * Settings.scale;
        HITBOX_H = 80F * Settings.scale;
    }
}
