import java.io.File;
import java.util.Iterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
//import com.drew.metadata.exif.ExifDirectory;

/**
 * Created by asens on 2016/11/23.
 */
public class TestS {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\88.jpg");
        Metadata metadata = ImageMetadataReader.readMetadata(file);

        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                //if(directory.getName().equals("GPS"))
                System.out.println("["+directory.getName()+"] - "+tag.getTagName()+"="+tag.getDescription());
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
    }
}
