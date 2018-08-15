# AndroidAdvanceDemo

> 不定时更新中...

Question：这个仓库有什么东西？
> 这个仓库都是一些手写的Android进阶框架，如：IOC框架、图片加载框架、网络请求框架等。

Question：为什么要弄这个仓库？
> 现在项目中网络请求用 Retrofit 或者 Okhttp，图片加载 Glide之类的，用了好几年，一直都处于使用阶段。
每次看源码看了好久，过一段时间就忘了，所以想自己动手把这些框架都写一遍。

Question：动手撸一遍框架有什么用？
> 工作大部分时间都是写业务代码，写久了都差不多，如果一直这样，时间再长也不可能晋升到高级工程师。
自己动手撸一遍框架可以提升自己的架构设计思想、设计模式的熟练度，更轻松的阅读流行开源框架的源码，
简直就是好处多多。


PS：
> 这个仓库中很有多的代码非本人所写，我只是来收集用来学习，感谢网上各路大神的无私贡献。

### AOP编程
1. AOP编程检查权限是否开启
2. AOP编程检查网络状态是否可用
3. AOP检查用户登录
4. AOP用户行为统计
5. 待开发

### 手写IOC框架
```
    @ViewById(R.id.tv_ioc)
    private TextView tvIoc;

    @StringById(R.string.ioc)
    private String text;

    @ColorById(R.color.colorAccent)
    private int color;

    @ContentViewById(R.layout.activity_main)

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn5})
    @OnLongClick({R.id.btn4})
    @CheckNet()
    public void openIoc(View view) {}
```

### 手写图片加载框架
> 主要来自何洪辉大神：
https://blog.csdn.net/column/details/android-imageloader.html

初始化
```
    private void initImageLoader() {
        ImageLoaderConfig config = new ImageLoaderConfig()
                .setLoadingPlaceholder(R.mipmap.loading)
                .setNotFoundPlaceholder(R.mipmap.not_found)
                .setCache(new DoubleCache(this))
                .setThreadCount(4)
                .setLoadPolicy(new ReversePolicy());
        SimpleImageLoader.getInstance().init(config);
    }
```

销毁
```
    @Override
    protected void onDestroy() {
        SimpleImageLoader.getInstance().stop();
        super.onDestroy();
    }
```

加载图片
```
    SimpleImageLoader.getInstance().displayImage(imageview,getItem(position));
```