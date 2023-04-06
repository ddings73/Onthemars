import Web3 from 'web3';
import O2Token from 'contracts/O2Token.json';
import MARS_NFT from 'contracts/MARS_NFT.json';
import SaleFactory from 'contracts/SaleFactory.json';

export const web3 = new Web3(window.ethereum);
export const chainId = 2731;

export const O2_CONTRACT_ADDRESS = '0xB614570513d83784Bbc077C7738E2AC23248397F';
export const MARS_CONTRACT_ADDRESS = '0x4eB6D96422A52228d622C3Bd13CFe07aa992538d';
export const SALE_FACTORY_CONTRACT_ADDRESS = '0x94DcBC303C43Ed8abe30e8429c0c4510333dA12E';
export const ADMIN_ADDRESS = '0x361d589c907ddbf1545b12e8df812b1b5a4b2953';

export const O2Contract = new web3.eth.Contract(O2Token.abi, O2_CONTRACT_ADDRESS);
export const NFTContract = new web3.eth.Contract(MARS_NFT.abi, MARS_CONTRACT_ADDRESS);
export const SaleContract = new web3.eth.Contract(SaleFactory.abi, SALE_FACTORY_CONTRACT_ADDRESS);