package vn.spacepc.hischool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Score;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.TeacherAssignment;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

  List<Score> findByTeacherAssignment(TeacherAssignment teacherAssignment);
  List<Score> findBySubject(Subject subject);
}
