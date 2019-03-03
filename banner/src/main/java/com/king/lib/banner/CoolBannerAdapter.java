package com.king.lib.banner;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2019/3/1 9:31
 */
public abstract class CoolBannerAdapter<T> extends PagerAdapter  {

    protected List<T> list;

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    @Override
    public int getCount() {
        return list == null ? 0:list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        BannerLog.e("position=" + position);
        // container.getContext()拿到的context是ContextThemeWrapper，有的控件需要通过activity的context构造，因此，这里取activity的context
        View view = LayoutInflater.from(getContext(container)).inflate(getLayoutRes(), null);
        onBindView(view, position, list.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        BannerLog.e("position=" + position);
        container.removeView((View) object);
    }

    protected abstract int getLayoutRes();

    protected abstract void onBindView(View view, int position, T bean);


    /**
     * try get host activity from view.
     * views hosted on floating window like dialog and toast will sure return null.
     * @return host activity; or null if not available
     */
    public static Context getContext(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
