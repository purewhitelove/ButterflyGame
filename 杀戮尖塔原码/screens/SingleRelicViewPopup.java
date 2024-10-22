// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SingleRelicViewPopup.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;

public class SingleRelicViewPopup
{

    public SingleRelicViewPopup()
    {
        isOpen = false;
        fadeTimer = 0.0F;
        fadeColor = Color.BLACK.cpy();
        rarityLabel = "";
        RELIC_OFFSET_Y = 76F * Settings.scale;
    }

    public void open(AbstractRelic relic, ArrayList group)
    {
        CardCrawlGame.isPopupOpen = true;
        relic.playLandingSFX();
        prevRelic = null;
        nextRelic = null;
        prevHb = null;
        nextHb = null;
        int i = 0;
        do
        {
            if(i >= group.size())
                break;
            if(group.get(i) == relic)
            {
                if(i != 0)
                    prevRelic = (AbstractRelic)group.get(i - 1);
                if(i != group.size() - 1)
                    nextRelic = (AbstractRelic)group.get(i + 1);
                break;
            }
            i++;
        } while(true);
        prevHb = new Hitbox(160F * Settings.scale, 160F * Settings.scale);
        nextHb = new Hitbox(160F * Settings.scale, 160F * Settings.scale);
        prevHb.move((float)Settings.WIDTH / 2.0F - 400F * Settings.scale, (float)Settings.HEIGHT / 2.0F);
        nextHb.move((float)Settings.WIDTH / 2.0F + 400F * Settings.scale, (float)Settings.HEIGHT / 2.0F);
        popupHb = new Hitbox(550F * Settings.scale, 680F * Settings.scale);
        popupHb.move((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        isOpen = true;
        this.group = group;
        this.relic = relic;
        fadeTimer = 0.25F;
        fadeColor.a = 0.0F;
        generateRarityLabel();
        generateFrameImg();
        this.relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
        initializeLargeImg();
    }

    public void open(AbstractRelic relic)
    {
        CardCrawlGame.isPopupOpen = true;
        relic.playLandingSFX();
        prevRelic = null;
        nextRelic = null;
        prevHb = null;
        nextHb = null;
        popupHb = new Hitbox(550F * Settings.scale, 680F * Settings.scale);
        popupHb.move((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        isOpen = true;
        group = null;
        this.relic = relic;
        fadeTimer = 0.25F;
        fadeColor.a = 0.0F;
        generateRarityLabel();
        generateFrameImg();
        this.relic.isSeen = UnlockTracker.isRelicSeen(relic.relicId);
        initializeLargeImg();
    }

    private void initializeLargeImg()
    {
        largeImg = ImageMaster.loadImage((new StringBuilder()).append("images/largeRelics/").append(relic.imgUrl).toString());
    }

    public void close()
    {
        CardCrawlGame.isPopupOpen = false;
        isOpen = false;
        InputHelper.justReleasedClickLeft = false;
        if(largeImg != null)
        {
            largeImg.dispose();
            largeImg = null;
        }
        if(relicFrameImg != null)
        {
            relicFrameImg.dispose();
            relicFrameImg = null;
        }
    }

    public void update()
    {
        popupHb.update();
        updateArrows();
        updateInput();
        updateFade();
        updateSoundEffect();
    }

    private void updateArrows()
    {
        if(prevRelic != null)
        {
            prevHb.update();
            if(prevHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(prevHb.clicked || prevRelic != null && CInputActionSet.pageLeftViewDeck.isJustPressed())
                openPrev();
        }
        if(nextRelic != null)
        {
            nextHb.update();
            if(nextHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(nextHb.clicked || nextRelic != null && CInputActionSet.pageRightViewExhaust.isJustPressed())
                openNext();
        }
    }

    private void updateInput()
    {
        if(InputHelper.justClickedLeft)
        {
            if(prevRelic != null && prevHb.hovered)
            {
                prevHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                return;
            }
            if(nextRelic != null && nextHb.hovered)
            {
                nextHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                return;
            }
        }
        if(InputHelper.justReleasedClickLeft)
        {
            if(!popupHb.hovered)
            {
                close();
                FontHelper.ClearSRVFontTextures();
            }
        } else
        if(InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed())
        {
            CInputActionSet.cancel.unpress();
            InputHelper.pressedEscape = false;
            if(AbstractDungeon.screen == null || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NONE)
                AbstractDungeon.isScreenUp = false;
            close();
            FontHelper.ClearSRVFontTextures();
        }
        if(prevRelic != null && InputActionSet.left.isJustPressed())
            openPrev();
        else
        if(nextRelic != null && InputActionSet.right.isJustPressed())
            openNext();
    }

    private void openPrev()
    {
        close();
        open(prevRelic, group);
        fadeTimer = 0.0F;
        fadeColor.a = 0.9F;
    }

    private void openNext()
    {
        close();
        open(nextRelic, group);
        fadeTimer = 0.0F;
        fadeColor.a = 0.9F;
    }

    private void updateFade()
    {
        fadeTimer -= Gdx.graphics.getDeltaTime();
        if(fadeTimer < 0.0F)
            fadeTimer = 0.0F;
        fadeColor.a = Interpolation.pow2In.apply(0.9F, 0.0F, fadeTimer * 4F);
    }

    private void updateSoundEffect()
    {
        String key = null;
        if(relic instanceof Tingsha)
            key = "TINGSHA";
        else
        if(relic instanceof Damaru)
            key = "DAMARU";
        else
        if(relic instanceof SingingBowl)
            key = "SINGING_BOWL";
        else
        if(relic instanceof CallingBell)
            key = "BELL";
        else
        if(relic instanceof ChemicalX)
            key = "POTION_3";
        else
        if(relic instanceof Cauldron)
            key = "POTION_1";
        else
        if(relic instanceof MembershipCard)
            key = "SHOP_PURCHASE";
        else
        if(relic instanceof CharonsAshes)
            key = "CARD_BURN";
        if(InputActionSet.selectCard_1.isJustPressed())
            CardCrawlGame.sound.playA(key, -0.2F);
        else
        if(InputActionSet.selectCard_2.isJustPressed())
            CardCrawlGame.sound.playA(key, -0.15F);
        else
        if(InputActionSet.selectCard_3.isJustPressed())
            CardCrawlGame.sound.playA(key, -0.1F);
        else
        if(InputActionSet.selectCard_4.isJustPressed())
            CardCrawlGame.sound.playA(key, -0.05F);
        else
        if(InputActionSet.selectCard_5.isJustPressed())
            CardCrawlGame.sound.playA(key, 0.0F);
        else
        if(InputActionSet.selectCard_6.isJustPressed())
            CardCrawlGame.sound.playA(key, 0.05F);
        else
        if(InputActionSet.selectCard_7.isJustPressed())
            CardCrawlGame.sound.playA(key, 0.1F);
        else
        if(InputActionSet.selectCard_8.isJustPressed())
            CardCrawlGame.sound.playA(key, 0.15F);
        else
        if(InputActionSet.selectCard_9.isJustPressed())
            CardCrawlGame.sound.playA(key, 0.2F);
        else
        if(InputActionSet.selectCard_10.isJustPressed())
            CardCrawlGame.sound.playA(key, 0.25F);
    }

    private void generateRarityLabel()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier = new int[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.DEPRECATED.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SHOP.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SPECIAL.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.STARTER.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[relic.tier.ordinal()])
        {
        case 1: // '\001'
            rarityLabel = TEXT[0];
            break;

        case 2: // '\002'
            rarityLabel = TEXT[1];
            break;

        case 3: // '\003'
            rarityLabel = TEXT[2];
            break;

        case 4: // '\004'
            rarityLabel = TEXT[3];
            break;

        case 5: // '\005'
            rarityLabel = TEXT[4];
            break;

        case 6: // '\006'
            rarityLabel = TEXT[5];
            break;

        case 7: // '\007'
            rarityLabel = TEXT[6];
            break;

        case 8: // '\b'
            rarityLabel = TEXT[7];
            break;
        }
    }

    private void generateFrameImg()
    {
        if(!relic.isSeen)
        {
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameCommon.png");
            return;
        }
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[relic.tier.ordinal()])
        {
        case 1: // '\001'
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameBoss.png");
            break;

        case 2: // '\002'
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameCommon.png");
            break;

        case 3: // '\003'
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameCommon.png");
            break;

        case 4: // '\004'
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameRare.png");
            break;

        case 5: // '\005'
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameRare.png");
            break;

        case 6: // '\006'
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameRare.png");
            break;

        case 7: // '\007'
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameCommon.png");
            break;

        case 8: // '\b'
            relicFrameImg = ImageMaster.loadImage("images/ui/relicFrameUncommon.png");
            break;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(fadeColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        renderPopupBg(sb);
        renderFrame(sb);
        renderArrows(sb);
        renderRelicImage(sb);
        renderName(sb);
        renderRarity(sb);
        renderDescription(sb);
        renderQuote(sb);
        renderTips(sb);
        popupHb.render(sb);
        if(prevHb != null)
            prevHb.render(sb);
        if(nextHb != null)
            nextHb.render(sb);
    }

    private void renderPopupBg(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.RELIC_POPUP, (float)Settings.WIDTH / 2.0F - 960F, (float)Settings.HEIGHT / 2.0F - 540F, 960F, 540F, 1920F, 1080F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1080, false, false);
    }

    private void renderFrame(SpriteBatch sb)
    {
        sb.draw(relicFrameImg, (float)Settings.WIDTH / 2.0F - 960F, (float)Settings.HEIGHT / 2.0F - 540F, 960F, 540F, 1920F, 1080F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1080, false, false);
    }

    private void renderArrows(SpriteBatch sb)
    {
        if(prevRelic != null)
        {
            sb.draw(ImageMaster.POPUP_ARROW, prevHb.cX - 128F, prevHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), (prevHb.cX - 32F) + 0.0F * Settings.scale, (prevHb.cY - 32F) + 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            if(prevHb.hovered)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
                sb.draw(ImageMaster.POPUP_ARROW, prevHb.cX - 128F, prevHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
            }
        }
        if(nextRelic != null)
        {
            sb.draw(ImageMaster.POPUP_ARROW, nextHb.cX - 128F, nextHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, true, false);
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), (nextHb.cX - 32F) + 0.0F * Settings.scale, (nextHb.cY - 32F) + 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            if(nextHb.hovered)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
                sb.draw(ImageMaster.POPUP_ARROW, nextHb.cX - 128F, nextHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, true, false);
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(770, 771);
            }
        }
    }

    private void renderRelicImage(SpriteBatch sb)
    {
        if(UnlockTracker.isRelicLocked(relic.relicId))
        {
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
            sb.draw(ImageMaster.RELIC_LOCK_OUTLINE, (float)Settings.WIDTH / 2.0F - 64F, ((float)Settings.HEIGHT / 2.0F - 64F) + RELIC_OFFSET_Y, 64F, 64F, 128F, 128F, Settings.scale * 2.0F, Settings.scale * 2.0F, 0.0F, 0, 0, 128, 128, false, false);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.RELIC_LOCK, (float)Settings.WIDTH / 2.0F - 64F, ((float)Settings.HEIGHT / 2.0F - 64F) + RELIC_OFFSET_Y, 64F, 64F, 128F, 128F, Settings.scale * 2.0F, Settings.scale * 2.0F, 0.0F, 0, 0, 128, 128, false, false);
            return;
        }
        if(!relic.isSeen)
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.75F));
        else
            sb.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
        sb.draw(relic.outlineImg, (float)Settings.WIDTH / 2.0F - 64F, ((float)Settings.HEIGHT / 2.0F - 64F) + RELIC_OFFSET_Y, 64F, 64F, 128F, 128F, Settings.scale * 2.0F, Settings.scale * 2.0F, 0.0F, 0, 0, 128, 128, false, false);
        if(!relic.isSeen)
            sb.setColor(Color.BLACK);
        else
            sb.setColor(Color.WHITE);
        if(largeImg == null)
            sb.draw(relic.img, (float)Settings.WIDTH / 2.0F - 64F, ((float)Settings.HEIGHT / 2.0F - 64F) + RELIC_OFFSET_Y, 64F, 64F, 128F, 128F, Settings.scale * 2.0F, Settings.scale * 2.0F, 0.0F, 0, 0, 128, 128, false, false);
        else
            sb.draw(largeImg, (float)Settings.WIDTH / 2.0F - 128F, ((float)Settings.HEIGHT / 2.0F - 128F) + RELIC_OFFSET_Y, 128F, 128F, 256F, 256F, Settings.scale, Settings.scale, 0.0F, 0, 0, 256, 256, false, false);
    }

    private void renderName(SpriteBatch sb)
    {
        if(UnlockTracker.isRelicLocked(relic.relicId))
            FontHelper.renderWrappedText(sb, FontHelper.SCP_cardDescFont, TEXT[8], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 265F * Settings.scale, 9999F, Settings.CREAM_COLOR, 0.9F);
        else
        if(relic.isSeen)
            FontHelper.renderWrappedText(sb, FontHelper.SCP_cardDescFont, relic.name, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 280F * Settings.scale, 9999F, Settings.CREAM_COLOR, 0.9F);
        else
            FontHelper.renderWrappedText(sb, FontHelper.SCP_cardDescFont, TEXT[9], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 265F * Settings.scale, 9999F, Settings.CREAM_COLOR, 0.9F);
    }

    private void renderRarity(SpriteBatch sb)
    {
        Color tmpColor;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[relic.tier.ordinal()])
        {
        case 1: // '\001'
            tmpColor = Settings.RED_TEXT_COLOR;
            break;

        case 4: // '\004'
            tmpColor = Settings.GOLD_COLOR;
            break;

        case 8: // '\b'
            tmpColor = Settings.BLUE_TEXT_COLOR;
            break;

        case 2: // '\002'
            tmpColor = Settings.CREAM_COLOR;
            break;

        case 7: // '\007'
            tmpColor = Settings.CREAM_COLOR;
            break;

        case 6: // '\006'
            tmpColor = Settings.GOLD_COLOR;
            break;

        case 5: // '\005'
            tmpColor = Settings.GOLD_COLOR;
            break;

        case 3: // '\003'
        default:
            tmpColor = Settings.CREAM_COLOR;
            break;
        }
        if(relic.isSeen)
            if(Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.VIE)
                FontHelper.renderWrappedText(sb, FontHelper.cardDescFont_N, (new StringBuilder()).append(TEXT[10]).append(rarityLabel).toString(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 240F * Settings.scale, 9999F, tmpColor, 1.0F);
            else
                FontHelper.renderWrappedText(sb, FontHelper.cardDescFont_N, (new StringBuilder()).append(rarityLabel).append(TEXT[10]).toString(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 240F * Settings.scale, 9999F, tmpColor, 1.0F);
    }

    private void renderDescription(SpriteBatch sb)
    {
        if(UnlockTracker.isRelicLocked(relic.relicId))
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, TEXT[11], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 140F * Settings.scale, Settings.CREAM_COLOR, 1.0F);
        else
        if(relic.isSeen)
            FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, relic.description, (float)Settings.WIDTH / 2.0F - 200F * Settings.scale, (float)Settings.HEIGHT / 2.0F - 140F * Settings.scale - FontHelper.getSmartHeight(FontHelper.cardDescFont_N, relic.description, DESC_LINE_WIDTH, DESC_LINE_SPACING) / 2.0F, DESC_LINE_WIDTH, DESC_LINE_SPACING, Settings.CREAM_COLOR);
        else
            FontHelper.renderFontCentered(sb, FontHelper.cardDescFont_N, TEXT[12], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 140F * Settings.scale, Settings.CREAM_COLOR, 1.0F);
    }

    private void renderQuote(SpriteBatch sb)
    {
        if(relic.isSeen)
            if(relic.flavorText != null)
                FontHelper.renderWrappedText(sb, FontHelper.SRV_quoteFont, relic.flavorText, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 310F * Settings.scale, DESC_LINE_WIDTH, Settings.CREAM_COLOR, 1.0F);
            else
                FontHelper.renderWrappedText(sb, FontHelper.SRV_quoteFont, "\"Missing quote...\"", (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 300F * Settings.scale, DESC_LINE_WIDTH, Settings.CREAM_COLOR, 1.0F);
    }

    private void renderTips(SpriteBatch sb)
    {
        if(relic.isSeen)
        {
            ArrayList t = new ArrayList();
            if(relic.tips.size() > 1)
            {
                for(int i = 1; i < relic.tips.size(); i++)
                    t.add(relic.tips.get(i));

            }
            if(!t.isEmpty())
                TipHelper.queuePowerTips((float)Settings.WIDTH / 2.0F + 340F * Settings.scale, 420F * Settings.scale, t);
        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public boolean isOpen;
    private ArrayList group;
    private AbstractRelic relic;
    private AbstractRelic prevRelic;
    private AbstractRelic nextRelic;
    private static final int W = 128;
    private Texture relicFrameImg;
    private Texture largeImg;
    private float fadeTimer;
    private Color fadeColor;
    private Hitbox popupHb;
    private Hitbox prevHb;
    private Hitbox nextHb;
    private String rarityLabel;
    private static final String LARGE_IMG_DIR = "images/largeRelics/";
    private static final float DESC_LINE_SPACING;
    private static final float DESC_LINE_WIDTH;
    private final float RELIC_OFFSET_Y;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("SingleViewRelicPopup");
        TEXT = uiStrings.TEXT;
        DESC_LINE_SPACING = 30F * Settings.scale;
        DESC_LINE_WIDTH = 418F * Settings.scale;
    }
}
