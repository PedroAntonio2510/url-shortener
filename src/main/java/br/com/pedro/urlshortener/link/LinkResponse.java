package br.com.pedro.urlshortener.link;


public record LinkResponse (
        String original_url,
        String short_url,
        String urlQrCode,
        int clickCount
){

}
