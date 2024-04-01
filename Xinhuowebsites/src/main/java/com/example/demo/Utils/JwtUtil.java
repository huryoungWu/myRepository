package com.example.demo.Utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 过期3600分钟
     */
    private static final long EXPIRE_TIME = 3600 * 60 * 1000;

    /**
     * jwt密钥
     */
    private static final String SECRET = "jwt_secret";

    /**
     * 生成jwt字符串，五分钟后过期  JWT(json web token)
     *
     * @param userId
     * @param info,Map的value只能存放值的类型为：Map，List，Boolean，Integer，Long，Double，String and Date
     * @return
     */
    public static String sign(String userId, Map<String, Object> info) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    //将userId保存到token里面
                    .withAudience(userId)
                    //存放自定义数据
                    .withClaim("info", info)
                    //五分钟后token过期
                    .withExpiresAt(date)
                    //token的密钥
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据token获取userId
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        try {
            String userId = JWT.decode(token).getAudience().get(0);
            return userId;
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 根据token获取自定义数据info
     *
     * @param token
     * @return
     */
    public static Map<String, Object> getInfo(String token) {
        try {
            return JWT.decode(token).getClaim("info").asMap();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    public static boolean checkSign(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    //.withClaim("username, username)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("token 无效，请重新获取");
        }
    }
    public static Map<String, Claim> verifyToken(String token){
        DecodedJWT jwt=null;
        try {
            JWTVerifier verifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt =verifier.verify(token);
            /*
            * 验证token是否过期
            if (jwt.getExpiresAt().before(new Date())){
                System.out.println("token 过期了");
            }
            * 其实不验证也可以，因为在执行verify的方法的时候，已经进行判断token有没有过期了，如果过期了，会抛出com.auth0.jwt.exceptions.TokenExpiredException异常

            * */
        }catch (Exception e){
            System.out.println("token解码异常");
            return null;
        }
        return jwt.getClaims();
    }
}


