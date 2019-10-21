import java.sql.Blob;

/**
 * @program: testBlob
 * @description: ${description}
 * @author: RenChao
 * @create: 2019-09-29 14:37
 **/
public class BlobFiles {
    Blob blob;
    String fileName;
    String fileType;

    public BlobFiles() {
    }

    public BlobFiles(Blob blob, String fileName, String fileType) {
        this.blob = blob;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "BlobFiles{" +
                "blob=" + blob +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
