package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.Teaching;

public interface TeachingService {
  public List<Teaching> getAllTeachings();
  public Teaching getTeachingById(long id);
  public Teaching addTeaching(Teaching teaching);
  public Teaching updateTeaching(Teaching teaching);
  public void deleteTeachingById(long id);

}
