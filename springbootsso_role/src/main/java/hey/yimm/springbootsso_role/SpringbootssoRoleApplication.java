package hey.yimm.springbootsso_role;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "hey.yimm.springbootsso_role.mapper")
public class SpringbootssoRoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootssoRoleApplication.class, args);
    }

}
