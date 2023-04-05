import React, { useEffect, useState } from 'react';
import styles from './UnityContainer.module.scss';
import unityres from './unityres.json';
import { O2Contract, NFTContract, MARS_CONTRACT_ADDRESS, ADMIN_ADDRESS } from 'apis/ContractAddress';
import { api } from 'apis/api/ApiController';

function UnityContainer() {
  const address = sessionStorage.getItem('address');
  const [colorUrl, setColorUrl] = useState('');
  const [cropUrl, setCropUrl] = useState('');
  console.log(unityres);
  const getData = () => {
    // 씨앗 구매
    const buySeedPrice = unityres.player.buySeedCnt * 1000;
    O2Contract.methods.transfer(ADMIN_ADDRESS, buySeedPrice).send({
      from: address,
      gasPrice: '0',
    });

    // 민팅
    const harvestCropList = unityres.player.harvestCropList;
    harvestCropList.map((item: any) => {
      const crop = item.type.substr(3, 2);
      const nonce = Math.floor(Math.random() * 100001);
      const tokenId = NFTContract.methods.mint(crop, nonce).send({
        from: address,
        gasPrice: '0',
      });
      // dna
      const dna = NFTContract.methods.getNftDna(tokenId).call();

      // 1단계 조회
      api
        .get(`farm/${dna}`, {
          headers: { Authorization: sessionStorage.getItem('accessToken') },
        })
        .then((res) => {
          setColorUrl(res.data.colorUrl);
          setCropUrl(res.data.cropUrl);
        });

      // 이미지 합성

      // 노션따라서 데이터 가공
      
      // 그걸 백으로 보내
    });
  };

  return (
    <>
      <div className={styles.container}></div>
    </>
  );
}

export default UnityContainer;
