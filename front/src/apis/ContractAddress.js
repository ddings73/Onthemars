import Web3 from 'web3';
import O2Token from 'contracts/O2Token.json';
import MARS_NFT from 'contracts/MARS_NFT.json';
import SaleFactory from 'contracts/SaleFactory.json';

export const web3 = new Web3(window.ethereum);
export const chainId = 2731;

export const O2_CONTRACT_ADDRESS = '0xcbD36033936D49935E670203dCfb6e9F9B1eF49c';
export const MARS_CONTRACT_ADDRESS = '0x45f6b5cE51f51e34d0beB46333F5DF2f942eFd3e';
export const SALE_FACTORY_CONTRACT_ADDRESS = '0x69E81C1F2b6730A5eAB5e7caC9D5F9304459F4E0';
export const ADMIN_ADDRESS = '0x361d589c907ddbf1545b12e8df812b1b5a4b2953';

export const O2Contract = new web3.eth.Contract(O2Token.abi, O2_CONTRACT_ADDRESS);
export const NFTContract = new web3.eth.Contract(MARS_NFT.abi, MARS_CONTRACT_ADDRESS);
export const SaleContract = new web3.eth.Contract(SaleFactory.abi, SALE_FACTORY_CONTRACT_ADDRESS);
