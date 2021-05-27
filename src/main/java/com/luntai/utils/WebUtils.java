package com.luntai.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebUtils {
    /**
     * inject parameters into provided bean object
     * @param value key-value map of parameters
     * @param bean  self-defined void (fresh newed) bean object of class T
     * @param <T>   self-defined bean class
     * @return  bean with values populated
     */
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            // will search for the bean using setAttr(); will do nothing if the method is not found
            // populate key-value pairs into the bean object
            BeanUtils.populate(bean, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * convert string to int
     * @param strInt    string to convert
     * @param defaultValue  if conversion not successful, return this default value
     * @return  converted Integer
     */
    public static int parseInt(String strInt, int defaultValue) {
        try {
            return Integer.parseInt(strInt);
        } catch (NumberFormatException e) {
            // e.printStackTrace();
        }
        return defaultValue;
    }

    public static Map<String, List<String>> fileRequestParamMap(HttpServletRequest request) {
        if(ServletFileUpload.isMultipartContent(request)){
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            Map<String, List<String>> map = new HashMap<>();

            try {

                List<FileItem> list = upload.parseRequest(request);

                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        String fieldName = fileItem.getFieldName();
                        String fieldValue = fileItem.getString("UTF-8");
                        if (map.containsKey(fieldName)) {
                            map.get(fieldName).add(fieldValue);
                        }
                        else {
                            List<String> values = new ArrayList<>();
                            values.add(fieldValue);
                            map.put(fieldName, values);

                        }
                    }
                    else {
                        System.out.println("Filename: " + fileItem.getName());
                    }
                }

                return map;

            } catch (FileUploadException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


}
