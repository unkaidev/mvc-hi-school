package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.Subject;

public interface SubjectService {
  public List<Subject> getAllSubjects();
  public Subject getSubjectById(long id);
  public Subject addSubject(Subject subject);
  public Subject updateSubject(Subject subject);
  public void deleteSubjectById(long id);

}
