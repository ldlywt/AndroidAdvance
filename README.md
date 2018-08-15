# AndroidAdvanceDemo
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