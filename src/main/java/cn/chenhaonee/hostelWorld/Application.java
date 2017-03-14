package cn.chenhaonee.hostelWorld;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;

/**
 * Created by nichenhao on 2017/3/13.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    @Transactional
    public void run(String... strings) throws Exception {

    }
}