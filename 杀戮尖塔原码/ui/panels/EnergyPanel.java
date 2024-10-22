// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnergyPanel.java

package com.megacrit.cardcrawl.ui.panels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.RefreshEnergyEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.ui.panels:
//            AbstractPanel

public class EnergyPanel extends AbstractPanel
{

    public EnergyPanel()
    {
        super(198F * Settings.xScale, 190F * Settings.yScale, -480F * Settings.scale, 200F * Settings.yScale, 12F * Settings.scale, -12F * Settings.scale, null, true);
        tipHitbox = new Hitbox(0.0F, 0.0F, 120F * Settings.scale, 120F * Settings.scale);
        energyVfxAngle = 0.0F;
        energyVfxScale = Settings.scale;
        energyVfxColor = Color.WHITE.cpy();
        gainEnergyImg = AbstractDungeon.player.getEnergyImage();
    }

    public static void setEnergy(int energy)
    {
        totalCount = energy;
        AbstractDungeon.effectsQueue.add(new RefreshEnergyEffect());
        energyVfxTimer = 2.0F;
        fontScale = 2.0F;
    }

    public static void addEnergy(int e)
    {
        totalCount += e;
        if(totalCount >= 9)
            UnlockTracker.unlockAchievement("ADRENALINE");
        if(totalCount > 999)
            totalCount = 999;
        AbstractDungeon.effectsQueue.add(new RefreshEnergyEffect());
        fontScale = 2.0F;
        energyVfxTimer = 2.0F;
    }

    public static void useEnergy(int e)
    {
        totalCount -= e;
        if(totalCount < 0)
            totalCount = 0;
        if(e != 0)
            fontScale = 2.0F;
    }

    public void update()
    {
        AbstractDungeon.player.updateOrb(totalCount);
        updateVfx();
        if(fontScale != 1.0F)
            fontScale = MathHelper.scaleLerpSnap(fontScale, 1.0F);
        tipHitbox.update();
        if(tipHitbox.hovered && !AbstractDungeon.isScreenUp)
            AbstractDungeon.overlayMenu.hoveredTip = true;
        if(Settings.isDebug)
            if(InputHelper.scrolledDown)
                addEnergy(1);
            else
            if(InputHelper.scrolledUp && totalCount > 0)
                useEnergy(1);
    }

    private void updateVfx()
    {
        if(energyVfxTimer != 0.0F)
        {
            energyVfxColor.a = Interpolation.exp10In.apply(0.5F, 0.0F, 1.0F - energyVfxTimer / 2.0F);
            energyVfxAngle += Gdx.graphics.getDeltaTime() * -30F;
            energyVfxScale = Settings.scale * Interpolation.exp10In.apply(1.0F, 0.1F, 1.0F - energyVfxTimer / 2.0F);
            energyVfxTimer -= Gdx.graphics.getDeltaTime();
            if(energyVfxTimer < 0.0F)
            {
                energyVfxTimer = 0.0F;
                energyVfxColor.a = 0.0F;
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        tipHitbox.move(current_x, current_y);
        renderOrb(sb);
        renderVfx(sb);
        String energyMsg = (new StringBuilder()).append(totalCount).append("/").append(AbstractDungeon.player.energy.energy).toString();
        AbstractDungeon.player.getEnergyNumFont().getData().setScale(fontScale);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), energyMsg, current_x, current_y, ENERGY_TEXT_COLOR);
        tipHitbox.render(sb);
        if(tipHitbox.hovered && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp)
            TipHelper.renderGenericTip(50F * Settings.scale, 380F * Settings.scale, LABEL[0], MSG[0]);
    }

    private void renderOrb(SpriteBatch sb)
    {
        if(totalCount == 0)
            AbstractDungeon.player.renderOrb(sb, false, current_x, current_y);
        else
            AbstractDungeon.player.renderOrb(sb, true, current_x, current_y);
    }

    private void renderVfx(SpriteBatch sb)
    {
        if(energyVfxTimer != 0.0F)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(energyVfxColor);
            sb.draw(gainEnergyImg, current_x - 128F, current_y - 128F, 128F, 128F, 256F, 256F, energyVfxScale, energyVfxScale, -energyVfxAngle + 50F, 0, 0, 256, 256, true, false);
            sb.draw(gainEnergyImg, current_x - 128F, current_y - 128F, 128F, 128F, 256F, 256F, energyVfxScale, energyVfxScale, energyVfxAngle, 0, 0, 256, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public static int getCurrentEnergy()
    {
        if(AbstractDungeon.player == null)
            return 0;
        else
            return totalCount;
    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    private static final int RAW_W = 256;
    private static final Color ENERGY_TEXT_COLOR = new Color(1.0F, 1.0F, 0.86F, 1.0F);
    public static float fontScale = 1.0F;
    public static final float FONT_POP_SCALE = 2F;
    public static int totalCount = 0;
    private Hitbox tipHitbox;
    private Texture gainEnergyImg;
    private float energyVfxAngle;
    private float energyVfxScale;
    private Color energyVfxColor;
    public static float energyVfxTimer = 0.0F;
    public static final float ENERGY_VFX_TIME = 2F;
    private static final float VFX_ROTATE_SPEED = -30F;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Energy Panel Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
    }
}
