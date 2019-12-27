package hey.yimm.springbootsso_role.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtil {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void addTokenKey(String token,String password){
        redisTemplate.opsForValue().set(token,password,30, TimeUnit.MINUTES);
    }

    public String getTokenValue(String token){
        String password = redisTemplate.opsForValue().get(token);
        return password;
    }

}
