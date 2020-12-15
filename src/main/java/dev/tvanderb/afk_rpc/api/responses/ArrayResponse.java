package com.ts_mc.smcb.main.api.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ArrayResponse<T> {

    private final ArrayResponse<T> tArrayResponse = this;
    private List<T> list;

    public ArrayResponse() {}

    public ArrayResponse(@NotNull List<T> list) {
        this.list = list;
    }

    @XmlElement(name = "values")
    @JsonProperty("values")
    @NotNull
    public List<T> getList() {
        return list;
    }

    @XmlElement(name = "size")
    @JsonProperty("size")
    @NotNull
    public Integer getSize() {
        return list.size();
    }

}
