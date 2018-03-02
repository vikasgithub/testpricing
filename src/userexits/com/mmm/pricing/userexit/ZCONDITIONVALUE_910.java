package userexits.com.mmm.pricing.userexit;

import java.math.BigDecimal;
import java.util.SortedSet;

import com.sap.spe.base.logging.UserexitLogger;
import com.sap.spe.condmgnt.customizing.IProcedure;
import com.sap.spe.condmgnt.customizing.IStep;
import com.sap.spe.condmgnt.masterdata.IConditionRecord;
import com.sap.spe.conversion.ICurrencyConversionResult;
import com.sap.spe.conversion.ICurrencyValue;
import com.sap.spe.conversion.exc.ConversionMissingDataException;
import com.sap.spe.conversion.exc.CurrencyConversionException;
import com.sap.spe.pricing.customizing.IPricingStep;
import com.sap.spe.pricing.customizing.PricingCustomizingConstants;
import com.sap.spe.pricing.customizing.impl.PricingStep;
import com.sap.spe.pricing.masterdata.impl.PricingConditionRecord;
import com.sap.spe.pricing.transactiondata.impl.PricingCondition;
import com.sap.spe.pricing.transactiondata.impl.PricingItem;
import com.sap.spe.pricing.transactiondata.userexit.IPricingConditionUserExit;
import com.sap.spe.pricing.transactiondata.userexit.IPricingItemUserExit;
import com.sap.spe.pricing.transactiondata.userexit.ValueFormulaAdapter;

import customizing.ExtendedPricingCondition;

public class ZCONDITIONVALUE_910 extends ValueFormulaAdapter {

	private static UserexitLogger userexitlogger = new UserexitLogger(ZCONDITIONVALUE_910.class);

	private static int UNIT_PRICE_DECIMALS = 8;

	private static int NET_PRICE_DECIMALS = 4;

	private SortedSet getSourceConditions(IPricingItemUserExit item, PricingStep currentStep) {
		return ((PricingItem) item).getConditions(currentStep.getFromStep(), currentStep.getToStep());
	}

	@Override
	public BigDecimal overwriteConditionValue(IPricingItemUserExit item, IPricingConditionUserExit condition) {
		// System.out.println(ç.getConditionTypeName());
		System.out.println(condition.getConditionTypeName() + ", " + condition.getConditionValue());

		if (condition.getConditionTypeName() != null && condition.getConditionTypeName().equals("NETP")) {
			return new BigDecimal("0");
		}
		
		if (condition.getConditionTypeName() != null && condition.getConditionTypeName().equals("ZVCR")) {
			SortedSet<PricingCondition> conditions = getSourceConditions(item, (PricingStep) condition.getStep());

			BigDecimal conditionValue = new BigDecimal("0");
			for (PricingCondition condition2 : conditions) {
				conditionValue = conditionValue.add(condition2.getConditionValue().getValue());
			}

			return conditionValue;
		}
		
		if (condition.getConditionTypeName() != null && condition.getConditionTypeName().equals("ZMAP")) {
			SortedSet<PricingCondition> conditions = getSourceConditions(item, (PricingStep) condition.getStep());

			BigDecimal conditionValue = new BigDecimal("0");
			for (PricingCondition condition2 : conditions) {
				conditionValue = conditionValue.add(condition2.getConditionValue().getValue());
			}

			return conditionValue;
		}

		if (condition.getConditionTypeName() != null && condition.getConditionTypeName().equals("ZPST")) {
			SortedSet<PricingCondition> conditions = getSourceConditions(item, (PricingStep) condition.getStep());

			BigDecimal conditionValue = new BigDecimal("0");
			for (PricingCondition condition2 : conditions) {
				conditionValue = conditionValue.add(condition2.getConditionValue().getValue());
			}

			return conditionValue;
		}

		// if (true) {
		// return null;
		// }
		//

		if (!PricingCustomizingConstants.CalculationType.isPercentage(condition.getCalculationType())) {

			return applyUoMConversionToConditionValue(item, condition);
		}
		
		return null;
	}

	private ICurrencyValue applyUomConversionToConditionRate(IPricingItemUserExit item,
			IPricingConditionUserExit condition) {

		PricingConditionRecord conditionRecord = (PricingConditionRecord) condition.getConditionRecord();
		ICurrencyValue rat = conditionRecord.getConditionRate();
		BigDecimal unitRateDecimal = null;
		if (!item.getUserExitDocument().getDocumentCurrencyUnit().getUnitName().equals(rat.getUnitName())) {
			try {
				ICurrencyConversionResult unitPriceInDocumentCurrency = rat.convertToForeignCurrency(
						item.getExchangeRateDate(), item.getUserExitDocument().getDocumentCurrencyUnit());
				unitRateDecimal = unitPriceInDocumentCurrency.getValue().getValue();
			} catch (CurrencyConversionException cex) {
				userexitlogger.writeLogError("Currency Conversion Exception thrown: " + cex.getMessage());
			} catch (ConversionMissingDataException cmx) {
				userexitlogger.writeLogError("ConversionMissingDataException thrown: " + cmx.getMessage());
			}
		}

		unitRateDecimal = unitRateDecimal
				.divide(new BigDecimal(conditionRecord.getFractionForConversionToBaseUnit().getNumerator()));

		String conditionUnit = condition.getPricingUnit().getUnitName();
		String productUnit = item.getProductQuantity().getUnitName();

		if (conditionUnit != null && !conditionUnit.equals("") && (productUnit != null && !productUnit.equals(""))) {
			if (!conditionUnit.equals(productUnit)) {
				if (!item.getUserExitDocument().getDocumentCurrencyUnit().getUnitName().equals(rat.getUnitName())) {
					int num = condition.getFraction().getNumerator();
					BigDecimal numdec = new BigDecimal(num);
					// unitRateDecimal.multiply(prodqnt)).divide(numdec
				}
			}

			return condition.getConditionRate();
		} else {
			return condition.getConditionRate();
		}
	}

	private BigDecimal applyUoMConversionToConditionValue(IPricingItemUserExit item,
			IPricingConditionUserExit condition) {
		String productunit = null;

		ICurrencyConversionResult newcurrrat = null;
		BigDecimal newcurrencyrat = null;
		BigDecimal newval = null;

		BigDecimal prodqnt = item.getProductQuantity().getValue();

		String prunit = condition.getPricingUnit().getUnitName();

		// 1 CS = 16 EA num = 16. we need to divide by num.
		int num = condition.getFraction().getNumerator();
		BigDecimal numdec = new BigDecimal(num);

		productunit = item.getProductQuantity().getUnitName();

		if (prunit != null && !prunit.equals("") && (productunit != null && !productunit.equals(""))) {

			if (!prunit.equals(productunit)) {
				ICurrencyValue val = condition.getConditionValue();
				ICurrencyValue rat = condition.getConditionRate();
				try {
					if (!item.getUserExitDocument().getDocumentCurrencyUnit().getUnitName().equals(rat.getUnitName())) {
						newcurrrat = rat.convertToForeignCurrency(item.getExchangeRateDate(),
								item.getUserExitDocument().getDocumentCurrencyUnit());
						newcurrencyrat = newcurrrat.getValue().getValue();
						newval = (newcurrencyrat.multiply(prodqnt)).divide(numdec, val.getUnit().getNumberOfDecimals(),
								BigDecimal.ROUND_HALF_UP);
						return newval;
					}
				} catch (CurrencyConversionException cex) {
					userexitlogger.writeLogError("Currency Conversion Exception thrown: " + cex.getMessage());
				} catch (ConversionMissingDataException cmx) {
					userexitlogger.writeLogError("ConversionMissingDataException thrown: " + cmx.getMessage());
				}

				userexitlogger.writeLogDebug("old cond value: " + val.getValueAsString());

				newval = ((rat.getValue().multiply(prodqnt)).divide(numdec, val.getUnit().getNumberOfDecimals(),
						BigDecimal.ROUND_HALF_UP));

				userexitlogger.writeLogDebug("new cond value: " + newval);

				return newval;
			}

			return null;
		} else {
			userexitlogger.writeLogError("Empty or null Product Unit");
			return new BigDecimal("0");
		}
	}

	private boolean greaterThan(BigDecimal value, String comparisonValue) {
		return value != null && value.compareTo(new BigDecimal(comparisonValue)) == 1;
	}

}
