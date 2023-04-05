import { baseURL, imgBaseURL } from 'apis/baseApi';
import axios from 'axios';
import Card from 'component/nftCard/card';
import { useEffect, useState } from 'react';
import { NftSearchDetail } from 'Store/type/NftSearchDetail';
import { BuyDiv } from './buy';
import styles from './index.module.scss';
import { NFTInfo } from './Info';
import { ItemActivity } from './ItemActivity';
import { NftDetailNameInfo } from './NftDetailNameInfo';
import { PriceHistory } from './PriceHistory';

export function NFTDetail() {
  const url = window.location.href.split("/");
  const transactionId: string = url[url.length - 1]
  const [data, setData] = useState<NftSearchDetail>();


  useEffect(() => {
    axios({
      method: 'get',
      url: baseURL + `/nft/detail/${transactionId}`,
      headers: {
        Authorization: sessionStorage.getItem('accessToken'),
      },
    }).then((res) => {
      console.log(' bb', res.data);
      setData(res.data);
    });
  }, [transactionId]);

  if (!data) {
    return null;
  }

  return (
    <div className={styles.container}>
      <div className={styles.leftDiv}>
        <Card size="lg" img_address={imgBaseURL + data.imgUrl} />
        <NFTInfo detaildata={data} />
      </div>
      <div className={styles.rightDiv}>
        <NftDetailNameInfo detaildata={data} />
        <BuyDiv isOwner={data.isOwner} transactionId={data.info.transactionId} price={data.price} activated={data.activated} nickname={data.ownerNickname} />
        <PriceHistory />
        <ItemActivity />
      </div>
    </div>
  );
}
