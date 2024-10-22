// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Metrics.java

package com.megacrit.cardcrawl.metrics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.metrics:
//            MetricData

public class Metrics
    implements Runnable
{
    public static final class MetricRequestType extends Enum
    {

        public static MetricRequestType[] values()
        {
            return (MetricRequestType[])$VALUES.clone();
        }

        public static MetricRequestType valueOf(String name)
        {
            return (MetricRequestType)Enum.valueOf(com/megacrit/cardcrawl/metrics/Metrics$MetricRequestType, name);
        }

        public static final MetricRequestType UPLOAD_METRICS;
        public static final MetricRequestType UPLOAD_CRASH;
        public static final MetricRequestType NONE;
        private static final MetricRequestType $VALUES[];

        static 
        {
            UPLOAD_METRICS = new MetricRequestType("UPLOAD_METRICS", 0);
            UPLOAD_CRASH = new MetricRequestType("UPLOAD_CRASH", 1);
            NONE = new MetricRequestType("NONE", 2);
            $VALUES = (new MetricRequestType[] {
                UPLOAD_METRICS, UPLOAD_CRASH, NONE
            });
        }

        private MetricRequestType(String s, int i)
        {
            super(s, i);
        }
    }


    public Metrics()
    {
        params = new HashMap();
        gson = new Gson();
        monsters = null;
    }

    public void setValues(boolean death, boolean trueVictor, MonsterGroup monsters, MetricRequestType type)
    {
        this.death = death;
        trueVictory = trueVictor;
        this.monsters = monsters;
        this.type = type;
    }

    private void sendPost(String s)
    {
    }

    private void addData(Object key, Object value)
    {
        params.put(key, value);
    }

    private void sendPost(String url, final String fileToDelete)
    {
        HashMap event = new HashMap();
        event.put("event", params);
        if(Settings.isBeta)
            event.put("host", CardCrawlGame.playerName);
        else
            event.put("host", CardCrawlGame.alias);
        event.put("time", Long.valueOf(System.currentTimeMillis() / 1000L));
        String data = gson.toJson(event);
        logger.info((new StringBuilder()).append("UPLOADING METRICS TO: url=").append(url).append(",data=").append(data).toString());
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        com.badlogic.gdx.Net.HttpRequest httpRequest = requestBuilder.newRequest().method("POST").url(url).header("Content-Type", "application/json").header("Accept", "application/json").header("User-Agent", "curl/7.43.0").build();
        httpRequest.setContent(data);
        Gdx.net.sendHttpRequest(httpRequest, new com.badlogic.gdx.Net.HttpResponseListener() {

            public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpResponse)
            {
                Metrics.logger.info((new StringBuilder()).append("Metrics: http request response: ").append(httpResponse.getResultAsString()).toString());
                if(fileToDelete != null)
                    Gdx.files.local(fileToDelete).delete();
            }

            public void failed(Throwable t)
            {
                Metrics.logger.info((new StringBuilder()).append("Metrics: http request failed: ").append(t.toString()).toString());
            }

            public void cancelled()
            {
                Metrics.logger.info("Metrics: http request cancelled.");
            }

            final String val$fileToDelete;
            final Metrics this$0;

            
            {
                this.this$0 = Metrics.this;
                fileToDelete = s;
                super();
            }
        }
);
    }

    private void gatherAllData(boolean death, boolean trueVictor, MonsterGroup monsters)
    {
        addData("play_id", UUID.randomUUID().toString());
        addData("build_version", CardCrawlGame.TRUE_VERSION_NUM);
        addData("seed_played", Settings.seed.toString());
        addData("chose_seed", Boolean.valueOf(Settings.seedSet));
        addData("seed_source_timestamp", Long.valueOf(Settings.seedSourceTimestamp));
        addData("is_daily", Boolean.valueOf(Settings.isDailyRun));
        addData("special_seed", Settings.specialSeed);
        if(ModHelper.enabledMods.size() > 0)
            addData("daily_mods", ModHelper.getEnabledModIDs());
        addData("is_trial", Boolean.valueOf(Settings.isTrial));
        addData("is_endless", Boolean.valueOf(Settings.isEndless));
        if(death)
        {
            AbstractPlayer player = AbstractDungeon.player;
            CardCrawlGame.metricData.current_hp_per_floor.add(Integer.valueOf(player.currentHealth));
            CardCrawlGame.metricData.max_hp_per_floor.add(Integer.valueOf(player.maxHealth));
            CardCrawlGame.metricData.gold_per_floor.add(Integer.valueOf(player.gold));
        }
        addData("is_ascension_mode", Boolean.valueOf(AbstractDungeon.isAscensionMode));
        addData("ascension_level", Integer.valueOf(AbstractDungeon.ascensionLevel));
        addData("neow_bonus", CardCrawlGame.metricData.neowBonus);
        addData("neow_cost", CardCrawlGame.metricData.neowCost);
        addData("is_beta", Boolean.valueOf(Settings.isBeta));
        addData("is_prod", Boolean.valueOf(Settings.isDemo));
        addData("victory", Boolean.valueOf(!death));
        addData("floor_reached", Integer.valueOf(AbstractDungeon.floorNum));
        if(trueVictor)
            addData("score", Integer.valueOf(VictoryScreen.calcScore(!death)));
        else
            addData("score", Integer.valueOf(DeathScreen.calcScore(!death)));
        lastPlaytimeEnd = System.currentTimeMillis() / 1000L;
        addData("timestamp", Long.valueOf(lastPlaytimeEnd));
        addData("local_time", timestampFormatter.format(Calendar.getInstance().getTime()));
        addData("playtime", Long.valueOf((long)CardCrawlGame.playtime));
        addData("player_experience", Long.valueOf(Settings.totalPlayTime));
        addData("master_deck", AbstractDungeon.player.masterDeck.getCardIdsForMetrics());
        addData("relics", AbstractDungeon.player.getRelicNames());
        addData("gold", Integer.valueOf(AbstractDungeon.player.gold));
        addData("campfire_rested", Integer.valueOf(CardCrawlGame.metricData.campfire_rested));
        addData("campfire_upgraded", Integer.valueOf(CardCrawlGame.metricData.campfire_upgraded));
        addData("purchased_purges", Integer.valueOf(CardCrawlGame.metricData.purchased_purges));
        addData("potions_floor_spawned", CardCrawlGame.metricData.potions_floor_spawned);
        addData("potions_floor_usage", CardCrawlGame.metricData.potions_floor_usage);
        addData("current_hp_per_floor", CardCrawlGame.metricData.current_hp_per_floor);
        addData("max_hp_per_floor", CardCrawlGame.metricData.max_hp_per_floor);
        addData("gold_per_floor", CardCrawlGame.metricData.gold_per_floor);
        addData("path_per_floor", CardCrawlGame.metricData.path_per_floor);
        addData("path_taken", CardCrawlGame.metricData.path_taken);
        addData("items_purchased", CardCrawlGame.metricData.items_purchased);
        addData("item_purchase_floors", CardCrawlGame.metricData.item_purchase_floors);
        addData("items_purged", CardCrawlGame.metricData.items_purged);
        addData("items_purged_floors", CardCrawlGame.metricData.items_purged_floors);
        addData("character_chosen", AbstractDungeon.player.chosenClass.name());
        addData("card_choices", CardCrawlGame.metricData.card_choices);
        addData("event_choices", CardCrawlGame.metricData.event_choices);
        addData("boss_relics", CardCrawlGame.metricData.boss_relics);
        addData("damage_taken", CardCrawlGame.metricData.damage_taken);
        addData("potions_obtained", CardCrawlGame.metricData.potions_obtained);
        addData("relics_obtained", CardCrawlGame.metricData.relics_obtained);
        addData("campfire_choices", CardCrawlGame.metricData.campfire_choices);
        addData("circlet_count", Integer.valueOf(AbstractDungeon.player.getCircletCount()));
        Prefs pref = AbstractDungeon.player.getPrefs();
        int numVictory = pref.getInteger("WIN_COUNT", 0);
        int numDeath = pref.getInteger("LOSE_COUNT", 0);
        if(numVictory <= 0)
            addData("win_rate", Float.valueOf(0.0F));
        else
            addData("win_rate", Integer.valueOf(numVictory / (numDeath + numVictory)));
        if(death && monsters != null)
            addData("killed_by", AbstractDungeon.lastCombatMetricKey);
        else
            addData("killed_by", null);
    }

    private void gatherAllDataAndSend(boolean death, boolean trueVictor, MonsterGroup monsters)
    {
        if(DeathScreen.shouldUploadMetricData())
        {
            gatherAllData(death, trueVictor, monsters);
            sendPost(null);
        }
    }

    public void gatherAllDataAndSave(boolean death, boolean trueVictor, MonsterGroup monsters)
    {
        gatherAllData(death, trueVictor, monsters);
        String data = gson.toJson(params);
        FileHandle file;
        if(!Settings.isDailyRun)
        {
            String local_runs_save_path = (new StringBuilder()).append("runs").append(File.separator).toString();
            switch(CardCrawlGame.saveSlot)
            {
            default:
                local_runs_save_path = (new StringBuilder()).append(local_runs_save_path).append(CardCrawlGame.saveSlot).append("_").toString();
                // fall through

            case 0: // '\0'
                local_runs_save_path = (new StringBuilder()).append(local_runs_save_path).append(AbstractDungeon.player.chosenClass.name()).append(File.separator).append(lastPlaytimeEnd).append(".run").toString();
                break;
            }
            file = Gdx.files.local(local_runs_save_path);
        } else
        {
            String tmpPath = (new StringBuilder()).append("runs").append(File.separator).toString();
            switch(CardCrawlGame.saveSlot)
            {
            default:
                tmpPath = (new StringBuilder()).append(tmpPath).append(CardCrawlGame.saveSlot).append("_").toString();
                // fall through

            case 0: // '\0'
                file = Gdx.files.local((new StringBuilder()).append(tmpPath).append("DAILY").append(File.separator).append(lastPlaytimeEnd).append(".run").toString());
                break;
            }
        }
        file.writeString(data, false);
        removeExcessRunFiles();
    }

    private void removeExcessRunFiles()
    {
        if(!Settings.isConsoleBuild)
            return;
        FileHandle fh = Gdx.files.local("runs");
        FileHandle allFolders[] = fh.list();
        HashMap map = new HashMap();
        List runNames = new ArrayList();
        FileHandle afilehandle[] = allFolders;
        int numFilesToDelete = afilehandle.length;
        for(int k = 0; k < numFilesToDelete; k++)
        {
            FileHandle i = afilehandle[k];
            FileHandle runs[] = i.list("run");
            FileHandle afilehandle1[] = runs;
            int l = afilehandle1.length;
            for(int i1 = 0; i1 < l; i1++)
            {
                FileHandle j = afilehandle1[i1];
                runNames.add(j.name());
                map.put(j.name(), j);
            }

        }

        int excessFileThreshold = 500;
        numFilesToDelete = runNames.size() - excessFileThreshold;
        if(runNames.size() < excessFileThreshold)
            return;
        Collections.sort(runNames);
        for(int i = 0; i < numFilesToDelete; i++)
            if(map.containsKey(runNames.get(i)))
            {
                logger.info((new StringBuilder()).append("DELETING EXCESS RUN: ").append(map.get(((String)runNames.get(i)).toString())).toString());
                ((FileHandle)map.get(runNames.get(i))).delete();
            }

    }

    public void run()
    {
        static class _cls2
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$metrics$Metrics$MetricRequestType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$metrics$Metrics$MetricRequestType = new int[MetricRequestType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$Metrics$MetricRequestType[MetricRequestType.UPLOAD_CRASH.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$metrics$Metrics$MetricRequestType[MetricRequestType.UPLOAD_METRICS.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls2..SwitchMap.com.megacrit.cardcrawl.metrics.Metrics.MetricRequestType[type.ordinal()])
        {
        case 1: // '\001'
            if(!Settings.isModded)
                gatherAllDataAndSend(death, false, monsters);
            break;

        case 2: // '\002'
            if(!Settings.isModded)
                gatherAllDataAndSend(death, trueVictory, monsters);
            break;

        default:
            logger.info((new StringBuilder()).append("Unspecified MetricRequestType: ").append(type.name()).append(" in run()").toString());
            break;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/metrics/Metrics.getName());
    private HashMap params;
    private Gson gson;
    private long lastPlaytimeEnd;
    public static final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
    public boolean death;
    public boolean trueVictory;
    public MonsterGroup monsters;
    public MetricRequestType type;


}
