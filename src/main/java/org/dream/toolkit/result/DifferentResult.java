package org.dream.toolkit.result;

import java.util.List;
import java.util.Map;

/**
 * @author tobiasy
 */
public class DifferentResult {
    private Long spendTime;
    private List<String> changedAttributes;
    private Map<String, Object> originValue;
    private Map<String, Object> changedValue;

    public Long getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Long spendTime) {
        this.spendTime = spendTime;
    }

    public List<String> getChangedAttributes() {
        return changedAttributes;
    }

    public void setChangedAttributes(List<String> changedAttributes) {
        this.changedAttributes = changedAttributes;
    }

    public Map<String, Object> getOriginValue() {
        return originValue;
    }

    public void setOriginValue(Map<String, Object> originValue) {
        this.originValue = originValue;
    }

    public Map<String, Object> getChangedValue() {
        return changedValue;
    }

    public void setChangedValue(Map<String, Object> changedValue) {
        this.changedValue = changedValue;
    }

    @Override
    public String toString() {
        return "DifferentResult{" +
                "spendTime=" + spendTime +
                ", changedAttributes=" + changedAttributes +
                ", originValue=" + originValue +
                ", changedValue=" + changedValue +
                '}';
    }
}
