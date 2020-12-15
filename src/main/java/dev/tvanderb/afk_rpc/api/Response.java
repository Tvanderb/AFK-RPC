package dev.tvanderb.afk_rpc.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.tvanderb.afk_rpc.api.responses.ArrayResponse;
import dev.tvanderb.afk_rpc.api.responses.DefaultResponse;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({DefaultResponse.class, ArrayResponse.class})
public class Response<T> {

    private Long id;
    private Integer status;
    private T content;

    public Response() {}

    /**
     * Create a new Response object.
     *
     * @param id The ID of the request this {@link Response Response} is for.
     * @param status The HTTP status of the exchange.
     * @param content The object representing the content of the {@link Response Response}.
     */
    public Response(@NotNull Long id, @NotNull Integer status, @NotNull T content) {
        this.id = id;
        this.status = status;
        this.content = content;
    }

    /**
     * @return The status of the exchange.
     */
    @XmlElement(name = "status")
    @JsonProperty("status")
    @NotNull
    public Integer getResponse() {
        return status;
    }

    /**
     * @return The ID of the request.
     */
    @XmlElement(name = "id")
    @JsonProperty("id")
    @NotNull
    public Long getId() {
        return id;
    }

    /**
     * @return The object representing the response content.
     */
    @XmlElement(name = "content")
    @JsonProperty("content")
    @NotNull
    public T getContent() {
        return content;
    }

}
