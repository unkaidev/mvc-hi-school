package vn.spacepc.hischool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.SchoolYear;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear,Long> {

}
