package temperaturesensorproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class TemperatureActivator implements BundleActivator {
    private TemperatureService service;

    @Override
    public void start(BundleContext context) throws Exception {
        service = new TemperatureServiceImpl();
        context.registerService(TemperatureService.class, service, null);
        System.out.println("✅ Temperature Service Started...");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("❌ Temperature Service Stopped...");
    }
}
