package ua.mkorniie.model.annotations;

import java.lang.reflect.Method;

public class AllBasicFalseClass {
    public void parse(Class<?> clazz) throws Exception {
        Method[] methods = clazz.getMethods();
        int pass = 0;
        int fail = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(AllBasicFalse.class)) {
                try {
                    method.invoke(null);
                    pass++;
                } catch (Exception e) {
                    fail++;
                }
            }
        }
    }
}
