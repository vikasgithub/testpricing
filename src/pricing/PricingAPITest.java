package pricing;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sap.custdev.projects.fbs.slc.cfg.ConfigSession;
import com.sap.custdev.projects.fbs.slc.cfg.client.IItemInfo;
import com.sap.custdev.projects.fbs.slc.cfg.client.IPricingCondition;
import com.sap.custdev.projects.fbs.slc.cfg.client.IPricingDocumentInfo;
import com.sap.custdev.projects.fbs.slc.cfg.client.IPricingProcedureInfo;
import com.sap.custdev.projects.fbs.slc.cfg.client.ItemInfoData;
import com.sap.custdev.projects.fbs.slc.cfg.command.CreateDocument;
import com.sap.custdev.projects.fbs.slc.cfg.command.CreateItems;
import com.sap.custdev.projects.fbs.slc.cfg.command.GetPricingDocumentInfo;
import com.sap.custdev.projects.fbs.slc.cfg.command.beans.DocumentData;
import com.sap.custdev.projects.fbs.slc.cfg.exception.IpcCommandException;
import com.sap.custdev.projects.fbs.slc.cfg.exception.PricingCommandException;
import com.sap.custdev.projects.fbs.slc.cfg.ipintegration.PricingException;
import com.sap.custdev.projects.fbs.slc.helper.ConfigSessionManager;
import com.sap.custdev.projects.fbs.slc.pricing.spc.api.SPCPricingException;
import com.sap.spc.document.ISPCDocument;
import com.sap.spc.document.ISPCItem;
import com.sap.spe.condmgnt.customizing.IProcedure;
import com.sap.spe.condmgnt.exception.ConditionInconsistentDBException;
import com.sap.spe.condmgnt.masterdata.IConditionRecord;
import com.sap.spe.condmgnt.masterdata.IScale;
import com.sap.spe.condmgnt.masterdata.IScaleLevel;
import com.sap.spe.conversion.ICurrencyValue;
import com.sap.spe.pricing.customizing.IPricingCustomizingEngine;
import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.customizing.PricingCustomizingEngineFactory;
import com.sap.spe.pricing.masterdata.IPricingScaleLevel;
import com.sap.spe.pricing.masterdata.IPricingScaleRate;
import com.sap.spe.pricing.masterdata.impl.PricingScale;
import com.sap.spe.pricing.masterdata.impl.PricingScaleLevel;
import com.sap.spe.pricing.transactiondata.IPricingDocument;
import com.sap.spe.pricing.transactiondata.impl.PricingCondition;
import com.sap.spe.pricing.transactiondata.impl.PricingDocument;

import customizing.ExtendedPricingCondition;
import util.IpcPricingHelper;

public class PricingAPITest {

	public static final String USAGE = "A";
	public static final String APPL = "V";
	private static final String PROCEDURE = "ZBSTND";
	//private String docId = IpcPricingHelper.byteArrayToHexString();
	private static final String PRODUCT_ID_1 = "000000007100002834";
	IItemInfo[] arrItemInfo = null;
	private String[] itemIdArray = { IpcPricingHelper.byteArrayToHexString() };

	ConfigSession session = new ConfigSession();
	ConfigSessionManager sessionManager = null;
	@Before
	public void setUp() throws Exception {
		//System.setProperty("runtimeEnvironment", "hybris");
		System.setProperty("PRICING_DATA_MODEL", "CRM");
		//CacheUtil.loadCacheRegions();

		ConfigSession.init();
		
		sessionManager = new ConfigSessionManager(session);
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void testGetProcedure() throws IpcCommandException {
		IPricingCustomizingEngine prCustEng = PricingCustomizingEngineFactory
				.getFactory().getPricingCustomizingEngine(USAGE);
		IProcedure procedure = null;
		try {
			procedure = prCustEng.getProcedure(APPL, PROCEDURE);
			System.out.println(procedure);
		} catch (ConditionInconsistentDBException e) {
			throw new IpcCommandException(e);
		}
	}
	
	private String createDocument(String application, String usage, String documentId) throws PricingCommandException {
		long startTime = System.currentTimeMillis();
	    DocumentData documentData = createDocumentData(application, usage, documentId);
	    
	    //
	    String docId = new CreateDocument(sessionManager).executeCommand(documentData);
	    System.out.println("CreateDocument: " + (System.currentTimeMillis() - startTime));
	   // assertThat(docId == documentId).as("Unable to create document needed for items").isTrue();
	    return docId;
	  }

	public DocumentData createDocumentData(String application, String usage, String documentId) {
		DocumentData documentData = new DocumentData();
	    documentData.setExternalId(IpcPricingHelper.INITIAL_GUID);
	    documentData.setUsage(usage);
	    documentData.setApplication(application);
	    //documentData.setVariantConditionTargetFieldname("PRT_VARCOND");
	    documentData.setGroupConditionProcessingFlag(Boolean.TRUE);
	    documentData.setDocumentId(documentId);
	    documentData.setPricingProcedure(PROCEDURE);
	    //documentData.setAuthorityDisplay("0100");
	    //documentData.setAuthorityEdit("0100");
	    documentData.setDocumentCurrencyUnit("EUR");
	    documentData.setLocalCurrencyUnit("EUR");
	    documentData.setExpiringCheckDate(IpcPricingHelper.getDateFromAbapString("2006-09-20"));
	    documentData.setEditMode('A');
	    documentData.setPerformTrace(Boolean.TRUE);
	    documentData.setSalesOrg("4033");
	    documentData.setDistributionChannel("00");
	    documentData.addAttribute("PRSFD", "X");
	    documentData.addAttribute("PMATN", PRODUCT_ID_1);
	    documentData.addAttribute("VKORG", "4033");
	    documentData.addAttribute("VTWEG", "00");
	    documentData.addAttribute("WAERK", "EUR");
	    documentData.addAttribute("KUNNR", "0016009734");
	    documentData.addAttribute("BZIRK", "403300");
	    //documentData.addAttribute("ZZPARTNER", "10000410");
	    //documentData.addAttribute("ZZSTND_GRPID", "0000020027");
		return documentData;
	}
	
	private String[] createItems(String documentId, int noOfItems, String priceDate) throws PricingCommandException {
	    arrItemInfo = new IItemInfo[noOfItems];
	    for (int i = 0; i < arrItemInfo.length; i++) {
	      ItemInfoData itemData = createItemContext(documentId, priceDate, i);
	      
	      
	      arrItemInfo[i] = itemData;
	    }
	    String[] resultItemArry = new CreateItems(sessionManager).executeCommand(arrItemInfo, Boolean.FALSE, documentId);
	    return resultItemArry;
	  }

	public ItemInfoData createItemContext(String documentId, String priceDate, int i) {
		ItemInfoData itemData = new ItemInfoData();
	      itemData.setId(itemIdArray[i]);
	      itemData.setSuppressBomExplosion(Boolean.TRUE);
	      itemData.setDocumentId(documentId);
	      itemData.setExternalId("000000000" + (i + 1));
	      itemData.setHighLevelItemID(IpcPricingHelper.INITIAL_GUID);
	      itemData.setProductId(PRODUCT_ID_1);
	      //itemData.setMultiplicity(0);
	      itemData.setPricingRelevant(Boolean.TRUE);
	      //itemData.setExchangeRateDate("0000-00-00");// KURSK_DAT
	      itemData.setExchangeRateType("M");// KURST
	      //itemData.setExchangeRate(BigDecimal.valueOf(0));// KURSK
	      itemData.setQuantity(BigDecimal.valueOf(9));// MGAME
	      itemData.setQuantityUnit("EA");// VRKME
	      itemData.addTimestamp("PRICE_DATE", priceDate);
	      itemData.addAttribute("PRODUCT", PRODUCT_ID_1);
	      //itemData.setNumerator(10);
	      //itemData.setDenominator(1);
	      //itemData.setExponent(0);
	    //  itemData.setGrossWeightValue(BigDecimal.valueOf(30));// BRGEW
	   //   itemData.setNetWeightValue(BigDecimal.valueOf(20));// NTGEW
	   //   itemData.setGrossWeightUnit("KG");// GEWEI
	   //   itemData.setNetWeightUnit("KG");
	  //    itemData.setVolume(BigDecimal.valueOf(20));// VOLUM
	  //    itemData.setVolumneUnit("PT");// VOLEH
	  //    itemData.setPointsValue(BigDecimal.valueOf(0));// ANZPU
//	      itemData.setDaysPerMonth((short) 0);
//	      itemData.setDaysPerYear((short) 0);
//	      itemData.setDateFromB("0000-00-00");
//	      itemData.setDateToB("0000-00-00");
//	      itemData.setDaysB((double) 0.0);
//	      itemData.setWeeksB((double) 0.0);
//	      itemData.setMonthsB((double) 0.0);
//	      itemData.setYearsB((double) 0.0);
//	      itemData.setDateFromS("0000-00-00");
//	      itemData.setDateToS("0000-00-00");
//	      itemData.setDaysS((double) 0.0);
//	      itemData.setWeeksS((double) 0.0);
//	      itemData.setMonthsS((double) 0.0);
//	      itemData.setYearsS((double) 0.0);
//	      itemData.setxHighLevelItemId(Boolean.TRUE);
//	      itemData.setxProductId(Boolean.TRUE);
//	      itemData.setxPricingRelevant(Boolean.TRUE);
//	      itemData.setxReturnFlag(Boolean.TRUE);
//	      itemData.setxStatistical(Boolean.TRUE);
//	      itemData.setxUnchangeable(Boolean.TRUE);
//	      itemData.setxQuantity(Boolean.TRUE);
//	      itemData.setxNumerator(Boolean.TRUE);
//	      itemData.setxWeight(Boolean.TRUE);
//	      itemData.setxVolumne(Boolean.TRUE);
//	      itemData.setxPoints(Boolean.TRUE);
//	      itemData.setxBase(Boolean.TRUE);
//	      itemData.setxScale(Boolean.TRUE);
//	      itemData.setxVariantCondition(Boolean.TRUE);
		return itemData;
	}
	
	@Test
	public void testGetPricingConditions() throws IpcCommandException, PricingCommandException, SPCPricingException, PricingException {
		long startTime = System.currentTimeMillis();
		testOnce("20180208");
		System.out.println(System.currentTimeMillis() - startTime);
		//testOnce("20180206");
		System.out.println(System.currentTimeMillis() - startTime);
		//testOnce("20180207");
		System.out.println(System.currentTimeMillis() - startTime);
		
		
//		Map accumulatedValue = pricingDocument.getAccumulatedValuesForConditionsWithPurpose();
//		spcItem.getPricingItem().getCon
//		System.out.println(ispcDocument);
	}

	private void testOnce(String priceDate) throws PricingCommandException, SPCPricingException, PricingException {
		String docId = IpcPricingHelper.byteArrayToHexString();
		createDocument(APPL, USAGE, docId);
		itemIdArray = createItems(docId, 1, priceDate);
//		IPricingDocumentInfo pricingDocumentInfo = new GetPricingDocumentInfo(sessionManager).executeCommand(docId, itemIdArray, true);
//		System.out.println(pricingDocumentInfo);
		//IPricingProcedureInfo pricingProcedureInfo = session.getPricingConditions(docId, itemIds, true);
		//System.out.println(pricingProcedureInfo);
		ISPCDocument ispcDocument = sessionManager.getSlcPricing().getSPCDocument(docId);
		com.sap.spe.pricing.transactiondata.IPricingCondition[] pricingConditions2 = ispcDocument.getSPCItems()[0].getPricingItem().getPricingConditions();
		for (com.sap.spe.pricing.transactiondata.IPricingCondition pricingCondition : pricingConditions2) {
			//System.out.println(pricingCondition.getConditionTypeName());
			IConditionRecord conditionRecord = pricingCondition.getConditionRecord();
			
			if (conditionRecord != null) {
				IScale scale = conditionRecord.getScale();
				if (scale != null) {
					IScaleLevel[] scaleLevels = scale.getScaleLevels();
					for (IScaleLevel scaleLevel : scaleLevels) {
						if (scaleLevel instanceof PricingScaleLevel) {
							IPricingScaleLevel scaleLevel2 = (IPricingScaleLevel)scaleLevel;
							IPricingScaleRate scaleRate = ((PricingScale) scale).getScaleRate(scaleLevel2);
							System.out.println(scaleLevel2.getAmount().toString() + scaleRate.getConditionRate());
						
						}
					}
				}
			}
		}
//		ISPCItem spcItem = ispcDocument.getSPCItem(itemIdArray[0]);
//		Map subTotals = ispcDocument.getSubtotals();
//		IPricingDocument pricingDocument = ispcDocument.getPricingDocument();
		com.sap.spe.pricing.transactiondata.IPricingCondition[] pricingConditions = ispcDocument.getPricingConditions();
		Object subTotal2 = ispcDocument.getSubtotals().get(Character.toString(PricingCustomizingConstants.ConditionSubtotal.SUBTOTAL_2));
		System.out.println("Subtotal2:" + ((ICurrencyValue)subTotal2).getValue().divide(new BigDecimal("9")));

		
		
		
		for (com.sap.spe.pricing.transactiondata.IPricingCondition pricingCondition : pricingConditions) {
			ExtendedPricingCondition pricingCondition2 = (ExtendedPricingCondition)pricingCondition;
			System.out.println(pricingCondition2.getConditionTypeName() + ", " + pricingCondition2.getConditionValueValue() 
			+ ", " + pricingCondition2.getConditionRate()
			+ ", " + pricingCondition2.getUnitPrice()
			+ ", " + pricingCondition2.getDiscount()
			);
		}
	}
	
	
}
