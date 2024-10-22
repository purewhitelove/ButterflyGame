// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharSelectInfo.java

package com.megacrit.cardcrawl.screens;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import java.util.*;

public class CharSelectInfo
{

    public CharSelectInfo(String name, String flavorText, int currentHp, int maxHp, int maxOrbs, int gold, int cardDraw, 
            AbstractPlayer player, ArrayList relics, ArrayList deck, boolean resumeGame)
    {
        this.name = name;
        this.flavorText = flavorText;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.maxOrbs = maxOrbs;
        hp = (new StringBuilder()).append(Integer.toString(currentHp)).append("/").append(Integer.toString(maxHp)).toString();
        this.gold = gold;
        this.cardDraw = cardDraw;
        this.relics = relics;
        this.deck = deck;
        this.player = player;
        this.resumeGame = resumeGame;
        if(!resumeGame)
            setDeck();
    }

    public CharSelectInfo(String name, String fText, int currentHp, int maxHp, int maxOrbs, int gold, int cardDraw, 
            AbstractPlayer player, ArrayList relics, ArrayList deck, long saveDate, int floorNum, String levelName, 
            boolean isHardMode)
    {
        this(name, fText, currentHp, maxHp, maxOrbs, gold, cardDraw, player, relics, deck, true);
        this.isHardMode = isHardMode;
        this.saveDate = saveDate;
        this.floorNum = floorNum;
        this.levelName = levelName;
    }

    private void setDeck()
    {
        deckString = createDeckInfoString(player.getStartingDeck());
    }

    public static String createDeckInfoString(ArrayList deck)
    {
        HashMap cards = new HashMap();
        for(Iterator iterator = deck.iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            if(!cards.containsKey(s))
                cards.put(s, Integer.valueOf(1));
            else
                cards.put(s, Integer.valueOf(((Integer)cards.get(s)).intValue() + 1));
        }

        StringBuilder sb = new StringBuilder();
        for(Iterator iterator1 = cards.entrySet().iterator(); iterator1.hasNext(); sb.append(", "))
        {
            java.util.Map.Entry c = (java.util.Map.Entry)iterator1.next();
            sb.append("#b").append(c.getValue()).append(" ").append((String)c.getKey());
            if(((Integer)c.getValue()).intValue() > 1)
                sb.append("s");
        }

        String retVal = sb.toString();
        if(retVal.length() > 80)
            return "Click the deck icon to view starting cards.";
        else
            return retVal.substring(0, retVal.length() - 2);
    }

    public String name;
    public String flavorText;
    public String hp;
    public int gold;
    public int currentHp;
    public int maxHp;
    public int maxOrbs;
    public int cardDraw;
    public int floorNum;
    public String levelName;
    public long saveDate;
    public AbstractPlayer player;
    public String deckString;
    public ArrayList relics;
    public ArrayList deck;
    public boolean resumeGame;
    public boolean isHardMode;
}
