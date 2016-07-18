/**
* Copyright © 1998-2016, Glodon Inc. All Rights Reserved.
*/
package org.suren.autotest.web.framework.selenium;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;
import org.suren.autotest.web.framework.settings.DriverConstants;

/**
 * @author suren
 * @since jdk1.6 2016年6月29日
 */
@Component
public class SeleniumEngine
{
	private String		driverStr;
	private WebDriver	driver;
	private long		timeout;
	private boolean		fullScreen;
	private int			width;
	private int			height;

	public SeleniumEngine()
	{
	}
	
	public void init()
	{
		InputStream  inputStream = null;
		try
		{
			inputStream = this.getClass().getClassLoader().getResourceAsStream("engine.properties");
			Properties enginePro = new Properties();
			enginePro.load(inputStream);
			
			System.getProperties().putAll(enginePro);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			IOUtils.closeQuietly(inputStream);
		}
		
		if(DriverConstants.DRIVER_CHROME.equals(getDriverStr()))
		{
			driver = new ChromeDriver();
		}
		else if(DriverConstants.DRIVER_IE.equals(getDriverStr()))
		{
			DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(capability);
		}
		else if(DriverConstants.DRIVER_FIREFOX.equals(getDriverStr()))
		{
			driver = new FirefoxDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		
		Window window = driver.manage().window();
		if(fullScreen)
		{
			try
			{
				window.fullscreen();
			}
			catch(UnsupportedCommandException e)
			{
				e.printStackTrace();
			}
		}
		
		if(getWidth() > 0)
		{
			window.setSize(new Dimension(getWidth(), window.getSize().getHeight()));
		}
		
		if(getHeight() > 0)
		{
			window.setSize(new Dimension(window.getSize().getWidth(), getHeight()));
		}
	}

	public void openUrl(String url)
	{
		driver.get(url);
	}

	public void close()
	{
		driver.close();
	}

	public WebDriver getDriver()
	{
		return driver;
	}

	public String getDriverStr()
	{
		return driverStr;
	}

	public void setDriverStr(String driverStr)
	{
		this.driverStr = driverStr;
	}

	public long getTimeout()
	{
		return timeout;
	}

	public void setTimeout(long timeout)
	{
		this.timeout = timeout;
	}

	public boolean isFullScreen()
	{
		return fullScreen;
	}

	public void setFullScreen(boolean fullScreen)
	{
		this.fullScreen = fullScreen;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}