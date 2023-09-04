package dev.a7exschin.tycho.example.api;

import java.util.Properties;

import org.osgi.annotation.versioning.ProviderType;

@ProviderType
public interface SystemProperties {
	Properties getSystemProperties();
}
