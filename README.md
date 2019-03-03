# CoolBanner

## Download

```
dependencies {
    compile 'com.king.lib:banner:1.0.0'
}
```
## Sample

use CoolBanner like this

```
    <com.king.lib.banner.CoolBanner
        android:id="@+id/banner"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:switchDuration="5000"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
        
    define adapter extends from CoolBannerAdapter<T>:
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
    
    banner.setAdapter(adapter);
    banner.startAutoPlay();

```



| attributes    | value      | explaination                                                 |
| ------------- | ---------- | ------------------------------------------------------------ |
| switchDuration| Integer    | define the interval time to switch page |

```
banner.setLoop(false); // enable or disable loop
banner.setEnableSwitch(false); // enable or disable touch event
```
