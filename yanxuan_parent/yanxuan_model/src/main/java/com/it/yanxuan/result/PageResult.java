package com.it.yanxuan.result;

import java.util.List;

/**
 * 分页结果包装类
 * @author aaaa
 */
public class PageResult<T> implements java.io.Serializable{
    private Long total;
    private List<T> result;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
