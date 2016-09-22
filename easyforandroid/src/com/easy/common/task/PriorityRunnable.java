package com.easy.common.task;


/**
 * 带有优先级的Runnable类型(仅在task包内可用)
 */
public class PriorityRunnable implements Runnable {

	public long SEQ;

    public final Priority priority;
    private final Runnable runnable;

    public PriorityRunnable(Priority priority, Runnable runnable) {
        this.priority = priority == null ? Priority.DEFAULT : priority;
        this.runnable = runnable;
    }

    @Override
    public final void run() {
        this.runnable.run();
    }
}
