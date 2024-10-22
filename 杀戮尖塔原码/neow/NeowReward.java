// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NeowReward.java

package com.megacrit.cardcrawl.neow;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.helpers.SaveHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.NeowsLament;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.neow:
//            NeowEvent

public class NeowReward
{
    public static final class NeowRewardDrawback extends Enum
    {

        public static NeowRewardDrawback[] values()
        {
            return (NeowRewardDrawback[])$VALUES.clone();
        }

        public static NeowRewardDrawback valueOf(String name)
        {
            return (NeowRewardDrawback)Enum.valueOf(com/megacrit/cardcrawl/neow/NeowReward$NeowRewardDrawback, name);
        }

        public static final NeowRewardDrawback NONE;
        public static final NeowRewardDrawback TEN_PERCENT_HP_LOSS;
        public static final NeowRewardDrawback NO_GOLD;
        public static final NeowRewardDrawback CURSE;
        public static final NeowRewardDrawback PERCENT_DAMAGE;
        private static final NeowRewardDrawback $VALUES[];

        static 
        {
            NONE = new NeowRewardDrawback("NONE", 0);
            TEN_PERCENT_HP_LOSS = new NeowRewardDrawback("TEN_PERCENT_HP_LOSS", 1);
            NO_GOLD = new NeowRewardDrawback("NO_GOLD", 2);
            CURSE = new NeowRewardDrawback("CURSE", 3);
            PERCENT_DAMAGE = new NeowRewardDrawback("PERCENT_DAMAGE", 4);
            $VALUES = (new NeowRewardDrawback[] {
                NONE, TEN_PERCENT_HP_LOSS, NO_GOLD, CURSE, PERCENT_DAMAGE
            });
        }

        private NeowRewardDrawback(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class NeowRewardType extends Enum
    {

        public static NeowRewardType[] values()
        {
            return (NeowRewardType[])$VALUES.clone();
        }

        public static NeowRewardType valueOf(String name)
        {
            return (NeowRewardType)Enum.valueOf(com/megacrit/cardcrawl/neow/NeowReward$NeowRewardType, name);
        }

        public static final NeowRewardType RANDOM_COLORLESS_2;
        public static final NeowRewardType THREE_CARDS;
        public static final NeowRewardType ONE_RANDOM_RARE_CARD;
        public static final NeowRewardType REMOVE_CARD;
        public static final NeowRewardType UPGRADE_CARD;
        public static final NeowRewardType RANDOM_COLORLESS;
        public static final NeowRewardType TRANSFORM_CARD;
        public static final NeowRewardType THREE_SMALL_POTIONS;
        public static final NeowRewardType RANDOM_COMMON_RELIC;
        public static final NeowRewardType TEN_PERCENT_HP_BONUS;
        public static final NeowRewardType HUNDRED_GOLD;
        public static final NeowRewardType THREE_ENEMY_KILL;
        public static final NeowRewardType REMOVE_TWO;
        public static final NeowRewardType TRANSFORM_TWO_CARDS;
        public static final NeowRewardType ONE_RARE_RELIC;
        public static final NeowRewardType THREE_RARE_CARDS;
        public static final NeowRewardType TWO_FIFTY_GOLD;
        public static final NeowRewardType TWENTY_PERCENT_HP_BONUS;
        public static final NeowRewardType BOSS_RELIC;
        private static final NeowRewardType $VALUES[];

        static 
        {
            RANDOM_COLORLESS_2 = new NeowRewardType("RANDOM_COLORLESS_2", 0);
            THREE_CARDS = new NeowRewardType("THREE_CARDS", 1);
            ONE_RANDOM_RARE_CARD = new NeowRewardType("ONE_RANDOM_RARE_CARD", 2);
            REMOVE_CARD = new NeowRewardType("REMOVE_CARD", 3);
            UPGRADE_CARD = new NeowRewardType("UPGRADE_CARD", 4);
            RANDOM_COLORLESS = new NeowRewardType("RANDOM_COLORLESS", 5);
            TRANSFORM_CARD = new NeowRewardType("TRANSFORM_CARD", 6);
            THREE_SMALL_POTIONS = new NeowRewardType("THREE_SMALL_POTIONS", 7);
            RANDOM_COMMON_RELIC = new NeowRewardType("RANDOM_COMMON_RELIC", 8);
            TEN_PERCENT_HP_BONUS = new NeowRewardType("TEN_PERCENT_HP_BONUS", 9);
            HUNDRED_GOLD = new NeowRewardType("HUNDRED_GOLD", 10);
            THREE_ENEMY_KILL = new NeowRewardType("THREE_ENEMY_KILL", 11);
            REMOVE_TWO = new NeowRewardType("REMOVE_TWO", 12);
            TRANSFORM_TWO_CARDS = new NeowRewardType("TRANSFORM_TWO_CARDS", 13);
            ONE_RARE_RELIC = new NeowRewardType("ONE_RARE_RELIC", 14);
            THREE_RARE_CARDS = new NeowRewardType("THREE_RARE_CARDS", 15);
            TWO_FIFTY_GOLD = new NeowRewardType("TWO_FIFTY_GOLD", 16);
            TWENTY_PERCENT_HP_BONUS = new NeowRewardType("TWENTY_PERCENT_HP_BONUS", 17);
            BOSS_RELIC = new NeowRewardType("BOSS_RELIC", 18);
            $VALUES = (new NeowRewardType[] {
                RANDOM_COLORLESS_2, THREE_CARDS, ONE_RANDOM_RARE_CARD, REMOVE_CARD, UPGRADE_CARD, RANDOM_COLORLESS, TRANSFORM_CARD, THREE_SMALL_POTIONS, RANDOM_COMMON_RELIC, TEN_PERCENT_HP_BONUS, 
                HUNDRED_GOLD, THREE_ENEMY_KILL, REMOVE_TWO, TRANSFORM_TWO_CARDS, ONE_RARE_RELIC, THREE_RARE_CARDS, TWO_FIFTY_GOLD, TWENTY_PERCENT_HP_BONUS, BOSS_RELIC
            });
        }

        private NeowRewardType(String s, int i)
        {
            super(s, i);
        }
    }

    public static class NeowRewardDrawbackDef
    {

        public NeowRewardDrawback type;
        public String desc;

        public NeowRewardDrawbackDef(NeowRewardDrawback type, String desc)
        {
            this.type = type;
            this.desc = desc;
        }
    }

    public static class NeowRewardDef
    {

        public NeowRewardType type;
        public String desc;

        public NeowRewardDef(NeowRewardType type, String desc)
        {
            this.type = type;
            this.desc = desc;
        }
    }


    public NeowReward(boolean firstMini)
    {
        optionLabel = "";
        drawback = NeowRewardDrawback.NONE;
        activated = false;
        hp_bonus = 0;
        cursed = false;
        hp_bonus = (int)((float)AbstractDungeon.player.maxHealth * 0.1F);
        NeowRewardDef reward;
        if(firstMini)
            reward = new NeowRewardDef(NeowRewardType.THREE_ENEMY_KILL, TEXT[28]);
        else
            reward = new NeowRewardDef(NeowRewardType.TEN_PERCENT_HP_BONUS, (new StringBuilder()).append(TEXT[7]).append(hp_bonus).append(" ]").toString());
        optionLabel = (new StringBuilder()).append(optionLabel).append(reward.desc).toString();
        type = reward.type;
    }

    public NeowReward(int category)
    {
        optionLabel = "";
        drawback = NeowRewardDrawback.NONE;
        activated = false;
        hp_bonus = 0;
        cursed = false;
        hp_bonus = (int)((float)AbstractDungeon.player.maxHealth * 0.1F);
        ArrayList possibleRewards = getRewardOptions(category);
        NeowRewardDef reward = (NeowRewardDef)possibleRewards.get(NeowEvent.rng.random(0, possibleRewards.size() - 1));
        if(drawback != NeowRewardDrawback.NONE && drawbackDef != null)
            optionLabel = (new StringBuilder()).append(optionLabel).append(drawbackDef.desc).toString();
        optionLabel = (new StringBuilder()).append(optionLabel).append(reward.desc).toString();
        type = reward.type;
    }

    private ArrayList getRewardDrawbackOptions()
    {
        ArrayList drawbackOptions = new ArrayList();
        drawbackOptions.add(new NeowRewardDrawbackDef(NeowRewardDrawback.TEN_PERCENT_HP_LOSS, (new StringBuilder()).append(TEXT[17]).append(hp_bonus).append(TEXT[18]).toString()));
        drawbackOptions.add(new NeowRewardDrawbackDef(NeowRewardDrawback.NO_GOLD, TEXT[19]));
        drawbackOptions.add(new NeowRewardDrawbackDef(NeowRewardDrawback.CURSE, TEXT[20]));
        drawbackOptions.add(new NeowRewardDrawbackDef(NeowRewardDrawback.PERCENT_DAMAGE, (new StringBuilder()).append(TEXT[21]).append((AbstractDungeon.player.currentHealth / 10) * 3).append(TEXT[29]).append(" ").toString()));
        return drawbackOptions;
    }

    private ArrayList getRewardOptions(int category)
    {
        ArrayList rewardOptions = new ArrayList();
        switch(category)
        {
        default:
            break;

        case 0: // '\0'
            rewardOptions.add(new NeowRewardDef(NeowRewardType.THREE_CARDS, TEXT[0]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.ONE_RANDOM_RARE_CARD, TEXT[1]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.REMOVE_CARD, TEXT[2]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.UPGRADE_CARD, TEXT[3]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.TRANSFORM_CARD, TEXT[4]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.RANDOM_COLORLESS, TEXT[30]));
            break;

        case 1: // '\001'
            rewardOptions.add(new NeowRewardDef(NeowRewardType.THREE_SMALL_POTIONS, TEXT[5]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.RANDOM_COMMON_RELIC, TEXT[6]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.TEN_PERCENT_HP_BONUS, (new StringBuilder()).append(TEXT[7]).append(hp_bonus).append(" ]").toString()));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.THREE_ENEMY_KILL, TEXT[28]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.HUNDRED_GOLD, (new StringBuilder()).append(TEXT[8]).append(100).append(TEXT[9]).toString()));
            break;

        case 2: // '\002'
            ArrayList drawbackOptions = getRewardDrawbackOptions();
            drawbackDef = (NeowRewardDrawbackDef)drawbackOptions.get(NeowEvent.rng.random(0, drawbackOptions.size() - 1));
            drawback = drawbackDef.type;
            rewardOptions.add(new NeowRewardDef(NeowRewardType.RANDOM_COLORLESS_2, TEXT[31]));
            if(drawback != NeowRewardDrawback.CURSE)
                rewardOptions.add(new NeowRewardDef(NeowRewardType.REMOVE_TWO, TEXT[10]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.ONE_RARE_RELIC, TEXT[11]));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.THREE_RARE_CARDS, TEXT[12]));
            if(drawback != NeowRewardDrawback.NO_GOLD)
                rewardOptions.add(new NeowRewardDef(NeowRewardType.TWO_FIFTY_GOLD, (new StringBuilder()).append(TEXT[13]).append(250).append(TEXT[14]).toString()));
            rewardOptions.add(new NeowRewardDef(NeowRewardType.TRANSFORM_TWO_CARDS, TEXT[15]));
            if(drawback != NeowRewardDrawback.TEN_PERCENT_HP_LOSS)
                rewardOptions.add(new NeowRewardDef(NeowRewardType.TWENTY_PERCENT_HP_BONUS, (new StringBuilder()).append(TEXT[16]).append(hp_bonus * 2).append(" ]").toString()));
            break;

        case 3: // '\003'
            rewardOptions.add(new NeowRewardDef(NeowRewardType.BOSS_RELIC, UNIQUE_REWARDS[0]));
            break;
        }
        return rewardOptions;
    }

    public void update()
    {
        if(activated)
        {
            if(!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
            {
                static class _cls1
                {

                    static final int $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[];
                    static final int $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardDrawback[];
                    static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[];

                    static 
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.values().length];
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError nosuchfielderror) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError nosuchfielderror1) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError nosuchfielderror2) { }
                        $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardDrawback = new int[NeowRewardDrawback.values().length];
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardDrawback[NeowRewardDrawback.CURSE.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError nosuchfielderror3) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardDrawback[NeowRewardDrawback.NO_GOLD.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError nosuchfielderror4) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardDrawback[NeowRewardDrawback.TEN_PERCENT_HP_LOSS.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError nosuchfielderror5) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardDrawback[NeowRewardDrawback.PERCENT_DAMAGE.ordinal()] = 4;
                        }
                        catch(NoSuchFieldError nosuchfielderror6) { }
                        $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType = new int[NeowRewardType.values().length];
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.UPGRADE_CARD.ordinal()] = 1;
                        }
                        catch(NoSuchFieldError nosuchfielderror7) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.REMOVE_CARD.ordinal()] = 2;
                        }
                        catch(NoSuchFieldError nosuchfielderror8) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.REMOVE_TWO.ordinal()] = 3;
                        }
                        catch(NoSuchFieldError nosuchfielderror9) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.TRANSFORM_CARD.ordinal()] = 4;
                        }
                        catch(NoSuchFieldError nosuchfielderror10) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.TRANSFORM_TWO_CARDS.ordinal()] = 5;
                        }
                        catch(NoSuchFieldError nosuchfielderror11) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.RANDOM_COLORLESS_2.ordinal()] = 6;
                        }
                        catch(NoSuchFieldError nosuchfielderror12) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.RANDOM_COLORLESS.ordinal()] = 7;
                        }
                        catch(NoSuchFieldError nosuchfielderror13) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.THREE_RARE_CARDS.ordinal()] = 8;
                        }
                        catch(NoSuchFieldError nosuchfielderror14) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.HUNDRED_GOLD.ordinal()] = 9;
                        }
                        catch(NoSuchFieldError nosuchfielderror15) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.ONE_RANDOM_RARE_CARD.ordinal()] = 10;
                        }
                        catch(NoSuchFieldError nosuchfielderror16) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.RANDOM_COMMON_RELIC.ordinal()] = 11;
                        }
                        catch(NoSuchFieldError nosuchfielderror17) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.ONE_RARE_RELIC.ordinal()] = 12;
                        }
                        catch(NoSuchFieldError nosuchfielderror18) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.BOSS_RELIC.ordinal()] = 13;
                        }
                        catch(NoSuchFieldError nosuchfielderror19) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.THREE_ENEMY_KILL.ordinal()] = 14;
                        }
                        catch(NoSuchFieldError nosuchfielderror20) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.TEN_PERCENT_HP_BONUS.ordinal()] = 15;
                        }
                        catch(NoSuchFieldError nosuchfielderror21) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.THREE_CARDS.ordinal()] = 16;
                        }
                        catch(NoSuchFieldError nosuchfielderror22) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.THREE_SMALL_POTIONS.ordinal()] = 17;
                        }
                        catch(NoSuchFieldError nosuchfielderror23) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.TWENTY_PERCENT_HP_BONUS.ordinal()] = 18;
                        }
                        catch(NoSuchFieldError nosuchfielderror24) { }
                        try
                        {
                            $SwitchMap$com$megacrit$cardcrawl$neow$NeowReward$NeowRewardType[NeowRewardType.TWO_FIFTY_GOLD.ordinal()] = 19;
                        }
                        catch(NoSuchFieldError nosuchfielderror25) { }
                    }
                }

                switch(_cls1..SwitchMap.com.megacrit.cardcrawl.neow.NeowReward.NeowRewardType[type.ordinal()])
                {
                case 1: // '\001'
                    AbstractCard c = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    c.upgrade();
                    AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy()));
                    AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    break;

                case 2: // '\002'
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), Settings.WIDTH / 2, Settings.HEIGHT / 2));
                    AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    break;

                case 3: // '\003'
                    CardCrawlGame.sound.play("CARD_EXHAUST");
                    AbstractCard c2 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractCard c3 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c2, (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 30F * Settings.scale, Settings.HEIGHT / 2));
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c3, (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 30F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.removeCard(c2);
                    AbstractDungeon.player.masterDeck.removeCard(c3);
                    break;

                case 4: // '\004'
                    AbstractDungeon.transformCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0), false, NeowEvent.rng);
                    AbstractDungeon.player.masterDeck.removeCard((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0));
                    AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    break;

                case 5: // '\005'
                    AbstractCard t1 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
                    AbstractCard t2 = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(1);
                    AbstractDungeon.player.masterDeck.removeCard(t1);
                    AbstractDungeon.player.masterDeck.removeCard(t2);
                    AbstractDungeon.transformCard(t1, false, NeowEvent.rng);
                    AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 30F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.transformCard(t2, false, NeowEvent.rng);
                    AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 30F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                    break;

                default:
                    logger.info((new StringBuilder()).append("[ERROR] Missing Neow Reward Type: ").append(type.name()).toString());
                    break;
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                SaveHelper.saveIfAppropriate(com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType.POST_NEOW);
                activated = false;
            }
            if(cursed)
            {
                cursed = !cursed;
                AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getCardWithoutRng(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }
    }

    public void activate()
    {
        activated = true;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.neow.NeowReward.NeowRewardDrawback[drawback.ordinal()])
        {
        case 1: // '\001'
            cursed = true;
            break;

        case 2: // '\002'
            AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
            break;

        case 3: // '\003'
            AbstractDungeon.player.decreaseMaxHealth(hp_bonus);
            break;

        case 4: // '\004'
            AbstractDungeon.player.damage(new DamageInfo(null, (AbstractDungeon.player.currentHealth / 10) * 3, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
            break;

        default:
            logger.info((new StringBuilder()).append("[ERROR] Missing Neow Reward Drawback: ").append(drawback.name()).toString());
            break;
        }
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.neow.NeowReward.NeowRewardType[type.ordinal()])
        {
        default:
            break;

        case 6: // '\006'
            AbstractDungeon.cardRewardScreen.open(getColorlessRewardCards(true), null, CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
            break;

        case 7: // '\007'
            AbstractDungeon.cardRewardScreen.open(getColorlessRewardCards(false), null, CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
            break;

        case 8: // '\b'
            AbstractDungeon.cardRewardScreen.open(getRewardCards(true), null, TEXT[22]);
            break;

        case 9: // '\t'
            CardCrawlGame.sound.play("GOLD_JINGLE");
            AbstractDungeon.player.gainGold(100);
            break;

        case 10: // '\n'
            AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(AbstractDungeon.getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, NeowEvent.rng).makeCopy(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            break;

        case 11: // '\013'
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, AbstractDungeon.returnRandomRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON));
            break;

        case 12: // '\f'
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, AbstractDungeon.returnRandomRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE));
            break;

        case 13: // '\r'
            AbstractDungeon.player.loseRelic(((AbstractRelic)AbstractDungeon.player.relics.get(0)).relicId);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, AbstractDungeon.returnRandomRelic(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS));
            break;

        case 14: // '\016'
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, new NeowsLament());
            break;

        case 2: // '\002'
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, TEXT[23], false, false, false, true);
            break;

        case 3: // '\003'
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, TEXT[24], false, false, false, false);
            break;

        case 15: // '\017'
            AbstractDungeon.player.increaseMaxHp(hp_bonus, true);
            break;

        case 16: // '\020'
            AbstractDungeon.cardRewardScreen.open(getRewardCards(false), null, CardCrawlGame.languagePack.getUIString("CardRewardScreen").TEXT[1]);
            break;

        case 17: // '\021'
            CardCrawlGame.sound.play("POTION_1");
            for(int i = 0; i < 3; i++)
                AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion());

            AbstractDungeon.combatRewardScreen.open();
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
            int remove = -1;
            int j = 0;
            do
            {
                if(j >= AbstractDungeon.combatRewardScreen.rewards.size())
                    break;
                if(((RewardItem)AbstractDungeon.combatRewardScreen.rewards.get(j)).type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.CARD)
                {
                    remove = j;
                    break;
                }
                j++;
            } while(true);
            if(remove != -1)
                AbstractDungeon.combatRewardScreen.rewards.remove(remove);
            break;

        case 4: // '\004'
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 1, TEXT[25], false, true, false, false);
            break;

        case 5: // '\005'
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, TEXT[26], false, false, false, false);
            break;

        case 18: // '\022'
            AbstractDungeon.player.increaseMaxHp(hp_bonus * 2, true);
            break;

        case 19: // '\023'
            CardCrawlGame.sound.play("GOLD_JINGLE");
            AbstractDungeon.player.gainGold(250);
            break;

        case 1: // '\001'
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getUpgradableCards(), 1, TEXT[27], true, false, false, false);
            break;
        }
        CardCrawlGame.metricData.addNeowData(type.name(), drawback.name());
    }

    public ArrayList getColorlessRewardCards(boolean rareOnly)
    {
        ArrayList retVal = new ArrayList();
        int numCards = 3;
        for(int i = 0; i < numCards; i++)
        {
            com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity = rollRarity();
            if(rareOnly)
                rarity = com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
            else
            if(rarity == com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON)
                rarity = com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON;
            AbstractCard card;
            for(card = AbstractDungeon.getColorlessCardFromPool(rarity); retVal.contains(card); card = AbstractDungeon.getColorlessCardFromPool(rarity));
            retVal.add(card);
        }

        ArrayList retVal2 = new ArrayList();
        AbstractCard c;
        for(Iterator iterator = retVal.iterator(); iterator.hasNext(); retVal2.add(c.makeCopy()))
            c = (AbstractCard)iterator.next();

        return retVal2;
    }

    public ArrayList getRewardCards(boolean rareOnly)
    {
        ArrayList retVal = new ArrayList();
        int numCards = 3;
        for(int i = 0; i < numCards; i++)
        {
            com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity = rollRarity();
            if(rareOnly)
                rarity = com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
            AbstractCard card = null;
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
            {
            case 1: // '\001'
                card = getCard(rarity);
                break;

            case 2: // '\002'
                card = getCard(rarity);
                break;

            case 3: // '\003'
                card = getCard(rarity);
                break;

            default:
                logger.info("WTF?");
                break;
            }
            for(; retVal.contains(card); card = getCard(rarity));
            retVal.add(card);
        }

        ArrayList retVal2 = new ArrayList();
        AbstractCard c;
        for(Iterator iterator = retVal.iterator(); iterator.hasNext(); retVal2.add(c.makeCopy()))
            c = (AbstractCard)iterator.next();

        return retVal2;
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rollRarity()
    {
        if(NeowEvent.rng.randomBoolean(0.33F))
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON;
        else
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON;
    }

    public AbstractCard getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 1: // '\001'
            return AbstractDungeon.rareCardPool.getRandomCard(NeowEvent.rng);

        case 2: // '\002'
            return AbstractDungeon.uncommonCardPool.getRandomCard(NeowEvent.rng);

        case 3: // '\003'
            return AbstractDungeon.commonCardPool.getRandomCard(NeowEvent.rng);
        }
        logger.info("Error in getCard in Neow Reward");
        return null;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/neow/NeowReward.getName());
    private static final CharacterStrings characterStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    public static final String UNIQUE_REWARDS[];
    public String optionLabel;
    public NeowRewardType type;
    public NeowRewardDrawback drawback;
    private boolean activated;
    private int hp_bonus;
    private boolean cursed;
    private static final int GOLD_BONUS = 100;
    private static final int LARGE_GOLD_BONUS = 250;
    private NeowRewardDrawbackDef drawbackDef;

    static 
    {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Neow Reward");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
        UNIQUE_REWARDS = characterStrings.UNIQUE_REWARDS;
    }
}
