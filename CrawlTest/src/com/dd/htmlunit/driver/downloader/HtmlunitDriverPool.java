/**
 * 
 */
package com.dd.htmlunit.driver.downloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * @author meide.zhangmd
 *
 */
public class HtmlunitDriverPool {
	private Logger logger = Logger.getLogger(getClass());

    private final static int DEFAULT_CAPACITY = 5;

    private final int capacity;

    private final static int STAT_RUNNING = 1;

    private final static int STAT_CLODED = 2;

    private AtomicInteger stat = new AtomicInteger(STAT_RUNNING);

    /**
     * store webDrivers created
     */
    private List<HtmlUnitDriver> webClientList = Collections.synchronizedList(new ArrayList<HtmlUnitDriver>());

    /**
     * store webDrivers available
     */
    private BlockingDeque<HtmlUnitDriver> innerQueue = new LinkedBlockingDeque<HtmlUnitDriver>();

    public HtmlunitDriverPool(int capacity) {
        this.capacity = capacity;
    }

    public HtmlunitDriverPool() {
        this(DEFAULT_CAPACITY);
    }

    public HtmlUnitDriver get() throws InterruptedException {
        checkRunning();
        HtmlUnitDriver poll = innerQueue.poll();
        if (poll != null) {
            return poll;
        }
        if (webClientList.size() < capacity) {
            synchronized (webClientList) {
                if (webClientList.size() < capacity) {
                	HtmlUnitDriver e = new HtmlUnitDriver(BrowserVersion.FIREFOX_24);
                    innerQueue.add(e);
                    webClientList.add(e);
                }
            }

        }
        return innerQueue.take();
    }

    public void returnToPool(HtmlUnitDriver webClient) {
        checkRunning();
        innerQueue.add(webClient);
    }

    protected void checkRunning() {
        if (!stat.compareAndSet(STAT_RUNNING, STAT_RUNNING)) {
            throw new IllegalStateException("Already closed!");
        }
    }

    public void closeAll() {
        boolean b = stat.compareAndSet(STAT_RUNNING, STAT_CLODED);
        if (!b) {
            throw new IllegalStateException("Already closed!");
        }
        if(webClientList ==null) return;
        for (HtmlUnitDriver webClient : webClientList) {
            logger.info("Quit webDriver" + webClient);
            webClient.close();
            webClient=null;
        }
    }
}
