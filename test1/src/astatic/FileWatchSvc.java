package astatic;


import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;

import static java.nio.file.StandardWatchEventKinds.*;

import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.time.LocalDateTime;

import io.nio.Watcher;

//readme.txt https://docs.oracle.com/javase/tutorial/essential/io/notification.html
public class FileWatchSvc {
	//Watcher watcher = new
	//private static String watchFolder = "C:/Temp/destinationZip/monitor.txt";
	private static String watchFolder = "C:/Temp/destinationZip";
	
	public static void main(String[] args){
		
		try( WatchService filewatchserive =  FileSystems.getDefault().newWatchService() ){
			Path dirTowatch = Paths.get(watchFolder); //path is watchable
			dirTowatch.register(filewatchserive, ENTRY_MODIFY, ENTRY_CREATE, ENTRY_DELETE);
			processEvent(filewatchserive);
		}catch(IOException e){
			System.out.println(e);
		}
	}
	//-----FileWatcherService registered above to the watchfolder for 3 events. Below watch now
	
	public static void processEvent(WatchService filewatchSevice){
		for(;;){
			WatchKey key;			
			try{
				key = filewatchSevice.take();
				
				for(WatchEvent<?> event: key.pollEvents() ){
					if(event.context() != null){
						WatchEvent<Path> ev1 = (WatchEvent<Path>)  event;
						System.out.println(ev1.context().toString());
					}
					WatchEvent.Kind<?> kind = event.kind();
					if(kind == ENTRY_DELETE){
						System.out.println(LocalDateTime.now() + ":A file is deleted");
					}
					if(kind == ENTRY_CREATE){
						System.out.println(LocalDateTime.now() + ":A file is created");
					}
					if(kind == ENTRY_MODIFY){
						System.out.println(LocalDateTime.now() + ":A file is modified");
					}
					
					key.reset(); // catch events continuously
				}
				
			}catch(InterruptedException x){
				System.out.println("Watching interrupted...");
				return;
			}
		}
	}
}
