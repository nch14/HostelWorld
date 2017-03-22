package cn.chenhaonee.hostelWorld.dao;

import cn.chenhaonee.hostelWorld.model.common.PullRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by nichenhao on 2017/3/21.
 */
public interface PullRequestRepository extends JpaRepository<PullRequest,Long>{

    List<PullRequest> findByAvalibleDateIsNull();
}
