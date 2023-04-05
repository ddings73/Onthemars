// SPDX-License-Identifier: MIT
pragma solidity >=0.7.0 <0.9.0;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract O2Token is ERC20, Ownable{
    constructor () ERC20("O2Token", "O2") {
    }

    function decimals() override public pure returns (uint8) {
        return 2;
    }

    function mint(uint256 amount) public {
        _mint(_msgSender(), amount);
    }
    
    function mintToMember(address to, uint256 amount) public {
        _mint(to, amount);
    }

     function transferFrom(
        address from,
        address to,
        uint256 amount
    ) public virtual override returns (bool) {
        address spender = _msgSender();
        _approve(_msgSender(), from, amount);
        _spendAllowance(from, spender, amount);
        _transfer(from, to, amount);
        return true;
    }
}