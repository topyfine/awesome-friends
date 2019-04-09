package com.qihang.friendship;

/*import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;*/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * <p>项目名称: awesome-friends </p>
 * <p>文件名称: com.qihang.friendship.JwtTests </p>
 * <p>创建时间: 2019/3/20 </p>
 * <p>公司信息: 上海启航软件科技有限公司 </p>
 *
 * @author <a href="mail to: young_fine@163.com" rel="nofollow">topyfine</a>
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTests {

    /*@Test
    public void testVerify() {
        SecretKey key2 = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String string1 = Base64.getEncoder().encodeToString(key2.getEncoded());//;Base64Utils.encodeToString(key2.getEncoded());
        System.out.println(string1);
        // We need a signing key, so we'll create one just for this example. Usually
        // the key would be read from your application configuration instead.
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String secStr = "Nrv0v2euSdLPXN7pajIsqYd+o5H1puwzKdOzNA0JNevusrzod12oObQlMug2zODD9ljdDiJwhvMTPpcMv/CiBw==";
        SecretKey key = Keys.hmacShaKeyFor(secStr.getBytes(StandardCharsets.UTF_8));
        String jws = Jwts.builder().signWith(key)
                .setSubject("Joe")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 3600 * 1000))
                .compact();
        System.out.println(jws);
        assert Jwts.parser().setSigningKey(key).parseClaimsJws(jws).getBody().getSubject().equals("Joe");
    }*/
}
