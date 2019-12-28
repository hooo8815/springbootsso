package hey.yimm.springbootsso_role.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    public void addTokenKey(String token,String username){
        redisTemplate.opsForValue().set(token,username,30, TimeUnit.MINUTES);
    }

    public String getTokenValue(String token){
        String username = redisTemplate.opsForValue().get(token);
        return username;
    }

}
