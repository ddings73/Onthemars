export interface CategoryInfoType {
  backImg: string, //배경 이미지
  cardImg: string,
  cropParent: string,   // Carrots
  cropBio: string,      // 당근은 눈에 좋아!
  totalVolume: number,
  floorPrice: number,
  listed: number,          // 반올림한 정수값
  mintedCnt: number,
}

export interface CategoryData {
  imgUrl: string,
  nftName: string,
  price: number,      // 가격 없는 경우 -1.0
  lastSalePrice: number,
  address: string,
}

export interface MainCategoryRank {
  rank: number,
  cropType: string,   // CRS01
  imgUrl: string,
  cropParent: string, // Carrots (NFT 이름)
  floorPrice: number,
  volume: number
}