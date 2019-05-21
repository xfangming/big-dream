package org.dream.toolkit.bean;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.dream.toolkit.result.DifferentResult;
import org.dream.toolkit.enums.BeanPrefix;

import static org.dream.toolkit.utils.StringUtils.capitalizeCase;
import static org.dream.toolkit.reflect.ReflectUtils.getGlobalFields;
import static org.dream.toolkit.reflect.ReflectUtils.invoke;


/**
 * 根据两个实体对象判断字段更改情况
 *
 * @author tobiasy
 */
public class BeanDifferentUtils {

    /**
     * 比较两个对象中属性值不同的字段，这两个对象可以不同
     *
     * @param origin
     * @param current
     * @return
     */
    public static DifferentResult compare(Object origin, Object current) {
        Long start = System.currentTimeMillis();
        DifferentResult result = new DifferentResult();
        List<String> changedAttributes = new ArrayList<>();
        Map<String, Object> originValue = new LinkedHashMap<>();
        Map<String, Object> changedValue = new LinkedHashMap();
        Class<? extends Object> originClass = origin.getClass();
        Class<? extends Object> currentClass = current.getClass();
        List<Field> originField = getGlobalFields(originClass);
        List<Field> targetField = getGlobalFields(currentClass);
        for (Field field : originField) {
            String fieldName = field.getName();
            for (Field field1 : targetField) {
                String fieldName1 = field1.getName();
                Boolean f = field.getType().getName().equals(field1.getType().getName());
                if (fieldName.equals(fieldName1) && f) {
                    String methodName = BeanPrefix.GET.getKey() + capitalizeCase(fieldName);
                    try {
                        Object s1 = invoke(origin, methodName);
                        Object s2 = invoke(current, methodName);
                        Boolean flag = (s1 != null && s2 != null);
                        Boolean flag2 = !(s1 == null && s2 == null);
                        Boolean flagMark = false;
                        if (flag) {
                            if (!s1.equals(s2)) {
                                flagMark = true;
                            }
                        } else if (flag2) {
                            flagMark = true;
                        } else {
                            continue;
                        }
                        if (flagMark) {
                            changedAttributes.add(fieldName);
                            originValue.put(fieldName, s1);
                            changedValue.put(fieldName, s2);
                        }
                    } catch (RuntimeException e) {
                        continue;
                    }
                }
            }
        }
        result.setChangedAttributes(changedAttributes);
        result.setChangedValue(changedValue);
        result.setOriginValue(originValue);
        Long end = System.currentTimeMillis();
        result.setSpendTime(end - start);
        return result;
    }


}

