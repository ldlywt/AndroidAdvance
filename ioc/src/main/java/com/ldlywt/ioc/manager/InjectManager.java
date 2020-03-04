package com.ldlywt.ioc.manager;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;
/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class InjectManager {

    public static void inject(Activity activity) {
        inject(new ViewManager(activity), activity);
    }

    public static void inject(Fragment fragment) {
        inject(new ViewManager(fragment), fragment);
    }

    public static void inject(View view) {
        inject(new ViewManager(view), view);
    }

    private static void inject(ViewManager viewManager, Object object) {
        InjectManagerService.injectContentView(viewManager, object);
        InjectManagerService.injectFieldById(viewManager, object);
        InjectManagerService.injectEvent(viewManager, object);
    }

}
