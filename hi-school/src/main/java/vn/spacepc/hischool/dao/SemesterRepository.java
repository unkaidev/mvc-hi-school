package vn.spacepc.hischool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.Parent;
import vn.spacepc.hischool.entity.Semester;

@Repository
public interface SemesterRepository extends JpaRepository<Semester,Long> {

}
