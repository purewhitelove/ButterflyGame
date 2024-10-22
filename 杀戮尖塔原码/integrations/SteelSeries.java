// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SteelSeries.java

package com.megacrit.cardcrawl.integrations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import java.io.Reader;
import java.nio.file.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SteelSeries
{

    public SteelSeries()
    {
        timeAtLastHealthcheck = 0L;
        String program_data = System.getenv("PROGRAMDATA");
        Path winPath = Paths.get((new StringBuilder()).append(program_data).append("/SteelSeries/SteelSeries Engine 3/coreProps.json").toString(), new String[0]);
        Path macPath = Paths.get("/Library/Application Support/SteelSeries Engine 3/coreProps.json", new String[0]);
        Boolean winExists = Boolean.valueOf(Files.exists(winPath, new LinkOption[0]));
        Boolean macExists = Boolean.valueOf(Files.exists(macPath, new LinkOption[0]));
        isEnabled = Boolean.valueOf(winExists.booleanValue() || macExists.booleanValue());
        logger.info((new StringBuilder()).append("enabled=").append(isEnabled).toString());
        if(!isEnabled.booleanValue())
            return;
        String _url = winExists.booleanValue() ? getUrl(winPath) : getUrl(macPath);
        if(_url != null)
            url = (new StringBuilder()).append("http://").append(_url).toString();
        else
            logger.info("ERROR: url is null!");
        register();
    }

    private String getUrl(Path path)
    {
        Gson gson = new Gson();
        try
        {
            Reader reader = Files.newBufferedReader(path);
            Map map = (Map)gson.fromJson(reader, java/util/Map);
            reader.close();
            return (String)map.get("address");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        isEnabled = Boolean.valueOf(false);
        return null;
    }

    public void update()
    {
        if(System.currentTimeMillis() - timeAtLastHealthcheck > 14000L)
        {
            doHealthCheck();
            timeAtLastHealthcheck = System.currentTimeMillis();
        }
    }

    private void doHealthCheck()
    {
        if(!isEnabled.booleanValue())
        {
            return;
        } else
        {
            Map data = new HashMap();
            data.put("game", "SLAY_THE_SPIRE");
            sendPost((new StringBuilder()).append(url).append("/game_heartbeat").toString(), data, new com.badlogic.gdx.Net.HttpResponseListener() {

                public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpresponse)
                {
                }

                public void failed(Throwable t)
                {
                    logger.info("Healthcheck failed.");
                    isEnabled = Boolean.valueOf(false);
                }

                public void cancelled()
                {
                }

                final SteelSeries this$0;

            
            {
                this.this$0 = SteelSeries.this;
                super();
            }
            }
);
            return;
        }
    }

    private void register()
    {
        if(!isEnabled.booleanValue())
        {
            return;
        } else
        {
            Map data = new HashMap();
            data.put("game", "SLAY_THE_SPIRE");
            data.put("game_display_name", "Slay the Spire");
            data.put("developer", "MEGACRIT");
            sendPost((new StringBuilder()).append(url).append("/game_metadata").toString(), data, new com.badlogic.gdx.Net.HttpResponseListener() {

                public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpResponse)
                {
                    create_event_handler();
                }

                public void failed(Throwable t)
                {
                    logger.info("Steel Series service not running.");
                    isEnabled = Boolean.valueOf(false);
                }

                public void cancelled()
                {
                    logger.info("http request cancelled.");
                }

                final SteelSeries this$0;

            
            {
                this.this$0 = SteelSeries.this;
                super();
            }
            }
);
            return;
        }
    }

    private Map create_event_map(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass character, Map color)
    {
        Map c1 = new HashMap();
        c1.put("red", Integer.valueOf(0));
        c1.put("green", Integer.valueOf(0));
        c1.put("blue", Integer.valueOf(0));
        Map gradient = new HashMap();
        gradient.put("zero", c1);
        gradient.put("hundred", color);
        Map colorConfig = new HashMap();
        colorConfig.put("gradient", gradient);
        Map keyboardHandler = new HashMap();
        keyboardHandler.put("device-type", "keyboard");
        keyboardHandler.put("zone", "all");
        keyboardHandler.put("color", colorConfig);
        keyboardHandler.put("mode", "percent");
        Map mouseHandler = new HashMap();
        mouseHandler.put("device-type", "mouse");
        mouseHandler.put("zone", "all");
        mouseHandler.put("color", colorConfig);
        mouseHandler.put("mode", "percent");
        List handlers = new ArrayList();
        handlers.add(keyboardHandler);
        handlers.add(mouseHandler);
        Map data = new HashMap();
        data.put("game", "SLAY_THE_SPIRE");
        data.put("event", character.toString());
        data.put("min_value", Integer.valueOf(0));
        data.put("max_value", Integer.valueOf(100));
        data.put("icon_id", Integer.valueOf(0));
        data.put("handlers", handlers);
        return data;
    }

    private com.badlogic.gdx.Net.HttpResponseListener newEventHandlerListener()
    {
        return new com.badlogic.gdx.Net.HttpResponseListener() {

            public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpresponse)
            {
            }

            public void failed(Throwable t)
            {
                logger.info("Steel Series service not running.");
                isEnabled = Boolean.valueOf(false);
            }

            public void cancelled()
            {
                logger.info("http request cancelled.");
            }

            final SteelSeries this$0;

            
            {
                this.this$0 = SteelSeries.this;
                super();
            }
        }
;
    }

    private void create_event_handler()
    {
        if(!isEnabled.booleanValue())
        {
            return;
        } else
        {
            Map ironclad_color = new HashMap();
            ironclad_color.put("red", Integer.valueOf(255));
            ironclad_color.put("green", Integer.valueOf(0));
            ironclad_color.put("blue", Integer.valueOf(0));
            Map eventMap = create_event_map(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD, ironclad_color);
            sendPost((new StringBuilder()).append(url).append("/bind_game_event").toString(), eventMap, newEventHandlerListener());
            Map silent_color = new HashMap();
            silent_color.put("red", Integer.valueOf(0));
            silent_color.put("green", Integer.valueOf(255));
            silent_color.put("blue", Integer.valueOf(0));
            eventMap = create_event_map(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT, silent_color);
            sendPost((new StringBuilder()).append(url).append("/bind_game_event").toString(), eventMap, newEventHandlerListener());
            Map defect_color = new HashMap();
            defect_color.put("red", Integer.valueOf(0));
            defect_color.put("green", Integer.valueOf(0));
            defect_color.put("blue", Integer.valueOf(255));
            eventMap = create_event_map(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT, defect_color);
            sendPost((new StringBuilder()).append(url).append("/bind_game_event").toString(), eventMap, newEventHandlerListener());
            Map watcher_color = new HashMap();
            watcher_color.put("red", Integer.valueOf(148));
            watcher_color.put("green", Integer.valueOf(0));
            watcher_color.put("blue", Integer.valueOf(211));
            eventMap = create_event_map(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER, watcher_color);
            sendPost((new StringBuilder()).append(url).append("/bind_game_event").toString(), eventMap, newEventHandlerListener());
            return;
        }
    }

    public void event_character_chosen(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass character)
    {
        if(!isEnabled.booleanValue())
        {
            return;
        } else
        {
            Map value = new HashMap();
            value.put("value", Integer.valueOf(100));
            Map data = new HashMap();
            data.put("game", "SLAY_THE_SPIRE");
            data.put("event", character.toString());
            data.put("data", value);
            sendPost((new StringBuilder()).append(url).append("/game_event").toString(), data, new com.badlogic.gdx.Net.HttpResponseListener() {

                public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpresponse)
                {
                }

                public void failed(Throwable t)
                {
                    logger.info("Steel Series service not running.");
                    isEnabled = Boolean.valueOf(false);
                }

                public void cancelled()
                {
                    logger.info("http request cancelled.");
                }

                final SteelSeries this$0;

            
            {
                this.this$0 = SteelSeries.this;
                super();
            }
            }
);
            return;
        }
    }

    private void sendPost(String url, Map data, com.badlogic.gdx.Net.HttpResponseListener listener)
    {
        Gson gson = new Gson();
        String content = gson.toJson(data);
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        com.badlogic.gdx.Net.HttpRequest httpRequest = requestBuilder.newRequest().method("POST").url(url).header("Content-Type", "application/json").header("Accept", "application/json").header("User-Agent", (new StringBuilder()).append("sts/").append(CardCrawlGame.TRUE_VERSION_NUM).toString()).timeout(1).build();
        httpRequest.setContent(content);
        Gdx.net.sendHttpRequest(httpRequest, listener);
    }

    private final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/integrations/SteelSeries.getName());
    private final String gameName = "SLAY_THE_SPIRE";
    public Boolean isEnabled;
    private String url;
    private long timeAtLastHealthcheck;


}
