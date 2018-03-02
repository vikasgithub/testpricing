package customizing;

import java.math.BigDecimal;

import com.sap.spe.condmgnt.customizing.IAccess;
import com.sap.spe.pricing.customizing.IPricingConditionType;
import com.sap.spe.pricing.customizing.IPricingStep;
import com.sap.spe.pricing.masterdata.IPricingConditionRecord;
import com.sap.spe.pricing.transactiondata.IExternalDataSource;
import com.sap.spe.pricing.transactiondata.impl.PricingCondition;
import com.sap.spe.pricing.transactiondata.impl.PricingItem;
import com.sap.sxe.sys.SAPTimestamp;

public class ExtendedPricingCondition extends PricingCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal unitPrice;
	
	private BigDecimal discount;

	public ExtendedPricingCondition(int stepNumber, int counter) {
		super(stepNumber, counter);
	}

	public ExtendedPricingCondition(PricingItem pricingItem, IPricingStep step, IAccess access,
			IPricingConditionType ct, IPricingConditionRecord prConditionRecord, IExternalDataSource externalDataSource,
			SAPTimestamp pricingTimestamp) {
		super(pricingItem, step, access, ct, prConditionRecord, externalDataSource, pricingTimestamp);
	}

	public ExtendedPricingCondition(PricingItem pricingItem, IPricingStep step, IAccess access,
			IPricingConditionType ct, IPricingConditionRecord prConditionRecord, SAPTimestamp pricingTimestamp) {
		super(pricingItem, step, access, ct, prConditionRecord, pricingTimestamp);
	}

	public ExtendedPricingCondition(PricingItem pricingItem, IPricingStep step, IPricingConditionType conditionType,
			IExternalDataSource externalDataSource) {
		super(pricingItem, step, conditionType, externalDataSource);
	}

	public ExtendedPricingCondition(PricingItem pricingItem, IPricingStep step, IPricingConditionType conditionType) {
		super(pricingItem, step, conditionType);
	}

	public ExtendedPricingCondition(PricingItem pricingItem, IPricingStep step) {
		super(pricingItem, step);
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
}
