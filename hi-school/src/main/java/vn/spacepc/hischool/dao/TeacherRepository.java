package vn.spacepc.hischool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Email;
import vn.spacepc.hischool.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

  Teacher findByEmail(Email userEmail);
}
