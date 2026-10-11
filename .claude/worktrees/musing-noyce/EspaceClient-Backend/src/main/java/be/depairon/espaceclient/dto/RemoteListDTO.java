package be.depairon.espaceclient.dto;

import java.util.List;

public class RemoteListDTO {
    private long totalCount;
    private List<?> data;
    private List<?> summary;
    private String key; // used to group

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public List<?> getSummary() {
        return summary;
    }

    public void setSummary(List<?> summary) {
        this.summary = summary;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
