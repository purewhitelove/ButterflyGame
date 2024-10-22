// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Random.java

package com.megacrit.cardcrawl.random;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Random
{

    public Random()
    {
        this(Long.valueOf(MathUtils.random(9999L)), MathUtils.random(99));
    }

    public Random(Long seed)
    {
        counter = 0;
        random = new RandomXS128(seed.longValue());
    }

    public Random(Long seed, int counter)
    {
        this.counter = 0;
        random = new RandomXS128(seed.longValue());
        for(int i = 0; i < counter; i++)
            random(999);

    }

    public Random copy()
    {
        Random copied = new Random();
        copied.random = new RandomXS128(random.getState(0), random.getState(1));
        copied.counter = counter;
        return copied;
    }

    public void setCounter(int targetCounter)
    {
        if(counter < targetCounter)
        {
            int count = targetCounter - counter;
            for(int i = 0; i < count; i++)
                randomBoolean();

        } else
        {
            logger.info("ERROR: Counter is already higher than target counter!");
        }
    }

    public int random(int range)
    {
        counter++;
        return random.nextInt(range + 1);
    }

    public int random(int start, int end)
    {
        counter++;
        return start + random.nextInt((end - start) + 1);
    }

    public long random(long range)
    {
        counter++;
        return (long)(random.nextDouble() * (double)range);
    }

    public long random(long start, long end)
    {
        counter++;
        return start + (long)(random.nextDouble() * (double)(end - start));
    }

    public long randomLong()
    {
        counter++;
        return random.nextLong();
    }

    public boolean randomBoolean()
    {
        counter++;
        return random.nextBoolean();
    }

    public boolean randomBoolean(float chance)
    {
        counter++;
        return random.nextFloat() < chance;
    }

    public float random()
    {
        counter++;
        return random.nextFloat();
    }

    public float random(float range)
    {
        counter++;
        return random.nextFloat() * range;
    }

    public float random(float start, float end)
    {
        counter++;
        return start + random.nextFloat() * (end - start);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/random/Random.getName());
    public RandomXS128 random;
    public int counter;

}
