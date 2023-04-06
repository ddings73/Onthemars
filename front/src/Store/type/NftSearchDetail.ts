export interface NftSearchDetail {
  ownerNickname: string; // 소유자 nickname
  cropParent: string;    // Carrots
  nftName: string;       // Carrot #0123
  viewCnt: number;
  price: number;
  tier: number;
  activated: boolean;    // 현재 거래중인 nft인가요?
  isFavorite: boolean;   // true : 좋아요; false : 안 좋아요
  isOwner: boolean;
  imgUrl: string;
  ownerAddress: string,
  info: {
    attributes: {
      value: string;
      data: string;
    }[];
    address: string;
    tokenId: string;
    tokenStandard: string;
    chain: string;
    lastUpdated: string;
    dna: string;
    transactionId: number;
  }
}