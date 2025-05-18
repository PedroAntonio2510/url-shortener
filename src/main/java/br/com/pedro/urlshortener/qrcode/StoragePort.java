package br.com.pedro.urlshortener.qrcode;

public interface StoragePort {
    String uploadFile(byte[] fileData, String fileName, String contentType);
}
