package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.Parent;

public interface ParentService {
  public List<Parent> getAllParents();
  public Parent getParentById(long id);
  public Parent addParent(Parent parent);
  public Parent updateParent(Parent parent);
  public void deleteParentById(long id);
}
