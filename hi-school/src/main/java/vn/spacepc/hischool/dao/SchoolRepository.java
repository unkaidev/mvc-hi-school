package vn.spacepc.hischool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.spacepc.hischool.entity.School;

@Repository
public interface SchoolRepository extends JpaRepository<School,Long> {

}
