package vn.spacepc.hischool.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.spacepc.hischool.entity.Email;

public interface EmailRepository extends JpaRepository<Email,String> {

}
