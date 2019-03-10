
public class Node {
	private String str;
	private boolean maintenance;
	private int passed_train;
    
	public Node(String str){
		this.str=str;
		
	}
	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	public boolean isMaintenance() {
		return maintenance;
	}
	public void setMaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}
	public int getPassed_train() {
		return passed_train;
	}
	public void setPassed_train(int passed_train) {
		this.passed_train = passed_train;
	}

}
