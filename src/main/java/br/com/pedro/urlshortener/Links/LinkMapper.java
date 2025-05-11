package br.com.pedro.urlshortener.Links;

import org.springframework.stereotype.Component;

@Component
public class LinkMapper {

    public static LinkResponse toResponse(Link link) {
        return new LinkResponse(
                link.getUrl(),
                link.getShortUrl(),
                link.getUrlQrCode(),
                link.getClickCount()
        );
    }

    public static LinkResponse toResponseList(Link link){
//        String baseUrl = "http://localhost:8080/" + link.getShortUrl();
        String baseUrl = "http://short.local/" + link.getShortUrl();
        return new LinkResponse(
                link.getUrl(),
                baseUrl,
                link.getUrlQrCode(),
                link.getClickCount()
        );
    }

}
