package io.github.zapproject.jzap;

import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;


public abstract class BaseContract extends Contract {
    public String provider;
    public String name;
    public String address;
    public Web3j web3j;
    public int networkId;
    public ZapCoordinator coordinator;
    public Artifacts artifact = new Artifacts();

    BaseContract(BaseContractType type) {
        super(
            type.bytecode,
            type.address,
            type.web3j,
            type.credentials,
            type.contractGasProvider
        ); 
        
        // Below this is unecessary if class instances do not have to be accessed.
        // HashMap zapArtifact = new Artifacts().getMap("ZAPCOORDINATOR");
        
        // // this.name = type.artifactName;
        // this.provider = type.networkProvider;
        // this.networkId = type.networkId;
        // this.address = type.address;

        // Credentials creds = Credentials.create(type.accountKey);
        // ContractGasProvider gasPro = new DefaultGasProvider();

        // if (type.web3j != null) {
        //     this.web3j = type.web3j;   
        // } else {
        //     this.web3j = Web3j.build(new HttpService());
        // }

        // if (!type.coordinator.isEmpty()){
            // this.coordinator = ZapCoordinator.load(type);
        // } else {
        //     this.coordinator = ZapCoordinator.load(ZapCoordinator.BINARY, web3j, creds, gasPro);
        // }
    }

    BaseContract(String bytecode, NetworkProviderOptions type, String artifactName) throws Exception {
        super(
            bytecode,
            new Artifacts().getAddress(artifactName, type.networkId),
            type.web3j,
            type.credentials,
            type.contractGasProvider
        );
    }
}