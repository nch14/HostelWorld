package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.MemberCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by carlos on 2017/3/14.
 */
public interface MemberCardRepository extends JpaRepository<MemberCard,String> {

    @Query("select count(*) from MemberCard m where m.id = ?1")
    int findByCardNum(String cardNum);
}