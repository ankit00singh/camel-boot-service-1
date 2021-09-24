package com.online.assignment.camelbootservice1.service;

import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.springframework.stereotype.Service;

@Service("userEncryptionData")
public class UserEncryptionData {

    public CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
        CertificateException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        ClassLoader classLoader = getClass().getClassLoader();
        keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
        Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

        CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
        return sharedKeyCrypto;
    }
}
