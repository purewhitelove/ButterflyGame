// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomMod.java

package com.megacrit.cardcrawl.screens.custom;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.RunModStrings;
import java.util.HashSet;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.screens.custom:
//            CustomModeScreen

public class CustomMod
{

    public CustomMod(String setID, String color, boolean isDailyMod)
    {
        selected = false;
        if(offset_x == 0.0F)
        {
            offset_x = CustomModeScreen.screenX + 120F * Settings.scale;
            line_spacing = Settings.BIG_TEXT_MODE ? 40F * Settings.scale : 32F * Settings.scale;
            text_max_width = Settings.isMobile ? 1170F * Settings.scale : 1050F * Settings.scale;
        }
        this.color = color;
        ID = setID;
        RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(setID);
        name = modStrings.NAME;
        description = modStrings.DESCRIPTION;
        hb = new Hitbox(text_max_width, 70F * Settings.scale);
        this.isDailyMod = isDailyMod;
        label = (new StringBuilder()).append(FontHelper.colorString((new StringBuilder()).append("[").append(name).append("]").toString(), color)).append(" ").append(description).toString();
        height = -FontHelper.getSmartHeight(FontHelper.charDescFont, label, text_max_width, line_spacing) + 70F * Settings.scale;
    }

    public void update(float y)
    {
        hb.update();
        hb.move(offset_x + (text_max_width - 80F * Settings.scale) / 2.0F, y + OFFSET_Y);
        if(hb.justHovered)
            playHoverSound();
        if(hb.hovered && InputHelper.justClickedLeft)
            hb.clickStarted = true;
        if(hb.clicked)
        {
            hb.clicked = false;
            selected = !selected;
            playClickSound();
            if(selected)
                disableMutuallyExclusiveMods();
        }
    }

    public void render(SpriteBatch sb)
    {
        float scale = Settings.isMobile ? Settings.scale * 1.2F : Settings.scale;
        if(hb.hovered)
        {
            sb.draw(ImageMaster.CHECKBOX, offset_x - 32F, hb.cY - 32F, 32F, 32F, 64F, 64F, scale * 1.2F, scale * 1.2F, 0.0F, 0, 0, 64, 64, false, false);
            sb.setColor(Color.GOLD);
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.CHECKBOX, offset_x - 32F, hb.cY - 32F, 32F, 32F, 64F, 64F, scale * 1.2F, scale * 1.2F, 0.0F, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
            sb.setColor(Color.WHITE);
        } else
        {
            sb.draw(ImageMaster.CHECKBOX, offset_x - 32F, hb.cY - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if(selected)
            sb.draw(ImageMaster.TICK, offset_x - 32F, hb.cY - 32F, 32F, 32F, 64F, 64F, scale, scale, 0.0F, 0, 0, 64, 64, false, false);
        if(hb.hovered)
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, label, offset_x + 46F * Settings.scale, hb.cY + 12F * Settings.scale, text_max_width, line_spacing, Settings.LIGHT_YELLOW_COLOR);
        else
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, label, offset_x + 40F * Settings.scale, hb.cY + 12F * Settings.scale, text_max_width, line_spacing, Settings.CREAM_COLOR);
        hb.render(sb);
    }

    private void playClickSound()
    {
        CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
    }

    private void playHoverSound()
    {
        CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
    }

    public void setMutualExclusionPair(CustomMod otherMod)
    {
        setMutualExclusion(otherMod);
        otherMod.setMutualExclusion(this);
    }

    private void setMutualExclusion(CustomMod otherMod)
    {
        if(mutuallyExclusive == null)
            mutuallyExclusive = new HashSet();
        mutuallyExclusive.add(otherMod);
    }

    private void disableMutuallyExclusiveMods()
    {
        if(mutuallyExclusive != null)
        {
            for(Iterator iterator = mutuallyExclusive.iterator(); iterator.hasNext();)
            {
                CustomMod mods = (CustomMod)iterator.next();
                mods.selected = false;
            }

        }
    }

    public String ID;
    public String name;
    public String description;
    public String color;
    private String label;
    public boolean isDailyMod;
    public boolean selected;
    public Hitbox hb;
    private static float offset_x = 0.0F;
    private static float text_max_width;
    private static float line_spacing;
    private static final float OFFSET_Y;
    public float height;
    private HashSet mutuallyExclusive;

    static 
    {
        OFFSET_Y = 130F * Settings.scale;
    }
}
