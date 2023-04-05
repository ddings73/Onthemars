// SPDX-License-Identifier: MIT
pragma solidity >=0.7.0 <0.9.0;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC721/IERC721Receiver.sol";
import "@openzeppelin/contracts/utils/math/SafeMath.sol";

import "./O2Token.sol";
import "./MARS_NFT.sol";

contract Sale is Ownable, IERC721Receiver {
    using SafeMath for uint256;

    // 판매하는 MFT ID
    uint256 private _MARS_NFT_Id;
    // 판매자의 지갑 주소
    address private _seller;
    // 구매자의 지갑 주소
    address private _buyer;
    // 즉시 구매 금액
    uint256 private _price;
    // 판매 시작 시간
    uint256 private _startedAt;
    // 판매 종료 시간
    uint256 private _endedAt;
    // 판매 종료 여부
    bool private _isEnded;
    // 판매 취소 여부
    bool private _isCanceled;

    // O2 토큰 활용을 위한 ERC-20 토큰 컨트랙트
    O2Token private _O2TokenContract;
    // MFT 활용을 위한 ERC-721 토큰 컨트랙트
    MARS_NFT private _MARS_NFTContract;

    /**
    * constructor
    * 새로운 Sale 컨트랙트 생성
    * 
    * @ param uint256 MFTId MFT ID
    * @ param address seller 판매자
    * @ param uint256 price 즉시 구매 금액
    * @ param uint256 startedAt 판매 시작 시간
    * @ param address O2TokenContractAddress O2 토큰(ERC-20) 컨트랙트 주소
    * @ param address MARS_NFTContractAddress MARS_NFT(ERC-721) 컨트랙트 주소
    * @ return None
    * @ exception 즉시 구매 금액은 0 이상이어야 함
    * @ exception MFT가 판매자의 소유여야 함    
    */
    constructor(
        uint256 MARS_NFT_Id, 
        address seller, 
        uint256 price, 
        uint256 startedAt, 
        address O2TokenContractAddress,
        address MARS_NFTContractAddress
    ) {
        require(price > 0, "Price must be higher than 0.");
        require(MARS_NFT(MARS_NFTContractAddress).ownerOf(MARS_NFT_Id) == seller, "Seller is not owner.");

        _MARS_NFT_Id = MARS_NFT_Id;
        _seller = seller;
        _price = price;
        _startedAt = startedAt;
        _isEnded = false;
        _isCanceled = false;
        _O2TokenContract = O2Token(O2TokenContractAddress);
        _MARS_NFTContract = MARS_NFT(MARS_NFTContractAddress);
    }

    /**
    * end
    * 거래 종료시간을 기록한다.
    * 해당 Sale을 종료 처리한다.
    * 
    * @ param None
    * @ return None
    * @ exception 종료할 Sale이 진행중이어야 함
    * @ exception 종료할 Sale이 취소상태가 아니어야 함
    */
    function end() public {
        _endedAt = block.timestamp;
        _isEnded = true;
    }

    /**
    * cancel
    * 해당 Sale을 취소 상태로 만들고,
    * 취소상태가 아닌 제안들을 환불한다.
    * 위 절차가 완료 되면 해당 Sale을 종료 처리한다.
    * 
    * @ param None
    * @ return None
    * @ exception 취소할 Sale이 진행중이어야 함
    * @ exception 취소할 Sale이 취소상태가 아니어야 함
    */
    function cancel() public {
        require(!_isEnded, "This sale is already ended.");
        require(!_isCanceled, "This sale is already canceled.");
        
        _isCanceled = true;
        end();
    }

    function balanceOf() public view returns(uint256) {
        return _O2TokenContract.balanceOf(address(this));
    }

    /**
    * buyNow
    * 판매자의 MFT와 구매자의 O2Token을 서로 Transfer한다. 
    * 구매 관련 정보를 갱신한다.
    * 해당 Sale을 종료처리한다.
    * 
    * @ param address buyer 구매자 지갑 주소
    * @ return None
    * @ exception 거래가 종료상태가 아니어야 함
    * @ exception 판매자가 MARS_NFT의 소유자여야 함
    * @ exception 구매자가 즉시 구매 금액 이상의 금액을 가지고 있어야 함
    */
    function buyNow(
        address buyer
    ) public payable {
        require(!_isEnded, "This sale is already ended.");
        require(_MARS_NFTContract.ownerOf(_MARS_NFT_Id) == _seller, "seller is not owner");
        require(_O2TokenContract.balanceOf(buyer) >= _price, "Buyer's balance is exhausted.");

        _MARS_NFTContract.safeTransferFrom(_seller, buyer, _MARS_NFT_Id);
        
        _O2TokenContract.transferFrom(buyer, _seller, _price);

        _buyer = buyer;
        end();
    }

    function getMARS_NFT_Id() public view returns(uint256) {
        return _MARS_NFT_Id;
    }

    function getSeller() public view returns(address) {
        return _seller;
    }

    function getBuyer() public view returns(address) {
        return _buyer;
    }

    function getPrice() public view returns(uint256) {
        return _price;
    }

    function getStartedAt() public view returns(uint256) {
        return _startedAt;
    }

    function getEndedAt() public view returns(uint256) {
        require(_isEnded, "This sale is not ended yet.");
        return _endedAt;
    }

    function getIsEnded() public view returns(bool) {
        return _isEnded;
    }

    function getIsCanceled() public view returns(bool) {
        return _isCanceled;
    }

    function onERC721Received(address _operator, address _from, uint256 _tokenId, bytes memory _data) override external pure returns(bytes4)
    {
        return this.onERC721Received.selector;
    }
}
