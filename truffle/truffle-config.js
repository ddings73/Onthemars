module.exports = {
  networks: {
    development: {
      host: "j8e207.p.ssafy.io", // Localhost (default: none)
      port: 8545, // Standard Ethereum port (default: none)
      network_id: "2731", // Any network (default: none)
      gasPrice: "0",
    },
  },

  // Set default mocha options here, use special reporters, etc.
  mocha: {},

  // Configure your compilers
  compilers: {
    solc: {
      version: "0.8.18", // Fetch exact version from solc-bin (default: truffle's version)
    },
  },
};
