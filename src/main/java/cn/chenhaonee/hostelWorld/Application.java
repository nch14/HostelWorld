package cn.chenhaonee.hostelWorld;

import cn.chenhaonee.hostelWorld.dao.MemberRepository;
import cn.chenhaonee.hostelWorld.model.Member.Member;
import cn.chenhaonee.hostelWorld.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MemberRepository memberRepository;

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        Member member = new Member();
        member.setUsername("nch14");
        member.setPasswordHash("nch2012");
        member.setRole(Role.Member);
        memberRepository.save(member);
    }
}