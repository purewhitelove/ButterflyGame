// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DistributorFactory.java

package com.megacrit.cardcrawl.integrations;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.integrations.discord.DiscordIntegration;
import com.megacrit.cardcrawl.integrations.ea.EaIntegration;
import com.megacrit.cardcrawl.integrations.gog.GogIntegration;
import com.megacrit.cardcrawl.integrations.microsoft.MicrosoftIntegration;
import com.megacrit.cardcrawl.integrations.steam.SteamIntegration;
import com.megacrit.cardcrawl.integrations.wegame.WeGameIntegration;

// Referenced classes of package com.megacrit.cardcrawl.integrations:
//            DistributorFactoryException, PublisherIntegration

public class DistributorFactory
{
    public static final class Distributor extends Enum
    {

        public static Distributor[] values()
        {
            return (Distributor[])$VALUES.clone();
        }

        public static Distributor valueOf(String name)
        {
            return (Distributor)Enum.valueOf(com/megacrit/cardcrawl/integrations/DistributorFactory$Distributor, name);
        }

        public static final Distributor STEAM;
        public static final Distributor DISCORD;
        public static final Distributor WEGAME;
        public static final Distributor GOG;
        public static final Distributor EA;
        public static final Distributor MICROSOFT;
        private static final Distributor $VALUES[];

        static 
        {
            STEAM = new Distributor("STEAM", 0);
            DISCORD = new Distributor("DISCORD", 1);
            WEGAME = new Distributor("WEGAME", 2);
            GOG = new Distributor("GOG", 3);
            EA = new Distributor("EA", 4);
            MICROSOFT = new Distributor("MICROSOFT", 5);
            $VALUES = (new Distributor[] {
                STEAM, DISCORD, WEGAME, GOG, EA, MICROSOFT
            });
        }

        private Distributor(String s, int i)
        {
            super(s, i);
        }
    }


    public DistributorFactory()
    {
    }

    public static PublisherIntegration getEnabledDistributor(String distributor)
        throws DistributorFactoryException
    {
        String s = distributor;
        byte byte0 = -1;
        switch(s.hashCode())
        {
        case 109760848: 
            if(s.equals("steam"))
                byte0 = 0;
            break;

        case 1671380268: 
            if(s.equals("discord"))
                byte0 = 1;
            break;

        case -791657536: 
            if(s.equals("wegame"))
                byte0 = 2;
            break;

        case 102527: 
            if(s.equals("gog"))
                byte0 = 3;
            break;

        case 3228: 
            if(s.equals("ea"))
                byte0 = 4;
            break;

        case -94228242: 
            if(s.equals("microsoft"))
                byte0 = 5;
            break;
        }
        switch(byte0)
        {
        case 0: // '\0'
            return new SteamIntegration();

        case 1: // '\001'
            return new DiscordIntegration();

        case 2: // '\002'
            return new WeGameIntegration();

        case 3: // '\003'
            return new GogIntegration();

        case 4: // '\004'
            return new EaIntegration();

        case 5: // '\005'
            return new MicrosoftIntegration();
        }
        throw new DistributorFactoryException((new StringBuilder()).append("Unrecognized distributor=").append(distributor).toString());
    }

    public static boolean isLeaderboardEnabled()
    {
        return CardCrawlGame.publisherIntegration.getType() == Distributor.STEAM;
    }
}
