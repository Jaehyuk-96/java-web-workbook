package com.example.b01.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
public class UploadFileDTO {

    private List<MultipartFile> files;
}
