package br.com.pedro.urlshortener.link;

import br.com.pedro.urlshortener.qrcode.QrCodeGeneratorService;
import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final QrCodeGeneratorService qrCodeGeneratorService;

    public LinkService(LinkRepository linkRepository, QrCodeGeneratorService qrCodeGeneratorService) {
        this.linkRepository = linkRepository;
        this.qrCodeGeneratorService = qrCodeGeneratorService;
    }

    public String generateRandomUrl() {
        //noinspection deprecation
        String shortCode = RandomStringUtils.randomAlphanumeric(5, 10);
        return shortCode;
    }

    public Link shortUrl(String url) throws IOException, WriterException {
        String urlQrCode = this.qrCodeGeneratorService.generate(url);
        Link link = new Link();
        link.setUrl(url);
        link.setShortUrl(generateRandomUrl());
        link.setUrlQrCode(urlQrCode);
        link.setCreatedAt(LocalDateTime.now());
        link.setClickCount(0);

        return linkRepository.save(link);
    }

    public List<Link> getAll() {
        return linkRepository.findAll();
    }

    public Link getUrl(String urlShort) {
        try {
            return linkRepository.findByShortUrl(urlShort);
        } catch (Exception error) {
            throw new RuntimeException("URL DOESN'T EXIST ON OUR DATABASE");
        }
    }

    @Transactional
    public void deleteByShortCode(String shortCode) {
        linkRepository.deleteLinkByShortUrl(shortCode);
    }

    public void incrementCount(Link link) {
        link.setClickCount(link.getClickCount() + 1);
        linkRepository.save(link);
    }
}
