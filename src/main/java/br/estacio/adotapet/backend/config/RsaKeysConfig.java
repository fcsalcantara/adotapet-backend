package br.estacio.adotapet.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * Configura a utilização de um par de chaves RSA privada e pública contidos na key store ".jks".
 */
@Configuration
public class RsaKeysConfig {

    @Value("${adotapet.jwt.key-store}")
    private String keyStoreCaminho; // Caminho do arquivo ".jks" da key store.

    @Value("${adotapet.jwt.key-store-password}")
    private String keyStoreSenha; // Senha de acesso ao arquivo ".jks" da key store.

    @Value("${adotapet.jwt.key-alias}")
    private String keyApelido; // Um dos pares de chaves contidos no arquivo ".jks"

    @Value("${adotapet.jwt.key-password}")
    private String keySenha; // Senha de acesso à chave privada.

    private KeyStore keyStore;

    /**
     * Carrega a key store que contém as chaves RSA privada e pública.
     */
    @PostConstruct
    public void carregaKeyStore() {

        try {

            keyStore = KeyStore.getInstance("jks");

            InputStream keystoreAsStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(keyStoreCaminho);

            keyStore.load(keystoreAsStream, keyStoreSenha.toCharArray());
        } catch (CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Não foi possível carregar a Key Store.");
        }
    }

    /**
     * Para obter o bean da chave RSA privada contida na key store.
     *
     * @return A chave RSA privada.
     */
    @Bean
    protected RSAPrivateKey rsaPrivateKey() {

        try {

            Key key = keyStore.getKey(keyApelido, keySenha.toCharArray());
            if (!(key instanceof RSAPrivateKey))
                throw new IllegalArgumentException("Não foi possível carregar a RSA Private Key.");

            return (RSAPrivateKey) key;
        } catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new IllegalArgumentException("Não foi possível carregar a RSA Private Key.");
        }
    }

    /**
     * Para obter o bean da chave RSA pública contida na key store.
     *
     * @return A chave RSA pública.
     */
    @Bean
    protected RSAPublicKey rsaPublicKey() {

        try {

            Certificate certificate = keyStore.getCertificate(keyApelido);
            PublicKey publicKey = certificate.getPublicKey();
            if (!(publicKey instanceof RSAPublicKey))
                throw new IllegalArgumentException("Não foi possível carregar a RSA Public Key.");

            return (RSAPublicKey) publicKey;
        } catch (KeyStoreException e) {
            throw new IllegalArgumentException("Não foi possível carregar a RSA Public Key.");
        }
    }
}

