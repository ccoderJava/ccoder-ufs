package cc.ccoder.ufs.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 * </p>
 *
 * @author congcong
 * @email cong.ccoder@gmail.com
 * @date UfsTestApplication.java v1.0 2021/11/1 18:02
 */
@SpringBootTest
public class UfsTestApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UfsTestApplication.class);
        application.run(args);
    }
}
