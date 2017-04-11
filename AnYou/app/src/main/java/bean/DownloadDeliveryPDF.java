package bean;

/**
 * Created by Administrator on 2017/1/24 0024.
 */
public class DownloadDeliveryPDF {

    private String FileName;
    private byte[] filebyte;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public byte[] getFilebyte() {
        return filebyte;
    }

    public void setFilebyte(byte[] filebyte) {
        this.filebyte = filebyte;
    }
}
