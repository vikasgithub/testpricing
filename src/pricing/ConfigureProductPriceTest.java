package pricing;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.PacketTooBigException;
import com.sap.custdev.projects.fbs.slc.cfg.ConfigSession;
import com.sap.custdev.projects.fbs.slc.cfg.client.IDocument;
import com.sap.custdev.projects.fbs.slc.cfg.client.IPricingDocumentInfo;
import com.sap.custdev.projects.fbs.slc.cfg.client.ISPCGetDocumentInfo;
import com.sap.custdev.projects.fbs.slc.cfg.client.ItemInfoData;
import com.sap.custdev.projects.fbs.slc.cfg.command.beans.DocumentData;
import com.sap.custdev.projects.fbs.slc.cfg.exception.IpcCommandException;
import com.sap.custdev.projects.fbs.slc.cfg.ipintegration.InteractivePricingException;
import com.sap.custdev.projects.fbs.slc.helper.ConfigSessionManager;
import com.sap.custdev.projects.fbs.slc.kbo.local.OrchestratedCstic;
import com.sap.custdev.projects.fbs.slc.kbo.local.OrchestratedInstance;
import com.sap.custdev.projects.fbs.slc.pricing.slc.bean.SLCDocumentDataBean;
import com.sap.custdev.projects.fbs.slc.pricing.slc.bean.SLCItemDataBean;
import com.sap.sxe.sys.SAPDate;

import util.IpcPricingHelper;

public class ConfigureProductPriceTest {

	public static final String USAGE = "A";
	public static final String APPL = "V";
	private static final String PROCEDURE = "ZBSTND";
	//private String docId = IpcPricingHelper.byteArrayToHexString();
	private static final String PRODUCT_ID_1 = "000000007100007990";
	
	@Before
	public void setUp() throws Exception {
		//System.setProperty("runtimeEnvironment", "hybris");
		System.setProperty("PRICING_DATA_MODEL", "CRM");
		//CacheUtil.loadCacheRegions();		
	}
	
	@Test
	public void testPricing() throws IpcCommandException, InteractivePricingException {
	
		Hashtable<String, String> context = new Hashtable<>();
		context.put("VBAK-VKORG", "2000");
		context.put("VBAP-WERKS", "1031");
		context.put("VBAP-MATWZ", "7100007990");
		context.put("VBAK-ZZSME", "X");
		context.put("VBAP-KWMENG", "1");
		
		ConfigSession session = new ConfigSession();
		session.createSession("true", "S1", null, true, "EN");
		//ConfigSessionManager sessionManager = new ConfigSessionManager(session);
		String rfcConfigId = "";
		String kbLogSys = null;
		String kbName = null;
		String kbVersion = null;
		String kbProfile = null;
		Integer kbIdInt = null;
		String kbDateStr = "20180301";
		String kbBuild = null;
		String useTraceStr = null;
		boolean setRichConfigId = false;
		
		String configId = session.createConfig(rfcConfigId, "000000007100007990", "MARA", 
				kbLogSys, kbName, kbVersion, kbProfile,
				kbIdInt, kbDateStr, kbBuild, useTraceStr, context, setRichConfigId);
		
		OrchestratedInstance rootInstanceLocal = session.getRootInstanceLocal("S1");
		OrchestratedCstic[] cstics = rootInstanceLocal.getCstics();
		
		
		for (OrchestratedCstic cstic : cstics) {
			if (cstic.getName().equals("VC_ORDER_QTY_WORK")) {
				cstic.setValues(new String[] {"3"});
				System.out.println(cstic.getName());
			}
			if (cstic.getName().equals("VC_WIDTH_SLS_I")) {
				cstic.setValues(new String[] {"50"});
				System.out.println(cstic.getName());
			}
			if (cstic.getName().equals("VC_LENGTH_SLS_I")) {
				cstic.setValues(new String[] {"120"});
				System.out.println(cstic.getName());
			}
		}
		
		ItemInfoData itemPricingContext = createItemContext(null, "20180206");
		IDocument documentPricingContext = createDocumentData(PricingAPITest.APPL, PricingAPITest.USAGE);
		session.getConfigSessionManager().setPricingContext("S1", documentPricingContext, itemPricingContext, null);
		session.setInteractivePricingEnabled(configId, true);
		
		ISPCGetDocumentInfo documentInfo = session.getDocumentInfo("S1");
		IPricingDocumentInfo pricingDocumentInfo = session.getPricingDocumentInfo(documentInfo.getDocumentInfo()[0].getDocumentID(), null, true);
		System.out.println(pricingDocumentInfo);
	}
	
	public DocumentData createDocumentData(String application, String usage) {
		DocumentData documentData = new DocumentData();
	    //documentData.setExternalId(IpcPricingHelper.INITIAL_GUID);
	    documentData.setUsage(usage);
	    documentData.setApplication(application);
	    //documentData.setVariantConditionTargetFieldname("PRT_VARCOND");
	    documentData.setGroupConditionProcessingFlag(Boolean.TRUE);
	    //documentData.setDocumentId(documentId);
	    documentData.setPricingProcedure(PROCEDURE);
	    //documentData.setAuthorityDisplay("0100");
	    //documentData.setAuthorityEdit("0100");
	    documentData.setDocumentCurrencyUnit("CAD");
	    documentData.setLocalCurrencyUnit("CAD");
	    documentData.setExpiringCheckDate(IpcPricingHelper.getDateFromAbapString("2006-09-20"));
	    documentData.setEditMode('A');
	    documentData.setPerformTrace(Boolean.TRUE);
	    documentData.setSalesOrg("2000");
	    documentData.setDistributionChannel("00");
	    documentData.addAttribute("PRSFD", "X");
	    documentData.addAttribute("PMATN", PRODUCT_ID_1);
	    documentData.addAttribute("VKORG", "2000");
	    documentData.addAttribute("VTWEG", "00");
	    documentData.addAttribute("WAERK", "CAD");
	    documentData.addAttribute("KUNNR", "10000410");
	    documentData.addAttribute("BZIRK", "200001");
	    documentData.addAttribute("ZZPARTNER", "10000410");
	    documentData.addAttribute("ZZSTND_GRPID", "0000020027");
		return documentData;
	}
	
	public ItemInfoData createItemContext(String documentId, String priceDate) {
		ItemInfoData itemData = new ItemInfoData();
	      itemData.setSuppressBomExplosion(Boolean.TRUE);
	      itemData.setDocumentId(documentId);
	      itemData.setExternalId("0000000001");
	      itemData.setHighLevelItemID(IpcPricingHelper.INITIAL_GUID);
	      itemData.setProductId(PRODUCT_ID_1);
	      //itemData.setMultiplicity(0);
	      itemData.setPricingRelevant(Boolean.TRUE);
	      //itemData.setExchangeRateDate("0000-00-00");// KURSK_DAT
	      itemData.setExchangeRateType("M");// KURST
	      //itemData.setExchangeRate(BigDecimal.valueOf(0));// KURSK
	      itemData.setQuantity(BigDecimal.valueOf(3));// MGAME
	      itemData.setQuantityUnit("EA");// VRKME
	      itemData.addTimestamp("PRICE_DATE", priceDate);
	      itemData.addAttribute("PRODUCT", PRODUCT_ID_1);
		return itemData;
	}
	
}
