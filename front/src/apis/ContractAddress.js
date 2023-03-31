import Web3 from "web3"
import O2Token from "contracts/O2Token.json";
import MARS_NFT from "contracts/MARS_NFT.json";
import SaleFactory from "contracts/SaleFactory.json"

export const web3 = new Web3(window.ethereum)
export const chainId = 2731

export const O2_CONTRACT_ADDRESS = "0xF138bcf78b44eE56e735AB308bBcD89e202C586E";
export const MARS_CONTRACT_ADDRESS = "0x4C8b2A1D710220ea3180DF8F7266E4228A89cf81";
export const SALE_FACTORY_CONTRACT_ADDRESS = "0xc12855f8214C43846F88D7D1D1975192113B0497";

export const O2Contract = new web3.eth.Contract(O2Token.abi, O2_CONTRACT_ADDRESS);
export const NFTContract = new web3.eth.Contract(MARS_NFT.abi, MARS_CONTRACT_ADDRESS);
export const SaleContract = new web3.eth.Contract(SaleFactory.abi,SALE_FACTORY_CONTRACT_ADDRESS);
