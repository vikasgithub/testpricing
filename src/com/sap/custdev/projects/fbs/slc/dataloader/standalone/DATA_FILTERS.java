package com.sap.custdev.projects.fbs.slc.dataloader.standalone;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class DATA_FILTERS {

	public static final String KNOWLEDGEBASIS = "KNOWLEDGEBASIS";
	public static final String MATERIALS = "MATERIALS";
	public static final String CONDITIONS = "CONDITIONS";

	public static void X0Q(HashMap<String, String[]> filters) {
		filters.put(KNOWLEDGEBASIS, new String[] { "AD_ROL,AD_ROL_RTV" });
		filters.put(MATERIALS, new String[] { 
				"table=MARA,", 
				"table=MAKT,MARA",
				"table=MARM,MARA", 
				"table=MARC,MARA", "table=MLAN,MARA",
				"table=MVKE,MARA", "table=CUCFG,MARA",
				"range=MARA,MATNR,I,CP,*" });
		filters.put(CONDITIONS, new String[] { "table=A304", "table=A305",
				"table=A057", "range=A*,MATNR,I,CP,*" });
	}
	
	public static void DR5(HashMap<String, String[]> filters) {
		filters.put(KNOWLEDGEBASIS, new String[] { "AD_ROL,AD_ROL_RTV" });
				
		filters.put(MATERIALS, new String[] { 
				"table=MARA,", 
				"table=MAKT,MARA",
				"table=MARM,MARA", 
				"table=MARC,MARA", 
				"table=MLAN,MARA",
				"table=MVKE,MARA", 
				"table=CUCFG,MARA",
				"range=MARA,KZKFG,I,EQ,X" });
				
		filters.put(CONDITIONS, new String[] { 
				"table=A519",
				"table=A520",
				"table=A550",
				"table=A503",
				"table=A506",
				"table=A305",
				"table=A515",
				"table=A518",
				"table=A522",
				"table=A523",
				"table=A533",
				"table=A534",
				"table=A546",
				"table=A547",
				"table=A548",
				"table=A556",
				"table=A557",
				"range=A*,KSCHL,I,EQ,ZRQ1",
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
				"range=A*,KSCHL,I,EQ,ZVGA",
				"range=A*,VKORG,I,EQ,1000",
				"range=A*,VKORG,I,EQ,2000",
				"range=A*,VTWEG,I,EQ,00",
				"range=A*,DATBI,I,GT,20180201",
				"range=A*,MATNR,I,EQ,000000007100005063",
				"range=A*,MATNR,I,EQ,000000007100005478" });
	}

	public static void XJ4(HashMap<String, String[]> filters) {
		filters.put(KNOWLEDGEBASIS, new String[] { "FBS_OFFICE,1.0.0.2" });
		filters.put(MATERIALS, new String[] { "table=MARA,", "table=MAKT,MARA",
				"table=MARM,MARA", "table=MARC,MARA", "table=MLAN,MARA",
				"table=MVKE,MARA", "table=CUCFG,MARA",
				"range=MARA,MATNR,I,CP,1*" });
		filters.put(CONDITIONS, new String[] { 
				"table=A519",
				"table=A520",
				"table=A550",
				"table=A503",
				"table=A506",
				"table=A305",
				"table=A515",
				"table=A518",
				"table=A522",
				"table=A523",
				"table=A533",
				"table=A534",
				"table=A546",
				"table=A547",
				"table=A548",
				"table=A556",
				"table=A557",
				"range=A*,KSCHL,I,EQ,ZRQ1",
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
				"range=A*,KSCHL,I,EQ,ZVGA",
				"range=A*,VKORG,I,EQ,1000",
				"range=A*,VKORG,I,EQ,2000",
				"range=A*,VTWEG,I,EQ,00",
				"range=A*,DATBI,I,GT,20180201",
				"range=A*,MATNR,I,EQ,000000007100005063",
				"range=A*,MATNR,I,EQ,000000007100005478" });
	}

	public static void WEF(HashMap<String, String[]> filters) {
		filters.put(KNOWLEDGEBASIS, new String[] { "P_600,01",
				"P_500,01", "P_501,01",
				"P_502,01", "KB_1898,KB_1898_01",
				"T-AQ303,001" });
		filters.put(MATERIALS, new String[] { "table=MARA,", "table=MAKT,MARA",
				"table=MARM,MARA", "table=MARC,MARA", "table=MLAN,MARA",
				"table=MVKE,MARA", "table=CUCFG,MARA",
				"range=MARA,MATNR,I,CP,WCEM*", "range=MARA,MATNR,I,CP,WEC*",
				"range=MARA,MATNR,I,CP,YSAP*" });
		filters.put(CONDITIONS, new String[] { "table=A304", "table=A305",
				"table=A057", "range=A*,MATNR,I,CP,WCEM*",
				"range=A*,MATNR,I,CP,WEC*", "range=A*,MATNR,I,CP,YSAP*" });
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

}
