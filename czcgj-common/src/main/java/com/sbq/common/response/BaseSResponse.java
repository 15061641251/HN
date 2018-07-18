package com.sbq.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyuan on 2017/2/13.
 */
public class BaseSResponse implements Serializable {

    private int returnCode;
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result = null;

    @XmlElement
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List resultList = null;

    public BaseSResponse() {
        super();
    }

    public BaseSResponse(int returnCode, String description) {
        this.returnCode = returnCode;
        this.description = description;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public BaseSResponse setReturnCode(int returnCode) {
        this.returnCode = returnCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BaseSResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public BaseSResponse setResult(Object result) {
        this.result = result;
        return this;
    }

    public List getResultList() {
        return resultList;
    }

    public BaseSResponse setResultList(List resultList) {
        this.resultList = resultList;
        return this;
    }

    @Override
    public String toString() {
        return "BaseSResponse{" +
            "returnCode=" + returnCode +
            ", description='" + description + '\'' +
            ", result=" + result +
            ", resultList=" + resultList +
            '}';
    }
}
