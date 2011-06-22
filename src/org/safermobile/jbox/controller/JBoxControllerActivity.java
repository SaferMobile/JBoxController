package org.safermobile.jbox.controller;

import java.util.Stack;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

public class JBoxControllerActivity extends Activity implements Runnable, StreamThread.StreamUpdate
{
	
	public final static String TAG = "JBC";
	
	private TextView tvConsole;
	private ScrollView textScroll;
	private Handler handler;
	
	private Thread thread;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	@Override
	protected void onStart() {
		super.onStart();
		
		handler = new Handler();
		tvConsole = (TextView) findViewById(R.id.consoleText);
		textScroll = (ScrollView) findViewById(R.id.consoleScroll);
		
		if (thread == null)
		{
			thread = new Thread (this);
			thread.start();
		}
		
		
	}

	private java.util.Stack<String> logs = new Stack<String>();
	
	@Override
	public void update(String val) {
		
		logs.push(val);
		
		App.logi(val);
		
		handler.post(new Runnable()
		{
			public void run()
			{
				for (int i = 0; i < logs.size(); i++)
				{
					tvConsole.append(logs.pop());
					
				}
			}
		});
		handler.postDelayed(new Runnable()
		{
			public void run()
			{
				textScroll.scrollTo(0, tvConsole.getHeight());
			}
		}, 300);
	}

	@Override
	public void run() {
		try
		{

			update("> Starting debian...");
			
			LilDebiShell lds = new LilDebiShell(this);
			
			update("> checking dependencies... ");
			
			lds.installPackage("traceroute");
			lds.installPackage("wget");
			lds.installPackage("tcptraceroute");
			lds.installPackage("python");

			
			//lds.doTest();
			
			update("> beginning tests... ");
			
			lds.runJB("/mnt/sdcard/jb/");
		}
		catch (Exception e)
		{
			Log.e(TAG, "error running lildebishell",e);
		}
		
	}
    
    
}