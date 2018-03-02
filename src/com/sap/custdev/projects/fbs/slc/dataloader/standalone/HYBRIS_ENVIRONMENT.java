package com.sap.custdev.projects.fbs.slc.dataloader.standalone;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class HYBRIS_ENVIRONMENT {

		public static void init(HashMap<String, String> config) {
	
			String[] HYBRIS_PROPERTIES = new String[8];
	
			HYBRIS_PROPERTIES[0] = "crm.system_type="
					+ config.get(DataloaderConfiguration.DB_TYPE);
			HYBRIS_PROPERTIES[1] = "crm.database_hostname="
					+ config.get(DataloaderConfiguration.DB_HOST);
			HYBRIS_PROPERTIES[2] = "crm.database="
					+ config.get(DataloaderConfiguration.DB_NAME);
			HYBRIS_PROPERTIES[3] = "crm.database_port="
					+ config.get(DataloaderConfiguration.DB_PORT);
			HYBRIS_PROPERTIES[4] = "crm.database_user="
					+ config.get(DataloaderConfiguration.DB_USERNAME);
			HYBRIS_PROPERTIES[5] = "crm.database_password="
					+ config.get(DataloaderConfiguration.DB_PASSWORD);
			HYBRIS_PROPERTIES[6] = "crm.ssc_jndi_usage=false";
			HYBRIS_PROPERTIES[7] = "crm.ssc_jndi_datasource=jdbc/SSC_IPC";
	
			System.setProperty("runtimeEnvironment", "hybris");
			System.setProperty("HYBRIS_BIN_DIR",
					createHybrisPropertyFile(HYBRIS_PROPERTIES).getAbsolutePath());
	
		}
	
		private static File createHybrisPropertyFile(String[] content) {
			try {
				File bin = File.createTempFile("bin", "pid");
				bin = new File(bin.getParentFile(), "bin");
	
				File config = File.createTempFile("config", "pid");
				config = new File(config.getParentFile(), "config");
				config.mkdir();
	
				File propFile = new File(config, "local.properties");
				final FileOutputStream fileOutputStream = new FileOutputStream(
						propFile);
				final PrintWriter printWriter = new PrintWriter(fileOutputStream,
						true);
				for (int i = 0; i < content.length; i++) {
					printWriter.println(content[i]);
				}
				printWriter.close();
				propFile.deleteOnExit();
				return bin;
	
			} catch (Exception e) {
				assertTrue(e.toString(), false);
				return null;
			}
	
		}

}
