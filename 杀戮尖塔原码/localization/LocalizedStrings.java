// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalizedStrings.java

package com.megacrit.cardcrawl.localization;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.Settings;
import java.io.File;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.localization:
//            PowerStrings, MonsterStrings, EventStrings, PotionStrings, 
//            CreditStrings, TutorialStrings, KeywordStrings, CharacterStrings, 
//            UIStrings, OrbStrings, StanceStrings, RunModStrings, 
//            BlightStrings, ScoreBonusStrings, AchievementStrings, CardStrings, 
//            RelicStrings

public class LocalizedStrings
{

    public LocalizedStrings()
    {
        long startTime = System.currentTimeMillis();
        Gson gson = new Gson();
        static class _cls18
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage = new int[com.megacrit.cardcrawl.core.Settings.GameLanguage.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.ENG.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.DUT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.EPO.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.PTB.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.ZHT.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.FIN.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.FRA.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.DEU.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.GRE.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.IND.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.ITA.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.JPN.ordinal()] = 13;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.KOR.ordinal()] = 14;
                }
                catch(NoSuchFieldError nosuchfielderror13) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.NOR.ordinal()] = 15;
                }
                catch(NoSuchFieldError nosuchfielderror14) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.POL.ordinal()] = 16;
                }
                catch(NoSuchFieldError nosuchfielderror15) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.RUS.ordinal()] = 17;
                }
                catch(NoSuchFieldError nosuchfielderror16) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.SPA.ordinal()] = 18;
                }
                catch(NoSuchFieldError nosuchfielderror17) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.SRP.ordinal()] = 19;
                }
                catch(NoSuchFieldError nosuchfielderror18) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.SRB.ordinal()] = 20;
                }
                catch(NoSuchFieldError nosuchfielderror19) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.THA.ordinal()] = 21;
                }
                catch(NoSuchFieldError nosuchfielderror20) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.TUR.ordinal()] = 22;
                }
                catch(NoSuchFieldError nosuchfielderror21) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.UKR.ordinal()] = 23;
                }
                catch(NoSuchFieldError nosuchfielderror22) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.VIE.ordinal()] = 24;
                }
                catch(NoSuchFieldError nosuchfielderror23) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$core$Settings$GameLanguage[com.megacrit.cardcrawl.core.Settings.GameLanguage.WWW.ordinal()] = 25;
                }
                catch(NoSuchFieldError nosuchfielderror24) { }
            }
        }

        String langPackDir;
        switch(_cls18..SwitchMap.com.megacrit.cardcrawl.core.Settings.GameLanguage[Settings.language.ordinal()])
        {
        case 1: // '\001'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("eng").toString();
            break;

        case 2: // '\002'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("dut").toString();
            break;

        case 3: // '\003'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("epo").toString();
            break;

        case 4: // '\004'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("ptb").toString();
            break;

        case 5: // '\005'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("zhs").toString();
            break;

        case 6: // '\006'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("zht").toString();
            break;

        case 7: // '\007'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("fin").toString();
            break;

        case 8: // '\b'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("fra").toString();
            break;

        case 9: // '\t'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("deu").toString();
            break;

        case 10: // '\n'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("gre").toString();
            break;

        case 11: // '\013'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("ind").toString();
            break;

        case 12: // '\f'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("ita").toString();
            break;

        case 13: // '\r'
            if(Settings.isConsoleBuild)
                langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("jpn").toString();
            else
                langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("jpn2").toString();
            break;

        case 14: // '\016'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("kor").toString();
            break;

        case 15: // '\017'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("nor").toString();
            break;

        case 16: // '\020'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("pol").toString();
            break;

        case 17: // '\021'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("rus").toString();
            break;

        case 18: // '\022'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("spa").toString();
            break;

        case 19: // '\023'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("srp").toString();
            break;

        case 20: // '\024'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("srb").toString();
            break;

        case 21: // '\025'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("tha").toString();
            break;

        case 22: // '\026'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("tur").toString();
            break;

        case 23: // '\027'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("ukr").toString();
            break;

        case 24: // '\030'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("vie").toString();
            break;

        case 25: // '\031'
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("www").toString();
            break;

        default:
            langPackDir = (new StringBuilder()).append("localization").append(File.separator).append("www").toString();
            break;
        }
        String monsterPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("monsters.json").toString();
        Type monstersType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        monsters = (Map)gson.fromJson(loadJson(monsterPath), monstersType);
        String powerPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("powers.json").toString();
        Type powerType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        powers = (Map)gson.fromJson(loadJson(powerPath), powerType);
        String cardPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("cards.json").toString();
        Type cardType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        cards = (Map)gson.fromJson(loadJson(cardPath), cardType);
        String relicPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("relics.json").toString();
        Type relicType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        relics = (Map)gson.fromJson(loadJson(relicPath), relicType);
        String eventPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("events.json").toString();
        Type eventType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        events = (Map)gson.fromJson(loadJson(eventPath), eventType);
        String potionPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("potions.json").toString();
        Type potionType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        potions = (Map)gson.fromJson(loadJson(potionPath), potionType);
        String creditPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("credits.json").toString();
        Type creditType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        credits = (Map)gson.fromJson(loadJson(creditPath), creditType);
        String tutorialsPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("tutorials.json").toString();
        Type tutorialType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        tutorials = (Map)gson.fromJson(loadJson(tutorialsPath), tutorialType);
        String keywordsPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("keywords.json").toString();
        Type keywordsType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        keywords = (Map)gson.fromJson(loadJson(keywordsPath), keywordsType);
        String scoreBonusesPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("score_bonuses.json").toString();
        Type scoreBonusType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        scoreBonuses = (Map)gson.fromJson(loadJson(scoreBonusesPath), scoreBonusType);
        String characterPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("characters.json").toString();
        Type characterType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        characters = (Map)gson.fromJson(loadJson(characterPath), characterType);
        String uiPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("ui.json").toString();
        Type uiType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        ui = (Map)gson.fromJson(loadJson(uiPath), uiType);
        PERIOD = getUIString("Period").TEXT[0];
        String orbPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("orbs.json").toString();
        Type orbType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        orb = (Map)gson.fromJson(loadJson(orbPath), orbType);
        String stancePath = (new StringBuilder()).append(langPackDir).append(File.separator).append("stances.json").toString();
        Type stanceType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        stance = (Map)gson.fromJson(loadJson(stancePath), stanceType);
        String modPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("run_mods.json").toString();
        Type modType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        mod = (Map)gson.fromJson(loadJson(modPath), modType);
        String blightPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("blights.json").toString();
        Type blightType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        blights = (Map)gson.fromJson(loadJson(blightPath), blightType);
        String achievePath = (new StringBuilder()).append(langPackDir).append(File.separator).append("achievements.json").toString();
        Type achieveType = (new TypeToken() {

            final LocalizedStrings this$0;

            
            {
                this.this$0 = LocalizedStrings.this;
                super();
            }
        }
).getType();
        achievements = (Map)gson.fromJson(loadJson(achievePath), achieveType);
        String lineBreakPath = (new StringBuilder()).append(langPackDir).append(File.separator).append("line_break.json").toString();
        if(Gdx.files.internal(lineBreakPath).exists())
            break_chars = Gdx.files.internal(lineBreakPath).readString(String.valueOf(StandardCharsets.UTF_8));
        logger.info((new StringBuilder()).append("Loc Strings load time: ").append(System.currentTimeMillis() - startTime).append("ms").toString());
    }

    public PowerStrings getPowerStrings(String powerName)
    {
        if(powers.containsKey(powerName))
        {
            return (PowerStrings)powers.get(powerName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] PowerString: ").append(powerName).append(" not found").toString());
            return PowerStrings.getMockPowerString();
        }
    }

    public MonsterStrings getMonsterStrings(String monsterName)
    {
        if(monsters.containsKey(monsterName))
        {
            return (MonsterStrings)monsters.get(monsterName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] MonsterString: ").append(monsterName).append(" not found").toString());
            return MonsterStrings.getMockMonsterString();
        }
    }

    public EventStrings getEventString(String eventName)
    {
        if(events.containsKey(eventName))
        {
            return (EventStrings)events.get(eventName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] EventString: ").append(eventName).append(" not found").toString());
            return EventStrings.getMockEventString();
        }
    }

    public PotionStrings getPotionString(String potionName)
    {
        if(potions.containsKey(potionName))
        {
            return (PotionStrings)potions.get(potionName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] PotionString: ").append(potionName).append(" not found").toString());
            return PotionStrings.getMockPotionString();
        }
    }

    public CreditStrings getCreditString(String creditName)
    {
        if(credits.containsKey(creditName))
        {
            return (CreditStrings)credits.get(creditName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] CreditString: ").append(creditName).append(" not found").toString());
            return CreditStrings.getMockCreditString();
        }
    }

    public TutorialStrings getTutorialString(String tutorialName)
    {
        if(tutorials.containsKey(tutorialName))
        {
            return (TutorialStrings)tutorials.get(tutorialName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] TutorialString: ").append(tutorialName).append(" not found").toString());
            return TutorialStrings.getMockTutorialString();
        }
    }

    public KeywordStrings getKeywordString(String keywordName)
    {
        return (KeywordStrings)keywords.get(keywordName);
    }

    public CharacterStrings getCharacterString(String characterName)
    {
        return (CharacterStrings)characters.get(characterName);
    }

    public UIStrings getUIString(String uiName)
    {
        return (UIStrings)ui.get(uiName);
    }

    public OrbStrings getOrbString(String orbName)
    {
        if(orb.containsKey(orbName))
        {
            return (OrbStrings)orb.get(orbName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] OrbStrings: ").append(orbName).append(" not found").toString());
            return OrbStrings.getMockOrbString();
        }
    }

    public StanceStrings getStanceString(String stanceName)
    {
        return (StanceStrings)stance.get(stanceName);
    }

    public RunModStrings getRunModString(String modName)
    {
        if(mod.containsKey(modName))
        {
            return (RunModStrings)mod.get(modName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] RunModStrings: ").append(modName).append(" not found").toString());
            return RunModStrings.getMockModString();
        }
    }

    public BlightStrings getBlightString(String blightName)
    {
        if(blights.containsKey(blightName))
        {
            return (BlightStrings)blights.get(blightName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] BlightStrings: ").append(blightName).append(" not found").toString());
            return BlightStrings.getBlightOrbString();
        }
    }

    public ScoreBonusStrings getScoreString(String scoreName)
    {
        if(scoreBonuses.containsKey(scoreName))
        {
            return (ScoreBonusStrings)scoreBonuses.get(scoreName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] ScoreBonusStrings: ").append(scoreName).append(" not found").toString());
            return ScoreBonusStrings.getScoreBonusString();
        }
    }

    public AchievementStrings getAchievementString(String achievementName)
    {
        return (AchievementStrings)achievements.get(achievementName);
    }

    public CardStrings getCardStrings(String cardName)
    {
        if(cards.containsKey(cardName))
        {
            return (CardStrings)cards.get(cardName);
        } else
        {
            logger.info((new StringBuilder()).append("[ERROR] CardString: ").append(cardName).append(" not found").toString());
            return CardStrings.getMockCardString();
        }
    }

    public static String[] createMockStringArray(int size)
    {
        String retVal[] = new String[size];
        for(int i = 0; i < retVal.length; i++)
            retVal[i] = (new StringBuilder()).append("[MISSING_").append(i).append("]").toString();

        return retVal;
    }

    public RelicStrings getRelicStrings(String relicName)
    {
        return (RelicStrings)relics.get(relicName);
    }

    private static String loadJson(String jsonPath)
    {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/localization/LocalizedStrings.getName());
    private static final String LOCALIZATION_DIR = "localization";
    public static String PERIOD = null;
    private static Map monsters;
    private static Map powers;
    private static Map cards;
    private static Map relics;
    private static Map events;
    private static Map potions;
    private static Map credits;
    private static Map tutorials;
    private static Map keywords;
    private static Map scoreBonuses;
    private static Map characters;
    private static Map ui;
    private static Map orb;
    private static Map stance;
    public static Map mod;
    private static Map blights;
    private static Map achievements;
    public static String break_chars = null;

}
