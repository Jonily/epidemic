package com.tcloudsoft.auth.provider.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

  /**
   * 上传
   * 
   * @param files
   * @param path
   * @throws Exception
   */
  public static void uploadDoc(MultipartFile files, String path) throws Exception {
    byte[] bytes = files.getBytes();
    File file = new File(path);
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(bytes);
    fos.flush();
    fos.close();
  }

  /**
   * 
   * @param response
   * @param fileName
   * @param path
   * @throws Exception
   */
  public static void downLoad(HttpServletResponse response, String fileName, String path)
      throws Exception {
    String name = URLDecoder.decode(fileName, "UTF-8");
    FileInputStream fileInputStream = new FileInputStream(path);
    name = URLEncoder.encode(name, "UTF-8");
    response.setHeader("Content-Disposition", "attachment; filename=" + name);
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/x-msdownload;charset=UTF-8");
    ServletOutputStream servletOutputStream = response.getOutputStream();
    byte[] b = new byte[1024];
    int i = 0;
    while ((i = fileInputStream.read(b)) > 0) {
      servletOutputStream.write(b, 0, i);
    }
    fileInputStream.close();
    servletOutputStream.flush();
    servletOutputStream.close();
  }

  public static Boolean isWin() {
    String os = System.getProperty("os.name");
    if (os.toLowerCase().startsWith("win")) {
      return true;
    }
    return false;
  }

}
