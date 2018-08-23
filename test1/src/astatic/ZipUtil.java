package astatic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	
	public static void main(String[] args){
		String inputPath = "C:/Temp/from";
		String outDir = "C:/Temp/destinationZip";
		//plainFileToCopy(inputPath, outDir);
		zipdirectoryCopy(inputPath, outDir);
	}
	
	public static void zipdirectoryCopy(String in, String out){
		Path outp = Paths.get(out);
		if(!Files.exists(outp)){
			outp.toFile().mkdir();	
		}
		//create monitor fle
		String monitorFile = "monitor.txt";
		Path monitorPath = Paths.get(out, monitorFile);
		System.out.println("monitorPath =" + monitorPath);
		try {
			monitorPath.toFile().createNewFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(out +"/bundle.zip")) ){
			//start recursive zipping
			zipper(new File(in), zipout, out);			
		}catch (IOException ioe) {
			System.out.println("Filezipping fails: " + ioe.getMessage());	
			return;
		}
		System.out.println("1 successfully zipped under " + out);
		System.out.println("2 successfully ftped ... to external server");
		System.out.println("Now delete the monitor file");//release lock

		if(!monitorPath.toFile().delete()){
			System.out.println("Deleting file error");
		}
			

		
	}
	
	
	private static final FileFilter FOLDERS_FLTR = new FileFilter() {
		
		@Override
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	};
	private static final FileFilter FILES_FLTR = new FileFilter() {
		
		@Override
		public boolean accept(File pathname) {
			return pathname.isFile();
		}
	};	
	
	public static void zipper(File file, ZipOutputStream zoutstream, String zipoutpath){
		//of 2 sets
		//1st set
		if(file.isDirectory()){  
			//1(2) regular files only
			File[] subFiles = file.listFiles(FILES_FLTR);
			if(subFiles != null){
				for(File f1: subFiles){
					zipper(f1, zoutstream, new File(zipoutpath, f1.getName()).getAbsolutePath());
				}
			}
			//2(2) directories only. //nesting walk
			File[] filesOrDirs = file.listFiles(FOLDERS_FLTR); // at level 1. no recurse
			if(filesOrDirs != null){ //not empty dir
				for(File asubdir: filesOrDirs){
					//recursive call
					zipper(asubdir, zoutstream, new File(zipoutpath, asubdir.getName()).getAbsolutePath());
				}
			}
			//2nd set. plain files on 1st directory on first iteration. Next rounds under each directory 
		} else if(file.exists()){ //this cease point of recursion

			byte[] buffer = new byte[1024];
			int len;
			try(BufferedInputStream buffInstream = new BufferedInputStream(new FileInputStream(file)) ){
				zoutstream.putNextEntry(new ZipEntry(zipoutpath)); //zipping begins
				while (( len = buffInstream.available()) > 0){
					buffInstream.read(buffer);
					//zoutstream.write(buffer, 0, len);
					zoutstream.write(buffer);
				}
				zoutstream.closeEntry();//finish 1 entry. position for next
			}catch(IOException e){
				System.out.println("zipping error: " + e.getMessage());				
				e.printStackTrace();
			}
		}
	}
	
}
