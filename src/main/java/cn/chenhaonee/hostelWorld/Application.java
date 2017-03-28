package cn.chenhaonee.hostelWorld;

import cn.chenhaonee.hostelWorld.dao.ManagerDao;
import cn.chenhaonee.hostelWorld.dao.MemberRepository;
import cn.chenhaonee.hostelWorld.init.InitVisaCard;
import cn.chenhaonee.hostelWorld.model.Manager;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.model.Role;
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
    private ManagerDao managerDao;

    @Autowired
    private MemberStateChecker memberStateChecker;

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        if (managerDao.findOne("manager")==null){
            Manager manager = new Manager("manager", new BCryptPasswordEncoder().encode("manager"));
            managerDao.save(manager);
            initVisaCard.init();
        }
        memberStateChecker.run();
    }
}