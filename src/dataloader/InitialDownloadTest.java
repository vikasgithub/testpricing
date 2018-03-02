package dataloader;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.sap.custdev.projects.fbs.slc.dataloader.settings.IDatabaseSetting;
import com.sap.custdev.projects.fbs.slc.dataloader.settings.IDataloaderConfiguration;
import com.sap.custdev.projects.fbs.slc.dataloader.standalone.DataloaderConfiguration;
import com.sap.custdev.projects.fbs.slc.dataloader.standalone.DataloaderFacadeImpl;
import com.sap.custdev.projects.fbs.slc.dataloader.standalone.IProgressListener;
import com.sap.custdev.projects.fbs.slc.dataloader.standalone.Status;
import com.sap.sxe.dbmt.imp.a_kb_data_key;
import com.sap.sxe.loader.controller.events.DataExchangeListener;

public class InitialDownloadTest {

	private Logger LOGGER;
	private IDataloaderConfiguration configuration;
	private HashMap<String, String[]> filters;
	private HashMap<String, String> config;
	
	public static final String KNOWLEDGEBASIS = "KNOWLEDGEBASIS";
	public static final String MATERIALS = "MATERIALS";
	public static final String CONDITIONS = "CONDITIONS";

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
	    DR5(filters);
	}
	
	public static void DR5(HashMap<String, String[]> filters) {
		filters.put(KNOWLEDGEBASIS, new String[] { "KT_680_ROL,20170122_2001_OBRO_PP01" });
				
		filters.put(MATERIALS, new String[] { 
				"table=MARA,", 
				"table=MAKT,MARA",
				"table=MARM,MARA", 
				"table=MARC,MARA", 
				"table=MLAN,MARA",
				"table=MVKE,MARA", 
				"table=CUCFG,MARA",
				"range=MARA,MATNR,I,EQ,000000007100008340",
				"range=MARA,MATNR,I,EQ,000000007100000059",
				"range=MARA,MATNR,I,EQ,000000007100007990",
				"range=MARA,MATNR,I,EQ,000000007100049023",
				"range=MARA,MATNR,I,EQ,000000007100005478",
				"range=MARA,MATNR,I,EQ,000000007100002834"
				});
				
		filters.put(CONDITIONS, new String[] { 
				"table=A501",
				"table=A502",
				"table=A503",
				"table=A504",
				"table=A505",
				"table=A506",
				"table=A513",
				"table=A514",
				"table=A515",
				"table=A516",
				"table=A517",
				"table=A518",
				"table=A519",
				"table=A520",
				"table=A521",
				"table=A522",
				"table=A523",
				"table=A525",
				"table=A526",
				"table=A527",
				"table=A528",
				"table=A529",
				"table=A530",
				"table=A531",
				"table=A532",
				"table=A533",
				"table=A534",
				"table=A535",
				"table=A538",
				"table=A539",
				"table=A540",
				"table=A541",
				"table=A542",
				"table=A543",
				"table=A545",
				"table=A546",
				"table=A547",
				"table=A548",
				"table=A549",
				"table=A550",
				"table=A552",
				"table=A553",
				"table=A554",
				"table=A556",
				"table=A557",
				"table=A558",
				"table=A559",
				"table=A561",
				"table=A562",
				"table=A567",
				"table=A057",
				"table=A304",
				"table=A305",
				"table=A307",
	/*			"range=A*,KSCHL,I,EQ,ZRQ1",
				"range=A*,KSCHL,I,EQ,ZRQ2",
				"range=A*,KSCHL,I,EQ,ZRQ3",
				"range=A*,KSCHL,I,EQ,ZRQ6",
				"range=A*,KSCHL,I,EQ,ZRQ7",
				"range=A*,KSCHL,I,EQ,ZRQ8",
				"range=A*,KSCHL,I,EQ,ZRQ9",
				"range=A*,KSCHL,I,EQ,ZP00",
				"range=A*,KSCHL,I,EQ,ZP02",
				"range=A*,KSCHL,I,EQ,ZVHP",
				"range=A*,KSCHL,I,EQ,ZVPD",
				"range=A*,KSCHL,I,EQ,ZVPB",
				"range=A*,KSCHL,I,EQ,ZVMC",
				"range=A*,KSCHL,I,EQ,ZVPP",
				"range=A*,KSCHL,I,EQ,ZVGA",*/
				"range=A*,VKORG,I,EQ,1000",
				"range=A*,VKORG,I,EQ,4033",
				"range=A*,VKORG,I,EQ,2000",
//				"range=A*,VKORG,I,EQ,2000",
				"range=A*,VTWEG,I,EQ,00",
//				"range=A*,DATBI,I,GT,20180201",
				});
	}
	
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
	
	@Before
	public void setUp() {

		LOGGER = Logger
				.getLogger("com.sap.custdev.projects.fbs.slc.dataloader"); //$NON-NLS-1$
		config = new HashMap<String, String>();

		filters = new HashMap<String, String[]>();
		config = new HashMap<String, String>();
		
	    DR5_301(config, filters);
	    MYSQL_LOCAL(config);
		init(config);

		try {
			String[] filter = filters.get(KNOWLEDGEBASIS);
			config.put(
					DataloaderConfiguration.KB_FILTER_FILE_PATH,
					filter == null ? null : createFile(null,
							"knowledgebasis", filter, ".txt").getAbsolutePath());

			filter = filters.get(MATERIALS);
			config.put(
					DataloaderConfiguration.MATERIALS_FILTER_FILE_PATH,
					filter == null ? null : createFile(null,
							"materials", filter, ".txt").getAbsolutePath());

			filter = filters.get(CONDITIONS);
			config.put(
					DataloaderConfiguration.CONDITIONS_FILTER_FILE_PATH,
					filter == null ? null : createFile(null,
							"conditions", filter, ".txt").getAbsolutePath());

		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.toString());
			assertTrue("filter files could not be created", false);
		}

		configuration = new DataloaderConfiguration(true, true, true, true,
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
	
	public static File createFile(File folder, String name, String[] content,
			String postfix) throws IOException {
		File filterFile = null;
		if (folder == null) {
			filterFile = File.createTempFile(name, postfix);
		} else {
			filterFile = new File(folder, name + postfix);
		}
		final FileOutputStream fileOutputStream = new FileOutputStream(
				filterFile);
		final PrintWriter printWriter = new PrintWriter(fileOutputStream, true);
		for (int i = 0; i < content.length; i++) {
			printWriter.println(content[i]);
		}
		printWriter.close();
		filterFile.deleteOnExit();
		return filterFile;
	}
	
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
