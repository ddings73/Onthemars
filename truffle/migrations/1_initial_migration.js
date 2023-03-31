// const Migrations = artifacts.require("Migrations");
const MARS_NFT = artifacts.require("MARS_NFT.sol");
const O2Token = artifacts.require("O2Token.sol");
const SaleFactory = artifacts.require("SaleFactory.sol");

module.exports = function (deployer) {
  deployer
    .deploy(O2Token)
    .then(function () {
      return deployer.deploy(MARS_NFT);
    })
    .then(function () {
      return deployer.deploy(SaleFactory, O2Token.address, MARS_NFT.address);
    });
};
