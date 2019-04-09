package com.qihang.friendship.util;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.core.io.ClassPathResource;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.util.JwtTokenUtils </p>
 * <p>创建时间: 2019/3/22 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
public class JwtTokenUtils {
    private static RSAKey rsaJWK;

    static {
        // Load JWK set from filesystem
        ClassPathResource resource = new ClassPathResource("jwt/jwks.json");
        try {
            JWKSet localKeys = JWKSet.load(resource.getFile());
            // 默认使用第1个，选用其他请使用localKeys.getKeyByKeyId(kid)
            rsaJWK = (RSAKey) localKeys.getKeys().get(0);
        } catch (Exception e) {
            throw new RuntimeException("file [classpath:jwt/jwks.json] not found.");
        }
    }

    private JwtTokenUtils() {
    }

    public static String issue(JWTClaimsSet claimsSet) throws Exception {
        // Create RSA-signer with the private key
        JWSSigner signer = new RSASSASigner(rsaJWK);
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJWK.getKeyID()).build();
        SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
        // Compute the RSA signature
        signedJWT.sign(signer);
        // To serialize to compact form, produces something like
        // eyJhbGciOiJSUzI1NiJ9.SW4gUlNBIHdlIHRydXN0IQ.IRMQENi4nJyp4er2L
        // mZq3ivwoAjqa1uUkSBKFIX7ATndFF5ivnt-m8uApHO4kfIFOrW7w2Ezmlg3Qd
        // maXlS9DhN0nUk_hGI3amEjkKd0BWYCB8vfUbUv0XGjQip78AI4z1PrFRNidm7
        // -jPDm5Iq0SZnjKjCNS5Q15fokXZc8u0A
        return signedJWT.serialize();
    }

    public static boolean verify(String token) throws Exception {
        // 公钥用来验证
        RSAKey rsaPublicJWK = rsaJWK.toPublicJWK();
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new RSASSAVerifier(rsaPublicJWK);
        return signedJWT.verify(verifier);
    }
}
