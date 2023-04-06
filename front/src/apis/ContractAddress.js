import Web3 from 'web3';
import O2Token from 'contracts/O2Token.json';
import MARS_NFT from 'contracts/MARS_NFT.json';
import SaleFactory from 'contracts/SaleFactory.json';

export const web3 = new Web3(window.ethereum);
export const chainId = 2731;

export const O2_CONTRACT_ADDRESS = '0xabeFCDB830b2F60F5A90732B857BC5CC82AfCA1A';
export const MARS_CONTRACT_ADDRESS = '0xD1f686A503557A338003ec761e171579a07f2932';
export const SALE_FACTORY_CONTRACT_ADDRESS = '0x4183b721df77D54aB38A6778Cc7283df05dd1731';
export const ADMIN_ADDRESS = '0x361d589c907ddbf1545b12e8df812b1b5a4b2953';

export const O2Contract = new web3.eth.Contract(O2Token.abi, O2_CONTRACT_ADDRESS);
export const NFTContract = new web3.eth.Contract(MARS_NFT.abi, MARS_CONTRACT_ADDRESS);
export const SaleContract = new web3.eth.Contract(SaleFactory.abi, SALE_FACTORY_CONTRACT_ADDRESS);