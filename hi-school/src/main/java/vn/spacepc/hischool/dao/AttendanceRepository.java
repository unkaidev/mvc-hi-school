package vn.spacepc.hischool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.spacepc.hischool.entity.Attendance;
import vn.spacepc.hischool.entity.Schedule;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;

public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

  Attendance findByScheduleAndStudentAndTeacher(Schedule schedule, Student student, Teacher teacher);

  List<Attendance> findByTeacher(Teacher teacher);
}
