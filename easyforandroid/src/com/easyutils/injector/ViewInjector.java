package com.easyutils.injector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.easyutils.injector.annotation.ContentView;
import com.easyutils.injector.annotation.ViewInject;

/**
 * ViewInjector 2016-6-15 19:40
 * @author KCJ
 *
 */
public class ViewInjector {

	private static final HashSet<Class<?>> IGNORED = new HashSet<Class<?>>();
	
	static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(android.app.Fragment.class);
        try {
            IGNORED.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable ignored) {
        }
    }
	
	private static final Object lock = new Object();
    private static volatile ViewInjector instance;
    
    private ViewInjector() {}
    
    public static ViewInjector register() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ViewInjector();
                }
            }
        }
        return instance;
    }
    
    public static void unregister() {
        if (instance == null) {
        	instance = null;
        }
    }
    
	public void inject(View view) {
		injectObject(view, view.getClass(), new ViewFinder(view));
	}

	public void inject(Activity activity) {
		//获取Activity的ContentView的注解
        Class<?> handlerType = activity.getClass();
        try {
            ContentView contentView = findContentView(handlerType);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    Method setContentViewMethod = handlerType.getMethod("setContentView", int.class);
                    setContentViewMethod.invoke(activity, viewId);
                }
            }else {
            	Log.e("contentView", "null");
            }
        } catch (Throwable ex) {
            Log.e("inject()", ex.toString());
        }

        injectObject(activity, handlerType, new ViewFinder(activity));
	}

	public void inject(Object handler, View view) {
		injectObject(handler, handler.getClass(), new ViewFinder(view));
	}

	public View inject(Object fragment, LayoutInflater inflater, ViewGroup container) {
		// inject ContentView
        View view = null;
        Class<?> handlerType = fragment.getClass();
        try {
            ContentView contentView = findContentView(handlerType);
            if (contentView != null) {
                int viewId = contentView.value();
                if (viewId > 0) {
                    view = inflater.inflate(viewId, container, false);
                }
            }
        } catch (Throwable ex) {
            Log.e(ex.getMessage(), ex.toString());
        }

        // inject res & event
        injectObject(fragment, handlerType, new ViewFinder(view));

        return view;
	}
	
	/**
     * 从父类获取注解View
     */
    private static ContentView findContentView(Class<?> thisCls) {
        if (thisCls == null || IGNORED.contains(thisCls)) {
            return null;
        }
        ContentView contentView = thisCls.getAnnotation(ContentView.class);
        if (contentView == null) {
            return findContentView(thisCls.getSuperclass());
        }
        return contentView;
    }
    
    private static void injectObject(Object handler, Class<?> handlerType, ViewFinder finder) {
		if (handlerType == null || IGNORED.contains(handlerType)) {
            return;
        }
		// 从父类到子类递归
        injectObject(handler, handlerType.getSuperclass(), finder);
        // inject view
        Field[] fields = handlerType.getDeclaredFields(); // 获取私有的
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {

                Class<?> fieldType = field.getType();
                if (
                /* 不注入静态字段 */     Modifier.isStatic(field.getModifiers()) ||
                /* 不注入final字段 */    Modifier.isFinal(field.getModifiers()) ||
                /* 不注入基本类型字段 */  fieldType.isPrimitive() ||
                /* 不注入数组类型字段 */  fieldType.isArray()) {
                    continue;
                }

                ViewInject viewInject = field.getAnnotation(ViewInject.class);
                if (viewInject != null) {
                    try {
                        View view = finder.findViewById(viewInject.value(), viewInject.parentId());
                        if (view != null) {
                            field.setAccessible(true);
                            field.set(handler, view);
                        } else {
                            throw new RuntimeException("Invalid @ViewInject for "
                                    + handlerType.getSimpleName() + "." + field.getName());
                        }
                    } catch (Throwable ex) {
                        Log.e("injectObject", ex.toString());
                    }
                }
            }
        } // end inject view
    }
}
