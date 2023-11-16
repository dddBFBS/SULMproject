package com.AuthenticationSystem.util;

import com.AuthenticationSystem.common.constant.JwtConstant;
import com.AuthenticationSystem.entity.CheckResult;
import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * jwt加密和解密的工具类
 */
public class JwtUtils {

    /**
     * 签发JWT
     * @param id
     * @param subject 可以是JSON数据 尽可能少
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;//签名算法HS256
        long nowMillis = System.currentTimeMillis();//获取当前时间，结果为时间戳格式
        Date now = new Date(nowMillis);//时间戳转换为易于理解的格式
        SecretKey secretKey = generalKey();//生成签名时使用的服务端私钥
        //为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder()//new一个JwtBuilder，设置jwt的body
                .setId(id) //jwt的唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                .setSubject(subject)   // jwt所面向的用户，放登录的用户名，一个json格式的字符串，可存放userid，roldid之类，作为用户的唯一标志
                .setIssuer("Java1234")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey); // 设置签名，使用签名算法以及签名使用的密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 设置过期时间
        }
        return builder.compact();
    }

    /**
     * 生成jwt token
     * @param username
     * @return
     */
    public static String genJwtToken(String username){
        return createJWT(username,username,60*60*1000);//调用createJWT(使用用户名创建Jwt),生成token
    }

    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJWT(String jwtStr) {
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(JwtConstant.JWT_ERRCODE_EXPIRE);
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(JwtConstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(JwtConstant.JWT_ERRCODE_FAIL);
            checkResult.setSuccess(false);
        }
        return checkResult;
    }

    /**
     * 生成加密Key
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode( JwtConstant.JWT_SECERT);//本地的密码解码
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");//根据给定的字节数组使用AES加密算法构造一个密钥
        return key;
    }

    /**
     * 解析JWT字符串,返回claims认证实体
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();//签名秘钥，和生成签名的秘钥一模一样
        return Jwts.parser()//得到defaultJwtParser
                .setSigningKey(secretKey)//设置签名的密钥
                .parseClaimsJws(jwt)//设置需要解析的jwt
                .getBody();
    }

    /**
     * 测试
     */
    public static void main(String[] args) throws InterruptedException {
        //小明失效 10s
        String sc = createJWT("1","小明", 60 * 60 * 1000);
        System.out.println(sc);
        System.out.println(validateJWT(sc).getErrCode());
        System.out.println(validateJWT(sc).getClaims().getId());
        System.out.println(validateJWT(sc).getClaims().getSubject());
        //Thread.sleep(3000);
        System.out.println(validateJWT(sc).getClaims());
        Claims claims = validateJWT(sc).getClaims();
        String sc2 = createJWT(claims.getId(),claims.getSubject(), JwtConstant.JWT_TTL);
        System.out.println(sc2);
    }

}