package util;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import com.sap.custdev.projects.fbs.slc.cfg.client.IProduct;
import com.sap.custdev.projects.fbs.slc.cfg.command.beans.Product;
import com.sap.spc.product.IProductBaseData;
import com.sap.spc.product.IProductEngine;
import com.sap.spc.product.IProductEngineFactory;
import com.sap.spc.product.exception.ProductInconsistentDBException;
import com.sap.spe.document.IDocument;

public class IpcPricingHelper {

	private static boolean originalGroupProcessing;
	public static final String INITIAL_GUID = "00000000000000000000000000000000";

	public static Date getDateFromAbapString(String sDate) {
		if (sDate != null) {
			int iYear = Integer.parseInt(sDate.substring(0, 4));
			int iMonth = Integer.parseInt(sDate.substring(5, 7));
			int iDay = Integer.parseInt(sDate.substring(8, 10));
			Calendar cDate = new GregorianCalendar(iYear, iMonth, iDay);
			return cDate.getTime();
		} else
			return null;
	}

	public static void setGroupProcessingBegin(IDocument spcDocument) {

		// performance optimization: switch off group processing locally
		originalGroupProcessing = spcDocument.getAlwaysPerformGroupConditionProcessing();
		spcDocument.setAlwaysPerformGroupConditionProcessing(false);
	}

	public static void setGroupProcessingEnd(IDocument spcDocument) {

		// performance optimization: switch on group processing again (if
		// originally switched on)
		if (originalGroupProcessing) {
			spcDocument.setAlwaysPerformGroupConditionProcessing(true);
			spcDocument.pricingGroupConditionProcessing();
		}
	}

	public static byte[] getRandomUUIDAsBytes() {
		UUID uuid = UUID.randomUUID();
		ByteBuffer byteBuffer = ByteBuffer.allocate(16);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return byteBuffer.array();
	}

	/**
	 * This method returns the Product Guid based on the Product Id.
	 * 
	 * @param productId
	 * @param logSys
	 * @param productType
	 * @param noLike
	 * @return
	 */
	public static String getProductGuid(String productId, String logSys, String productType, boolean noLike) {
		String productGuid = null;
		try {
			IProductEngine prodEng = IProductEngineFactory.getFactory().getProductEngine("CRM");
			IProductBaseData[] productBaseArray = null;
			productBaseArray = prodEng.getProductBaseDataByExternalID(productId, logSys, productType, noLike);
			productGuid = productBaseArray[0].getProductGUID();
		} catch (ProductInconsistentDBException e) {
			e.printStackTrace();
		}
		return productGuid;
	}

	public static String byteArrayToHexString() {
		byte[] ba = getRandomUUIDAsBytes();
		StringBuilder hex = new StringBuilder(ba.length * 2);
		for (Byte b : ba) {
			hex.append(String.format("%02X", b));
		}
		return hex.toString();
	}

	// helper method to check whether a string contains the 'initial' GUID
	public static boolean isInitialGuid(String str) {
		return str.equals(INITIAL_GUID);
	}

	public static IProduct[] getProductData(String productId, String logSys, String productType) {
		IProduct[] productData = new Product[1];
		productData[0] = new Product();
		productData[0].setProductId(productId);
		productData[0].setProductLogsys(logSys);
		productData[0].setProductType(productType);
		return productData;
	}
}
