package com.probestar.configurationtools;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.probestar.configuration.Configuration;
import com.probestar.configuration.ConfigurationSettings;
import com.probestar.configuration.ConfigurationTableManager;
import com.probestar.configurationtools.handler.AddHandler;
import com.probestar.configurationtools.handler.ClearHandler;
import com.probestar.configurationtools.handler.GetHandler;
import com.probestar.configurationtools.handler.HelpHandler;
import com.probestar.configurationtools.handler.ListHandler;
import com.probestar.configurationtools.handler.QuitHandler;
import com.probestar.configurationtools.handler.ReloadHandler;
import com.probestar.configurationtools.handler.RemoveHandler;
import com.probestar.configurationtools.handler.SetHandler;
import com.probestar.configurationtools.handler.SyncHancler;
import com.probestar.configurationtools.handler.UseHandler;
import com.probestar.configurationtools.handler.VersionHandler;
import com.probestar.configurationtools.handler.WizardHandler;
import com.probestar.psutils.PSConsole;
import com.probestar.psutils.PSTracer;

public class CTEntry {
	private static PSTracer _tracer = PSTracer.getInstance(CTEntry.class);

	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure("log4j.properties");
			Configuration.getInstance().setSettings(loadSettings());
			if (!initRootPath())
				return;
			initAuth();
			initTablePath();
			initCTDispatcher();

			boolean isRunning = true;
			System.out.println("Welcome to the configuration world.");
			while (isRunning) {
				System.out.print("\r\n" + CTSession.getInstance().getPrefix() + " >>>");
				String cmd = PSConsole.readLine();
				if (cmd.equalsIgnoreCase(""))
					continue;

				CTResult result = CTDispatcher.getInstance().dispatch(cmd);
				switch (result.getType()) {
				case Print:
					System.out.println(result.getContext());
					break;
				case Quit:
					System.out.println(result.getContext());
					isRunning = false;
					break;
				default:
					break;
				}
			}
		} catch (Throwable t) {
			_tracer.error("ConfigurationTools.main error.", t);
			t.printStackTrace();
		} finally {
			Configuration.getInstance().dispose();
		}
	}

	private static ConfigurationSettings loadSettings() throws Throwable {
		Properties p = new Properties();
		p.load(new FileInputStream("ZKSettings.properties"));
		ConfigurationSettings settings = new ConfigurationSettings();
		settings.setServers(p.getProperty("Servers"));
		return settings;
	}

	private static boolean initRootPath() throws Throwable {
		if (Configuration.getInstance().checkRootPath())
			return true;
		System.out.print("No root path has been detected. Do you want to create it now?\r\n[y/n] ");
		if (!PSConsole.readLine().equalsIgnoreCase("y"))
			return false;
		CTSession.getInstance().setOperatorName(inputItem("Operator's name", "probestar"));
		CTSession.getInstance().setPassword(inputItem("Operator's password", "wyw"));
		Configuration.getInstance().createRootPath(CTSession.getInstance().getOperatorName(), CTSession.getInstance().getPassword());
		return true;
	}

	private static void initAuth() throws Throwable {
		if (CTSession.getInstance().getOperatorName() == null)
			CTSession.getInstance().setOperatorName(inputItem("Operator's name", "probestar"));
		if (CTSession.getInstance().getPassword() == null)
			CTSession.getInstance().setPassword(inputItem("Operator's password", "wyw"));
	}

	private static void initTablePath() throws Throwable {
		String name = CTSession.getInstance().getOperatorName();
		String pwd = CTSession.getInstance().getPassword();
		for (String tableName : ConfigurationTableManager.getAllTableNames())
			Configuration.getInstance().getBridge("", name, pwd).create(tableName, "0".getBytes());
	}

	private static void initCTDispatcher() {
		CTDispatcher.getInstance().register(new HelpHandler());
		CTDispatcher.getInstance().register(new ReloadHandler());
		CTDispatcher.getInstance().register(new AddHandler());
		CTDispatcher.getInstance().register(new RemoveHandler());
		CTDispatcher.getInstance().register(new ClearHandler());
		CTDispatcher.getInstance().register(new ListHandler());
		CTDispatcher.getInstance().register(new GetHandler());
		CTDispatcher.getInstance().register(new SetHandler());
		CTDispatcher.getInstance().register(new UseHandler());
		CTDispatcher.getInstance().register(new WizardHandler());
		CTDispatcher.getInstance().register(new VersionHandler());
		CTDispatcher.getInstance().register(new QuitHandler());
		CTDispatcher.getInstance().register(new SyncHancler());
	}

	private static String inputItem(String key, String value) {
		System.out.print(String.format("%s[%s]: ", key, value));
		String input = PSConsole.readLine();
		if (input.equalsIgnoreCase(""))
			return value;
		return input;
	}
}
