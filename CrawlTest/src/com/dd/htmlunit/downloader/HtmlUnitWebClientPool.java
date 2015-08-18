/**
 * 
 */
package com.dd.htmlunit.downloader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * @author meide.zhangmd
 *
 */
public class HtmlUnitWebClientPool {
	private Logger logger = Logger.getLogger(getClass());

    private final static int DEFAULT_CAPACITY = 5;

    private final int capacity;

    private final static int STAT_RUNNING = 1;

    private final static int STAT_CLODED = 2;

    private AtomicInteger stat = new AtomicInteger(STAT_RUNNING);

    /**
     * store webDrivers created
     */
    private List<WebClient> webClientList = Collections.synchronizedList(new ArrayList<WebClient>());

    /**
     * store webDrivers available
     */
    private BlockingDeque<WebClient> innerQueue = new LinkedBlockingDeque<WebClient>();

    public HtmlUnitWebClientPool(int capacity) {
        this.capacity = capacity;
    }

    public HtmlUnitWebClientPool() {
        this(DEFAULT_CAPACITY);
    }

    public WebClient get() throws InterruptedException {
        checkRunning();
        WebClient poll = innerQueue.poll();
        if (poll != null) {
            return poll;
        }
        if (webClientList.size() < capacity) {
            synchronized (webClientList) {
                if (webClientList.size() < capacity) {
                	WebClient e = new WebClient(BrowserVersion.FIREFOX_24);
                    innerQueue.add(e);
                    webClientList.add(e);
                }
            }

        }
        return innerQueue.take();
    }

    public void returnToPool(WebClient webClient) {
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
        for (WebClient webClient : webClientList) {
            logger.info("Quit webDriver" + webClient);
            webClient.closeAllWindows();
            webClient=null;
        }
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
