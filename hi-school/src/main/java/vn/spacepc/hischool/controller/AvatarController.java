package vn.spacepc.hischool.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
public class AvatarController {

  private final DataSource dataSource;

  public AvatarController(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @GetMapping("/avatar/{studentId}")
  public ResponseEntity<byte[]> getStudentAvatar(@PathVariable Long studentId) {
    try (Connection connection = dataSource.getConnection()) {
      String query = "SELECT avatar FROM students WHERE student_id = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setLong(1, studentId);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
          if (resultSet.next()) {
            Blob avatarBlob = resultSet.getBlob("avatar");
            if (avatarBlob != null) {
              try (InputStream inputStream = avatarBlob.getBinaryStream()) {
                byte[] avatarBytes = new byte[inputStream.available()];
                inputStream.read(avatarBytes);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                return ResponseEntity.ok()
                    .headers(headers)
                    .body(avatarBytes);
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ResponseEntity.notFound().build();
  }
}
