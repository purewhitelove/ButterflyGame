// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MindBloom.java

package com.megacrit.cardcrawl.events.beyond;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.curses.Normality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MindBloom extends AbstractImageEvent
{
    private static final class CurScreen extends Enum
    {

        public static CurScreen[] values()
        {
            return (CurScreen[])$VALUES.clone();
        }

        public static CurScreen valueOf(String name)
        {
            return (CurScreen)Enum.valueOf(com/megacrit/cardcrawl/events/beyond/MindBloom$CurScreen, name);
        }

        public static final CurScreen INTRO;
        public static final CurScreen FIGHT;
        public static final CurScreen LEAVE;
        private static final CurScreen $VALUES[];

        static 
        {
            INTRO = new CurScreen("INTRO", 0);
            FIGHT = new CurScreen("FIGHT", 1);
            LEAVE = new CurScreen("LEAVE", 2);
            $VALUES = (new CurScreen[] {
                INTRO, FIGHT, LEAVE
            });
        }

        private CurScreen(String s, int i)
        {
            super(s, i);
        }
    }


    public MindBloom()
    {
        super(NAME, DIALOG_1, "images/events/mindBloom.jpg");
        screen = CurScreen.INTRO;
        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption(OPTIONS[3]);
        if(AbstractDungeon.floorNum % 50 <= 40)
            imageEventText.setDialogOption(OPTIONS[1], CardLibrary.getCopy("Normality"));
        else
            imageEventText.setDialogOption(OPTIONS[2], CardLibrary.getCopy("Doubt"));
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$beyond$MindBloom$CurScreen[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$beyond$MindBloom$CurScreen = new int[CurScreen.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$MindBloom$CurScreen[CurScreen.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$beyond$MindBloom$CurScreen[CurScreen.LEAVE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.beyond.MindBloom.CurScreen[screen.ordinal()])
        {
        case 1: // '\001'
            switch(buttonPressed)
            {
            default:
                break;

            case 0: // '\0'
                imageEventText.updateBodyText(DIALOG_2);
                screen = CurScreen.FIGHT;
                logMetric("MindBloom", "Fight");
                CardCrawlGame.music.playTempBgmInstantly("MINDBLOOM", true);
                ArrayList list = new ArrayList();
                list.add("The Guardian");
                list.add("Hexaghost");
                list.add("Slime Boss");
                Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter((String)list.get(0));
                AbstractDungeon.getCurrRoom().rewards.clear();
                if(AbstractDungeon.ascensionLevel >= 13)
                    AbstractDungeon.getCurrRoom().addGoldToRewards(25);
                else
                    AbstractDungeon.getCurrRoom().addGoldToRewards(50);
                AbstractDungeon.getCurrRoom().addRelicToRewards(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE);
                enterCombatFromImage();
                AbstractDungeon.lastCombatMetricKey = "Mind Bloom Boss Battle";
                break;

            case 1: // '\001'
                imageEventText.updateBodyText(DIALOG_3);
                screen = CurScreen.LEAVE;
                int effectCount = 0;
                List upgradedCards = new ArrayList();
                List obtainedRelic = new ArrayList();
                Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractCard c = (AbstractCard)iterator.next();
                    if(c.canUpgrade())
                    {
                        if(++effectCount <= 20)
                        {
                            float x = MathUtils.random(0.1F, 0.9F) * (float)Settings.WIDTH;
                            float y = MathUtils.random(0.2F, 0.8F) * (float)Settings.HEIGHT;
                            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), x, y));
                            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
                        }
                        upgradedCards.add(c.cardID);
                        c.upgrade();
                        AbstractDungeon.player.bottledCardUpgradeCheck(c);
                    }
                } while(true);
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F, RelicLibrary.getRelic("Mark of the Bloom").makeCopy());
                obtainedRelic.add("Mark of the Bloom");
                logMetric("MindBloom", "Upgrade", null, null, null, upgradedCards, obtainedRelic, null, null, 0, 0, 0, 0, 0, 0);
                imageEventText.updateDialogOption(0, OPTIONS[4]);
                break;

            case 2: // '\002'
                if(AbstractDungeon.floorNum % 50 <= 40)
                {
                    imageEventText.updateBodyText(DIALOG_2);
                    screen = CurScreen.LEAVE;
                    List cardsAdded = new ArrayList();
                    cardsAdded.add("Normality");
                    cardsAdded.add("Normality");
                    logMetric("MindBloom", "Gold", cardsAdded, null, null, null, null, null, null, 0, 0, 0, 0, 999, 0);
                    AbstractDungeon.effectList.add(new RainingGoldEffect(999));
                    AbstractDungeon.player.gainGold(999);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Normality(), (float)Settings.WIDTH * 0.6F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Normality(), (float)Settings.WIDTH * 0.3F, (float)Settings.HEIGHT / 2.0F));
                    imageEventText.updateDialogOption(0, OPTIONS[4]);
                } else
                {
                    imageEventText.updateBodyText(DIALOG_2);
                    screen = CurScreen.LEAVE;
                    AbstractCard curse = new Doubt();
                    logMetricObtainCardAndHeal("MindBloom", "Heal", curse, AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth);
                    AbstractDungeon.player.heal(AbstractDungeon.player.maxHealth);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    imageEventText.updateDialogOption(0, OPTIONS[4]);
                }
                break;
            }
            imageEventText.clearRemainingOptions();
            break;

        case 2: // '\002'
            openMap();
            break;

        default:
            openMap();
            break;
        }
    }

    public static final String ID = "MindBloom";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String DIALOG_2;
    private static final String DIALOG_3;
    private CurScreen screen;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("MindBloom");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        DIALOG_2 = DESCRIPTIONS[1];
        DIALOG_3 = DESCRIPTIONS[2];
    }
}
