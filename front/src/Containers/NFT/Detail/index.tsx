import Card from 'component/nftCard/card';
import { BuyDiv } from './buy';
import styles from './index.module.scss';
import { NFTInfo } from './Info';
import { ItemActivity } from './ItemActivity';
import { NftDetailNameInfo } from './NftDetailNameInfo';
import { PriceHistory } from './PriceHistory';

export function NFTDetail() {
  const test = {
    ownerNickname: 'mihyeon', // 소유자 nickname
    cropParent: 'Carrots', // Carrots
    nftName: 'Carrot #0123', // Carrot #0123
    viewCnt: 456,
    price: 30.45,
    tier: 2,
    activated: true, // 현재 거래중인 nft인가요?
    isFavorite: false, // true : 좋아요, false : 안 좋아요
    imgUrl: 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg',
    info: {
      attributes: [
        { value: 'Tier', data: 'Orange' },
        { value: 'Background', data: 'Default' },
        { value: 'Eyes', data: 'Default' },
        { value: 'Mouth', data: 'Smile' },
        { value: 'Head Gear', data: 'Default' },
      ],
      address: '0x1a9216d8568645312c',
      tokenId: '0123',
      tokenStandard: 'ERC-721',
      chain: 'Ethereum',
      lastUpdate: '2023-02-23',
      dna: '1325896856254562',
    },
  };

  return (
    <div className={styles.container}>
      <div className={styles.leftDiv}>
        <Card size="lg" img_address={test.imgUrl} />
        <NFTInfo detaildata={test} />
      </div>
      <div className={styles.rightDiv}>
        <NftDetailNameInfo detaildata={test} />
        <BuyDiv data={test.price} />
        <PriceHistory />
        <ItemActivity />
      </div>
    </div>
  );
}
