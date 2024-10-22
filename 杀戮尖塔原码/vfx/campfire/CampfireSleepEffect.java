// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CampfireSleepEffect.java

package com.megacrit.cardcrawl.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.NightTerrors;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class CampfireSleepEffect extends AbstractGameEffect
{

    public CampfireSleepEffect()
    {
        hasHealed = false;
        screenColor = AbstractDungeon.fadeColor.cpy();
        if(Settings.FAST_MODE)
            startingDuration = 1.5F;
        else
            startingDuration = 3F;
        duration = startingDuration;
        screenColor.a = 0.0F;
        ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
        AbstractDungeon.overlayMenu.proceedButton.hide();
        if(ModHelper.isModEnabled("Night Terrors"))
        {
            healAmount = (int)((float)AbstractDungeon.player.maxHealth * 1.0F);
            AbstractDungeon.player.decreaseMaxHealth(5);
        } else
        {
            healAmount = (int)((float)AbstractDungeon.player.maxHealth * 0.3F);
        }
        if(AbstractDungeon.player.hasRelic("Regal Pillow"))
            healAmount += 15;
    }

    public void update()
    {
        duration -= Gdx.graphics.getDeltaTime();
        updateBlackScreenColor();
        if(duration < startingDuration - 0.5F && !hasHealed)
        {
            playSleepJingle();
            hasHealed = true;
            if(AbstractDungeon.player.hasRelic("Regal Pillow"))
                AbstractDungeon.player.getRelic("Regal Pillow").flash();
            AbstractDungeon.player.heal(healAmount, false);
            AbstractRelic r;
            for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onRest())
                r = (AbstractRelic)iterator.next();

        }
        if(duration < startingDuration / 2.0F)
        {
            if(AbstractDungeon.player.hasRelic("Dream Catcher"))
            {
                AbstractDungeon.player.getRelic("Dream Catcher").flash();
                ArrayList rewardCards = AbstractDungeon.getRewardCards();
                if(rewardCards != null && !rewardCards.isEmpty())
                    AbstractDungeon.cardRewardScreen.open(rewardCards, null, TEXT[0]);
            }
            isDone = true;
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
            AbstractRoom.waitTimer = 0.0F;
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
        }
    }

    private void playSleepJingle()
    {
        int roll = MathUtils.random(0, 2);
        String s = AbstractDungeon.id;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -1887678253: 
            if(s.equals("Exordium"))
                byte0 = 0;
            break;

        case 313705820: 
            if(s.equals("TheCity"))
                byte0 = 1;
            break;

        case 791401920: 
            if(s.equals("TheBeyond"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        default:
            break;

        case 0: // '\0'
            if(roll == 0)
            {
                CardCrawlGame.sound.play("SLEEP_1-1");
                break;
            }
            if(roll == 1)
                CardCrawlGame.sound.play("SLEEP_1-2");
            else
                CardCrawlGame.sound.play("SLEEP_1-3");
            break;

        case 1: // '\001'
            if(roll == 0)
            {
                CardCrawlGame.sound.play("SLEEP_2-1");
                break;
            }
            if(roll == 1)
                CardCrawlGame.sound.play("SLEEP_2-2");
            else
                CardCrawlGame.sound.play("SLEEP_2-3");
            break;

        case 2: // '\002'
            if(roll == 0)
            {
                CardCrawlGame.sound.play("SLEEP_3-1");
                break;
            }
            if(roll == 1)
                CardCrawlGame.sound.play("SLEEP_3-2");
            else
                CardCrawlGame.sound.play("SLEEP_3-3");
            break;
        }
    }

    private void updateBlackScreenColor()
    {
        if(duration > startingDuration - 0.5F)
            screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - (startingDuration - 0.5F)) * 2.0F);
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

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final float HEAL_AMOUNT = 0.3F;
    private static final float DUR = 3F;
    private static final float FAST_MODE_DUR = 1.5F;
    private boolean hasHealed;
    private int healAmount;
    private Color screenColor;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CampfireSleepEffect");
        TEXT = uiStrings.TEXT;
    }
}
