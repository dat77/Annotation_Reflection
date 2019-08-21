package javapro.anno;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//@FileToSaveTo("C:\\src.txt")
@FileToSaveTo
public class TextContainer {

    @Save
    private String infoString;
    @Save
    private int versionInfo;
    @Save
    private final String VERSIONID= "New Version of 2019";


    public TextContainer(String infoString) {
        this.infoString = infoString;
    }

    public TextContainer(String infoString, int versionInfo) {
        this.infoString = infoString;
        this.versionInfo = versionInfo;
    }

    public TextContainer() {
    }

    public String getInfoString() {
        return infoString;
    }

    public void setInfoString(String infoString) {
        this.infoString = infoString;
    }

    public int getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(int versionInfo) {
        this.versionInfo = versionInfo;
    }

    @SaveMethod
    public void saveInfoString(File fileToSaveTo) {
        if (fileToSaveTo == null || infoString == null) {
            throw new IllegalArgumentException();
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileToSaveTo))) {
            bufferedWriter.write(infoString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "TextContainer{" +
                "infoString='" + infoString + '\'' +
                ", versionInfo=" + versionInfo +
                ", VERSIONID='" + VERSIONID + '\'' +
                '}';
    }
}
