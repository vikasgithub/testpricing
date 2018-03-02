package com.sap.custdev.projects.fbs.slc.dataloader.standalone;

import java.util.HashMap;

public class ERP_CONNECTIONS {
	
	private static final String ERP_USERNAME = "sscdl";
	private static final String ERP_PASSWORD = "Welcome1";

	public static void X0Q_700_Old(HashMap<String, String> config,
			HashMap<String, String[]> filters) {
		//config.put(OldDataloaderConfiguration.OUTBOUND_DESTINATION_NAME, "X0Q_700");
		config.put(OldDataloaderConfiguration.ECC_CLIENT, "700");
		config.put(OldDataloaderConfiguration.ECC_USERNAME, ERP_USERNAME);
		config.put(OldDataloaderConfiguration.ECC_PASSWORD, ERP_PASSWORD);
		config.put(OldDataloaderConfiguration.ECC_SERVER, "ldcix0q.wdf.sap.corp");
		config.put(OldDataloaderConfiguration.ECC_SYSTEM_NUMBER, "11");
		config.put(OldDataloaderConfiguration.ECC_RFC_DESTINATION,
				"SSC_DATALOADER_VEHBR52030");
		config.put(OldDataloaderConfiguration.ECC_LOAD_BALANCED,
				Boolean.FALSE.toString());
		DATA_FILTERS.X0Q(filters);
	}
	
	public static void DR5_301(HashMap<String, String> config,
			HashMap<String, String[]> filters) {
		//config.put(OldDataloaderConfiguration.OUTBOUND_DESTINATION_NAME, "X0Q_700");
		config.put(OldDataloaderConfiguration.ECC_CLIENT, "301");
		config.put(OldDataloaderConfiguration.ECC_USERNAME, "RFC_BCOM_ECC");
		config.put(OldDataloaderConfiguration.ECC_PASSWORD, "Testing123");
		config.put(OldDataloaderConfiguration.ECC_SERVER, "sapgdr502.mmm.com");
		config.put(OldDataloaderConfiguration.ECC_SYSTEM_NUMBER, "00");
		config.put(OldDataloaderConfiguration.ECC_RFC_DESTINATION,
				"SSC_DATALOADER");
		config.put(OldDataloaderConfiguration.ECC_LOAD_BALANCED,
				Boolean.FALSE.toString());
		DATA_FILTERS.DR5(filters);
	}
	
	public static void X0Q_700(HashMap<String, String> config,
			HashMap<String, String[]> filters) {
		config.put(DataloaderConfiguration.OUTBOUND_DESTINATION_NAME, "X0Q_700");
		config.put(DataloaderConfiguration.ECC_CLIENT, "700");
		config.put(DataloaderConfiguration.ECC_USERNAME, ERP_USERNAME);
		config.put(DataloaderConfiguration.ECC_PASSWORD, ERP_PASSWORD);
		config.put(DataloaderConfiguration.ECC_SERVER, "ldcix0q.wdf.sap.corp");
		config.put(DataloaderConfiguration.ECC_SYSTEM_NUMBER, "11");
		config.put(DataloaderConfiguration.ECC_RFC_DESTINATION,
				"SSC_DATALOADER_VEHBR52032");
		config.put(DataloaderConfiguration.ECC_LOAD_BALANCED,
				Boolean.FALSE.toString());
		DATA_FILTERS.X0Q(filters);
	}

	public static void WEF_504(HashMap<String, String> config,
			HashMap<String, String[]> filters) {
		config.put(DataloaderConfiguration.OUTBOUND_DESTINATION_NAME, "WEF_504");
		config.put(DataloaderConfiguration.ECC_CLIENT, "504");
		config.put(DataloaderConfiguration.ECC_USERNAME, ERP_USERNAME);
		config.put(DataloaderConfiguration.ECC_PASSWORD, ERP_PASSWORD);
		config.put(DataloaderConfiguration.ECC_SERVER, "ldciwef.wdf.sap.corp");
		config.put(DataloaderConfiguration.ECC_SYSTEM_NUMBER, "20");
		config.put(DataloaderConfiguration.ECC_RFC_DESTINATION,
				"SSC_DATALOADER_GENERIC");
		config.put(DataloaderConfiguration.ECC_LOAD_BALANCED,
				Boolean.FALSE.toString());
		DATA_FILTERS.WEF(filters);
	}

	public static void WEF_504_WITHOUT_DESTINATION_NAME(HashMap<String, String> config,
			HashMap<String, String[]> filters) {
		config.put(DataloaderConfiguration.ECC_CLIENT, "504");
		config.put(DataloaderConfiguration.ECC_USERNAME, ERP_USERNAME);
		config.put(DataloaderConfiguration.ECC_PASSWORD, ERP_PASSWORD);
		config.put(DataloaderConfiguration.ECC_SERVER, "ldciwef.wdf.sap.corp");
		config.put(DataloaderConfiguration.ECC_SYSTEM_NUMBER, "20");
		config.put(DataloaderConfiguration.ECC_RFC_DESTINATION,
				
				"SSC_DATALOADER_GENERIC");
		config.put(DataloaderConfiguration.ECC_LOAD_BALANCED,
				Boolean.FALSE.toString());
		DATA_FILTERS.WEF(filters);
	}

	public static void XJ4_700(HashMap<String, String> config,
			HashMap<String, String[]> filters) {
		config.put(DataloaderConfiguration.OUTBOUND_DESTINATION_NAME, "XJ4_700");
		config.put(DataloaderConfiguration.ECC_CLIENT, "700");
		config.put(DataloaderConfiguration.ECC_USERNAME, ERP_USERNAME);
		config.put(DataloaderConfiguration.ECC_PASSWORD, ERP_PASSWORD);
		config.put(DataloaderConfiguration.ECC_SERVER, "ldcixj4.wdf.sap.corp");
		config.put(DataloaderConfiguration.ECC_SYSTEM_NUMBER, "75");
		config.put(DataloaderConfiguration.ECC_RFC_DESTINATION, "IPC_DL");
		config.put(DataloaderConfiguration.ECC_LOAD_BALANCED,
				Boolean.FALSE.toString());
		DATA_FILTERS.XJ4(filters);
	}

	public static void XJ4_700_LOAD_BLANCED(HashMap<String, String> config,
			HashMap<String, String[]> filters) {
		config.put(DataloaderConfiguration.OUTBOUND_DESTINATION_NAME, "XJ4_700_LOAD_BLANCED");
		config.put(DataloaderConfiguration.ECC_CLIENT, "700");
		config.put(DataloaderConfiguration.ECC_USERNAME, ERP_USERNAME);
		config.put(DataloaderConfiguration.ECC_PASSWORD, ERP_PASSWORD);
		config.put(DataloaderConfiguration.ECC_SERVER, "ldcixj4.wdf.sap.corp");
		config.put(DataloaderConfiguration.ECC_SYSTEM_ID, "X4J");
		config.put(DataloaderConfiguration.ECC_RFC_DESTINATION, "IPC_DL");
		config.put(DataloaderConfiguration.ECC_LOAD_BALANCED,
				Boolean.TRUE.toString());
		config.put(DataloaderConfiguration.ECC_GROUP, "PUBLIC");
		DATA_FILTERS.XJ4(filters);
	}

	public static void XJ4_700_WITHOUD_DESTINATION_NAME_LOAD_BLANCED(HashMap<String, String> config,
			HashMap<String, String[]> filters) {
		config.put(DataloaderConfiguration.OUTBOUND_DESTINATION_NAME, "");
		config.put(DataloaderConfiguration.ECC_CLIENT, "700");
		config.put(DataloaderConfiguration.ECC_USERNAME, ERP_USERNAME);
		config.put(DataloaderConfiguration.ECC_PASSWORD, ERP_PASSWORD);
		config.put(DataloaderConfiguration.ECC_SERVER, "ldcixj4.wdf.sap.corp");
		config.put(DataloaderConfiguration.ECC_SYSTEM_ID, "X4J");
		config.put(DataloaderConfiguration.ECC_RFC_DESTINATION, "IPC_DL");
		config.put(DataloaderConfiguration.ECC_LOAD_BALANCED,
				Boolean.TRUE.toString());
		config.put(DataloaderConfiguration.ECC_GROUP, "PUBLIC");
		DATA_FILTERS.XJ4(filters);
	}
}
