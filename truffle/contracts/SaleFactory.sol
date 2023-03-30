// // SPDX-License-Identifier: MIT
pragma solidity >=0.7.0 <0.9.0;


import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
import "./Sale.sol";
import "./O2Token.sol";
import "./MARS_NFT.sol";


contract SaleFactory is Ownable {
    using Counters for Counters.Counter;

    // Sale ID(1씩 자동 증가)
    Counters.Counter private _saleIds;
    // Sale 컨트랙트 주소
    mapping(uint256 => address) private _saleAddrs;
    // NFT 판매자
    mapping(uint256 => address) private _saleSellers;
    // NFT 구매자
    mapping(uint256 => address) private _saleBuyers;
    // 해당 지갑 주소가 판매자였던 Sale ID 목록
    mapping(address => uint256[]) private _sellSaleIdsByWallet;
    // 해당 지갑 주소가 구매자였던 Sale ID 목록
    mapping(address => uint256[]) private _buySaleIdsByWallet;
    // 해당 지갑 주소의 모든 Sale ID 목록
    mapping(address => uint256[]) private _saleIdsByWallet;
    // 해당 NFT ID의 모든 Sale ID 목록
    mapping(uint256 => uint256[]) private _saleIdsOfMARS_NFT;
    // 해당 NFT ID의 판매 상태
    mapping(uint256 => bool) private _saleStatusOfMARS_NFT;
    // 해당 NFT ID가 현재 판매중인 Sale
    mapping(uint256 => uint256) private _currentSaleOfMARS_NFT;

    // OT 토큰 활용을 위한 ERC-20 토큰 컨트랙트 주소
    address private _O2TokenContractAddress;
    // MARS_NFT 활용을 위한 ERC-721 토큰 컨트랙트 주소
    address private _MARS_NFTContractAddress;

    /**
    * constructor
    * Sale 컨트랙트를 관리하는 SaleFactory 생성
    * 
    * @ param address O2TokenContractAddress O2 토큰(ERC-20) 컨트랙트 주소
    * @ param address MARS_NFTContractAddress NFT(ERC-721) 컨트랙트 주소
    * @ return None
    * @ exception None
    */
    constructor(address O2TokenContractAddress, address MARS_NFTContractAddress) payable {
        _O2TokenContractAddress = O2TokenContractAddress;
        _MARS_NFTContractAddress = MARS_NFTContractAddress;
    }

    /**
    * createSale
    * 새로운 Sale 컨트랙트를 생성하고 관리할 정보들을 갱신
    * 
    * @ param uint256 MARS_NFT_Id MARS_NFT ID 토큰아이디인듯
    * @ param address seller 판매자
    * @ param uint256 buyNowPrice 즉시 구매 금액
    * @ param uint256 startedAt 판매 시작 시간
    * @ return None
    * @ exception 즉시 구매 금액은 0 이상이어야 함
    * @ exception MARS_NFT가 판매자의 소유여야 함    
    * @ exception 해당 MARS_NFT가 판매중이어서는 안됨
    */
    function createSale(uint256 MARS_NFT_Id, address seller, uint256 buyNowPrice) public payable {
        require(buyNowPrice > 0, "Price must be higher than 0.");
        require(MARS_NFT(_MARS_NFTContractAddress).ownerOf(MARS_NFT_Id) == seller, "Seller is not owner.");
        require(!_saleStatusOfMARS_NFT[MARS_NFT_Id], "This MARS_NFT is already on sale.");

        // 새로운 Sale의 ID 결정
        _saleIds.increment();
        uint256 newSaleId = _saleIds.current();
        
        // 새로운 Sale 컨트랙트 생성
        Sale newSale = new Sale(MARS_NFT_Id, seller, buyNowPrice, block.timestamp, _O2TokenContractAddress, _MARS_NFTContractAddress);

        // 새로운 Sale 컨트랙트가 거래 대상인 MARS_NFT에 대한 접근 권한을 획득
        MARS_NFT(_MARS_NFTContractAddress).approveTo(address(newSale), MARS_NFT_Id);

        // Sale 관리 정보 갱신
        _saleAddrs[newSaleId] = address(newSale);
        _saleSellers[newSaleId] = seller;
        _sellSaleIdsByWallet[seller].push(newSaleId);
        _saleIdsByWallet[seller].push(newSaleId);
        _saleIdsOfMARS_NFT[MARS_NFT_Id].push(newSaleId);
        _saleStatusOfMARS_NFT[MARS_NFT_Id] = true;
        _currentSaleOfMARS_NFT[MARS_NFT_Id] = newSaleId;
    }

    /**
    * cancelSale
    * 해당 Sale을 취소하는 함수를 호출한다.
    * 
    * @ param uint256 saleId 취소할 Sale ID
    * @ return None
    * @ exception 취소할 Sale이 진행중이어야 함
    * @ exception 취소할 Sale이 취소상태가 아니어야 함
    */
    function cancelSale(uint256 saleId) public {
        Sale canceledSale = Sale(_saleAddrs[saleId]);
        require(!canceledSale.getIsEnded(), "This sale is already ended.");
        require(!canceledSale.getIsCanceled(), "This sale is already canceled.");

        canceledSale.cancel();

        _saleStatusOfMARS_NFT[canceledSale.getMARS_NFT_Id()] = false;
    }


    /**
    * buyNow
    * Sale의 즉시 구매 금액으로 구매하는 함수를 호출한다.
    * 구매자 관련 정보를 갱신한다.
    * 
    * @ param uint256 saleId Sale ID
    * @ param address buyer 구매자 지갑 주소
    * @ return None
    * @ exception 거래가 종료상태가 아니어야 함
    * @ exception 판매자가 MARS_NFT의 소유자여야 함
    * @ exception 구매자가 즉시 구매 금액 이상의 금액을 가지고 있어야 함
    */
    function SalePrice(uint256 saleId) public view returns(uint256) {
        return Sale(_saleAddrs[saleId]).getPrice();
    }

    function buyerBal(address buyer) public view returns(uint256){
        return O2Token(_O2TokenContractAddress).balanceOf(buyer);
    }

    function buyNow(uint256 saleId, address buyer) public payable {
        Sale finishedSale = Sale(_saleAddrs[saleId]);
        require(!finishedSale.getIsEnded(), "This sale is already ended.");
        require(MARS_NFT(_MARS_NFTContractAddress).ownerOf(finishedSale.getMARS_NFT_Id()) != buyer, "Buyer must non equal owner of the token.");
        require(MARS_NFT(_MARS_NFTContractAddress).ownerOf(finishedSale.getMARS_NFT_Id()) == finishedSale.getSeller(), "Seller must equal owner of the token.");
        require(O2Token(_O2TokenContractAddress).balanceOf(buyer) >= finishedSale.getPrice(), "Buyer's balance is exhausted......");

        finishedSale.buyNow(buyer);

        reportBuyer(saleId, buyer);
        _saleStatusOfMARS_NFT[finishedSale.getMARS_NFT_Id()] = false;
    }

    /** 
    * reportBuyer
    * Sale 종료 후 구매자 정보 기록
    * 
    * @ param uint256 endedSaleId 종료된 Sale Id
    * @ param address saleBuyer 구매자
    * @ return None
    * @ exception None
    */
    function reportBuyer(uint256 endedSaleId, address saleBuyer) private {
        _saleBuyers[endedSaleId] = saleBuyer;
        _buySaleIdsByWallet[saleBuyer].push(endedSaleId);
        _saleIdsByWallet[saleBuyer].push(endedSaleId);
    }

    function getSale(uint256 saleId) public view returns(address) {
        return _saleAddrs[saleId];
    }

    function getSeller(uint256 saleId) public view returns(address) {
        return _saleSellers[saleId];
    }

    function getBuyer(uint256 saleId) public view returns(address) {
        require(_saleBuyers[saleId] != address(0), "This sale is proceeding or canceled.");

        return _saleBuyers[saleId];
    }

    function getSellIdsByWallet(address seller) public view returns(uint256[] memory) {
        return _sellSaleIdsByWallet[seller];
    }

    function getBuyIdsByWallet(address buyer) public view returns(uint256[] memory) {
        return _buySaleIdsByWallet[buyer];
    }

    function getSaleIdsByWallet(address walletAddr) public view returns(uint256[] memory) {
        return _saleIdsByWallet[walletAddr];
    }

    function getSaleIdsOfMARS_NFT(uint256 MARS_NFT_Id) public view returns(uint256[] memory) {
        return _saleIdsOfMARS_NFT[MARS_NFT_Id];
    }

    function getSaleStatusOfMARS_NFT(uint256 MARS_NFT_Id) public view returns(bool) {
        return _saleStatusOfMARS_NFT[MARS_NFT_Id];
    }

    function getCurrentSaleOfMARS_NFT(uint256 MARS_NFT_Id) public view returns(uint256) {
        require(_saleStatusOfMARS_NFT[MARS_NFT_Id], "This MARS_NFT is not on sale.");
        return _currentSaleOfMARS_NFT[MARS_NFT_Id];
    }
}