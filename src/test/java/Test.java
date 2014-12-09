import com.wt.uum2.domain.User;

public class Test
{

	/**
	 * 方法说明：
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		String uuid = "ff80808146b5317c0146b5334b830009";
		String pwd = "x6sclVPHm8bDdBqvtvwqRg==";
		User u = new User();
		u.setUuid(uuid);
		u.setPassword(pwd);
		System.out.println(u.getPlainPassword());
		boolean flag = false;
		if (flag = true && 1 == 2) {
			System.out.println("fuck");
		}
	}

}
