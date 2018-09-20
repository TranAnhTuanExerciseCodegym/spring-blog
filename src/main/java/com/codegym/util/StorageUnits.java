package com.codegym.util;

import java.io.File;

public class StorageUnits {
    public static final String FEATURE_LOCATION = "F:\\codegym\\exercise\\WBD_JAVA\\spring-blog-tuan\\build\\libs\\exploded\\spring-blog-tuan-1.0-SNAPSHOT.war\\image";

    public static String getFileExtension(String fileName) {
        int ditIndex = fileName.lastIndexOf('.');
        return fileName.substring(ditIndex);
    }

    public static void removeFeature(String fileName) {
        File file = new File(FEATURE_LOCATION + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
