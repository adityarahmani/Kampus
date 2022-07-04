import frame.DosenViewFrame;
import helpers.Koneksi;
import frame.MahasiswaViewFrame;

public class Main {
    public static void main(String[] args) {
//        Koneksi.getConnection();
        DosenViewFrame viewFrame = new DosenViewFrame();
        /*MahasiswaViewFrame viewFrame = new MahasiswaViewFrame();*/
        viewFrame.setVisible(true);
    }

}
