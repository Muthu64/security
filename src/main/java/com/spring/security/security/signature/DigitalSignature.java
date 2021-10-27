package com.spring.security.security.signature;

import java.util.Arrays;

public class DigitalSignature
{
    private String dataEncoding;
    private byte[] bytes;
    private String base64;

    public DigitalSignature( String dataEncoding, byte[] bytes, String base64 )
    {
        this.dataEncoding = dataEncoding;
        this.bytes = bytes;
        this.base64 = base64;
    }

    public String getDataEncoding()
    {
        return dataEncoding;
    }

    public byte[] getBytes()
    {
        return bytes;
    }

    public String getBase64()
    {
        return base64;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder( "DigitalSignature{" );
        sb.append( "dataEncoding='" ).append( dataEncoding ).append( '\'' );
        sb.append( ", bytes=" ).append( Arrays.toString( bytes ) );
        sb.append( ", base64='" ).append( base64 ).append( '\'' );
        sb.append( '}' );
        return sb.toString();
    }
}
