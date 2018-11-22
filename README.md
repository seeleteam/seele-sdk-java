seele-sdk-java

seele-sdk-java is a generic java API library for the Seele blockchain.


Example

sign（签名）

Method：static String sign(SignTransactionDTO transactionDTO)

Generate transaction and sign, the rawTx transactionDTO be in the example format, otherwise an error will occur.

example：

        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xd738b0c1198e55050f754bdf0f824ee4febd962a6b751faab86c081ad5033b0d");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");
        rawTx.setFrom("0xb265a2e04087a9a83492ffe191316f46b4730751");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
        String jsonStr = TransactionServiceImpl.sign(signTransactionDTO);


success return：
{"result":{"data":{"AccountNonce":0,"Amount":0,"From":"0xb265a2e04087a9a83492ffe191316f46b4730751","GasLimit":3000000,"GasPrice":1,"Payload":"","Timestamp":0,"To":"0x0000000000000000000000000000000000000000"},"signature":{"Sig":"Hw6HmcWh+MyhEZvn0rrBx80681sDlq1x9LjxAHD8T/1/LXCQb5Gmc7qaM+AmkT5VgIQY82gIKLwZ2VNQEpZTMgA="},"hash":"0xe6abd9480976d73eece9780caecaf8e9069cc75a8a80fa7fde822c2e6abfe5a5"}}

fail return:

{
	errMsg: “generateTx failed:”
}


sendTx（发送交易/转账）

Method:statci String sendTx(SignTransactionDTO signTransactionDTO,String uri)

send transaction , the SignTransactionDTO must be in the example format, otherwise an error will occur.

example：

        SignTransactionDTO signTransactionDTO = new SignTransactionDTO();
        signTransactionDTO.setPrivateKey("0xd738b0c1198e55050f754bdf0f824ee4febd962a6b751faab86c081ad5033b0d");
        RawTx rawTx = new RawTx();
        rawTx.setTo("0x0a57a2714e193b7ac50475ce625f2dcfb483d741");
        rawTx.setFrom("0xb265a2e04087a9a83492ffe191316f46b4730751");
        rawTx.setAmount(0);
        rawTx.setAccountNonce(0);
        rawTx.setTimestamp(0);
        rawTx.setGasPrice(1);
        rawTx.setGasLimit(3000000);
        signTransactionDTO.setRawTx(rawTx);
       String jsonStr = TransactionServiceImpl.sendTx(signTransactionDTO, " http://127.0.0.1:8037");

success return：
{
	"result":{
			"result":true,
			"id":1542684211408,
			"jsonrpc":"2.0"
	}
}


fail return：
{
	errMsg: “error:addTransactionDTO is null”
}


gettxbyhash（根据hash获取交易信息）

Method:static String gettxbyhash(String hash,String uri)

get transaction by hash, the hash must be in the example format(64 length), otherwise an error will occur.

example：


String jsonStr = TransactionServiceImpl.gettxbyhash("0x03f097fef1bbda18257b020b80a3a79834bcd324635fcc4f932173c1767c2889", "http://localhost:8089");



success return：
{
	"result":
	{
		"result":
		{
			"blockHash":"0x000000a7ec6a660fad6a00b65f0e416bfd02235dfbaf1d812d47afb50dcc7e8c",
			"blockHeight":52,
			"transaction":
			{
				"gasLimit":3000000,
				"amount":0,
				"payload":"",
				"from":"0xb265a2e04087a9a83492ffe191316f46b4730751",
				"to":"0x0a57a2714e193b7ac50475ce625f2dcfb483d741",
				"accountNonce":0,
				"hash":"0xa489ac38226716ce9cb7f72a41ac09e77599599df0c2f80152870461b55a1b98",
				"gasPrice":1,
				"timestamp":0
			},
			"txIndex":1,
			"status":"block"
		},
		"id":1542873171265,
		"jsonrpc":"2.0"
	}
}

fail return ：
{
	errMsg: “leveldb: not found”
}

key（生成私钥）

Method:static String key()

generate the private key 

example：

String jsonStr = TransactionServiceImpl.key("http://127.0.0.1:8037");


success return ：
{
	"result":
			{"public key":"0x83438e124c835f5fb60251664882cf35388eeb5b",
				"private key":"0x338ed964bf839a87923a990d08d3435b4f27cc89cdc025ca019a7e60812fbafe"
			}
}


getAddress

Method: static byte[] getAddress(byte[] publicKey)

get public address by publicKey

example：
 String pubString = "040947751e3022ecf3016be03ec77ab0ce3c2662b4843898cb068d74f698ccc8ad75aa17564ae80a20bb044ee7a6d903e8e8df624b089c95d66a0570f051e5a05b";
 byte[] pubKey = Hex.decode(pubString);

 AddressServiceImpl.getAddress(pubKey);




