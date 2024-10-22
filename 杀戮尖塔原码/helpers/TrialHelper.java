// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TrialHelper.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.trials.*;
import java.util.HashMap;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            SeedHelper

public class TrialHelper
{
    private static final class TRIAL extends Enum
    {

        public static TRIAL[] values()
        {
            return (TRIAL[])$VALUES.clone();
        }

        public static TRIAL valueOf(String name)
        {
            return (TRIAL)Enum.valueOf(com/megacrit/cardcrawl/helpers/TrialHelper$TRIAL, name);
        }

        public static final TRIAL RANDOM_MODS;
        public static final TRIAL NO_CARD_DROPS;
        public static final TRIAL UNCEASING_TOP;
        public static final TRIAL LOSE_MAX_HP;
        public static final TRIAL SNECKO;
        public static final TRIAL SLOW;
        public static final TRIAL FORMS;
        public static final TRIAL DRAFT;
        public static final TRIAL MEGA_DRAFT;
        public static final TRIAL ONE_HP;
        public static final TRIAL MORE_CARDS;
        public static final TRIAL CURSED;
        private static final TRIAL $VALUES[];

        static 
        {
            RANDOM_MODS = new TRIAL("RANDOM_MODS", 0);
            NO_CARD_DROPS = new TRIAL("NO_CARD_DROPS", 1);
            UNCEASING_TOP = new TRIAL("UNCEASING_TOP", 2);
            LOSE_MAX_HP = new TRIAL("LOSE_MAX_HP", 3);
            SNECKO = new TRIAL("SNECKO", 4);
            SLOW = new TRIAL("SLOW", 5);
            FORMS = new TRIAL("FORMS", 6);
            DRAFT = new TRIAL("DRAFT", 7);
            MEGA_DRAFT = new TRIAL("MEGA_DRAFT", 8);
            ONE_HP = new TRIAL("ONE_HP", 9);
            MORE_CARDS = new TRIAL("MORE_CARDS", 10);
            CURSED = new TRIAL("CURSED", 11);
            $VALUES = (new TRIAL[] {
                RANDOM_MODS, NO_CARD_DROPS, UNCEASING_TOP, LOSE_MAX_HP, SNECKO, SLOW, FORMS, DRAFT, MEGA_DRAFT, ONE_HP, 
                MORE_CARDS, CURSED
            });
        }

        private TRIAL(String s, int i)
        {
            super(s, i);
        }
    }


    public TrialHelper()
    {
    }

    private static void initialize()
    {
        if(trialKeysMap != null)
        {
            return;
        } else
        {
            trialKeysMap = new HashMap();
            trialKeysMap.put(formatKey("RandomMods"), TRIAL.RANDOM_MODS);
            trialKeysMap.put(formatKey("DailyMods"), TRIAL.RANDOM_MODS);
            trialKeysMap.put(formatKey("StarterDeck"), TRIAL.NO_CARD_DROPS);
            trialKeysMap.put(formatKey("Inception"), TRIAL.UNCEASING_TOP);
            trialKeysMap.put(formatKey("FadeAway"), TRIAL.LOSE_MAX_HP);
            trialKeysMap.put(formatKey("PraiseSnecko"), TRIAL.SNECKO);
            trialKeysMap.put(formatKey("YoureTooSlow"), TRIAL.SLOW);
            trialKeysMap.put(formatKey("MyTrueForm"), TRIAL.FORMS);
            trialKeysMap.put(formatKey("Draft"), TRIAL.DRAFT);
            trialKeysMap.put(formatKey("MegaDraft"), TRIAL.MEGA_DRAFT);
            trialKeysMap.put(formatKey("1HitWonder"), TRIAL.ONE_HP);
            trialKeysMap.put(formatKey("MoreCards"), TRIAL.MORE_CARDS);
            trialKeysMap.put(formatKey("Cursed"), TRIAL.CURSED);
            return;
        }
    }

    private static String formatKey(String key)
    {
        return SeedHelper.sterilizeString(key);
    }

    public static boolean isTrialSeed(String seed)
    {
        initialize();
        return trialKeysMap.containsKey(seed);
    }

    public static AbstractTrial getTrialForSeed(String seed)
    {
        initialize();
        if(seed == null)
            return null;
        TRIAL picked = (TRIAL)trialKeysMap.get(seed);
        if(picked == null)
            return null;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL = new int[TRIAL.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.RANDOM_MODS.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.NO_CARD_DROPS.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.UNCEASING_TOP.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.LOSE_MAX_HP.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.SNECKO.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.SLOW.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.FORMS.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.DRAFT.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.MEGA_DRAFT.ordinal()] = 9;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.ONE_HP.ordinal()] = 10;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.MORE_CARDS.ordinal()] = 11;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$helpers$TrialHelper$TRIAL[TRIAL.CURSED.ordinal()] = 12;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.helpers.TrialHelper.TRIAL[picked.ordinal()])
        {
        case 1: // '\001'
            return new RandomModsTrial();

        case 2: // '\002'
            return new StarterDeckTrial();

        case 3: // '\003'
            return new InceptionTrial();

        case 4: // '\004'
            return new LoseMaxHpTrial();

        case 5: // '\005'
            return new SneckoTrial();

        case 6: // '\006'
            return new SlowpokeTrial();

        case 7: // '\007'
            return new MyTrueFormTrial();

        case 8: // '\b'
            return new DraftTrial();

        case 9: // '\t'
            return new AnyColorDraftTrial();

        case 10: // '\n'
            return new OneHpTrial();

        case 11: // '\013'
            return new HoarderTrial();

        case 12: // '\f'
            return new CursedTrial();
        }
        return null;
    }

    private static HashMap trialKeysMap;
}
