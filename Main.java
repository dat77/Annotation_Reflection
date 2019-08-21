package javapro.anno;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        /**
         * 2*.
         * Написать класс TextContainer , который содержит в себе строку. С помощью механизма
         * аннотаций указать 1) в какой файл должен сохраниться текст 2) метод, который выполнит
         * сохранение. Написать класс Saver , который сохранит поле класса TextContainer в указанный
         * файл.
         */


        TextContainer textContainer = new TextContainer("Lolita, light of my life, fire of my loins.");
        Class cls = textContainer.getClass();
        if (cls.isAnnotationPresent(FileToSaveTo.class)){
            FileToSaveTo anno = (FileToSaveTo) cls.getAnnotation(FileToSaveTo.class);
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(SaveMethod.class)){
                    try {
                        method.invoke(textContainer, new File(anno.value()));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 3**.
         * Написать код, который сериализирует и десериализирует в из файла все значения полей
         * класса, которые отмечены аннотацией @
         */
        TextContainer textContainer1 = new TextContainer("Lolita, light of my life, fire of my loins.", 10);
        ClassSaver<TextContainer> classSaver = new ClassSaver<>(new File("classsaver.txt"));
        classSaver.saveObject(textContainer1);
        TextContainer textContainer2 = classSaver.obtainObject(TextContainer.class);
        System.out.println(textContainer2.toString());
    }
}
