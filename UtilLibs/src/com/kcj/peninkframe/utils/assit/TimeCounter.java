package com.kcj.peninkframe.utils.assit;

import android.util.Log;

/**
 * @ClassName: TimeCounter
 * @Description: 计时器， 顾名思义，统计耗时用的
 * @author:
 * @date:
 */
public class TimeCounter {

	private static final String TAG = TimeCounter.class.getSimpleName();
	private long t;

	public TimeCounter() {
		start();
	}

	/**
	 * Count start.
	 */
	public long start() {
		t = System.currentTimeMillis();
		return t;
	}

	/**
	 * Get duration and restart.
	 */
	public long durationRestart() {
		long now = System.currentTimeMillis();
		long d = now - t;
		t = now;
		return d;
	}

	/**
	 * Get duration.
	 */
	public long duration() {
		return System.currentTimeMillis() - t;
	}

	/**
	 * Print duration.
	 */
	public void printDuration(String tag) {
		Log.i(TAG, tag + " :  " + duration());
	}

	/**
	 * Print duration.
	 */
	public void printDurationRestart(String tag) {
		Log.i(TAG, tag + " :  " + durationRestart());
	}
}