package userexits.com.mmm.pricing.userexit;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import com.sap.spe.conversion.ICurrencyValue;
import com.sap.spe.conversion.IPhysicalUnit;
import com.sap.spe.conversion.impl.CurrencyUnit;
import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.masterdata.impl.PricingConditionRecord;
import com.sap.spe.pricing.transactiondata.IPricingCondition;
import com.sap.spe.pricing.transactiondata.PricingTransactiondataConstants;
import com.sap.spe.pricing.transactiondata.impl.PricingItem;
import com.sap.spe.pricing.transactiondata.userexit.BaseFormulaAdapter;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.sxe.sys.SAPString;

public class ZCONDBASEVALUE_902_Temp extends BaseFormulaAdapter{
	
	private static final List<String> CONDITIONS = Arrays.asList("ZMAN", "ZVPD", "ZVHP", "ZVPP", "ZVGA", "ZVPB", "ZVMC", "ZR05", "ZR01");
	
	public BigDecimal overwriteConditionBase(IPricingItemUserExit priceItem,
			IPricingConditionUserExit priceCond) {
		
		String lc_VarCond = "STARTING_PRICE_NEG";
		BigDecimal newCondRate = PricingTransactiondataConstants.ZERO;
		BigDecimal condQTY;
		
		PricingItem pricingItem = (PricingItem) priceItem;
		
		IPricingCondition[] conditionsForCumulation = pricingItem.getPricingConditions();
		
		PricingConditionRecord sourcePricingConditionRecord = null;
		for (IPricingCondition pricingCondition : conditionsForCumulation) {
			String conditionTypeName = pricingCondition.getConditionTypeName();
			if (conditionTypeName != null && CONDITIONS.contains(conditionTypeName) && pricingCondition.getInactive() == PricingCustomizingConstants.InactiveFlag.NOT_INACTIVE) {
				sourcePricingConditionRecord = (PricingConditionRecord)pricingCondition.getConditionRecord();
				priceCond.setPricingUnit(sourcePricingConditionRecord.getPricingUnit().getValue(), (IPhysicalUnit)sourcePricingConditionRecord.getPricingUnit().getUnit());
				break;
			}
		}
		
//		lv_kbetr = l_wa_komv-kbetr.
//				***Start of changes by Kiran for HPQC 22499 on 08/09/2013
//				                    lv_kpein = l_wa_komv-kpein.
//				                    lv_waers = l_wa_komv-waers.
//				                   lv_kmein = l_wa_komv-kmein 
					
//		LOG.debug("===Condition Base Formula 902 Begin===");
//		LOG.debug("   902-SubtotalF = " + xworkf.toPlainString());
		
		ICurrencyValue conditionRate = sourcePricingConditionRecord.getConditionRate();
		if (conditionRate != null && conditionRate.getValue().compareTo(PricingTransactiondataConstants.ZERO) == 1) {				
			String varCondKey	= priceCond.getVariantConditionKey();
			if (varCondKey.compareTo(lc_VarCond) == 0) {
				priceCond.setConditionRate(conditionRate.getValue().negate(), (CurrencyUnit)conditionRate.getUnit());	
			} else 
			{
				priceCond.setConditionRate(conditionRate.getValue(), (CurrencyUnit)conditionRate.getUnit());	
			}			
		}
		
		return null;
	}
	
}
