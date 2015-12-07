package com.liteutil.example.http.download;

import com.liteutil.annotation.Column;
import com.liteutil.annotation.PrimaryKey;
import com.liteutil.annotation.Table;
import com.liteutil.orm.db.enums.AssignType;


/**
 * Author: wyouflf
 * Date: 13-11-10
 * Time: 下午8:11
 */
@Table("download")
public class DownloadInfo {

    public DownloadInfo() {
    }

    @PrimaryKey(AssignType.AUTO_INCREMENT)
	@Column("_id")
    protected long id;

    private DownloadState state = DownloadState.STOPPED;

    private String url;

    private String label;

    private String fileSavePath;

    private int progress;

    private long fileLength;

    private boolean autoResume;

    private boolean autoRename;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DownloadState getState() {
        return state;
    }

    public void setState(DownloadState state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFileSavePath() {
        return fileSavePath;
    }

    public void setFileSavePath(String fileSavePath) {
        this.fileSavePath = fileSavePath;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public boolean isAutoResume() {
        return autoResume;
    }

    public void setAutoResume(boolean autoResume) {
        this.autoResume = autoResume;
    }

    public boolean isAutoRename() {
        return autoRename;
    }

    public void setAutoRename(boolean autoRename) {
        this.autoRename = autoRename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DownloadInfo)) return false;

        DownloadInfo that = (DownloadInfo) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
