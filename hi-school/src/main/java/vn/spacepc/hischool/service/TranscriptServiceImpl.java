package vn.spacepc.hischool.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.spacepc.hischool.dao.TranscriptRepository;
import vn.spacepc.hischool.entity.SchoolYear;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Transcript;

@Service
public class TranscriptServiceImpl implements TranscriptService {

  private TranscriptRepository transcriptRepository;
  private EntityManager entityManager;

  @Autowired
  public TranscriptServiceImpl(TranscriptRepository transcriptRepository,
      EntityManager entityManager) {
    this.transcriptRepository = transcriptRepository;
    this.entityManager = entityManager;
  }


  @Override
  public List<Transcript> getAllTranscripts() {
    return transcriptRepository.findAll();
  }

  @Override
  public Transcript getTranscriptById(long id) {
    return transcriptRepository.getById(id);
  }

  @Override
  public List<Transcript> getTranscriptByStudentId(long studentId) {
    String jpql = "SELECT s FROM Transcript s JOIN s.student st WHERE st.studentId = :studentId";
    List<Transcript> transcripts = entityManager.createQuery(jpql, Transcript.class)
        .setParameter("studentId", studentId)
        .getResultList();
    return transcripts;
  }

  @Override
  public List<Transcript> getTranscriptByClassId(Long classId) {
    String jpql = "SELECT t FROM Transcript t JOIN t.student st JOIN st.schoolClasses sc WHERE sc.classId = :classId";
    List<Transcript> transcripts = entityManager.createQuery(jpql, Transcript.class)
        .setParameter("classId", classId)
        .getResultList();
    return transcripts;
  }


  @Override
  @Transactional
  public Transcript addTranscript(Transcript transcript) {
    return transcriptRepository.save(transcript);
  }

  @Override
  @Transactional
  public Transcript updateTranscript(Transcript transcript) {
    return transcriptRepository.saveAndFlush(transcript);
  }

  @Override
  @Transactional
  public void deleteTranscriptById(long id) {
    transcriptRepository.deleteById(id);
  }
}
