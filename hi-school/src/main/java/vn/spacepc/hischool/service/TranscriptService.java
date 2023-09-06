package vn.spacepc.hischool.service;

import java.util.List;
import vn.spacepc.hischool.entity.SchoolYear;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Transcript;

public interface TranscriptService {
  public List<Transcript> getAllTranscripts();
  public Transcript getTranscriptById(long id);

  List<Transcript> getTranscriptByStudentId(long studentId);


  public Transcript addTranscript(Transcript transcript);
  public Transcript updateTranscript(Transcript transcript);
  public void deleteTranscriptById(long id);


  List<Transcript> getTranscriptByClassId(Long classId);

}
