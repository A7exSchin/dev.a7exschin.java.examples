package dev.a7exschin.osgi.dynamic.service.api;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface DynamicService {
	
	public String getDate();
}
