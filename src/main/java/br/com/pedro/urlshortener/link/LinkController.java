package br.com.pedro.urlshortener.link;

import br.com.pedro.urlshortener.qrcode.QrCodeGeneratorService;
import com.google.zxing.WriterException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService){
        this.linkService = linkService;
    }

    @PostMapping("/api/shorten")
    public ResponseEntity<Map<String, String>> generateShorUrl(@RequestBody Map<String, String> request) throws IOException, WriterException {
        String url = request.get("url");
        Link link = this.linkService.shortUrl(url);

        String generateRedirectUrl = "http://short.local/" + link.getShortUrl();
//        String generateRedirectUrl = "http://localhost:8080/" + link.getShortUrl();

        LinkResponse response = new LinkResponse(
                link.getUrl(),
                generateRedirectUrl,
                link.getUrlQrCode(),
                link.getClickCount()
        );

        return ResponseEntity.ok(Map.of("original_url", link.getUrl(), "short_url", generateRedirectUrl));
    }

    @GetMapping("/api/links")
    public ResponseEntity<List<LinkResponse>> findAll() {
        var response = linkService.getAll();

        List<LinkResponse> resultList = response.stream()
                .map(LinkMapper::toResponseList)
                .toList();

        return ResponseEntity.ok(resultList);
    }

    @GetMapping("{short_code}")
    public ResponseEntity<?> redirectLink(@PathVariable String short_code,
                                               HttpServletResponse response) throws IOException {
        Link link = linkService.getUrl(short_code);
        linkService.incrementCount(link);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(link.getUrl()))
                .build();
    }

    @DeleteMapping("/api/links/{short_code}")
    public ResponseEntity<?> deleteLink(@PathVariable String short_code) {
        linkService.deleteByShortCode(short_code);
        return ResponseEntity.ok(Map.of("message", "Link deleted successfully"));
    }

}
