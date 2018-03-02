package util;

import java.io.File;
import java.util.Iterator;
import java.util.Properties;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sap.custdev.projects.fbs.slc.cml.ext.cachereader.CacheReader;
import com.sap.custdev.projects.fbs.slc.cml.ext.cacheregion.CacheException;
import com.sap.custdev.projects.fbs.slc.cml.ext.cacheregion.CacheRegionExtended;
import com.sap.custdev.projects.fbs.slc.cml.ext.cacheregion.CacheRegionExtendedImpl;
import com.sap.custdev.projects.fbs.slc.commons.delegates.CacheRegionFactoryDelegateImpl;
import com.sap.custdev.projects.fbs.slc.commons.delegates.CacheRegionFactoryInterface;
import com.sap.custdev.projects.fbs.slc.logging.ILoggingHandle;
import com.sap.custdev.projects.fbs.slc.logging.LoggingServiceFactory;
import com.sap.custdev.projects.fbs.slc.logging.ServerUtil;
import com.sap.custdev.projects.fbs.slc.util.ICacheConstants;
import com.sap.custdev.projects.fbs.slc.util.IIPCConstants;
import com.sap.custdev.projects.fbs.slc.util.XMLTagAttribute;
import com.sap.custdev.projects.fbs.slc.util.XMLUtil;
import com.sap.tc.logging.Severity;
import com.sap.util.cache.CacheFacade;
import com.sap.util.cache.CacheRegion;
import com.sap.util.cache.RegionConfigurationInfo;

public class CacheUtil {

	private static final ILoggingHandle location = LoggingServiceFactory.getLoggingHandle(CacheUtil.class);
	
	/**
	 * @see {@link CacheRegionFactoryInterface#getCacheRegion(String)}
	 * 
	 * @param regionName
	 * @return
	 */
	public static CacheRegion getCacheRegion(String regionName) {
		CacheRegionFactoryInterface regionFactory = CacheRegionFactoryDelegateImpl.getInstance();
		return regionFactory.getCacheRegion(regionName);
	}

	public static CacheFacade getCacheFacade(String regionName) {
		CacheRegion region = getCacheRegion(regionName);
		return region.getCacheFacade();
	}

	public static void initCacheReader(CacheRegion region, String appName, String logicalName) throws CacheException {

		if (region == null || appName == null || logicalName == null)
			throw new IllegalArgumentException("Parameters may not be null.");

		CacheRegionExtended regExt = checkInstOfCacheReaderExtended(region);

		// do not proceed with fetching the cache reader name as the subsequent
		// call of initCacheReader will not do anything if the reader is already
		// initialized.
		if (regExt.isReaderInitialized() || !regExt.isReaderAvailable())
			return;

		// TODO: Instead of fetching it manually, it might be a better solution
		// to extend the existing JEE region configuration xml with the cache
		// region name. The cache region definition itself could also happen at
		// the same place.
		// Update: It is realized now:

		// Old implementation:
		// // String cacheReaderName =
		// fetchCacheReaderName(region.getRegionConfigurationInfo().getName(),
		// appName,
		// // logicalName);
		//
		// if (cacheReaderName != null)
		// regExt.initCacheReader(cacheReaderName, appName, logicalName);
		// else {
		// // Set reader availability to false in order to avoid later attempts
		// to initialize it.
		// regExt.setReaderAvailability(false);
		// location.infoT("For the given cache region '" + region
		// + "' is no cache reader maintained. The reader is not initialized.");
		// }
		//
		// New implementation:
		if (regExt.getCacheReaderName() == null) {
			// Set reader availability to false in order to avoid later attemps
			// to initialize it.
			regExt.setReaderAvailability(false);
			location.debugT("For the given cache region '" + region
					+ "' is no cache reader maintained. The reader is not initialized.");
			return;
		}
		if(ServerUtil.isHybris()){
			CacheRegionExtendedImpl regionExtendedHybris = (CacheRegionExtendedImpl) regExt;
			regionExtendedHybris.initCacheReaderHybris(regionExtendedHybris.getCacheReaderName());
		}else{
			regExt.initCacheReader(appName, logicalName);
		}
			
	}

	/**
	 * @see {@link CacheRegionExtended#getCacheReader()}
	 * 
	 * @param region
	 * @return
	 * @throws CacheException
	 */
	public static CacheReader getCacheReader(CacheRegion region) throws CacheException {

		if (region == null)
			throw new IllegalArgumentException("Parameter may not be null.");

		return checkInstOfCacheReaderExtended(region).getCacheReader();
	}

	/**
	 * Checks if the passed region is an instance of CacheRegionExtended class.
	 * It either throws an exception or returns the casted instance.
	 * 
	 * @param region
	 * @return
	 * @throws CacheException
	 */
	private static CacheRegionExtended checkInstOfCacheReaderExtended(CacheRegion region) throws CacheException {
		if (!(region instanceof CacheRegionExtended)) {
			CacheException e = new CacheException(
					"Passed region object must be an instance of CacheRegionExtended class.");
			location.throwing(e);
			throw e;
		}

		return (CacheRegionExtended) region;
	}

	/**
	 * Creates all the cache regions defined in CacheRegions.xml
	 * @throws com.sap.util.cache.exception.CacheException 
	 */
	public static void loadCacheRegions() {

		
		boolean isHybris = ServerUtil.isHybris();
		//In hybris cache regions are injected via spring
		if(isHybris){
			
			Document xmlDoc = loadXML();
			
			NodeList regions = XMLUtil.getElementsByTagName(ICacheConstants.CACHE_TAG_REGIONS,xmlDoc);
			if(regions != null){
				NodeList regionConf = XMLUtil.getChildren(regions.item(0));
				Node region = null;
				XMLTagAttribute tagAttr = null;
				String regionName = null;
				for(int i=0,size = regionConf.getLength();i<size;i++)
				{
					region = regionConf.item(i);
					if(region instanceof Element)
						//Node region = getRegionNode(xmlDoc,regionName);
					{
						tagAttr = new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_NAME);
						regionName = XMLUtil.getAttributeValue(region,tagAttr);
						createCacheRegion(regionName,region);
					}
				}
			}else{
				location.debugT("Failed to load regions from XML file");
			}
		}
	}
	
	/**
	 * Creates the Cache Region for the given RegionName
	 * @param regionName the name of the region read from the XML
	 * @param region the node of the region
	 * @return boolean
	 * @throws com.sap.util.cache.exception.CacheException 
	 */
	public static boolean createCacheRegion(String regionName,Node region) {
		CacheRegionFactoryInterface factory = getCacheRegionFactory();
		try {
			factory.initDefaultPluggables();
			boolean alreadyPresent = false;
			Iterator regions = factory.iterateRegions();
			while (null != regions && regions.hasNext()) {
				String cur = (String) regions.next();
				if (cur.equals(regionName)) {
					alreadyPresent = true;
					//refresh the cache content
					try {
						if (null != factory.getCacheRegion(cur).getCacheFacade())
							// remove everything in case the region is present 
							// '**' is the wildcard character which will remove everything from cache
							factory
								.getCacheRegion(cur)
								.getCacheFacade()
								.remove(
								"**");
					} catch (Exception ce) {
						location.traceThrowableT(
							Severity.ERROR,
							"Error while removing cache objects from region ["
								+ regionName
								+ "]",
							ce);
						alreadyPresent = false;
					}

					break;
				}

			}
			if (!alreadyPresent) {
				
				defineRegion(regionName,factory,region);
			}
			
			// set additional cache reader information if available
			String cacheReaderName = getCacheReaderName(region);
			if(cacheReaderName == null) {
				location.debugT("No cache reader found for region " + regionName);
			} else {
				CacheRegionExtended cacheRegionExt = (CacheRegionExtended) com.sap.custdev.projects.fbs.slc.cml.util.CacheUtil
						.getCacheRegion(regionName); // can safely be down-cased
				if(cacheRegionExt == null) {
					location.errorT("Cannot set the cache region name. There is no cache region for '" + regionName
							+ "' available.");
				} else {
					try {
						cacheRegionExt.setCacheReaderName(cacheReaderName);
					} catch (com.sap.custdev.projects.fbs.slc.cml.ext.cacheregion.CacheException e) {
						location.catching(e);
						location.errorT("Cannot set the cache region name.");
					}
				}
			}
		} catch (com.sap.util.cache.exception.CacheException ce) {
			// log or react
			location.traceThrowableT(
				Severity.ERROR,
				"Error while putting index to cache",
				ce);
			return false;
		}

		return true;
	}

	
	private static void defineRegion(String regionName,CacheRegionFactoryInterface factory,Node region) throws com.sap.util.cache.exception.CacheException {
				
		Properties confProps = new Properties(); 
		setCacheRegionConf(region,confProps);
		String evictionPolicy = getEvictionPolicy(region);
		String storagePolicy = getStoragePolicy(region);
				
		factory.defineRegion(
			regionName,
			storagePolicy,
			evictionPolicy,
			confProps);	
	}


	/**
	 * @return the xmlDocument
	 */
	private static Document loadXML() {
		String target = System.getProperty("user.home") + IIPCConstants.FORWARD_SLASH_CHAR;
		String confDir = "META-INF/conf/CacheRegions.xml";
		String fileName = target + confDir;
		File f = new File(fileName);
		Document xmldoc = XMLUtil.loadXML(f);	
		return xmldoc;	
	}

	
	/**
	 * Sets all the properties of cache region
	 * @param region
	 * @param confProps
	 */
	private static void setCacheRegionConf(Node region, Properties confProps) {
		NodeList regionNodes = XMLUtil.getChildren(region);
	
		setThresholdAttributes(regionNodes,confProps);
		
		setScopeAttributes(regionNodes,confProps);
		
		setFlagAttributes(regionNodes,confProps);
		
	}

	/**
	 * @param regionNodes
	 * @param confProps
	 */
	private static void setFlagAttributes(NodeList regionNodes, Properties confProps) {

		// Reading the flag Tag
		Node flag = XMLUtil.findNode(regionNodes,ICacheConstants.CACHE_TAG_FLAGS);
		
		// Reading the synchronous attribute of the flag Tag
		String synchronousFlag = XMLUtil.getAttributeValue(flag,
										new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_SYNCRONOUS)); 
		confProps.setProperty(
			RegionConfigurationInfo.PROP_SYNCHRONOUS,
			synchronousFlag);

		// Reading the direct-invalidation attribute of the flag Tag
		String directInvalidationFlag = XMLUtil.getAttributeValue(flag,
										new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_DIRECT_INVALIDATION)); 
		confProps.setProperty(
			RegionConfigurationInfo.PROP_DIRECT_INVALIDATION_MODE,
			directInvalidationFlag);

		// Reading the loggin-mode attribute of the flag Tag
		String loggingmodeflag = XMLUtil.getAttributeValue(flag,
										new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_LOGGING_NOE)); 
		confProps.setProperty(
			RegionConfigurationInfo.PROP_LOGGING_MODE,
			loggingmodeflag);

	}

	/**
	 * @param regionNodes
	 * @param confProps
	 */
	private static void setScopeAttributes(NodeList regionNodes, Properties confProps) {
		// Reading the scope tag
		Node scope = XMLUtil.findNode(regionNodes,ICacheConstants.CACHE_TAG_SCOPES); 
		// Local scope (the only possible with "HashMapStorage") 
		String regionscope = XMLUtil.getAttributeValue(scope,
									new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_REGION));
		confProps.setProperty(
			RegionConfigurationInfo.PROP_REGION_SCOPE,
			regionscope);

		// Local invalidation 
		String invalidationscope = XMLUtil.getAttributeValue(scope,new XMLTagAttribute(
					ICacheConstants.CACHE_ATTRIBUTE_INVALIDATION));
		confProps.setProperty(
			"PROP_INVALIDATION_SCOPE",
			invalidationscope);
		
	}

	/**
	 * @param regionNodes
	 * @param confProps
	 */
	private static void setThresholdAttributes(NodeList regionNodes,
			Properties confProps) {
		// Getting the threshold Value for count tag start attribute
		Node threshold = XMLUtil.findNode(regionNodes,ICacheConstants.CACHE_TAG_THRESHOLDS);
		
		NodeList thresholdNodes = XMLUtil.getChildren(threshold);
		// Reading the count Tag		
		Node count = XMLUtil.findNode(thresholdNodes,ICacheConstants.CACHE_TAG_COUNT);
		String countStart = XMLUtil.getAttributeValue(count,
								new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_START)); 
		confProps.setProperty(
			RegionConfigurationInfo.PROP_COUNT_START_OF_EVICTION_THRESHOLD,
			countStart);
			
		// Getting the threshold Value for count tag Upper attribute	
		String countUpper = XMLUtil.getAttributeValue(count,
								new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_UPPER));	
		confProps.setProperty(
			RegionConfigurationInfo.PROP_COUNT_UPPER_LIMIT_THRESHOLD,
			countUpper);
		
		// Getting the threshold Value for count tag critical attribute
		String countCritical = XMLUtil.getAttributeValue(count,
									new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_CRITICAL));	
		confProps.setProperty(
			RegionConfigurationInfo.PROP_COUNT_CRITICAL_LIMIT_THRESHOLD,
			countCritical);

		// Reading the size Tag
		Node size = XMLUtil.findNode(thresholdNodes,ICacheConstants.CACHE_TAG_SIZE);
		// not used but has to be configured due to fast changes in SAP JVM		
		// Getting the threshold Value for size tag start attribute
		String sizeStart = XMLUtil.getAttributeValue(size,
								new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_START)); 
		confProps.setProperty(
			RegionConfigurationInfo.PROP_SIZE_START_OF_EVICTION_THRESHOLD,
			sizeStart);

		// Not used due to fast changes in SAP JVM but has to be configured 
		// Getting the threshold Value for size tag upper attribute
		String sizeUpper = XMLUtil.getAttributeValue(size,
								new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_UPPER));
		confProps.setProperty(
			RegionConfigurationInfo.PROP_SIZE_UPPER_LIMIT_THRESHOLD, 
			sizeUpper); 

		// Not used due to fast changes in SAP JVM but has to be configured
		// Getting the threshold Value for size tag critical attribute		
		String sizeCritical = XMLUtil.getAttributeValue(size,
								new XMLTagAttribute(ICacheConstants.CACHE_ATTRIBUTE_CRITICAL)); 
		confProps.setProperty(
			RegionConfigurationInfo.PROP_SIZE_CRITICAL_LIMIT_THRESHOLD, 
			sizeCritical);

	}
	
	/**
	 * Retrieves the storage Policy for the given region
	 * @param region
	 * @return the storage Policy
	 */
	private static String getStoragePolicy(Node region) {
		
		NodeList regionNodes = XMLUtil.getChildren(region);
		
		Node plugins = XMLUtil.findNode(regionNodes,ICacheConstants.CACHE_TAG_PLUGINS);
		
		NodeList pluginNodes = XMLUtil.getChildren(plugins);
		
		Node evictionConfPlugins = XMLUtil.findNode(pluginNodes,ICacheConstants.CACHE_TAG_STORAGE_CONFIGURATION);
		
		return XMLUtil.getAttributeValue(evictionConfPlugins,new XMLTagAttribute(
				ICacheConstants.CACHE_ATTRIBUTE_TYPE));
	}

	/**
	 * Retrieves the eviction Policy for the given region
	 * @param regionName
	 * @return the eviction policy
	 */
	private static String getEvictionPolicy(Node region) {
		// Now, having a configuration we can define a region. As eviction 
		// policy the only one currently present is used - SimpleLRU which 
		// implements a strict LRU algorithm
		
		NodeList regionNodes = XMLUtil.getChildren(region);
		
		Node plugins = XMLUtil.findNode(regionNodes,ICacheConstants.CACHE_TAG_PLUGINS);
		
		NodeList pluginNodes = XMLUtil.getChildren(plugins);
		
		Node evictionConfPlugins = XMLUtil.findNode(pluginNodes,ICacheConstants.CACHE_TAG_EVICTION_CONFIGURATION);
		
		return XMLUtil.getAttributeValue(evictionConfPlugins,new XMLTagAttribute(
				ICacheConstants.CACHE_ATTRIBUTE_TYPE));
	}

	/**
	 * Retrieves the cache reader name of a cache region. Returns null if no
	 * reader name is defined.
	 * 
	 * Cache readers are not supported in the standard CML implementation. The
	 * support has been added along with the FBS implementation (see DC
	 * cdev/fbs_slc_cmn).
	 * 
	 * @param region
	 * @return
	 */
	private static String getCacheReaderName(Node region) {
		NodeList regionNodes = XMLUtil.getChildren(region);
		Node cacheReader = XMLUtil.findNode(regionNodes, ICacheConstants.CACHE_TAG_CACHE_READER);		
		return (cacheReader == null) ? null : cacheReader.getTextContent();
	}
	
	public static CacheRegionFactoryInterface getCacheRegionFactory() {
		return CacheRegionFactoryDelegateImpl.getInstance();
	}
}
