// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CampfireSmithEffect.java

package com.megacrit.cardcrawl.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class CampfireSmithEffect extends AbstractGameEffect
{

    public CampfireSmithEffect()
    {
        openedScreen = false;
        screenColor = AbstractDungeon.fadeColor.cpy();
        duration = 1.5F;
        screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update()
    {
        if(!AbstractDungeon.isScreenUp)
        {
            duration -= Gdx.graphics.getDeltaTime();
            updateBlackScreenColor();
        }
        if(!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && AbstractDungeon.gridSelectScreen.forUpgrade)
        {
            AbstractCard c;
            for(Iterator iterator = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); iterator.hasNext(); AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy())))
            {
                c = (AbstractCard)iterator.next();
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                CardCrawlGame.metricData.campfire_upgraded++;
                CardCrawlGame.metricData.addCampfireChoiceData("SMITH", c.getMetricID());
                c.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(c);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            ((RestRoom)AbstractDungeon.getCurrRoom()).fadeIn();
        }
        if(duration < 1.0F && !openedScreen)
        {
            openedScreen = true;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, TEXT[0], true, false, true, false);
            AbstractRelic r;
            for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onSmith())
                r = (AbstractRelic)iterator1.next();

        }
        if(duration < 0.0F)
        {
            isDone = true;
            if(CampfireUI.hidden)
            {
                AbstractRoom.waitTimer = 0.0F;
                AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
                ((RestRoom)AbstractDungeon.getCurrRoom()).cutFireSound();
            }
        }
    }

    private void updateBlackScreenColor()
    {
        if(duration > 1.0F)
            screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (duration - 1.0F) * 2.0F);
        else
            screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, duration / 1.5F);
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.GRID)
            AbstractDungeon.gridSelectScreen.render(sb);
    }

    public void dispose()
    {
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final float DUR = 1.5F;
    private boolean openedScreen;
    private Color screenColor;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CampfireSmithEffect");
        TEXT = uiStrings.TEXT;
    }
}
