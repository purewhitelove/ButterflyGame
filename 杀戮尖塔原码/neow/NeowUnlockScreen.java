// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NeowUnlockScreen.java

package com.megacrit.cardcrawl.neow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.buttons.UnlockConfirmButton;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;
import java.util.Iterator;

public class NeowUnlockScreen
{

    public NeowUnlockScreen()
    {
        cones = new ArrayList();
        shinyTimer = 0.0F;
        button = new UnlockConfirmButton();
    }

    public void open(ArrayList unlock, boolean isVictory)
    {
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
        unlockBundle = unlock;
        button.show();
        id = CardCrawlGame.sound.play("UNLOCK_SCREEN");
        cones.clear();
        for(int i = 0; i < 30; i++)
            cones.add(new ConeEffect());

        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType = new int[com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType[com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CARD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType[com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.RELIC.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType[com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CHARACTER.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$unlock$AbstractUnlock$UnlockType[com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.MISC.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType[((AbstractUnlock)unlockBundle.get(0)).type.ordinal()])
        {
        case 4: // '\004'
        default:
            break;

        case 1: // '\001'
            for(int i = 0; i < unlockBundle.size(); i++)
            {
                UnlockTracker.unlockCard(((AbstractUnlock)unlockBundle.get(i)).card.cardID);
                AbstractDungeon.dynamicBanner.appearInstantly(TEXT[0]);
                ((AbstractUnlock)unlockBundle.get(i)).card.targetDrawScale = 1.0F;
                ((AbstractUnlock)unlockBundle.get(i)).card.drawScale = 0.01F;
                ((AbstractUnlock)unlockBundle.get(i)).card.current_x = (float)Settings.WIDTH * (0.25F * (float)(i + 1));
                ((AbstractUnlock)unlockBundle.get(i)).card.current_y = (float)Settings.HEIGHT / 2.0F;
                ((AbstractUnlock)unlockBundle.get(i)).card.target_x = (float)Settings.WIDTH * (0.25F * (float)(i + 1));
                ((AbstractUnlock)unlockBundle.get(i)).card.target_y = (float)Settings.HEIGHT / 2.0F - 30F * Settings.scale;
            }

            break;

        case 2: // '\002'
            for(int i = 0; i < unlockBundle.size(); i++)
            {
                UnlockTracker.hardUnlockOverride(((AbstractUnlock)unlockBundle.get(i)).relic.relicId);
                UnlockTracker.markRelicAsSeen(((AbstractUnlock)unlockBundle.get(i)).relic.relicId);
                ((AbstractUnlock)unlockBundle.get(i)).relic.loadLargeImg();
                AbstractDungeon.dynamicBanner.appearInstantly(TEXT[1]);
                ((AbstractUnlock)unlockBundle.get(i)).relic.currentX = (float)Settings.WIDTH * (0.25F * (float)(i + 1));
                ((AbstractUnlock)unlockBundle.get(i)).relic.currentY = (float)Settings.HEIGHT / 2.0F;
                ((AbstractUnlock)unlockBundle.get(i)).relic.hb.move(((AbstractUnlock)unlockBundle.get(i)).relic.currentX, ((AbstractUnlock)unlockBundle.get(i)).relic.currentY);
            }

            break;

        case 3: // '\003'
            ((AbstractUnlock)unlockBundle.get(0)).onUnlockScreenOpen();
            AbstractDungeon.dynamicBanner.appearInstantly(TEXT[2]);
            break;
        }
    }

    public void reOpen()
    {
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK;
        button.show();
        id = CardCrawlGame.sound.play("UNLOCK_SCREEN");
        cones.clear();
        for(int i = 0; i < 30; i++)
            cones.add(new ConeEffect());

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType[((AbstractUnlock)unlockBundle.get(0)).type.ordinal()])
        {
        case 4: // '\004'
        default:
            break;

        case 1: // '\001'
            for(int i = 0; i < unlockBundle.size(); i++)
            {
                UnlockTracker.unlockCard(((AbstractUnlock)unlockBundle.get(i)).card.cardID);
                AbstractDungeon.dynamicBanner.appearInstantly(TEXT[0]);
                ((AbstractUnlock)unlockBundle.get(i)).card.targetDrawScale = 1.0F;
                ((AbstractUnlock)unlockBundle.get(i)).card.drawScale = 0.01F;
                ((AbstractUnlock)unlockBundle.get(i)).card.current_x = (float)Settings.WIDTH * (0.25F * (float)(i + 1));
                ((AbstractUnlock)unlockBundle.get(i)).card.current_y = (float)Settings.HEIGHT / 2.0F;
                ((AbstractUnlock)unlockBundle.get(i)).card.target_x = (float)Settings.WIDTH * (0.25F * (float)(i + 1));
                ((AbstractUnlock)unlockBundle.get(i)).card.target_y = (float)Settings.HEIGHT / 2.0F - 30F * Settings.scale;
            }

            break;

        case 2: // '\002'
            for(int i = 0; i < unlockBundle.size(); i++)
            {
                AbstractDungeon.dynamicBanner.appearInstantly(TEXT[1]);
                ((AbstractUnlock)unlockBundle.get(i)).relic.currentX = (float)Settings.WIDTH * (0.25F * (float)(i + 1));
                ((AbstractUnlock)unlockBundle.get(i)).relic.currentY = (float)Settings.HEIGHT / 2.0F;
                ((AbstractUnlock)unlockBundle.get(i)).relic.hb.move(((AbstractUnlock)unlockBundle.get(i)).relic.currentX, ((AbstractUnlock)unlockBundle.get(i)).relic.currentY);
            }

            break;

        case 3: // '\003'
            ((AbstractUnlock)unlockBundle.get(0)).onUnlockScreenOpen();
            AbstractDungeon.dynamicBanner.appearInstantly(TEXT[2]);
            break;
        }
    }

    public void update()
    {
        shinyTimer -= Gdx.graphics.getDeltaTime();
        if(shinyTimer < 0.0F)
        {
            shinyTimer = 0.2F;
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect());
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect());
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect2());
        }
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType[((AbstractUnlock)unlockBundle.get(0)).type.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            updateConeEffect();
            for(int i = 0; i < unlockBundle.size(); i++)
            {
                ((AbstractUnlock)unlockBundle.get(i)).card.update();
                ((AbstractUnlock)unlockBundle.get(i)).card.updateHoverLogic();
                ((AbstractUnlock)unlockBundle.get(i)).card.targetDrawScale = 1.0F;
            }

            break;

        case 2: // '\002'
            updateConeEffect();
            for(int i = 0; i < unlockBundle.size(); i++)
                ((AbstractUnlock)unlockBundle.get(i)).relic.update();

            break;

        case 3: // '\003'
            updateConeEffect();
            ((AbstractUnlock)unlockBundle.get(0)).player.update();
            break;
        }
        button.update();
    }

    private void updateConeEffect()
    {
        Iterator e = cones.iterator();
        do
        {
            if(!e.hasNext())
                break;
            ConeEffect d = (ConeEffect)e.next();
            d.update();
            if(d.isDone)
                e.remove();
        } while(true);
        if(cones.size() < 30)
            cones.add(new ConeEffect());
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(0.05F, 0.15F, 0.18F, 1.0F));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setBlendFunction(770, 1);
        ConeEffect e;
        for(Iterator iterator = cones.iterator(); iterator.hasNext(); e.render(sb))
            e = (ConeEffect)iterator.next();

        sb.setBlendFunction(770, 771);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType[((AbstractUnlock)unlockBundle.get(0)).type.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            for(int i = 0; i < unlockBundle.size(); i++)
            {
                ((AbstractUnlock)unlockBundle.get(i)).card.renderHoverShadow(sb);
                ((AbstractUnlock)unlockBundle.get(i)).card.render(sb);
                ((AbstractUnlock)unlockBundle.get(i)).card.renderCardTip(sb);
            }

            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
            sb.draw(ImageMaster.UNLOCK_TEXT_BG, (float)Settings.WIDTH / 2.0F - 500F, (float)Settings.HEIGHT / 2.0F - 330F * Settings.scale - 130F, 500F, 130F, 1000F, 260F, Settings.scale * 1.2F, Settings.scale * 0.8F, 0.0F, 0, 0, 1000, 260, false, false);
            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[3], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 330F * Settings.scale, Settings.CREAM_COLOR);
            break;

        case 3: // '\003'
            ((AbstractUnlock)unlockBundle.get(0)).render(sb);
            ((AbstractUnlock)unlockBundle.get(0)).player.renderPlayerImage(sb);
            break;

        case 2: // '\002'
            for(int i = 0; i < unlockBundle.size(); i++)
            {
                if(RelicLibrary.redList.contains(((AbstractUnlock)unlockBundle.get(i)).relic))
                    ((AbstractUnlock)unlockBundle.get(i)).relic.render(sb, false, Settings.RED_RELIC_COLOR);
                else
                if(RelicLibrary.greenList.contains(((AbstractUnlock)unlockBundle.get(i)).relic))
                    ((AbstractUnlock)unlockBundle.get(i)).relic.render(sb, false, Settings.GREEN_RELIC_COLOR);
                else
                if(RelicLibrary.blueList.contains(((AbstractUnlock)unlockBundle.get(i)).relic))
                    ((AbstractUnlock)unlockBundle.get(i)).relic.render(sb, false, Settings.BLUE_RELIC_COLOR);
                else
                if(RelicLibrary.whiteList.contains(((AbstractUnlock)unlockBundle.get(i)).relic))
                    ((AbstractUnlock)unlockBundle.get(i)).relic.render(sb, false, Settings.PURPLE_RELIC_COLOR);
                else
                    ((AbstractUnlock)unlockBundle.get(i)).relic.render(sb, false, Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
                sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
                sb.draw(ImageMaster.UNLOCK_TEXT_BG, (float)Settings.WIDTH / 2.0F - 500F, (float)Settings.HEIGHT / 2.0F - 330F * Settings.scale - 130F, 500F, 130F, 1000F, 260F, Settings.scale * 1.2F, Settings.scale * 0.8F, 0.0F, 0, 0, 1000, 260, false, false);
                FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, ((AbstractUnlock)unlockBundle.get(i)).relic.name, (float)Settings.WIDTH * (0.25F * (float)(i + 1)), (float)Settings.HEIGHT / 2.0F - 150F * Settings.scale, Settings.GOLD_COLOR, 1.2F);
            }

            FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, TEXT[3], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 330F * Settings.scale, Settings.CREAM_COLOR);
            break;
        }
        button.render(sb);
    }

    public ArrayList unlockBundle;
    private ArrayList cones;
    private static final int CONE_AMT = 30;
    private float shinyTimer;
    private static final float SHINY_INTERVAL = 0.2F;
    public UnlockConfirmButton button;
    public long id;
    private static final UIStrings uiStrings;
    public static final String TEXT[];

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("UnlockScreen");
        TEXT = uiStrings.TEXT;
    }
}
