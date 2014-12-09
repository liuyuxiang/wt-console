package com.wt.uum2.domain;

import java.io.Serializable;

public class UserDuty implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2323178154655083887L;
	private User user;
	private Duty duty;
	private String useruuid;
	private String dutyuuid;
	
	public UserDuty(){
		
	}
	
	public UserDuty(Duty d, User u)
	{
		setUser(u);
		setDuty(d);
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
		if(user!=null){
			setUseruuid(user.getUuid());
		}
	}
	public Duty getDuty()
	{
		return duty;
	}
	public void setDuty(Duty duty)
	{
		this.duty = duty;
		if(duty!=null){
			setDutyuuid(duty.getUuid());
		}
	}
	public String getUseruuid()
	{
		return useruuid;
	}
	public void setUseruuid(String useruuid)
	{
		this.useruuid = useruuid;
	}
	public String getDutyuuid()
	{
		return dutyuuid;
	}
	public void setDutyuuid(String dutyuuid)
	{
		this.dutyuuid = dutyuuid;
	}
	
}
