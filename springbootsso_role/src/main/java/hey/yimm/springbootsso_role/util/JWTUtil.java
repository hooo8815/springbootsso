package hey.yimm.springbootsso_role.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import hey.yimm.springbootsso_role.bean.User;

public class JWTUtil {

    public static String createToken(User user){
        JWTCreator.Builder builder = JWT.create().withClaim("username",user.getUsername());

        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());

        String sign = builder.sign(algorithm);

        return sign;
    }

    public static String getUsernameByToken(String token){
        DecodedJWT decode = JWT.decode(token);

        String usernameAddPassowrd = decode.getClaim("username").asString();

        return usernameAddPassowrd;
    }


    public static boolean checkToken(String username,String password,String token){
        //构建验证器
        JWTVerifier build = JWT.require(Algorithm.HMAC256(password)).build();
        //执行验证方法
        String stringClaim = build.verify(token).getClaim("username").asString();
        if (stringClaim.equals(username)){
            return true;
        }
        return false;
    }


}
