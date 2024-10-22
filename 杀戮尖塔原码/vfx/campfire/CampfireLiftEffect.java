// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CampfireLiftEffect.java

package com.megacrit.cardcrawl.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Girya;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import java.util.ArrayList;

public class CampfireLiftEffect extends AbstractGameEffect
{

    public CampfireLiftEffect()
    {
        hasTrained = false;
        screenColor = AbstractDungeon.fadeColor.cpy();
        duration = 2.0F;
        screenColor.a = 0.0F;
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        updateBlackScreenColor();
        if(duration < 1.0F && !hasTrained)
        {
            hasTrained = true;
            if(AbstractDungeon.player.hasRelic("Girya"))
            {
                AbstractDungeon.player.getRelic("Girya").flash();
                AbstractDungeon.player.getRelic("Girya").counter++;
                CardCrawlGame.sound.play("ATTACK_HEAVY");
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, true);
                CardCrawlGame.metricData.addCampfireChoiceData("LIFT", Integer.toString(AbstractDungeon.player.getRelic("Girya").counter));
            }
            AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(new Color(0.8F, 0.6F, 0.1F, 0.0F)));
        }
        if(duration < 0.0F)
        {
            isDone = true;
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
        }
    }

    private void updateBlackScreenColor()
    {
        if(duration > 1.5F)
            screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - 1.5F) * 2.0F);
        else
        if(duration < 1.0F)
            screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, duration);
        else
            screenColor.a = 1.0F;
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose()
    {
    }

    private static final float DUR = 2F;
    private boolean hasTrained;
    private Color screenColor;
}
