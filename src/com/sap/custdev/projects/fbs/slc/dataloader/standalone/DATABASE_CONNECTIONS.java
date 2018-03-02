package com.sap.custdev.projects.fbs.slc.dataloader.standalone;

import java.util.HashMap;

import com.sap.custdev.projects.fbs.slc.dataloader.settings.IDatabaseSetting;

public class DATABASE_CONNECTIONS {
	
	public static void MYSQL_LOCAL(HashMap<String, String> config) {
		config.put(DataloaderConfiguration.DB_CLIENT, "000");
		config.put(DataloaderConfiguration.DB_USERNAME, "root");
		config.put(DataloaderConfiguration.DB_PASSWORD, "root");
		config.put(DataloaderConfiguration.DB_HOST,
				"localhost");
		config.put(DataloaderConfiguration.DB_PORT, "3306");
		config.put(DataloaderConfiguration.DB_NAME, "pricing");
		config.put(DataloaderConfiguration.DB_TYPE, IDatabaseSetting.MYSQL);
		config.put(DataloaderConfiguration.TARGET_FROM_PROPERTIES,
				Boolean.FALSE.toString());
	}

	public static void MYSQL(HashMap<String, String> config) {
		config.put(DataloaderConfiguration.DB_CLIENT, "000");
		config.put(DataloaderConfiguration.DB_USERNAME, "hybris_extern");
		config.put(DataloaderConfiguration.DB_PASSWORD, "welcome");
		config.put(DataloaderConfiguration.DB_HOST,
				"vehbr52030.dhcp.wdf.sap.corp");
		config.put(DataloaderConfiguration.DB_PORT, "3306");
		config.put(DataloaderConfiguration.DB_NAME, "sscx0q700wef504");
		config.put(DataloaderConfiguration.DB_TYPE, IDatabaseSetting.MYSQL);
		config.put(DataloaderConfiguration.TARGET_FROM_PROPERTIES,
				Boolean.FALSE.toString());
	}

	public static void ORACLE(HashMap<String, String> config) {
		config.put(DataloaderConfiguration.DB_CLIENT, "000");
		config.put(DataloaderConfiguration.DB_USERNAME, "wef504");
		config.put(DataloaderConfiguration.DB_PASSWORD, "wef504");
		config.put(DataloaderConfiguration.DB_HOST,
				"dewdfgwd01466.wdf.sap.corp");
		config.put(DataloaderConfiguration.DB_PORT, "1521");
		config.put(DataloaderConfiguration.DB_NAME, "orcl");
		config.put(DataloaderConfiguration.DB_TYPE, IDatabaseSetting.ORACLE);
		config.put(DataloaderConfiguration.TARGET_FROM_PROPERTIES,
				Boolean.FALSE.toString());
	}

	public static void MSSQLTEST(HashMap<String, String> config) {
		config.put(DataloaderConfiguration.DB_CLIENT, "000");
		config.put(DataloaderConfiguration.DB_USERNAME, "ssc_sme");
		config.put(DataloaderConfiguration.DB_PASSWORD, "welcome");
		config.put(DataloaderConfiguration.DB_HOST,
				"dewdfgwp01685.wdf.sap.corp");
		config.put(DataloaderConfiguration.DB_PORT, "49287");
		config.put(DataloaderConfiguration.DB_NAME, "SSC_SME");
		config.put(DataloaderConfiguration.DB_TYPE, IDatabaseSetting.MSSQL_2);
		config.put(DataloaderConfiguration.TARGET_FROM_PROPERTIES,
				Boolean.FALSE.toString());
	}

	public static void MSSQLREMOTE(HashMap<String, String> config) {
		config.put(DataloaderConfiguration.DB_CLIENT, "000");
		config.put(DataloaderConfiguration.DB_USERNAME, "SMEAdmin");
		config.put(DataloaderConfiguration.DB_PASSWORD, "SMEAdmin");
		config.put(DataloaderConfiguration.DB_HOST,
				"vehbr52074.dhcp.wdf.sap.corp");
		config.put(DataloaderConfiguration.DB_PORT, "1433");
		config.put(DataloaderConfiguration.DB_NAME, "SSC2");
		config.put(DataloaderConfiguration.DB_TYPE, IDatabaseSetting.MSSQL_2);
		config.put(DataloaderConfiguration.TARGET_FROM_PROPERTIES,
				Boolean.FALSE.toString());
	}

	public static void MSSQLCENTRAL(HashMap<String, String> config) {
		config.put(DataloaderConfiguration.DB_CLIENT, "000");
		config.put(DataloaderConfiguration.DB_USERNAME, "sa");
		config.put(DataloaderConfiguration.DB_PASSWORD, "root2014!");
		config.put(DataloaderConfiguration.DB_HOST,
				"dewdfgwd01401.wdf.sap.corp");
		config.put(DataloaderConfiguration.DB_PORT, "1433");
		config.put(DataloaderConfiguration.DB_NAME, "SSC_SME");
		config.put(DataloaderConfiguration.DB_TYPE, IDatabaseSetting.MSSQL_2);
		config.put(DataloaderConfiguration.TARGET_FROM_PROPERTIES,
				Boolean.FALSE.toString());
	}

	public static void MSSQLLOCAL(HashMap<String, String> config) {
		config.put(DataloaderConfiguration.DB_CLIENT, "000");
		config.put(DataloaderConfiguration.DB_USERNAME, "SSC");
		config.put(DataloaderConfiguration.DB_PASSWORD, "SSC");
		config.put(DataloaderConfiguration.DB_HOST, "localhost");
		config.put(DataloaderConfiguration.DB_PORT, "1433");
		config.put(DataloaderConfiguration.DB_NAME, "SSC");
		config.put(DataloaderConfiguration.DB_TYPE, IDatabaseSetting.MSSQL_2);
		config.put(DataloaderConfiguration.TARGET_FROM_PROPERTIES,
				Boolean.FALSE.toString());
	}

	public static void HANA(HashMap<String, String> config) {
		config.put(DataloaderConfiguration.DB_CLIENT, "000");
//		config.put(DataloaderConfiguration.DB_USERNAME, "ADMINISTRATOR");
//		config.put(DataloaderConfiguration.DB_PASSWORD, "Initial1!");
		config.put(DataloaderConfiguration.DB_USERNAME, "DLTESTUSR");
		config.put(DataloaderConfiguration.DB_PASSWORD, "DLTest25");
		config.put(DataloaderConfiguration.DB_HOST, "lddbcpq.mo.sap.corp");
		config.put(DataloaderConfiguration.DB_PORT, "31015");
		config.put(DataloaderConfiguration.DB_NAME, "CPQ");
		config.put(DataloaderConfiguration.DB_TYPE, IDatabaseSetting.HANA);
		config.put(DataloaderConfiguration.TARGET_FROM_PROPERTIES, Boolean.FALSE.toString());
	}
}
