/**
 * Project Name: SMLabTesting
 * File Name: LoadUnloadDevice.java
 * Package Name: simModel
 * Date: Apr 15, 2020 9:01:59 PM
 * Copyright (c) 2020, yanggao2019@hotmail.com All Rights Reserved.
 */
 package simModel;

 /**
 * Describe: 
 * 
 * @since JDK 1.8
 * @author Yang Gao 300128708
 * @Email ygao151@uottawa.ca
 * @Date: Apr 15, 2020 9:01:59 PM
 */
public class LoadUnloadDevice {
	
	/**
	 *Indicates the status of the Load/Unload device. TRUE when busy and FALSE otherwise.
	 */
	protected boolean busy;
	
	enum logicType { CURRENT_LOGIC, NEW_LOGIC };
	
	/**
	 *True if NEW_LOGIC will be added, False otherwise
	 */
	protected boolean logicConfiguration;

}
