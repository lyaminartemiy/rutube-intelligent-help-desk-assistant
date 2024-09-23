package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class FileDto implements Serializable {

    /*
        Mandatory field for fileDTO is only Multipart file.
     */

    private String title;
    private String description;
    @JsonIgnore
    @NotNull
    private MultipartFile file;
    private String url;
    private Long size;
    private String filename;
    private String s3UUID;


}
