package br.com.pedro.urlshortener.Links;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String shortUrl;
    private String urlQrCode;
    private int clickCount;
    private LocalDateTime createdAt;

    public Link() {

    }

    public Link(Long id, String url, String shortUrl, String urlQrCode, int clickCount, LocalDateTime createdAt) {
        this.id = id;
        this.url = url;
        this.shortUrl = shortUrl;
        this.urlQrCode = urlQrCode;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlQrCode() {
        return urlQrCode;
    }

    public void setUrlQrCode(String urlQrCode) {
        this.urlQrCode = urlQrCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
