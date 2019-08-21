package javapro.anno;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassSaver <T> {

    private File fileStorage;

    public ClassSaver(File fileStorage) {
        this.fileStorage = fileStorage;
    }

    public ClassSaver() {
    }

    public File getFileStorage() {
        return fileStorage;
    }

    public void setFileStorage(File fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void saveObject(T objectToSave){
        Map<String, String> fieldsMap= new HashMap<>();
        Class cls = objectToSave.getClass();
        fieldsMap.put("[ClassName]", cls.getName());
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Save.class)){
                try {
                    field.setAccessible(true);
                    fieldsMap.put(field.getName(), field.get(objectToSave).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileStorage))) {
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + "=" + entry.getValue());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T obtainObject(Class<T> cls) {
        Map<String, String> fieldsMap= new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileStorage))) {
            String str;
            while ((str = bufferedReader.readLine())!=null){
                String[] pair = str.split("=");
                fieldsMap.put(pair[0], pair[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!cls.getName().equals(fieldsMap.get("[ClassName]"))){
            throw new IllegalArgumentException();
        }
        T resultClass = null;
        try {
            resultClass = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Save.class)){
                    field.setAccessible(true);
                    String fieldType = field.getType().getName();
                    String fieldValue = fieldsMap.get(field.getName());
                    switch (fieldType){
                        case "int":
                        case "java.lang.Integer": field.setInt(resultClass, Integer.parseInt(fieldValue));break;
                        case "long":
                        case "java.lang.Long":field.setLong(resultClass, Long.parseLong(fieldValue));break;
                        case "double":
                        case "java.lang.Double":field.setDouble(resultClass, Double.parseDouble(fieldValue));break;
                        case "float":
                        case "java.lang.Float":field.setFloat(resultClass, Float.parseFloat(fieldValue));break;
                        case "java.lang.String":field.set(resultClass, fieldValue);break;
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return resultClass;
    }



}
