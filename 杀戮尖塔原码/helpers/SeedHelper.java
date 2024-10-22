// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SeedHelper.java

package com.megacrit.cardcrawl.helpers;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.random.Random;
import java.io.PrintStream;
import java.math.BigInteger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            BadWordChecker, TrialHelper

public class SeedHelper
{

    public SeedHelper()
    {
    }

    public static void setSeed(String seedStr)
    {
        if(seedStr.isEmpty())
        {
            Settings.seedSet = false;
            Settings.seed = null;
            Settings.specialSeed = null;
        } else
        {
            long seed = getLong(seedStr);
            Settings.seedSet = true;
            Settings.seed = Long.valueOf(seed);
            Settings.specialSeed = null;
            Settings.isDailyRun = false;
            cachedSeed = null;
        }
    }

    public static String getUserFacingSeedString()
    {
        if(Settings.seed != null)
        {
            if(cachedSeed == null)
                cachedSeed = getString(Settings.seed.longValue());
            return cachedSeed;
        } else
        {
            return "";
        }
    }

    public static String getValidCharacter(String character, String textSoFar)
    {
        character = character.toUpperCase();
        if(character.equals("O"))
            character = "0";
        boolean isValid = "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ".contains(character);
        if(isValid)
            return character;
        else
            return null;
    }

    public static String sterilizeString(String raw)
    {
        raw = raw.trim().toUpperCase();
        String pattern = "([A-Z]*[0-9]*)*";
        if(raw.matches("([A-Z]*[0-9]*)*"))
            return raw.replace("O", "0");
        else
            return "";
    }

    public static String getString(long seed)
    {
        StringBuilder bldr = new StringBuilder();
        BigInteger leftover = new BigInteger(Long.toUnsignedString(seed));
        BigInteger charCount = BigInteger.valueOf("0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ".length());
        char c;
        for(; !leftover.equals(BigInteger.ZERO); bldr.insert(0, c))
        {
            BigInteger remainder = leftover.remainder(charCount);
            leftover = leftover.divide(charCount);
            int charIndex = remainder.intValue();
            c = "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ".charAt(charIndex);
        }

        return bldr.toString();
    }

    public static long getLong(String seedStr)
    {
        long total = 0L;
        seedStr = seedStr.toUpperCase().replaceAll("O", "0");
        for(int i = 0; i < seedStr.length(); i++)
        {
            char toFind = seedStr.charAt(i);
            int remainder = "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ".indexOf(toFind);
            if(remainder == -1)
                System.out.println((new StringBuilder()).append("Character in seed is invalid: ").append(toFind).toString());
            total *= "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ".length();
            total += remainder;
        }

        return total;
    }

    public static long generateUnoffensiveSeed(Random rng)
    {
        String safeString;
        long possible;
        for(safeString = "fuck"; BadWordChecker.containsBadWord(safeString) || TrialHelper.isTrialSeed(safeString); safeString = getString(possible))
            possible = rng.randomLong();

        return getLong(safeString);
    }

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";
    public static String cachedSeed = null;
    public static final int SEED_DEFAULT_LENGTH = getString(0x8000000000000000L).length();

}
