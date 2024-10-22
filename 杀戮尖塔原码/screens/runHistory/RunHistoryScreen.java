// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RunHistoryScreen.java

package com.megacrit.cardcrawl.screens.runHistory;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.Metrics;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.screens.options.DropdownMenu;
import com.megacrit.cardcrawl.screens.options.DropdownMenuListener;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.screens.stats.RunData;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.runHistory:
//            RunHistoryPath, ModIcons, CopyableTextElement, TinyCard, 
//            RunPathElement

public class RunHistoryScreen
    implements DropdownMenuListener
{
    private static final class InputSection extends Enum
    {

        public static InputSection[] values()
        {
            return (InputSection[])$VALUES.clone();
        }

        public static InputSection valueOf(String name)
        {
            return (InputSection)Enum.valueOf(com/megacrit/cardcrawl/screens/runHistory/RunHistoryScreen$InputSection, name);
        }

        public static final InputSection DROPDOWN;
        public static final InputSection ROOM;
        public static final InputSection RELIC;
        public static final InputSection CARD;
        private static final InputSection $VALUES[];

        static 
        {
            DROPDOWN = new InputSection("DROPDOWN", 0);
            ROOM = new InputSection("ROOM", 1);
            RELIC = new InputSection("RELIC", 2);
            CARD = new InputSection("CARD", 3);
            $VALUES = (new InputSection[] {
                DROPDOWN, ROOM, RELIC, CARD
            });
        }

        private InputSection(String s, int i)
        {
            super(s, i);
        }
    }


    public RunHistoryScreen()
    {
        button = new MenuCancelButton();
        unfilteredRuns = new ArrayList();
        filteredRuns = new ArrayList();
        runIndex = 0;
        viewedRun = null;
        screenUp = false;
        currentChar = null;
        screenX = HIDE_X;
        targetX = HIDE_X;
        grabbedScreen = false;
        grabStartY = 0.0F;
        scrollTargetY = 0.0F;
        scrollY = 0.0F;
        scrollLowerBound = 0.0F;
        scrollUpperBound = 0.0F;
        relics = new ArrayList();
        cards = new ArrayList();
        cardCountByRarityString = "";
        relicCountByRarityString = "";
        circletCount = 0;
        controllerUiColor = new Color(0.7F, 0.9F, 1.0F, 0.25F);
        hoveredRelic = null;
        clickStartedRelic = null;
        runPath = new RunHistoryPath();
        modIcons = new ModIcons();
        seedElement = new CopyableTextElement(FontHelper.cardDescFont_N);
        secondSeedElement = new CopyableTextElement(FontHelper.cardDescFont_N);
        prevHb = new Hitbox(110F * Settings.scale, 110F * Settings.scale);
        prevHb.move(180F * Settings.scale, (float)Settings.HEIGHT / 2.0F);
        nextHb = new Hitbox(110F * Settings.scale, 110F * Settings.scale);
        nextHb.move((float)Settings.WIDTH - 180F * Settings.xScale, (float)Settings.HEIGHT / 2.0F);
    }

    public void refreshData()
    {
        FileHandle afilehandle[];
        int i;
        int j;
        FileHandle subfolders[] = Gdx.files.local((new StringBuilder()).append("runs").append(File.separator).toString()).list();
        unfilteredRuns.clear();
        afilehandle = subfolders;
        i = afilehandle.length;
        j = 0;
_L6:
        if(j >= i) goto _L2; else goto _L1
_L1:
        FileHandle afilehandle1[];
        int k;
        int l;
        FileHandle subFolder = afilehandle[j];
        switch(CardCrawlGame.saveSlot)
        {
        case 0: // '\0'
            if(!subFolder.name().contains("0_") && !subFolder.name().contains("1_") && !subFolder.name().contains("2_"))
                break;
            continue; /* Loop/switch isn't completed */

        default:
            if(!subFolder.name().contains((new StringBuilder()).append(CardCrawlGame.saveSlot).append("_").toString()))
                continue; /* Loop/switch isn't completed */
            break;
        }
        afilehandle1 = subFolder.list();
        k = afilehandle1.length;
        l = 0;
_L5:
        if(l >= k) goto _L4; else goto _L3
_L3:
        FileHandle file = afilehandle1[l];
        RunData data;
        data = (RunData)gson.fromJson(file.readString(), com/megacrit/cardcrawl/screens/stats/RunData);
        if(data != null && data.timestamp == null)
        {
            data.timestamp = file.nameWithoutExtension();
            String exampleDaysSinceUnixStr = "17586";
            boolean assumeDaysSinceUnix = data.timestamp.length() == exampleDaysSinceUnixStr.length();
            if(assumeDaysSinceUnix)
                try
                {
                    long secondsInDay = 0x15180L;
                    long days = Long.parseLong(data.timestamp);
                    data.timestamp = Long.toString(days * 0x15180L);
                }
                catch(NumberFormatException ex)
                {
                    logger.info((new StringBuilder()).append("Run file ").append(file.path()).append(" name is could not be parsed into a Timestamp.").toString());
                    data = null;
                }
        }
        if(data == null)
            continue; /* Loop/switch isn't completed */
        try
        {
            com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.valueOf(data.character_chosen);
            unfilteredRuns.add(data);
        }
        catch(RuntimeException ex)
        {
            logger.info((new StringBuilder()).append("Run file ").append(file.path()).append(" does not use a real character: ").append(data.character_chosen).toString());
        }
        continue; /* Loop/switch isn't completed */
        JsonSyntaxException ex;
        ex;
        logger.info((new StringBuilder()).append("Failed to load RunData from JSON file: ").append(file.path()).toString());
        l++;
          goto _L5
_L4:
        j++;
          goto _L6
_L2:
        if(unfilteredRuns.size() > 0)
        {
            unfilteredRuns.sort(RunData.orderByTimestampDesc);
            viewedRun = (RunData)unfilteredRuns.get(0);
        }
        String charFilterOptions[] = {
            ALL_CHARACTERS_TEXT, IRONCLAD_NAME, SILENT_NAME, DEFECT_NAME, WATCHER_NAME
        };
        characterFilter = new DropdownMenu(this, charFilterOptions, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        String winLossFilterOptions[] = {
            WINS_AND_LOSSES_TEXT, WINS_TEXT, LOSSES_TEXT
        };
        winLossFilter = new DropdownMenu(this, winLossFilterOptions, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        String runTypeFilterOptions[] = {
            RUN_TYPE_ALL, RUN_TYPE_NORMAL, RUN_TYPE_ASCENSION, RUN_TYPE_DAILY
        };
        runTypeFilter = new DropdownMenu(this, runTypeFilterOptions, FontHelper.cardDescFont_N, Settings.CREAM_COLOR);
        resetRunsDropdown();
        return;
    }

    private void resetRunsDropdown()
    {
        filteredRuns.clear();
        boolean only_wins = winLossFilter.getSelectedIndex() == 1;
        boolean only_losses = winLossFilter.getSelectedIndex() == 2;
        boolean only_ironclad = characterFilter.getSelectedIndex() == 1;
        boolean only_silent = characterFilter.getSelectedIndex() == 2;
        boolean only_defect = characterFilter.getSelectedIndex() == 3;
        boolean only_watcher = characterFilter.getSelectedIndex() == 4;
        boolean only_normal = runTypeFilter.getSelectedIndex() == 1;
        boolean only_ascension = runTypeFilter.getSelectedIndex() == 2;
        boolean only_daily = runTypeFilter.getSelectedIndex() == 3;
        ArrayList options = unfilteredRuns.iterator();
        do
        {
            if(!options.hasNext())
                break;
            RunData data = (RunData)options.next();
            boolean includeMe = true;
            if(only_wins)
                includeMe = includeMe && data.victory;
            else
            if(only_losses)
                includeMe = includeMe && !data.victory;
            String runCharacter = data.character_chosen;
            if(only_ironclad)
                includeMe = includeMe && runCharacter.equals(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.name());
            else
            if(only_silent)
                includeMe = includeMe && runCharacter.equals(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.name());
            else
            if(only_defect)
                includeMe = includeMe && runCharacter.equals(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.name());
            else
            if(only_watcher)
                includeMe = includeMe && runCharacter.equals(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.name());
            if(only_normal)
                includeMe = includeMe && !data.is_ascension_mode && !data.is_daily;
            else
            if(only_ascension)
                includeMe = includeMe && data.is_ascension_mode;
            else
            if(only_daily)
                includeMe = includeMe && data.is_daily;
            if(includeMe)
                filteredRuns.add(data);
        } while(true);
        options = new ArrayList();
        SimpleDateFormat dateFormat;
        if(Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.JPN)
            dateFormat = new SimpleDateFormat(TEXT[34], Locale.JAPAN);
        else
            dateFormat = new SimpleDateFormat(TEXT[34]);
        for(Iterator iterator = filteredRuns.iterator(); iterator.hasNext();)
        {
            RunData run = (RunData)iterator.next();
            try
            {
                String dateTimeStr;
                if(run.local_time != null)
                    dateTimeStr = dateFormat.format(Metrics.timestampFormatter.parse(run.local_time));
                else
                    dateTimeStr = dateFormat.format(Long.valueOf(Long.valueOf(run.timestamp).longValue() * 1000L));
                dateTimeStr = (new StringBuilder()).append(dateTimeStr).append(" - ").append(run.score).toString();
                options.add(dateTimeStr);
            }
            catch(Exception e)
            {
                logger.info(e.getMessage());
            }
        }

        runsDropdown = new DropdownMenu(this, options, FontHelper.panelNameFont, Settings.CREAM_COLOR);
        runIndex = 0;
        if(filteredRuns.size() > 0)
        {
            reloadWithRunData((RunData)filteredRuns.get(runIndex));
        } else
        {
            viewedRun = null;
            reloadWithRunData(null);
        }
    }

    public String baseCardSuffixForCharacter(String character)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[];
            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[];
            static final int $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[];
            static final int $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunHistoryScreen$InputSection[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunHistoryScreen$InputSection = new int[InputSection.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunHistoryScreen$InputSection[InputSection.DROPDOWN.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunHistoryScreen$InputSection[InputSection.ROOM.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunHistoryScreen$InputSection[InputSection.RELIC.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$runHistory$RunHistoryScreen$InputSection[InputSection.CARD.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier = new int[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.STARTER.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SPECIAL.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SHOP.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.DEPRECATED.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardRarity[com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror17) { }
                $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass = new int[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror18) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror19) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror20) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror21) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.valueOf(character).ordinal()])
        {
        case 1: // '\001'
            return "_R";

        case 2: // '\002'
            return "_G";

        case 3: // '\003'
            return "_B";

        case 4: // '\004'
            return "_W";
        }
        return "";
    }

    public void reloadWithRunData(RunData runData)
    {
        if(runData == null)
        {
            logger.info("Attempted to load Run History with 0 runs.");
            return;
        }
        scrollUpperBound = 0.0F;
        viewedRun = runData;
        reloadRelics(runData);
        reloadCards(runData);
        runPath.setRunData(runData);
        modIcons.setRunData(runData);
        try
        {
            if(viewedRun.special_seed == null || viewedRun.special_seed.longValue() == 0L || viewedRun.is_daily)
            {
                String seedFormat = viewedRun.chose_seed ? CUSTOM_SEED_LABEL : SEED_LABEL;
                String seedText = SeedHelper.getString(Long.parseLong(runData.seed_played));
                seedElement.setText(String.format(seedFormat, new Object[] {
                    seedText
                }), seedText);
                secondSeedElement.setText("", "");
            } else
            {
                String seedText = SeedHelper.getString(runData.special_seed.longValue());
                seedElement.setText(String.format(CUSTOM_SEED_LABEL, new Object[] {
                    seedText
                }), seedText);
                String secondSeedText = SeedHelper.getString(Long.parseLong(runData.seed_played));
                secondSeedElement.setText(String.format(SEED_LABEL, new Object[] {
                    secondSeedText
                }), secondSeedText);
            }
        }
        catch(NumberFormatException ex)
        {
            seedElement.setText("", "");
            secondSeedElement.setText("", "");
        }
        scrollTargetY = 0.0F;
        resetScrolling();
        if(runsDropdown != null)
            runsDropdown.setSelectedIndex(filteredRuns.indexOf(runData));
    }

    private void reloadRelics(RunData runData)
    {
        relics.clear();
        circletCount = runData.circlet_count;
        boolean circletCountSet = circletCount > 0;
        Hashtable relicRarityCounts = new Hashtable();
        AbstractRelic circlet = null;
        for(Iterator iterator = runData.relics.iterator(); iterator.hasNext();)
        {
            String relicName = (String)iterator.next();
            try
            {
                AbstractRelic relic = RelicLibrary.getRelic(relicName).makeCopy();
                relic.isSeen = true;
                if(relic instanceof Circlet)
                {
                    if(relicName.equals("Circlet"))
                    {
                        if(!circletCountSet)
                            circletCount++;
                        if(circlet == null)
                        {
                            circlet = relic;
                            relics.add(relic);
                        }
                    } else
                    {
                        logger.info((new StringBuilder()).append("Could not find relic for: ").append(relicName).toString());
                    }
                } else
                {
                    relics.add(relic);
                }
                int newCount = relicRarityCounts.containsKey(relic.tier) ? ((Integer)relicRarityCounts.get(relic.tier)).intValue() + 1 : 1;
                relicRarityCounts.put(relic.tier, Integer.valueOf(newCount));
            }
            catch(NullPointerException ex)
            {
                logger.info((new StringBuilder()).append("NPE while loading: ").append(relicName).toString());
            }
        }

        if(circlet != null && circletCount > 1)
            circlet.setCounter(circletCount);
        StringBuilder bldr = new StringBuilder();
        com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier arelictier[] = orderedRelicRarity;
        int i = arelictier.length;
        for(int j = 0; j < i; j++)
        {
            com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier rarity = arelictier[j];
            if(!relicRarityCounts.containsKey(rarity))
                continue;
            if(bldr.length() > 0)
                bldr.append(", ");
            bldr.append(String.format(COUNT_WITH_LABEL, new Object[] {
                relicRarityCounts.get(rarity), rarityLabel(rarity)
            }));
        }

        relicCountByRarityString = bldr.toString();
    }

    private void reloadCards(RunData runData)
    {
        Hashtable rawNameToCards = new Hashtable();
        Hashtable cardCounts = new Hashtable();
        Hashtable cardRarityCounts = new Hashtable();
        CardGroup sortedMasterDeck = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        StringBuilder bldr = runData.master_deck.iterator();
        do
        {
            if(!bldr.hasNext())
                break;
            String cardID = (String)bldr.next();
            AbstractCard card;
            if(rawNameToCards.containsKey(cardID))
                card = (AbstractCard)rawNameToCards.get(cardID);
            else
                card = cardForName(runData, cardID);
            if(card != null)
            {
                int value = cardCounts.containsKey(card) ? ((Integer)cardCounts.get(card)).intValue() + 1 : 1;
                cardCounts.put(card, Integer.valueOf(value));
                rawNameToCards.put(cardID, card);
                int rarityCount = cardRarityCounts.containsKey(card.rarity) ? ((Integer)cardRarityCounts.get(card.rarity)).intValue() + 1 : 1;
                cardRarityCounts.put(card.rarity, Integer.valueOf(rarityCount));
            }
        } while(true);
        sortedMasterDeck.clear();
        AbstractCard card;
        for(bldr = rawNameToCards.values().iterator(); bldr.hasNext(); sortedMasterDeck.addToTop(card))
            card = (AbstractCard)bldr.next();

        sortedMasterDeck.sortAlphabetically(true);
        sortedMasterDeck.sortByRarityPlusStatusCardType(false);
        sortedMasterDeck = sortedMasterDeck.getGroupedByColor();
        cards.clear();
        AbstractCard card;
        for(bldr = sortedMasterDeck.group.iterator(); bldr.hasNext(); cards.add(new TinyCard(card, ((Integer)cardCounts.get(card)).intValue())))
            card = (AbstractCard)bldr.next();

        bldr = new StringBuilder();
        com.megacrit.cardcrawl.cards.AbstractCard.CardRarity acardrarity[] = orderedRarity;
        int i = acardrarity.length;
        for(int j = 0; j < i; j++)
        {
            com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity = acardrarity[j];
            if(!cardRarityCounts.containsKey(rarity))
                continue;
            if(bldr.length() > 0)
                bldr.append(", ");
            bldr.append(String.format(COUNT_WITH_LABEL, new Object[] {
                cardRarityCounts.get(rarity), rarityLabel(rarity)
            }));
        }

        cardCountByRarityString = bldr.toString();
    }

    private String rarityLabel(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity rarity)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[rarity.ordinal()])
        {
        case 1: // '\001'
            return RARITY_LABEL_STARTER;

        case 2: // '\002'
            return RARITY_LABEL_SPECIAL;

        case 3: // '\003'
            return RARITY_LABEL_COMMON;

        case 4: // '\004'
            return RARITY_LABEL_UNCOMMON;

        case 5: // '\005'
            return RARITY_LABEL_RARE;

        case 6: // '\006'
            return RARITY_LABEL_CURSE;
        }
        return RARITY_LABEL_UNKNOWN;
    }

    private String rarityLabel(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier rarity)
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[rarity.ordinal()])
        {
        case 1: // '\001'
            return RARITY_LABEL_STARTER;

        case 2: // '\002'
            return RARITY_LABEL_COMMON;

        case 3: // '\003'
            return RARITY_LABEL_UNCOMMON;

        case 4: // '\004'
            return RARITY_LABEL_RARE;

        case 5: // '\005'
            return RARITY_LABEL_SPECIAL;

        case 6: // '\006'
            return RARITY_LABEL_BOSS;

        case 7: // '\007'
            return RARITY_LABEL_SHOP;

        case 8: // '\b'
        default:
            return RARITY_LABEL_UNKNOWN;
        }
    }

    private void layoutTinyCards(ArrayList cards, float x, float y)
    {
        float originX = x + screenPosX(60F);
        float originY = y - screenPosY(64F);
        float rowHeight = screenPosY(48F);
        float columnWidth = screenPosX(340F);
        int row = 0;
        int column = 0;
        TinyCard.desiredColumns = cards.size() > 36 ? 4 : 3;
        int cardsPerColumn = cards.size() / TinyCard.desiredColumns;
        int remainderCards = cards.size() - cardsPerColumn * TinyCard.desiredColumns;
        int columnSizes[] = new int[TinyCard.desiredColumns];
        Arrays.fill(columnSizes, cardsPerColumn);
        for(int i = 0; i < remainderCards; i++)
            columnSizes[i % TinyCard.desiredColumns]++;

        for(Iterator iterator = cards.iterator(); iterator.hasNext();)
        {
            TinyCard card = (TinyCard)iterator.next();
            if(row >= columnSizes[column])
            {
                row = 0;
                column++;
            }
            float cardY = originY - (float)row * rowHeight;
            card.hb.move(originX + (float)column * columnWidth + card.hb.width / 2.0F, cardY);
            if(card.col == -1)
            {
                card.col = column;
                card.row = row;
            }
            row++;
            scrollUpperBound = Math.max(scrollUpperBound, (scrollY - cardY) + screenPosY(50F));
        }

    }

    private AbstractCard cardForName(RunData runData, String cardID)
    {
        String libraryLookupName = cardID;
        if(cardID.endsWith("+"))
            libraryLookupName = cardID.substring(0, cardID.length() - 1);
        if(libraryLookupName.equals("Defend") || libraryLookupName.equals("Strike"))
            libraryLookupName = (new StringBuilder()).append(libraryLookupName).append(baseCardSuffixForCharacter(runData.character_chosen)).toString();
        AbstractCard card = CardLibrary.getCard(libraryLookupName);
        int upgrades = 0;
        if(card != null)
        {
            if(cardID.endsWith("+"))
                upgrades = 1;
        } else
        if(libraryLookupName.contains("+"))
        {
            String split[] = libraryLookupName.split("\\+", -1);
            libraryLookupName = split[0];
            upgrades = Integer.parseInt(split[1]);
            card = CardLibrary.getCard(libraryLookupName);
        }
        if(card != null)
        {
            card = card.makeCopy();
            for(int i = 0; i < upgrades; i++)
                card.upgrade();

            return card;
        } else
        {
            logger.info((new StringBuilder()).append("Could not find card named: ").append(cardID).toString());
            return null;
        }
    }

    public void update()
    {
        updateControllerInput();
        if(Settings.isControllerMode && !CardCrawlGame.isPopupOpen && currentHb != null)
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.8F)
            {
                scrollTargetY += Settings.SCROLL_SPEED / 2.0F;
                if(scrollTargetY > scrollUpperBound)
                    scrollTargetY = scrollUpperBound;
            } else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.2F && scrollY > 100F)
            {
                scrollTargetY -= Settings.SCROLL_SPEED / 2.0F;
                if(scrollTargetY < scrollLowerBound)
                    scrollTargetY = scrollLowerBound;
            }
        if(runsDropdown.isOpen)
        {
            runsDropdown.update();
            return;
        }
        if(winLossFilter.isOpen)
        {
            winLossFilter.update();
            return;
        }
        if(characterFilter.isOpen)
        {
            characterFilter.update();
            return;
        }
        if(runTypeFilter.isOpen)
        {
            runTypeFilter.update();
            return;
        }
        runsDropdown.update();
        winLossFilter.update();
        characterFilter.update();
        runTypeFilter.update();
        button.update();
        updateScrolling();
        updateArrows();
        modIcons.update();
        runPath.update();
        if(!seedElement.getText().isEmpty())
            seedElement.update();
        if(!secondSeedElement.getText().isEmpty())
            secondSeedElement.update();
        if(button.hb.clicked || InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            hide();
        }
        screenX = MathHelper.uiLerpSnap(screenX, targetX);
        if(filteredRuns.size() == 0)
            return;
        boolean isAPopupOpen = CardCrawlGame.cardPopup.isOpen || CardCrawlGame.relicPopup.isOpen;
        if(!isAPopupOpen)
        {
            if(InputActionSet.left.isJustPressed())
            {
                runIndex = Math.max(0, runIndex - 1);
                reloadWithRunData((RunData)filteredRuns.get(runIndex));
            }
            if(InputActionSet.right.isJustPressed())
            {
                runIndex = Math.min(runIndex + 1, filteredRuns.size() - 1);
                reloadWithRunData((RunData)filteredRuns.get(runIndex));
            }
        }
        handleRelicInteraction();
        Iterator iterator = cards.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            TinyCard card = (TinyCard)iterator.next();
            boolean didClick = card.updateDidClick();
            if(didClick)
            {
                CardGroup cardGroup = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
                TinyCard addMe;
                for(Iterator iterator1 = cards.iterator(); iterator1.hasNext(); cardGroup.addToTop(addMe.card))
                    addMe = (TinyCard)iterator1.next();

                CardCrawlGame.cardPopup.open(card.card, cardGroup);
            }
        } while(true);
        if(Settings.isControllerMode && currentHb != null)
            CInputHelper.setCursor(currentHb);
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || runsDropdown.isOpen || winLossFilter.isOpen || characterFilter.isOpen || runTypeFilter.isOpen)
            return;
        InputSection section = null;
        boolean anyHovered = false;
        int index = 0;
        ArrayList hbs = new ArrayList();
        if(!runsDropdown.rows.isEmpty())
            hbs.add(runsDropdown.getHitbox());
        hbs.add(winLossFilter.getHitbox());
        hbs.add(characterFilter.getHitbox());
        hbs.add(runTypeFilter.getHitbox());
        Iterator iterator = hbs.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            Hitbox hb = (Hitbox)iterator.next();
            if(hb.hovered)
            {
                section = InputSection.DROPDOWN;
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator1 = runPath.pathElements.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                RunPathElement e = (RunPathElement)iterator1.next();
                if(e.hb.hovered)
                {
                    section = InputSection.ROOM;
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator2 = relics.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator2.next();
                if(r.hb.hovered)
                {
                    section = InputSection.RELIC;
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator3 = cards.iterator();
            do
            {
                if(!iterator3.hasNext())
                    break;
                TinyCard card = (TinyCard)iterator3.next();
                if(card.hb.hovered)
                {
                    section = InputSection.CARD;
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            CInputHelper.setCursor((Hitbox)hbs.get(0));
            currentHb = (Hitbox)hbs.get(0);
            scrollTargetY = 0.0F;
        } else
        {
label0:
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.runHistory.RunHistoryScreen.InputSection[section.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if(--index != -1)
                    {
                        CInputHelper.setCursor((Hitbox)hbs.get(index));
                        currentHb = (Hitbox)hbs.get(index);
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    index++;
                    if(hbs.size() == 4)
                    {
                        if(index > hbs.size() - 1 || index == 1)
                        {
                            if(!runPath.pathElements.isEmpty())
                            {
                                CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(0)).hb);
                                currentHb = ((RunPathElement)runPath.pathElements.get(0)).hb;
                                break;
                            }
                            if(!relics.isEmpty())
                            {
                                CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
                                currentHb = ((AbstractRelic)relics.get(0)).hb;
                            } else
                            {
                                CInputHelper.setCursor(((TinyCard)cards.get(0)).hb);
                                currentHb = ((TinyCard)cards.get(0)).hb;
                            }
                        } else
                        {
                            CInputHelper.setCursor((Hitbox)hbs.get(index));
                            currentHb = (Hitbox)hbs.get(index);
                        }
                        break;
                    }
                    if(index > hbs.size() - 1)
                        index = 0;
                    CInputHelper.setCursor((Hitbox)hbs.get(index));
                    currentHb = (Hitbox)hbs.get(index);
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(index == 0)
                    {
                        CInputHelper.setCursor((Hitbox)hbs.get(1));
                        currentHb = (Hitbox)hbs.get(1);
                    }
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(index == 1)
                {
                    CInputHelper.setCursor((Hitbox)hbs.get(0));
                    currentHb = (Hitbox)hbs.get(0);
                    scrollTargetY = 0.0F;
                    break;
                }
                if(index <= 1)
                    break;
                if(!runPath.pathElements.isEmpty())
                {
                    CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(0)).hb);
                    currentHb = ((RunPathElement)runPath.pathElements.get(0)).hb;
                    break;
                }
                if(!relics.isEmpty())
                {
                    CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
                    currentHb = ((AbstractRelic)relics.get(0)).hb;
                    break;
                }
                if(!cards.isEmpty())
                {
                    CInputHelper.setCursor(((TinyCard)cards.get(0)).hb);
                    currentHb = ((TinyCard)cards.get(0)).hb;
                }
                break;

            case 2: // '\002'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    int c = ((RunPathElement)runPath.pathElements.get(index)).col;
                    int r = ((RunPathElement)runPath.pathElements.get(index)).row - 1;
                    if(r < 0)
                    {
                        CInputHelper.setCursor((Hitbox)hbs.get(0));
                        currentHb = (Hitbox)hbs.get(0);
                        scrollTargetY = 0.0F;
                        break;
                    }
                    boolean foundNode = false;
                    int i = 0;
                    do
                    {
                        if(i >= runPath.pathElements.size())
                            break;
                        if(((RunPathElement)runPath.pathElements.get(i)).row == r && ((RunPathElement)runPath.pathElements.get(i)).col == c)
                        {
                            CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(i)).hb);
                            currentHb = ((RunPathElement)runPath.pathElements.get(i)).hb;
                            foundNode = true;
                            break;
                        }
                        i++;
                    } while(true);
                    if(!foundNode)
                    {
                        i = runPath.pathElements.size() - 1;
                        do
                        {
                            if(i <= 0)
                                break;
                            if(((RunPathElement)runPath.pathElements.get(i)).row == r)
                            {
                                CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(i)).hb);
                                currentHb = ((RunPathElement)runPath.pathElements.get(i)).hb;
                                foundNode = true;
                                break;
                            }
                            i--;
                        } while(true);
                    }
                    if(!foundNode)
                    {
                        CInputHelper.setCursor((Hitbox)hbs.get(0));
                        currentHb = (Hitbox)hbs.get(0);
                        scrollTargetY = 0.0F;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    int c = ((RunPathElement)runPath.pathElements.get(index)).col;
                    int r = ((RunPathElement)runPath.pathElements.get(index)).row + 1;
                    if(r > ((RunPathElement)runPath.pathElements.get(runPath.pathElements.size() - 1)).row)
                    {
                        if(!relics.isEmpty())
                        {
                            CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
                            currentHb = ((AbstractRelic)relics.get(0)).hb;
                        } else
                        {
                            CInputHelper.setCursor(((TinyCard)cards.get(0)).hb);
                            currentHb = ((TinyCard)cards.get(0)).hb;
                        }
                        break;
                    }
                    boolean foundNode = false;
                    int i = runPath.pathElements.size() - 1;
                    do
                    {
                        if(i <= 0)
                            break;
                        if(((RunPathElement)runPath.pathElements.get(i)).row == r && ((RunPathElement)runPath.pathElements.get(i)).col == c)
                        {
                            CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(i)).hb);
                            currentHb = ((RunPathElement)runPath.pathElements.get(i)).hb;
                            foundNode = true;
                            break;
                        }
                        i--;
                    } while(true);
                    if(!foundNode)
                    {
                        i = runPath.pathElements.size() - 1;
                        do
                        {
                            if(i <= 0)
                                break;
                            if(((RunPathElement)runPath.pathElements.get(i)).row == r)
                            {
                                CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(i)).hb);
                                currentHb = ((RunPathElement)runPath.pathElements.get(i)).hb;
                                foundNode = true;
                                break;
                            }
                            i--;
                        } while(true);
                    }
                    if(foundNode)
                        break;
                    if(!relics.isEmpty())
                    {
                        CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
                        currentHb = ((AbstractRelic)relics.get(0)).hb;
                    } else
                    {
                        CInputHelper.setCursor(((TinyCard)cards.get(0)).hb);
                        currentHb = ((TinyCard)cards.get(0)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                    {
                        if(hbs.size() > 3)
                        {
                            CInputHelper.setCursor((Hitbox)hbs.get(3));
                            currentHb = (Hitbox)hbs.get(3);
                        }
                    } else
                    {
                        CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(index)).hb);
                        currentHb = ((RunPathElement)runPath.pathElements.get(index)).hb;
                    }
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > runPath.pathElements.size() - 1)
                {
                    if(!relics.isEmpty())
                    {
                        CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
                        currentHb = ((AbstractRelic)relics.get(0)).hb;
                    } else
                    {
                        CInputHelper.setCursor(((TinyCard)cards.get(0)).hb);
                        currentHb = ((TinyCard)cards.get(0)).hb;
                    }
                } else
                {
                    CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(index)).hb);
                    currentHb = ((RunPathElement)runPath.pathElements.get(index)).hb;
                }
                break;

            case 3: // '\003'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if((index -= 15) < 0)
                    {
                        if(!runPath.pathElements.isEmpty())
                        {
                            CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(0)).hb);
                            currentHb = ((RunPathElement)runPath.pathElements.get(0)).hb;
                        } else
                        {
                            CInputHelper.setCursor((Hitbox)hbs.get(0));
                            currentHb = (Hitbox)hbs.get(0);
                            scrollTargetY = 0.0F;
                        }
                    } else
                    {
                        CInputHelper.setCursor(((AbstractRelic)relics.get(index)).hb);
                        currentHb = ((AbstractRelic)relics.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 15) > relics.size() - 1)
                    {
                        if(!cards.isEmpty())
                        {
                            CInputHelper.setCursor(((TinyCard)cards.get(0)).hb);
                            currentHb = ((TinyCard)cards.get(0)).hb;
                        }
                    } else
                    {
                        CInputHelper.setCursor(((AbstractRelic)relics.get(index)).hb);
                        currentHb = ((AbstractRelic)relics.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                    {
                        if(!cards.isEmpty())
                        {
                            CInputHelper.setCursor(((TinyCard)cards.get(0)).hb);
                            currentHb = ((TinyCard)cards.get(0)).hb;
                        }
                        break;
                    }
                    if(!relics.isEmpty())
                    {
                        CInputHelper.setCursor(((AbstractRelic)relics.get(index)).hb);
                        currentHb = ((AbstractRelic)relics.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > relics.size() - 1)
                    {
                        if(!cards.isEmpty())
                        {
                            CInputHelper.setCursor(((TinyCard)cards.get(0)).hb);
                            currentHb = ((TinyCard)cards.get(0)).hb;
                        }
                        break;
                    }
                    if(!relics.isEmpty())
                    {
                        CInputHelper.setCursor(((AbstractRelic)relics.get(index)).hb);
                        currentHb = ((AbstractRelic)relics.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.select.isJustPressed())
                    CardCrawlGame.relicPopup.open((AbstractRelic)relics.get(index), relics);
                break;

            case 4: // '\004'
                int c = ((TinyCard)cards.get(index)).col;
                int r = ((TinyCard)cards.get(index)).row;
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--c < 0)
                    {
                        int i = cards.size() - 1;
                        do
                        {
                            if(i <= 0)
                                break label0;
                            if((((TinyCard)cards.get(i)).col == TinyCard.desiredColumns - 1 || ((TinyCard)cards.get(i)).col == 1) && ((TinyCard)cards.get(i)).row == r)
                            {
                                CInputHelper.setCursor(((TinyCard)cards.get(i)).hb);
                                currentHb = ((TinyCard)cards.get(i)).hb;
                                break label0;
                            }
                            i--;
                        } while(true);
                    }
                    boolean foundNode = false;
                    int i = 0;
                    do
                    {
                        if(i >= cards.size())
                            break;
                        if(((TinyCard)cards.get(i)).col == c && ((TinyCard)cards.get(i)).row == r)
                        {
                            CInputHelper.setCursor(((TinyCard)cards.get(i)).hb);
                            currentHb = ((TinyCard)cards.get(i)).hb;
                            foundNode = true;
                            break;
                        }
                        i++;
                    } while(true);
                    if(foundNode)
                        break;
                    if(!relics.isEmpty())
                    {
                        CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
                        currentHb = ((AbstractRelic)relics.get(0)).hb;
                    } else
                    {
                        CInputHelper.setCursor(runsDropdown.getHitbox());
                        currentHb = runsDropdown.getHitbox();
                    }
                    break;
                }
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++c > TinyCard.desiredColumns - 1)
                    {
                        c = 0;
                        break;
                    }
                    boolean foundNode = false;
                    int i = 0;
                    do
                    {
                        if(i >= cards.size())
                            break;
                        if(((TinyCard)cards.get(i)).col == c && ((TinyCard)cards.get(i)).row == r)
                        {
                            CInputHelper.setCursor(((TinyCard)cards.get(i)).hb);
                            currentHb = ((TinyCard)cards.get(i)).hb;
                            foundNode = true;
                            break;
                        }
                        i++;
                    } while(true);
                    if(foundNode)
                        break;
                    c = 0;
                    i = 0;
                    do
                    {
                        if(i >= cards.size())
                            break label0;
                        if(((TinyCard)cards.get(i)).col == c && ((TinyCard)cards.get(i)).row == r)
                        {
                            CInputHelper.setCursor(((TinyCard)cards.get(i)).hb);
                            currentHb = ((TinyCard)cards.get(i)).hb;
                            break label0;
                        }
                        i++;
                    } while(true);
                }
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if(--index < 0)
                    {
                        if(!relics.isEmpty())
                        {
                            CInputHelper.setCursor(((AbstractRelic)relics.get(relics.size() - 1)).hb);
                            currentHb = ((AbstractRelic)relics.get(relics.size() - 1)).hb;
                            break;
                        }
                        if(!runPath.pathElements.isEmpty())
                        {
                            CInputHelper.setCursor(((RunPathElement)runPath.pathElements.get(0)).hb);
                            currentHb = ((RunPathElement)runPath.pathElements.get(0)).hb;
                        } else
                        {
                            CInputHelper.setCursor(runsDropdown.getHitbox());
                            currentHb = runsDropdown.getHitbox();
                        }
                    } else
                    {
                        CInputHelper.setCursor(((TinyCard)cards.get(index)).hb);
                        currentHb = ((TinyCard)cards.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if(++index <= cards.size() - 1)
                    {
                        CInputHelper.setCursor(((TinyCard)cards.get(index)).hb);
                        currentHb = ((TinyCard)cards.get(index)).hb;
                    }
                    break;
                }
                if(!CInputActionSet.select.isJustPressed())
                    break;
                CardGroup cardGroup = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
                TinyCard addMe;
                for(Iterator iterator4 = cards.iterator(); iterator4.hasNext(); cardGroup.addToTop(addMe.card))
                    addMe = (TinyCard)iterator4.next();

                CardCrawlGame.cardPopup.open(((TinyCard)cards.get(index)).card, cardGroup);
                break;
            }
        }
    }

    public void open()
    {
        currentHb = null;
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.RUN_HISTORY;
        SingleCardViewPopup.enableUpgradeToggle = false;
        refreshData();
        targetX = SHOW_X;
        button.show(TEXT[3]);
        screenUp = true;
        scrollY = scrollLowerBound;
        scrollTargetY = scrollLowerBound;
    }

    public void hide()
    {
        targetX = HIDE_X;
        button.hide();
        screenUp = false;
        currentChar = null;
        CardCrawlGame.mainMenuScreen.panelScreen.refresh();
        SingleCardViewPopup.enableUpgradeToggle = true;
    }

    public void render(SpriteBatch sb)
    {
        renderRunHistoryScreen(sb);
        renderArrows(sb);
        renderFilters(sb);
        button.render(sb);
        renderControllerUi(sb, currentHb);
    }

    private void renderControllerUi(SpriteBatch sb, Hitbox hb)
    {
        if(Settings.isControllerMode && hb != null)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(controllerUiColor);
            sb.draw(ImageMaster.CONTROLLER_HB_HIGHLIGHT, hb.cX - hb.width / 2.0F, hb.cY - hb.height / 2.0F, hb.width, hb.height);
            sb.setBlendFunction(770, 771);
        }
    }

    private String characterText(String chosenCharacter)
    {
        if(chosenCharacter.equals(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.name()))
            return IRONCLAD_NAME;
        if(chosenCharacter.equals(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.name()))
            return SILENT_NAME;
        if(chosenCharacter.equals(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.name()))
            return DEFECT_NAME;
        if(chosenCharacter.equals(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.name()))
            return WATCHER_NAME;
        else
            return chosenCharacter;
    }

    private void renderArrows(SpriteBatch sb)
    {
        if(runIndex < filteredRuns.size() - 1)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.POPUP_ARROW, nextHb.cX - 128F, nextHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, true, false);
            if(nextHb.hovered)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.POPUP_ARROW, nextHb.cX - 128F, nextHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, true, false);
                sb.setBlendFunction(770, 771);
            }
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), nextHb.cX - 32F, nextHb.cY - 32F - 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            nextHb.render(sb);
        }
        if(runIndex > 0)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.POPUP_ARROW, prevHb.cX - 128F, prevHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, false, false);
            if(prevHb.hovered)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.POPUP_ARROW, prevHb.cX - 128F, prevHb.cY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 0.75F, Settings.scale * 0.75F, 0.0F, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(770, 771);
            }
            if(Settings.isControllerMode)
                sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), prevHb.cX - 32F, prevHb.cY - 32F - 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            prevHb.render(sb);
        }
    }

    private void renderRunHistoryScreen(SpriteBatch sb)
    {
        float TOP_POSITION = 1020F;
        if(viewedRun == null)
        {
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, TEXT[4], (float)Settings.WIDTH * 0.43F, (float)Settings.HEIGHT * 0.53F, Settings.GOLD_COLOR);
            return;
        }
        float header1x = screenX + screenPosX(100F);
        float header2x = screenX + screenPosX(120F);
        float yOffset = scrollY + screenPosY(TOP_POSITION);
        String characterName = characterText(viewedRun.character_chosen);
        renderHeader1(sb, characterName, header1x, yOffset);
        float approxCharNameWidth = approximateHeader1Width(characterName);
        if(!seedElement.getText().isEmpty())
        {
            seedElement.render(sb, screenX + 1200F * Settings.scale, yOffset);
            if(!secondSeedElement.getText().isEmpty())
                secondSeedElement.render(sb, 1200F * Settings.scale, yOffset - screenPosY(36F));
        }
        yOffset -= screenPosY(50F);
        String specialModeText = "";
        if(viewedRun.is_daily)
            specialModeText = (new StringBuilder()).append(" (").append(TEXT[27]).append(")").toString();
        else
        if(viewedRun.is_ascension_mode)
            specialModeText = (new StringBuilder()).append(" (").append(TEXT[5]).append(viewedRun.ascension_level).append(")").toString();
        String resultText;
        Color resultTextColor;
        if(viewedRun.victory)
        {
            resultText = (new StringBuilder()).append(TEXT[8]).append(specialModeText).toString();
            resultTextColor = Settings.GREEN_TEXT_COLOR;
        } else
        {
            resultTextColor = Settings.RED_TEXT_COLOR;
            if(viewedRun.killed_by == null)
                resultText = (new StringBuilder()).append(String.format(TEXT[7], new Object[] {
                    Integer.valueOf(viewedRun.floor_reached)
                })).append(specialModeText).toString();
            else
                resultText = (new StringBuilder()).append(String.format(TEXT[6], new Object[] {
                    Integer.valueOf(viewedRun.floor_reached), MonsterHelper.getEncounterName(viewedRun.killed_by)
                })).append(specialModeText).toString();
        }
        renderSubHeading(sb, resultText, header1x, yOffset, resultTextColor);
        if(viewedRun.victory)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.TIMER_ICON, ((header1x + approximateSubHeaderWidth(resultText)) - 32F) + 54F * Settings.scale, yOffset - 32F - 10F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            renderSubHeading(sb, CharStat.formatHMSM(viewedRun.playtime), header1x + approximateSubHeaderWidth(resultText) + 80F * Settings.scale, yOffset, Settings.CREAM_COLOR);
        }
        float scoreLineXOffset = header1x;
        yOffset -= screenPosY(40F);
        String scoreText = String.format(TEXT[22], new Object[] {
            Integer.valueOf(viewedRun.score)
        });
        renderSubHeading(sb, scoreText, header1x, yOffset, Settings.GOLD_COLOR);
        scoreLineXOffset += approximateSubHeaderWidth(scoreText);
        if(modIcons.hasMods())
        {
            modIcons.renderDailyMods(sb, scoreLineXOffset, yOffset);
            scoreLineXOffset += modIcons.approximateWidth();
        }
        yOffset -= screenPosY(18F);
        runPath.render(sb, header2x + 52F * Settings.scale, yOffset);
        yOffset -= runPath.approximateHeight();
        yOffset -= screenPosY(35F);
        float relicBottom = renderRelics(sb, header2x, yOffset);
        yOffset = relicBottom - screenPosY(70F);
        renderDeck(sb, header2x, yOffset);
        runsDropdown.render(sb, header1x + approxCharNameWidth + screenPosX(30F), scrollY + screenPosY(TOP_POSITION + 6F));
    }

    private void renderHeader1(SpriteBatch sb, String text, float x, float y)
    {
        FontHelper.renderSmartText(sb, FontHelper.charTitleFont, text, x, y, 9999F, 36F * Settings.scale, Settings.GOLD_COLOR);
    }

    private float approximateHeader1Width(String text)
    {
        return FontHelper.getSmartWidth(FontHelper.charTitleFont, text, 9999F, 36F * Settings.scale);
    }

    private float approximateSubHeaderWidth(String text)
    {
        return FontHelper.getSmartWidth(FontHelper.buttonLabelFont, text, 9999F, 36F * Settings.scale);
    }

    private void renderSubHeading(SpriteBatch sb, String text, float x, float y, Color color)
    {
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, text, x, y, 9999F, 36F * Settings.scale, color);
    }

    private void renderSubHeadingWithMessage(SpriteBatch sb, String main, String description, float x, float y)
    {
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.buttonLabelFont, main, x, y, Settings.GOLD_COLOR);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.cardDescFont_N, description, x + FontHelper.getSmartWidth(FontHelper.buttonLabelFont, main, 99999F, 0.0F), y - 4F * Settings.scale, Settings.CREAM_COLOR);
    }

    private void renderDeck(SpriteBatch sb, float x, float y)
    {
        layoutTinyCards(cards, screenX + screenPosX(90F), y);
        int cardCount = 0;
        for(Iterator iterator = cards.iterator(); iterator.hasNext();)
        {
            TinyCard card = (TinyCard)iterator.next();
            card.render(sb);
            cardCount += card.count;
        }

        String mainText = String.format(LABEL_WITH_COUNT_IN_PARENS, new Object[] {
            TEXT[9], Integer.valueOf(cardCount)
        });
        renderSubHeadingWithMessage(sb, mainText, cardCountByRarityString, x, y);
    }

    private float renderRelics(SpriteBatch sb, float x, float y)
    {
        String mainText = String.format(LABEL_WITH_COUNT_IN_PARENS, new Object[] {
            TEXT[10], Integer.valueOf(relics.size())
        });
        renderSubHeadingWithMessage(sb, mainText, relicCountByRarityString, x, y);
        int col = 0;
        int row = 0;
        float relicStartX = x + screenPosX(30F) + RELIC_SPACE / 2.0F;
        float relicStartY = y - RELIC_SPACE - screenPosY(10F);
        for(Iterator iterator = relics.iterator(); iterator.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(col == 15)
            {
                col = 0;
                row++;
            }
            r.currentX = relicStartX + RELIC_SPACE * (float)col;
            r.currentY = relicStartY - RELIC_SPACE * (float)row;
            r.hb.move(r.currentX, r.currentY);
            r.render(sb, false, Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
            col++;
        }

        return relicStartY - RELIC_SPACE * (float)row;
    }

    private void handleRelicInteraction()
    {
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            boolean wasScreenUp = AbstractDungeon.isScreenUp;
            AbstractDungeon.isScreenUp = true;
            r.update();
            AbstractDungeon.isScreenUp = wasScreenUp;
            if(r.hb.hovered)
                hoveredRelic = r;
        } while(true);
        if(hoveredRelic != null)
        {
            CardCrawlGame.cursor.changeType(com.megacrit.cardcrawl.core.GameCursor.CursorType.INSPECT);
            if(InputHelper.justClickedLeft)
                clickStartedRelic = hoveredRelic;
            if(InputHelper.justReleasedClickLeft && hoveredRelic == clickStartedRelic)
            {
                CardCrawlGame.relicPopup.open(hoveredRelic, relics);
                clickStartedRelic = null;
            }
        } else
        {
            clickStartedRelic = null;
        }
        hoveredRelic = null;
    }

    private float screenPos(float val)
    {
        return val * Settings.scale;
    }

    private float screenPosX(float val)
    {
        return val * Settings.xScale;
    }

    private float screenPosY(float val)
    {
        return val * Settings.yScale;
    }

    private void updateArrows()
    {
        if(runIndex < filteredRuns.size() - 1)
        {
            nextHb.update();
            if(nextHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            else
            if(nextHb.hovered && InputHelper.justClickedLeft)
            {
                nextHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            } else
            if(nextHb.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed() && !CardCrawlGame.isPopupOpen)
            {
                nextHb.clicked = false;
                runIndex = Math.min(runIndex + 1, filteredRuns.size() - 1);
                reloadWithRunData((RunData)filteredRuns.get(runIndex));
            }
        }
        if(runIndex > 0)
        {
            prevHb.update();
            if(prevHb.justHovered)
                CardCrawlGame.sound.play("UI_HOVER");
            else
            if(prevHb.hovered && InputHelper.justClickedLeft)
            {
                prevHb.clickStarted = true;
                CardCrawlGame.sound.play("UI_CLICK_1");
            } else
            if(prevHb.clicked || CInputActionSet.pageLeftViewDeck.isJustPressed() && !CardCrawlGame.isPopupOpen)
            {
                prevHb.clicked = false;
                runIndex = Math.max(0, runIndex - 1);
                reloadWithRunData((RunData)filteredRuns.get(runIndex));
            }
        }
    }

    private void renderFilters(SpriteBatch sb)
    {
        float filterX = screenX + screenPosX(-270F);
        float winLossY = scrollY + screenPosY(1000F);
        float charY = winLossY - screenPosY(54F);
        float runTypeY = charY - screenPosY(54F);
        runTypeFilter.render(sb, filterX, runTypeY);
        characterFilter.render(sb, filterX, charY);
        winLossFilter.render(sb, filterX, winLossY);
    }

    private void updateScrolling()
    {
        int y = InputHelper.mY;
        if(scrollUpperBound > 0.0F)
            if(!grabbedScreen)
            {
                if(InputHelper.scrolledDown)
                    scrollTargetY += Settings.SCROLL_SPEED;
                else
                if(InputHelper.scrolledUp)
                    scrollTargetY -= Settings.SCROLL_SPEED;
                if(InputHelper.justClickedLeft)
                {
                    grabbedScreen = true;
                    grabStartY = (float)y - scrollTargetY;
                }
            } else
            if(InputHelper.isMouseDown)
                scrollTargetY = (float)y - grabStartY;
            else
                grabbedScreen = false;
        scrollY = MathHelper.scrollSnapLerpSpeed(scrollY, scrollTargetY);
        resetScrolling();
    }

    private void resetScrolling()
    {
        if(scrollTargetY < scrollLowerBound)
            scrollTargetY = MathHelper.scrollSnapLerpSpeed(scrollTargetY, scrollLowerBound);
        else
        if(scrollTargetY > scrollUpperBound)
            scrollTargetY = MathHelper.scrollSnapLerpSpeed(scrollTargetY, scrollUpperBound);
    }

    public void changedSelectionTo(DropdownMenu dropdownMenu, int index, String optionText)
    {
        if(dropdownMenu == runsDropdown)
            runDropdownChangedTo(index);
        else
        if(isFilterDropdown(dropdownMenu))
            resetRunsDropdown();
    }

    private boolean isFilterDropdown(DropdownMenu dropdownMenu)
    {
        return dropdownMenu == winLossFilter || dropdownMenu == characterFilter || dropdownMenu == runTypeFilter;
    }

    private void runDropdownChangedTo(int index)
    {
        if(runIndex == index)
        {
            return;
        } else
        {
            runIndex = index;
            reloadWithRunData((RunData)filteredRuns.get(runIndex));
            return;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/runHistory/RunHistoryScreen.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final com.megacrit.cardcrawl.cards.AbstractCard.CardRarity orderedRarity[];
    private static final com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier orderedRelicRarity[];
    private static final boolean SHOULD_SHOW_PATH = true;
    private static final String IRONCLAD_NAME;
    private static final String SILENT_NAME;
    private static final String DEFECT_NAME;
    private static final String WATCHER_NAME;
    private static final String ALL_CHARACTERS_TEXT;
    private static final String WINS_AND_LOSSES_TEXT;
    private static final String WINS_TEXT;
    private static final String LOSSES_TEXT;
    private static final String RUN_TYPE_ALL;
    private static final String RUN_TYPE_NORMAL;
    private static final String RUN_TYPE_ASCENSION;
    private static final String RUN_TYPE_DAILY;
    private static final String RARITY_LABEL_STARTER;
    private static final String RARITY_LABEL_COMMON;
    private static final String RARITY_LABEL_UNCOMMON;
    private static final String RARITY_LABEL_RARE;
    private static final String RARITY_LABEL_SPECIAL;
    private static final String RARITY_LABEL_CURSE;
    private static final String RARITY_LABEL_BOSS;
    private static final String RARITY_LABEL_SHOP;
    private static final String RARITY_LABEL_UNKNOWN;
    private static final String COUNT_WITH_LABEL;
    private static final String LABEL_WITH_COUNT_IN_PARENS;
    private static final String SEED_LABEL;
    private static final String CUSTOM_SEED_LABEL;
    public MenuCancelButton button;
    private static Gson gson = new Gson();
    private ArrayList unfilteredRuns;
    private ArrayList filteredRuns;
    private int runIndex;
    private RunData viewedRun;
    public boolean screenUp;
    public com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass currentChar;
    private static final float SHOW_X;
    private static final float HIDE_X;
    private float screenX;
    private float targetX;
    private RunHistoryPath runPath;
    private ModIcons modIcons;
    private CopyableTextElement seedElement;
    private CopyableTextElement secondSeedElement;
    private boolean grabbedScreen;
    private float grabStartY;
    private float scrollTargetY;
    private float scrollY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private DropdownMenu characterFilter;
    private DropdownMenu winLossFilter;
    private DropdownMenu runTypeFilter;
    private Hitbox prevHb;
    private Hitbox nextHb;
    private ArrayList relics;
    private ArrayList cards;
    private String cardCountByRarityString;
    private String relicCountByRarityString;
    private int circletCount;
    private DropdownMenu runsDropdown;
    private static final float ARROW_SIDE_PADDING = 180F;
    private Hitbox currentHb;
    private static final float RELIC_SPACE;
    private Color controllerUiColor;
    AbstractRelic hoveredRelic;
    AbstractRelic clickStartedRelic;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RunHistoryScreen");
        TEXT = uiStrings.TEXT;
        orderedRarity = (new com.megacrit.cardcrawl.cards.AbstractCard.CardRarity[] {
            com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.SPECIAL, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.BASIC, com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.CURSE
        });
        orderedRelicRarity = (new com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[] {
            com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.BOSS, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SPECIAL, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.SHOP, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.STARTER, com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.DEPRECATED
        });
        IRONCLAD_NAME = TEXT[0];
        SILENT_NAME = TEXT[1];
        DEFECT_NAME = TEXT[2];
        WATCHER_NAME = TEXT[35];
        ALL_CHARACTERS_TEXT = TEXT[23];
        WINS_AND_LOSSES_TEXT = TEXT[24];
        WINS_TEXT = TEXT[25];
        LOSSES_TEXT = TEXT[26];
        RUN_TYPE_ALL = TEXT[28];
        RUN_TYPE_NORMAL = TEXT[29];
        RUN_TYPE_ASCENSION = TEXT[30];
        RUN_TYPE_DAILY = TEXT[31];
        RARITY_LABEL_STARTER = TEXT[11];
        RARITY_LABEL_COMMON = TEXT[12];
        RARITY_LABEL_UNCOMMON = TEXT[13];
        RARITY_LABEL_RARE = TEXT[14];
        RARITY_LABEL_SPECIAL = TEXT[15];
        RARITY_LABEL_CURSE = TEXT[16];
        RARITY_LABEL_BOSS = TEXT[17];
        RARITY_LABEL_SHOP = TEXT[18];
        RARITY_LABEL_UNKNOWN = TEXT[19];
        COUNT_WITH_LABEL = TEXT[20];
        LABEL_WITH_COUNT_IN_PARENS = TEXT[21];
        SEED_LABEL = TEXT[32];
        CUSTOM_SEED_LABEL = TEXT[33];
        SHOW_X = 300F * Settings.xScale;
        HIDE_X = -800F * Settings.xScale;
        RELIC_SPACE = 64F * Settings.scale;
    }
}
