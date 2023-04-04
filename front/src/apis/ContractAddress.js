import Web3 from 'web3';
import O2Token from 'contracts/O2Token.json';
import MARS_NFT from 'contracts/MARS_NFT.json';
import SaleFactory from 'contracts/SaleFactory.json';

export const web3 = new Web3(window.ethereum);
export const chainId = 2731;

export const O2_CONTRACT_ADDRESS = '0xaC89d6173F68b08Afa6a72478D1371238d12f175';
export const MARS_CONTRACT_ADDRESS = '0x28Fb0412aCb2A1107b378596D690611a9296AF27';
export const SALE_FACTORY_CONTRACT_ADDRESS = '0x8974Be1FcCE5a14920571AC12D74e67D0B7632Bf';
export const ADMIN_ADDRESS = '0x361d589c907ddbf1545b12e8df812b1b5a4b2953';

export const O2Contract = new web3.eth.Contract(O2Token.abi, O2_CONTRACT_ADDRESS);
export const NFTContract = new web3.eth.Contract(MARS_NFT.abi, MARS_CONTRACT_ADDRESS);
export const SaleContract = new web3.eth.Contract(SaleFactory.abi, SALE_FACTORY_CONTRACT_ADDRESS);
