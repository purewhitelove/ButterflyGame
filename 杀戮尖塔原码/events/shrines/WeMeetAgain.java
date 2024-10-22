// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WeMeetAgain.java

package com.megacrit.cardcrawl.events.shrines;

import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class WeMeetAgain extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/WeMeetAgain$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN COMPLETE;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            COMPLETE = new CUR_SCREEN("COMPLETE", 1);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, COMPLETE
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public WeMeetAgain()
    {
        super(NAME, DIALOG_1, "images/events/weMeetAgain.jpg");
        screen = CUR_SCREEN.INTRO;
        potionOption = AbstractDungeon.player.getRandomPotion();
        if(potionOption != null)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[0]).append(FontHelper.colorString(potionOption.name, "r")).append(OPTIONS[6]).toString());
        else
            imageEventText.setDialogOption(OPTIONS[1], true);
        goldAmount = getGoldAmount();
        if(goldAmount != 0)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[2]).append(goldAmount).append(OPTIONS[9]).append(OPTIONS[6]).toString());
        else
            imageEventText.setDialogOption(OPTIONS[3], true);
        cardOption = getRandomNonBasicCard();
        if(cardOption != null)
            imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[4]).append(cardOption.name).append(OPTIONS[6]).toString(), cardOption.makeStatEquivalentCopy());
        else
            imageEventText.setDialogOption(OPTIONS[5], true);
        imageEventText.setDialogOption(OPTIONS[7]);
    }

    private AbstractCard getRandomNonBasicCard()
    {
        ArrayList list = new ArrayList();
        Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.rarity != com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC && c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE)
                list.add(c);
        } while(true);
        if(list.isEmpty())
        {
            return null;
        } else
        {
            Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
            return (AbstractCard)list.get(0);
        }
    }

    private int getGoldAmount()
    {
        if(AbstractDungeon.player.gold < 50)
            return 0;
        if(AbstractDungeon.player.gold > 150)
            return AbstractDungeon.miscRng.random(50, 150);
        else
            return AbstractDungeon.miscRng.random(50, AbstractDungeon.player.gold);
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$WeMeetAgain$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$WeMeetAgain$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$WeMeetAgain$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$WeMeetAgain$CUR_SCREEN[CUR_SCREEN.COMPLETE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.WeMeetAgain.CUR_SCREEN[screen.ordinal()])
        {
        case 1: // '\001'
            screen = CUR_SCREEN.COMPLETE;
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText((new StringBuilder()).append(DESCRIPTIONS[1]).append(DESCRIPTIONS[5]).toString());
                AbstractDungeon.player.removePotion(potionOption);
                relicReward();
                logMetricObtainRelic("WeMeetAgain", "Gave Potion", givenRelic);
                break;

            case 1: // '\001'
                imageEventText.updateBodyText((new StringBuilder()).append(DESCRIPTIONS[2]).append(DESCRIPTIONS[5]).toString());
                AbstractDungeon.player.loseGold(goldAmount);
                relicReward();
                logMetricObtainRelicAtCost("WeMeetAgain", "Paid Gold", givenRelic, goldAmount);
                break;

            case 2: // '\002'
                imageEventText.updateBodyText((new StringBuilder()).append(DESCRIPTIONS[3]).append(DESCRIPTIONS[5]).toString());
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(cardOption, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(cardOption);
                relicReward();
                logMetricRemoveCardAndObtainRelic("WeMeetAgain", "Gave Card", cardOption, givenRelic);
                break;

            case 3: // '\003'
                imageEventText.updateBodyText(DESCRIPTIONS[4]);
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
                CardCrawlGame.sound.play("BLUNT_HEAVY");
                logMetricIgnored("WeMeetAgain");
                break;
            }
            imageEventText.updateDialogOption(0, OPTIONS[8]);
            imageEventText.clearRemainingOptions();
            break;

        case 2: // '\002'
            openMap();
            break;
        }
    }

    private void relicReward()
    {
        givenRelic = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH * 0.28F, (float)Settings.HEIGHT / 2.0F, givenRelic);
    }

    public static final String ID = "WeMeetAgain";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final int MAX_GOLD = 150;
    private AbstractPotion potionOption;
    private AbstractCard cardOption;
    private int goldAmount;
    private AbstractRelic givenRelic;
    private static final String DIALOG_1;
    private CUR_SCREEN screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("WeMeetAgain");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
    }
}
