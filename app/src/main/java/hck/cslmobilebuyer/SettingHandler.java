package hck.cslmobilebuyer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hck on 31/3/2016.
 */
public class SettingHandler {
    public final static String fileName = "setting";
    private File file;

    public SettingHandler(File file){
        this.file = new File(file, fileName);
    }

    public ArrayList<String> readFile(){
        ArrayList<String> data = new ArrayList<String>();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String readLine;
            while ((readLine = br.readLine()) != null) {
                readLine = readLine.replaceAll("\\r\\n", "\n");
                data.add(readLine);
            }
            br.close();
            isr.close();
            fis.close();

        } catch (IOException e) {
            return null;
        }
        return data;
    }

    public boolean writeFile(List<String> content){
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
//            Log.w("hck writeFile", String.valueOf(content.size()));

            for (String line:content){
                line = line.replaceAll("\\n", "\r\n");
                bw.write(line);
                bw.newLine();
            }

            bw.close();
            osw.close();
            fos.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
