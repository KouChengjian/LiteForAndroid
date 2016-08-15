package com.liteutil.example.http.download;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import com.liteutil.http.*;
import com.liteutil.example.MyApplication;
import com.liteutil.exception.DbException;
import com.liteutil.http.listener.Callback;
import com.liteutil.http.request.RequestParams;
import com.liteutil.http.task.PriorityExecutor;
import com.liteutil.orm.db.DataBase;
import com.liteutil.orm.db.assit.QueryBuilder;
import com.liteutil.util.Log;

/**
 * Author: wyouflf
 * Date: 13-11-10
 * Time: 下午8:10
 */
public final class DownloadManager {

    private static DownloadManager instance;

    private final static int MAX_DOWNLOAD_THREAD = 2; // 有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.

    private DataBase db1;
    private final Executor executor = new PriorityExecutor(MAX_DOWNLOAD_THREAD);
    private final List<DownloadInfo> downloadInfoList = new ArrayList<DownloadInfo>();
    private final ConcurrentHashMap<DownloadInfo, DownloadCallback>
            callbackMap = new ConcurrentHashMap<DownloadInfo, DownloadCallback>(5);

    private DownloadManager() {
        db1 = MyApplication.getInstance().getDBInstance();
        List<DownloadInfo> infoList = db1.query(DownloadInfo.class);
        if (infoList != null) {
            for (DownloadInfo info : infoList) {
                if (info.getState().value() < DownloadState.FINISHED.value()) {
                    info.setState(DownloadState.STOPPED);
                }
                downloadInfoList.add(info);
            }
        }   
        Log.e("downloadInfoList.size",downloadInfoList.size()+"====");
    }

    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    public void updateDownloadInfo(DownloadInfo info) throws DbException {
    	db1.update(info);
    }

    public int getDownloadListCount() {
        return downloadInfoList.size();
    }

    public DownloadInfo getDownloadInfo(int index) {
        return downloadInfoList.get(index);
    }

    public synchronized void startDownload(String url, String label, String savePath,
                                           boolean autoResume, boolean autoRename,
                                           DownloadViewHolder viewHolder) throws DbException {

        String fileSavePath = new File(savePath).getAbsolutePath();
//        DownloadInfo downloadInfo = db.selector(DownloadInfo.class)
//                .where("label", "=", label)
//                .and("fileSavePath", "=", fileSavePath)
//                .findFirst();
        QueryBuilder qb = new QueryBuilder(DownloadInfo.class);
        qb = new QueryBuilder(DownloadInfo.class)
        .whereEquals("label", label)
        .whereAppendAnd()
        .whereEquals("fileSavePath", fileSavePath);
        List<DownloadInfo> list = db1.<DownloadInfo>query(qb);
        DownloadInfo downloadInfo = null;
        if(list.size() > 0){
        	downloadInfo = list.get(0);
        	if (downloadInfo != null) {
                DownloadCallback callback = callbackMap.get(downloadInfo);
                if (callback != null) {
                    if (viewHolder == null) {
                        viewHolder = new DefaultDownloadViewHolder(null, downloadInfo);
                    }
                    if (callback.switchViewHolder(viewHolder)) {
                        return;
                    } else {
                        callback.cancel();
                    }
                }
            }
        }
        // create download info
        if (downloadInfo == null) {
            downloadInfo = new DownloadInfo();
            downloadInfo.setUrl(url);
            downloadInfo.setAutoRename(autoRename);
            downloadInfo.setAutoResume(autoResume);
            downloadInfo.setLabel(label);
            downloadInfo.setFileSavePath(fileSavePath);
            db1.save(downloadInfo);
        }

        // start downloading
        if (viewHolder == null) {
            viewHolder = new DefaultDownloadViewHolder(null, downloadInfo);
        }
        DownloadCallback callback = new DownloadCallback(viewHolder);
        callback.setDownloadManager(this);
        callback.switchViewHolder(viewHolder);
        RequestParams params = new RequestParams(url);
        params.setAutoResume(downloadInfo.isAutoResume());
        params.setAutoRename(downloadInfo.isAutoRename());
        params.setSaveFilePath(downloadInfo.getFileSavePath());
        params.setExecutor(executor);
        params.setCancelFast(true);
        Callback.Cancelable cancelable = LiteHttp.http().get(params, callback);
        callback.setCancelable(cancelable);
        callbackMap.put(downloadInfo, callback);

        if (!downloadInfoList.contains(downloadInfo)) {
            downloadInfoList.add(downloadInfo);
        }
    }

    public void stopDownload(int index) {
        DownloadInfo downloadInfo = downloadInfoList.get(index);
        stopDownload(downloadInfo);
    }

    public void stopDownload(DownloadInfo downloadInfo) {
        Callback.Cancelable cancelable = callbackMap.get(downloadInfo);
        if (cancelable != null) {
            cancelable.cancel();
        }
    }

    public void stopAllDownload() {
        for (DownloadInfo downloadInfo : downloadInfoList) {
            Callback.Cancelable cancelable = callbackMap.get(downloadInfo);
            if (cancelable != null) {
                cancelable.cancel();
            }
        }
    }

    public void removeDownload(int index) throws DbException {
        DownloadInfo downloadInfo = downloadInfoList.get(index);
        db1.delete(downloadInfo);
        stopDownload(downloadInfo);
        downloadInfoList.remove(index);
    }

    public void removeDownload(DownloadInfo downloadInfo) throws DbException {
        db1.delete(downloadInfo);
        stopDownload(downloadInfo);
        downloadInfoList.remove(downloadInfo);
    }
}
