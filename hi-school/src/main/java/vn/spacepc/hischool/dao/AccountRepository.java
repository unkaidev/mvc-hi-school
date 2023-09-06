package vn.spacepc.hischool.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.Teacher;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
  public Account findByUserName(String userName);

  boolean existsByStudent_StudentId(Long studentId);

  boolean existsByTeacher_TeacherId(Long teacherId);

}
