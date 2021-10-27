package com.spring.security.security.signature;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class DigitalSignatureTest
{
    public static final String DSA = "DSA";
    private static final String FILE_PATH = "C:\\Users\\paramasivamm\\Downloads\\backup\\backup\\Homework\\security\\src\\main\\resources\\keystore.jks";
    private static final String KEY_PASSWORD = "muthu";
    private static final String KEY_ALIAS = "muthu";
    private static final String JKS = "JKS";

    public static void main( String[] args ) throws Exception
    {
        new DigitalSignatureTest().testSignature();
    }

    private void testSignature() throws Exception
    {
        /*KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( DSA );
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        */

        File keyStoreFile = new File( FILE_PATH );
        InputStream keyStoreFileInputStream = new FileInputStream( keyStoreFile );

        KeyStore keystore = KeyStore.getInstance( JKS );
        keystore.load( keyStoreFileInputStream, KEY_PASSWORD.toCharArray() );
        Certificate certificate = keystore.getCertificate( KEY_ALIAS );

        PrivateKey privateKey = (PrivateKey) keystore.getKey( KEY_ALIAS, KEY_PASSWORD.toCharArray() );
        PublicKey publicKey = certificate.getPublicKey();

        DigitalSignatureFactory digitalSignatureFactory = new DigitalSignatureFactory( publicKey, privateKey );

        String testStringToSign = "Muthu";
        DigitalSignature digitalSignature = digitalSignatureFactory.sign( testStringToSign );

        System.out.println( digitalSignature );

        String testStringToVerify = "Muthu";

        boolean verify = digitalSignatureFactory.verify( testStringToVerify, digitalSignature );

        System.out.println( "verification-->" + verify );
    }
}
