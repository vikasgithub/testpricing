package pricing;

import java.math.BigDecimal;

import com.sap.spe.pricing.transactiondata.impl.PricingItem;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

public class ConditionValueFormula extends ValueFormulaAdapter {
	public BigDecimal overwriteConditionValue(IPricingItemUserExit item, IPricingConditionUserExit condition) {
	
		//condition.
		//item.getProduct().
		//condition.get
		//((PricingItem)item).get	
		return null;
				
	}
}
