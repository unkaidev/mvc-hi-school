package vn.spacepc.hischool.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.SchoolYear;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Teacher;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass,Long> {
  List<SchoolClass> findByClassNameAndSchoolYear( String schoolClass, SchoolYear schoolYear);
  List<SchoolClass> findBySchoolYearAndStudentsContaining(SchoolYear schoolYear, Student student);

  List<SchoolClass> findBySchoolYear(SchoolYear selectedSchoolYear);
}
