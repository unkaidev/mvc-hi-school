package vn.spacepc.hischool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Parent;
import vn.spacepc.hischool.entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

}
