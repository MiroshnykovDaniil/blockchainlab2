package com.example.application.views.helloworld;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Random;

public class ECDSA {

    BigInteger N=BigInteger.ZERO;
    BigInteger Gx=BigInteger.ZERO;
    BigInteger Gy=BigInteger.ZERO;
    BigInteger PrivateKey=BigInteger.ZERO;
    BigInteger PublicKeyX=BigInteger.ZERO;
    BigInteger PublicKeyY=BigInteger.ZERO;

    BigInteger R = BigInteger.ZERO;
    BigInteger S = BigInteger.ZERO;



    public void computePublicKey(){
        if(Gx.equals(Gy)||Gy.equals(PrivateKey)) {
            throw new IllegalArgumentException("One of BigIntegers is not set");
        }

        PublicKeyX = PrivateKey.multiply(Gx);
        PublicKeyY = PrivateKey.multiply(Gy);
    }

    public void sign(String msg) throws NoSuchAlgorithmException {
        if(PublicKeyX.equals(PrivateKey)){
            throw new IllegalArgumentException("Public or Private key is not set");
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        BigInteger hash = new BigInteger(digest.digest(msg.getBytes(StandardCharsets.UTF_8)));
        Random random = new Random();
        BigInteger k = new BigInteger(256, random).mod(N);

        BigInteger x = k.multiply(Gx);//.mod(p);
        R = x.mod(N);
        S = k.modInverse(N).multiply((hash).add(PrivateKey.multiply(R).mod(N)));
        S = S.mod(N);

    }

    public boolean verify(String msg) throws NoSuchAlgorithmException {
        if(PublicKeyX.equals(PrivateKey)){
            throw new IllegalArgumentException("Public or Private key is not set");
        }

        BigInteger w = S.modInverse(N);
        System.out.println("w="+w.toString());
        BigInteger u;

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        BigInteger hash = new BigInteger(digest.digest(msg.getBytes(StandardCharsets.UTF_8)));


        u = (hash.multiply(w)).mod(N); //Проверка с истинным хешем
        //u = (hashwrong.multiply(w)).mod(N); //Проверка с неправильным хешем
        BigInteger v = (R.multiply(w)).mod(N);

        BigInteger x = (u.multiply(Gx)).add(v.multiply(PublicKeyX)).mod(N);

        if(x.equals(R)){
            return true;
        }
        return false;
    }

}
