package zapprotocol.jzap.wrappers;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ZapCoordinatorTest {
    private static ZapCoordinator coordinator;
    private static ZapCoordinator coord1;
    private static ZapCoordinator coord2;
    private static Database database;
    private static Bondage bondage;
    private static TransactionReceipt txReceipt;
    private static Web3j web3j;
    private static Credentials creds;
    private static ContractGasProvider gasPro;

    @BeforeAll
    static void testDeployZapCoordinator() throws Exception {
        web3j = Web3j.build(new HttpService());
        creds = Credentials.create("0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80");
        gasPro = new DefaultGasProvider();
        database = Database.deploy(web3j, creds, gasPro).send();
        coord1 = ZapCoordinator.deploy(web3j, creds, gasPro).send();
        coord2 = ZapCoordinator.deploy(web3j, creds, gasPro).send();
        bondage = Bondage.deploy(web3j, creds, gasPro, coord2.getContractAddress()).send();
        coordinator = new ZapCoordinator("0xe7f1725e7734ce288f8367e1bb143e90bb3f0512", web3j, creds, gasPro);
    }

    @Test
    void testZapCoordinatorDB() throws Exception {
        assertNotNull(coordinator.db().send());
    }

    @Test
    void testZapCoordinatorOwner() throws Exception {
        assertNotNull(coordinator.owner().send());
    }

    @Test
    void testZapCoordinatorTransferOwnership() throws Exception {
        txReceipt = coord1.transferOwnership(coord2.getContractAddress()).send();
        assertNotNull(txReceipt.getLogs());
    }

    @Test 
    void testZapCoordinatorUpdateAllDependencies() throws Exception {
        assertNotNull(coordinator.updateAllDependencies());

        // Function function = new Function(
        //     "updateAllDependencies",
        //     Arrays.<Type>asList(), 
        //     Collections.<TypeReference<?>>emptyList());

        // String encodedFunction = FunctionEncoder.encode(function);
        
        // EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
        //     creds.getAddress(), 
        //     DefaultBlockParameterName.LATEST).send();

        // BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        
        // Transaction transaction = Transaction.createFunctionCallTransaction(
        //                 creds.getAddress(), 
        //                 nonce, gasPro.getGasPrice(), 
        //                 gasPro.getGasLimit(), 
        //                 "0xe7f1725e7734ce288f8367e1bb143e90bb3f0512", 
        //                 encodedFunction);
        
        // org.web3j.protocol.core.methods.response.EthSendTransaction transactionResponse = web3j.ethSendTransaction(
        //     transaction).sendAsync().get();
        
        // String transactionHash = transactionResponse.getTransactionHash();
        // System.out.println(transactionHash);
    }
    
    @Test 
    void testZapCoordinatorUpdateContract() throws Exception {
        txReceipt = coordinator.updateContract("NewZapCoordinator", coord2.getContractAddress()).send();
        assertNotNull(txReceipt.getLogs());
    }
    
    @Test
    void testZapCoordinatorAddImmutableContract() throws Exception {
        assertNotNull(coordinator.addImmutableContract("NewDatabase", database.getContractAddress()));

        // Function function = new Function(
        //     "addImmutableContract",
        //     Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String("NewDatabase"), 
        //     new org.web3j.abi.datatypes.Address(database.getContractAddress())), 
        //     Collections.<TypeReference<?>>emptyList());

        // String encodedFunction = FunctionEncoder.encode(function);
        
        // EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
        //     creds.getAddress(), 
        //     DefaultBlockParameterName.LATEST).send();

        // BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        
        // Transaction transaction = Transaction.createFunctionCallTransaction(
        //                 creds.getAddress(), 
        //                 nonce, gasPro.getGasPrice(), 
        //                 gasPro.getGasLimit(), 
        //                 "0xe7f1725e7734ce288f8367e1bb143e90bb3f0512", 
        //                 encodedFunction);
        
        // org.web3j.protocol.core.methods.response.EthSendTransaction transactionResponse = web3j.ethSendTransaction(
        //     transaction).sendAsync().get();
        
        // String transactionHash = transactionResponse.getTransactionHash();
    }

    @Test
    void testZapCoordinatorGetContractName() throws Exception {
        assertNotNull(coordinator.getContractName(BigInteger.valueOf(0)).send());
    }

    @Test
    void testZapCoordinatorGetContract() throws Exception {
        assertNotNull(coordinator.getContract("ZapCoordinator"));
        System.out.println("GetContract(): " + coordinator.getContract("ZapCoordinator"));
    }

    @Test
    void testZapCoordinatorLoadedContracts() throws Exception {
        assertNotNull("LoadedContracts(): " + coordinator.loadedContracts(BigInteger.valueOf(0)).send());
    }

    @Test
    void testZapCoordinatorGetOwnershipTransferredEvents() {
        List eventResponse = coordinator.getOwnershipTransferredEvents(txReceipt);
        assertNotNull(eventResponse);
        // System.out.println("getOwnershipTransferredEvents(): " + eventResponse.size());
    }

    @Test
    void testZapCoordinatorOwnershipTransferredEventsFlowable() {
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
        DefaultBlockParameterName.LATEST, coord2.getContractAddress());
        Flowable eventResponse = coordinator.ownershipTransferredEventFlowable(filter);
        assertNotNull(eventResponse);
        // System.out.println("OwnershipTranferredEventsFlowable(): " + eventResponse.count());

        assertNotNull(coordinator.ownershipTransferredEventFlowable(DefaultBlockParameterName.EARLIEST,
        DefaultBlockParameterName.LATEST));
    }

    @Test
    void testZapCooridnatorGetUpdateContractEvents() {
        List eventResponse = coordinator.getUpdatedContractEvents(txReceipt);
        assertNotNull(eventResponse);
        // System.out.println("GetUpdateContractEvents(): " + eventResponse.size());
    }

    @Test
    void testZapCoordinatorUpdatedContractEventFlowable() {
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
        DefaultBlockParameterName.LATEST, coord2.getContractAddress());
        Flowable eventResponse = coordinator.updatedContractEventFlowable(filter);
        assertNotNull(eventResponse);
        // System.out.println("UpdatedContractEventFlowable(): " + eventResponse.count());

        assertNotNull(coordinator.updatedContractEventFlowable(DefaultBlockParameterName.EARLIEST,
        DefaultBlockParameterName.LATEST));
    }

    @Test
    void testZapCoordinatorGetUpdatedDependenciesEvents() {
        List eventResponse = coordinator.getUpdatedDependenciesEvents(txReceipt);
        assertNotNull(eventResponse);
    }

    @Test
    void testZapCoordinatorUpdateDependenciesEventFlowable() {
        EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
        DefaultBlockParameterName.LATEST, coord2.getContractAddress());
        Flowable eventResponse = coordinator.updatedDependenciesEventFlowable(filter);
        assertNotNull(eventResponse);

        assertNotNull(coordinator.updatedDependenciesEventFlowable(DefaultBlockParameterName.EARLIEST,
        DefaultBlockParameterName.LATEST));
    }
}