package com.controller;

import com.annotation.IgnoreAuth;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.entity.ConfigEntity;
import com.entity.EIException;
import com.service.ConfigService;
import com.utils.EncryptUtil;
import com.utils.FileUtil;
import com.utils.R;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

/**
 * File upload and download endpoints.
 */
@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private ConfigService configService;

    @RequestMapping("/upload")
    @IgnoreAuth
    public R upload(@RequestParam("file") MultipartFile file, String type) {
        try {
            if (file.isEmpty()) {
                throw new EIException("上传文件不能为空");
            }

            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                throw new EIException("文件名不合法");
            }

            originalFilename = URLDecoder.decode(originalFilename, StandardCharsets.UTF_8.name());
            String fileExt = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);

            File uploadDir = Paths.get(FileUtil.getUploadDir()).toFile();
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                throw new EIException("上传目录创建失败");
            }

            String fileName;
            if (StringUtils.isNotBlank(type) && type.contains("_template")) {
                fileName = type + "." + fileExt;
                File oldFile = new File(uploadDir, fileName);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            } else {
                fileName = System.currentTimeMillis() + "_" + new Random().nextInt(1000) + "." + fileExt;
            }

            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);

            if ("1".equals(type)) {
                ConfigEntity configEntity = configService.selectOne(new EntityWrapper<ConfigEntity>().eq("name", "faceFile"));
                if (configEntity == null) {
                    configEntity = new ConfigEntity();
                    configEntity.setName("faceFile");
                }
                configEntity.setValue(fileName);
                configService.insertOrUpdate(configEntity);
            }

            return R.ok().put("file", fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("文件上传失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("系统异常，请检查配置或联系管理员");
        }
    }

    @IgnoreAuth
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Path normalizedFileName = Paths.get(fileName).normalize();
            if (normalizedFileName.getParent() != null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            File uploadDir = Paths.get(FileUtil.getUploadDir()).toFile();
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

            File targetFile = new File(uploadDir, fileName);
            if (!targetFile.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);

            byte[] fileBytes = FileUtils.readFileToByteArray(targetFile);
            return new ResponseEntity<>(fileBytes, headers, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/encrypt")
    @IgnoreAuth
    public R encrypt(@RequestParam String fileName, @RequestParam String type) throws IOException {
        File uploadDir = Paths.get(FileUtil.getUploadDir()).toFile();
        File originalFile = new File(uploadDir, fileName);

        if (!originalFile.exists() || !originalFile.getParentFile().equals(uploadDir)) {
            return R.error("非法文件路径或文件不存在");
        }

        String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        String encryptedFileName = baseName + "_encrypt" + extension;
        File encryptedFile = new File(uploadDir, encryptedFileName);

        try (InputStream is = new FileInputStream(originalFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }

            byte[] encryptedBytes;
            switch (type.toLowerCase()) {
                case "des":
                    encryptedBytes = EncryptUtil.desEncrypt(baos.toByteArray());
                    break;
                case "aes":
                    encryptedBytes = EncryptUtil.aesEncrypt(baos.toByteArray());
                    break;
                default:
                    return R.error("不支持的加密类型");
            }

            FileUtils.write(encryptedFile, Base64.getEncoder().encodeToString(encryptedBytes), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("加密失败: " + e.getMessage());
        }

        return R.ok().put("file", encryptedFileName);
    }

    @RequestMapping("/decrypt")
    @IgnoreAuth
    public R decrypt(@RequestParam String fileName, @RequestParam String type) {
        if (fileName == null || fileName.isEmpty()) {
            return R.error("文件名不能为空");
        }

        if (!fileName.contains("_encrypt")) {
            return R.error("只能解密带有 '_encrypt' 标识的文件");
        }

        try {
            File uploadDir = Paths.get(FileUtil.getUploadDir()).toFile();
            if (!uploadDir.exists()) {
                return R.error("上传目录不存在");
            }

            File encryptedFile = new File(uploadDir, fileName);
            if (!encryptedFile.exists()) {
                return R.error("文件不存在");
            }

            String encryptedBase64 = FileUtils.readFileToString(encryptedFile, StandardCharsets.UTF_8);
            byte[] cipherBytes = Base64.getDecoder().decode(encryptedBase64);

            byte[] decryptedBytes;
            switch (type.toLowerCase()) {
                case "des":
                    decryptedBytes = EncryptUtil.desDecrypt(cipherBytes);
                    break;
                case "aes":
                    decryptedBytes = EncryptUtil.aesDecrypt(cipherBytes);
                    break;
                default:
                    return R.error("不支持的解密类型");
            }

            String decryptedFileName = fileName.replace("_encrypt", "");
            File decryptedFile = new File(uploadDir, decryptedFileName);
            try (FileOutputStream fos = new FileOutputStream(decryptedFile)) {
                fos.write(decryptedBytes);
            }

            return R.ok().put("file", decryptedFileName);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("文件解密失败: " + e.getMessage());
        }
    }
}
