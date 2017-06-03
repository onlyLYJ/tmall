package tmall.bean;

public class User {
	
	private String password;
	private String name;
	private int id;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAnonymousName() {
		
		if (null == name) {
			return null;
		}
		
		int nameLen = this.name.length();
		
		if (nameLen <= 1) {
			return "*";
		}
		
		if (nameLen == 2) {
			return this.name.substring(0, 1) + "*";
		}
		
		char[] result = this.name.toCharArray();
		for (int i = 1; i < nameLen - 1; i++) {
			result[i] = '*';
		}
		
		return new String(result);
		
	}


}
