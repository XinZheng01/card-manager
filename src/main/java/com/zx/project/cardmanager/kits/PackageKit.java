package com.zx.project.cardmanager.kits;

import com.zx.project.cardmanager.common.BaseHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

public class PackageKit {

  public static Set<Class> scanPackage(String scan, Class superClass) {
    Set<Class> classes = new HashSet<>();
    try {
      ClassLoader classLoader = PackageKit.class.getClassLoader();
      URL url = classLoader.getResource(scan.replace('.', '/'));
      if ("file".equals(url.getProtocol())) {
        final String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
          throw new IllegalArgumentException("scan file dont exists or scan file is not directory!");
        }
        File[] files = dir.listFiles();
        for (File file : files) {
          final String fileName = file.getName();
          if (file.isFile() && fileName.lastIndexOf(".class") > 0) {
            final String className = fileName.substring(0, fileName.length() - 6);
            Class clazz = classLoader.loadClass(scan + "." + className);
            if (superClass != null) {
              if (superClass.isAssignableFrom(clazz))
                classes.add(clazz);
            } else {
              classes.add(clazz);
            }
          }
        }
      } else {
        throw new IllegalArgumentException("scan package is not file!");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("io error" + e.getMessage());
    } catch (ClassNotFoundException e) {
      throw new IllegalArgumentException("class not found!" + e.getMessage());
    }
    return classes;
  }

  public static void main(String[] args) {
    Set<Class> classes = scanPackage("com.zx.project.cardmanager.handler", BaseHandler.class);
    System.out.println(classes.size());
  }
}
