package com.king.lib.banner;

import com.king.lib.banner.transformer.LMPageTransformer;
import com.king.lib.banner.transformer.TransitionEffect;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2019/3/1 10:16
 */
public class BannerFlipStyleProvider {

    public static final String[] ANIM_TYPES = new String[]{
            "defaultEffect", "alpha", "rotate", "cube", "flip", "accordion", "zoomFade",
            "fade", "zoomCenter", "zoomStack", "stack", "depth", "zoom", "zoomOut"/*, "parallax"*/
    };

    /**
     * 切换时的动画模式
     * @param position
     */
    public static void setPagerAnim(CoolBanner banner, int position){
        switch (position) {
            case 0:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Default));//Default
                break;
            case 1:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Alpha));//Alpha
                break;
            case 2:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Rotate));//Rotate
                break;
            case 3:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Cube));//Cube
                break;
            case 4:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Flip));//Flip
                break;
            case 5:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Accordion));//Accordion
                break;
            case 6:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.ZoomFade));//ZoomFade
                break;
            case 7:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Fade));//Fade
                break;
            case 8:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.ZoomCenter));//ZoomCenter
                break;
            case 9:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.ZoomStack));//ZoomStack
                break;
            case 10:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Stack));//Stack
                break;
            case 11:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Depth));//Depth
                break;
            case 12:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.Zoom));//Zoom
                break;
            case 13:
                banner.setPageTransformer(true, LMPageTransformer.getPageTransformer(TransitionEffect.ZoomOut));//ZoomOut
                break;
//            case 14:
//                lmBanners.setHoriZontalCustomTransformer(new ParallaxTransformer(R.id.id_image));//Parallax
//                break;

        }
    }

}
