package cn.asens;

import org.junit.Test;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by asens on 2016/11/23.
 */
public class TestS {
    private File tar;
    @Test
    public void mm() throws Exception {
        tar=new File("D:\\code6.txt");
        if(!tar.exists()) tar.createNewFile();
        File file=new File("C:\\Users\\asens\\IdeaProjects\\tomcat"){
            public boolean exists(){
                return false;
            }
        };
        System.out.println(file.exists());
       // writeDictionary(file);


    }

    private void writeDictionary(File file) throws Exception {
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            for(File child:files)
                writeDictionary(child);
        }else{
            writeFile(file);
        }
    }

    private void writeFile(File file) throws Exception {
        String fileName=file.getName();
        if(!fileName.endsWith("html")&&!fileName.endsWith("java")&&!fileName.endsWith("xml"))
            return;
        if(fileName.endsWith("html")&&!file.getAbsolutePath().contains("WEB-INF"))
            return;
        writeFile(file,tar);
    }

    private void writeFile(File file, File tar) throws Exception {
        System.out.println(file.getName());
        FileReader fis=new FileReader(file);
        BufferedReader bis=new BufferedReader(fis);
        FileWriter fos=new FileWriter(tar,true);
        BufferedWriter bos=new BufferedWriter(fos);
        int b=0;
        while((b=bis.read())!=-1){
            bos.write(b);
        }
        fos.flush();
        bos.flush();
    }
}
