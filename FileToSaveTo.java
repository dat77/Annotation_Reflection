package javapro.anno;

import java.lang.annotation.*;

@Target(value= ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface FileToSaveTo {
    String value() default "src.txt";

}
