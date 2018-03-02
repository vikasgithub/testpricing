package customizing;


import com.sap.custdev.projects.fbs.slc.logging.ILoggingHandle;
import com.sap.custdev.projects.fbs.slc.logging.LoggingServiceFactory;
import com.sap.custdev.projects.fbs.slc.logging.Severity;
import com.sap.spe.condmgnt.customizing.IAccess;
import com.sap.spe.pricing.Configuration;
import com.sap.spe.pricing.customizing.IPricingConditionType;
import com.sap.spe.pricing.customizing.IPricingStep;
import com.sap.spe.pricing.masterdata.IPricingConditionRecord;
import com.sap.spe.pricing.transactiondata.PricingEngineFactory;
import com.sap.spe.pricing.transactiondata.impl.PricingCondition;
import com.sap.spe.pricing.transactiondata.impl.PricingConditionBuilder;
import com.sap.spe.pricing.transactiondata.impl.PricingEngine;
import com.sap.spe.pricing.transactiondata.impl.PricingItem;
import com.sap.sxe.sys.SAPTimestamp;

public class CustomPricingEngine extends PricingEngine {
    private static final ILoggingHandle LOCATION = LoggingServiceFactory.getLoggingHandle(PricingConditionBuilder.class);

	public CustomPricingEngine(String usage) {
		super(usage);
	}

	// factory method for the creation of a pricing condition
	// in order to be overwritten in RebateEngine
	public PricingCondition createPricingCondition(int stepNo, int counter) {
		return new ExtendedPricingCondition(stepNo, counter);
	}

	// factory method for the creation of a pricing condition
	// which represents a subtotal line, so has no ConditionType
	// in order to be overwritten in RebateEngine
	public PricingCondition createPricingCondition(PricingItem pricingItem, IPricingStep step) {
		return new ExtendedPricingCondition(pricingItem, step);
	}

	// factory method for the creation of a pricing condition
	// with a ConditionType that has no access sequence
	// in order to be overwritten in RebateEngine
	public PricingCondition createPricingCondition(PricingItem pricingItem, IPricingStep step,
			IPricingConditionType conditionType) {

		// CRMR: check whether desired usage fits to actual usage
		String desiredUsage = conditionType.getUsage();
		String actualUsage = usage; // pricingCustomizingEngine.getUsage();
		if (desiredUsage.equals(actualUsage)) {

			// usage is fitting
			return new ExtendedPricingCondition(pricingItem, step, conditionType);
		} else {

			// get engine for desired usage
			PricingEngine prEng = this;
			try {
				prEng = (PricingEngine) PricingEngineFactory.getFactory().getPricingEngine(desiredUsage);
			} catch (Exception exc) {
				Configuration.CATEGORY.logThrowable(Severity.INFO, LOCATION, Configuration.MESSAGE_CLASS, 170,
						"Call of getPricingEngine() failed. Desired usage was: ", new Object[] { desiredUsage }, exc);
			}

			// delegate call to engine for desired usage
			return prEng.createPricingCondition(pricingItem, step, conditionType);
		}
	}

	// factory method for the creation of a pricing condition
	// with a pricing condition record
	// in order to be overwritten in RebateEngine
	public PricingCondition createPricingCondition(PricingItem pricingItem, IPricingStep step, IAccess access,
			IPricingConditionType ct, IPricingConditionRecord prConditionRecord, SAPTimestamp pricingTimestamp) {

		// CRMR: check whether desired usage fits to actual usage
		String desiredUsage = ct.getUsage();
		String actualUsage = usage; // pricingCustomizingEngine.getUsage();
		if (desiredUsage.equals(actualUsage)) {

			// usage is fitting
			return new ExtendedPricingCondition(pricingItem, step, access, ct, prConditionRecord, pricingTimestamp);
		} else {

			// get engine for desired usage
			PricingEngine prEng = this;
			try {
				prEng = (PricingEngine) PricingEngineFactory.getFactory().getPricingEngine(desiredUsage);
			} catch (Exception exc) {
				Configuration.CATEGORY.logThrowable(Severity.INFO, LOCATION, Configuration.MESSAGE_CLASS, 170,
						"Call of getPricingEngine() failed. Desired usage was: ", new Object[] { desiredUsage }, exc);
			}

			// delegate call to engine for desired usage
			return prEng.createPricingCondition(pricingItem, step, access, ct, prConditionRecord, pricingTimestamp);
		}
	}
}
