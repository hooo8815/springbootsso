package hey.yimm.ssochildone.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import hey.yimm.ssochildone.entity.User;
import org.springframework.stereotype.Service;

@Service
public class JWTServiceImpl {

    public String createToken(User user){
        JWTCreator.Builder builder = JWT.create().withClaim("username",user.getUsername());

        Algorithm algorithm = Algorithm.HMAC256(user.getPassword());

        String sign = builder.sign(algorithm);

        return sign;
    }

    public String getUsernameByToken(String token){
        DecodedJWT decode = JWT.decode(token);

        String username = decode.getClaim("username").asString();

        return username;
    }

}
