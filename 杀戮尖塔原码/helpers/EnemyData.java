// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnemyData.java

package com.megacrit.cardcrawl.helpers;


// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            GameDataStringBuilder

public class EnemyData
{
    public static final class MonsterType extends Enum
    {

        public static MonsterType[] values()
        {
            return (MonsterType[])$VALUES.clone();
        }

        public static MonsterType valueOf(String name)
        {
            return (MonsterType)Enum.valueOf(com/megacrit/cardcrawl/helpers/EnemyData$MonsterType, name);
        }

        public static final MonsterType WEAK;
        public static final MonsterType STRONG;
        public static final MonsterType ELITE;
        public static final MonsterType BOSS;
        public static final MonsterType EVENT;
        private static final MonsterType $VALUES[];

        static 
        {
            WEAK = new MonsterType("WEAK", 0);
            STRONG = new MonsterType("STRONG", 1);
            ELITE = new MonsterType("ELITE", 2);
            BOSS = new MonsterType("BOSS", 3);
            EVENT = new MonsterType("EVENT", 4);
            $VALUES = (new MonsterType[] {
                WEAK, STRONG, ELITE, BOSS, EVENT
            });
        }

        private MonsterType(String s, int i)
        {
            super(s, i);
        }
    }


    public EnemyData(String key, int level, MonsterType type)
    {
        name = key;
        this.level = level;
        this.type = type;
    }

    public static String gameDataUploadHeader()
    {
        GameDataStringBuilder builder = new GameDataStringBuilder();
        builder.addFieldData("name");
        builder.addFieldData("level");
        builder.addFieldData("type");
        return builder.toString();
    }

    public String gameDataUploadData()
    {
        GameDataStringBuilder builder = new GameDataStringBuilder();
        builder.addFieldData(name);
        builder.addFieldData(level);
        builder.addFieldData(type.name());
        return builder.toString();
    }

    public String name;
    public int level;
    public MonsterType type;
}
