// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractChest.java

package com.megacrit.cardcrawl.rewards.chests;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractChest
{
    public static final class RelicReward extends Enum
    {

        public static RelicReward[] values()
        {
            return (RelicReward[])$VALUES.clone();
        }

        public static RelicReward valueOf(String name)
        {
            return (RelicReward)Enum.valueOf(com/megacrit/cardcrawl/rewards/chests/AbstractChest$RelicReward, name);
        }

        public static final RelicReward COMMON_RELIC;
        public static final RelicReward UNCOMMON_RELIC;
        public static final RelicReward RARE_RELIC;
        private static final RelicReward $VALUES[];

        static 
        {
            COMMON_RELIC = new RelicReward("COMMON_RELIC", 0);
            UNCOMMON_RELIC = new RelicReward("UNCOMMON_RELIC", 1);
            RARE_RELIC = new RelicReward("RARE_RELIC", 2);
            $VALUES = (new RelicReward[] {
                COMMON_RELIC, UNCOMMON_RELIC, RARE_RELIC
            });
        }

        private RelicReward(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractChest()
    {
        isOpen = false;
        goldReward = false;
        cursed = false;
    }

    protected boolean keyRequirement()
    {
        isOpen = true;
        return true;
    }

    public void randomizeReward()
    {
        int roll = AbstractDungeon.treasureRng.random(0, 99);
        if(roll < GOLD_CHANCE)
            goldReward = true;
        if(roll < COMMON_CHANCE)
            relicReward = RelicReward.COMMON_RELIC;
        else
        if(roll < UNCOMMON_CHANCE + COMMON_CHANCE)
            relicReward = RelicReward.UNCOMMON_RELIC;
        else
            relicReward = RelicReward.RARE_RELIC;
    }

    public void open(boolean bossChest)
    {
        AbstractDungeon.overlayMenu.proceedButton.setLabel(TEXT[0]);
        AbstractRelic r;
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onChestOpen(bossChest))
            r = (AbstractRelic)iterator.next();

        CardCrawlGame.sound.play("CHEST_OPEN");
        if(goldReward)
            if(Settings.isDailyRun)
                AbstractDungeon.getCurrRoom().addGoldToRewards(GOLD_AMT);
            else
                AbstractDungeon.getCurrRoom().addGoldToRewards(Math.round(AbstractDungeon.treasureRng.random((float)GOLD_AMT * 0.9F, (float)GOLD_AMT * 1.1F)));
        if(cursed)
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.returnRandomCurse(), hb.cX, hb.cY));
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$rewards$chests$AbstractChest$RelicReward[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$rewards$chests$AbstractChest$RelicReward = new int[RelicReward.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$chests$AbstractChest$RelicReward[RelicReward.COMMON_RELIC.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$chests$AbstractChest$RelicReward[RelicReward.UNCOMMON_RELIC.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rewards$chests$AbstractChest$RelicReward[RelicReward.RARE_RELIC.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.rewards.chests.AbstractChest.RelicReward[relicReward.ordinal()])
        {
        case 1: // '\001'
            AbstractDungeon.getCurrRoom().addRelicToRewards(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON);
            break;

        case 2: // '\002'
            AbstractDungeon.getCurrRoom().addRelicToRewards(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON);
            break;

        case 3: // '\003'
            AbstractDungeon.getCurrRoom().addRelicToRewards(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE);
            break;

        default:
            logger.info((new StringBuilder()).append("ERROR: Unspecified reward: ").append(relicReward.name()).toString());
            break;
        }
        if(Settings.isFinalActAvailable && !Settings.hasSapphireKey)
            AbstractDungeon.getCurrRoom().addSapphireKey((RewardItem)AbstractDungeon.getCurrRoom().rewards.get(AbstractDungeon.getCurrRoom().rewards.size() - 1));
        AbstractRelic r;
        for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext(); r.onChestOpenAfter(bossChest))
            r = (AbstractRelic)iterator1.next();

        AbstractDungeon.combatRewardScreen.open();
    }

    public void update()
    {
        hb.update();
        if((hb.hovered && InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) && !AbstractDungeon.isScreenUp && !isOpen && keyRequirement())
        {
            InputHelper.justClickedLeft = false;
            open(false);
        }
    }

    public void close()
    {
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        float angle = 0.0F;
        if(isOpen && openedImg == null)
            angle = 180F;
        float xxx = (float)Settings.WIDTH / 2.0F + 348F * Settings.scale;
        if(!isOpen || angle == 180F)
        {
            sb.draw(img, CHEST_LOC_X - 256F, CHEST_LOC_Y - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, angle, 0, 0, 512, 512, false, false);
            if(hb.hovered)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
                sb.draw(img, CHEST_LOC_X - 256F, CHEST_LOC_Y - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, angle, 0, 0, 512, 512, false, false);
                sb.setBlendFunction(770, 771);
            }
        } else
        {
            sb.draw(openedImg, xxx - 256F, CHEST_LOC_Y - 256F, 256F, 256F, 512F, 512F, Settings.scale, Settings.scale, angle, 0, 0, 512, 512, false, false);
        }
        if(Settings.isControllerMode && !isOpen)
        {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.select.getKeyImg(), CHEST_LOC_X - 32F - 150F * Settings.scale, CHEST_LOC_Y - 32F - 210F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        hb.render(sb);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/rewards/chests/AbstractChest.getName());
    public static final float CHEST_LOC_X;
    public static final float CHEST_LOC_Y;
    private static final int RAW_W = 512;
    protected Hitbox hb;
    protected Texture img;
    protected Texture openedImg;
    public boolean isOpen;
    public int COMMON_CHANCE;
    public int UNCOMMON_CHANCE;
    public int RARE_CHANCE;
    public int GOLD_CHANCE;
    public int GOLD_AMT;
    public RelicReward relicReward;
    public boolean goldReward;
    public boolean cursed;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractChest");
        TEXT = uiStrings.TEXT;
        CHEST_LOC_X = (float)Settings.WIDTH / 2.0F + 348F * Settings.scale;
        CHEST_LOC_Y = AbstractDungeon.floorY + 192F * Settings.scale;
    }
}
