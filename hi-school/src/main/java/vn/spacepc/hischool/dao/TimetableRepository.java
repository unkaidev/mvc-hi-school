package vn.spacepc.hischool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Timetable;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable,Long> {
}
