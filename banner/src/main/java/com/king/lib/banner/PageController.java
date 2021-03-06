package com.king.lib.banner;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * Desc: 处理viewpager的循环切换页面以及自动轮播
 * 循环滚动的思路：
 * 假设adapter有4个item  a, b, c, d
 * 那么在首位加一个末尾item, 在末尾加一个首位item，即以下对应关系
 * 内容:  d, a, b, c, d, a
 * index: 0, 1, 2, 3, 4, 5
 * 从左向右滑动时，若滑到index=5, 则设置viewpager.setCurrentItem(1, false)(一定要设置滚动动画为false)
 * 从左向右滑动时，若滑到index=0, 则设置viewpager.setCurrentItem(4, false)
 * 这样就实现了循环滚动
 *
 * @author：Jing Yang
 * @date: 2019/3/1 10:00
 */
public class PageController implements ViewPager.OnPageChangeListener {

    private final int MSG_NEXT = 1;

    private final CoolBanner banner;

    private Thread timerThread;

    private int timeCount;

    private OnBannerPageListener onBannerPageListener;

    public PageController(CoolBanner coolBanner) {
        this.banner = coolBanner;
        banner.addOnPageChangeListener(this);
    }

    /**
     * 主动设置page时，重置计时
     * @param realPage 数据源的index
     */
    public void setPage(int realPage) {
        setPageByAdapterIndex(realPage + 1);
    }

    /**
     * 主动设置page时，重置计时
     * @param page 在adapter中的实际index
     */
    public void setPageByAdapterIndex(int page) {
        resetTimer();
        if (page < banner.getAdapter().getCount()) {
            banner.setCurrentItem(page, false);
            banner.scrollBy(1, 1);
        }
    }

    public void nextPage() {
        int index = banner.getCurrentItem();
        index ++;
        BannerLog.e("index=" + index);
        if (index < banner.getAdapter().getCount()) {
            banner.setCurrentItem(index, true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int index) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // onPageSelected是在release的瞬间就执行了，所以为了达到更好的切换效果，在SCROLL_STATE_IDLE的状态下处理循环
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            int index = banner.getCurrentItem();
            BannerLog.e("index=" + index);
            indexChange(index);
        }
    }

    private void indexChange(int index) {
        // 至少两个item才需要循环滚动
        if (isLoop()) {
            if (index == 0) {
                int changeIndex = banner.getAdapter().getCount() - 2;
                BannerLog.e("changeIndex=" + changeIndex);
                banner.setCurrentItem(changeIndex, false);
                if (onBannerPageListener != null) {
                    onBannerPageListener.onSetPage(changeIndex - 1, changeIndex);
                }
            }
            else if (index == banner.getAdapter().getCount() - 1) {
                int changeIndex = 1;
                BannerLog.e("changeIndex=" + changeIndex);
                banner.setCurrentItem(changeIndex, false);
                if (onBannerPageListener != null) {
                    onBannerPageListener.onSetPage(changeIndex - 1, changeIndex);
                }
            }
            else {
                if (onBannerPageListener != null) {
                    onBannerPageListener.onSetPage(index - 1, index);
                }
            }
        }
        else {
            if (onBannerPageListener != null) {
                onBannerPageListener.onSetPage(index, index);
            }
        }
    }

    public boolean isLoop() {
        CoolBannerAdapter adapter = (CoolBannerAdapter) banner.getAdapter();
        // 1个以上支持循环滚动
        return banner.isLoop() && adapter.getList() != null && adapter.getList().size() > 1;
    }

    public void onAdapterSet() {
        CoolBannerAdapter adapter = (CoolBannerAdapter) banner.getAdapter();
        // 首末位加上循环措施
        if (isLoop()) {
            Object first = adapter.getList().get(0);
            Object last = adapter.getList().get(adapter.getList().size() - 1);
            adapter.getList().add(0, last);
            adapter.getList().add(first);
            // notifyDataSetChanged经实测在较低性能的设备上会造成刷新滞后于setCurrentItem
            // 因此会引起setCurrentItem(1)实际定位到了位置2
//                adapter.notifyDataSetChanged();
            banner.updateAdapter(adapter);
            banner.setCurrentItem(1);
        }
    }

    /**
     * 开始自动轮播
     */
    public void startAutoPlay() {
        stopAutoPlay();
        if (banner.getAdapter() != null && banner.getAdapter().getCount() > 1) {
            timerThread = new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep(1000);
                            timeCount += 1000;
                            BannerLog.e("timeCount=" + timeCount);
                            if (timeCount >= banner.getDuration()) {
                                timeCount = 0;
                                handler.sendEmptyMessage(MSG_NEXT);
                            }
                        }
                    } catch (InterruptedException e) {}
                }
            };
            timerThread.start();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_NEXT:
                    nextPage();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 停止自动轮播
     */
    public void stopAutoPlay() {
        timeCount = 0;
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    /**
     * 暂停切换计时
     */
    public void resetTimer() {
        timeCount = 0;
    }

    public void setOnBannerPageListener(OnBannerPageListener onBannerPageListener) {
        this.onBannerPageListener = onBannerPageListener;
    }

    public int getCurrentDataPosition() {
        if (isLoop()) {
            int index = banner.getCurrentItem();
            if (index == 0) {
                int changeIndex = banner.getAdapter().getCount() - 2;
                return changeIndex - 1;
            }
            else if (index == banner.getAdapter().getCount() - 1) {
                int changeIndex = 1;
                return changeIndex - 1;
            }
            else {
                return index - 1;
            }
        }
        else {
            return banner.getCurrentItem();
        }
    }
}
