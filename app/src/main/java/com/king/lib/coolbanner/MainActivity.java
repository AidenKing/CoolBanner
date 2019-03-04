package com.king.lib.coolbanner;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.king.lib.banner.BannerFlipStyleProvider;
import com.king.lib.banner.BannerLog;
import com.king.lib.banner.CoolBanner;
import com.king.lib.banner.CoolBannerAdapter;
import com.king.lib.banner.OnBannerPageListener;
import com.king.lib.banner.guide.GuideView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CoolBanner banner;
    private GuideView guideView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        banner = findViewById(R.id.banner);
        guideView = findViewById(R.id.guide_view);
        BannerFlipStyleProvider.setPagerAnim(banner, 3);
        // 设置非循环
//        banner.setLoop(false);
        // 设置不允许手动翻页
//        banner.setEnableSwitch(false);

        findViewById(R.id.btn_effect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainActivity.this)
                        .setItems(BannerFlipStyleProvider.ANIM_TYPES, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position) {
                                // 中途切换第一次会有问题，后面就好了
                                BannerFlipStyleProvider.setPagerAnim(banner, position);
                            }
                        })
                        .show();
            }
        });

        findViewById(R.id.btn_set_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int page = Integer.parseInt(((EditText) findViewById(R.id.et_page)).getText().toString());
                    banner.getController().setPage(page);
                } catch (Exception e) {}
            }
        });

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.demo_item1);
        list.add(R.drawable.demo_item2);
        list.add(R.drawable.demo_item3);
        list.add(R.drawable.demo_item6);
        list.add(R.drawable.demo_item7);
        guideView.setPointNumber(list.size());
//        bannerGuide.setGuideAsTextAtNum(3);
        guideView.setGuideTextGravity(Gravity.CENTER);

        final ItemAdapter adapter = new ItemAdapter();
        adapter.setList(list);
        banner.setAdapter(adapter);
        banner.startAutoPlay();

        banner.setOnBannerPageListener(new OnBannerPageListener() {
            @Override
            public void onSetPage(int page, int adapterIndex) {
                BannerLog.e("page=" + page + ", adapterIndex=" + adapterIndex);
                guideView.setFocusIndex(page);
            }
        });
    }

    @Override
    protected void onDestroy() {
        banner.stopAutoPlay();
        super.onDestroy();
    }

    private class ItemAdapter extends CoolBannerAdapter<Integer> {

        @Override
        protected int getLayoutRes() {
            return R.layout.adapter_banner;
        }

        @Override
        protected void onBindView(View view, int position, Integer resId) {
            ImageView image = view.findViewById(R.id.iv_image);
            image.setImageResource(resId);
        }
    }
}
