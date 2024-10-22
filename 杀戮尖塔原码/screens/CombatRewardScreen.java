// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CombatRewardScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.Vintage;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PrayerWheel;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class CombatRewardScreen
{

    public CombatRewardScreen()
    {
        rewards = new ArrayList();
        effects = new ArrayList();
        hasTakenAll = false;
        labelOverride = null;
        mug = false;
        smoke = false;
        rewardAnimTimer = 0.2F;
        tipY = -100F * Settings.scale;
        uiColor = Color.BLACK.cpy();
    }

    public void update()
    {
        if(InputHelper.justClickedLeft && Settings.isDebug)
            tip = CardCrawlGame.tips.getTip();
        rewardViewUpdate();
        updateEffects();
    }

    private void updateEffects()
    {
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
    }

    public void setupItemReward()
    {
        rewardAnimTimer = 0.2F;
        InputHelper.justClickedLeft = false;
        rewards = new ArrayList(AbstractDungeon.getCurrRoom().rewards);
        if((AbstractDungeon.getCurrRoom().event == null || AbstractDungeon.getCurrRoom().event != null && !AbstractDungeon.getCurrRoom().event.noCardsInRewards) && !(AbstractDungeon.getCurrRoom() instanceof TreasureRoom) && !(AbstractDungeon.getCurrRoom() instanceof RestRoom))
            if(ModHelper.isModEnabled("Vintage") && (AbstractDungeon.getCurrRoom() instanceof MonsterRoom))
            {
                if((AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) || (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))
                {
                    RewardItem cardReward = new RewardItem();
                    if(cardReward.cards.size() > 0)
                        rewards.add(cardReward);
                }
            } else
            {
                RewardItem cardReward = new RewardItem();
                if(cardReward.cards.size() > 0)
                    rewards.add(cardReward);
                if((AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && AbstractDungeon.player.hasRelic("Prayer Wheel") && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))
                {
                    cardReward = new RewardItem();
                    if(cardReward.cards.size() > 0)
                        rewards.add(cardReward);
                }
            }
        AbstractDungeon.overlayMenu.proceedButton.show();
        hasTakenAll = false;
        positionRewards();
    }

    public void positionRewards()
    {
        for(int i = 0; i < rewards.size(); i++)
            ((RewardItem)rewards.get(i)).move(((float)Settings.HEIGHT / 2.0F + 124F * Settings.scale) - (float)i * 100F * Settings.scale);

        if(rewards.isEmpty())
            hasTakenAll = true;
    }

    private void rewardViewUpdate()
    {
        if(rewardAnimTimer != 0.0F)
        {
            rewardAnimTimer -= Gdx.graphics.getDeltaTime();
            if(rewardAnimTimer < 0.0F)
                rewardAnimTimer = 0.0F;
            uiColor.r = 1.0F - rewardAnimTimer / 0.2F;
            uiColor.g = 1.0F - rewardAnimTimer / 0.2F;
            uiColor.b = 1.0F - rewardAnimTimer / 0.2F;
        }
        tipY = MathHelper.uiLerpSnap(tipY, (float)Settings.HEIGHT / 2.0F - 460F * Settings.scale);
        updateControllerInput();
        boolean removedSomething = false;
        Iterator i = rewards.iterator();
        do
        {
            if(!i.hasNext())
                break;
            RewardItem r = (RewardItem)i.next();
            r.update();
            if(r.isDone)
                if(r.claimReward())
                {
                    i.remove();
                    removedSomething = true;
                } else
                if(r.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.POTION)
                {
                    r.isDone = false;
                    AbstractDungeon.topPanel.flashRed();
                    tip = CardCrawlGame.tips.getPotionTip();
                } else
                {
                    r.isDone = false;
                }
        } while(true);
        if(removedSomething)
        {
            positionRewards();
            setLabel();
        }
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || rewards.isEmpty() || AbstractDungeon.topPanel.selectPotionMode || !AbstractDungeon.topPanel.potionUi.isHidden || AbstractDungeon.player.viewingRelics)
            return;
        int index = 0;
        boolean anyHovered = false;
        Iterator iterator = rewards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RewardItem r = (RewardItem)iterator.next();
            if(r.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            index = 0;
            Gdx.input.setCursorPosition((int)((RewardItem)rewards.get(index)).hb.cX, Settings.HEIGHT - (int)((RewardItem)rewards.get(index)).hb.cY);
        } else
        if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
        {
            if(--index < 0)
                index = rewards.size() - 1;
            Gdx.input.setCursorPosition((int)((RewardItem)rewards.get(index)).hb.cX, Settings.HEIGHT - (int)((RewardItem)rewards.get(index)).hb.cY);
        } else
        if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
        {
            if(++index > rewards.size() - 1)
                index = 0;
            Gdx.input.setCursorPosition((int)((RewardItem)rewards.get(index)).hb.cX, Settings.HEIGHT - (int)((RewardItem)rewards.get(index)).hb.cY);
        }
    }

    private void setLabel()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType = new int[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.CARD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.GOLD.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.POTION.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$RewardItem$RewardType[com.megacrit.cardcrawl.rewards.RewardItem.RewardType.RELIC.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        if(rewards.size() == 0)
            AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
        else
        if(rewards.size() == 1)
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.rewards.RewardItem.RewardType[((RewardItem)rewards.get(0)).type.ordinal()])
            {
            case 1: // '\001'
                AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[1]);
                break;

            case 2: // '\002'
                AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[2]);
                break;

            case 3: // '\003'
                AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[3]);
                break;

            case 4: // '\004'
                AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[4]);
                break;
            }
        else
            AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[5]);
    }

    public void render(SpriteBatch sb)
    {
        renderItemReward(sb);
        FontHelper.renderFontCentered(sb, FontHelper.panelNameFont, (new StringBuilder()).append(TEXT[12]).append(tip).toString(), (float)Settings.WIDTH / 2.0F, tipY, Color.LIGHT_GRAY);
        AbstractGameEffect e;
        for(Iterator iterator = effects.iterator(); iterator.hasNext(); e.render(sb))
            e = (AbstractGameEffect)iterator.next();

    }

    private void renderItemReward(SpriteBatch sb)
    {
        AbstractDungeon.overlayMenu.proceedButton.render(sb);
        sb.setColor(uiColor);
        sb.draw(ImageMaster.REWARD_SCREEN_SHEET, (float)Settings.WIDTH / 2.0F - 306F, (float)Settings.HEIGHT / 2.0F - 46F * Settings.scale - 358F, 306F, 358F, 612F, 716F, Settings.xScale, Settings.scale, 0.0F, 0, 0, 612, 716, false, false);
        RewardItem i;
        for(Iterator iterator = rewards.iterator(); iterator.hasNext(); i.render(sb))
            i = (RewardItem)iterator.next();

    }

    public void reopen()
    {
        AbstractDungeon.getCurrRoom().rewardTime = true;
        rewardAnimTimer = 0.2F;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.isScreenUp = true;
        if(labelOverride == null || mug || smoke)
        {
            if(mug || smoke)
                AbstractDungeon.dynamicBanner.appear(labelOverride);
            else
                AbstractDungeon.dynamicBanner.appear(getRandomBannerLabel());
            AbstractDungeon.overlayMenu.proceedButton.show();
        } else
        {
            AbstractDungeon.dynamicBanner.appear(labelOverride);
            AbstractDungeon.overlayMenu.cancelButton.show(TEXT[6]);
        }
        setLabel();
    }

    public void open(String setLabel)
    {
        AbstractDungeon.getCurrRoom().rewardTime = true;
        labelOverride = setLabel;
        mug = false;
        smoke = false;
        tip = CardCrawlGame.tips.getTip();
        tipY = (float)Settings.HEIGHT - 1110F * Settings.scale;
        rewardAnimTimer = 0.5F;
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        AbstractDungeon.dynamicBanner.appear(setLabel);
        AbstractDungeon.overlayMenu.showBlackScreen();
        setupItemReward();
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.cancelButton.show(TEXT[6]);
        Iterator iterator = rewards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RewardItem r = (RewardItem)iterator.next();
            if(r.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.RELIC)
                UnlockTracker.markRelicAsSeen(r.relic.relicId);
        } while(true);
    }

    public void openCombat(String setLabel)
    {
        openCombat(setLabel, false);
    }

    public void openCombat(String setLabel, boolean smoked)
    {
        AbstractDungeon.getCurrRoom().rewardTime = true;
        labelOverride = setLabel;
        mug = !smoked;
        smoke = smoked;
        tip = CardCrawlGame.tips.getTip();
        tipY = (float)Settings.HEIGHT - 1110F * Settings.scale;
        rewardAnimTimer = 0.5F;
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        AbstractDungeon.dynamicBanner.appear(setLabel);
        AbstractDungeon.overlayMenu.showBlackScreen();
        if(!smoke)
        {
            setupItemReward();
            Iterator iterator = rewards.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                RewardItem r = (RewardItem)iterator.next();
                if(r.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.RELIC)
                    UnlockTracker.markRelicAsSeen(r.relic.relicId);
            } while(true);
        } else
        {
            AbstractDungeon.overlayMenu.proceedButton.show();
        }
        setLabel();
    }

    public void open()
    {
        AbstractDungeon.getCurrRoom().rewardTime = true;
        tip = CardCrawlGame.tips.getTip();
        mug = false;
        smoke = false;
        tipY = (float)Settings.HEIGHT - 1110F * Settings.scale;
        rewardAnimTimer = 0.5F;
        AbstractDungeon.topPanel.unhoverHitboxes();
        AbstractDungeon.isScreenUp = true;
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        AbstractDungeon.dynamicBanner.appear(getRandomBannerLabel());
        labelOverride = null;
        AbstractDungeon.overlayMenu.proceedButton.hide();
        AbstractDungeon.overlayMenu.showBlackScreen();
        setupItemReward();
        setLabel();
        Iterator iterator = rewards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            RewardItem r = (RewardItem)iterator.next();
            if(r.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.RELIC)
                UnlockTracker.markRelicAsSeen(r.relic.relicId);
        } while(true);
    }

    private String getRandomBannerLabel()
    {
        ArrayList list = new ArrayList();
        if(AbstractDungeon.getCurrRoom() instanceof TreasureRoom)
        {
            list.add(TEXT[7]);
            list.add(TEXT[8]);
        } else
        {
            list.add(TEXT[9]);
            list.add(TEXT[10]);
            list.add(TEXT[11]);
        }
        return (String)list.get(MathUtils.random(list.size() - 1));
    }

    public void clear()
    {
        rewards.clear();
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public ArrayList rewards;
    public ArrayList effects;
    public boolean hasTakenAll;
    private String labelOverride;
    private boolean mug;
    private boolean smoke;
    private static final float REWARD_ANIM_TIME = 0.2F;
    private float rewardAnimTimer;
    private float tipY;
    private Color uiColor;
    private static final int SHEET_W = 612;
    private static final int SHEET_H = 716;
    private String tip;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CombatRewardScreen");
        TEXT = uiStrings.TEXT;
    }
}
