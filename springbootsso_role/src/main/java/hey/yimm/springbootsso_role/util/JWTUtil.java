package hey.yimm.springbootsso_role.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hey.yimm.springbootsso_role.bean.User;

public class JWTUtil {

    public static String createToken(User user){
        JWTCreator.Builder builder = JWT.create().withClaim("usernameAddPassowrd",user.getUsername()+user.getPassword());
        builder.withSubject(user.getUsername());
        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());

        String sign = builder.sign(algorithm);

        return sign;
    }

    public static String getUsernameAddPasswordByToken(String token){
        DecodedJWT decode = JWT.decode(token);

        String usernameAddPassowrd = decode.getClaim("usernameAddPassowrd").asString();

        return usernameAddPassowrd;
    }

    public static String getUsernameByToken(String token){
        DecodedJWT decode = JWT.decode(token);

        String username = decode.getSubject();

        return username;
    }



}
