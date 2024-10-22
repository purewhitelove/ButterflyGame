// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimeLookup.java

package com.megacrit.cardcrawl.daily;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.daily:
//            TimeHelper

public class TimeLookup
{

    public TimeLookup()
    {
    }

    private static void makeHTTPReq(String url)
    {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        com.badlogic.gdx.Net.HttpRequest httpRequest = requestBuilder.newRequest().method("GET").url(url).header("Content-Type", "application/json").header("Accept", "application/json").header("User-Agent", "curl/7.43.0").timeout(5000).build();
        Gdx.net.sendHttpRequest(httpRequest, new com.badlogic.gdx.Net.HttpResponseListener() {

            public void handleHttpResponse(com.badlogic.gdx.Net.HttpResponse httpResponse)
            {
                String status = String.valueOf(httpResponse.getStatus().getStatusCode());
                String result = httpResponse.getResultAsString();
                if(!status.startsWith("2"))
                    TimeLookup.logger.info((new StringBuilder()).append("Query to sts-time-server failed: status_code=").append(status).append(" result=").append(result).toString());
                TimeLookup.logger.info((new StringBuilder()).append("Time server response: ").append(result).toString());
                long serverTime = Long.parseLong(result);
                TimeLookup.isDone = true;
                TimeHelper.setTime(serverTime, false);
            }

            public void failed(Throwable t)
            {
                TimeLookup.logger.info((new StringBuilder()).append("http request failed: ").append(t.toString()).toString());
                TimeLookup.logger.info((new StringBuilder()).append("retry count: ").append(TimeLookup.retryCount).toString());
                if(TimeLookup.retryCount.get() > 2)
                {
                    TimeLookup.logger.info("Failed to lookup time. Switching to OFFLINE MODE!");
                    long localTime = System.currentTimeMillis() / 1000L;
                    TimeHelper.setTime(localTime, true);
                    return;
                }
                long waitTime = (long)Math.pow(2D, TimeLookup.retryCount.get());
                TimeLookup.logger.info((new StringBuilder()).append("wait time: ").append(waitTime).toString());
                if(waitTime > 2L)
                    waitTime = 2L;
                TimeLookup.logger.info((new StringBuilder()).append("Retry ").append(TimeLookup.retryCount.get()).append(": waiting ").append(waitTime).append(" seconds for time lookup").toString());
                TimeLookup.retryCount.getAndIncrement();
                try
                {
                    Thread.sleep(waitTime * 1000L);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                    TimeLookup.logger.info("Thread interrupted!");
                }
                TimeLookup.makeHTTPReq("https://hyi3lwrhf5.execute-api.us-east-1.amazonaws.com/prod/time");
            }

            public void cancelled()
            {
                TimeLookup.logger.info("http request cancelled.");
            }

        }
);
    }

    public static void fetchDailyTimeAsync()
    {
        makeHTTPReq("https://hyi3lwrhf5.execute-api.us-east-1.amazonaws.com/prod/time");
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/daily/TimeLookup.getName());
    public static volatile boolean isDone = false;
    private static final String timeServer = "https://hyi3lwrhf5.execute-api.us-east-1.amazonaws.com/prod/time";
    private static volatile AtomicInteger retryCount = new AtomicInteger(1);
    private static final int MAX_RETRY = 2;
    private static final int WAIT_TIME_CAP = 2;




}
