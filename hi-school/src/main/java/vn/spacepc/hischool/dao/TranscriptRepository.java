package vn.spacepc.hischool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.SchoolYear;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.Transcript;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long> {

  List<Transcript> findByStudentAndSchoolYear(Student student, SchoolYear schoolYear);
}
