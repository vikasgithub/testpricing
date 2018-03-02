package com.sap.custdev.projects.fbs.slc.dataloader.standalone;

import java.util.HashMap;

import com.sap.custdev.projects.fbs.slc.dataloader.settings.IClientSetting;
import com.sap.custdev.projects.fbs.slc.dataloader.settings.IDatabaseSetting;
import com.sap.custdev.projects.fbs.slc.dataloader.settings.IDataloaderConfiguration;
import com.sap.custdev.projects.fbs.slc.dataloader.settings.IDataloaderSource;
import com.sap.custdev.projects.fbs.slc.dataloader.settings.IDataloaderTarget;
import com.sap.custdev.projects.fbs.slc.dataloader.settings.IEccSetting;
import com.sap.custdev.projects.fbs.slc.dataloader.settings.IInitialDownloadConfiguration;

public class OldDataloaderConfiguration {

	public static final String OUTBOUND_DESTINATION_NAME = "OUTBOUND_DESTINATION_NAME";
	public static final String ECC_CLIENT = "ECC_CLIENT";
	public static final String ECC_USERNAME = "ECC_USERNAME";
	public static final String ECC_PASSWORD = "ECC_PASSWORD";
	public static final String ECC_SERVER = "ECC_SERVER";
	public static final String ECC_SYSTEM_NUMBER = "ECC_SYSTEM_NUMBER";
	public static final String ECC_SYSTEM_ID = "ECC_SYSTEM_ID";
	public static final String ECC_RFC_DESTINATION = "ECC_RFC_DESTINATION";
	public static final String ECC_GROUP = "ECC_GROUP";
	public static final String ECC_LOAD_BALANCED = "ECC_LOAD_BALANCED";
	public static final String BACKEND_TYPE = "BACKEND_TYPE";

	public static final String DB_CLIENT = "DB_CLIENT";
	public static final String DB_USERNAME = "DB_USERNAME";
	public static final String DB_PASSWORD = "DB_PASSWORD";
	public static final String DB_HOST = "DB_HOST";
	public static final String DB_PORT = "DB_PORT";
	public static final String DB_NAME = "DB_NAME";
	public static final String DB_TYPE = "DB_TYPE";
	public static final String TARGET_FROM_PROPERTIES = "TARGET_FROM_PROPERTIES";

	public static final String KB_FILTER_FILE_PATH = "KB_FILTER_FILE_PATH";
	public static final String MATERIALS_FILTER_FILE_PATH = "MATERIALS_FILTER_FILE_PATH";
	public static final String CONDITIONS_FILTER_FILE_PATH = "CONDITION_FILTER_FILE_PATH";

	private IDataloaderConfiguration configuration;
	private HashMap<String, String> configurationMap;

	private boolean _DOWNLOAD_SCE_DATA;
	private boolean _DOWNLOAD_MATERIAL_DATA;
	private boolean _DOWNLOAD_DICTIONARY_DATA;
	private boolean _DOWNLOAD_CONDITION_DATA;
	private boolean _DOWNLOAD_CUSTOMIZING_DATA;

	public IDataloaderConfiguration getConfiguration() {
		return configuration;
	}

	public OldDataloaderConfiguration(boolean DOWNLOAD_SCE_DATA,
			boolean DOWNLOAD_MATERIAL_DATA, boolean DOWNLOAD_DICTIONARY_DATA,
			boolean DOWNLOAD_CONDITION_DATA, boolean DOWNLOAD_CUSTOMIZING_DATA,
			HashMap<String, String> configMap) {

		this.configurationMap = configMap;

		this._DOWNLOAD_SCE_DATA = DOWNLOAD_SCE_DATA;
		this._DOWNLOAD_MATERIAL_DATA = DOWNLOAD_MATERIAL_DATA;
		this._DOWNLOAD_DICTIONARY_DATA = DOWNLOAD_DICTIONARY_DATA;
		this._DOWNLOAD_CONDITION_DATA = DOWNLOAD_CONDITION_DATA;
		this._DOWNLOAD_CUSTOMIZING_DATA = DOWNLOAD_CUSTOMIZING_DATA;

		configuration = new IDataloaderConfiguration() {

			@Override
			public IDataloaderSource getSource() {
				return new IDataloaderSource() {

					@Override
					public IEccSetting getEccSetting() {
						return new IEccSetting() {

							@Override
							public String getGroup() {
								return isLoadBalanced() ? ECC_GROUP : null;
							}

							@Override
							public String getMessageServer() {
								return configurationMap.get(ECC_SERVER);
							}

							@Override
							public String getSid() {
								if (isLoadBalanced()) {
									return configurationMap.get(ECC_SYSTEM_ID);
								} else {
									return configurationMap
											.get(ECC_SYSTEM_NUMBER);
								}
							}

							@Override
							public boolean isLoadBalanced() {
								return Boolean.TRUE.toString()
										.equals(configurationMap
												.get(ECC_LOAD_BALANCED));
							}

						};
					}

					@Override
					public IClientSetting getClientSetting() {
						return new IClientSetting() {

							@Override
							public String getClient() {
								return configurationMap.get(ECC_CLIENT);
							}

							@Override
							public String getPassword() {
								return configurationMap.get(ECC_PASSWORD);
							}

							@Override
							public String getUser() {
								return configurationMap.get(ECC_USERNAME);
							}

						};
					}

					

					@Override
					public String getRfcDestination() {
						return configurationMap.get(ECC_RFC_DESTINATION);
					}

					

				};
			}

			@Override
			public IDataloaderTarget getTarget() {
				if (Boolean.TRUE.toString().equals(
						configurationMap.get(TARGET_FROM_PROPERTIES))) {
					return null;
				}

				return new IDataloaderTarget() {

					@Override
					public IDatabaseSetting getDatabaseSetting() {

						return new IDatabaseSetting() {

							@Override
							public String getDatabaseName() {
								return configurationMap.get(DB_NAME);
							}

							@Override
							public String getHost() {
								return configurationMap.get(DB_HOST);
							}

							@Override
							public int getPort() {
								return Integer.parseInt(configurationMap
										.get(DB_PORT));
							}

							@Override
							public String getDatabaseType() {
								return configurationMap.get(DB_TYPE);
							}
						};
					}

					@Override
					public IClientSetting getClientSetting() {
						return new IClientSetting() {

							@Override
							public String getClient() {
								return configurationMap.get(DB_CLIENT);
							}

							@Override
							public String getPassword() {
								return configurationMap.get(DB_PASSWORD);
							}

							@Override
							public String getUser() {
								return configurationMap.get(DB_USERNAME);
							}

						};
					}

				};
			}

			@Override
			public IInitialDownloadConfiguration getInitialDownloadConfiguration() {
				return new IInitialDownloadConfiguration() {

					@Override
					public boolean isSceFilterEnabled() {
						return configurationMap.get(KB_FILTER_FILE_PATH) != null;
					}

					@Override
					public String getSceFilterFile() {
						return configurationMap.get(KB_FILTER_FILE_PATH);
					}

					@Override
					public boolean isConditionsFilterEnabled() {
						return configurationMap
								.get(CONDITIONS_FILTER_FILE_PATH) != null;
					}

					@Override
					public String getConditionsFilterFile() {
						return configurationMap
								.get(CONDITIONS_FILTER_FILE_PATH);
					}

					@Override
					public boolean isMaterialsFilterEnabled() {
						return configurationMap.get(MATERIALS_FILTER_FILE_PATH) != null;
					}

					@Override
					public String getMaterialsFilterFile() {
						return configurationMap.get(MATERIALS_FILTER_FILE_PATH);
					}

					@Override
					public boolean downloadSceData() {
						return _DOWNLOAD_SCE_DATA;
					}

					@Override
					public boolean downloadConditionData() {
						return _DOWNLOAD_CONDITION_DATA;
					}

					@Override
					public boolean downloadCustomizingData() {
						return _DOWNLOAD_CUSTOMIZING_DATA;
					}

					@Override
					public boolean downloadDictionaryData() {
						return _DOWNLOAD_DICTIONARY_DATA;
					}

					@Override
					public boolean downloadMaterialData() {
						return _DOWNLOAD_MATERIAL_DATA;
					}

				};
			}
		};
	}

}
