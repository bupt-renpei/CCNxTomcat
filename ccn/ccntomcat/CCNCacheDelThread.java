package ccntomcat;


public class CCNCacheDelThread implements Runnable {
	testHash testhash;
	String hashName;
	String trieTreeName;
	long time;
	CCNCacheDelThread(testHash testhash,String hashName,String trieTreeName,long time){
		this.testhash = testhash;
		this.hashName = hashName;
		this.trieTreeName = trieTreeName;
		this.time = time;
	}
	
	 public void run() {
		 try {
			Thread.sleep(this.time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("Sleep over");
		 if(this.testhash.FindPath(this.hashName,this.trieTreeName)!=null){
			 this.testhash.DeletePath(this.hashName, this.trieTreeName);
			 System.out.println("DeletePath success with "+this.time +" time,delete "+this.trieTreeName);
		 }
		 System.out.println("DeletePath failed with "+this.time +" time,delete "+this.trieTreeName);
	    	
	}
}
