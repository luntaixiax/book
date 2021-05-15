package com.luntai.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
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


}
