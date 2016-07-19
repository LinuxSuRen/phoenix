/**
 * http://surenpi.com
 */
package org.suren.autotest.web.framework.selenium;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.suren.autotest.web.framework.autoit3.AutoItCmd;
import org.suren.autotest.web.framework.core.action.ClickAble;
import org.suren.autotest.web.framework.core.action.FileUploadAble;
import org.suren.autotest.web.framework.core.ui.Element;
import org.suren.autotest.web.framework.selenium.strategy.SearchStrategyUtils;

/**
 * @author suren
 * @date 2016年7月19日 上午9:24:34
 */
@Component
public class SeleniumFileUpload implements FileUploadAble
{

	@Autowired
	private SeleniumEngine			engine;
	@Autowired
	private SearchStrategyUtils		searchStrategyUtils;
	@Autowired
	private ClickAble clickAble;
	
	@Override
	public boolean isEnabled(Element element)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden(Element element)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upload(Element element, URL url)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean upload(Element element, File file)
	{
		WebElement webEle = searchStrategyUtils.findStrategy(WebElement.class, element).search(element);
		if(webEle != null)
		{
			String title = "打开";
//			title = "选择要上载的问加你，通过：192.168.8.120";
			
			AutoItCmd.execFileChoose(title, file);
			System.out.println("exec auit3 over");
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			click(element);
			
			return true;
		}
		
		return false;
	}

	@Override
	public boolean click(Element element)
	{
		clickAble.click(element);
		return false;
	}
}
