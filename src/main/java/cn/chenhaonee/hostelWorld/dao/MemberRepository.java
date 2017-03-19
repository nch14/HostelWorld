package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by nichenhao on 2017/3/18.
 */
public interface MemberRepository extends JpaRepository<Member,String> {
}
