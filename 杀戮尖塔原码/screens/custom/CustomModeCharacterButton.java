// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomModeCharacterButton.java

package com.megacrit.cardcrawl.screens.custom;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;

// Referenced classes of package com.megacrit.cardcrawl.screens.custom:
//            CustomModeScreen

public class CustomModeCharacterButton
{

    public CustomModeCharacterButton(AbstractPlayer c, boolean locked)
    {
        selected = false;
        this.locked = false;
        highlightColor = new Color(1.0F, 0.8F, 0.2F, 0.0F);
        drawScale = 1.0F;
        buttonImg = c.getCustomModeCharacterButtonImage();
        charStrings = c.getCharacterString();
        hb = Settings.isMobile ? new Hitbox(120F * Settings.scale, 120F * Settings.scale) : new Hitbox(80F * Settings.scale, 80F * Settings.scale);
        drawScale = Settings.isMobile ? Settings.scale * 1.2F : Settings.scale;
        this.locked = locked;
        this.c = c;
    }

    public void move(float x, float y)
    {
        this.x = x;
        this.y = y;
        hb.move(x, y);
    }

    public void update(float x, float y)
    {
        this.x = x;
        this.y = y;
        hb.move(x, y);
        updateHitbox();
    }

    private void updateHitbox()
    {
        hb.update();
        if(hb.justHovered)
            CardCrawlGame.sound.playA("UI_HOVER", -0.3F);
        if(InputHelper.justClickedLeft && !locked && hb.hovered)
        {
            CardCrawlGame.sound.playA("UI_CLICK_1", -0.4F);
            hb.clickStarted = true;
        }
        if(hb.clicked)
        {
            hb.clicked = false;
            if(!selected)
            {
                CardCrawlGame.mainMenuScreen.customModeScreen.deselectOtherOptions(this);
                selected = true;
                CardCrawlGame.chosenCharacter = c.chosenClass;
                CardCrawlGame.mainMenuScreen.customModeScreen.confirmButton.isDisabled = false;
                CardCrawlGame.mainMenuScreen.customModeScreen.confirmButton.show();
                CardCrawlGame.sound.playA(c.getCustomModeCharacterButtonSoundKey(), MathUtils.random(-0.2F, 0.2F));
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        renderOptionButton(sb);
        if(hb.hovered)
            TipHelper.renderGenericTip((float)InputHelper.mX + 180F * Settings.scale, hb.cY + 40F * Settings.scale, charStrings.NAMES[0], charStrings.TEXT[0]);
        hb.render(sb);
    }

    private void renderOptionButton(SpriteBatch sb)
    {
        if(selected)
        {
            highlightColor.a = 0.25F + (MathUtils.cosDeg((System.currentTimeMillis() / 4L) % 360L) + 1.25F) / 3.5F;
            sb.setColor(highlightColor);
            sb.draw(ImageMaster.FILTER_GLOW_BG, hb.cX - 64F, hb.cY - 64F, 64F, 64F, 128F, 128F, drawScale, drawScale, 0.0F, 0, 0, 128, 128, false, false);
        }
        if(locked)
            ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.GRAYSCALE);
        else
        if(selected || hb.hovered)
            sb.setColor(Color.WHITE);
        else
            sb.setColor(Color.LIGHT_GRAY);
        sb.draw(buttonImg, hb.cX - 64F, y - 64F, 64F, 64F, 128F, 128F, drawScale, drawScale, 0.0F, 0, 0, 128, 128, false, false);
        if(locked)
            ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.DEFAULT);
    }

    private CharacterStrings charStrings;
    private Texture buttonImg;
    public AbstractPlayer c;
    public boolean selected;
    public boolean locked;
    public Hitbox hb;
    private static final int W = 128;
    public float x;
    public float y;
    private Color highlightColor;
    private float drawScale;
}
