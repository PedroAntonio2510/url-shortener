package br.com.pedro.urlshortener.Links;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public String generateRandomUrl() {
        //noinspection deprecation
        String shortCode = RandomStringUtils.randomAlphanumeric(5, 10);
        return shortCode;
    }

    public Link shortUrl(String url) {
        Link link = new Link();
        link.setUrl(url);
        link.setShortUrl(generateRandomUrl());
        link.setCreatedAt(LocalDateTime.now());
        link.setUrlQrCode("QR CODE UNAVAILABLE AT THE MOMENT");
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
