package vn.spacepc.hischool.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.spacepc.hischool.dao.AccountRepository;
import vn.spacepc.hischool.dao.SchoolClassRepository;
import vn.spacepc.hischool.dao.StudentRepository;
import vn.spacepc.hischool.dao.SubjectRepository;
import vn.spacepc.hischool.dao.TeacherRepository;
import vn.spacepc.hischool.dao.TranscriptRepository;
import vn.spacepc.hischool.entity.Account;
import vn.spacepc.hischool.entity.SchoolClass;
import vn.spacepc.hischool.entity.SchoolYear;
import vn.spacepc.hischool.entity.Score;
import vn.spacepc.hischool.entity.Semester;
import vn.spacepc.hischool.entity.Student;
import vn.spacepc.hischool.entity.Subject;
import vn.spacepc.hischool.entity.Teacher;
import vn.spacepc.hischool.entity.TeacherAssignment;
import vn.spacepc.hischool.entity.Transcript;
import vn.spacepc.hischool.service.SchoolClassService;
import vn.spacepc.hischool.service.StudentService;
import vn.spacepc.hischool.service.TranscriptService;
import vn.spacepc.hischool.service.TranscriptServiceImpl;

@Controller
@RequestMapping("/transcript")
public class TranscriptController {

  private TranscriptRepository transcriptRepository;
  private StudentRepository studentRepository;
  private TeacherRepository teacherRepository;
  private SubjectRepository subjectRepository;
  private AccountRepository accountRepository;
  private SchoolClassRepository schoolClassRepository;
  private SchoolClassService schoolClassService;
  private StudentService studentService;
  private TranscriptService transcriptService;

  @Autowired
  public TranscriptController(TranscriptRepository transcriptRepository,
      StudentRepository studentRepository, TeacherRepository teacherRepository,
      SubjectRepository subjectRepository, AccountRepository accountRepository,
      SchoolClassRepository schoolClassRepository, SchoolClassService schoolClassService,
      StudentService studentService, TranscriptService transcriptService) {
    this.transcriptRepository = transcriptRepository;
    this.studentRepository = studentRepository;
    this.teacherRepository = teacherRepository;
    this.subjectRepository = subjectRepository;
    this.accountRepository = accountRepository;
    this.schoolClassRepository = schoolClassRepository;
    this.schoolClassService = schoolClassService;
    this.studentService = studentService;
    this.transcriptService = transcriptService;
  }


  @GetMapping("/manager/list")
  public String listTranscriptsForManager(Model model) {
    List<Transcript> transcripts = transcriptRepository.findAll();

    Map<SchoolYear, List<Transcript>> transcriptsByYear = new HashMap<>();

    Map<Student, Map<SchoolYear, List<Score>>> scoresByStudentAndYear = new HashMap<>();

    for (Transcript transcript : transcripts) {
      SchoolYear schoolYear = transcript.getSchoolYear();

      if (!transcriptsByYear.containsKey(schoolYear)) {
        transcriptsByYear.put(schoolYear, new ArrayList<>());
      }

      boolean exists = transcriptsByYear.get(schoolYear)
          .stream()
          .anyMatch(existingTranscript -> existingTranscript.getTranscriptId() == transcript.getTranscriptId());

      if (!exists) {
        transcriptsByYear.get(schoolYear).add(transcript);
      }

      Student student = transcript.getStudent();

      List<Score> studentScores = student.getScores();

      for (Score score : studentScores) {
        SchoolYear scoreSchoolYear = score.getSemester().getSchoolYear();

        if (!scoresByStudentAndYear.containsKey(student)) {
          scoresByStudentAndYear.put(student, new HashMap<>());
        }

        if (!scoresByStudentAndYear.get(student).containsKey(scoreSchoolYear)) {
          scoresByStudentAndYear.get(student).put(scoreSchoolYear, new ArrayList<>());
        }

        boolean scoreExists = scoresByStudentAndYear.get(student).get(scoreSchoolYear)
            .stream()
            .anyMatch(existingScore -> existingScore.getScoreId() == score.getScoreId());

        if (!scoreExists) {
          scoresByStudentAndYear.get(student).get(scoreSchoolYear).add(score);
        }
      }
    }

    model.addAttribute("transcriptsByYear", transcriptsByYear);
    model.addAttribute("scoresByStudentAndYear", scoresByStudentAndYear);
    System.out.println(scoresByStudentAndYear);
    return "head-teacher/transcript-list";
  }


@GetMapping("/head-teacher/list")
public String listTranscriptsForHeadTeacher(Model model){
    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    String username=authentication.getName();

    if(authentication!=null&&authentication.isAuthenticated()){
    Account account=accountRepository.findByUserName(username);
    Teacher teacher=account.getTeacher();

    if(teacher!=null){
    List<SchoolClass> schoolClasses=schoolClassService.getSchoolClassByTeacherId(
    teacher.getTeacherId());

    Map<SchoolYear, List<Transcript>>transcriptsByYear=new HashMap<>();

    Map<Student, Map<SchoolYear, List<Score>>>scoresByStudentAndYear=new HashMap<>();

    for(SchoolClass schoolClass:schoolClasses){
    List<Student> studentsInClass=studentService.getStudentsByClassId(
    schoolClass.getClassId());

    for(Student student:studentsInClass){
    List<Transcript> studentTranscripts=transcriptService.getTranscriptByStudentId(
    student.getStudentId());

    for(Transcript transcript:studentTranscripts){
    SchoolYear schoolYear=transcript.getSchoolYear();

    if(!transcriptsByYear.containsKey(schoolYear)){
    transcriptsByYear.put(schoolYear,new ArrayList<>());
    }

    boolean exists=transcriptsByYear.get(schoolYear)
    .stream()
    .anyMatch(existingTranscript->existingTranscript.getTranscriptId()==transcript.getTranscriptId());

    if(!exists){
    transcriptsByYear.get(schoolYear).add(transcript);
    }
    }

    List<Score> studentScores=student.getScores();

    for(Score score:studentScores){
    SchoolYear schoolYear=score.getSemester().getSchoolYear();

    if(!scoresByStudentAndYear.containsKey(student)){
    scoresByStudentAndYear.put(student,new HashMap<>());
    }

    if(!scoresByStudentAndYear.get(student).containsKey(schoolYear)){
    scoresByStudentAndYear.get(student).put(schoolYear,new ArrayList<>());
    }

    boolean exists=scoresByStudentAndYear.get(student).get(schoolYear)
    .stream()
    .anyMatch(existingScore->existingScore.getScoreId()==score.getScoreId());

    if(!exists){
    scoresByStudentAndYear.get(student).get(schoolYear).add(score);
    }
    }
    }
    }

    model.addAttribute("transcriptsByYear",transcriptsByYear);
    model.addAttribute("scoresByStudentAndYear",scoresByStudentAndYear);
    System.out.println(scoresByStudentAndYear);
    return"head-teacher/transcript-list";
    }
    }

    return"home";
    }

@GetMapping("/student/list")
public String listTranscriptsAndScoresForStudent(Model model,Authentication authentication){
    if(authentication!=null&&authentication.isAuthenticated()){
    String username=authentication.getName();

    Account account=accountRepository.findByUserName(username);

    if(account!=null&&account.getStudent()!=null){
    List<Transcript> studentTranscripts=account.getStudent().getTranscripts();

    List<Score> studentScores=account.getStudent().getScores();

    Map<SchoolYear, List<Transcript>>transcriptsByYear=new HashMap<>();

    for(Transcript transcript:studentTranscripts){
    SchoolYear schoolYear=transcript.getSchoolYear();

    if(!transcriptsByYear.containsKey(schoolYear)){
    transcriptsByYear.put(schoolYear,new ArrayList<>());
    }

    transcriptsByYear.get(schoolYear).add(transcript);
    }

    Map<SchoolYear, List<Score>>scoresByYear=new HashMap<>();

    for(Score score:studentScores){
    SchoolYear schoolYear=score.getSemester().getSchoolYear();

    if(!scoresByYear.containsKey(schoolYear)){
    scoresByYear.put(schoolYear,new ArrayList<>());
    }

    scoresByYear.get(schoolYear).add(score);
    }

    model.addAttribute("transcriptsByYear",transcriptsByYear);
    model.addAttribute("scoresByYear",scoresByYear);
    }
    }

    return"student/transcript-list";
    }

@GetMapping("/edit/{id}")
public String showEditTranscriptForm(@PathVariable("id") Long id,Model model){
    Transcript transcript=transcriptRepository.findById(id).orElse(null);
    if(transcript==null){
    return"redirect:/transcript/list";
    }
    model.addAttribute("transcript",transcript);

    return"head-teacher/transcript-form";
    }

@GetMapping("/delete/{id}")
public String deleteTranscript(@PathVariable Long id,Principal principal){
    transcriptRepository.deleteById(id);
    if(isHeadTeacher(principal)){
    return"redirect:/transcript/head-teacher/list";
    }else if(isManager(principal)){
    return"redirect:/transcript/manager/list";
    }else{
    return"redirect://checkUserRole";
    }
    }

@PostMapping("/save")
public String saveTranscript(@ModelAttribute Transcript transcript,Principal principal){
    // Lưu hoặc cập nhật transcript

    transcriptRepository.save(transcript);
    if(isHeadTeacher(principal)){
    return"redirect:/transcript/head-teacher/list";
    }else if(isManager(principal)){
    return"redirect:/transcript/manager/list";
    }else{
    return"redirect://checkUserRole";
    }
    }

private boolean isHeadTeacher(Principal principal){
    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    if(authentication!=null){
    for(GrantedAuthority authority:authentication.getAuthorities()){
    if("ROLE_HEADTEACHER".equals(authority.getAuthority())){
    return true;
    }
    }
    }
    return false;
    }

private boolean isManager(Principal principal){
    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    if(authentication!=null){
    for(GrantedAuthority authority:authentication.getAuthorities()){
    if("ROLE_MANAGER".equals(authority.getAuthority())){
    return true;
    }
    }
    }
    return false;
    }
    }

