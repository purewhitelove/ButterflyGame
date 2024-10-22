// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KnowingSkull.java

package com.megacrit.cardcrawl.events.city;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Sozu;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KnowingSkull extends AbstractImageEvent
{
    private static final class Reward extends Enum
    {

        public static Reward[] values()
        {
            return (Reward[])$VALUES.clone();
        }

        public static Reward valueOf(String name)
        {
            return (Reward)Enum.valueOf(com/megacrit/cardcrawl/events/city/KnowingSkull$Reward, name);
        }

        public static final Reward POTION;
        public static final Reward LEAVE;
        public static final Reward GOLD;
        public static final Reward CARD;
        private static final Reward $VALUES[];

        static 
        {
            POTION = new Reward("POTION", 0);
            LEAVE = new Reward("LEAVE", 1);
            GOLD = new Reward("GOLD", 2);
            CARD = new Reward("CARD", 3);
            $VALUES = (new Reward[] {
                POTION, LEAVE, GOLD, CARD
            });
        }

        private Reward(String s, int i)
        {
            super(s, i);
        }
    }

    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/city/KnowingSkull$CurScreen, name);
        }

        public static final CurScreen INTRO_1;
        public static final CurScreen ASK;
        public static final CurScreen COMPLETE;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO_1 = new CurScreen("INTRO_1", 0);
            ASK = new CurScreen("ASK", 1);
            COMPLETE = new CurScreen("COMPLETE", 2);
            $VALUES = (new CurScreen[] {
                INTRO_1, ASK, COMPLETE
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public KnowingSkull()
    {
        super(NAME, INTRO_MSG, "images/events/knowingSkull.jpg");
        screen = CurScreen.INTRO_1;
        optionsChosen = "";
        options = new ArrayList();
        imageEventText.setDialogOption(OPTIONS[0]);
        options.add(Reward.CARD);
        options.add(Reward.GOLD);
        options.add(Reward.POTION);
        options.add(Reward.LEAVE);
        leaveCost = 6;
        cardCost = leaveCost;
        potionCost = leaveCost;
        goldCost = leaveCost;
        damageTaken = 0;
        goldEarned = 0;
        cards = new ArrayList();
        potions = new ArrayList();
    }

    public void onEnterRoom()
    {
        if(Settings.AMBIANCE_ON)
            CardCrawlGame.sound.play("EVENT_SKULL");
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$city$KnowingSkull$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$city$KnowingSkull$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$KnowingSkull$CurScreen[CurScreen.INTRO_1.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$KnowingSkull$CurScreen[CurScreen.ASK.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$city$KnowingSkull$CurScreen[CurScreen.COMPLETE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.city.KnowingSkull.CurScreen[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            imageEventText.updateBodyText(INTRO_2_MSG);
            imageEventText.clearAllDialogs();
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[4]).append(potionCost).append(OPTIONS[1]).toString());
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[5]).append(90).append(OPTIONS[6]).append(goldCost).append(OPTIONS[1]).toString());
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(cardCost).append(OPTIONS[1]).toString());
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[7]).append(leaveCost).append(OPTIONS[1]).toString());
            screen = CurScreen.ASK;
            break;

        case 2: // '\002'
            CardCrawlGame.sound.play("DEBUFF_2");
            switch(buttonPressed)
            {
            case 0: // '\0'
                obtainReward(0);
                break;

            case 1: // '\001'
                obtainReward(1);
                break;

            case 2: // '\002'
                obtainReward(2);
                break;

            default:
                AbstractDungeon.player.damage(new DamageInfo(null, leaveCost, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
                damageTaken += leaveCost;
                setLeave();
                break;
            }
            break;

        case 3: // '\003'
            logMetric("Knowing Skull", optionsChosen, cards, null, null, null, null, potions, null, damageTaken, 0, 0, 0, goldEarned, 0);
            openMap();
            break;
        }
    }

    private void obtainReward(int slot)
    {
        slot;
        JVM INSTR tableswitch 0 2: default 471
    //                   0 28
    //                   1 181
    //                   2 318;
           goto _L1 _L2 _L3 _L4
_L2:
        AbstractDungeon.player.damage(new DamageInfo(null, potionCost, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
        damageTaken += potionCost;
        potionCost++;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        optionsChosen;
        append();
        "POTION ";
        append();
        toString();
        optionsChosen;
        imageEventText.updateBodyText((new StringBuilder()).append(POTION_MSG).append(ASK_AGAIN_MSG).toString());
        if(AbstractDungeon.player.hasRelic("Sozu"))
        {
            AbstractDungeon.player.getRelic("Sozu").flash();
        } else
        {
            AbstractPotion p = PotionHelper.getRandomPotion();
            potions.add(p.ID);
            AbstractDungeon.player.obtainPotion(p);
        }
        break MISSING_BLOCK_LABEL_481;
_L3:
        AbstractDungeon.player.damage(new DamageInfo(null, goldCost, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
        damageTaken += goldCost;
        goldCost++;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        optionsChosen;
        append();
        "GOLD ";
        append();
        toString();
        optionsChosen;
        imageEventText.updateBodyText((new StringBuilder()).append(GOLD_MSG).append(ASK_AGAIN_MSG).toString());
        AbstractDungeon.effectList.add(new RainingGoldEffect(90));
        AbstractDungeon.player.gainGold(90);
        goldEarned += 90;
        break MISSING_BLOCK_LABEL_481;
_L4:
        AbstractDungeon.player.damage(new DamageInfo(null, cardCost, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS));
        damageTaken += cardCost;
        cardCost++;
        new StringBuilder();
        this;
        JVM INSTR dup_x1 ;
        optionsChosen;
        append();
        "CARD ";
        append();
        toString();
        optionsChosen;
        imageEventText.updateBodyText((new StringBuilder()).append(CARD_MSG).append(ASK_AGAIN_MSG).toString());
        AbstractCard c = AbstractDungeon.returnColorlessCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON).makeCopy();
        cards.add(c.cardID);
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        break MISSING_BLOCK_LABEL_481;
_L1:
        logger.info("This should never happen.");
        imageEventText.clearAllDialogs();
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[4]).append(potionCost).append(OPTIONS[1]).toString());
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[5]).append(90).append(OPTIONS[6]).append(goldCost).append(OPTIONS[1]).toString());
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[3]).append(cardCost).append(OPTIONS[1]).toString());
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[7]).append(leaveCost).append(OPTIONS[1]).toString());
        return;
    }

    private void setLeave()
    {
        imageEventText.updateBodyText(LEAVE_MSG);
        imageEventText.clearAllDialogs();
        imageEventText.setDialogOption(OPTIONS[8]);
        screen = CurScreen.COMPLETE;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/events/city/KnowingSkull.getName());
    public static final String ID = "Knowing Skull";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String INTRO_MSG;
    private static final String INTRO_2_MSG;
    private static final String ASK_AGAIN_MSG;
    private static final String POTION_MSG;
    private static final String CARD_MSG;
    private static final String GOLD_MSG;
    private static final String LEAVE_MSG;
    private int potionCost;
    private int cardCost;
    private int goldCost;
    private int leaveCost;
    private static final int GOLD_REWARD = 90;
    private CurScreen screen;
    private String optionsChosen;
    private int damageTaken;
    private int goldEarned;
    private List potions;
    private List cards;
    private ArrayList options;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Knowing Skull");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        INTRO_MSG = DESCRIPTIONS[0];
        INTRO_2_MSG = DESCRIPTIONS[1];
        ASK_AGAIN_MSG = DESCRIPTIONS[2];
        POTION_MSG = DESCRIPTIONS[4];
        CARD_MSG = DESCRIPTIONS[5];
        GOLD_MSG = DESCRIPTIONS[6];
        LEAVE_MSG = DESCRIPTIONS[7];
    }
}
