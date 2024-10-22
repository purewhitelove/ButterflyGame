// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharacterManager.java

package com.megacrit.cardcrawl.characters;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.random.Random;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.characters:
//            Ironclad, TheSilent, Defect, Watcher, 
//            AbstractPlayer

public class CharacterManager
{

    public CharacterManager()
    {
        if(masterCharacterList.isEmpty())
        {
            masterCharacterList.add(new Ironclad(CardCrawlGame.playerName));
            masterCharacterList.add(new TheSilent(CardCrawlGame.playerName));
            masterCharacterList.add(new Defect(CardCrawlGame.playerName));
            masterCharacterList.add(new Watcher(CardCrawlGame.playerName));
        } else
        {
            AbstractPlayer c;
            for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext(); c.loadPrefs())
                c = (AbstractPlayer)iterator.next();

        }
    }

    public AbstractPlayer setChosenCharacter(AbstractPlayer.PlayerClass c)
    {
        for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext();)
        {
            AbstractPlayer character = (AbstractPlayer)iterator.next();
            if(character.chosenClass == c)
            {
                AbstractDungeon.player = character;
                return character;
            }
        }

        logger.error((new StringBuilder()).append("The character ").append(c.name()).append(" does not exist in the CharacterManager's master character list").toString());
        return null;
    }

    public boolean anySaveFileExists()
    {
        for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext();)
        {
            AbstractPlayer character = (AbstractPlayer)iterator.next();
            if(character.saveFileExists())
                return true;
        }

        return false;
    }

    public AbstractPlayer loadChosenCharacter()
    {
        for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext();)
        {
            AbstractPlayer character = (AbstractPlayer)iterator.next();
            if(character.saveFileExists())
            {
                AbstractDungeon.player = character;
                return character;
            }
        }

        logger.info("No character save file was found!");
        return null;
    }

    public ArrayList getAllCharacterStats()
    {
        ArrayList allCharStats = new ArrayList();
        AbstractPlayer c;
        for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext(); allCharStats.add(c.getCharStat()))
            c = (AbstractPlayer)iterator.next();

        return allCharStats;
    }

    public void refreshAllCharStats()
    {
        AbstractPlayer c;
        for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext(); c.refreshCharStat())
            c = (AbstractPlayer)iterator.next();

    }

    public ArrayList getAllPrefs()
    {
        ArrayList allPrefs = new ArrayList();
        AbstractPlayer c;
        for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext(); allPrefs.add(c.getPrefs()))
            c = (AbstractPlayer)iterator.next();

        return allPrefs;
    }

    public AbstractPlayer getRandomCharacter(Random rng)
    {
        int index = rng.random(masterCharacterList.size() - 1);
        return (AbstractPlayer)masterCharacterList.get(index);
    }

    public AbstractPlayer recreateCharacter(AbstractPlayer.PlayerClass p)
    {
        for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext();)
        {
            AbstractPlayer old = (AbstractPlayer)iterator.next();
            if(old.chosenClass == p)
            {
                AbstractPlayer newChar = old.newInstance();
                masterCharacterList.set(masterCharacterList.indexOf(old), newChar);
                old.dispose();
                logger.info((new StringBuilder()).append("Successfully recreated ").append(newChar.chosenClass.name()).toString());
                return newChar;
            }
        }

        return null;
    }

    public AbstractPlayer getCharacter(AbstractPlayer.PlayerClass c)
    {
        for(Iterator iterator = masterCharacterList.iterator(); iterator.hasNext();)
        {
            AbstractPlayer character = (AbstractPlayer)iterator.next();
            if(character.chosenClass == c)
                return character;
        }

        logger.error((new StringBuilder()).append("The character ").append(c.name()).append(" does not exist in the CharacterManager's master character list").toString());
        return null;
    }

    public ArrayList getAllCharacters()
    {
        return masterCharacterList;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/characters/CharacterManager.getName());
    private static ArrayList masterCharacterList = new ArrayList();

}
