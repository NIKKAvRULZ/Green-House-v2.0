package humidityserviceproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class HumidityServiceProducerActivator implements BundleActivator {

	private HumidityService service;

	@Override
    public void start(BundleContext context) throws Exception {
        service = new HumidityServiceImpl();
        context.registerService(HumidityService.class.getName(), service, null);
        System.out.println("✅ Humidity Service Started...");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("❌ Humidity Service Stopped...");
    }
}