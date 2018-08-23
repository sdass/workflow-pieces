package astatic;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CustomScheduler {
	
	static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	
	public static void main(String [] args){
		scheduledExecutorService.scheduleAtFixedRate(new MyRunableTask(), 5, 10, TimeUnit.SECONDS);
	}

}


class MyRunableTask implements Runnable{
	static int ith = 1;
	@Override
	public void run() {
		System.out.println(ith++ +"th task . . . to check for monitor file "+ LocalDateTime.now());
		
	}
		
}