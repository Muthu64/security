package com.spring.security.security.signature;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class DigitalSignatureFactory
{
    private static final String SHA_256_WITH_DSA = "SHA256WithDSA";
    public static final String UTF_8 = "UTF-8";
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Signature signature;

    public DigitalSignatureFactory( PublicKey publicKey, PrivateKey privateKey ) throws NoSuchAlgorithmException
    {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.signature = Signature.getInstance( SHA_256_WITH_DSA );
    }

    public DigitalSignature sign( String stringToEncrypt ) throws Exception
    {
        this.signature.initSign( this.privateKey );
        byte[] data = stringToEncrypt.getBytes( UTF_8 );
        this.signature.update(data);

        byte[] digitalSignatureBytes = signature.sign();

        String encodedString = Base64.getEncoder().encodeToString( digitalSignatureBytes );

        DigitalSignature digitalSignature = new DigitalSignature( UTF_8, digitalSignatureBytes, encodedString );

        return digitalSignature;
    }


    public boolean verify( String dataToVerify, DigitalSignature digitalSignature ) throws Exception
    {
        this.signature.initVerify( this.publicKey );
        byte[] data = dataToVerify.getBytes( UTF_8 );

        this.signature.update( data );
        boolean verify = this.signature.verify( digitalSignature.getBytes() );

        return verify;
    }
}
