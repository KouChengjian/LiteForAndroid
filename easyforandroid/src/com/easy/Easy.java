package com.easy;

import android.content.Context;

public final class Easy {

	private static Context mContext;
	private static boolean debug = true;
//  private static TaskController taskController;
//  private static HttpManager httpManager;
  //private static ImageManager imageManager;
  //private static ViewInjector viewInjector;
	
//  static {
//      TaskControllerImpl.registerInstance();
//      // 默认信任所有https域名
//      HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//          @Override
//          public boolean verify(String hostname, SSLSession session) {
//              return true;
//          }
//      });
//  }
	
	public static void init(Context context){
		mContext = context;
	}
	
	public static boolean isDebug() {
		return debug;
	}

	public static Context getContext() {
		return mContext;
	}

//     
//     public static void init(Application app) {
//         if (app == null) {
//        	 Easy.app = app;
//         }
//     }
//
//     public static void setDebug(boolean debug) {
//    	 Easy.debug = debug;
//     }
//     
//     public static boolean isDebug() {
//         return Easy.debug;
//     }
//
//     public static void setTaskController(TaskController taskController) {
//         if (Easy.taskController == null) {
//        	 Easy.taskController = taskController;
//         }
//     }
//     
//     public static TaskController task() {
//         return Easy.taskController;
//     }
//
//     public static void setHttpManager(HttpManager httpManager) {
//    	 Easy.httpManager = httpManager;
//     }
//     
//     public static HttpManager http() {
//         if (Easy.httpManager == null) {
//             HttpManagerImpl.registerInstance();
//         }
//         return Easy.httpManager;
//     }

//     public static void setImageManager(ImageManager imageManager) {
//    	 Easy.imageManager = imageManager;
//     }
     
//     public static ImageManager image() {
//         if (Ext.imageManager == null) {
//             ImageManagerImpl.registerInstance();
//         }
//         return Ext.imageManager;
//     }

//     public static void setViewInjector(ViewInjector viewInjector) {
//         Ext.viewInjector = viewInjector;
//     }

//     public static ViewInjector view() {
//         if (Ext.viewInjector == null) {
//             ViewInjectorImpl.registerInstance();
//         }
//         return Ext.viewInjector;
//     }

//     public static DbManager getDb(DbManager.DaoConfig daoConfig) {
//         return DbManagerImpl.getInstance(daoConfig);
//     }
}
