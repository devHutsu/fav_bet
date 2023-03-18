package dev.hutsu.bet_app.tmp;

import java.io.*;

public class IOTmp {
    private final static String PATH_FILE = "src/main/resources/tmp/basket_event_parse.txt";

    private IOTmp(){}

    public static void savePathEventHtml(String html){
        IOTmp ioTmp = new IOTmp();
        ioTmp.saveHtml(html);
    }

    public static String getHTML(){
        return new IOTmp().getHtml();
    }

    private void saveHtml(String html){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH_FILE))) {
            bw.write(html);
            bw.flush();
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private String getHtml(){

        try(BufferedReader br = new BufferedReader(new FileReader(PATH_FILE))) {

            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                buffer.append(line);

            return buffer.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
