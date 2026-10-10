package com.gamelog.gamelog;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RestController
public class UploadController {
    private static final String UPLOAD_DIR = "/home/ava/gamelog-uploads/";

    @PostMapping("/uploads")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws BizException {
        if (file.isEmpty()) {
            throw new BizException(400, "文件为空");
        }
        try {
            Files.createDirectories(Path.of(UPLOAD_DIR));
            String original = file.getOriginalFilename();
            String ext = "";
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf('.'));
            }
            String saved = UUID.randomUUID() + ext;
            file.transferTo(Path.of(UPLOAD_DIR, saved));
            return Result.success(saved);
        } catch (IOException e) {
            throw new BizException(500, "上传失败：" + e.getMessage());
        }
    }
}