package vn.spacepc.hischool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.Score;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.TeacherAssignment;

public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment,Long> {

 List<TeacherAssignment> findByTeacher(Teacher teacher);
}
