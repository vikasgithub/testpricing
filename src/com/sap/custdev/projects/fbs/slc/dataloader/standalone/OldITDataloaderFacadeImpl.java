package com.sap.custdev.projects.fbs.slc.dataloader.standalone;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.sap.custdev.projects.fbs.slc.dataloader.settings.IDataloaderConfiguration;
import com.sap.sxe.dbmt.imp.a_kb_data_key;
import com.sap.sxe.loader.controller.events.DataExchangeListener;

public class OldITDataloaderFacadeImpl {

	private Logger LOGGER;
	private IDataloaderConfiguration configuration;
	private HashMap<String, String[]> filters;
	private HashMap<String, String> config;

	@Before
	public void setUp() {

		LOGGER = Logger
				.getLogger("com.sap.custdev.projects.fbs.slc.dataloader"); //$NON-NLS-1$
		config = new HashMap<String, String>();

		filters = new HashMap<String, String[]>();
		config = new HashMap<String, String>();
		
//		ERP_CONNECTIONS.X0Q_700(config, filters);
		ERP_CONNECTIONS.X0Q_700_Old(config, filters);
		DATABASE_CONNECTIONS.ORACLE(config);
		HYBRIS_ENVIRONMENT.init(config);

		try {
			String[] filter = filters.get(DATA_FILTERS.KNOWLEDGEBASIS);
			config.put(
					OldDataloaderConfiguration.KB_FILTER_FILE_PATH,
					filter == null ? null : DATA_FILTERS.createFile(null,
							"knowledgebasis", filter, ".txt").getAbsolutePath());

			filter = filters.get(DATA_FILTERS.MATERIALS);
			config.put(
					OldDataloaderConfiguration.MATERIALS_FILTER_FILE_PATH,
					filter == null ? null : DATA_FILTERS.createFile(null,
							"materials", filter, ".txt").getAbsolutePath());

			filter = filters.get(DATA_FILTERS.CONDITIONS);
			config.put(
					OldDataloaderConfiguration.CONDITIONS_FILTER_FILE_PATH,
					filter == null ? null : DATA_FILTERS.createFile(null,
							"conditions", filter, ".txt").getAbsolutePath());

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString());
			assertTrue("filter files could not be created", false);
		}

		configuration = new OldDataloaderConfiguration(true, true, true, true,
				true, config).getConfiguration();
	}

	@Test
	public void testCreateTables() {

		final DataloaderFacadeImpl facadeImpl = new DataloaderFacadeImpl();
		final Status status = facadeImpl
				.createTables(configuration.getTarget());
		if (status.isOK()) {
			LOGGER.log(Level.FINE, status.getMessage());
		} else {
			LOGGER.log(Level.SEVERE, status.getMessage());
		}
		assertTrue("status should be ok", status.isOK());

	}

	@Test
	public void testInitialDownload() {

		final DataloaderFacadeImpl facadeImpl = new DataloaderFacadeImpl();

		final Status status = facadeImpl.initialDownload(configuration,
				new IProgressListener() {

					@Override
					public void progressMessage(String message) {
						LOGGER.log(Level.INFO, message);
					}

				},

				new DataExchangeListener() {

					@Override
					public void updatedKb(a_kb_data_key kb) {
						LOGGER.log(Level.INFO, "UPDATED KB: " + kb.get_kbname()
								+ "/" + kb.get_kbversion());
					}

					@Override
					public void deletedKb(a_kb_data_key kb) {
					}

					@Override
					public void updatedCondition(String key) {
						LOGGER.log(Level.INFO, "UPDATED CONDITION: " + key);
					}

					@Override
					public void deletedCondition(String key) {
					}
				});

		if (status.isOK()) {
			LOGGER.log(Level.FINE, status.getMessage());
		} else {
			LOGGER.log(Level.SEVERE, status.getMessage());
		}
		assertTrue("status should be ok", status.isOK());
	}
	
}
