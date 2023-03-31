// SPDX-License-Identifier: MIT
pragma solidity >=0.7.0 <0.9.0;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";


contract MARS_NFT is ERC721Enumerable{
    using Counters for Counters.Counter;

    // uint == uint256 
    constructor() ERC721("MARS_NFT", "MARSNFT") {}

    Counters.Counter private _tokenId;
    mapping (uint256 => uint40) private _marsDnas;
    
        
    // nft 생성(민팅)
    function mint(uint40 crop, uint256 nonce) public returns (uint256){
        _tokenId.increment();

        uint256 newTokenId = _tokenId.current();
        
        uint40 color = uint40(uint(keccak256(abi.encodePacked(block.timestamp, _msgSender(), nonce))) % 11);
        crop = createGen(crop, 10, nonce);
        color = createGen(color, 11, nonce);
        // 배경은 10가지 이고 0은 none 으로 쓰기로 했으니 나누기 11 
        _marsDnas[newTokenId] = 10000000000 + crop * 100000000 + color * 1000000;
        
        _mint(_msgSender(), newTokenId);
        return newTokenId;
    }

    function burn(uint256 tokenId) public {
        _burn(tokenId);

        if(_marsDnas[tokenId] != 0){
            delete _marsDnas[tokenId];
        }
    }

    // nft 조합
    function combNFT(uint256 nft1TokenId, uint256 nft2TokenId, uint256 nonce) public returns (uint256){
        require(_msgSender() == ownerOf(nft1TokenId), "you are not NFT owner");
        require(_msgSender() == ownerOf(nft2TokenId), "you are not NFT owner");
        require(nft1TokenId != nft2TokenId, "same token Id");

        uint40 nft1Dna = getNftDna(nft1TokenId);
        uint40 nft2Dna = getNftDna(nft2TokenId);
        
        _burn(nft1TokenId);
        _burn(nft2TokenId);

        _tokenId.increment();
        uint256 newTokenId = _tokenId.current();

        // 조합 시작
        uint40 t2DnaBase = nft1Dna + nft2Dna;
        uint40 t2Dna = uint40(uint(keccak256(abi.encodePacked(block.timestamp, _msgSender(), nonce))) % 1000000);
        uint40 t2Crop = uint40(((nft1Dna - 10000000000) / 100000000) % 10);
        t2Crop = createGen(t2Crop, 10, nonce);

        uint40 dna = 20000000000 + (t2Crop * 100000000) + (t2DnaBase % 100000000) + t2Dna;
        _marsDnas[newTokenId] = dna;

        _mint(_msgSender(), newTokenId);
        return newTokenId;
    }

    function approveTo(address to, uint tokenId) public{
        _approve(to, tokenId);
    }

    function createGen(uint40 item, uint256 size, uint256 nonce) private view returns (uint40){
        return uint40(item + uint40(uint(keccak256(abi.encodePacked(block.timestamp, _msgSender(), nonce))) % 10000) * size) % 100;
    }

    // tokenIds 는 생성된 token 수 만큼 증가한다.
    // 현재까지 생성된 nft 수 리턴 (지워진 토큰 포함)
    function current() public view returns (uint256) {
        return _tokenId.current();
    }
    

    // nft의 유전 정보 조회
    function getNftDna(uint256 tokenId) public view returns (uint40){
        return _marsDnas[tokenId];
    }
}