// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RewardItem.java

package com.megacrit.cardcrawl.rewards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Colossus;
import com.megacrit.cardcrawl.daily.mods.Midas;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.TreasureRoom;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RewardItem
{
    public static final class RewardType extends Enum
    {

        public static RewardType[] values()
        {
            return (RewardType[])$VALUES.clone();
        }

        public static RewardType valueOf(String name)
        {
            return (RewardType)Enum.valueOf(com/megacrit/cardcrawl/rewards/RewardItem$RewardType, name);
        }

        public static final RewardType CARD;
        public static final RewardType GOLD;
        public static final RewardType RELIC;
        public static final RewardType POTION;
        public static final RewardType STOLEN_GOLD;
        public static final RewardType EMERALD_KEY;
        public static final RewardType SAPPHIRE_KEY;
        private static final RewardType $VALUES[];

        static 
        {
            CARD = new RewardType("CARD", 0);
            GOLD = new RewardType("GOLD", 1);
            RELIC = new RewardType("RELIC", 2);
            POTION = new RewardType("POTION", 3);
            STOLEN_GOLD = new RewardType("STOLEN_GOLD", 4);
            EMERALD_KEY = new RewardType("EMERALD_KEY", 5);
            SAPPHIRE_KEY = new RewardType("SAPPHIRE_KEY", 6);
            $VALUES = (new RewardType[] {
                CARD, GOLD, RELIC, POTION, STOLEN_GOLD, EMERALD_KEY, SAPPHIRE_KEY
            });
        }

        private RewardType(String s, int i)
        {
            super(s, i);
        }
    }


    public RewardItem(RewardItem setRelicLink, RewardType type)
    {
        outlineImg = null;
        img = null;
        goldAmt = 0;
        bonusGold = 0;
        effects = new ArrayList();
        hb = new Hitbox(460F * Settings.xScale, 90F * Settings.yScale);
        flashTimer = 0.0F;
        isDone = false;
        ignoreReward = false;
        redText = false;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.type = type;
        if(type == RewardType.SAPPHIRE_KEY)
        {
            text = TEXT[6];
            img = ImageMaster.loadImage("images/relics/sapphire_key.png");
            outlineImg = ImageMaster.loadImage("images/relics/outline/sapphire_key.png");
            relicLink = setRelicLink;
            setRelicLink.relicLink = this;
        } else
        if(type == RewardType.EMERALD_KEY)
        {
            text = TEXT[5];
            img = ImageMaster.loadImage("images/relics/emerald_key.png");
            outlineImg = ImageMaster.loadImage("images/relics/outline/emerald_key.png");
        }
    }

    public RewardItem(int gold)
    {
        outlineImg = null;
        img = null;
        goldAmt = 0;
        bonusGold = 0;
        effects = new ArrayList();
        hb = new Hitbox(460F * Settings.xScale, 90F * Settings.yScale);
        flashTimer = 0.0F;
        isDone = false;
        ignoreReward = false;
        redText = false;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        type = RewardType.GOLD;
        goldAmt = gold;
        applyGoldBonus(false);
    }

    public RewardItem(int gold, boolean theft)
    {
        outlineImg = null;
        img = null;
        goldAmt = 0;
        bonusGold = 0;
        effects = new ArrayList();
        hb = new Hitbox(460F * Settings.xScale, 90F * Settings.yScale);
        flashTimer = 0.0F;
        isDone = false;
        ignoreReward = false;
        redText = false;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        type = RewardType.STOLEN_GOLD;
        goldAmt = gold;
        applyGoldBonus(theft);
    }

    private void applyGoldBonus(boolean theft)
    {
        int tmp = goldAmt;
        bonusGold = 0;
        if(theft)
        {
            text = (new StringBuilder()).append(goldAmt).append(TEXT[0]).toString();
        } else
        {
            if(!(AbstractDungeon.getCurrRoom() instanceof TreasureRoom))
            {
                if(AbstractDungeon.player.hasRelic("Golden Idol"))
                    bonusGold += MathUtils.round((float)tmp * 0.25F);
                if(ModHelper.isModEnabled("Midas"))
                    bonusGold += MathUtils.round((float)tmp * 2.0F);
                if(ModHelper.isModEnabled("MonsterHunter"))
                    bonusGold += MathUtils.round((float)tmp * 1.5F);
            }
            if(bonusGold == 0)
                text = (new StringBuilder()).append(goldAmt).append(TEXT[1]).toString();
            else
                text = (new StringBuilder()).append(goldAmt).append(TEXT[1]).append(" (").append(bonusGold).append(")").toString();
        }
    }

    public RewardItem(AbstractRelic relic)
    {
        outlineImg = null;
        img = null;
        goldAmt = 0;
        bonusGold = 0;
        effects = new ArrayList();
        hb = new Hitbox(460F * Settings.xScale, 90F * Settings.yScale);
        flashTimer = 0.0F;
        isDone = false;
        ignoreReward = false;
        redText = false;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        type = RewardType.RELIC;
        this.relic = relic;
        relic.hb = new Hitbox(80F * Settings.scale, 80F * Settings.scale);
        relic.hb.move(-1000F, -1000F);
        text = relic.name;
    }

    public RewardItem(AbstractPotion potion)
    {
        outlineImg = null;
        img = null;
        goldAmt = 0;
        bonusGold = 0;
        effects = new ArrayList();
        hb = new Hitbox(460F * Settings.xScale, 90F * Settings.yScale);
        flashTimer = 0.0F;
        isDone = false;
        ignoreReward = false;
        redText = false;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        type = RewardType.POTION;
        this.potion = potion;
        text = potion.name;
    }

    public RewardItem()
    {
        outlineImg = null;
        img = null;
        goldAmt = 0;
        bonusGold = 0;
        effects = new ArrayList();
        hb = new Hitbox(460F * Settings.xScale, 90F * Settings.yScale);
        flashTimer = 0.0F;
        isDone = false;
        ignoreReward = false;
        redText = false;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        type = RewardType.CARD;
        isBoss = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
        cards = AbstractDungeon.getRewardCards();
        text = TEXT[2];
    }

    public RewardItem(com.megacrit.cardcrawl.cards.AbstractCard.CardColor colorType)
    {
        outlineImg = null;
        img = null;
        goldAmt = 0;
        bonusGold = 0;
        effects = new ArrayList();
        hb = new Hitbox(460F * Settings.xScale, 90F * Settings.yScale);
        flashTimer = 0.0F;
        isDone = false;
        ignoreReward = false;
        redText = false;
        reticleColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        type = RewardType.CARD;
        isBoss = AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss;
        if(colorType == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS)
            cards = AbstractDungeon.getColorlessRewardCards();
        else
            cards = AbstractDungeon.getRewardCards();
        text = TEXT[2];
        for(Iterator iterator = cards.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            Iterator iterator1 = AbstractDungeon.player.relics.iterator();
            while(iterator1.hasNext()) 
            {
                AbstractRelic r = (AbstractRelic)iterator1.next();
                r.onPreviewObtainCard(c);
            }
        }

    }

    public void incrementGold(int gold)
    {
        if(type == RewardType.GOLD)
        {
            goldAmt += gold;
            applyGoldBonus(false);
        } else
        if(type == RewardType.STOLEN_GOLD)
        {
            goldAmt += gold;
            applyGoldBonus(true);
        } else
        {
            logger.info("ERROR: Using increment() wrong for RewardItem");
        }
    }

    public void move(float y)
    {
        this.y = y;
        hb.move((float)Settings.WIDTH / 2.0F, y);
        if(type == RewardType.POTION)
            potion.move(REWARD_ITEM_X, y);
        else
        if(type == RewardType.RELIC)
        {
            relic.currentX = REWARD_ITEM_X;
            relic.currentY = y;
            relic.targetX = REWARD_ITEM_X;
            relic.targetY = y;
        }
    }

    public void flash()
    {
        flashTimer = 0.5F;
    }

    public void update()
    {
        if(flashTimer > 0.0F)
        {
            flashTimer -= Gdx.graphics.getDeltaTime();
            if(flashTimer < 0.0F)
                flashTimer = 0.0F;
        }
        hb.update();
        updateReticle();
        if(effects.size() == 0)
            effects.add(new RewardGlowEffect(hb.cX, hb.cY));
        Iterator i = effects.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType = new int[RewardType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[RewardType.POTION.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[RewardType.GOLD.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[RewardType.STOLEN_GOLD.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[RewardType.RELIC.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[RewardType.CARD.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[RewardType.SAPPHIRE_KEY.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[RewardType.EMERALD_KEY.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
            }
        }

        if(hb.hovered)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.rewards.RewardItem.RewardType[type.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(!AbstractDungeon.topPanel.potionCombine)
                    TipHelper.queuePowerTips(360F * Settings.scale, InputHelper.mY, potion.tips);
                break;
            }
        if(relicLink != null)
            relicLink.redText = hb.hovered;
        if(hb.justHovered)
            CardCrawlGame.sound.play("UI_HOVER");
        if(hb.hovered && InputHelper.justClickedLeft && !isDone)
        {
            CardCrawlGame.sound.playA("UI_CLICK_1", 0.1F);
            hb.clickStarted = true;
        }
        if(hb.hovered && CInputActionSet.select.isJustPressed() && !isDone)
        {
            hb.clicked = true;
            CardCrawlGame.sound.playA("UI_CLICK_1", 0.1F);
        }
        if(hb.clicked)
        {
            hb.clicked = false;
            isDone = true;
        }
    }

    private void updateReticle()
    {
        if(!Settings.isControllerMode)
            return;
        if(hb.hovered)
        {
            reticleColor.a += Gdx.graphics.getDeltaTime() * 3F;
            if(reticleColor.a > 1.0F)
                reticleColor.a = 1.0F;
        } else
        {
            reticleColor.a = 0.0F;
        }
    }

    public boolean claimReward()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.rewards.RewardItem.RewardType[type.ordinal()])
        {
        case 2: // '\002'
            CardCrawlGame.sound.play("GOLD_GAIN");
            if(bonusGold == 0)
                AbstractDungeon.player.gainGold(goldAmt);
            else
                AbstractDungeon.player.gainGold(goldAmt + bonusGold);
            return true;

        case 3: // '\003'
            CardCrawlGame.sound.play("GOLD_GAIN");
            if(bonusGold == 0)
                AbstractDungeon.player.gainGold(goldAmt);
            else
                AbstractDungeon.player.gainGold(goldAmt + bonusGold);
            return true;

        case 1: // '\001'
            if(AbstractDungeon.player.hasRelic("Sozu"))
            {
                AbstractDungeon.player.getRelic("Sozu").flash();
                return true;
            }
            if(AbstractDungeon.player.obtainPotion(potion))
            {
                if(!((Boolean)TipTracker.tips.get("POTION_TIP")).booleanValue())
                {
                    AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, potion);
                    TipTracker.neverShowAgain("POTION_TIP");
                }
                CardCrawlGame.metricData.addPotionObtainData(potion);
                return true;
            } else
            {
                return false;
            }

        case 4: // '\004'
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.GRID)
                return false;
            if(!ignoreReward)
            {
                relic.instantObtain();
                CardCrawlGame.metricData.addRelicObtainData(relic);
            }
            if(relicLink != null)
            {
                relicLink.isDone = true;
                relicLink.ignoreReward = true;
            }
            return true;

        case 5: // '\005'
            if(AbstractDungeon.player.hasRelic("Question Card"))
                AbstractDungeon.player.getRelic("Question Card").flash();
            if(AbstractDungeon.player.hasRelic("Busted Crown"))
                AbstractDungeon.player.getRelic("Busted Crown").flash();
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD)
            {
                AbstractDungeon.cardRewardScreen.open(cards, this, TEXT[4]);
                AbstractDungeon.previousScreen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD;
            }
            return false;

        case 6: // '\006'
            if(!ignoreReward)
                AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(com.megacrit.cardcrawl.vfx.ObtainKeyEffect.KeyColor.BLUE));
            relicLink.isDone = true;
            relicLink.ignoreReward = true;
            img.dispose();
            outlineImg.dispose();
            return true;

        case 7: // '\007'
            AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(com.megacrit.cardcrawl.vfx.ObtainKeyEffect.KeyColor.GREEN));
            img.dispose();
            outlineImg.dispose();
            return true;
        }
        logger.info("ERROR: Claim Reward() failed");
        return false;
    }

    public void render(SpriteBatch sb)
    {
        if(hb.hovered)
            sb.setColor(new Color(0.4F, 0.6F, 0.6F, 1.0F));
        else
            sb.setColor(new Color(0.5F, 0.6F, 0.6F, 0.8F));
        if(hb.clickStarted)
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, (float)Settings.WIDTH / 2.0F - 232F, y - 49F, 232F, 49F, 464F, 98F, Settings.xScale * 0.98F, Settings.scale * 0.98F, 0.0F, 0, 0, 464, 98, false, false);
        else
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, (float)Settings.WIDTH / 2.0F - 232F, y - 49F, 232F, 49F, 464F, 98F, Settings.xScale, Settings.scale, 0.0F, 0, 0, 464, 98, false, false);
        if(flashTimer != 0.0F)
        {
            sb.setColor(0.6F, 1.0F, 1.0F, flashTimer * 1.5F);
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.REWARD_SCREEN_ITEM, (float)Settings.WIDTH / 2.0F - 232F, y - 49F, 232F, 49F, 464F, 98F, Settings.xScale * 1.03F, Settings.scale * 1.15F, 0.0F, 0, 0, 464, 98, false, false);
            sb.setBlendFunction(770, 771);
        }
        sb.setColor(Color.WHITE);
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.rewards.RewardItem.RewardType[type.ordinal()])
        {
        case 5: // '\005'
            if(isBoss)
                sb.draw(ImageMaster.REWARD_CARD_BOSS, REWARD_ITEM_X - 32F, y - 32F - 2.0F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            else
                sb.draw(ImageMaster.REWARD_CARD_NORMAL, REWARD_ITEM_X - 32F, y - 32F - 2.0F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            break;

        case 2: // '\002'
            sb.draw(ImageMaster.UI_GOLD, REWARD_ITEM_X - 32F, y - 32F - 2.0F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            break;

        case 3: // '\003'
            sb.draw(ImageMaster.UI_GOLD, REWARD_ITEM_X - 32F, y - 32F - 2.0F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            break;

        case 4: // '\004'
            relic.renderWithoutAmount(sb, new Color(0.0F, 0.0F, 0.0F, 0.25F));
            if(hb.hovered)
                if(relicLink != null)
                {
                    ArrayList tips = new ArrayList();
                    tips.add(new PowerTip(relic.name, relic.description));
                    if(relicLink.type == RewardType.SAPPHIRE_KEY)
                        tips.add(new PowerTip(TEXT[7], (new StringBuilder()).append(TEXT[8]).append(FontHelper.colorString((new StringBuilder()).append(TEXT[6]).append(TEXT[9]).toString(), "y")).toString()));
                    TipHelper.queuePowerTips(360F * Settings.scale, (float)InputHelper.mY + 50F * Settings.scale, tips);
                } else
                {
                    relic.renderTip(sb);
                }
            break;

        case 6: // '\006'
            renderKey(sb);
            if(hb.hovered && relicLink != null)
                TipHelper.renderGenericTip(360F * Settings.scale, (float)InputHelper.mY + 50F * Settings.scale, TEXT[7], (new StringBuilder()).append(TEXT[8]).append(FontHelper.colorString((new StringBuilder()).append(relicLink.relic.name).append(TEXT[9]).toString(), "y")).toString());
            break;

        case 7: // '\007'
            renderKey(sb);
            break;

        case 1: // '\001'
            potion.renderLightOutline(sb);
            potion.render(sb);
            potion.generateSparkles(potion.posX, potion.posY, true);
            break;
        }
        Color color;
        if(hb.hovered)
            color = Settings.GOLD_COLOR;
        else
            color = Settings.CREAM_COLOR;
        if(redText)
            color = Settings.RED_TEXT_COLOR;
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, text, REWARD_TEXT_X, y + 5F * Settings.scale, 1000F * Settings.scale, 0.0F, color);
        if(!hb.hovered)
        {
            AbstractGameEffect e;
            for(Iterator iterator = effects.iterator(); iterator.hasNext(); e.render(sb))
                e = (AbstractGameEffect)iterator.next();

        }
        if(Settings.isControllerMode)
            renderReticle(sb, hb);
        if(type == RewardType.SAPPHIRE_KEY)
            renderRelicLink(sb);
        hb.render(sb);
    }

    public void renderReticle(SpriteBatch sb, Hitbox hb)
    {
        float offset = Interpolation.fade.apply(18F * Settings.scale, 12F * Settings.scale, reticleColor.a);
        sb.setColor(reticleColor);
        renderReticleCorner(sb, -hb.width / 2.0F + offset, hb.height / 2.0F - offset, hb, false, false);
        renderReticleCorner(sb, hb.width / 2.0F - offset, hb.height / 2.0F - offset, hb, true, false);
        renderReticleCorner(sb, -hb.width / 2.0F + offset, -hb.height / 2.0F + offset, hb, false, true);
        renderReticleCorner(sb, hb.width / 2.0F - offset, -hb.height / 2.0F + offset, hb, true, true);
    }

    private void renderReticleCorner(SpriteBatch sb, float x, float y, Hitbox hb, boolean flipX, boolean flipY)
    {
        sb.draw(ImageMaster.RETICLE_CORNER, (hb.cX + x) - 18F, (hb.cY + y) - 18F, 18F, 18F, 36F, 36F, Settings.scale, Settings.scale, 0.0F, 0, 0, 36, 36, flipX, flipY);
    }

    private void renderRelicLink(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.RELIC_LINKED, hb.cX - 64F, (y - 64F) + 52F * Settings.scale, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
    }

    private void renderKey(SpriteBatch sb)
    {
        if(img != null && outlineImg != null)
        {
            sb.setColor(AbstractRelic.PASSIVE_OUTLINE_COLOR);
            sb.draw(outlineImg, REWARD_ITEM_X - 64F, y - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
            sb.setColor(Color.WHITE);
            sb.draw(img, REWARD_ITEM_X - 64F, y - 64F, 64F, 64F, 128F, 128F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        }
    }

    public void recordCardSkipMetrics()
    {
        if(cards != null && !cards.isEmpty())
        {
            HashMap choice = new HashMap();
            ArrayList notpicked = new ArrayList();
            AbstractCard card;
            for(Iterator iterator = cards.iterator(); iterator.hasNext(); notpicked.add(card.getMetricID()))
                card = (AbstractCard)iterator.next();

            choice.put("picked", "SKIP");
            choice.put("not_picked", notpicked);
            choice.put("floor", Integer.valueOf(AbstractDungeon.floorNum));
            CardCrawlGame.metricData.card_choices.add(choice);
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/rewards/RewardItem.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    public RewardType type;
    public Texture outlineImg;
    public Texture img;
    public int goldAmt;
    public int bonusGold;
    public String text;
    public RewardItem relicLink;
    public AbstractRelic relic;
    public AbstractPotion potion;
    public ArrayList cards;
    private ArrayList effects;
    private boolean isBoss;
    public Hitbox hb;
    public float y;
    public float flashTimer;
    public boolean isDone;
    public boolean ignoreReward;
    public boolean redText;
    private static final float FLASH_DUR = 0.5F;
    private static final int ITEM_W = 464;
    private static final int ITEM_H = 98;
    public static final float REWARD_ITEM_X;
    private static final float REWARD_TEXT_X;
    private Color reticleColor;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RewardItem");
        TEXT = uiStrings.TEXT;
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Potion Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        REWARD_ITEM_X = (float)Settings.WIDTH * 0.41F;
        REWARD_TEXT_X = (float)Settings.WIDTH * 0.434F;
    }
}
