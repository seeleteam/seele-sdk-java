package com.seeletech.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {
    private static Map<String, List<String>> commandMap ;
    static{
        commandMap = new HashMap<String,List<String>>();
        commandMap.put("seele", Arrays.asList("getInfo","getBalance", "addTx", "getAccountNonce", "getBlockHeight", "getBlock", "call", "getLogs", "generatePayload", "estimateGas","key"));
        commandMap.put("txpool", Arrays.asList("getBlockTransactionCount","getTransactionByBlockIndex", "getTransactionByHash", "getReceiptByTxHash", "getDebtByHash"));
        commandMap.put("network", Arrays.asList("getPeersInfo", "getPeerCount", "getNetworkVersion", "getProtocolVersion", "getNetworkID", "isListening"));
        commandMap.put("miner", Arrays.asList("start", "stop", "status", "getCoinbase", "setThreads", "setCoinbase", "getEngineInfo"));
        commandMap.put("debug", Arrays.asList("printBlock", "getTxPoolContent", "getTxPoolTxCount", "getPendingTransactions", "getPendingDebts", "dumpHeap"));
    }
    public static String getNamespace(String command){
        for( Map.Entry<String, List<String>> entry : commandMap.entrySet()){
            if(entry.getValue().contains(command)){
                return entry.getKey();
            }
        }
        return "";
    }
}


