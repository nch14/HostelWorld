package cn.chenhaonee.hostelWorld;

import cn.chenhaonee.hostelWorld.repository.ManagerRepository;
import cn.chenhaonee.hostelWorld.init.InitVisaCard;
import cn.chenhaonee.hostelWorld.model.Manager;
import cn.chenhaonee.hostelWorld.util.MemberStateChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;

/**
 * Created by nichenhao on 2017/3/13.
 */
@SpringBootApplication
public class Application implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private InitVisaCard initVisaCard;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private MemberStateChecker memberStateChecker;

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        if (managerRepository.findOne("manager")==null){
            Manager manager = new Manager("manager", new BCryptPasswordEncoder().encode("manager"));
            managerRepository.save(manager);
            initVisaCard.init();
        }
        memberStateChecker.run();
    }
}