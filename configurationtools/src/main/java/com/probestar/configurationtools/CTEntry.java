package com.probestar.configurationtools;


import com.probestar.configuration.zk.ZKBridge;
import com.probestar.configurationtools.handler.*;
import com.probestar.psutils.PSConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CTEntry {
    private static Logger _tracer = LoggerFactory.getLogger(CTEntry.class);

    public static void main(String[] args) {
        try {
            CTSettings.initialize();
            initCTDispatcher();
            if (!initRootPath())
                return;
            initAuth();

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
        }
    }

    private static boolean initRootPath() throws Throwable {
        if (checkRootPath())
            return true;
        System.out.print("No root path has been detected. Do you want to create it now?\r\n[y/n] ");
        if (!PSConsole.readLine().equalsIgnoreCase("y"))
            return false;
        CTSession.getInstance().setOperatorName(inputItem("Operator's name", "probestar"));
        CTSession.getInstance().setPassword(inputItem("Operator's password", "wyw"));
        createRootPath();
        return true;
    }

    private static void initAuth() throws Throwable {
        if (CTSession.getInstance().getOperatorName() == null)
            CTSession.getInstance().setOperatorName(inputItem("Operator's name", "probestar"));
        if (CTSession.getInstance().getPassword() == null)
            CTSession.getInstance().setPassword(inputItem("Operator's password", "wyw"));
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

    private static boolean checkRootPath() {
        final ZKBridge bridge = new ZKBridge(CTSettings.getInstance().getAddress());
        bridge.start();
        boolean exists = bridge.exists(CTSettings.getInstance().getPath());
        new Thread(new Runnable() {
            @Override
            public void run() {
                bridge.close();
            }
        });
        return exists;
    }

    private static void createRootPath() throws InterruptedException {
        ZKBridge bridge = new ZKBridge(CTSettings.getInstance().getAddress(), CTSession.getInstance().getOperatorName(), CTSession.getInstance().getPassword());
        bridge.start();
        bridge.create(CTSettings.getInstance().getPath(), "Elvis.Wong@ultrapowersoft.com".getBytes());
        bridge.close();
    }
}
