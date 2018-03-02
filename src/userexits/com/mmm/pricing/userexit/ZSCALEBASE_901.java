package userexits.com.mmm.pricing.userexit;

import java.math.BigDecimal;

import com.sap.custdev.projects.fbs.slc.util.StringUtil;
import com.sap.spe.pricing.transactiondata.userexit.IGroupConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ScaleBaseFormulaAdapter;

public class ZSCALEBASE_901 extends ScaleBaseFormulaAdapter{

	private static final String ZZVEDCONTR = "zzvedcontr";
	private static final String ZZEXCDEAL = "zzexcdeal";

	@Override
	public BigDecimal overwriteScaleBase(IPricingItemUserExit pricingItem, IPricingConditionUserExit pricingCondition,
			IGroupConditionUserExit groupCondition) { 
		
		String dealExclusionFlag = pricingItem.getAttributeValue(ZZEXCDEAL);
		String zzvedcontr = pricingItem.getAttributeValue(ZZVEDCONTR);
		
		if ("X".equals(dealExclusionFlag) && StringUtil.isEmpty(zzvedcontr)) {
			pricingCondition.setScaleBaseValue(new BigDecimal("0"));
		}
		
		return super.overwriteScaleBase(pricingItem, pricingCondition, groupCondition);
	}
}
