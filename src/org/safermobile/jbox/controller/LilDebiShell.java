package org.safermobile.jbox.controller;

import java.io.IOException;
import java.util.Stack;

import android.os.Handler;
import android.util.Log;

public class LilDebiShell {

	private String DEBIAN_PATH = "/data/data/info.guardianproject.lildebi/app_bin/debian";
	
	private ShellUtils su;
	

	public LilDebiShell (StreamThread.StreamUpdate logUpdate) throws Exception
	{

		su = new ShellUtils(true, logUpdate);

		initDebian();
	}
	
	public void runJB (String dirPath) throws Exception
	{

		
		runCommand(dirPath + "dirconntest.sh");
		
		su.doExit();
	}
	
	public void doTest () throws Exception
	{
			
		
		runCommand("ping -c 4 safermobile.org");

		runCommand("cd /tmp");
		runCommand("wget http://safermobile.org");

		
		controlService("ssh","status");

		installPackage("ssh");
		
		controlService("ssh","start");
		
		su.doExit();
	}
	
	private void initDebian () throws Exception
	{
		
		runCommand(DEBIAN_PATH);

		
		
	}

	public void runCommand (String cmd) throws Exception
	{
		String[] cmds = {cmd};
		su.doShellCommand(cmds);
	}
	
	
	
	public void installPackage (String packageName) throws Exception
	{
		String[] cmds =
		{
				"apt-get install " + packageName
		};
		
		su.doShellCommand(cmds);
		
	}
	
	public void controlService (String serviceName, String cmd) throws Exception
	{
		String[] cmds =
		{
				"/etc/init.d/" + serviceName + ' ' + cmd
		};
		
		su.doShellCommand(cmds);
		
	}
	
}
