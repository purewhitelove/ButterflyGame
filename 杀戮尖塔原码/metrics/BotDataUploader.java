// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BotDataUploader.java

package com.megacrit.cardcrawl.metrics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotDataUploader
    implements Runnable
{
    public static final class GameDataType extends Enum
    {

        public static GameDataType[] values()
        {
            return (GameDataType[])$VALUES.clone();
        }

        public static GameDataType valueOf(String name)
        {
            return (GameDataType)Enum.valueOf(com/megacrit/cardcrawl/metrics/BotDataUploader$GameDataType, name);
        }

        public static final GameDataType BANDITS;
        public static final GameDataType CRASH_DATA;
        public static final GameDataType DAILY_DATA;
        public static final GameDataType DEMO_EMBARK;
        public static final GameDataType VICTORY_DATA;
        public static final GameDataType CARD_DATA;
        public static final GameDataType ENEMY_DATA;
        public static final GameDataType RELIC_DATA;
        public static final GameDataType POTION_DATA;
        public static final GameDataType DAILY_MOD_DATA;
        public static final GameDataType BLIGHT_DATA;
        public static final GameDataType KEYWORD_DATA;
        private static final GameDataType $VALUES[];

        static 
        {
            BANDITS = new GameDataType("BANDITS", 0);
            CRASH_DATA = new GameDataType("CRASH_DATA", 1);
            DAILY_DATA = new GameDataType("DAILY_DATA", 2);
            DEMO_EMBARK = new GameDataType("DEMO_EMBARK", 3);
            VICTORY_DATA = new GameDataType("VICTORY_DATA", 4);
            CARD_DATA = new GameDataType("CARD_DATA", 5);
            ENEMY_DATA = new GameDataType("ENEMY_DATA", 6);
            RELIC_DATA = new GameDataType("RELIC_DATA", 7);
            POTION_DATA = new GameDataType("POTION_DATA", 8);
            DAILY_MOD_DATA = new GameDataType("DAILY_MOD_DATA", 9);
            BLIGHT_DATA = new GameDataType("BLIGHT_DATA", 10);
            KEYWORD_DATA = new GameDataType("KEYWORD_DATA", 11);
            $VALUES = (new GameDataType[] {
                BANDITS, CRASH_DATA, DAILY_DATA, DEMO_EMBARK, VICTORY_DATA, CARD_DATA, ENEMY_DATA, RELIC_DATA, POTION_DATA, DAILY_MOD_DATA, 
                BLIGHT_DATA, KEYWORD_DATA
            });
        }

        private GameDataType(String s, int i)
        {
            super(s, i);
        }
    }


    public BotDataUploader()
    {
        gson = new Gson();
        url = System.getenv("STS_DATA_UPLOAD_URL");
    }

    public void setValues(GameDataType type, String header, ArrayList data)
    {
        this.type = type;
        this.header = header;
        this.data = data;
    }

    public void sendPost(HashMap eventData)
    {
        HashMap event;
        if(url == null)
            return;
        event = new HashMap();
        try
        {
            String hostname = InetAddress.getLocalHost().getHostName();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(hostname.getBytes());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ExceptionHandler.handleException(e, logger);
        }
        eventData.put("STS_DATA_UPLOAD_KEY", System.getenv("STS_DATA_UPLOAD_KEY"));
        event.putAll(eventData);
        String data = gson.toJson(event);
        logger.info((new StringBuilder()).append("UPLOADING TO LEADER BOARD: ").append(data).toString());
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        com.badlogic.gdx.Net.HttpRequest httpRequest = requestBuilder.newRequest().method("POST").url(url).header("Content-Type", "application/json").header("Accept", "application/json").build();
        httpRequest.setContent(data);
        Gdx.net.sendHttpRequest(httpRequest, new com.badlogic.gdx.Net.HttpResponseListener() {

            public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpResponse)
            {
                if(Settings.isDev)
                    BotDataUploader.logger.info((new StringBuilder()).append("Bot Data Upload: http request response: ").append(httpResponse.getStatus().getStatusCode()).append(" ").append(httpResponse.getResultAsString()).toString());
            }

            public void failed(Throwable t)
            {
                if(Settings.isDev)
                    BotDataUploader.logger.info((new StringBuilder()).append("Bot Data Upload: http request failed: ").append(t.toString()).toString());
            }

            public void cancelled()
            {
                if(Settings.isDev)
                    BotDataUploader.logger.info("Bot Data Upload: http request cancelled.");
            }

            final BotDataUploader this$0;

            
            {
                this.this$0 = BotDataUploader.this;
                super();
            }
        }
);
        return;
    }

    private void sendData(String table, String header, ArrayList entries)
    {
        HashMap data = new HashMap(entries.size() + 1);
        data.put("event type", (new StringBuilder()).append(table).append(" data update").toString());
        data.put("header", header);
        data.put("data", entries);
        sendPost(data);
    }

    public static void uploadDataAsync(GameDataType type, String header, ArrayList data)
    {
        BotDataUploader poster = new BotDataUploader();
        poster.setValues(type, header, data);
        Thread t = new Thread(poster);
        t.setName("LeaderboardPoster");
        t.start();
    }

    public void run()
    {
        static class _cls2
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType = new int[GameDataType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType[GameDataType.CARD_DATA.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType[GameDataType.RELIC_DATA.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType[GameDataType.ENEMY_DATA.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType[GameDataType.POTION_DATA.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType[GameDataType.DAILY_MOD_DATA.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType[GameDataType.BLIGHT_DATA.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$BotDataUploader$GameDataType[GameDataType.KEYWORD_DATA.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
            }
        }

        switch(_cls2..SwitchMap.com.megacrit.cardcrawl.metrics.BotDataUploader.GameDataType[type.ordinal()])
        {
        case 1: // '\001'
            sendData("card", header, data);
            break;

        case 2: // '\002'
            sendData("relic", header, data);
            break;

        case 3: // '\003'
            sendData("enemy", header, data);
            break;

        case 4: // '\004'
            sendData("potion", header, data);
            break;

        case 5: // '\005'
            sendData("daily mod", header, data);
            break;

        case 6: // '\006'
            sendData("blight", header, data);
            break;

        case 7: // '\007'
            sendData("keywords", header, data);
            break;

        default:
            logger.info((new StringBuilder()).append("Unspecified/deprecated LeaderboardPosterType: ").append(type.name()).append(" in run()").toString());
            break;
        }
    }

    public static void uploadKeywordData()
    {
        TreeMap keywords = GameDictionary.keywords;
        if(keywords.isEmpty())
            GameDictionary.initialize();
        ArrayList data = new ArrayList();
        String name;
        for(Iterator iterator = keywords.keySet().iterator(); iterator.hasNext(); data.add(String.format("%s\t%s", new Object[] {
    name, keywords.get(name)
})))
            name = (String)iterator.next();

        uploadDataAsync(GameDataType.KEYWORD_DATA, "name\ttext", data);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/metrics/BotDataUploader.getName());
    private Gson gson;
    private String url;
    private GameDataType type;
    private String header;
    private ArrayList data;


}
