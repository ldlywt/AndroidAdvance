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

## 库里面有什么东西
- AOP编程
- 手写IOC框架
- 手写图片加载框架
- 手写OkHttp框架
- DialogFragment封装
- Android全局异常捕获
- 手写EventBus框架
- 待添加

---

### AOP编程
1. AOP编程检查权限是否开启
2. AOP编程检查网络状态是否可用
3. AOP检查用户登录
4. AOP用户行为统计
5. 待开发

### 手写IOC框架
使用实例：
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

![image](https://github.com/ldlywt/AndroidAdvanceDemo/raw/master/images/手写IOC注解框架.png)

### 手写图片加载框架
> 主要来自何洪辉大神：
https://blog.csdn.net/column/details/android-imageloader.html

具体使用跟 Glide 差不多

![image](https://github.com/ldlywt/AndroidAdvanceDemo/raw/master/images/SimpleImageLoader的基本结构.png)
![image](https://github.com/ldlywt/AndroidAdvanceDemo/raw/master/images/SimpleImageLoader的工程结构.png)

### 手写OkHttp框架
> 主要来自 https://github.com/ouyangshengduo/SenduoHttp

![image](https://github.com/ldlywt/AndroidAdvanceDemo/raw/master/images/OkHttp工程结构.png)

- 实现跟 OkHttp 一样的调用
- 具体使用见 HttpFragment
- Okhttp的源码解析文章见：<https://www.jianshu.com/p/897964b79732>

### DialogFragment封装
Q：为什么要使用 DialogFragment？
- 继承自Fragment，拥有Fragment的生命周期。
- 当旋转屏幕和按下后退键时可以更好的管理其声明周期

PS:我见过很多的 Dialog 的库都把 Listview 或者 RecycleView 封装进去了，个人并不是很赞同这种做法。为了把列表封装进去，
将整个 lib 瞬间多了很多的代码，不同情况下的列表并不一样，也不能通用所用情况，还不如直接通过 `setView(recycleVIew)`
这中方式将列表 set 进去。

> 具体使用见 `DialogTestFragment`

### 手写 EventBus
XEventBus 就只有四个类，如下：

![image](https://github.com/ldlywt/AndroidAdvanceDemo/raw/master/images/XEventBus类.png)

- register()流程 和 post()流程

![image](https://github.com/ldlywt/AndroidAdvanceDemo/raw/master/images/EventBus流程.png)

- unRegister()流程
```
    //private Map<Object, List<SubscribeMethod>> mSubscribeMethodMap;
    public void unRegister(Object object) {
        if (mSubscribeMethodMap != null) {
            mSubscribeMethodMap.remove(object);
        }
    }
```

> 主要通过注解和反射来实现