package zapprotocol.jzap.wrappers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MintableTokenTest {
    private static MintableToken mintableToken;

    @BeforeAll
    static void testDeployMintableToken() throws Exception {
        Web3j web3j = Web3j.build(new HttpService());
        Credentials creds = Credentials.create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80");
        ContractGasProvider gasPro = new DefaultGasProvider();
        mintableToken = MintableToken.deploy(web3j, creds, gasPro).send();
    }

    @Test
    void test() {
        
    }
}