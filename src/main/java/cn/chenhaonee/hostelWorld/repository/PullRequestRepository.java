package cn.chenhaonee.hostelWorld.repository;

import cn.chenhaonee.hostelWorld.model.common.HostelRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by nichenhao on 2017/3/21.
 */
public interface PullRequestRepository extends JpaRepository<HostelRequest,Long>{

    List<HostelRequest> findByAvalibleDateIsNull();
}
