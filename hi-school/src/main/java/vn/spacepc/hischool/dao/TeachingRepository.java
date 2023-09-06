package vn.spacepc.hischool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Schedule;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Teaching;

@Repository
public interface TeachingRepository extends JpaRepository<Teaching,Long> {

  List<Teaching> findByTeacher(Teacher teacher);

  Teaching findByScheduleAndSchoolClassAndTeacherAndSubject(Schedule schedule, SchoolClass schoolClass, Teacher teacher, Subject subject);
}
