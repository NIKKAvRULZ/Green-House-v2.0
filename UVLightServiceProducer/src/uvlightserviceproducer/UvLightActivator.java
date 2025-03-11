package uvlightserviceproducer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class UvLightActivator implements BundleActivator {
    private UVLightService uvLightService;

	private UVLightService service;
	
	public void start(BundleContext context) throws Exception {
		service = new UVLightServiceImpl();
        ServiceReference<UVLightService> serviceReference = context.getServiceReference(UVLightService.class);

        context.registerService(UVLightService.class.getName(), service, null);
        System.out.println("✅ UV Light Service Started...");
        if (serviceReference != null) {
            // Retrieve the service using the reference
            uvLightService = context.getService(serviceReference);
            System.out.println("✅ UV Light Service retrieved successfully.");
        } else {
            System.out.println("❌ UV Light Service not found.");
        }

        // Test the service
        if (uvLightService != null) {
            System.out.println("UV Light Level: " + UVLightService.getUVLightLevel);
        }
	}

	public void stop(BundleContext context) throws Exception {
        System.out.println("❌ UV Light Service Stopped...");
	}

}
