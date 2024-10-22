// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SingleCardViewPopup.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.*;

public class SingleCardViewPopup
{

    public SingleCardViewPopup()
    {
        isOpen = false;
        portraitImg = null;
        fadeTimer = 0.0F;
        fadeColor = Color.BLACK.cpy();
        upgradeHb = new Hitbox(250F * Settings.scale, 80F * Settings.scale);
        betaArtHb = null;
        viewBetaArt = false;
        prevHb = new Hitbox(200F * Settings.scale, 70F * Settings.scale);
        nextHb = new Hitbox(200F * Settings.scale, 70F * Settings.scale);
    }

    public void open(AbstractCard card, CardGroup group)
    {
        CardCrawlGame.isPopupOpen = true;
        prevCard = null;
        nextCard = null;
        prevHb = null;
        nextHb = null;
        int i = 0;
        do
        {
            if(i >= group.size())
                break;
            if(group.group.get(i) == card)
            {
                if(i != 0)
                    prevCard = (AbstractCard)group.group.get(i - 1);
                if(i != group.size() - 1)
                    nextCard = (AbstractCard)group.group.get(i + 1);
                break;
            }
            i++;
        } while(true);
        prevHb = new Hitbox(160F * Settings.scale, 160F * Settings.scale);
        nextHb = new Hitbox(160F * Settings.scale, 160F * Settings.scale);
        prevHb.move((float)Settings.WIDTH / 2.0F - 400F * Settings.scale, (float)Settings.HEIGHT / 2.0F);
        nextHb.move((float)Settings.WIDTH / 2.0F + 400F * Settings.scale, (float)Settings.HEIGHT / 2.0F);
        card_energy_w = 24F * Settings.scale;
        drawScale = 2.0F;
        cardHb = new Hitbox(550F * Settings.scale, 770F * Settings.scale);
        cardHb.move((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        this.card = card.makeStatEquivalentCopy();
        loadPortraitImg();
        this.group = group;
        isOpen = true;
        fadeTimer = 0.25F;
        fadeColor.a = 0.0F;
        current_x = (float)Settings.WIDTH / 2.0F - 10F * Settings.scale;
        current_y = (float)Settings.HEIGHT / 2.0F - 300F * Settings.scale;
        if(canToggleBetaArt())
        {
            if(allowUpgradePreview())
            {
                betaArtHb = new Hitbox(250F * Settings.scale, 80F * Settings.scale);
                betaArtHb.move((float)Settings.WIDTH / 2.0F + 270F * Settings.scale, 70F * Settings.scale);
                upgradeHb.move((float)Settings.WIDTH / 2.0F - 180F * Settings.scale, 70F * Settings.scale);
            } else
            {
                betaArtHb = new Hitbox(250F * Settings.scale, 80F * Settings.scale);
                betaArtHb.move((float)Settings.WIDTH / 2.0F, 70F * Settings.scale);
            }
            viewBetaArt = UnlockTracker.betaCardPref.getBoolean(card.cardID, false);
        } else
        {
            upgradeHb.move((float)Settings.WIDTH / 2.0F, 70F * Settings.scale);
            betaArtHb = null;
        }
    }

    private boolean canToggleBetaArt()
    {
        if(UnlockTracker.isAchievementUnlocked("THE_ENDING"))
            return true;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[];
            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[];
            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardType[com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardColor[com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
        {
        case 1: // '\001'
            return UnlockTracker.isAchievementUnlocked("RUBY_PLUS");

        case 2: // '\002'
            return UnlockTracker.isAchievementUnlocked("EMERALD_PLUS");

        case 3: // '\003'
            return UnlockTracker.isAchievementUnlocked("SAPPHIRE_PLUS");

        case 4: // '\004'
            return UnlockTracker.isAchievementUnlocked("AMETHYST_PLUS");
        }
        return false;
    }

    private void loadPortraitImg()
    {
        if(Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(card.cardID, false))
        {
            portraitImg = ImageMaster.loadImage((new StringBuilder()).append("images/1024PortraitsBeta/").append(card.assetUrl).append(".png").toString());
        } else
        {
            portraitImg = ImageMaster.loadImage((new StringBuilder()).append("images/1024Portraits/").append(card.assetUrl).append(".png").toString());
            if(portraitImg == null)
                portraitImg = ImageMaster.loadImage((new StringBuilder()).append("images/1024PortraitsBeta/").append(card.assetUrl).append(".png").toString());
        }
    }

    public void open(AbstractCard card)
    {
        CardCrawlGame.isPopupOpen = true;
        prevCard = null;
        nextCard = null;
        prevHb = null;
        nextHb = null;
        card_energy_w = 24F * Settings.scale;
        drawScale = 2.0F;
        cardHb = new Hitbox(550F * Settings.scale, 770F * Settings.scale);
        cardHb.move((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
        this.card = card.makeStatEquivalentCopy();
        loadPortraitImg();
        group = null;
        isOpen = true;
        fadeTimer = 0.25F;
        fadeColor.a = 0.0F;
        current_x = (float)Settings.WIDTH / 2.0F - 10F * Settings.scale;
        current_y = (float)Settings.HEIGHT / 2.0F - 300F * Settings.scale;
        betaArtHb = null;
        if(canToggleBetaArt())
        {
            betaArtHb = new Hitbox(250F * Settings.scale, 80F * Settings.scale);
            betaArtHb.move((float)Settings.WIDTH / 2.0F + 270F * Settings.scale, 70F * Settings.scale);
            upgradeHb.move((float)Settings.WIDTH / 2.0F - 180F * Settings.scale, 70F * Settings.scale);
            viewBetaArt = UnlockTracker.betaCardPref.getBoolean(card.cardID, false);
        } else
        {
            upgradeHb.move((float)Settings.WIDTH / 2.0F, 70F * Settings.scale);
        }
    }

    public void close()
    {
        isViewingUpgrade = false;
        InputHelper.justReleasedClickLeft = false;
        CardCrawlGame.isPopupOpen = false;
        isOpen = false;
        if(portraitImg != null)
        {
            portraitImg.dispose();
            portraitImg = null;
        }
    }

    public void update()
    {
        cardHb.update();
        updateArrows();
        updateInput();
        updateFade();
        if(allowUpgradePreview())
            updateUpgradePreview();
        if(betaArtHb != null && canToggleBetaArt())
            updateBetaArtToggler();
    }

    private void updateBetaArtToggler()
    {
        betaArtHb.update();
        if(betaArtHb.hovered && InputHelper.justClickedLeft)
            betaArtHb.clickStarted = true;
        if(betaArtHb.clicked || CInputActionSet.topPanel.isJustPressed())
        {
            CInputActionSet.topPanel.unpress();
            betaArtHb.clicked = false;
            viewBetaArt = !viewBetaArt;
            UnlockTracker.betaCardPref.putBoolean(card.cardID, viewBetaArt);
            UnlockTracker.betaCardPref.flush();
            if(portraitImg != null)
                portraitImg.dispose();
            loadPortraitImg();
        }
    }

    private void updateUpgradePreview()
    {
        upgradeHb.update();
        if(upgradeHb.hovered && InputHelper.justClickedLeft)
            upgradeHb.clickStarted = true;
        if(upgradeHb.clicked || CInputActionSet.proceed.isJustPressed())
        {
            CInputActionSet.proceed.unpress();
            upgradeHb.clicked = false;
            isViewingUpgrade = !isViewingUpgrade;
        }
    }

    private boolean allowUpgradePreview()
    {
        return enableUpgradeToggle && card.color != com.megacrit.cardcrawl.cards.AbstractCard.CardColor.CURSE && card.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.STATUS;
    }

    private void updateArrows()
    {
        if(prevCard != null)
        {
            prevHb.update();
            if(prevHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(prevHb.clicked || prevCard != null && CInputActionSet.pageLeftViewDeck.isJustPressed())
            {
                CInputActionSet.pageLeftViewDeck.unpress();
                openPrev();
            }
        }
        if(nextCard != null)
        {
            nextHb.update();
            if(nextHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            if(nextHb.clicked || nextCard != null && CInputActionSet.pageRightViewExhaust.isJustPressed())
            {
                CInputActionSet.pageRightViewExhaust.unpress();
                openNext();
            }
        }
    }

    private void updateInput()
    {
        if(InputHelper.justClickedLeft)
        {
            if(prevCard != null && prevHb.hovered)
            {
                prevHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                return;
            }
            if(nextCard != null && nextHb.hovered)
            {
                nextHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
                return;
            }
        }
        if(InputHelper.justClickedLeft)
        {
            if(!cardHb.hovered && !upgradeHb.hovered && (betaArtHb == null || betaArtHb != null && !betaArtHb.hovered))
            {
                close();
                InputHelper.justClickedLeft = false;
                FontHelper.ClearSCPFontTextures();
            }
        } else
        if(InputHelper.pressedEscape || CInputActionSet.cancel.isJustPressed())
        {
            CInputActionSet.cancel.unpress();
            InputHelper.pressedEscape = false;
            close();
            FontHelper.ClearSCPFontTextures();
        }
        if(prevCard != null && InputActionSet.left.isJustPressed())
            openPrev();
        else
        if(nextCard != null && InputActionSet.right.isJustPressed())
            openNext();
    }

    private void openPrev()
    {
        boolean tmp = isViewingUpgrade;
        close();
        open(prevCard, group);
        isViewingUpgrade = tmp;
        fadeTimer = 0.0F;
        fadeColor.a = 0.9F;
    }

    private void openNext()
    {
        boolean tmp = isViewingUpgrade;
        close();
        open(nextCard, group);
        isViewingUpgrade = tmp;
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

    public void render(SpriteBatch sb)
    {
        AbstractCard copy = null;
        if(isViewingUpgrade)
        {
            copy = card.makeStatEquivalentCopy();
            card.upgrade();
            card.displayUpgrades();
        }
        sb.setColor(fadeColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setColor(Color.WHITE);
        renderCardBack(sb);
        renderPortrait(sb);
        renderFrame(sb);
        renderCardBanner(sb);
        renderCardTypeText(sb);
        if(Settings.lineBreakViaCharacter)
            renderDescriptionCN(sb);
        else
            renderDescription(sb);
        renderTitle(sb);
        renderCost(sb);
        renderArrows(sb);
        renderTips(sb);
        cardHb.render(sb);
        if(nextHb != null)
            nextHb.render(sb);
        if(prevHb != null)
            prevHb.render(sb);
        FontHelper.cardTitleFont.getData().setScale(1.0F);
        if(canToggleBetaArt())
            renderBetaArtToggle(sb);
        if(allowUpgradePreview())
        {
            renderUpgradeViewToggle(sb);
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.proceed.getKeyImg(), upgradeHb.cX - 132F * Settings.scale - 32F, -32F + 67F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if(betaArtHb != null && Settings.isControllerMode)
            sb.draw(CInputActionSet.topPanel.getKeyImg(), betaArtHb.cX - 132F * Settings.scale - 32F, -32F + 67F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        if(copy != null)
            card = copy;
    }

    public void renderCardBack(SpriteBatch sb)
    {
        com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion tmpImg = getCardBackAtlasRegion();
        if(tmpImg != null)
        {
            renderHelper(sb, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, tmpImg);
        } else
        {
            Texture img = getCardBackImg();
            if(img != null)
                sb.draw(img, (float)Settings.WIDTH / 2.0F - 512F, (float)Settings.HEIGHT / 2.0F - 512F, 512F, 512F, 1024F, 1024F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
        }
    }

    private Texture getCardBackImg()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[card.type.ordinal()])
        {
        case 1: // '\001'
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
            {
            }
            // fall through

        case 2: // '\002'
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
            {
            }
            // fall through

        default:
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
            {
            default:
                return null;
            }
        }
    }

    private com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getCardBackAtlasRegion()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[card.type.ordinal()])
        {
        case 1: // '\001'
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
            {
            case 1: // '\001'
                return ImageMaster.CARD_ATTACK_BG_RED_L;

            case 2: // '\002'
                return ImageMaster.CARD_ATTACK_BG_GREEN_L;

            case 3: // '\003'
                return ImageMaster.CARD_ATTACK_BG_BLUE_L;

            case 4: // '\004'
                return ImageMaster.CARD_ATTACK_BG_PURPLE_L;

            case 5: // '\005'
                return ImageMaster.CARD_ATTACK_BG_GRAY_L;
            }
            // fall through

        case 2: // '\002'
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
            {
            case 1: // '\001'
                return ImageMaster.CARD_POWER_BG_RED_L;

            case 2: // '\002'
                return ImageMaster.CARD_POWER_BG_GREEN_L;

            case 3: // '\003'
                return ImageMaster.CARD_POWER_BG_BLUE_L;

            case 4: // '\004'
                return ImageMaster.CARD_POWER_BG_PURPLE_L;

            case 5: // '\005'
                return ImageMaster.CARD_POWER_BG_GRAY_L;
            }
            // fall through

        default:
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
            {
            case 1: // '\001'
                return ImageMaster.CARD_SKILL_BG_RED_L;

            case 2: // '\002'
                return ImageMaster.CARD_SKILL_BG_GREEN_L;

            case 3: // '\003'
                return ImageMaster.CARD_SKILL_BG_BLUE_L;

            case 4: // '\004'
                return ImageMaster.CARD_SKILL_BG_PURPLE_L;

            case 5: // '\005'
                return ImageMaster.CARD_SKILL_BG_GRAY_L;

            case 6: // '\006'
                return ImageMaster.CARD_SKILL_BG_BLACK_L;
            }
            break;
        }
        return null;
    }

    private void renderPortrait(SpriteBatch sb)
    {
        com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img = null;
        if(card.isLocked)
        {
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[card.type.ordinal()])
            {
            case 1: // '\001'
                img = ImageMaster.CARD_LOCKED_ATTACK_L;
                break;

            case 2: // '\002'
                img = ImageMaster.CARD_LOCKED_POWER_L;
                break;

            case 3: // '\003'
            default:
                img = ImageMaster.CARD_LOCKED_SKILL_L;
                break;
            }
            renderHelper(sb, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 136F * Settings.scale, img);
        } else
        if(portraitImg != null)
            sb.draw(portraitImg, (float)Settings.WIDTH / 2.0F - 250F, ((float)Settings.HEIGHT / 2.0F - 190F) + 136F * Settings.scale, 250F, 190F, 500F, 380F, Settings.scale, Settings.scale, 0.0F, 0, 0, 500, 380, false, false);
        else
        if(card.jokePortrait != null)
            sb.draw(card.jokePortrait, (float)Settings.WIDTH / 2.0F - (float)card.portrait.packedWidth / 2.0F, ((float)Settings.HEIGHT / 2.0F - (float)card.portrait.packedHeight / 2.0F) + 140F * Settings.scale, (float)card.portrait.packedWidth / 2.0F, (float)card.portrait.packedHeight / 2.0F, card.portrait.packedWidth, card.portrait.packedHeight, drawScale * Settings.scale, drawScale * Settings.scale, 0.0F);
    }

    private void renderFrame(SpriteBatch sb)
    {
        com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion tmpImg = null;
        float tOffset = 0.0F;
        float tWidth = 0.0F;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[card.type.ordinal()])
        {
        case 1: // '\001'
            tWidth = AbstractCard.typeWidthAttack;
            tOffset = AbstractCard.typeOffsetAttack;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
            {
            case 1: // '\001'
                tmpImg = ImageMaster.CARD_FRAME_ATTACK_UNCOMMON_L;
                break;

            case 2: // '\002'
                tmpImg = ImageMaster.CARD_FRAME_ATTACK_RARE_L;
                break;

            case 3: // '\003'
            default:
                tmpImg = ImageMaster.CARD_FRAME_ATTACK_COMMON_L;
                break;
            }
            break;

        case 2: // '\002'
            tWidth = AbstractCard.typeWidthPower;
            tOffset = AbstractCard.typeOffsetPower;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
            {
            case 1: // '\001'
                tmpImg = ImageMaster.CARD_FRAME_POWER_UNCOMMON_L;
                break;

            case 2: // '\002'
                tmpImg = ImageMaster.CARD_FRAME_POWER_RARE_L;
                break;

            case 3: // '\003'
            default:
                tmpImg = ImageMaster.CARD_FRAME_POWER_COMMON_L;
                break;
            }
            break;

        case 4: // '\004'
            tWidth = AbstractCard.typeWidthCurse;
            tOffset = AbstractCard.typeOffsetCurse;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
            {
            case 1: // '\001'
                tmpImg = ImageMaster.CARD_FRAME_SKILL_UNCOMMON_L;
                break;

            case 2: // '\002'
                tmpImg = ImageMaster.CARD_FRAME_SKILL_RARE_L;
                break;

            case 3: // '\003'
            default:
                tmpImg = ImageMaster.CARD_FRAME_SKILL_COMMON_L;
                break;
            }
            break;

        case 5: // '\005'
            tWidth = AbstractCard.typeWidthStatus;
            tOffset = AbstractCard.typeOffsetStatus;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
            {
            case 1: // '\001'
                tmpImg = ImageMaster.CARD_FRAME_SKILL_UNCOMMON_L;
                break;

            case 2: // '\002'
                tmpImg = ImageMaster.CARD_FRAME_SKILL_RARE_L;
                break;

            case 3: // '\003'
            default:
                tmpImg = ImageMaster.CARD_FRAME_SKILL_COMMON_L;
                break;
            }
            break;

        case 3: // '\003'
            tWidth = AbstractCard.typeWidthSkill;
            tOffset = AbstractCard.typeOffsetSkill;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
            {
            case 1: // '\001'
                tmpImg = ImageMaster.CARD_FRAME_SKILL_UNCOMMON_L;
                break;

            case 2: // '\002'
                tmpImg = ImageMaster.CARD_FRAME_SKILL_RARE_L;
                break;

            case 3: // '\003'
            default:
                tmpImg = ImageMaster.CARD_FRAME_SKILL_COMMON_L;
                break;
            }
            break;
        }
        if(tmpImg != null)
        {
            renderHelper(sb, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, tmpImg);
        } else
        {
            Texture img = getFrameImg();
            tWidth = AbstractCard.typeWidthSkill;
            tOffset = AbstractCard.typeOffsetSkill;
            if(img != null)
                sb.draw(img, (float)Settings.WIDTH / 2.0F - 512F, (float)Settings.HEIGHT / 2.0F - 512F, 512F, 512F, 1024F, 1024F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
            else
                renderHelper(sb, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, ImageMaster.CARD_FRAME_SKILL_COMMON_L);
        }
        renderDynamicFrame(sb, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, tOffset, tWidth);
    }

    private Texture getFrameImg()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
        {
        default:
            return null;
        }
    }

    private void renderDynamicFrame(SpriteBatch sb, float x, float y, float typeOffset, float typeWidth)
    {
        if(typeWidth <= 1.1F)
            return;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
        {
        case 3: // '\003'
        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_COMMON_FRAME_MID_L, 0.0F, typeWidth);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_COMMON_FRAME_LEFT_L, -typeOffset, 1.0F);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_COMMON_FRAME_RIGHT_L, typeOffset, 1.0F);
            break;

        case 1: // '\001'
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_UNCOMMON_FRAME_MID_L, 0.0F, typeWidth);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_UNCOMMON_FRAME_LEFT_L, -typeOffset, 1.0F);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_UNCOMMON_FRAME_RIGHT_L, typeOffset, 1.0F);
            break;

        case 2: // '\002'
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_RARE_FRAME_MID_L, 0.0F, typeWidth);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_RARE_FRAME_LEFT_L, -typeOffset, 1.0F);
            dynamicFrameRenderHelper(sb, ImageMaster.CARD_RARE_FRAME_RIGHT_L, typeOffset, 1.0F);
            break;
        }
    }

    private void dynamicFrameRenderHelper(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img, float xOffset, float xScale)
    {
        sb.draw(img, (((float)Settings.WIDTH / 2.0F + img.offsetX) - (float)img.originalWidth / 2.0F) + xOffset * drawScale, ((float)Settings.HEIGHT / 2.0F + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, Settings.scale * xScale, Settings.scale, 0.0F);
    }

    private void renderCardBanner(SpriteBatch sb)
    {
        com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion tmpImg = null;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
        {
        case 1: // '\001'
            tmpImg = ImageMaster.CARD_BANNER_UNCOMMON_L;
            break;

        case 2: // '\002'
            tmpImg = ImageMaster.CARD_BANNER_RARE_L;
            break;

        case 3: // '\003'
            tmpImg = ImageMaster.CARD_BANNER_COMMON_L;
            break;
        }
        if(tmpImg != null)
        {
            renderHelper(sb, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, tmpImg);
        } else
        {
            Texture img = getBannerImg();
            if(img != null)
            {
                sb.draw(img, (float)Settings.WIDTH / 2.0F - 512F, (float)Settings.HEIGHT / 2.0F - 512F, 512F, 512F, 1024F, 1024F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
            } else
            {
                tmpImg = ImageMaster.CARD_BANNER_COMMON_L;
                renderHelper(sb, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, tmpImg);
            }
        }
    }

    private Texture getBannerImg()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[card.rarity.ordinal()])
        {
        default:
            return null;
        }
    }

    private String getDynamicValue(char key)
    {
        switch(key)
        {
        case 66: // 'B'
            if(card.isBlockModified)
            {
                if(card.block >= card.baseBlock)
                    return (new StringBuilder()).append("[#7fff00]").append(Integer.toString(card.block)).append("[]").toString();
                else
                    return (new StringBuilder()).append("[#ff6563]").append(Integer.toString(card.block)).append("[]").toString();
            } else
            {
                return Integer.toString(card.baseBlock);
            }

        case 68: // 'D'
            if(card.isDamageModified)
            {
                if(card.damage >= card.baseDamage)
                    return (new StringBuilder()).append("[#7fff00]").append(Integer.toString(card.damage)).append("[]").toString();
                else
                    return (new StringBuilder()).append("[#ff6563]").append(Integer.toString(card.damage)).append("[]").toString();
            } else
            {
                return Integer.toString(card.baseDamage);
            }

        case 77: // 'M'
            if(card.isMagicNumberModified)
            {
                if(card.magicNumber >= card.baseMagicNumber)
                    return (new StringBuilder()).append("[#7fff00]").append(Integer.toString(card.magicNumber)).append("[]").toString();
                else
                    return (new StringBuilder()).append("[#ff6563]").append(Integer.toString(card.magicNumber)).append("[]").toString();
            } else
            {
                return Integer.toString(card.baseMagicNumber);
            }
        }
        return Integer.toString(-99);
    }

    private void renderDescriptionCN(SpriteBatch sb)
    {
        if(card.isLocked || !card.isSeen)
        {
            FontHelper.renderFontCentered(sb, FontHelper.largeCardFont, "? ? ?", (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 195F * Settings.scale, Settings.CREAM_COLOR);
            return;
        }
        BitmapFont font = FontHelper.SCP_cardDescFont;
        float draw_y = current_y + 100F * Settings.scale;
        draw_y += (float)card.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
        float spacing = (1.53F * -font.getCapHeight()) / Settings.scale / drawScale;
        GlyphLayout gl = new GlyphLayout();
        for(int i = 0; i < card.description.size(); i++)
        {
            float start_x = 0.0F;
            if(Settings.leftAlignCards)
                start_x = current_x - 214F * Settings.scale;
            else
                start_x = current_x - (((DescriptionLine)card.description.get(i)).width * drawScale) / 2.0F - 20F * Settings.scale;
            String as[] = ((DescriptionLine)card.description.get(i)).getCachedTokenizedTextCN();
            int k = as.length;
            for(int l = 0; l < k; l++)
            {
                String tmp = as[l];
                String updateTmp = null;
                int j = 0;
                do
                {
                    if(j >= tmp.length())
                        break;
                    if(tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                    {
                        updateTmp = tmp.substring(0, j);
                        updateTmp = (new StringBuilder()).append(updateTmp).append(getDynamicValue(tmp.charAt(j))).toString();
                        updateTmp = (new StringBuilder()).append(updateTmp).append(tmp.substring(j + 1)).toString();
                        break;
                    }
                    j++;
                } while(true);
                if(updateTmp != null)
                    tmp = updateTmp;
                j = 0;
                do
                {
                    if(j >= tmp.length())
                        break;
                    if(tmp.charAt(j) == 'D' || tmp.charAt(j) == 'B' && !tmp.contains("[B]") || tmp.charAt(j) == 'M')
                    {
                        updateTmp = tmp.substring(0, j);
                        updateTmp = (new StringBuilder()).append(updateTmp).append(getDynamicValue(tmp.charAt(j))).toString();
                        updateTmp = (new StringBuilder()).append(updateTmp).append(tmp.substring(j + 1)).toString();
                        break;
                    }
                    j++;
                } while(true);
                if(updateTmp != null)
                    tmp = updateTmp;
                if(!tmp.isEmpty() && tmp.charAt(0) == '*')
                {
                    tmp = tmp.substring(1);
                    String punctuation = "";
                    if(tmp.length() > 1 && tmp.charAt(tmp.length() - 2) != '+' && !Character.isLetter(tmp.charAt(tmp.length() - 2)))
                    {
                        punctuation = (new StringBuilder()).append(punctuation).append(tmp.charAt(tmp.length() - 2)).toString();
                        tmp = tmp.substring(0, tmp.length() - 2);
                        punctuation = (new StringBuilder()).append(punctuation).append(' ').toString();
                    }
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.GOLD_COLOR);
                    start_x = Math.round(start_x + gl.width);
                    gl.setText(font, punctuation);
                    FontHelper.renderRotatedText(sb, font, punctuation, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
                    gl.setText(font, punctuation);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[R]"))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_red, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[G]"))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_green, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[B]"))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_blue, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[W]"))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_purple, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                } else
                {
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
                    start_x += gl.width;
                }
            }

        }

        font.getData().setScale(1.0F);
    }

    private void renderDescription(SpriteBatch sb)
    {
        if(card.isLocked || !card.isSeen)
        {
            FontHelper.renderFontCentered(sb, FontHelper.largeCardFont, "? ? ?", (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F - 195F * Settings.scale, Settings.CREAM_COLOR);
            return;
        }
        BitmapFont font = FontHelper.SCP_cardDescFont;
        float draw_y = current_y + 100F * Settings.scale;
        draw_y += (float)card.description.size() * font.getCapHeight() * 0.775F - font.getCapHeight() * 0.375F;
        float spacing = (1.53F * -font.getCapHeight()) / Settings.scale / drawScale;
        GlyphLayout gl = new GlyphLayout();
        for(int i = 0; i < card.description.size(); i++)
        {
            float start_x = current_x - (((DescriptionLine)card.description.get(i)).width * drawScale) / 2.0F;
            String as[] = ((DescriptionLine)card.description.get(i)).getCachedTokenizedText();
            int j = as.length;
            for(int k = 0; k < j; k++)
            {
                String tmp = as[k];
                if(tmp.charAt(0) == '*')
                {
                    tmp = tmp.substring(1);
                    String punctuation = "";
                    if(tmp.length() > 1 && tmp.charAt(tmp.length() - 2) != '+' && !Character.isLetter(tmp.charAt(tmp.length() - 2)))
                    {
                        punctuation = (new StringBuilder()).append(punctuation).append(tmp.charAt(tmp.length() - 2)).toString();
                        tmp = tmp.substring(0, tmp.length() - 2);
                        punctuation = (new StringBuilder()).append(punctuation).append(' ').toString();
                    }
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.GOLD_COLOR);
                    start_x = Math.round(start_x + gl.width);
                    gl.setText(font, punctuation);
                    FontHelper.renderRotatedText(sb, font, punctuation, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
                    gl.setText(font, punctuation);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.charAt(0) == '!')
                {
                    if(tmp.length() == 4)
                    {
                        start_x += renderDynamicVariable(tmp.charAt(1), start_x, draw_y, i, font, sb, null);
                        continue;
                    }
                    if(tmp.length() == 5)
                        start_x += renderDynamicVariable(tmp.charAt(1), start_x, draw_y, i, font, sb, Character.valueOf(tmp.charAt(3)));
                    continue;
                }
                if(tmp.equals("[R] "))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_red, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[R]. "))
                {
                    gl.width = (card_energy_w * drawScale) / Settings.scale;
                    renderSmallEnergy(sb, AbstractCard.orb_red, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD, current_x, current_y, (start_x - current_x) + card_energy_w * drawScale, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
                    start_x += gl.width;
                    gl.setText(font, LocalizedStrings.PERIOD);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[G] "))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_green, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[G]. "))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_green, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD, current_x, current_y, (start_x - current_x) + card_energy_w * drawScale, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[B] "))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_blue, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[B]. "))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_blue, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD, current_x, current_y, (start_x - current_x) + card_energy_w * drawScale, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[W] "))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_purple, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    start_x += gl.width;
                    continue;
                }
                if(tmp.equals("[W]. "))
                {
                    gl.width = card_energy_w * drawScale;
                    renderSmallEnergy(sb, AbstractCard.orb_purple, (start_x - current_x) / Settings.scale / drawScale, -87F - ((((float)card.description.size() - 4F) / 2.0F - (float)i) + 1.0F) * spacing);
                    FontHelper.renderRotatedText(sb, font, LocalizedStrings.PERIOD, current_x, current_y, (start_x - current_x) + card_energy_w * drawScale, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
                    start_x += gl.width;
                } else
                {
                    gl.setText(font, tmp);
                    FontHelper.renderRotatedText(sb, font, tmp, current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
                    start_x += gl.width;
                }
            }

        }

        font.getData().setScale(1.0F);
    }

    private void renderSmallEnergy(SpriteBatch sb, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion region, float x, float y)
    {
        sb.setColor(Color.WHITE);
        sb.draw(region.getTexture(), (current_x + x * Settings.scale * drawScale + region.offsetX * Settings.scale) - 4F * Settings.scale, current_y + y * Settings.scale * drawScale + 280F * Settings.scale, 0.0F, 0.0F, region.packedWidth, region.packedHeight, drawScale * Settings.scale, drawScale * Settings.scale, 0.0F, region.getRegionX(), region.getRegionY(), region.getRegionWidth(), region.getRegionHeight(), false, false);
    }

    private void renderCardTypeText(SpriteBatch sb)
    {
        String label = "";
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardType[card.type.ordinal()])
        {
        case 1: // '\001'
            label = TEXT[0];
            break;

        case 3: // '\003'
            label = TEXT[1];
            break;

        case 2: // '\002'
            label = TEXT[2];
            break;

        case 4: // '\004'
            label = TEXT[3];
            break;

        case 5: // '\005'
            label = TEXT[7];
            break;
        }
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, label, (float)Settings.WIDTH / 2.0F + 3F * Settings.scale, (float)Settings.HEIGHT / 2.0F - 40F * Settings.scale, CARD_TYPE_COLOR);
    }

    private float renderDynamicVariable(char key, float start_x, float draw_y, int i, BitmapFont font, SpriteBatch sb, Character end)
    {
        StringBuilder stringBuilder = new StringBuilder();
        Color c = null;
        int num = 0;
        switch(key)
        {
        default:
            break;

        case 68: // 'D'
            num = card.baseDamage;
            if(card.upgradedDamage)
                c = Settings.GREEN_TEXT_COLOR;
            else
                c = Settings.CREAM_COLOR;
            break;

        case 66: // 'B'
            num = card.baseBlock;
            if(card.upgradedBlock)
                c = Settings.GREEN_TEXT_COLOR;
            else
                c = Settings.CREAM_COLOR;
            break;

        case 77: // 'M'
            num = card.baseMagicNumber;
            if(card.upgradedMagicNumber)
                c = Settings.GREEN_TEXT_COLOR;
            else
                c = Settings.CREAM_COLOR;
            break;
        }
        stringBuilder.append(Integer.toString(num));
        gl.setText(font, stringBuilder.toString());
        FontHelper.renderRotatedText(sb, font, stringBuilder.toString(), current_x, current_y, (start_x - current_x) + gl.width / 2.0F, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, c);
        if(end != null)
            FontHelper.renderRotatedText(sb, font, Character.toString(end.charValue()), current_x, current_y, (start_x - current_x) + gl.width + 10F * Settings.scale, (((float)i * 1.53F * -font.getCapHeight() + draw_y) - current_y) + -12F, 0.0F, true, Settings.CREAM_COLOR);
        stringBuilder.append(' ');
        gl.setText(font, stringBuilder.toString());
        return gl.width;
    }

    private void renderTitle(SpriteBatch sb)
    {
        if(card.isLocked)
            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, TEXT[4], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 338F * Settings.scale, Settings.CREAM_COLOR);
        else
        if(card.isSeen)
        {
            if(!isViewingUpgrade || allowUpgradePreview())
                FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, card.name, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 338F * Settings.scale, Settings.CREAM_COLOR);
            else
                FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, card.name, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 338F * Settings.scale, Settings.GREEN_TEXT_COLOR);
        } else
        {
            FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, TEXT[5], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 338F * Settings.scale, Settings.CREAM_COLOR);
        }
    }

    private void renderCost(SpriteBatch sb)
    {
        if(card.isLocked || !card.isSeen)
            return;
        if(card.cost > -2)
        {
            com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion tmpImg = null;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardColor[card.color.ordinal()])
            {
            case 1: // '\001'
                tmpImg = ImageMaster.CARD_RED_ORB_L;
                break;

            case 2: // '\002'
                tmpImg = ImageMaster.CARD_GREEN_ORB_L;
                break;

            case 3: // '\003'
                tmpImg = ImageMaster.CARD_BLUE_ORB_L;
                break;

            case 4: // '\004'
                tmpImg = ImageMaster.CARD_PURPLE_ORB_L;
                break;

            case 5: // '\005'
            default:
                tmpImg = ImageMaster.CARD_GRAY_ORB_L;
                break;
            }
            if(tmpImg != null)
                renderHelper(sb, (float)Settings.WIDTH / 2.0F - 270F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 380F * Settings.scale, tmpImg);
        }
        Color c = null;
        if(card.isCostModified)
            c = Settings.GREEN_TEXT_COLOR;
        else
            c = Settings.CREAM_COLOR;
        switch(card.cost)
        {
        case -1: 
            FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, "X", (float)Settings.WIDTH / 2.0F - 292F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 404F * Settings.scale, c);
            break;

        case 1: // '\001'
            FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(card.cost), (float)Settings.WIDTH / 2.0F - 284F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 404F * Settings.scale, c);
            break;

        case 0: // '\0'
        default:
            FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, Integer.toString(card.cost), (float)Settings.WIDTH / 2.0F - 292F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 404F * Settings.scale, c);
            break;

        case -2: 
            break;
        }
    }

    private void renderHelper(SpriteBatch sb, float x, float y, com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img)
    {
        if(img == null)
        {
            return;
        } else
        {
            sb.draw(img, (x + img.offsetX) - (float)img.originalWidth / 2.0F, (y + img.offsetY) - (float)img.originalHeight / 2.0F, (float)img.originalWidth / 2.0F - img.offsetX, (float)img.originalHeight / 2.0F - img.offsetY, img.packedWidth, img.packedHeight, Settings.scale, Settings.scale, 0.0F);
            return;
        }
    }

    private void renderArrows(SpriteBatch sb)
    {
        if(prevCard != null)
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
        if(nextCard != null)
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

    private void renderBetaArtToggle(SpriteBatch sb)
    {
        if(betaArtHb == null)
            return;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.CHECKBOX, betaArtHb.cX - 80F * Settings.scale - 32F, betaArtHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        if(betaArtHb.hovered)
            FontHelper.renderFont(sb, FontHelper.cardTitleFont, TEXT[14], betaArtHb.cX - 45F * Settings.scale, betaArtHb.cY + 10F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        else
            FontHelper.renderFont(sb, FontHelper.cardTitleFont, TEXT[14], betaArtHb.cX - 45F * Settings.scale, betaArtHb.cY + 10F * Settings.scale, Settings.GOLD_COLOR);
        if(viewBetaArt)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.TICK, betaArtHb.cX - 80F * Settings.scale - 32F, betaArtHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        betaArtHb.render(sb);
    }

    private void renderUpgradeViewToggle(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.CHECKBOX, upgradeHb.cX - 80F * Settings.scale - 32F, upgradeHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        if(upgradeHb.hovered)
            FontHelper.renderFont(sb, FontHelper.cardTitleFont, TEXT[6], upgradeHb.cX - 45F * Settings.scale, upgradeHb.cY + 10F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        else
            FontHelper.renderFont(sb, FontHelper.cardTitleFont, TEXT[6], upgradeHb.cX - 45F * Settings.scale, upgradeHb.cY + 10F * Settings.scale, Settings.GOLD_COLOR);
        if(isViewingUpgrade)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.TICK, upgradeHb.cX - 80F * Settings.scale - 32F, upgradeHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        upgradeHb.render(sb);
    }

    private void renderTips(SpriteBatch sb)
    {
        ArrayList t = new ArrayList();
        if(card.isLocked)
            t.add(new PowerTip(TEXT[4], (String)GameDictionary.keywords.get(TEXT[4].toLowerCase())));
        else
        if(!card.isSeen)
        {
            t.add(new PowerTip(TEXT[5], (String)GameDictionary.keywords.get(TEXT[5].toLowerCase())));
        } else
        {
            Iterator iterator = card.keywords.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                String s = (String)iterator.next();
                if(!s.equals("[R]") && !s.equals("[G]") && !s.equals("[B]") && !s.equals("[W]"))
                    t.add(new PowerTip(TipHelper.capitalize(s), (String)GameDictionary.keywords.get(s)));
            } while(true);
        }
        if(!t.isEmpty())
            TipHelper.queuePowerTips((float)Settings.WIDTH / 2.0F + 340F * Settings.scale, 420F * Settings.scale, t);
        if(card.cardsToPreview != null)
            card.renderCardPreviewInSingleView(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public boolean isOpen;
    private CardGroup group;
    private AbstractCard card;
    private AbstractCard prevCard;
    private AbstractCard nextCard;
    private Texture portraitImg;
    private Hitbox nextHb;
    private Hitbox prevHb;
    private Hitbox cardHb;
    private float fadeTimer;
    private Color fadeColor;
    private static final float LINE_SPACING = 1.53F;
    private float current_x;
    private float current_y;
    private float drawScale;
    private float card_energy_w;
    private static final float DESC_OFFSET_Y2 = -12F;
    private static final Color CARD_TYPE_COLOR = new Color(0.35F, 0.35F, 0.35F, 1.0F);
    private static final GlyphLayout gl = new GlyphLayout();
    public static boolean isViewingUpgrade = false;
    public static boolean enableUpgradeToggle = true;
    private Hitbox upgradeHb;
    private Hitbox betaArtHb;
    private boolean viewBetaArt;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("SingleCardViewPopup");
        TEXT = uiStrings.TEXT;
    }
}
