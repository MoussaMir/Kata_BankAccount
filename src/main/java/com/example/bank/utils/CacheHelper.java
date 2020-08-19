package com.example.bank.utils;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import com.example.bank.model.TransactionList;

public class CacheHelper {
	private CacheManager cacheManager;
	private Cache<String, TransactionList> transactionHistoryCache;

	private static CacheHelper INSTANCE = new CacheHelper();

	private CacheHelper() {
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build();
		cacheManager.init();

		transactionHistoryCache = cacheManager.createCache("transactionHistory", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, TransactionList.class, ResourcePoolsBuilder.heap(10)));
	}

	public  Cache<String, TransactionList> getTransactionHistoryCacheFromCacheManager() {
		return cacheManager.getCache("transactionHistory", String.class, TransactionList.class);
	}

	public static CacheHelper getInstance() {
		return INSTANCE;
	}
}
