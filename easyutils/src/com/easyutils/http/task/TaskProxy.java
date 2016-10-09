package com.easyutils.http.task;

import java.util.concurrent.Executor;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.easyutils.Easy;
import com.easyutils.http.utils.HttpLog;

/**
 * 异步任务的代理类(仅在task包内可用)
 *
 * @param <ResultType>
 */
public class TaskProxy<ResultType> extends AbsTask<ResultType> {

    static final InternalHandler sHandler = new InternalHandler();
    static final PriorityExecutor sDefaultExecutor = new PriorityExecutor(true);

    private final AbsTask<ResultType> task;
    private final Executor executor;
    private volatile boolean callOnCanceled = false;
    private volatile boolean callOnFinished = false;

    TaskProxy(AbsTask<ResultType> task) {
        super(task);
        this.task = task;
        this.task.setTaskProxy(this);
        this.setTaskProxy(null);
        Executor taskExecutor = task.getExecutor();
        if (taskExecutor == null) {
            taskExecutor = sDefaultExecutor;
        }
        this.executor = taskExecutor;
    }

    @Override
    protected final ResultType doBackground() throws Throwable {
        this.onWaiting();
        PriorityRunnable runnable = new PriorityRunnable(task.getPriority(), new Runnable() {
        	@Override
            public void run() {
                try {
                    // 等待过程中取消
                    if (callOnCanceled || TaskProxy.this.isCancelled()) {
                        throw new Callback.CancelledException("");
                    }

                    // start running
                    TaskProxy.this.onStarted();

                    if (TaskProxy.this.isCancelled()) { // 开始时取消
                        throw new Callback.CancelledException("");
                    }

                    // 执行task, 得到结果.
                    task.setResult(task.doBackground());
                    TaskProxy.this.setResult(task.getResult());

                    // 未在doBackground过程中取消成功
                    if (TaskProxy.this.isCancelled()) {
                        throw new Callback.CancelledException("");
                    }

                    // 执行成功
                    TaskProxy.this.onSuccess(task.getResult());
                } catch (Callback.CancelledException cex) {
                    TaskProxy.this.onCancelled(cex);
                } catch (Throwable ex) {
                    TaskProxy.this.onError(ex, false);
                } finally {
                    TaskProxy.this.onFinished();
                }
        	}
        });
        this.executor.execute(runnable);
        return null;
    }

    @Override
    protected void onWaiting() {
        this.setState(State.WAITING);
        sHandler.obtainMessage(MSG_WHAT_ON_WAITING, this).sendToTarget();
    }

    @Override
    protected void onStarted() {
        this.setState(State.STARTED);
        sHandler.obtainMessage(MSG_WHAT_ON_START, this).sendToTarget();
    }

    @Override
    protected void onSuccess(ResultType result) {
        this.setState(State.SUCCESS);
        sHandler.obtainMessage(MSG_WHAT_ON_SUCCESS, this).sendToTarget();
    }

    @Override
    protected void onError(Throwable ex, boolean isCallbackError) {
        this.setState(State.ERROR);
        sHandler.obtainMessage(MSG_WHAT_ON_ERROR, new ArgsObj(this, ex)).sendToTarget();
    }

    @Override
    protected void onUpdate(int flag, Object... args) {
        // obtainMessage(int what, int arg1, int arg2, Object obj), arg2 not be used.
        sHandler.obtainMessage(MSG_WHAT_ON_UPDATE, flag, flag, new ArgsObj(this, args)).sendToTarget();
    }

    @Override
    protected void onCancelled(Callback.CancelledException cex) {
        this.setState(State.CANCELLED);
        sHandler.obtainMessage(MSG_WHAT_ON_CANCEL, new ArgsObj(this, cex)).sendToTarget();
    }

    @Override
    protected void onFinished() {
        sHandler.obtainMessage(MSG_WHAT_ON_FINISHED, this).sendToTarget();
    }

    @Override
    public final void setState(State state) {
        super.setState(state);
        this.task.setState(state);
    }

    @Override
    public final Priority getPriority() {
        return task.getPriority();
    }

    @Override
    public final Executor getExecutor() {
        return this.executor;
    }

    // ########################### inner type #############################
    private static class ArgsObj {
        final TaskProxy taskProxy;
        final Object[] args;

        public ArgsObj(TaskProxy taskProxy, Object... args) {
            this.taskProxy = taskProxy;
            this.args = args;
        }
    }

    private final static int MSG_WHAT_BASE = 1000000000;
    private final static int MSG_WHAT_ON_WAITING = MSG_WHAT_BASE + 1; // 等待
    private final static int MSG_WHAT_ON_START = MSG_WHAT_BASE + 2; // 开始
    private final static int MSG_WHAT_ON_SUCCESS = MSG_WHAT_BASE + 3; // 成功
    private final static int MSG_WHAT_ON_ERROR = MSG_WHAT_BASE + 4; // 错误
    private final static int MSG_WHAT_ON_UPDATE = MSG_WHAT_BASE + 5; // 更新
    private final static int MSG_WHAT_ON_CANCEL = MSG_WHAT_BASE + 6; // 取消
    private final static int MSG_WHAT_ON_FINISHED = MSG_WHAT_BASE + 7; // 完成

    final static class InternalHandler extends Handler {

        private InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            if (msg.obj == null) {
                throw new IllegalArgumentException("msg must not be null");
            }
            TaskProxy taskProxy = null;
            Object[] args = null;
            if (msg.obj instanceof TaskProxy) {
                taskProxy = (TaskProxy) msg.obj;
            } else if (msg.obj instanceof ArgsObj) {
                ArgsObj argsObj = (ArgsObj) msg.obj;
                taskProxy = argsObj.taskProxy;
                args = argsObj.args;
            }
            if (taskProxy == null) {
                throw new RuntimeException("msg.obj not instanceof TaskProxy");
            }

            try {
                switch (msg.what) {
                    case MSG_WHAT_ON_WAITING: {
                        taskProxy.task.onWaiting();
                        break;
                    }
                    case MSG_WHAT_ON_START: {
                        taskProxy.task.onStarted();
                        break;
                    }
                    case MSG_WHAT_ON_SUCCESS: {
                        taskProxy.task.onSuccess(taskProxy.getResult());
                        break;
                    }
                    case MSG_WHAT_ON_ERROR: {
                        assert args != null;
                        Throwable throwable = (Throwable) args[0];
                        HttpLog.d(throwable.getMessage(), throwable);
                        taskProxy.task.onError(throwable, false);
                        break;
                    }
                    case MSG_WHAT_ON_UPDATE: {
                        taskProxy.task.onUpdate(msg.arg1, args);
                        break;
                    }
                    case MSG_WHAT_ON_CANCEL: {
                        if (taskProxy.callOnCanceled) return;
                        taskProxy.callOnCanceled = true;
                        assert args != null;
                        taskProxy.task.onCancelled((com.easyutils.http.task.Callback.CancelledException) args[0]);
                        break;
                    }
                    case MSG_WHAT_ON_FINISHED: {
                        if (taskProxy.callOnFinished) return;
                        taskProxy.callOnFinished = true;
                        taskProxy.task.onFinished();
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } catch (Throwable ex) {
                taskProxy.setState(State.ERROR);
                if (msg.what != MSG_WHAT_ON_ERROR) {
                    taskProxy.task.onError(ex, true);
                } else if (Easy.isDebug()) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
