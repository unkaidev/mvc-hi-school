package vn.spacepc.hischool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

  Student findByEmail(Email userEmail);

}
