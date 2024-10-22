// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonsterHelper.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MysteriousSphere;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.BotDataUploader;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.beyond.*;
import com.megacrit.cardcrawl.monsters.city.*;
import com.megacrit.cardcrawl.monsters.ending.*;
import com.megacrit.cardcrawl.monsters.exordium.*;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.helpers:
//            EnemyData

public class MonsterHelper
{

    public MonsterHelper()
    {
    }

    public static String getEncounterName(String key)
    {
        if(key == null)
            return "";
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -52065857: 
            if(s.equals("Flame Bruiser 1 Orb"))
                byte0 = 0;
            break;

        case -51142336: 
            if(s.equals("Flame Bruiser 2 Orb"))
                byte0 = 1;
            break;

        case 563756501: 
            if(s.equals("Slaver and Parasite"))
                byte0 = 2;
            break;

        case -222004000: 
            if(s.equals("Snecko and Mystics"))
                byte0 = 3;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
        case 1: // '\001'
            return MIXED_COMBAT_NAMES[25];

        case 2: // '\002'
            return MIXED_COMBAT_NAMES[26];

        case 3: // '\003'
            return MIXED_COMBAT_NAMES[27];
        }
        s = key;
        byte0 = -1;
        switch(s.hashCode())
        {
        case 1637395457: 
            if(s.equals("Blue Slaver"))
                byte0 = 0;
            break;

        case -1508851536: 
            if(s.equals("Cultist"))
                byte0 = 1;
            break;

        case -548386477: 
            if(s.equals("Jaw Worm"))
                byte0 = 2;
            break;

        case -2013219467: 
            if(s.equals("Looter"))
                byte0 = 3;
            break;

        case 893410389: 
            if(s.equals("Gremlin Gang"))
                byte0 = 4;
            break;

        case -2096825302: 
            if(s.equals("Red Slaver"))
                byte0 = 5;
            break;

        case -1342165661: 
            if(s.equals("Large Slime"))
                byte0 = 6;
            break;

        case -902890624: 
            if(s.equals("Exordium Thugs"))
                byte0 = 7;
            break;

        case -1368941933: 
            if(s.equals("Exordium Wildlife"))
                byte0 = 8;
            break;

        case -992209193: 
            if(s.equals("3 Louse"))
                byte0 = 9;
            break;

        case -1879712874: 
            if(s.equals("2 Louse"))
                byte0 = 10;
            break;

        case 1650599105: 
            if(s.equals("2 Fungi Beasts"))
                byte0 = 11;
            break;

        case 1057095158: 
            if(s.equals("Lots of Slimes"))
                byte0 = 12;
            break;

        case 70731812: 
            if(s.equals("Small Slimes"))
                byte0 = 13;
            break;

        case -663909825: 
            if(s.equals("Gremlin Nob"))
                byte0 = 14;
            break;

        case 1434486691: 
            if(s.equals("Lagavulin"))
                byte0 = 15;
            break;

        case -2108458454: 
            if(s.equals("3 Sentries"))
                byte0 = 16;
            break;

        case -2013947971: 
            if(s.equals("Lagavulin Event"))
                byte0 = 17;
            break;

        case -380917097: 
            if(s.equals("The Mushroom Lair"))
                byte0 = 18;
            break;

        case -1788706656: 
            if(s.equals("The Guardian"))
                byte0 = 19;
            break;

        case 644100489: 
            if(s.equals("Hexaghost"))
                byte0 = 20;
            break;

        case -1873987067: 
            if(s.equals("Slime Boss"))
                byte0 = 21;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return SlaverBlue.NAME;

        case 1: // '\001'
            return Cultist.NAME;

        case 2: // '\002'
            return JawWorm.NAME;

        case 3: // '\003'
            return Looter.NAME;

        case 4: // '\004'
            return MIXED_COMBAT_NAMES[0];

        case 5: // '\005'
            return SlaverRed.NAME;

        case 6: // '\006'
            return MIXED_COMBAT_NAMES[1];

        case 7: // '\007'
            return MIXED_COMBAT_NAMES[2];

        case 8: // '\b'
            return MIXED_COMBAT_NAMES[3];

        case 9: // '\t'
            return LouseNormal.NAME;

        case 10: // '\n'
            return LouseNormal.NAME;

        case 11: // '\013'
            return FungiBeast.NAME;

        case 12: // '\f'
            return MIXED_COMBAT_NAMES[4];

        case 13: // '\r'
            return MIXED_COMBAT_NAMES[5];

        case 14: // '\016'
            return GremlinNob.NAME;

        case 15: // '\017'
            return Lagavulin.NAME;

        case 16: // '\020'
            return MIXED_COMBAT_NAMES[23];

        case 17: // '\021'
            return Lagavulin.NAME;

        case 18: // '\022'
            return FungiBeast.NAME;

        case 19: // '\023'
            return TheGuardian.NAME;

        case 20: // '\024'
            return Hexaghost.NAME;

        case 21: // '\025'
            return SlimeBoss.NAME;
        }
        s = key;
        byte0 = -1;
        switch(s.hashCode())
        {
        case 75370758: 
            if(s.equals("2 Thieves"))
                byte0 = 0;
            break;

        case -1001149827: 
            if(s.equals("3 Byrds"))
                byte0 = 1;
            break;

        case -113646146: 
            if(s.equals("4 Byrds"))
                byte0 = 2;
            break;

        case 2017619858: 
            if(s.equals("Chosen"))
                byte0 = 3;
            break;

        case -621937833: 
            if(s.equals("Shell Parasite"))
                byte0 = 4;
            break;

        case 1989842815: 
            if(s.equals("Spheric Guardian"))
                byte0 = 5;
            break;

        case 15349611: 
            if(s.equals("Cultist and Chosen"))
                byte0 = 6;
            break;

        case 1328987920: 
            if(s.equals("3 Cultists"))
                byte0 = 7;
            break;

        case -537649645: 
            if(s.equals("Chosen and Byrds"))
                byte0 = 8;
            break;

        case -1706901225: 
            if(s.equals("Sentry and Sphere"))
                byte0 = 9;
            break;

        case -255148213: 
            if(s.equals("Snake Plant"))
                byte0 = 10;
            break;

        case -1814052995: 
            if(s.equals("Snecko"))
                byte0 = 11;
            break;

        case -757040165: 
            if(s.equals("Centurion and Healer"))
                byte0 = 12;
            break;

        case 813978224: 
            if(s.equals("Shelled Parasite and Fungi"))
                byte0 = 13;
            break;

        case 1917396788: 
            if(s.equals("Book of Stabbing"))
                byte0 = 14;
            break;

        case -279622453: 
            if(s.equals("Gremlin Leader"))
                byte0 = 15;
            break;

        case -461459912: 
            if(s.equals("Slavers"))
                byte0 = 16;
            break;

        case -1766312002: 
            if(s.equals("Masked Bandits"))
                byte0 = 17;
            break;

        case -1834311388: 
            if(s.equals("Colosseum Nobs"))
                byte0 = 18;
            break;

        case -1045722682: 
            if(s.equals("Colosseum Slavers"))
                byte0 = 19;
            break;

        case -617327920: 
            if(s.equals("Automaton"))
                byte0 = 20;
            break;

        case 65070879: 
            if(s.equals("Champ"))
                byte0 = 21;
            break;

        case -407507859: 
            if(s.equals("Collector"))
                byte0 = 22;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return MIXED_COMBAT_NAMES[6];

        case 1: // '\001'
            return MIXED_COMBAT_NAMES[7];

        case 2: // '\002'
            return MIXED_COMBAT_NAMES[8];

        case 3: // '\003'
            return Chosen.NAME;

        case 4: // '\004'
            return ShelledParasite.NAME;

        case 5: // '\005'
            return SphericGuardian.NAME;

        case 6: // '\006'
            return MIXED_COMBAT_NAMES[24];

        case 7: // '\007'
            return MIXED_COMBAT_NAMES[9];

        case 8: // '\b'
            return MIXED_COMBAT_NAMES[10];

        case 9: // '\t'
            return MIXED_COMBAT_NAMES[11];

        case 10: // '\n'
            return SnakePlant.NAME;

        case 11: // '\013'
            return Snecko.NAME;

        case 12: // '\f'
            return MIXED_COMBAT_NAMES[12];

        case 13: // '\r'
            return MIXED_COMBAT_NAMES[13];

        case 14: // '\016'
            return BookOfStabbing.NAME;

        case 15: // '\017'
            return GremlinLeader.NAME;

        case 16: // '\020'
            return Taskmaster.NAME;

        case 17: // '\021'
            return MIXED_COMBAT_NAMES[14];

        case 18: // '\022'
            return MIXED_COMBAT_NAMES[15];

        case 19: // '\023'
            return MIXED_COMBAT_NAMES[16];

        case 20: // '\024'
            return BronzeAutomaton.NAME;

        case 21: // '\025'
            return Champ.NAME;

        case 22: // '\026'
            return TheCollector.NAME;
        }
        s = key;
        byte0 = -1;
        switch(s.hashCode())
        {
        case -1315824018: 
            if(s.equals("Reptomancer"))
                byte0 = 0;
            break;

        case -1238252950: 
            if(s.equals("Transient"))
                byte0 = 1;
            break;

        case 1014856122: 
            if(s.equals("3 Darklings"))
                byte0 = 2;
            break;

        case -500373089: 
            if(s.equals("3 Shapes"))
                byte0 = 3;
            break;

        case 731946207: 
            if(s.equals("Jaw Worm Horde"))
                byte0 = 4;
            break;

        case 1679632599: 
            if(s.equals("Orb Walker"))
                byte0 = 5;
            break;

        case -209678232: 
            if(s.equals("Spire Growth"))
                byte0 = 6;
            break;

        case 77123: 
            if(s.equals("Maw"))
                byte0 = 7;
            break;

        case 1242437246: 
            if(s.equals("4 Shapes"))
                byte0 = 8;
            break;

        case -1235989956: 
            if(s.equals("Sphere and 2 Shapes"))
                byte0 = 9;
            break;

        case -1420565618: 
            if(s.equals("2 Orb Walkers"))
                byte0 = 10;
            break;

        case -793826098: 
            if(s.equals("Nemesis"))
                byte0 = 11;
            break;

        case -2106067372: 
            if(s.equals("Writhing Mass"))
                byte0 = 12;
            break;

        case 1871099803: 
            if(s.equals("Giant Head"))
                byte0 = 13;
            break;

        case -1178669457: 
            if(s.equals("Mysterious Sphere"))
                byte0 = 14;
            break;

        case 1282761458: 
            if(s.equals("Time Eater"))
                byte0 = 15;
            break;

        case 461826094: 
            if(s.equals("Awakened One"))
                byte0 = 16;
            break;

        case -1660991978: 
            if(s.equals("Donu and Deca"))
                byte0 = 17;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return Reptomancer.NAME;

        case 1: // '\001'
            return Transient.NAME;

        case 2: // '\002'
            return Darkling.NAME;

        case 3: // '\003'
            return MIXED_COMBAT_NAMES[17];

        case 4: // '\004'
            return MIXED_COMBAT_NAMES[18];

        case 5: // '\005'
            return OrbWalker.NAME;

        case 6: // '\006'
            return SpireGrowth.NAME;

        case 7: // '\007'
            return Maw.NAME;

        case 8: // '\b'
            return MIXED_COMBAT_NAMES[19];

        case 9: // '\t'
            return MIXED_COMBAT_NAMES[20];

        case 10: // '\n'
            return MIXED_COMBAT_NAMES[21];

        case 11: // '\013'
            return Nemesis.NAME;

        case 12: // '\f'
            return WrithingMass.NAME;

        case 13: // '\r'
            return GiantHead.NAME;

        case 14: // '\016'
            return MysteriousSphere.NAME;

        case 15: // '\017'
            return TimeEater.NAME;

        case 16: // '\020'
            return AwakenedOne.NAME;

        case 17: // '\021'
            return MIXED_COMBAT_NAMES[22];
        }
        s = key;
        byte0 = -1;
        switch(s.hashCode())
        {
        case -209380457: 
            if(s.equals("The Heart"))
                byte0 = 0;
            break;

        case 143439961: 
            if(s.equals("Shield and Spear"))
                byte0 = 1;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return CorruptHeart.NAME;

        case 1: // '\001'
            return MIXED_COMBAT_NAMES[28];
        }
        return "";
    }

    public static MonsterGroup getEncounter(String key)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 1637395457: 
            if(s.equals("Blue Slaver"))
                byte0 = 0;
            break;

        case -1508851536: 
            if(s.equals("Cultist"))
                byte0 = 1;
            break;

        case -548386477: 
            if(s.equals("Jaw Worm"))
                byte0 = 2;
            break;

        case -2013219467: 
            if(s.equals("Looter"))
                byte0 = 3;
            break;

        case 893410389: 
            if(s.equals("Gremlin Gang"))
                byte0 = 4;
            break;

        case -2096825302: 
            if(s.equals("Red Slaver"))
                byte0 = 5;
            break;

        case -1342165661: 
            if(s.equals("Large Slime"))
                byte0 = 6;
            break;

        case -902890624: 
            if(s.equals("Exordium Thugs"))
                byte0 = 7;
            break;

        case -1368941933: 
            if(s.equals("Exordium Wildlife"))
                byte0 = 8;
            break;

        case -992209193: 
            if(s.equals("3 Louse"))
                byte0 = 9;
            break;

        case -1879712874: 
            if(s.equals("2 Louse"))
                byte0 = 10;
            break;

        case 1650599105: 
            if(s.equals("2 Fungi Beasts"))
                byte0 = 11;
            break;

        case 1057095158: 
            if(s.equals("Lots of Slimes"))
                byte0 = 12;
            break;

        case 70731812: 
            if(s.equals("Small Slimes"))
                byte0 = 13;
            break;

        case -663909825: 
            if(s.equals("Gremlin Nob"))
                byte0 = 14;
            break;

        case 1434486691: 
            if(s.equals("Lagavulin"))
                byte0 = 15;
            break;

        case -2108458454: 
            if(s.equals("3 Sentries"))
                byte0 = 16;
            break;

        case -2013947971: 
            if(s.equals("Lagavulin Event"))
                byte0 = 17;
            break;

        case -380917097: 
            if(s.equals("The Mushroom Lair"))
                byte0 = 18;
            break;

        case -1788706656: 
            if(s.equals("The Guardian"))
                byte0 = 19;
            break;

        case 644100489: 
            if(s.equals("Hexaghost"))
                byte0 = 20;
            break;

        case -1873987067: 
            if(s.equals("Slime Boss"))
                byte0 = 21;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new MonsterGroup(new SlaverBlue(0.0F, 0.0F));

        case 1: // '\001'
            return new MonsterGroup(new Cultist(0.0F, -10F));

        case 2: // '\002'
            return new MonsterGroup(new JawWorm(0.0F, 25F));

        case 3: // '\003'
            return new MonsterGroup(new Looter(0.0F, 0.0F));

        case 4: // '\004'
            return spawnGremlins();

        case 5: // '\005'
            return new MonsterGroup(new SlaverRed(0.0F, 0.0F));

        case 6: // '\006'
            if(AbstractDungeon.miscRng.randomBoolean())
                return new MonsterGroup(new AcidSlime_L(0.0F, 0.0F));
            else
                return new MonsterGroup(new SpikeSlime_L(0.0F, 0.0F));

        case 7: // '\007'
            return bottomHumanoid();

        case 8: // '\b'
            return bottomWildlife();

        case 9: // '\t'
            return new MonsterGroup(new AbstractMonster[] {
                getLouse(-350F, 25F), getLouse(-125F, 10F), getLouse(80F, 30F)
            });

        case 10: // '\n'
            return new MonsterGroup(new AbstractMonster[] {
                getLouse(-200F, 10F), getLouse(80F, 30F)
            });

        case 11: // '\013'
            return new MonsterGroup(new AbstractMonster[] {
                new FungiBeast(-400F, 30F), new FungiBeast(-40F, 20F)
            });

        case 12: // '\f'
            return spawnManySmallSlimes();

        case 13: // '\r'
            return spawnSmallSlimes();

        case 14: // '\016'
            return new MonsterGroup(new GremlinNob(0.0F, 0.0F));

        case 15: // '\017'
            return new MonsterGroup(new Lagavulin(true));

        case 16: // '\020'
            return new MonsterGroup(new AbstractMonster[] {
                new Sentry(-330F, 25F), new Sentry(-85F, 10F), new Sentry(140F, 30F)
            });

        case 17: // '\021'
            return new MonsterGroup(new Lagavulin(false));

        case 18: // '\022'
            return new MonsterGroup(new AbstractMonster[] {
                new FungiBeast(-450F, 30F), new FungiBeast(-145F, 20F), new FungiBeast(180F, 15F)
            });

        case 19: // '\023'
            return new MonsterGroup(new TheGuardian());

        case 20: // '\024'
            return new MonsterGroup(new Hexaghost());

        case 21: // '\025'
            return new MonsterGroup(new SlimeBoss());
        }
        s = key;
        byte0 = -1;
        switch(s.hashCode())
        {
        case 75370758: 
            if(s.equals("2 Thieves"))
                byte0 = 0;
            break;

        case -1001149827: 
            if(s.equals("3 Byrds"))
                byte0 = 1;
            break;

        case -113646146: 
            if(s.equals("4 Byrds"))
                byte0 = 2;
            break;

        case 2017619858: 
            if(s.equals("Chosen"))
                byte0 = 3;
            break;

        case -621937833: 
            if(s.equals("Shell Parasite"))
                byte0 = 4;
            break;

        case 1989842815: 
            if(s.equals("Spheric Guardian"))
                byte0 = 5;
            break;

        case 15349611: 
            if(s.equals("Cultist and Chosen"))
                byte0 = 6;
            break;

        case 1328987920: 
            if(s.equals("3 Cultists"))
                byte0 = 7;
            break;

        case -537649645: 
            if(s.equals("Chosen and Byrds"))
                byte0 = 8;
            break;

        case -1706901225: 
            if(s.equals("Sentry and Sphere"))
                byte0 = 9;
            break;

        case -255148213: 
            if(s.equals("Snake Plant"))
                byte0 = 10;
            break;

        case -1814052995: 
            if(s.equals("Snecko"))
                byte0 = 11;
            break;

        case -757040165: 
            if(s.equals("Centurion and Healer"))
                byte0 = 12;
            break;

        case 813978224: 
            if(s.equals("Shelled Parasite and Fungi"))
                byte0 = 13;
            break;

        case 1917396788: 
            if(s.equals("Book of Stabbing"))
                byte0 = 14;
            break;

        case -279622453: 
            if(s.equals("Gremlin Leader"))
                byte0 = 15;
            break;

        case -461459912: 
            if(s.equals("Slavers"))
                byte0 = 16;
            break;

        case -1766312002: 
            if(s.equals("Masked Bandits"))
                byte0 = 17;
            break;

        case -1834311388: 
            if(s.equals("Colosseum Nobs"))
                byte0 = 18;
            break;

        case -1045722682: 
            if(s.equals("Colosseum Slavers"))
                byte0 = 19;
            break;

        case -617327920: 
            if(s.equals("Automaton"))
                byte0 = 20;
            break;

        case 65070879: 
            if(s.equals("Champ"))
                byte0 = 21;
            break;

        case -407507859: 
            if(s.equals("Collector"))
                byte0 = 22;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new MonsterGroup(new AbstractMonster[] {
                new Looter(-200F, 15F), new Mugger(80F, 0.0F)
            });

        case 1: // '\001'
            return new MonsterGroup(new AbstractMonster[] {
                new Byrd(-360F, MathUtils.random(25F, 70F)), new Byrd(-80F, MathUtils.random(25F, 70F)), new Byrd(200F, MathUtils.random(25F, 70F))
            });

        case 2: // '\002'
            return new MonsterGroup(new AbstractMonster[] {
                new Byrd(-470F, MathUtils.random(25F, 70F)), new Byrd(-210F, MathUtils.random(25F, 70F)), new Byrd(50F, MathUtils.random(25F, 70F)), new Byrd(310F, MathUtils.random(25F, 70F))
            });

        case 3: // '\003'
            return new MonsterGroup(new Chosen());

        case 4: // '\004'
            return new MonsterGroup(new ShelledParasite());

        case 5: // '\005'
            return new MonsterGroup(new SphericGuardian());

        case 6: // '\006'
            return new MonsterGroup(new AbstractMonster[] {
                new Cultist(-230F, 15F, false), new Chosen(100F, 25F)
            });

        case 7: // '\007'
            return new MonsterGroup(new AbstractMonster[] {
                new Cultist(-465F, -20F, false), new Cultist(-130F, 15F, false), new Cultist(200F, -5F)
            });

        case 8: // '\b'
            return new MonsterGroup(new AbstractMonster[] {
                new Byrd(-170F, MathUtils.random(25F, 70F)), new Chosen(80F, 0.0F)
            });

        case 9: // '\t'
            return new MonsterGroup(new AbstractMonster[] {
                new Sentry(-305F, 30F), new SphericGuardian()
            });

        case 10: // '\n'
            return new MonsterGroup(new SnakePlant(-30F, -30F));

        case 11: // '\013'
            return new MonsterGroup(new Snecko());

        case 12: // '\f'
            return new MonsterGroup(new AbstractMonster[] {
                new Centurion(-200F, 15F), new Healer(120F, 0.0F)
            });

        case 13: // '\r'
            return new MonsterGroup(new AbstractMonster[] {
                new ShelledParasite(-260F, 15F), new FungiBeast(120F, 0.0F)
            });

        case 14: // '\016'
            return new MonsterGroup(new BookOfStabbing());

        case 15: // '\017'
            return new MonsterGroup(new AbstractMonster[] {
                spawnGremlin(GremlinLeader.POSX[0], GremlinLeader.POSY[0]), spawnGremlin(GremlinLeader.POSX[1], GremlinLeader.POSY[1]), new GremlinLeader()
            });

        case 16: // '\020'
            return new MonsterGroup(new AbstractMonster[] {
                new SlaverBlue(-385F, -15F), new Taskmaster(-133F, 0.0F), new SlaverRed(125F, -30F)
            });

        case 17: // '\021'
            return new MonsterGroup(new AbstractMonster[] {
                new BanditPointy(-320F, 0.0F), new BanditLeader(-75F, -6F), new BanditBear(150F, -6F)
            });

        case 18: // '\022'
            return new MonsterGroup(new AbstractMonster[] {
                new Taskmaster(-270F, 15F), new GremlinNob(130F, 0.0F)
            });

        case 19: // '\023'
            return new MonsterGroup(new AbstractMonster[] {
                new SlaverBlue(-270F, 15F), new SlaverRed(130F, 0.0F)
            });

        case 20: // '\024'
            return new MonsterGroup(new BronzeAutomaton());

        case 21: // '\025'
            return new MonsterGroup(new Champ());

        case 22: // '\026'
            return new MonsterGroup(new TheCollector());
        }
        s = key;
        byte0 = -1;
        switch(s.hashCode())
        {
        case -52065857: 
            if(s.equals("Flame Bruiser 1 Orb"))
                byte0 = 0;
            break;

        case -51142336: 
            if(s.equals("Flame Bruiser 2 Orb"))
                byte0 = 1;
            break;

        case -1315824018: 
            if(s.equals("Reptomancer"))
                byte0 = 2;
            break;

        case -1238252950: 
            if(s.equals("Transient"))
                byte0 = 3;
            break;

        case 1014856122: 
            if(s.equals("3 Darklings"))
                byte0 = 4;
            break;

        case -500373089: 
            if(s.equals("3 Shapes"))
                byte0 = 5;
            break;

        case 731946207: 
            if(s.equals("Jaw Worm Horde"))
                byte0 = 6;
            break;

        case -222004000: 
            if(s.equals("Snecko and Mystics"))
                byte0 = 7;
            break;

        case 1679632599: 
            if(s.equals("Orb Walker"))
                byte0 = 8;
            break;

        case -209678232: 
            if(s.equals("Spire Growth"))
                byte0 = 9;
            break;

        case 77123: 
            if(s.equals("Maw"))
                byte0 = 10;
            break;

        case 1242437246: 
            if(s.equals("4 Shapes"))
                byte0 = 11;
            break;

        case -1235989956: 
            if(s.equals("Sphere and 2 Shapes"))
                byte0 = 12;
            break;

        case -1420565618: 
            if(s.equals("2 Orb Walkers"))
                byte0 = 13;
            break;

        case -793826098: 
            if(s.equals("Nemesis"))
                byte0 = 14;
            break;

        case -2106067372: 
            if(s.equals("Writhing Mass"))
                byte0 = 15;
            break;

        case 1871099803: 
            if(s.equals("Giant Head"))
                byte0 = 16;
            break;

        case -1178669457: 
            if(s.equals("Mysterious Sphere"))
                byte0 = 17;
            break;

        case 1282761458: 
            if(s.equals("Time Eater"))
                byte0 = 18;
            break;

        case 461826094: 
            if(s.equals("Awakened One"))
                byte0 = 19;
            break;

        case -1660991978: 
            if(s.equals("Donu and Deca"))
                byte0 = 20;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new MonsterGroup(new AbstractMonster[] {
                new Reptomancer(), new SnakeDagger(Reptomancer.POSX[0], Reptomancer.POSY[0])
            });

        case 1: // '\001'
        case 2: // '\002'
            return new MonsterGroup(new AbstractMonster[] {
                new SnakeDagger(Reptomancer.POSX[1], Reptomancer.POSY[1]), new Reptomancer(), new SnakeDagger(Reptomancer.POSX[0], Reptomancer.POSY[0])
            });

        case 3: // '\003'
            return new MonsterGroup(new Transient());

        case 4: // '\004'
            return new MonsterGroup(new AbstractMonster[] {
                new Darkling(-440F, 10F), new Darkling(-140F, 30F), new Darkling(180F, -5F)
            });

        case 5: // '\005'
            return spawnShapes(true);

        case 6: // '\006'
            return new MonsterGroup(new AbstractMonster[] {
                new JawWorm(-490F, -5F, true), new JawWorm(-150F, 20F, true), new JawWorm(175F, 5F, true)
            });

        case 7: // '\007'
            return new MonsterGroup(new AbstractMonster[] {
                new Healer(-475F, -10F), new Snecko(-130F, -13F), new Healer(175F, -10F)
            });

        case 8: // '\b'
            return new MonsterGroup(new OrbWalker(-30F, 20F));

        case 9: // '\t'
            return new MonsterGroup(new SpireGrowth());

        case 10: // '\n'
            return new MonsterGroup(new Maw(-70F, 20F));

        case 11: // '\013'
            return spawnShapes(false);

        case 12: // '\f'
            return new MonsterGroup(new AbstractMonster[] {
                getAncientShape(-435F, 10F), getAncientShape(-210F, 0.0F), new SphericGuardian(110F, 10F)
            });

        case 13: // '\r'
            return new MonsterGroup(new AbstractMonster[] {
                new OrbWalker(-250F, 32F), new OrbWalker(150F, 26F)
            });

        case 14: // '\016'
            return new MonsterGroup(new Nemesis());

        case 15: // '\017'
            return new MonsterGroup(new WrithingMass());

        case 16: // '\020'
            return new MonsterGroup(new GiantHead());

        case 17: // '\021'
            return new MonsterGroup(new AbstractMonster[] {
                getAncientShape(-475F, 10F), getAncientShape(-250F, 0.0F), new OrbWalker(150F, 30F)
            });

        case 18: // '\022'
            return new MonsterGroup(new TimeEater());

        case 19: // '\023'
            return new MonsterGroup(new AbstractMonster[] {
                new Cultist(-590F, 10F, false), new Cultist(-298F, -10F, false), new AwakenedOne(100F, 15F)
            });

        case 20: // '\024'
            return new MonsterGroup(new AbstractMonster[] {
                new Deca(), new Donu()
            });
        }
        s = key;
        byte0 = -1;
        switch(s.hashCode())
        {
        case -209380457: 
            if(s.equals("The Heart"))
                byte0 = 0;
            break;

        case 143439961: 
            if(s.equals("Shield and Spear"))
                byte0 = 1;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new MonsterGroup(new CorruptHeart());

        case 1: // '\001'
            return new MonsterGroup(new AbstractMonster[] {
                new SpireShield(), new SpireSpear()
            });
        }
        return new MonsterGroup(new ApologySlime());
    }

    private static float randomYOffset(float y)
    {
        return y + MathUtils.random(-20F, 20F);
    }

    private static float randomXOffset(float x)
    {
        return x + MathUtils.random(-20F, 20F);
    }

    public static AbstractMonster getGremlin(String key, float xPos, float yPos)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 1902169252: 
            if(s.equals("GremlinWarrior"))
                byte0 = 0;
            break;

        case 942423992: 
            if(s.equals("GremlinThief"))
                byte0 = 1;
            break;

        case 117167995: 
            if(s.equals("GremlinFat"))
                byte0 = 2;
            break;

        case 1076440234: 
            if(s.equals("GremlinTsundere"))
                byte0 = 3;
            break;

        case -762313271: 
            if(s.equals("GremlinWizard"))
                byte0 = 4;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new GremlinWarrior(xPos, yPos);

        case 1: // '\001'
            return new GremlinThief(xPos, yPos);

        case 2: // '\002'
            return new GremlinFat(xPos, yPos);

        case 3: // '\003'
            return new GremlinTsundere(xPos, yPos);

        case 4: // '\004'
            return new GremlinWizard(xPos, yPos);
        }
        logger.info((new StringBuilder()).append("UNKNOWN GREMLIN: ").append(key).toString());
        return null;
    }

    public static AbstractMonster getAncientShape(float x, float y)
    {
        switch(AbstractDungeon.miscRng.random(2))
        {
        case 0: // '\0'
            return new Spiker(x, y);

        case 1: // '\001'
            return new Repulsor(x, y);
        }
        return new Exploder(x, y);
    }

    public static AbstractMonster getShape(String key, float xPos, float yPos)
    {
        String s = key;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case -357033662: 
            if(s.equals("Repulsor"))
                byte0 = 0;
            break;

        case -1812079284: 
            if(s.equals("Spiker"))
                byte0 = 1;
            break;

        case -1864267823: 
            if(s.equals("Exploder"))
                byte0 = 2;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new Repulsor(xPos, yPos);

        case 1: // '\001'
            return new Spiker(xPos, yPos);

        case 2: // '\002'
            return new Exploder(xPos, yPos);
        }
        logger.info((new StringBuilder()).append("UNKNOWN SHAPE: ").append(key).toString());
        return null;
    }

    private static MonsterGroup spawnShapes(boolean weak)
    {
        ArrayList shapePool = new ArrayList();
        shapePool.add("Repulsor");
        shapePool.add("Repulsor");
        shapePool.add("Exploder");
        shapePool.add("Exploder");
        shapePool.add("Spiker");
        shapePool.add("Spiker");
        AbstractMonster retVal[];
        if(weak)
            retVal = new AbstractMonster[3];
        else
            retVal = new AbstractMonster[4];
        int index = AbstractDungeon.miscRng.random(shapePool.size() - 1);
        String key = (String)shapePool.get(index);
        shapePool.remove(index);
        retVal[0] = getShape(key, -480F, 6F);
        index = AbstractDungeon.miscRng.random(shapePool.size() - 1);
        key = (String)shapePool.get(index);
        shapePool.remove(index);
        retVal[1] = getShape(key, -240F, -6F);
        index = AbstractDungeon.miscRng.random(shapePool.size() - 1);
        key = (String)shapePool.get(index);
        shapePool.remove(index);
        retVal[2] = getShape(key, 0.0F, -12F);
        if(!weak)
        {
            index = AbstractDungeon.miscRng.random(shapePool.size() - 1);
            key = (String)shapePool.get(index);
            shapePool.remove(index);
            retVal[3] = getShape(key, 240F, 12F);
        }
        return new MonsterGroup(retVal);
    }

    private static MonsterGroup spawnSmallSlimes()
    {
        AbstractMonster retVal[] = new AbstractMonster[2];
        if(AbstractDungeon.miscRng.randomBoolean())
        {
            retVal[0] = new SpikeSlime_S(-230F, 32F, 0);
            retVal[1] = new AcidSlime_M(35F, 8F);
        } else
        {
            retVal[0] = new AcidSlime_S(-230F, 32F, 0);
            retVal[1] = new SpikeSlime_M(35F, 8F);
        }
        return new MonsterGroup(retVal);
    }

    private static MonsterGroup spawnManySmallSlimes()
    {
        ArrayList slimePool = new ArrayList();
        slimePool.add("SpikeSlime_S");
        slimePool.add("SpikeSlime_S");
        slimePool.add("SpikeSlime_S");
        slimePool.add("AcidSlime_S");
        slimePool.add("AcidSlime_S");
        AbstractMonster retVal[] = new AbstractMonster[5];
        int index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
        String key = (String)slimePool.get(index);
        slimePool.remove(index);
        if(key.equals("SpikeSlime_S"))
            retVal[0] = new SpikeSlime_S(-480F, 30F, 0);
        else
            retVal[0] = new AcidSlime_S(-480F, 30F, 0);
        index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
        key = (String)slimePool.get(index);
        slimePool.remove(index);
        if(key.equals("SpikeSlime_S"))
            retVal[1] = new SpikeSlime_S(-320F, 2.0F, 0);
        else
            retVal[1] = new AcidSlime_S(-320F, 2.0F, 0);
        index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
        key = (String)slimePool.get(index);
        slimePool.remove(index);
        if(key.equals("SpikeSlime_S"))
            retVal[2] = new SpikeSlime_S(-160F, 32F, 0);
        else
            retVal[2] = new AcidSlime_S(-160F, 32F, 0);
        index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
        key = (String)slimePool.get(index);
        slimePool.remove(index);
        if(key.equals("SpikeSlime_S"))
            retVal[3] = new SpikeSlime_S(10F, -12F, 0);
        else
            retVal[3] = new AcidSlime_S(10F, -12F, 0);
        index = AbstractDungeon.miscRng.random(slimePool.size() - 1);
        key = (String)slimePool.get(index);
        slimePool.remove(index);
        if(key.equals("SpikeSlime_S"))
            retVal[4] = new SpikeSlime_S(200F, 9F, 0);
        else
            retVal[4] = new AcidSlime_S(200F, 9F, 0);
        return new MonsterGroup(retVal);
    }

    private static MonsterGroup spawnGremlins()
    {
        ArrayList gremlinPool = new ArrayList();
        gremlinPool.add("GremlinWarrior");
        gremlinPool.add("GremlinWarrior");
        gremlinPool.add("GremlinThief");
        gremlinPool.add("GremlinThief");
        gremlinPool.add("GremlinFat");
        gremlinPool.add("GremlinFat");
        gremlinPool.add("GremlinTsundere");
        gremlinPool.add("GremlinWizard");
        AbstractMonster retVal[] = new AbstractMonster[4];
        int index = AbstractDungeon.miscRng.random(gremlinPool.size() - 1);
        String key = (String)gremlinPool.get(index);
        gremlinPool.remove(index);
        retVal[0] = getGremlin(key, -320F, 25F);
        index = AbstractDungeon.miscRng.random(gremlinPool.size() - 1);
        key = (String)gremlinPool.get(index);
        gremlinPool.remove(index);
        retVal[1] = getGremlin(key, -160F, -12F);
        index = AbstractDungeon.miscRng.random(gremlinPool.size() - 1);
        key = (String)gremlinPool.get(index);
        gremlinPool.remove(index);
        retVal[2] = getGremlin(key, 25F, -35F);
        index = AbstractDungeon.miscRng.random(gremlinPool.size() - 1);
        key = (String)gremlinPool.get(index);
        gremlinPool.remove(index);
        retVal[3] = getGremlin(key, 205F, 40F);
        return new MonsterGroup(retVal);
    }

    private static AbstractMonster spawnGremlin(float x, float y)
    {
        ArrayList gremlinPool = new ArrayList();
        gremlinPool.add("GremlinWarrior");
        gremlinPool.add("GremlinWarrior");
        gremlinPool.add("GremlinThief");
        gremlinPool.add("GremlinThief");
        gremlinPool.add("GremlinFat");
        gremlinPool.add("GremlinFat");
        gremlinPool.add("GremlinTsundere");
        gremlinPool.add("GremlinWizard");
        return getGremlin((String)gremlinPool.get(AbstractDungeon.miscRng.random(0, gremlinPool.size() - 1)), x, y);
    }

    private static MonsterGroup bottomHumanoid()
    {
        AbstractMonster monsters[] = new AbstractMonster[2];
        monsters[0] = bottomGetWeakWildlife(randomXOffset(-160F), randomYOffset(20F));
        monsters[1] = bottomGetStrongHumanoid(randomXOffset(130F), randomYOffset(20F));
        return new MonsterGroup(monsters);
    }

    private static MonsterGroup bottomWildlife()
    {
        int numMonster = 2;
        AbstractMonster monsters[] = new AbstractMonster[numMonster];
        if(numMonster == 2)
        {
            monsters[0] = bottomGetStrongWildlife(randomXOffset(-150F), randomYOffset(20F));
            monsters[1] = bottomGetWeakWildlife(randomXOffset(150F), randomYOffset(20F));
        } else
        if(numMonster == 3)
        {
            monsters[0] = bottomGetWeakWildlife(randomXOffset(-200F), randomYOffset(20F));
            monsters[1] = bottomGetWeakWildlife(randomXOffset(0.0F), randomYOffset(20F));
            monsters[2] = bottomGetWeakWildlife(randomXOffset(200F), randomYOffset(20F));
        }
        return new MonsterGroup(monsters);
    }

    private static AbstractMonster bottomGetStrongHumanoid(float x, float y)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new Cultist(x, y));
        monsters.add(getSlaver(x, y));
        monsters.add(new Looter(x, y));
        AbstractMonster output = (AbstractMonster)monsters.get(AbstractDungeon.miscRng.random(0, monsters.size() - 1));
        return output;
    }

    private static AbstractMonster bottomGetStrongWildlife(float x, float y)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(new FungiBeast(x, y));
        monsters.add(new JawWorm(x, y));
        AbstractMonster output = (AbstractMonster)monsters.get(AbstractDungeon.miscRng.random(0, monsters.size() - 1));
        return output;
    }

    private static AbstractMonster bottomGetWeakWildlife(float x, float y)
    {
        ArrayList monsters = new ArrayList();
        monsters.add(getLouse(x, y));
        monsters.add(new SpikeSlime_M(x, y));
        monsters.add(new AcidSlime_M(x, y));
        return (AbstractMonster)monsters.get(AbstractDungeon.miscRng.random(0, monsters.size() - 1));
    }

    private static AbstractMonster getSlaver(float x, float y)
    {
        if(AbstractDungeon.miscRng.randomBoolean())
            return new SlaverRed(x, y);
        else
            return new SlaverBlue(x, y);
    }

    private static AbstractMonster getLouse(float x, float y)
    {
        if(AbstractDungeon.miscRng.randomBoolean())
            return new LouseNormal(x, y);
        else
            return new LouseDefensive(x, y);
    }

    public static void uploadEnemyData()
    {
        ArrayList derp = new ArrayList();
        ArrayList data = new ArrayList();
        data.add(new EnemyData("Blue Slaver", 1, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("Cultist", 1, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("Jaw Worm", 1, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("2 Louse", 1, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("Small Slimes", 1, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("Gremlin Gang", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Large Slime", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Looter", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Lots of Slimes", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Exordium Thugs", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Exordium Wildlife", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Red Slaver", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("3 Louse", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("2 Fungi Beasts", 1, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Gremlin Nob", 1, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("Lagavulin", 1, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("3 Sentries", 1, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("Lagavulin Event", 1, EnemyData.MonsterType.EVENT));
        data.add(new EnemyData("The Mushroom Lair", 1, EnemyData.MonsterType.EVENT));
        data.add(new EnemyData("The Guardian", 1, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Hexaghost", 1, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Slime Boss", 1, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Chosen", 2, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("Shell Parasite", 2, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("Spheric Guardian", 2, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("3 Byrds", 2, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("2 Thieves", 2, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("Chosen and Byrds", 2, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Sentry and Sphere", 2, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Snake Plant", 2, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Snecko", 2, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Centurion and Healer", 2, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Cultist and Chosen", 2, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("3 Cultists", 2, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Shelled Parasite and Fungi", 2, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Gremlin Leader", 2, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("Slavers", 2, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("Book of Stabbing", 2, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("Masked Bandits", 2, EnemyData.MonsterType.EVENT));
        data.add(new EnemyData("Colosseum Nobs", 2, EnemyData.MonsterType.EVENT));
        data.add(new EnemyData("Colosseum Slavers", 2, EnemyData.MonsterType.EVENT));
        data.add(new EnemyData("Automaton", 2, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Champ", 2, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Collector", 2, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Orb Walker", 3, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("3 Darklings", 3, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("3 Shapes", 3, EnemyData.MonsterType.WEAK));
        data.add(new EnemyData("Transient", 3, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("4 Shapes", 3, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Maw", 3, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Jaw Worm Horde", 3, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Sphere and 2 Shapes", 3, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Spire Growth", 3, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Writhing Mass", 3, EnemyData.MonsterType.STRONG));
        data.add(new EnemyData("Giant Head", 3, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("Nemesis", 3, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("Reptomancer", 3, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("Mysterious Sphere", 3, EnemyData.MonsterType.EVENT));
        data.add(new EnemyData("Mind Bloom Boss Battle", 3, EnemyData.MonsterType.EVENT));
        data.add(new EnemyData("2 Orb Walkers", 3, EnemyData.MonsterType.EVENT));
        data.add(new EnemyData("Awakened One", 3, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Donu and Deca", 3, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Time Eater", 3, EnemyData.MonsterType.BOSS));
        data.add(new EnemyData("Shield and Spear", 4, EnemyData.MonsterType.ELITE));
        data.add(new EnemyData("The Heart", 4, EnemyData.MonsterType.BOSS));
        EnemyData d;
        for(Iterator iterator = data.iterator(); iterator.hasNext(); derp.add(d.gameDataUploadData()))
            d = (EnemyData)iterator.next();

        BotDataUploader.uploadDataAsync(com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType.ENEMY_DATA, EnemyData.gameDataUploadHeader(), derp);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/helpers/MonsterHelper.getName());
    private static final UIStrings uiStrings;
    public static final String MIXED_COMBAT_NAMES[];
    public static final String BLUE_SLAVER_ENC = "Blue Slaver";
    public static final String CULTIST_ENC = "Cultist";
    public static final String JAW_WORM_ENC = "Jaw Worm";
    public static final String LOOTER_ENC = "Looter";
    public static final String TWO_LOUSE_ENC = "2 Louse";
    public static final String SMALL_SLIMES_ENC = "Small Slimes";
    public static final String GREMLIN_GANG_ENC = "Gremlin Gang";
    public static final String RED_SLAVER_ENC = "Red Slaver";
    public static final String LARGE_SLIME_ENC = "Large Slime";
    public static final String LVL_1_THUGS_ENC = "Exordium Thugs";
    public static final String LVL_1_WILDLIFE_ENC = "Exordium Wildlife";
    public static final String THREE_LOUSE_ENC = "3 Louse";
    public static final String TWO_FUNGI_ENC = "2 Fungi Beasts";
    public static final String LOTS_OF_SLIMES_ENC = "Lots of Slimes";
    public static final String GREMLIN_NOB_ENC = "Gremlin Nob";
    public static final String LAGAVULIN_ENC = "Lagavulin";
    public static final String THREE_SENTRY_ENC = "3 Sentries";
    public static final String LAGAVULIN_EVENT_ENC = "Lagavulin Event";
    public static final String MUSHROOMS_EVENT_ENC = "The Mushroom Lair";
    public static final String GUARDIAN_ENC = "The Guardian";
    public static final String HEXAGHOST_ENC = "Hexaghost";
    public static final String SLIME_BOSS_ENC = "Slime Boss";
    public static final String TWO_THIEVES_ENC = "2 Thieves";
    public static final String THREE_BYRDS_ENC = "3 Byrds";
    public static final String CHOSEN_ENC = "Chosen";
    public static final String SHELL_PARASITE_ENC = "Shell Parasite";
    public static final String SPHERE_GUARDIAN_ENC = "Spheric Guardian";
    public static final String CULTIST_CHOSEN_ENC = "Cultist and Chosen";
    public static final String THREE_CULTISTS_ENC = "3 Cultists";
    public static final String FOUR_BYRDS_ENC = "4 Byrds";
    public static final String CHOSEN_FLOCK_ENC = "Chosen and Byrds";
    public static final String SENTRY_SPHERE_ENC = "Sentry and Sphere";
    public static final String SNAKE_PLANT_ENC = "Snake Plant";
    public static final String SNECKO_ENC = "Snecko";
    public static final String TANK_HEALER_ENC = "Centurion and Healer";
    public static final String PARASITE_AND_FUNGUS = "Shelled Parasite and Fungi";
    public static final String STAB_BOOK_ENC = "Book of Stabbing";
    public static final String GREMLIN_LEADER_ENC = "Gremlin Leader";
    public static final String SLAVERS_ENC = "Slavers";
    public static final String MASKED_BANDITS_ENC = "Masked Bandits";
    public static final String COLOSSEUM_SLAVER_ENC = "Colosseum Slavers";
    public static final String COLOSSEUM_NOB_ENC = "Colosseum Nobs";
    public static final String AUTOMATON_ENC = "Automaton";
    public static final String CHAMP_ENC = "Champ";
    public static final String COLLECTOR_ENC = "Collector";
    public static final String THREE_DARKLINGS_ENC = "3 Darklings";
    public static final String THREE_SHAPES_ENC = "3 Shapes";
    public static final String ORB_WALKER_ENC = "Orb Walker";
    public static final String TRANSIENT_ENC = "Transient";
    public static final String REPTOMANCER_ENC = "Reptomancer";
    public static final String SPIRE_GROWTH_ENC = "Spire Growth";
    public static final String MAW_ENC = "Maw";
    public static final String FOUR_SHAPES_ENC = "4 Shapes";
    public static final String SPHERE_TWO_SHAPES_ENC = "Sphere and 2 Shapes";
    public static final String JAW_WORMS_HORDE = "Jaw Worm Horde";
    public static final String SNECKO_WITH_MYSTICS = "Snecko and Mystics";
    public static final String WRITHING_MASS_ENC = "Writhing Mass";
    public static final String TWO_ORB_WALKER_ENC = "2 Orb Walkers";
    public static final String NEMESIS_ENC = "Nemesis";
    public static final String GIANT_HEAD_ENC = "Giant Head";
    public static final String MYSTERIOUS_SPHERE_ENC = "Mysterious Sphere";
    public static final String MIND_BLOOM_BOSS = "Mind Bloom Boss Battle";
    public static final String TIME_EATER_ENC = "Time Eater";
    public static final String AWAKENED_ENC = "Awakened One";
    public static final String DONU_DECA_ENC = "Donu and Deca";
    public static final String THE_HEART_ENC = "The Heart";
    public static final String SHIELD_SPEAR_ENC = "Shield and Spear";
    public static final String EYES_ENC = "The Eyes";
    public static final String APOLOGY_SLIME_ENC = "Apologetic Slime";
    public static final String OLD_REPTO_ONE_ENC = "Flame Bruiser 1 Orb";
    public static final String OLD_REPTO_TWO_ENC = "Flame Bruiser 2 Orb";
    public static final String OLD_SLAVER_PARASITE = "Slaver and Parasite";
    public static final String OLD_SNECKO_MYSTICS = "Snecko and Mystics";

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RunHistoryMonsterNames");
        MIXED_COMBAT_NAMES = uiStrings.TEXT;
    }
}
