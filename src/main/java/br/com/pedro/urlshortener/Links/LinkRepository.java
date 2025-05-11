package br.com.pedro.urlshortener.Links;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

    void deleteLinkByShortUrl(String shortUrl);

    Link findByShortUrl(String shortUrl);
}
